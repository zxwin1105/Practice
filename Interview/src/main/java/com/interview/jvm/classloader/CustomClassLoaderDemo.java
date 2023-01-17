package com.interview.jvm.classloader;

import sun.applet.AppletClassLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义类加载器
 * @author zhaixinwei
 * @date 2023/1/17
 */
public class CustomClassLoaderDemo {

    public static void main(String[] args) {

    }


    /**
     * 打破双亲委派模型的自定义类加载器
     * 需要重写loadClass(),因为jdk的双亲委派模型基于这个方法实现
     */
    class CustomBrokenClassLoader extends ClassLoader{

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            return super.loadClass(name, resolve);
        }
    }


    /**
     * 不打破双亲委派模型的类加载器
     * 只需要重写findClass()
     */
    class CustomClassLoader extends ClassLoader{

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String classFile = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream in = getClass().getResourceAsStream(classFile);
            if(null == in){
                return super.loadClass(name);
            }
            byte[] bytes = new byte[0];
            try {
                bytes = new byte[in.available()];
                in.read(bytes);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return super.findClass(name);
        }
    }

}
