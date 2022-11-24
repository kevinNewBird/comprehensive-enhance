package com.base.ibatis.builder.mapper;

import com.base.ibatis.builder.BaseBuilder;
import com.base.ibatis.builder.MapperBuilderAssistant;
import com.base.ibatis.builder.xml.XMLIncludeTransformer;
import com.base.ibatis.executor.SelectKeyConstant;
import com.base.ibatis.mapping.SqlCommandType;
import com.base.ibatis.mapping.SqlSource;
import com.base.ibatis.parsing.XNode;
import com.base.ibatis.scripting.LanguageDriver;
import com.base.ibatis.session.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * description  XMLStatementBuilder <BR>
 * <p>
 * author: zhao.song
 * date: created in 10:40  2022/9/20
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XMLStatementBuilder extends BaseBuilder {

    private final XNode context;

    private final String requiredDatabaseId;

    private final MapperBuilderAssistant builderAssistant;


    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context, String databaseId, Map<String, XNode> sqlFragments) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.context = context;
        this.requiredDatabaseId = databaseId;
    }

    /**
     * description   解析语句(select|insert|update|delete) <BR>
     * <p>
     * //<select
     * //  id="selectPerson"
     * //  parameterType="int"
     * //  parameterMap="deprecated"
     * //  resultType="hashmap"
     * //  resultMap="personResultMap"
     * //  flushCache="false"
     * //  useCache="true"
     * //  timeout="10000"
     * //  fetchSize="256"
     * //  statementType="PREPARED"
     * //  resultSetType="FORWARD_ONLY">
     * //  SELECT * FROM PERSON WHERE ID = #{id}
     * //</select>
     *
     * @param :
     * @return
     * @author zhao.song  2022/9/20  10:46
     */
    public void parseStatementNode() {
        // 获取SQL节点的id以及databaseId属性
        String id = context.getStringAttribute("id");
        String databaseId = context.getStringAttribute("databaseId");

        // 如果databaseId不匹配，退出
//        if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
//            return;
//        }

        // 根据SQL节点的名称决定其SqlCommandType
        String nodeName = context.getNode().getNodeName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        // TIP:忽略非必要属性flushCache/useCache/resultOrdered的处理

        // include fragments before parsing
        XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
        includeParser.applyIncludes(context.getNode());

        // 参数类型(默认为null，通过xml加载，不会同用户的java类型交互)
        Class<?> parameterTypeClass = null;

        // 脚本语言，mybatis3.2的新功能
//        String lang = context.getStringAttribute("lang");
        LanguageDriver langDriver = getLanguageDriver(null);

        // TODO 解析之前先解析<selectKey>
        processSelectKeyNodes(id, null, langDriver);

        // 解析成SqlSource, 一般是DynamicSqlSource
        SqlSource sqlSource = langDriver.createSqlSource(configuration, context, null);

        builderAssistant.addMappedStatement(context, id, sqlSource, sqlCommandType);
    }

    private LanguageDriver getLanguageDriver(String lang) {
        // 获取默认的xml解析器
        return configuration.getLanguageDriver();
    }

    private void processSelectKeyNodes(String id, Class<?> parameterTypeClass, LanguageDriver langDriver) {
        List<XNode> selectKeyNodes = context.evalNodes("selectKey");
        // 解析selectKey节点
        if (configuration.getDatabaseId() != null) {
            parseSelectKeyNodes(id, selectKeyNodes, parameterTypeClass, langDriver, configuration.getDatabaseId());
        }
        parseSelectKeyNodes(id, selectKeyNodes, parameterTypeClass, langDriver, null);
        // 移除selectKey节点
        removeSelectKeyNodes(selectKeyNodes);
    }

    private void parseSelectKeyNodes(String parentId, List<XNode> list, Class<?> parameterTypeClass, LanguageDriver langDriver, String skRequiredDatabaseId) {
        for (XNode nodeToHandle : list) {
            String id = parentId + SelectKeyConstant.SELECT_KEY_SUFFIX;
            String databaseId = nodeToHandle.getStringAttribute("databaseId");
//            if (databaseIdMatchesCurrent(id, databaseId, skRequiredDatabaseId)) {
            parseSelectKeyNode(id, nodeToHandle, parameterTypeClass, langDriver, databaseId);
//            }
        }
    }

    private void removeSelectKeyNodes(List<XNode> selectKeyNodes) {
        for (XNode nodeToHandle : selectKeyNodes) {
            nodeToHandle.getParent().getNode().removeChild(nodeToHandle.getNode());
        }
    }

    private void parseSelectKeyNode(String id, XNode nodeToHandle, Class<?> parameterTypeClass
            , LanguageDriver langDriver, String databaseId) {
        // 生成SqlSource对象
        SqlSource sqlSource = langDriver.createSqlSource(configuration, nodeToHandle, parameterTypeClass);
        // selectKey节点中只能配置select语句
        SqlCommandType sqlCommandType = SqlCommandType.SELECT;

        // 通过MapperBuilderAssistant创建MappedStatement对象，并添加到mappedStatement集合中保存，该集合为StrictMap类型
        builderAssistant.addMappedStatement(nodeToHandle, id, sqlSource, sqlCommandType);
    }
}
