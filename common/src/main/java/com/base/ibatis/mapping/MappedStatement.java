/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.base.ibatis.mapping;


import com.base.ibatis.session.Configuration;

/**
 * 映射的语句
 *
 * @author Clinton Begin
 */
public final class MappedStatement {

    // 节点中的id属性
    private String resource;
    private Configuration configuration;
    private String id;
    private Integer line;
//  // SqlSource对象，对应一条SQL语句
    private SqlSource sqlSource;
//  // SQL的类型
    private SqlCommandType sqlCommandType;

    MappedStatement() {
        // constructor disabled
    }

    //静态内部类，建造者模式
    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, SqlSource sqlSource, SqlCommandType sqlCommandType) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sqlSource = sqlSource;
//      mappedStatement.statementType = StatementType.PREPARED;
//      mappedStatement.resultSetType = ResultSetType.DEFAULT;
//      mappedStatement.parameterMap = new ParameterMap.Builder(configuration, "defaultParameterMap", null, new ArrayList<>()).build();
//      mappedStatement.resultMaps = new ArrayList<>();
            mappedStatement.sqlCommandType = sqlCommandType;
//      mappedStatement.keyGenerator = configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType) ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
//      String logId = id;
//      if (configuration.getLogPrefix() != null) {
//        logId = configuration.getLogPrefix() + id;
//      }
//      mappedStatement.statementLog = LogFactory.getLog(logId);
//      mappedStatement.lang = configuration.getDefaultScriptingLanguageInstance();
        }

        public Builder line(Integer line) {
            mappedStatement.line = line;
            return this;
        }

        public Builder resource(String resource) {
            mappedStatement.resource = resource;
            return this;
        }

        public String id() {
            return mappedStatement.id;
        }


        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            assert mappedStatement.sqlSource != null;
//      assert mappedStatement.lang != null;
//      mappedStatement.resultMaps = Collections.unmodifiableList(mappedStatement.resultMaps);
            return mappedStatement;
        }
    }

//  public KeyGenerator getKeyGenerator() {
//    return keyGenerator;
//  }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public String getResource() {
        return resource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getId() {
        return id;
    }

    public Integer getLine() {
        return line;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }



    public BoundSql getBoundSql(Object parameterObject) {
        //其实就是调用sqlSource.getBoundSql
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
//    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//    if (parameterMappings == null || parameterMappings.isEmpty()) {
        boundSql = new BoundSql(configuration, boundSql.getSql(), /*parameterMap.getParameterMappings()*/null, parameterObject);
//    }

        // check for nested result maps in parameter mappings (issue #30)
//    for (ParameterMapping pm : boundSql.getParameterMappings()) {
//      String rmId = pm.getResultMapId();
//      if (rmId != null) {
//        ResultMap rm = configuration.getResultMap(rmId);
//        if (rm != null) {
//          hasNestedResultMaps |= rm.hasNestedResultMaps();
//        }
//      }
//    }

        return boundSql;
    }

    private static String[] delimitedStringToArray(String in) {
        if (in == null || in.trim().length() == 0) {
            return null;
        } else {
            return in.split(",");
        }
    }

}
