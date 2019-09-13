package com.example.hw02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TOP_LIST = "toppingProf";

    private ImageView iv_pizza;
    private GridLayout gv_toppings;

    private Button bt_clearpizza;
    private Button bt_checkout;
    private Button bt_addtoppings;

    private CheckBox cb_delivery;
    private ProgressBar pb_toppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Pizza Store");

        final ArrayList<String> mToppingArray = new ArrayList<>();
        final String[] initToppings = getResources().getStringArray(R.array.toppings_array);

        iv_pizza = findViewById(R.id.iv_pizza);
        gv_toppings = findViewById(R.id.gv_toppings);

        bt_clearpizza = findViewById(R.id.bt_clearpizza);
        bt_checkout = findViewById(R.id.bt_checkout);
        bt_addtoppings = findViewById(R.id.bt_addtopping);

        cb_delivery = findViewById(R.id.cb_delivery);
        pb_toppings = findViewById(R.id.pb_toppings);

        pb_toppings.setVisibility(View.VISIBLE);
        pb_toppings.setMax(10);

        /* Generating Alert Box*/
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setTitle(R.string.pick_toppings)
                .setItems(R.array.toppings_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mToppingArray.add(initToppings[which]);
                        pb_toppings.setProgress(mToppingArray.size());
                    }
                });

        final AlertDialog alert11 = builder1.create();
        /* Alert Box Complete */

        /* Generating Grid Images */
        gv_toppings.removeAllViews();

        gv_toppings.setColumnCount(5);
        gv_toppings.setRowCount(2);
        /* Generating Grid Images End */


        bt_addtoppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mToppingArray.size() < 10){
                    alert11.show();
                } else {
                    Toast.makeText(getApplicationContext(),"Only 10 toppings Allowed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_clearpizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToppingArray.clear();
                pb_toppings.setProgress(0);
                cb_delivery.setChecked(false);
            }
        });

        bt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCheckout = new Intent(MainActivity.this, CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("bundleToppings", mToppingArray);
                bundle.putBoolean("delivery", cb_delivery.isChecked());
                toCheckout.putExtra(TOP_LIST, bundle);

                startActivity(toCheckout);
                finish();
            }
        });


    }
}
