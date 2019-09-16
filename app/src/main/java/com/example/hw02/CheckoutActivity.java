package com.example.hw02;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity {

    TextView tv_basepricemain;
    TextView tv_toppingprice;
    TextView tv_toppingList;
    TextView tv_deliveryprice;
    TextView tv_TotalPrice;
    Button bt_finish;
    private int totalItems = 0;
    TextView tv_deliverycost;
    TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setTitle("Pizza Store");

        tv_basepricemain = findViewById(R.id.tv_basepricemain);
        tv_toppingList = findViewById(R.id.tv_toppingList);
        tv_toppingprice = findViewById(R.id.tv_toppingprice);
        tv_deliveryprice = findViewById(R.id.tv_deliveryprice);
        tv_TotalPrice = findViewById(R.id.tv_TotalPrice);
        bt_finish = findViewById(R.id.bt_finish);
        tv_deliverycost = findViewById(R.id.tv_deliverycost);
        textView5 = findViewById(R.id.textView5);

        Double basePrice = 6.50;
        Double ppToppings = 1.50;
        Double DeliveryCharge = 4.0;

        tv_basepricemain.setText(MessageFormat.format("{0}$", (basePrice).toString()));

        final Bundle extrasFromMain = Objects.requireNonNull(getIntent().getExtras()).getBundle(MainActivity.TOP_LIST);

        ArrayList<String> mToppingList = Objects.requireNonNull(extrasFromMain).getStringArrayList("bundleToppings");

        mToppingList.remove(null);

        boolean delivery = extrasFromMain.getBoolean("delivery");

        StringBuilder toppings = new StringBuilder();

        for (String s : Objects.requireNonNull(mToppingList)) {
            if(s!=null)
            {
                toppings.append(s);
                toppings.append(", ");
                totalItems++;
            }

        }

        if(toppings.length()!=0)
            tv_toppingList.setText(toppings.toString().substring(0, toppings.length() - 2));

        Double toppingPrice = ppToppings * totalItems;
        tv_toppingprice.setText(MessageFormat.format("{0}$", (toppingPrice).toString()));

        Double TotalPrice = 0.0;

        if(delivery) {
            tv_deliveryprice.setText(MessageFormat.format("{0}$", Double.toString((DeliveryCharge))));
            TotalPrice = basePrice + toppingPrice + DeliveryCharge;
        } else {
            tv_deliveryprice.setVisibility(View.INVISIBLE);
            tv_deliverycost.setVisibility(View.INVISIBLE);
            TotalPrice = basePrice + toppingPrice;
        }

        tv_TotalPrice.setPaintFlags(tv_TotalPrice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_TotalPrice.setText(MessageFormat.format("{0}$", (TotalPrice).toString()));

        textView5.setPaintFlags(textView5.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(CheckoutActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });

    }
}
