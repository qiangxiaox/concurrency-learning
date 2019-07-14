package com.rxhui.concurrent.part1.demo4;

import java.text.SimpleDateFormat;

public class Tool {
    public static void main(String[] args) throws Exception{
        System.out.println(SafeDateFormat.get());
        System.out.println(Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(SafeDateFormat.get());
            }
        }).start();

       /* java.text.SimpleDateFormat@4f76f1a0
                main
        Thread-0
        java.text.SimpleDateFormat@4f76f1a0

        这里的SimpleDateFormat内存地址一样，其实不一样
        因为该类覆写了Objects的toString和hashCode方法，hashCode使用的是pattern.hashCode()，pattern就是我们设的"yyyy-MM-dd HH:mm:ss",所以内存地址会一样
        */

    }

    static class SafeDateFormat{
        static final ThreadLocal<SimpleDateFormat> sdf =
                ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        static SimpleDateFormat get(){
            return sdf.get();
        }
    }
}