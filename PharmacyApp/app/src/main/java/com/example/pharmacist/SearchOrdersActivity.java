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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_profile);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Button logOut = findViewById(R.id.logOutBtnSearchOrdersPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrdersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button searchAllOrders = findViewById(R.id.searchOrderInTrackingButton);
        searchAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText u = findViewById(R.id.searchOrderInTrackingEditText);
                String input = u.getText().toString();

                if (input.isEmpty()) {
                    builder.setTitle("Search Orders").setMessage("Search parameters cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else {
                    ContentValues params = new ContentValues();
                    params.put("OrderSearch", input);

                    LinearLayout leftMain = findViewById(R.id.linearLayoutOrderPageSearchDisplayInfoLeft);
                    LinearLayout rightMain = findViewById(R.id.linearLayoutOrderPageSearchDisplayInfoRight);
                    leftMain.removeAllViews();
                    rightMain.removeAllViews();

                    AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                            "http://lamp.ms.wits.ac.za/~s1644868/searchOrder.php",params) {
                        @Override
                        protected void onPostExecute(String output) {
                            if (output.equals("Order not found")) {
                                builder.setTitle("Search Orders").setMessage("No order was found").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else {
                                searchOrders(output);
                                return;
                            }
                        }
                    };
                    asyncHTTPPost.execute();
                }
            }
        });
    }

    public void searchOrders(String output) {

        LinearLayout leftMain = findViewById(R.id.linearLayoutOrderPageSearchDisplayInfoLeft);
        LinearLayout rightMain = findViewById(R.id.linearLayoutOrderPageSearchDisplayInfoRight);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        try {
            JSONArray ja = new JSONArray(output);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject)ja.get(i);

                LinearLayout.LayoutParams textViewSizeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);

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
                id.setText(jo.getString("ORDER_NUMBER"));

                TextView customer = new TextView(this);
                customer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                customer.setLayoutParams(textViewSizeParams);
                customer.setText(jo.getString("ORDER_CUSTOMER"));

                TextView med = new TextView(this);
                med.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                med.setLayoutParams(textViewSizeParams);
                med.setText(jo.getString("ORDER_PHARMACEUTICALS"));

                TextView price = new TextView(this);
                price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                price.setLayoutParams(textViewSizeParams);
                price.setText(jo.getString("ORDER_PRICE"));

                TextView datemade = new TextView(this);
                datemade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                datemade.setLayoutParams(textViewSizeParams);
                datemade.setText(jo.getString("ORDER_DATEORDERED"));

                TextView datedone = new TextView(this);
                datedone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                datedone.setLayoutParams(textViewSizeParams);
                datedone.setText(jo.getString("ORDER_DATECOMPLETED"));

                right.addView(id);
                right.addView(customer);
                right.addView(med);
                right.addView(price);
                right.addView(datemade);
                right.addView(datedone);

                TextView disp_id = new TextView(this);
                disp_id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_id.setLayoutParams(textViewSizeParams);
                disp_id.setText("Order number:");

                TextView disp_customer = new TextView(this);
                disp_customer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_customer.setLayoutParams(textViewSizeParams);
                disp_customer.setText("Customer:");

                TextView disp_med = new TextView(this);
                disp_med.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_med.setLayoutParams(textViewSizeParams);
                disp_med.setText("Pharmaceuticals:");

                TextView disp_price = new TextView(this);
                disp_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_price.setLayoutParams(textViewSizeParams);
                disp_price.setText("Price:");

                TextView disp_datemade = new TextView(this);
                disp_datemade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_datemade.setLayoutParams(textViewSizeParams);
                disp_datemade.setText("Date ordered:");

                TextView disp_datedone = new TextView(this);
                disp_datedone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_datedone.setLayoutParams(textViewSizeParams);
                disp_datedone.setText("Date completed:");

                left.addView(disp_id);
                left.addView(disp_customer);
                left.addView(disp_med);
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
