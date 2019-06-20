package com.rxhui.future.learning;

import com.rxhui.future.activity.Discount;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/5/29 22:15
 */
public class Shop {

    private String name;

    public Shop(String name){
        this.name = name;
    }
    /**
     * 同步方法
     * @param product
     * @return
     */
    public double getPrice(String product){
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            future.complete(price);
        }).start();
        return future;
    }

    public Future<Double> getPriceAsyncReturnException(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try{
                double price = calculatePrice(product);
                future.complete(price);
            }catch (Exception e){
                future.completeExceptionally(e);
            }

        }).start();
        return future;
    }
    public String getName(){
        return this.name;
    }


    public Future<Double> getPriceAsyncIn(String product){
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }


    private double calculatePrice(String product){
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public String getPriceA(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                new Random().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }
    /**
     * 模拟调用远程接口的时间
     */
    public static void delay(){
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
