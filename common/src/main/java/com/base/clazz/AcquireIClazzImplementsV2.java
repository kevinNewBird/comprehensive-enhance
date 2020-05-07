package com.base.clazz;


import com.base.clazz.impl.IResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description: 获取全部子类或接口的全部实现
 * @Author:zhao.song
 * @Date:2020/1/11 12:13
 * @Version:1.0
 */
public class AcquireIClazzImplementsV2 {

    /**
     * Description: http://blog.csdn.net/littleschemer/article/details/47378455
     * 具体思路:
     *   1.考虑了在运行环境中,使用JarFile工具类进行单独处理
     * 局限性:
     *   1.需要手动指定要扫描的Jar文件或目录,没有通过ClassLoader自动获取当前运行的上下文 . 此外,
     *     ClassLoader.getResource获得的资源目录是个URL对象,如何转换琛JarFile对象花费了我不少时间求索:
     *       JarURLConnection jarUrlConn = (JarURLConnection) url.openConnection();
     *       JarFile jarFile = jarUrlConn.getJarFile();
     */
    public static void main(String[] args) {
        List<String> fileList = new ArrayList<String>();

        File baseFile = new File(AcquireIClazzImplementsV2.class.getResource("/")+File.separator+"src"+File.separator+"bean");
        if(baseFile.exists()){//开发环境，读取源文件
            getSubFileNameList(baseFile,fileList);
        }else{//jar包环境
            fileList = getClassNameFrom("server.jar");
        }
        System.err.println("Animal类的所有子类有");
        for(String name:fileList) {
            if (isChildClass(name, IResult.class))
                System.err.println(name);
        }
    }

    /**
     *  递归查找指定目录下的类文件的全路径
     * @param baseFile 查找文件的入口
     * @param fileList 保存已经查找到的文件集合
     */
    public static  void getSubFileNameList(File baseFile, List<String> fileList){
        if(baseFile.isDirectory()){
            File[] files = baseFile.listFiles();
            for(File tmpFile : files){
                getSubFileNameList(tmpFile,fileList);
            }
        }
        String path = baseFile.getPath();
        if(path.endsWith(".java")){
            String name1 = path.substring(path.indexOf("src")+4, path.length());
            String name2 = name1.replaceAll("\\\\", ".");
            String name3 = name2.substring(0, name2.lastIndexOf(".java"));
            fileList.add(name3);
        }
    }

    /**
     *  从jar包读取所有的class文件名
     */
    private static List<String> getClassNameFrom(String jarName){
        List<String> fileList = new ArrayList<String>();
        try {
            JarFile jarFile = new JarFile(new File(jarName));
            Enumeration<JarEntry> en = jarFile.entries(); // 枚举获得JAR文件内的实体,即相对路径
            while (en.hasMoreElements()) {
                String name1 =  en.nextElement().getName();
                if(!name1.endsWith(".class")){//不是class文件
                    continue;
                }
                String name2 = name1.substring(0, name1.lastIndexOf(".class"));
                String name3 = name2.replaceAll("/", ".");
                fileList.add(name3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }


    /**
     *  判断一个类是否继承某个父类或实现某个接口
     */
    public static boolean isChildClass(String className, Class parentClazz){
        if(className == null) return false;

        Class clazz = null;
        try {
            clazz = Class.forName(className);
            if(Modifier.isAbstract(clazz.getModifiers())){//抽象类忽略
                return false;
            }
            if(Modifier.isInterface(clazz.getModifiers())){//接口忽略
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return parentClazz.isAssignableFrom(clazz);

    }


}

