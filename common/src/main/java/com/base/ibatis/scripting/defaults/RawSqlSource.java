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
package com.base.ibatis.scripting.defaults;



import com.base.ibatis.builder.SqlSourceBuilder;
import com.base.ibatis.mapping.BoundSql;
import com.base.ibatis.mapping.SqlSource;
import com.base.ibatis.scripting.xmltags.DynamicContext;
import com.base.ibatis.scripting.xmltags.SqlNode;
import com.base.ibatis.session.Configuration;

import java.util.HashMap;

/**
 * 原始SQL源码，比DynamicSqlSource快
 *
 * Static SqlSource. It is faster than {@link DynamicSqlSource} because mappings are
 * calculated during startup.
 *
 * @since 3.2.0
 * @author Eduardo Macarron
 */
public class RawSqlSource implements SqlSource {

  // StaticSqlSource对象
  private final SqlSource sqlSource;

  public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
    // 调用getSql方法，完成SQL语句的拼装和初步解析
    this(configuration, getSql(configuration, rootSqlNode), parameterType);
  }

  public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
    // 通过SqlSourceBuilder完成占位符的解析和替换操作
    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
    Class<?> clazz = parameterType == null ? Object.class : parameterType;
    // SqlSourceBuilder.parse方法返回的是StaticSqlSource
    sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
  }

  private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
    DynamicContext context = new DynamicContext(configuration, null);
    rootSqlNode.apply(context);
    return context.getSql();
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    return sqlSource.getBoundSql(parameterObject);
  }

}
