package com.rxhui.future.learning;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/5/30 0:03
 */
public class BuyMoreShop {

    public static final Executor executor;
    public static  final List<Shop> shops;

    static {
        shops = Arrays.asList(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("FuckShop"), new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("FuckShop"));
        executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true); //设置守护线程，这种方式不会阻止程序关停
                return t;
            }
        });
    }

    public static void main(String[] args) {
        long start = System.nanoTime();

        //System.out.println(findPrices(shops,"myIphone"));
        //System.out.println(findPricesParrel(shops,"myIphone"));
        System.out.println(findPricesFutureJoin(shops,"myIphone"));
        //System.out.println(findPricesFutureJoinExecutor(shops,"myIphone"));
        long eastTime = (System.nanoTime() - start) / 1000000;
        System.out.println("执行花费时间=" + eastTime + "s");
    }

    public static List<String> findPrices( List<Shop> shops,String product){

       return shops.stream().map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product))).collect(Collectors.toList());
    }

    public static List<String> findPricesParrel(List<Shop> shops,String product){
        return shops.parallelStream().map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product))).collect(Collectors.toList());
    }

    public static List<CompletableFuture<String>> findPricesFuture(List<Shop> shops,String product){
        List<CompletableFuture<String>> collect = shops.stream().map(shop ->
                CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))).
                collect(Collectors.toList());

        return collect;
    }

    public static List<String> findPricesFutureJoin(List<Shop> shops,String product){
        List<CompletableFuture<String>> collect = shops.stream().map(shop ->
                CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))).
                collect(Collectors.toList());

        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static List<String> findPricesFutureJoinExecutor(List<Shop> shops,String product){
        List<CompletableFuture<String>> collect = shops.stream().map(shop ->
                CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),executor)).
                collect(Collectors.toList());

        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}

