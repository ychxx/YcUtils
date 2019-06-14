package com.yc.ycutils.test;

import com.yc.ycutils.TestClass;

import java.lang.reflect.Field;

/**
 *  记录利用反射获取和修改private的变量
 */
public class TestMain {
    public static void main(String[] args) {
        testClass();
    }

    public static void testClass() {
        TestClass testClass = new TestClass();
        System.out.println(System.currentTimeMillis());
        try {
            Field field = TestClass.class.getDeclaredField("mValue");
            //设置允许利用反射访问私有变量
            field.setAccessible(true);
            //获取
            int value = (int) field.get(testClass);
            System.out.println(value);
            //修改
            field.set(testClass,123456);
            int newValue = (int) field.get(testClass);
            System.out.println(newValue);
            System.out.println(System.currentTimeMillis());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
