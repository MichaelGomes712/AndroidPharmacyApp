package com.example.pharmacist;

//Page to view/edit/search patients
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPatients extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<String> custArray;
    ListView lv;
    private Button buttonVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        try {
            final JSONObject infoDoc = new JSONObject(getIntent().getStringExtra("docInfo"));
            String docID = infoDoc.getString("DOCTOR_ID");

            //verify button
            buttonVerify = (Button) findViewById(R.id.buttonVerify);
            buttonVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openVerify(infoDoc);
                }
            });


            //get list of all patients of Doctor
            ContentValues params = new ContentValues();
            params.put("ID", docID);

            AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/docCust.php", params) {
                @Override
                protected void onPostExecute(String output) {
                    try {
                        final JSONArray ja = new JSONArray(output);
                        custArray = new ArrayList<>();
                        for (int i =0;i < ja.length();i++)
                        {
                            JSONObject jo = ja.getJSONObject(i);
                            final   String name = jo.getString("CUSTOMER_NAME");
                            final   String surname = jo.getString("CUSTOMER_SURNAME");
                            custArray.add(name + " " + surname);
                        }

                        lv = (ListView) findViewById(R.id.listCustomers);
                        adapter = new ArrayAdapter<String>(ViewPatients.this,android.R.layout.simple_list_item_1
                                , custArray);
                        lv.setAdapter(adapter);

                        // On Click Of list view item(not working)

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ///testing
                                showPatientInfo(ja,position);
                            }
                        });


                        EditText filter = (EditText) findViewById(R.id.textName);
                        filter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                (ViewPatients.this).adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

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

    public void showPatientInfo(JSONArray ja, final int position){

        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewPatients.this);

        try {

            int Pos = 0;
            String selectedFromList =(lv.getItemAtPosition(position).toString());

            for (int i = 0; i<custArray.size();i++)
            {
                if (selectedFromList.equals(custArray.get(i)))
                {
                    Pos = i;
                }
            }


            JSONObject curr = (JSONObject) ja.get(Pos);
            final   String Currname = curr.getString("CUSTOMER_NAME");
            final   String Currsurname = curr.getString("CUSTOMER_SURNAME");

            ContentValues currParams = new ContentValues();
            currParams.put("Name", Currname);
            currParams.put("Surname", Currsurname);

// Get customer info

            AsyncHTTPPost asyncHTTPPost =  new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getShowCustomer.php", currParams) {
                @Override
                protected void onPostExecute(String output) {
                    try {
                        JSONArray custInfo = new JSONArray(output);
                        JSONObject custDInfo = (JSONObject) custInfo.get(0);
                        String custID = custDInfo.getString("CUSTOMER_ID");
                        String custName = custDInfo.getString("CUSTOMER_NAME");
                        String custSurn = custDInfo.getString("CUSTOMER_SURNAME");
                        String custEmail = custDInfo.getString("CUSTOMER_EMAIL");
                        String custVerify = custDInfo.getString("CUSTOMER_VERIFYDOCTOR"); // ISSUE HERE

                        String message = "";
                        if (custVerify.equals("false"))
                        {
                            message = "Email: " + custEmail + "\n" +
                                    "ID number: " + custID + "\n" +
                                    "Verified: False ";

                        }else
                        {
                            message = "Email: " + custEmail + "\n" +
                                    "ID number: " + custID + "\n" +
                                    "Verified: True";
                        }
                        //Display Customer info for doctor
                        builder.setTitle(custName + " " + custSurn).setMessage(message).setNeutralButton("Close", null).create().show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            asyncHTTPPost.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void openVerify(JSONObject objDoc){
        Intent intent = new Intent(this, Verify.class);
        intent.putExtra("doc" , objDoc.toString());
        startActivity(intent);
    }
}

