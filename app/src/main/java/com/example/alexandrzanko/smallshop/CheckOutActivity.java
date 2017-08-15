package com.example.alexandrzanko.smallshop;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    private Button checkOut;
    private TextView totalPriceView;
    private EditText name, phone, street;
    private  String totalPrice;
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        totalPrice = getIntent().getStringExtra("totalPrice");
        checkOut = (Button) findViewById(R.id.btn_order);
        totalPriceView = (TextView) findViewById(R.id.checkTotalPriceR);
        totalPriceView.setText(totalPrice);

        name = (EditText)findViewById(R.id.order_user_name);
        phone = (EditText)findViewById(R.id.order_user_phone);
        street = (EditText)findViewById(R.id.order_user_street);
        addToolBarToScreen();
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder();
            }
        });
    }

    public boolean namePhoneNumbers(String name){
        Pattern namePattern = Pattern.compile("^\\+375(29|25|44|33)\\d{7}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = namePattern.matcher(name);
        return  matcher.find();
    }

    private void sendOrder() {
        String name = this.name.getText().toString().trim();
        if(name.length() < 2){
            Toast.makeText(this,"Слишком короткое имя", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = this.phone.getText().toString().trim();
        if(!namePhoneNumbers(phone)){
            Toast.makeText(this,"Номер телефона имеет формат +375XXYYYYYYY", Toast.LENGTH_SHORT).show();
            return;
        }
        String street = this.street.getText().toString().trim();
        if(street.length() < 2){
            Toast.makeText(this,"Слишком короткое название улицы", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONArray itemsJson = new JSONArray();
        List<OrderItem> items = SmallShopApp.getOrderItems();
        try{
            for(OrderItem item : items){
                JSONObject itemJson = new JSONObject();
                itemJson.put("id",item.getProduct().getId());
                itemJson.put("count",item.getCount());
                itemsJson.put(itemJson);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.i(TAG, "sendOrder: " + itemsJson.toString());

        ApiController.getApi().checkOut(name,phone,street,itemsJson.toString()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code() == 200){
                    showAlert(response.body());
                }else{
                    Log.i(TAG, "onResponse: code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void showAlert(int orderId) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Внимание!");
        builder.setMessage("Ваш заказ №"+orderId+ ", через несколько минут Вам перезвонит оператор, сумма заказа "+ totalPrice +" рублей");
        builder.setNeutralButton("Закрыть", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                SmallShopApp.clearOrder();
                Intent intent = getParentActivityIntent();
                startActivity(intent);
                finish();
            }
        });
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Intent intent = getParentActivityIntent();
                startActivity(intent);
                finish();

            }
        });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }


    private void addToolBarToScreen() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Оформление заказ");
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
