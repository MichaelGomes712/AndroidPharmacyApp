package com.example.pharmacist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DoctorOptions extends AppCompatActivity {
    private Button buttonInfo;
    private Button buttonPatients;
    private Button buttonLogout;
    private Button buttonPrescriptions;

    //doctor select button page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        try {
            final JSONObject infoDoc = new JSONObject(getIntent().getStringExtra("docInfo"));

            TextView startInfo = (TextView) findViewById(R.id.textWelcome);
            String DocName = infoDoc.getString("DOCTOR_SURNAME");
            startInfo.setText("Welcome, Dr." + DocName);


            //Open prescription add button
            buttonPrescriptions = (Button) findViewById(R.id.buttonPrescription);
            buttonPrescriptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCart(infoDoc);
                }
            });

            //Open Doctor info Button
            buttonInfo = (Button) findViewById(R.id.openInfo);
            buttonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDoctorInfo(infoDoc);
                }
            });

            //View Patients Button
            buttonPatients = (Button) findViewById(R.id.buttonPatients);
            buttonPatients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPatientsInfo(infoDoc);
                }
            });

            //Logout Button
            buttonLogout = (Button) findViewById(R.id.buttonLogOut);
            buttonLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logOut();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openDoctorInfo(JSONObject objDoc) {
        Intent intent = new Intent(this, DoctorInfo.class);
        intent.putExtra("docInfo", objDoc.toString());
        startActivity(intent);
    }

    public void openPatientsInfo(JSONObject objDoc) {
        Intent intent = new Intent(this, ViewPatients.class);
        intent.putExtra("docInfo", objDoc.toString());
        startActivity(intent);
    }

    public void logOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Open Cart
    public void openCart(JSONObject objDoc) {
        Intent intent = new Intent(this, stock.class);
        intent.putExtra("docInfo", objDoc.toString());
        intent.putExtra("Class","Doctor");
        intent.setClass(DoctorOptions.this, stock.class);
        startActivity(intent);
    }
}