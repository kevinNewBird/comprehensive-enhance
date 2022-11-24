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
package com.base.ibatis.builder.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Offline entity resolver for the MyBatis DTDs.
 *
 * 目的是未联网的情况下也能做DTD验证，实现原理就是将DTD搞到本地，然后用org.xml.sax.EntityResolver，最后调用DocumentBuilder.setEntityResolver来达到脱机验证
 * EntityResolver
 * public InputSource resolveEntity (String publicId, String systemId)
 * 应用程序可以使用此接口将系统标识符重定向到本地 URI
 * 但是用DTD是比较过时的做法，新的都改用xsd了
 * 这个类的名字并不准确，因为它被两个类都用到了（XMLConfigBuilder,XMLMapperBuilder）
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 */
public class XMLMapperEntityResolver implements EntityResolver {

  // 指定mybatis-config.xml文件和映射文件对应的DTD的SystemId
//  private static final String IBATIS_CONFIG_SYSTEM = "ibatis-3-config.dtd";
  private static final String IBATIS_MAPPER_SYSTEM = "ibatis-3-mapper.dtd";
//  private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-3-mapper.dtd";

  // 指定mybatis-config.xml文件和映射文件对应的DTD文件的具体位置
  private static final String MYBATIS_CONFIG_DTD = "com/base/ibatis/builder/xml/mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_DTD = "com/base/ibatis/builder/xml/mybatis-3-mapper.dtd";
//  private static final String MYBATIS_MAPPER_DTD = "D:\\work\\personal\\comprehensive_enhance\\common\\src\\main\\java\\com\\base\\ibatis\\builder\\xml\\mybatis-3-mapper.dtd";

  /**
   * Converts a public DTD into a local one.
   * 核心就是覆盖这个方法，达到转public DTD到本地DTD的目的
   *
   * @param publicId
   *          The public id that is what comes after "PUBLIC"
   * @param systemId
   *          The system id that is what comes after the public id.
   * @return The InputSource for the DTD
   *
   * @throws SAXException
   *           If anything goes wrong
   */
  @Override
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
    try {
      System.out.println(systemId);
      if (systemId != null) {
        String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
        // 查找SystemId指定的DTD文档，并调用getInputSource方法读取DTD文档
        if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM) || lowerCaseSystemId.contains(IBATIS_MAPPER_SYSTEM)) {
          return getInputSource(MYBATIS_MAPPER_DTD, publicId, systemId);
        }
      }
      return null;
    } catch (Exception e) {
      throw new SAXException(e.toString());
    }
  }

  private InputSource getInputSource(String path, String publicId, String systemId) {
    InputSource source = null;
    if (path != null) {
      try {
        InputStream in = XMLMapperEntityResolver.class.getResourceAsStream(path);
        source = new InputSource(in);
        source.setPublicId(publicId);
        source.setSystemId(systemId);
      } catch (Exception e) {
        // ignore, null is ok
      }
    }
    return source;
  }

}
