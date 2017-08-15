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
 * Created by alexandrzanko on 8/12/17.
 */

public class ProductAdapter extends BaseAdapter {


    private List<Product> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public final String TAG = this.getClass().getSimpleName();


    public ProductAdapter(Context context, List<Product> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Product getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductAdapter.ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.product_item_layout, null);
        holder = new ProductAdapter.ViewHolder();
        convertView.setTag(holder);
        final Product product = listData.get(position);
        holder.discountImg           = (ImageView) convertView.findViewById(R.id.product_icon);
        holder.discountName          = (TextView) convertView.findViewById(R.id.product_name);
        holder.discountPoints   = (TextView) convertView.findViewById(R.id.product_price);
        holder.discountDescription   = (TextView) convertView.findViewById(R.id.product_description);

        holder.getButton    = (Button) convertView.findViewById(R.id.product_add_basket);
        holder.discountName.setText(product.getName());
        holder.discountPoints.setText(product.getPrise()+ " руб.");
        holder.discountDescription.setText(product.getDescription());
        Glide.with(context)
                .load(product.getIcon())
                .into(holder.discountImg);


        holder.getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallShopApp.addOrderItem(product);
                ((MainActivity)context).fab.increase();
            }
        });



        return convertView;
    }

    static class ViewHolder {
        ImageView discountImg;
        TextView discountName, discountPoints;
        TextView  discountDescription;
        Button getButton;
    }
}

