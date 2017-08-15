package com.example.alexandrzanko.smallshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.andremion.counterfab.CounterFab;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private ProductAdapter adapter;
    private List<Product> foods;
    private ListView listView;
    public CounterFab fab;
    
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
        fab.setCount(SmallShopApp.getCount());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Пиццы");
        setSupportActionBar(toolbar);

        fab = (CounterFab) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fab.getCount() > 0){
                    Intent intent = new Intent(MainActivity.this, BasketActivity.class);
                    startActivity(intent);
                }else{
                    Snackbar.make(view, "Ваша корзина пуста", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });

        ApiController.getApi().getProducts().enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.code() == 200){
                    foods = response.body();
                    adapter  = new ProductAdapter(MainActivity.this, foods);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }
}
