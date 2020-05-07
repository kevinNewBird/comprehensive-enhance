package com.base.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description: 获取全部子类或接口的全部实现(优化)
 * @Author:zhao.song
 * @Date:2020/1/11 12:13
 * @Version:1.0
 */
public class IDiscoveryFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ClassUtil.class);

    public static List<Class> getAllClassByInterface(Class clazz) {
        List<Class> allClass = new ArrayList<>();
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                allClass = getAllImplClass(clazz.getPackage().getName(), clazz);
            } catch (Exception e) {
                LOG.error("出现异常{}", e.getMessage());
                throw new RuntimeException("出现异常" + e.getMessage());
            }
        }
        LOG.info("class list size :" + allClass.size());
        return allClass;
    }


    /**
     * 从一个指定路径下查找所有的类
     *
     * @param packagename
     */
    private static List<Class> getAllImplClass(String packagename, Class originClazz) {


        LOG.info("packageName to search：" + packagename);
        List<Class> classList = getClassInstance(packagename, originClazz);

        LOG.info("find list size :" + classList.size());
        return classList;
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static List<Class> getClassInstance(String packageName, Class originClazz) {

        List<Class> fileClazzs = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            LOG.debug("file type : " + type);
            if (type.equals("file")) {
                String fileSearchPath = url.getPath();
                LOG.debug("fileSearchPath: " + fileSearchPath);
                fileSearchPath = fileSearchPath.substring(0, fileSearchPath.indexOf("/classes")) + "/classes";
                LOG.debug("fileSearchPath: " + fileSearchPath);
                long start = System.currentTimeMillis();
                fileClazzs = getClassInstanceByFile(fileSearchPath, originClazz);
                long end = System.currentTimeMillis();
                LOG.debug("recursive load class time : " + (end - start) + "ms");
            } else if (type.equals("jar")) {
                try {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    fileClazzs = getClassInstanceByJar(jarFile, packagePath, originClazz);
                } catch (IOException e) {
                    throw new RuntimeException("open Package URL failed：" + e.getMessage());
                }

            } else {
                throw new RuntimeException("file system not support! cannot load MsgProcessor！");
            }
        }
        return fileClazzs;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath 文件路径
     * @return 类的完整名称
     */
    private static List<Class> getClassInstanceByFile(String filePath, Class originClazz) {
        List<Class> myClassList = new ArrayList<Class>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassList.addAll(getClassInstanceByFile(childFile.getPath(), originClazz));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    Class<?> aClass = null;
                    long start = System.currentTimeMillis();
                    try {
                        aClass = Class.forName(childFilePath);
                    } catch (ClassNotFoundException e) {
                        LOG.error("load class from name failed:" + childFilePath + e.getMessage());
                        throw new RuntimeException("load class from name failed:" + childFilePath + e.getMessage());
                    }
                    long end = System.currentTimeMillis();
                    LOG.debug("=== create " + aClass + " class spend time : " + (end - start) + " ms");
                    if (null != aClass && !aClass.equals(originClazz) && originClazz.isAssignableFrom(aClass)) {
                        myClassList.add(aClass);
                    }

                }
            }
        }

        return myClassList;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @return 类的完整名称
     */
    private static List<Class> getClassInstanceByJar(JarFile jarFile, String packagePath, Class originClazz) {
        List<Class> myClassList = new ArrayList<Class>();
        try {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                //LOG.info("entrys jarfile:"+entryName);
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    Class<?> aClass = null;
                    long start = System.currentTimeMillis();
                    try {
                        aClass = Class.forName(entryName);
                    } catch (ClassNotFoundException e) {
                        LOG.error("load class from name failed:" + entryName + e.getMessage());
                        throw new RuntimeException("load class from name failed:" + entryName + e.getMessage());
                    }
                    long end = System.currentTimeMillis();
                    LOG.debug("=== create " + aClass + " class spend time : " + (end - start) + " ms");
                    if (null != aClass && !aClass.equals(originClazz) && originClazz.isAssignableFrom(aClass)) {
                        myClassList.add(aClass);
                    }
                    //LOG.debug("Find Class :"+entryName);
                }
            }
        } catch (Exception e) {
            LOG.error("发生异常:" + e.getMessage());
            throw new RuntimeException("发生异常:" + e.getMessage());
        }
        return myClassList;
    }


}

