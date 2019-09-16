package com.example.hw02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

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


    /**
     * Author: Pranav Kamble
     * */
    //private TableRow tr_tableRow1;
    private LinearLayout lo_topping1;
    private LinearLayout lo_topping2;
    private short layoutElementCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Pizza Store");

        final ArrayList<String> mToppingArray = new ArrayList<>();
        final String[] initToppings = getResources().getStringArray(R.array.toppings_array);

        iv_pizza = findViewById(R.id.iv_pizza);

        /* Generating Grid Images */
        //gv_toppings = findViewById(R.id.gv_toppings);
        /* Generating Grid Images End */

        bt_clearpizza = findViewById(R.id.bt_clearpizza);
        bt_checkout = findViewById(R.id.bt_checkout);
        bt_addtoppings = findViewById(R.id.bt_addtopping);

        cb_delivery = findViewById(R.id.cb_delivery);
        pb_toppings = findViewById(R.id.pb_toppings);

        pb_toppings.setVisibility(View.VISIBLE);
        pb_toppings.setMax(10);


        /**
         * */
        //tr_tableRow1 = findViewById(R.id.tr_toppingRow1);
        lo_topping1 = findViewById(R.id.layout_toppings_1);
        lo_topping2 = findViewById(R.id.layout_toppings_2);

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        //final ImageView iv_addedTopping = new ImageView(this);

        /* Generating Alert Box*/
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setTitle(R.string.pick_toppings)
                .setItems(R.array.toppings_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Images = new ArrayList<>();
                        Integer topping = -1;
                        mToppingArray.add(initToppings[which]);

                        if(initToppings[which].equals("Bacon")){
                            Images.add(R.drawable.bacon);
                            topping = R.drawable.bacon;
                        } else if (initToppings[which].equals("Cheese")) {
                            Images.add(R.drawable.cheese);
                            topping = R.drawable.cheese;
                        } else if (initToppings[which].equals("Garlic")) {
                            Images.add(R.drawable.garlic);
                            topping = R.drawable.garlic;
                        } else if (initToppings[which].equals("Green Pepper")) {
                            Images.add(R.drawable.green_pepper);
                            topping = R.drawable.green_pepper;
                        } else if (initToppings[which].equals("Mashroom")) {
                            Images.add(R.drawable.mashroom);
                            topping = R.drawable.mashroom;
                        } else if (initToppings[which].equals("Olive")) {
                            Images.add(R.drawable.olive);
                            topping = R.drawable.olive;
                        } else if (initToppings[which].equals("Onion")) {
                            Images.add(R.drawable.onion);
                            topping = R.drawable.onion;
                        } else if (initToppings[which].equals("Red Pepper")) {
                            Images.add(R.drawable.red_pepper);
                            topping = R.drawable.red_pepper;
                        }

                        /**
                         * Author : Pranav Kamble
                         * Add images to layout
                         * */
                        ImageView iv_topping = new ImageView(MainActivity.this);
                        iv_topping.setId(layoutElementCount);
                        iv_topping.setImageResource(topping);

                            if(lo_topping1.getChildCount() < 5 ){
                                lo_topping1.addView(iv_topping,layoutParams);
                                iv_topping.getLayoutParams().height = 150;
                                iv_topping.getLayoutParams().width = 150;
                                layoutElementCount++;
                            }
                            else{
                                lo_topping2.addView(iv_topping,layoutParams);
                                iv_topping.getLayoutParams().height = 150;
                                iv_topping.getLayoutParams().width = 150;
                                layoutElementCount++;
                            }

                            iv_topping.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String idToRemove = Integer.toString(view.getId());
                                    Log.d("ID", "onClick: "+view.getId());
                                    if(view.getParent() == lo_topping1){
                                        lo_topping1.removeView(view);
                                        mToppingArray.set(view.getId(),null);
                                        layoutElementCount--;
                                    }

                                    else
                                    {
                                        lo_topping2.removeView(view);
                                        mToppingArray.set(view.getId(),null);
                                        layoutElementCount--;
                                    }
                                    //int index = 0;
                                    while(lo_topping1.getChildCount()<5 && layoutElementCount>4)
                                    {
                                        if(lo_topping2.getChildCount()>0)
                                        {
                                            View viewToMoveUp = lo_topping2.getChildAt(0);
                                            lo_topping2.removeView(viewToMoveUp);
                                            lo_topping1.addView(viewToMoveUp);
                                        }


                                    }
                                    Toast.makeText(getApplicationContext(), "Topping Removed", Toast.LENGTH_SHORT).show();
                                    pb_toppings.setProgress(layoutElementCount);
                                }

                            });
                        pb_toppings.setProgress(layoutElementCount);
                    }
                });

        final AlertDialog alert11 = builder1.create();
        /* Alert Box Complete */

        bt_addtoppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(layoutElementCount < 10){
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
                lo_topping1.removeAllViews();
                lo_topping2.removeAllViews();
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
