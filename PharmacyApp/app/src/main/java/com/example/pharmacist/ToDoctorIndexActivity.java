package com.example.pharmacist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToDoctorIndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_index_page);

        Button logOut = findViewById(R.id.logOutBtnDoctorIndexPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoctorIndexActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button allDocs = findViewById(R.id.listAllDoctorsPageButton);
        Button create = findViewById(R.id.createNewDoctorButton);
        Button search = findViewById(R.id.searchForDoctorButton);

        allDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoctorIndexActivity.this, ListDoctorsActivity.class));
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoctorIndexActivity.this, CreateDoctorActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoctorIndexActivity.this, SearchDoctorActivity.class));
            }
        });


    }


}
