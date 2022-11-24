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
package com.base.ibatis.scripting.xmltags;


import com.base.ibatis.exceptions.ScriptingException;
import com.base.ibatis.parsing.GenericTokenParser;
import com.base.ibatis.parsing.TokenHandler;

import java.util.regex.Pattern;

/**
 * 文本SQL节点（CDATA|TEXT）
 *
 * @author Clinton Begin
 */
public class TextSqlNode implements SqlNode {
  private final String text;
  private final Pattern injectionFilter;

  public TextSqlNode(String text) {
    this(text, null);
  }

  public TextSqlNode(String text, Pattern injectionFilter) {
    this.text = text;
    this.injectionFilter = injectionFilter;
  }

  //判断是否是动态sql
  public boolean isDynamic() {
    DynamicCheckerTokenParser checker = new DynamicCheckerTokenParser();
    // 创建GenericTokenParser对象
    GenericTokenParser parser = createParser(checker);
    parser.parse(text);
    return checker.isDynamic();
  }

  @Override
  public boolean apply(DynamicContext context) {
    // 创建GenericTokenParser解析器
    GenericTokenParser parser = createParser(new BindingTokenParser(context, injectionFilter));
    // 将解析后的SQL片段添加到DynamicContext中
    context.appendSql(parser.parse(text));
    return true;
  }

  private GenericTokenParser createParser(TokenHandler handler) {
    // 解析${}占位符
    return new GenericTokenParser("${", "}", handler);
  }


  //绑定记号解析器
  private static class BindingTokenParser implements TokenHandler {

    private DynamicContext context;
    private Pattern injectionFilter;

    public BindingTokenParser(DynamicContext context, Pattern injectionFilter) {
      this.context = context;
      this.injectionFilter = injectionFilter;
    }

    @Override
    public String handleToken(String content) {
      // 获取用户提供的实参
      Object parameter = context.getBindings().get("_parameter");
      return "";
    }

    //检查是否匹配正则表达式
    private void checkInjection(String value) {
      if (injectionFilter != null && !injectionFilter.matcher(value).matches()) {
        throw new ScriptingException("Invalid input. Please conform to regex" + injectionFilter.pattern());
      }
    }
  }

  //动态SQL检查器
  private static class DynamicCheckerTokenParser implements TokenHandler {

    private boolean isDynamic;

    public DynamicCheckerTokenParser() {
      // Prevent Synthetic Access
    }

    public boolean isDynamic() {
      return isDynamic;
    }

    @Override
    public String handleToken(String content) {
      //灰常简单，设置isDynamic为true，即调用了这个类就必定是动态sql
      this.isDynamic = true;
      return null;
    }
  }

}
