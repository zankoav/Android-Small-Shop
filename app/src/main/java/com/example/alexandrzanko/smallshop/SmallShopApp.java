package com.example.alexandrzanko.smallshop;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrzanko on 8/15/17.
 */

public class SmallShopApp extends Application {

    private static List<OrderItem> items;

    public static int getCount() {
        int count = 0;
        for(OrderItem item : items){
            count += item.getCount();
        }
        return count;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        items = new ArrayList<>();
    }

    public static List<OrderItem> getOrderItems(){
        if(items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public static void addOrderItem(Product product){
        boolean exists = false;
        for (OrderItem item : items) {
            if (product.equals(item.getProduct())){
                item.increes();
                exists = true;
                break;
            }
        }
        if(!exists){
            items.add(new OrderItem(product,1));
        }
    }

    public static void minusOrderItem(Product product){
        for (OrderItem item : items) {
            if (product.equals(item.getProduct())){
                item.dincrees();
                break;
            }
        }
    }

    public static void removeOrderItem(Product product){
        for (OrderItem item : items) {
            if (product.equals(item.getProduct())){
                items.remove(item);
                break;
            }
        }
    }

    public static void clearOrder() {
        items = new ArrayList<>();
    }
}
