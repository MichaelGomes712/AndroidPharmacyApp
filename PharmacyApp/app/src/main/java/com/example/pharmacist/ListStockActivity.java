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

public class ListStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_stock);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Button disp = findViewById(R.id.allStockDisplayButton);
        disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues params = new ContentValues();

                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                        "http://lamp.ms.wits.ac.za/~s1644868/getStock.php",params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if (output.equals("Items not found")) {
                            builder.setTitle("Stock").setMessage("Not found").setNeutralButton(
                                    "Close",null).create().show();
                        }
                        else {
                            displayStock(output);
                        }
                    }
                };
                asyncHTTPPost.execute();
            }
        });

        Button logOut = findViewById(R.id.logOutBtnListAllStockPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListStockActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void displayStock(String output) {
        LinearLayout leftMain = findViewById(R.id.displayStockLayoutLeft);
        LinearLayout rightMain = findViewById(R.id.displayStockLayoutRight);

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

                TextView id = new TextView(this);
                id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                id.setLayoutParams(textViewSizeParams);

                TextView name = new TextView(this);
                name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                name.setLayoutParams(textViewSizeParams);

                TextView schedule = new TextView(this);
                schedule.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                schedule.setLayoutParams(textViewSizeParams);

                TextView costprice = new TextView(this);
                costprice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                costprice.setLayoutParams(textViewSizeParams);

                TextView markup = new TextView(this);
                markup.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                markup.setLayoutParams(textViewSizeParams);

                TextView discount = new TextView(this);
                discount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                discount.setLayoutParams(textViewSizeParams);

                TextView available = new TextView(this);
                available.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                available.setLayoutParams(textViewSizeParams);

                TextView sellingprice = new TextView(this);
                sellingprice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                sellingprice.setLayoutParams(textViewSizeParams);

                TextView man = new TextView(this);
                man.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                man.setLayoutParams(textViewSizeParams);

                id.setText(jo.getString("PHARMACEUTICAL_ID"));
                name.setText(jo.getString("PHARMACEUTICAL_NAME"));
                schedule.setText(jo.getString("PHARMACEUTICAL_SCHEDULE"));
                costprice.setText(jo.getString("PHARMACEUTICAL_COSTPRICE"));
                markup.setText(jo.getString("PHARMACEUTICAL_MARKUP"));
                discount.setText(jo.getString("PHARMACEUTICAL_DISCOUNT"));
                available.setText(jo.getString("PHARMACEUTICAL_AVAILABLEQUANTITY"));
                sellingprice.setText(jo.getString("PHARMACEUTICAL_SELLINGPRICE"));
                man.setText(jo.getString("PHARMACEUTICAL_MANUFACTURER"));

                right.addView(id);
                right.addView(name);
                right.addView(schedule);
                right.addView(costprice);
                right.addView(markup);
                right.addView(discount);
                right.addView(available);
                right.addView(sellingprice);
                right.addView(man);

                TextView disp_id = new TextView(this);
                disp_id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_id.setLayoutParams(textViewSizeParams);

                TextView disp_name = new TextView(this);
                disp_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_name.setLayoutParams(textViewSizeParams);

                TextView disp_schedule = new TextView(this);
                disp_schedule.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_schedule.setLayoutParams(textViewSizeParams);

                TextView disp_costprice = new TextView(this);
                disp_costprice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_costprice.setLayoutParams(textViewSizeParams);

                TextView disp_markup = new TextView(this);
                disp_markup.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_markup.setLayoutParams(textViewSizeParams);

                TextView disp_discount = new TextView(this);
                disp_discount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_discount.setLayoutParams(textViewSizeParams);

                TextView disp_available = new TextView(this);
                disp_available.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_available.setLayoutParams(textViewSizeParams);

                TextView disp_sellingprice = new TextView(this);
                disp_sellingprice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_sellingprice.setLayoutParams(textViewSizeParams);

                TextView disp_man = new TextView(this);
                disp_man.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_man.setLayoutParams(textViewSizeParams);

                disp_id.setText("Pharmaceutical ID:");
                disp_name.setText("Name:");
                disp_schedule.setText("Schedule:");
                disp_costprice.setText("Cost price:");
                disp_markup.setText("Markup:");
                disp_discount.setText("Discount:");
                disp_available.setText("Available quantity:");
                disp_sellingprice.setText("Selling price:");
                disp_man.setText("Manufacturer:");

                left.addView(disp_id);
                left.addView(disp_name);
                left.addView(disp_schedule);
                left.addView(disp_costprice);
                left.addView(disp_markup);
                left.addView(disp_discount);
                left.addView(disp_available);
                left.addView(disp_sellingprice);
                left.addView(disp_man);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
