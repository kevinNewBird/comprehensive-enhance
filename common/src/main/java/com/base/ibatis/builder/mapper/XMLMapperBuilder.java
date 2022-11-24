package com.base.ibatis.builder.mapper;

import com.base.ibatis.builder.BaseBuilder;
import com.base.ibatis.builder.MapperBuilderAssistant;
import com.base.ibatis.builder.xml.XMLMapperEntityResolver;
import com.base.ibatis.exceptions.BuilderException;
import com.base.ibatis.exceptions.IncompleteElementException;
import com.base.ibatis.parsing.XNode;
import com.base.ibatis.parsing.XPathParser;
import com.base.ibatis.session.Configuration;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * description  XMLMapperBuilder <BR>
 * <p>
 * author: zhao.song
 * date: created in 9:48  2022/9/20
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XMLMapperBuilder extends BaseBuilder {

    // 用来存放sql片段的哈希表
    private final Map<String, XNode> sqlFragments;

    // 映射器构建助手
    private final MapperBuilderAssistant builderAssistant;

    private final String resource;

    private final XPathParser parser;

    private static final String MAPPER_ELE_EXPRESSION_CONS = "/mapper";

    private static final String SQL_ELE_EXPRESSION_CONS = "/mapper/sql";

    private static final String STMT_ELE_EXPRESSION_CONS = "select|insert|update|delete";

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments, String namespace) {
        this(inputStream, configuration, resource, sqlFragments);
        this.builderAssistant.setCurrentNamespace(namespace);
    }

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        // TODO variables后续可考虑配置文件加载(数据库连接信息), 后续评估是否可忽略
        /**
         * "url" -> "jdbc:mysql://localhost:3306/demo?serverTimezone=UTC"
         * "password" -> "root@123456"
         * "driver" -> "com.mysql.cj.jdbc.Driver"
         * "username" -> "root"
         */
        this(new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver()),
                configuration, resource, sqlFragments);
    }

    private XMLMapperBuilder(XPathParser parser, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        super(configuration);
        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
        this.parser = parser;
        this.sqlFragments = sqlFragments;
        this.resource = resource;

    }

    public String getCurrentNameSpace() {
       return this.builderAssistant.getCurrentNamespace();
    }


    /**
     * description   解析mapper.xml文件  <BR>
     *
     * @param :
     * @return
     * @author zhao.song  2022/9/20  10:00
     */
    public void parse() {

        // 处理mapper节点
        configurationElement(parser.evalNode(MAPPER_ELE_EXPRESSION_CONS));

    }

    private void configurationElement(XNode context) {
        // 1.获取mapper节点的命名空间
        String namespace = context.getStringAttribute("namespace");
        if (namespace == null || namespace.isEmpty()) {
            throw new BuilderException("Mapper's namespace cannot be empty");
        }
        this.builderAssistant.setCurrentNamespace(namespace);

        // TIP:忽略cache-ref、cache、parameterMap、resultMap等节点
        // REASON: 上述节点，对解析完成sql无实际的影响
        // TODO parameterMap待评估

        // 2.解析sql节点（tip：并非所有的sql节点都是可以被应用到sql语句中）
        sqlElement(context.evalNodes(SQL_ELE_EXPRESSION_CONS));

        // 3.解析select、update、insert、delete等SQL节点
        buildStatementFromContext(context.evalNodes(STMT_ELE_EXPRESSION_CONS));
    }

    private void sqlElement(List<XNode> list) {
        sqlElement(list, configuration.getDatabaseId());
    }

    /**
     * description   配置顶级标签<sql>  <BR>
     *
     * @param list:
     * @param requiredDatabaseId: 该字段保留
     * @return
     * @author zhao.song  2022/9/20  10:18
     */
    private void sqlElement(List<XNode> list, String requiredDatabaseId) {
        for (XNode context : list) {
            String databaseId = context.getStringAttribute("databaseId");
            String id = context.getStringAttribute("id");
            id = builderAssistant.applyCurrentNamespace(id, false);
            if (databaseIdMatchesCurrent(id, databaseId, requiredDatabaseId)) {
                sqlFragments.put(id, context);
            }
        }
    }

    private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
        if (requiredDatabaseId != null) {
            return requiredDatabaseId.equals(databaseId);
        }
        if (databaseId != null) {
            return false;
        }
        // 如果有重名的id了
        // <sql id="userColumns"> id,username,password </sql>
        if (!this.sqlFragments.containsKey(id)) {
            return true;
        }
        // skip this fragment if there is a previous one with a not null databaseId
        XNode context = this.sqlFragments.get(id);
        return context.getStringAttribute("databaseId") == null;
    }


    private void buildStatementFromContext(List<XNode> list) {
        if (configuration.getDatabaseId() != null) {
            buildStatementFromContext(list, configuration.getDatabaseId());
        }
        buildStatementFromContext(list, null);
    }

    private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
        for (XNode context : list) {
            final XMLStatementBuilder stmtParser = new XMLStatementBuilder(configuration, builderAssistant, context, requiredDatabaseId, sqlFragments);
            try {
                stmtParser.parseStatementNode();
            } catch (IncompleteElementException e) {
                // TODO sql语句不完成，塞入一个集合中
                configuration.addIncompleteStatement(stmtParser);
            }
        }
    }

}
