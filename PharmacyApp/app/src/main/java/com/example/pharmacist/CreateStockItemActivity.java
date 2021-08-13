package com.example.pharmacist;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateStockItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_stock_item);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Button logOut = findViewById(R.id.logOutBtnCreateStockItemPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateStockItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button createStockItem = findViewById(R.id.createNewStockItemEnterCreateItemButton);
        createStockItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText type_id = findViewById(R.id.createNewStockItemEnterId);
                EditText type_name = findViewById(R.id.createNewStockItemEnterName);
                EditText type_schedule = findViewById(R.id.createNewStockItemEnterSchedule);
                EditText type_costprice = findViewById(R.id.createNewStockItemEnterCostprice);
                EditText type_markup = findViewById(R.id.createNewStockItemEnterMarkup);
                EditText type_discount = findViewById(R.id.createNewStockItemEnterDiscount);
                EditText type_available = findViewById(R.id.createNewStockItemEnterAvailable);
                EditText type_sellingprice = findViewById(R.id.createNewStockItemEnterSellingprice);
                EditText type_manufacturer = findViewById(R.id.createNewStockItemEnterManufacturer);

                String id = type_id.getText().toString();
                String name = type_name.getText().toString();
                String schedule = type_schedule.getText().toString();
                String costprice = type_costprice.getText().toString();
                String markup = type_markup.getText().toString();
                String discount = type_discount.getText().toString();
                String available = type_available.getText().toString();
                String sellingprice = type_sellingprice.getText().toString();
                String manufacturer = type_manufacturer.getText().toString();

                if(id.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item ID cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (name.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item name must be known").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (schedule.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item schedule must be established").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (costprice.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item cost price must be established").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (markup.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item markup must be established").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (discount.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item discount must be established").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (available.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Available quantity must be established").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (sellingprice.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item selling price must be established").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (manufacturer.isEmpty()) {
                    builder.setTitle("New Stock Item").setMessage("Item manufacturer must be known").setNeutralButton(
                            "Close",null).create().show();
                }

                else {
                    ContentValues params = new ContentValues();
                    params.put("Id", id);
                    params.put("Name", name);
                    params.put("Schedule", schedule);
                    params.put("Costprice", costprice);
                    params.put("Markup", markup);
                    params.put("Discount", discount);
                    params.put("Available", available);
                    params.put("Sellingprice", sellingprice);
                    params.put("Manufacturer", manufacturer);

                    AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                            "http://lamp.ms.wits.ac.za/~s1644868/createStockItem.php",params) {
                        @Override
                        protected void onPostExecute(String output) {

                            if (output.equals("You didn't send the required values")) {
                                builder.setTitle("New Stock Item").setMessage("Incorrect item parameters given").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Failed")) {
                                builder.setTitle("New Stock Item").setMessage("Item creation failed").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Taken ID")) {
                                builder.setTitle("New Stock Item").setMessage("Item ID already in use").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Taken Name")) {
                                builder.setTitle("New Stock Item").setMessage("Item name already in use").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Success")) {
                                builder.setTitle("New Stock Item").setMessage("New item successfully created").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else {
                                builder.setTitle("New Stock Item").setMessage("not Working").setNeutralButton(
                                        "Close",null).create().show();
                            }
                        }
                    };
                    asyncHTTPPost.execute();
                }
            }
        });

    }
}
