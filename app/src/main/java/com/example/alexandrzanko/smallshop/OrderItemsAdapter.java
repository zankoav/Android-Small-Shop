package com.example.alexandrzanko.smallshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by alexandrzanko on 8/15/17.
 */

public class OrderItemsAdapter extends BaseAdapter {


    private List<OrderItem> items;
    private LayoutInflater layoutInflater;
    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    public OrderItemsAdapter(Context context, List<OrderItem> items) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public OrderItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderItemsAdapter.ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.basket_item, null);
        holder = new OrderItemsAdapter.ViewHolder();
        convertView.setTag(holder);
        final OrderItem item = items.get(position);
        holder.discountImg = (ImageView) convertView.findViewById(R.id.item_icon);
        holder.discountName = (TextView) convertView.findViewById(R.id.item_name);
        holder.discountPoints = (TextView) convertView.findViewById(R.id.item_price);
        holder.discountCount = (TextView) convertView.findViewById(R.id.item_count);

        holder.discountDescription = (TextView) convertView.findViewById(R.id.item_description);

        holder.incButton = (Button) convertView.findViewById(R.id.item_add_basket);
        holder.deincButton = (Button) convertView.findViewById(R.id.item_deinc_basket);
        holder.removeButton = (Button) convertView.findViewById(R.id.item_rem_basket);

        holder.discountName.setText(item.getProduct().getName());
        holder.discountCount.setText(item.getCount() + "");
        double price = item.getProduct().getPrise()* item.getCount();
        holder.discountPoints.setText( price + " руб.");
        holder.discountDescription.setText(item.getProduct().getDescription());
        Glide.with(context)
                .load(item.getProduct().getIcon())
                .into(holder.discountImg);


        holder.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallShopApp.addOrderItem(item.getProduct());
                holder.discountCount.setText(item.getCount() + "");
                double price = item.getProduct().getPrise()* item.getCount();
                holder.discountPoints.setText( price + " руб.");
                ((BasketActivity)context).updateTotalPrice();
            }
        });

        holder.deincButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallShopApp.minusOrderItem(item.getProduct());
                holder.discountCount.setText(item.getCount() + "");
                double price = item.getProduct().getPrise()* item.getCount();
                holder.discountPoints.setText( price + " руб.");
                ((BasketActivity)context).updateTotalPrice();
            }
        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallShopApp.removeOrderItem(item.getProduct());
                OrderItemsAdapter.this.notifyDataSetChanged();
                ((BasketActivity)context).updateTotalPrice();
            }
        });


        return convertView;
    }

    static class ViewHolder {
        ImageView discountImg;
        TextView discountName, discountPoints;
        TextView discountDescription, discountCount;
        Button incButton, deincButton, removeButton;
    }
}