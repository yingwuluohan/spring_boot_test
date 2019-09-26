package com.unisound.iot.controller.jdk.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Created by yingwuluohan on 2019/9/24.
 */
public class Shop {


    /**
     * 商店名称
     */
    private String name;


    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * (阻塞式)通过名称查询价格
     *
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }


    /**
     * 计算价格
     *
     * @param product
     * @return
     */
    private double calculatePrice(String product) {
        delay();
        //数字*字符=数字
        return 10 * product.charAt(0);

    }


    /**
     * 模拟耗时操作,阻塞1秒
     */
    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * (非阻塞式)异步获取价格
     *
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            //需要长时间计算的任务结束并返回结果时,设置Future返回值
            future.complete(price);
        }).start();

        //无需等待还没结束的计算,直接返回Future对象
        return future;
    }


    public Future<Double> getPriceAsync2(String product) {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> calculatePrice(product));

        //无需等待还没结束的计算,直接返回Future对象
        return future;
    }
    public static void main(String[] args) {
        Shop shop = new Shop("Best Shop");
        long start = System.nanoTime();
        double price = shop.getPrice("mac book pro");
        System.out.printf(shop.getName()+" Price is %.2f%n",price);
        long invocationTime = (System.nanoTime()-start)/1_000_000;
        System.out.println("同步方法调用花费时间:--- "+invocationTime+" --- msecs");


        //...其他操作
        System.out.println( "--!@@@@@@@@@@@@@@@@@@@@@");

        Future<Double> price1 = shop.getPriceAsync2( "completableFutrue");
        try {
            System.out.println( "--!@@@@@@@@@@@@@@@@@@@@@"+ price1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Long retrievalTime = (System.nanoTime()-start)/1_000_000;
        System.out.println( price1 + "result:" + retrievalTime);
    }





}



















