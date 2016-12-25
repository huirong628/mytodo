package com.android.huirongzhang.todo;

import org.junit.Test;

/**
 * Created by HuirongZhang
 * on 25/12/2016.
 * <p>
 * Java Passes By value or By reference
 * <p>
 * Definitions:
 * Pass by value: make a copy in memory of the actual parameter's value that is passed in.
 * Pass by reference: pass a copy of the address of the actual parameter.
 */

public class JavaPassTest {
    @Test
    public void main() throws Exception {
        Apple apple = new Apple();//apple is the original reference
        System.out.println(apple.color);//red
        System.out.println(apple);//com.android.huirongzhang.todo.JavaPassTest$Apple@78e03bb5
        change(apple);
        System.out.println(apple.color);//green

        int num = 10;
        System.out.println(num);//10
        change(num);
        System.out.println(num);//10

    }

    /**
     * 值传递和引用传递解释：
     * <p>
     * change(apple);
     * 值传递：传递的是变量apple中的值，
     * <p>
     * 引用传递：传递的是变量apple的地址，
     * <p>
     * 实参apple中存储的是对象new Apple()的地址，也就是它指向对象 new Apple();
     * <p>
     * 形参a得到的是apple中的值，而不是apple的地址，这样a 也指向了对象new Apple(),因此可以改变color的值。
     *
     * @param a
     */
    void change(Apple a) {//a is the copied reference, 形参和实参指向同一个对象：new Apple() ,因此可以改变值。
        System.out.println(a);//com.android.huirongzhang.todo.JavaPassTest$Apple@78e03bb5
        a.color = "green";
    }

    /**
     * 基本类型作为参数传递时，传递的是这个值的拷贝。无论你怎么改变这个拷贝，原值是不会改变的。
     * <p>
     * 内存模型分析：
     * 当执行了int num = 10;这句代码后，程序在栈内存中开辟了一块地址为AD8500的内存，里边放的值是10;
     * <p>
     * 执行到change(num);方法时，程序在栈内存中又开辟了一块地址为AD8600的内存，
     * 将num的值10传递进来，此时这块内存里边放的值10，param = 20;后，AD8600中的值变成了20。
     * <p>
     * 地址AD8600中用于存放param的值，和存放num的内存没有任何关系，
     * 无论你怎么改变param的值，实际改变的是地址为AD8600的内存中的值，
     * 而AD8500中的值并未改变，所以num的值也就没有改变。
     *
     * @param param
     */
    void change(int param) {
        param = 20;
    }

    class Apple {
        String color = "red";
    }

    /**
     * 无论是基本类型作为参数传递，还是对象作为参数传递，实际上传递的都是值，只是值的的形式不用而已。
     *
     * 第一个示例中用基本类型作为参数传递时，将栈内存中的值10传递到了change(int param)方法中。
     *
     * 第二个示例中用对象作为参数传递时，将栈内存中的值BE2500传递到了change(Apple a)方法中。
     *
     * 当用对象作为参数传递时，真正的值是放在堆内存中的，传递的是栈内存中的值，而栈内存中存放的是堆内存的地址，所以传递的就是堆内存的地址。
     *
     * 在Java中，String是一个引用类型，但是在作为参数传递的时候表现出来的却是基本类型的特性，即在方法中改变了String类型的变量的值后，不会影响方法外的String变量的值 。
     *
     */
}
