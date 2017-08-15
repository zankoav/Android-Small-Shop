package com.example.alexandrzanko.smallshop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BasketActivity extends AppCompatActivity {

    private OrderItemsAdapter adapter;
    private List<OrderItem> items;
    private ListView listView;
    private Button chekOut;
    private TextView totalPriceView;


    @Override
    protected void onResume() {
        super.onResume();
        items = SmallShopApp.getOrderItems();
        adapter  = new OrderItemsAdapter(BasketActivity.this, items);
        listView.setAdapter(adapter);
        updateTotalPrice();
    }

    public void updateTotalPrice(){
        double totalPrice = 0;
        for(OrderItem item: items){
            totalPrice += item.getCount()*item.getProduct().getPrise();
        }
        totalPriceView.setText(totalPrice + "");
    }

    private String getTotalPrice(){
        double totalPrice = 0;
        for(OrderItem item: items){
            totalPrice += item.getCount()*item.getProduct().getPrise();
        }
        return totalPrice + "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        listView = (ListView) findViewById(R.id.listView_order_items);
        chekOut = (Button) findViewById(R.id.btn_order);
        totalPriceView = (TextView) findViewById(R.id.checkTotalPriceR);
        addToolBarToScreen();
        chekOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasketActivity.this, CheckOutActivity.class);
                intent.putExtra("totalPrice",getTotalPrice());
                startActivity(intent);
            }
        });
    }

    private void addToolBarToScreen() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Корзина");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
