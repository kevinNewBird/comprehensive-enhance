package com.base.ibatis.parsing;

import com.base.ibatis.builder.xml.XMLMapperEntityResolver;
import com.base.ibatis.builder.xml.XmlLocateHandler;
import com.base.ibatis.exceptions.BuilderException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * description  XPathParser <BR>
 * <p>
 * author: zhao.song
 * date: created in 15:57  2022/9/19
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XPathParser {

    /**
     * JAXP attribute used to configure the schema language for validation.
     */
    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    /**
     * JAXP attribute value indicating the XSD schema language.
     */
    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    // 文档对象
    private final Document document;
    // 是否开启验证
    private boolean validation;
    // 用于加载本地DTD文件
    private EntityResolver entityResolver;
    // mybatis-config.xml中properties标签定义的键值对集合
    private Properties variables;
    // Xpath对象
    private XPath xpath;

    public XPathParser(InputStream xmlStream) {
        commonConstructor(true, null, new XMLMapperEntityResolver());
        this.document = createDocument(new InputSource(xmlStream));
    }

    public XPathParser(InputStream inputStream, boolean validation, Properties variables, EntityResolver entityResolver) {
        commonConstructor(validation, variables, entityResolver);
        this.document = createDocument(new InputSource(inputStream));
    }

    //17.设置Properties
    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    // 通过一系列的eval*方法来解析boolean、short、long、int、string、Node等类型的信息
    public String evalString(String expression) {
        return evalString(document, expression);
    }

    public String evalString(Object root, String expression) {
        //1.先用xpath解析
        String result = (String) evaluate(expression, root, XPathConstants.STRING);
        //2.再调用PropertyParser去解析,也就是替换 ${} 这种格式的字符串
        result = PropertyParser.parse(result, variables);
        return result;
    }

    public Boolean evalBoolean(String expression) {
        return evalBoolean(document, expression);
    }

    public Boolean evalBoolean(Object root, String expression) {
        return (Boolean) evaluate(expression, root, XPathConstants.BOOLEAN);
    }

    public Short evalShort(String expression) {
        return evalShort(document, expression);
    }

    public Short evalShort(Object root, String expression) {
        return Short.valueOf(evalString(root, expression));
    }

    public Integer evalInteger(String expression) {
        return evalInteger(document, expression);
    }

    public Integer evalInteger(Object root, String expression) {
        return Integer.valueOf(evalString(root, expression));
    }

    public Long evalLong(String expression) {
        return evalLong(document, expression);
    }

    public Long evalLong(Object root, String expression) {
        return Long.valueOf(evalString(root, expression));
    }

    public Float evalFloat(String expression) {
        return evalFloat(document, expression);
    }

    public Float evalFloat(Object root, String expression) {
        return Float.valueOf(evalString(root, expression));
    }

    public Double evalDouble(String expression) {
        return evalDouble(document, expression);
    }

    public Double evalDouble(Object root, String expression) {
        return (Double) evaluate(expression, root, XPathConstants.NUMBER);
    }

    public List<XNode> evalNodes(String expression) {
        return evalNodes(document, expression);
    }

    //返回节点List
    public List<XNode> evalNodes(Object root, String expression) {
        List<XNode> xnodes = new ArrayList<>();
        NodeList nodes = (NodeList) evaluate(expression, root, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            xnodes.add(new XNode(this, nodes.item(i), variables));
        }
        return xnodes;
    }

    public XNode evalNode(String expression) {
        return evalNode(document, expression);
    }

    //返回节点
    public XNode evalNode(Object root, String expression) {
        Node node = (Node) evaluate(expression, root, XPathConstants.NODE);
        if (node == null) {
            return null;
        }
        return new XNode(this, node, variables);
    }

    private Object evaluate(String expression, Object root, QName returnType) {
        try {
            // 最终合流到这儿，直接调用XPath.evaluate
            return xpath.evaluate(expression, root, returnType);
        } catch (Exception e) {
            throw new BuilderException("Error evaluating XPath.  Cause: " + e, e);
        }
    }

    // 在创建文档之前一定要先调用commonConstructor方法完成初始化操作
    private Document createDocument(InputSource inputSource) {
        // important: this must only be called AFTER common constructor
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // 创建DocumentBuilderFactory对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 对DocumentBuilderFactory进行一些列的配置
//            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(validation);

            // 名称空间
            factory.setNamespaceAware(true);
            factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);
            // 忽略注释
            factory.setIgnoringComments(true);
            // 忽略空白
            factory.setIgnoringElementContentWhitespace(false);
            // 把 CDATA 节点转换为 Text 节点
            factory.setCoalescing(false);
            // 扩展实体引用
            factory.setExpandEntityReferences(true);

            // 创建DocumentBuilder对象并进行配置
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 需要注意的就是定义了EntityResolver(XMLMapperEntityResolver)，这样不用联网去获取DTD，
            // 将DTD放在org\apache\ibatis\builder\xml\mybatis-3-config.dtd,来达到验证xml合法性的目的
            builder.setEntityResolver(entityResolver);
            // ErrorHandler接口的方法都是空实现
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    // NOP
                }
            });
            // 加载xml文件
            Document document = builder.newDocument();
            final XmlLocateHandler xmlLocateHandler = new XmlLocateHandler(new Stack<Element>(), new StringBuilder(), document);
            parser.parse(inputSource, xmlLocateHandler);
            return document;
        } catch (Exception e) {
            throw new RuntimeException("Error creating document instance.  Cause: " + e, e);
        } finally {
            InputStream is = inputSource.getByteStream();
            if (Objects.nonNull(is)) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void commonConstructor(boolean validation, Properties variables, EntityResolver entityResolver) {
        this.validation = validation;
        this.entityResolver = entityResolver;
        this.variables = variables;
        //共通构造函数，除了把参数都设置到实例变量里面去以外，还初始化了XPath
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public static void main(String[] args) {
        XPathParser parser = new XPathParser(XPathParser.class.getClassLoader().getResourceAsStream("mapper/EmpDao.xml"));
        System.out.println(parser.document);
    }
}
