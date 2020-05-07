package com.base.clazz;

import com.base.clazz.impl.IResult;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @Description: 抓取接口的所有实现类及其继承类(局限性 : 只能获取到接口同级目录下的)
 * @Author:zhao.song
 * @Date:2020/1/11 12:13
 * @Version:1.0
 */
public class AcquireIClazzImplementsV1 {

    /**
     * Description: http://www.cnblogs.com/ClassNotFoundException/p/6831577.html
     * 具体思路:
     * 1.获取当前线程的ClassLoader;
     * 2.通过ClassLoader获取当前工作目录,对目录下的文件进行遍历扫描
     * 3.过滤出以.class为后缀的类文件,并加载类到list中
     * 4.对list中所有类进行校验,判断是否为指定接口的实现类,并排除自身
     * 5.返回所有符合条件的类.
     * 局限性:
     * 1.这个方法没有考虑不同的文件格式.当程序打成jar包,发布运行时,上述的这种遍历file的操作就失效了
     * 2.局限性.只能扫描当前方法的统计目录及其子目录.无法覆盖整个模块
     * 3.遍历文件的逻辑太啰嗦.可以简化
     * 4.通过ClassLoader获取当前工作目录时,使用了"../bin/"这么一个固定的目录名(事实上,
     * 不同的IDE(主要是eclipse和idea)项目的资源目录,在这一点上是不同的
     */
    public static void main(String[] args) {
        ArrayList<Class> list = ClassUtil.getAllClassByInterface(IResult.class);
        System.out.println(list.get(0));
    }

}

@SuppressWarnings("unchecked")
class ClassUtil {


    @SuppressWarnings("unchecked")
    public static ArrayList<Class> getAllClassByInterface(Class clazz) {

        ArrayList<Class> list = new ArrayList<>();
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                ArrayList<Class> allClass = getAllClass(clazz.getPackage().getName());
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                for (int i = 0; i < allClass.size(); i++) {
                    /**
                     * 判断是不是同一个接口
                     */
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    if (clazz.isAssignableFrom(allClass.get(i))) {
                        if (!clazz.equals(allClass.get(i))) {
                            // 自身并不加进去
                            list.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常");
            }
        } else {
            // 如果不是接口，则获取它的所有子类
            try {
                ArrayList<Class> allClass = getAllClass(clazz.getPackage().getName());
                /**
                 * 循环判断路径下的所有类是否继承了指定类 并且排除父类自己
                 */
                for (int i = 0; i < allClass.size(); i++) {
                    if (clazz.isAssignableFrom(allClass.get(i))) {
                        if (!clazz.equals(allClass.get(i))) {
                            // 自身并不加进去
                            list.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常");
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Class> getAllClass(String packageName) {
        ArrayList<Class> clazzList = new ArrayList<>();
        //返回对当前正在执行的线程对象的引用
        //返回该线程的上下文 ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");

        try {
            ArrayList<File> fileList = new ArrayList<>();
            /**
             * 这里面的路径使用的是相对路径 如果大家在测试的时候获取不到,请理清目前工程所在的路径, 使用相对路径更加稳定
             * 另外,路径中切不可包含空格 特殊字符等!
             */
            Enumeration<URL> urlEnumeration = classLoader.getResources(path);
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                File file = new File(url.getFile());
                fileList.add(file);
            }
            for (File file : fileList) {
                clazzList.addAll(findClass(file, packageName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazzList;

    }


    public static ArrayList<Class>  findClass(File file, String packageName) {
        ArrayList<Class> list = new ArrayList<>();
        if (!file.exists()) {
            return list;
        }
        // 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                // assert !file2.getName().contains(".");// 添加断言用于判断
                if (!file2.getName().contains(".")) {
                    ArrayList<Class> arrayList = findClass(file2, packageName + "." + file2.getName());
                    list.addAll(arrayList);
                }
            } else if (file2.getName().endsWith(".class")) {
                try {
                    // 保存的类文件不需要后缀.class
                    list.add(Class.forName(packageName + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
