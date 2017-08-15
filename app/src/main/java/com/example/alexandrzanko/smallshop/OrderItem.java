package com.example.alexandrzanko.smallshop;

/**
 * Created by alexandrzanko on 8/15/17.
 */

public class OrderItem {

    private Product product;
    private int count;

    public OrderItem(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increes() {
        this.count++;
    }

    public void dincrees() {
        if(this.count > 1){
            this.count--;
        }
    }
}
