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
package com.base.ibatis.scripting;

import com.base.ibatis.mapping.SqlSource;
import com.base.ibatis.parsing.XNode;
import com.base.ibatis.session.Configuration;

//脚本语言驱动
public interface LanguageDriver {


  /**
   * 创建SQL源码(mapper xml方式)
   *
   * Creates an {@link SqlSource} that will hold the statement read from a mapper xml file.
   * It is called during startup, when the mapped statement is read from a class or an xml file.
   *
   * @param configuration The MyBatis configuration
   * @param script XNode parsed from a XML file
   * @param parameterType input parameter type got from a mapper method or specified in the parameterType xml attribute. Can be null.
   * @return the sql source
   */
  SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);

}
