package com.base.ibatis.builder.xml;

import com.base.ibatis.builder.MapperBuilderAssistant;
import com.base.ibatis.exceptions.BuilderException;
import com.base.ibatis.exceptions.IncompleteElementException;
import com.base.ibatis.parsing.PropertyParser;
import com.base.ibatis.parsing.XNode;
import com.base.ibatis.session.Configuration;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * description  XMLIncludeTransformer <BR>
 * <p>
 * author: zhao.song
 * date: created in 11:09  2022/9/20
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XMLIncludeTransformer {

    private final Configuration configuration;
    private final MapperBuilderAssistant builderAssistant;

    public XMLIncludeTransformer(Configuration configuration, MapperBuilderAssistant builderAssistant) {
        this.configuration = configuration;
        this.builderAssistant = builderAssistant;
    }

    public void applyIncludes(Node source) {
        Properties variablesContext = new Properties();
        Properties variables = configuration.getVariables();
        Optional.ofNullable(variables).ifPresent(variablesContext::putAll);
        // 处理include子节点
        applyIncludes(source, variablesContext, false);
    }

    private void applyIncludes(Node source, final Properties variablesContext, boolean included) {
        // 处理include子节点
        if ("include".equals(source.getNodeName())) {
            // 查找refid属性指向的sql节点，返回的是其克隆的Node对象
            Node toInclude = findSqlFragment(getStringAttribute(source, "refid"), variablesContext);
            // 解析include节点下的property节点，将得到的键值对添加到variablesContext中，并形成新的Properties对象返回，用于替换占位符
            Properties toIncludeContext = getVariablesContext(source, variablesContext);
            // 递归处理Include节点，在sql节点中可能会使用include引用了其他SQL片段
            applyIncludes(toInclude, toIncludeContext, true);
            if (toInclude.getOwnerDocument() != source.getOwnerDocument()) {
                toInclude = source.getOwnerDocument().importNode(toInclude, true);
            }
            // 将include节点替换成sql节点
            source.getParentNode().replaceChild(toInclude, source);
            // 将sql节点的子节点添加到sql节点前面
            while (toInclude.hasChildNodes()) {
                toInclude.getParentNode().insertBefore(toInclude.getFirstChild(), toInclude);
            }
            // 删除sql节点
            toInclude.getParentNode().removeChild(toInclude);
        } else if (source.getNodeType() == Node.ELEMENT_NODE) {
            if (included && !variablesContext.isEmpty()) {
                // replace variables in attribute values
                NamedNodeMap attributes = source.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    attr.setNodeValue(PropertyParser.parse(attr.getNodeValue(), variablesContext));
                }
            }
            // 遍历当前SQL语句的子节点
            NodeList children = source.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                applyIncludes(children.item(i), variablesContext, included);
            }
        } else if (included && (source.getNodeType() == Node.TEXT_NODE || source.getNodeType() == Node.CDATA_SECTION_NODE)
                && !variablesContext.isEmpty()) {
            // replace variables in text node
            // 使用之前解析得到的properties对象替换对应的占位符
            source.setNodeValue(PropertyParser.parse(source.getNodeValue(), variablesContext));
        }
    }

    private Node findSqlFragment(String refid, Properties variables) {
        refid = PropertyParser.parse(refid, variables);
        refid = builderAssistant.applyCurrentNamespace(refid, true);
        try {
            //去之前存到内存map的SQL片段中寻找
            XNode nodeToInclude = configuration.getSqlFragments().get(refid);
            return nodeToInclude.getNode().cloneNode(true);
        } catch (IllegalArgumentException e) {
            throw new IncompleteElementException("Could not find SQL statement to include with refid '" + refid + "'", e);
        }
    }

    private String getStringAttribute(Node node, String name) {
        return node.getAttributes().getNamedItem(name).getNodeValue();
    }

    private Properties getVariablesContext(Node node, Properties inheritedVariablesContext) {
        Map<String, String> declaredProperties = null;
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                String name = getStringAttribute(n, "name");
                // Replace variables inside
                String value = PropertyParser.parse(getStringAttribute(n, "value"), inheritedVariablesContext);
                if (declaredProperties == null) {
                    declaredProperties = new HashMap<>();
                }
                if (declaredProperties.put(name, value) != null) {
                    throw new BuilderException("Variable " + name + " defined twice in the same include definition");
                }
            }
        }
        if (declaredProperties == null) {
            return inheritedVariablesContext;
        } else {
            Properties newProperties = new Properties();
            newProperties.putAll(inheritedVariablesContext);
            newProperties.putAll(declaredProperties);
            return newProperties;
        }
    }
}
