package com.rxhui.future.learning;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/5/29 22:35
 */
public class BuyShop {

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> priceAsync = shop.getPriceAsync("my favorite book");
        long eastTime = (System.nanoTime() - start) / 1000000;
        System.out.println("执行花费时间=" + eastTime + "s");
        //执行更多的任务
        doSomthingElse();

        try {
            Double aDouble = priceAsync.get();
            System.out.println("获取的价格="+aDouble);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long endCostTime = (System.nanoTime() - start) / 1000000;
        System.out.println("最终执行花费时间=" + endCostTime + "s");
    }

    public static void doSomthingElse(){

    }
}
