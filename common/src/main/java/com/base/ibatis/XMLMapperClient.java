package com.base.ibatis;

import com.base.ibatis.builder.mapper.XMLMapperBuilder;
import com.base.ibatis.mapping.BoundSql;
import com.base.ibatis.mapping.MappedStatement;
import com.base.ibatis.parsing.XPathParser;
import com.base.ibatis.session.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description  XMLMapperClient <BR>
 * <p>
 * author: zhao.song
 * date: created in 17:18  2022/9/20
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XMLMapperClient {

    private static boolean isValid;

    static {
        try {
            URL url = new URL("http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(2_000);
            conn.setReadTimeout(2_000);
            conn.getDoOutput();
            isValid = true;
            System.out.println("1111");
        } catch (IOException e) {
            isValid = false;
        }

    }

    public static void main(String[] args) throws FileNotFoundException {

//        parse();
        try (Stream<Path> pathStream = Files.walk(Paths.get("D:\\work\\personal\\comprehensive_enhance\\common\\src\\main\\resources\\mapper\\EmpDao.xml"));) {

            pathStream.filter(path -> path.toFile().isFile())
                    .filter(path -> path.toString().toLowerCase().endsWith(".xml"))
                    .forEach(path -> {
                        try {
                            List<String> lineList = Files.readAllLines(path);
                            lineList = lineList.stream().map(line -> line.replaceAll("http://mybatis.org/dtd/mybatis-3-mapper.dtd", "/work/personal/comprehensive_enhance/common/src/main/resources/dtd/mybatis-3-mapper.dtd"))
                                    .collect(Collectors.toList());
                            Files.write(path, lineList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void parse() throws FileNotFoundException {
        String xmlPath = "D:\\work\\personal\\comprehensive_enhance\\common\\src\\main\\resources\\mapper\\EmpDao.xml";
        InputStream fis = new FileInputStream(xmlPath);
        InputStream is = XPathParser.class.getClassLoader().getResourceAsStream(xmlPath);
        Configuration configuration = new Configuration();
        XMLMapperBuilder xmlMapper = new XMLMapperBuilder(fis, configuration, xmlPath, configuration.getSqlFragments());
        xmlMapper.parse();

        MappedStatement mstmt = configuration.getMappedStatement("findEmpByEmpno");
        final Collection<String> set = configuration.getMappedStatementNames(xmlMapper.getCurrentNameSpace());
        System.out.println(set);
        final BoundSql boundSql = mstmt.getBoundSql(1);
        System.out.println("11111");
    }
}
