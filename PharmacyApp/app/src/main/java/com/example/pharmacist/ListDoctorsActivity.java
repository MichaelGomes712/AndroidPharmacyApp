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

public class ListDoctorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_doctors);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Button listDoctors = findViewById(R.id.listAllDoctorsButton);
        listDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues params = new ContentValues();


                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                        "http://lamp.ms.wits.ac.za/~s1644868/getDoctors.php",params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if (output.equals("Doctors not found")) {
                            builder.setTitle("All Doctors").setMessage("No Doctors found").setNeutralButton(
                                    "Close",null).create().show();
                        }
                        else {
                            displayDoctors(output);
                        }
                    }
                };
                asyncHTTPPost.execute();
            }
        });

        Button logOut = findViewById(R.id.logOutBtnListAllDoctorsPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDoctorsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void displayDoctors(String output) {
        LinearLayout leftMain = findViewById(R.id.displayAllDoctorsLayoutLeft);
        LinearLayout rightMain = findViewById(R.id.displayAllDoctorsLayoutRight);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

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

                TextView surname = new TextView(this);
                surname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                surname.setLayoutParams(textViewSizeParams);

                TextView phonenumber = new TextView(this);
                phonenumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                phonenumber.setLayoutParams(textViewSizeParams);

                TextView email = new TextView(this);
                email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                email.setLayoutParams(textViewSizeParams);

                TextView address = new TextView(this);
                address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                address.setLayoutParams(textViewSizeParams);

                TextView workhours = new TextView(this);
                workhours.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                workhours.setLayoutParams(textViewSizeParams);


                id.setText(jo.getString("DOCTOR_ID"));
                name.setText(jo.getString("DOCTOR_NAME"));
                surname.setText(jo.getString("DOCTOR_SURNAME"));
                phonenumber.setText(jo.getString("DOCTOR_PHONENUMBER"));
                email.setText(jo.getString("DOCTOR_EMAIL"));
                address.setText(jo.getString("DOCTOR_ADDRESS"));
                workhours.setText(jo.getString("DOCTOR_WORKHOURS"));

                right.addView(id);
                right.addView(name);
                right.addView(surname);
                right.addView(phonenumber);
                right.addView(email);
                right.addView(address);
                right.addView(workhours);

                TextView disp_id = new TextView(this);
                disp_id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_id.setLayoutParams(textViewSizeParams);

                TextView disp_name = new TextView(this);
                disp_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_name.setLayoutParams(textViewSizeParams);

                TextView disp_surname = new TextView(this);
                disp_surname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_surname.setLayoutParams(textViewSizeParams);

                TextView disp_phonenumber = new TextView(this);
                disp_phonenumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_phonenumber.setLayoutParams(textViewSizeParams);

                TextView disp_email = new TextView(this);
                disp_email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_email.setLayoutParams(textViewSizeParams);

                TextView disp_address = new TextView(this);
                disp_address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_address.setLayoutParams(textViewSizeParams);

                TextView disp_workhours = new TextView(this);
                disp_workhours.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                disp_workhours.setLayoutParams(textViewSizeParams);

                disp_id.setText("Doctor ID:");
                disp_name.setText("First name:");
                disp_surname.setText("Surname:");
                disp_phonenumber.setText("Phone number:");
                disp_email.setText("Email:");
                disp_address.setText("Address:");
                disp_workhours.setText("Work hours:");

                left.addView(disp_id);
                left.addView(disp_name);
                left.addView(disp_surname);
                left.addView(disp_phonenumber);
                left.addView(disp_email);
                left.addView(disp_address);
                left.addView(disp_workhours);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }



}
