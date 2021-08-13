package com.example.pharmacist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);


        ImageButton pro = (ImageButton) findViewById(R.id.ProfileBtn);
        final String ID = "9904307515897";
        pro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), profile.class);
                intent.putExtra("Cust_ID", ID);
                startActivity(intent);
            }

        });

        ImageButton stock = (ImageButton) findViewById(R.id.stockBtn);

        stock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), stock.class);
                intent.putExtra("Cust_ID", ID);
                startActivity(intent);
            }
        });
    }
}