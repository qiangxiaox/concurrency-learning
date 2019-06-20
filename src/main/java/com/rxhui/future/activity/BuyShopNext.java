package com.rxhui.future.activity;

import com.rxhui.future.learning.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/5/30 10:46
 */
public class BuyShopNext {
    public static final Executor executor;
    public static  final List<ShopNext> shops;

    static {
        shops = Arrays.asList(new ShopNext("BestPrice"),
                new ShopNext("LetsSaveBig"),
                new ShopNext("MyFavoriteShop"),
                new ShopNext("BuyItAll"),
                new ShopNext("FuckShop"), new ShopNext("LetsSaveBig"),
                new ShopNext("MyFavoriteShop"),
                new ShopNext("BuyItAll"),
                new ShopNext("FuckShop"));
        executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true); //设置守护线程，这种方式不会阻止程序关停
                return t;
            }
        });
    }

    /*public static void main(String[] args) {
        long start = System.nanoTime();

        //System.out.println(findPrices("myIphone"));
        //System.out.println(findPricesParalle("myIphone"));
        //System.out.println(findPricesFuture("myIphone"));
        //System.out.println(findPricesTwoFuture("myIphone"));
        findPricesFutureSteam("myIphone").map(price -> price.thenAccept(System.out::println));
        CompletableFuture[] myIphones = findPricesFutureSteam("myIphone").
                map(price -> price.thenAccept(System.out::println)).
                toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(myIphones).join();
        long eastTime = (System.nanoTime() - start) / 1000000;
        System.out.println("执行花费时间=" + eastTime + "s");
    }*/
    public static void main(String[] args) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesFutureSteam("myPhone27S")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in "
                + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public static List<String> findPricesParalle(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public static List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPrice(product), executor))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> Discount.applyDiscount(quote), executor)))
                        .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    public static List<Double> findPricesTwoFuture(String product) {

        List<CompletableFuture<Double>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.apply(quote.getPrice(), quote.getDiscountCode()), executor))
                        .thenCombine(
                                CompletableFuture.supplyAsync(
                                        () -> Money.getRate(Money.EUR, Money.USD), executor),
                                (price, rate) -> price * rate))
                .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    public static Stream<CompletableFuture<String>> findPricesFutureSteam(String product) {

        Stream<CompletableFuture<String>> futureStream = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)));
        return futureStream;
    }

}
