package com.example.hw02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> Images = new ArrayList<>();

    public static final String TOP_LIST = "toppingProf";

    private ImageView iv_pizza;

    private GridView gv_toppings;

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

        /* Generating Grid Images */
        gv_toppings = findViewById(R.id.gv_toppings);
        /* Generating Grid Images End */

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

                        if(initToppings[which].equals("Bacon")){
                            Images.add(R.drawable.bacon);
                        } else if (initToppings[which].equals("Cheese")) {
                            Images.add(R.drawable.cheese);
                        } else if (initToppings[which].equals("Garlic")) {
                            Images.add(R.drawable.garlic);
                        } else if (initToppings[which].equals("Green Pepper")) {
                            Images.add(R.drawable.green_pepper);
                        } else if (initToppings[which].equals("Mashroom")) {
                            Images.add(R.drawable.mashroom);
                        } else if (initToppings[which].equals("Olive")) {
                            Images.add(R.drawable.olive);
                        } else if (initToppings[which].equals("Onion")) {
                            Images.add(R.drawable.onion);
                        } else if (initToppings[which].equals("Red Pepper")) {
                            Images.add(R.drawable.red_pepper);
                        }

                        // Adapter Init
                        AdapterCustom adapterCustom = new AdapterCustom(getApplicationContext(), Images);
                        gv_toppings.setAdapter(adapterCustom);

                        gv_toppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Images.remove(position);
                                mToppingArray.remove(position);
                                // Adapter Init
                                AdapterCustom adapterCustom = new AdapterCustom(getApplicationContext(), Images);
                                gv_toppings.setAdapter(adapterCustom);
                                Toast.makeText(getApplicationContext(), "Topping Removed", Toast.LENGTH_SHORT).show();
                                pb_toppings.setProgress(mToppingArray.size());
                            }
                        });

                        pb_toppings.setProgress(mToppingArray.size());
                    }
                });

        final AlertDialog alert11 = builder1.create();
        /* Alert Box Complete */

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
