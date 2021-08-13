package com.example.pharmacist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_activity);


        Button logOut = findViewById(R.id.logOutBtnAdminMainPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Button toDoctorIndex = findViewById(R.id.toDoctorIndexPageButton);
        toDoctorIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, ToDoctorIndexActivity.class));
            }
        });

        Button toOrders = findViewById(R.id.toOrderPageIndexButton);
        toOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, OrderIndexPageActivity.class));
            }
        });

        Button toStock = findViewById(R.id.toStockIndexPageButton);
        toStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, ToStockPageActivity.class));
            }
        });

    }
}
