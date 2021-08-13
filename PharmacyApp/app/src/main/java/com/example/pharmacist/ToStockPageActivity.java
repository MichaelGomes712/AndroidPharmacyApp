package com.example.pharmacist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ToStockPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_page);

        Button toListStock = findViewById(R.id.stockIndexPageListAllStockItems);
        toListStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToStockPageActivity.this, ListStockActivity.class));
            }
        });

        Button toSearchStock = findViewById(R.id.stockIndexPageSearchForStockItem);
        toSearchStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToStockPageActivity.this, SearchStockActivity.class));
            }
        });

        Button toCreateStock = findViewById(R.id.stockIndexPageCreateNewStockItem);
        toCreateStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToStockPageActivity.this, CreateStockItemActivity.class));
            }
        });

        Button logOut = findViewById(R.id.LogOutBtnStockIndexPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToStockPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
