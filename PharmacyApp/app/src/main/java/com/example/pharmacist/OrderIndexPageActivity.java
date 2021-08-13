package com.example.pharmacist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderIndexPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_page);


        Button listAllOrders = findViewById(R.id.trackingIndexPageToListAllOrdersButton);
        listAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderIndexPageActivity.this, ListAllOrderActivity.class));
            }
        });

        Button searchOrders = findViewById(R.id.trackingIndexPageToSearchOrdersButton);
        searchOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderIndexPageActivity.this, SearchOrdersActivity.class));
            }
        });

        Button logOut = findViewById(R.id.logOutBtnTrackingIndexPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderIndexPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
