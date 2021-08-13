package com.example.pharmacist;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Verify extends AppCompatActivity {

    ListView lv;
    ArrayAdapter adapter;
    ArrayList<String> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        try {
            final JSONObject docInfo = new JSONObject(getIntent().getStringExtra("doc"));
            String docID = docInfo.getString("DOCTOR_ID");


            ContentValues params = new ContentValues();
            params.put("ID", docID);

            AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/verifyPatients.php", params) {
                @Override
                protected void onPostExecute(String output) {
                    //display none
                    try {
                        final JSONArray ja = new JSONArray(output);
                        patients = new ArrayList<String>();


                        if (ja.length()==0)
                        {
//                                final AlertDialog.Builder builder = new AlertDialog.Builder(Verify.this);
//                                builder.setTitle("App").setMessage("No patients to verify!").setNeutralButton("Close", null).create().show();
//                                returnPatients(docInfo);
                            patients.add("No patients to verify!");

                        }



                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            final String name = jo.getString("CUSTOMER_NAME");
                            final String surname = jo.getString("CUSTOMER_SURNAME");
                            patients.add(name + " " + surname);
                        }

                        lv = (ListView) findViewById(R.id.listVerify);
                        adapter = new ArrayAdapter<String>(Verify.this, android.R.layout.simple_list_item_1
                                , patients);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ///verify selected patient
                                try {
                                    doVerify(ja, position);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }
            };
            asyncHTTPPost.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void returnPatients(JSONObject ja)
    {
        Intent intent = new Intent(this,ViewPatients.class);
        intent.putExtra("docInfo" , ja.toString());
        startActivity(intent);
    }

    public void doVerify(JSONArray ja, final int position) throws JSONException {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Verify.this);

        JSONObject curr = (JSONObject) ja.get(position);
        final   String Currname = curr.getString("CUSTOMER_NAME");
        final   String Currsurname = curr.getString("CUSTOMER_SURNAME");

        ContentValues currParams = new ContentValues();
        currParams.put("Name", Currname);
        currParams.put("Surname", Currsurname);

        //update database

        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/doPatientVerify.php", currParams) {
            @Override
            protected void onPostExecute(String output) {
                builder.setTitle("Apotheware").setMessage("Patient has been verified").setNeutralButton("Close", null).create().show();
                // Successful update , Let Doctor View Info
                try {
                    JSONObject docInfo = new JSONObject(getIntent().getStringExtra("doc"));
                    postVerify(docInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        asyncHTTPPost.execute();



    }

    public void postVerify(JSONObject ja){
        try {
            final JSONObject docInfo = ja;
            String docID = docInfo.getString("DOCTOR_ID");


            ContentValues params = new ContentValues();
            params.put("ID", docID);

            AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/verifyPatients.php", params) {
                @Override
                protected void onPostExecute(String output) {
                    try {
                        final JSONArray ja = new JSONArray(output);

                        patients = new ArrayList<>();

                        if (ja.length()==0)
                        {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Verify.this);
//                                builder.setTitle("App").setMessage("No patients to verify!").setNeutralButton("Close", null).create().show();
//                                returnPatients(docInfo);
                            patients.add("No patients to verify!");
                        }

                        for (int i =0;i < ja.length();i++)
                        {
                            JSONObject jo = ja.getJSONObject(i);
                            final   String name = jo.getString("CUSTOMER_NAME");
                            final   String surname = jo.getString("CUSTOMER_SURNAME");
                            patients.add(name + " " + surname);
                        }

                        lv = (ListView) findViewById(R.id.listVerify);
                        adapter = new ArrayAdapter<String>(Verify.this,android.R.layout.simple_list_item_1
                                , patients);
                        lv.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            asyncHTTPPost.execute();





        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}