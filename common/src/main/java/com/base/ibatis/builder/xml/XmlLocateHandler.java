package com.base.ibatis.builder.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Stack;

/**
 * description  XmlLocateHandler <BR>
 * <p>
 * author: zhao.song
 * date: created in 13:54  2022/9/19
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XmlLocateHandler extends DefaultHandler {

  public static final String LINE_NUMBER_KEY_NAME = "lineNumber";

  private Locator locator;

  private final Stack<Element> elementStack;

  private final StringBuilder textBuffer;

  private final Document doc;

  public XmlLocateHandler(Stack<Element> elementStack, StringBuilder textBuffer, Document doc) {
    this.elementStack = elementStack;
    this.textBuffer = textBuffer;
    this.doc = doc;
  }

  @Override
  public void setDocumentLocator(final Locator locator) {
    this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.

  }



  @Override
  public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)

    throws SAXException {
    addTextIfNeeded();

    final Element el = doc.createElement(qName);

    for (int i = 0; i < attributes.getLength(); i++) {
      el.setAttribute(attributes.getQName(i), attributes.getValue(i));

    }

    el.setUserData(LINE_NUMBER_KEY_NAME, String.valueOf(this.locator.getLineNumber()), null);

    elementStack.push(el);

  }

  @Override

  public void endElement(final String uri, final String localName, final String qName) {
    addTextIfNeeded();

    final Element closedEl = elementStack.pop();

    if (elementStack.isEmpty()) { // Is this the root element?

      doc.appendChild(closedEl);

    } else {
      final Element parentEl = elementStack.peek();

      parentEl.appendChild(closedEl);

    }

  }

  @Override

  public void characters(final char ch[], final int start, final int length) throws SAXException {
    textBuffer.append(ch, start, length);

  }

// Outputs text accumulated under the current node

  private void addTextIfNeeded() {
    if (textBuffer.length() > 0) {
      final Element el = elementStack.peek();

      final Node textNode = doc.createTextNode(textBuffer.toString());

      el.appendChild(textNode);

      textBuffer.delete(0, textBuffer.length());

    }

  }

  // 指定mybatis-config.xml文件和映射文件对应的DTD的SystemId
//  private static final String IBATIS_CONFIG_SYSTEM = "ibatis-3-config.dtd";
  private static final String IBATIS_MAPPER_SYSTEM = "ibatis-3-mapper.dtd";
  //  private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-3-mapper.dtd";

  // 指定mybatis-config.xml文件和映射文件对应的DTD文件的具体位置
  private static final String MYBATIS_CONFIG_DTD = "dtd/mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_DTD = "dtd/mybatis-3-mapper.dtd";

  @Override
  public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
    System.out.println(publicId+systemId);
    if (systemId != null) {
      String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
      // 查找SystemId指定的DTD文档，并调用getInputSource方法读取DTD文档
      if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM) || lowerCaseSystemId.contains(IBATIS_MAPPER_SYSTEM)) {
        return getInputSource(MYBATIS_MAPPER_DTD, publicId, systemId);
      }
    }
    return super.resolveEntity(publicId, systemId);
  }

  private InputSource getInputSource(String path, String publicId, String systemId) {
    InputSource source = null;
    if (path != null) {
      try {
        InputStream in = XMLMapperEntityResolver.class.getClassLoader().getResourceAsStream(path);
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
