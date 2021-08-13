package com.example.pharmacist;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListAllOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_order);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Button disp = findViewById(R.id.allStockDisplayButton);
        disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues params = new ContentValues();

                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                        "http://lamp.ms.wits.ac.za/~s1644868/getOrders.php",params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if (output.equals("Orders not found")) {
                            builder.setTitle("Orders").setMessage("No orders found").setNeutralButton(
                                    "Close",null).create().show();
                        }
                        else {
                            displayOrders(output);
                        }
                    }
                };
                asyncHTTPPost.execute();
            }
        });

        Button logOut = findViewById(R.id.logOutBtnListAllOrdersPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAllOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void displayOrders(String output) {
        LinearLayout leftMain = findViewById(R.id.displayOrdersLayoutLeft);
        LinearLayout rightMain = findViewById(R.id.displayOrdersLayoutRight);

        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        try {
            JSONArray ja = new JSONArray(output);
            for (int i = 0; i < ja.length(); ++i) {
                JSONObject jo = (JSONObject)ja.get(i);

                LinearLayout.LayoutParams textViewSizeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 135);

                LinearLayout left = new LinearLayout(this);
                left.setOrientation(LinearLayout.VERTICAL);
                leftMain.addView(left);
                LinearLayout right = new LinearLayout(this);
                right.setOrientation(LinearLayout.VERTICAL);
                right.setLayoutParams(layoutParams);
                rightMain.addView(right);

                TextView number = new TextView(this);
                number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                number.setLayoutParams(textViewSizeParams);

                TextView customer = new TextView(this);
                customer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                customer.setLayoutParams(textViewSizeParams);

                TextView meds = new TextView(this);
                meds.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                meds.setLayoutParams(textViewSizeParams);

                TextView price = new TextView(this);
                price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                price.setLayoutParams(textViewSizeParams);

                TextView datemade = new TextView(this);
                datemade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                datemade.setLayoutParams(textViewSizeParams);

                TextView datedone = new TextView(this);
                datedone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                datedone.setLayoutParams(textViewSizeParams);



                number.setText(jo.getString("ORDER_NUMBER"));
                customer.setText(jo.getString("ORDER_CUSTOMER"));
                meds.setText(jo.getString("ORDER_PHARMACEUTICALS"));
                price.setText(jo.getString("ORDER_PRICE"));
                datemade.setText(jo.getString("ORDER_DATEORDERED"));
                datedone.setText(jo.getString("ORDER_DATECOMPLETED"));

                right.addView(number);
                right.addView(customer);
                right.addView(meds);
                right.addView(price);
                right.addView(datemade);
                right.addView(datedone);

                TextView disp_number = new TextView(this);
                disp_number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_number.setLayoutParams(textViewSizeParams);

                TextView disp_customer = new TextView(this);
                disp_customer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_customer.setLayoutParams(textViewSizeParams);

                TextView disp_meds = new TextView(this);
                disp_meds.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_meds.setLayoutParams(textViewSizeParams);

                TextView disp_price = new TextView(this);
                disp_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_price.setLayoutParams(textViewSizeParams);

                TextView disp_datemade = new TextView(this);
                disp_datemade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_datemade.setLayoutParams(textViewSizeParams);

                TextView disp_datedone = new TextView(this);
                disp_datedone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_datedone.setLayoutParams(textViewSizeParams);


                disp_number.setText("Order number:");
                disp_customer.setText("Customer:");
                disp_meds.setText("Pharmaceuticals:");
                disp_price.setText("Price:");
                disp_datemade.setText("Date order made:");
                disp_datedone.setText("Date order completed:");

                left.addView(disp_number);
                left.addView(disp_customer);
                left.addView(disp_meds);
                left.addView(disp_price);
                left.addView(disp_datemade);
                left.addView(disp_datedone);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



}
