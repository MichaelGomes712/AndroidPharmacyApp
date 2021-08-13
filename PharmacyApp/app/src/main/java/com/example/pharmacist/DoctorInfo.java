package com.example.pharmacist;

import android.content.ContentValues;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;

public class DoctorInfo extends AppCompatActivity {
    private String Name;
    private String Surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //create JSON Object on start

        try {
            final JSONObject jo = new JSONObject(getIntent().getStringExtra("docInfo"));
//////////////////
            String username = jo.getString("DOCTOR_USERNAME");
            ContentValues params = new ContentValues();
            params.put("Username", username);

            AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDoctorSalt.php", params) {
                @Override
                protected void onPostExecute(String output) {
                    try {
                        JSONArray ja = new JSONArray(output);
                        JSONObject infoDoc = (JSONObject) ja.get(0);

                        Name = infoDoc.getString("DOCTOR_NAME");

                        TextView startInfo = (TextView) findViewById(R.id.textName);
                        String DocSurname = infoDoc.getString("DOCTOR_SURNAME");
                        Surname = DocSurname;
                        startInfo.setText("Dr." + DocSurname);

                        EditText name = (EditText) findViewById(R.id.editName);
                        String DocName = infoDoc.getString("DOCTOR_NAME") + " " + infoDoc.getString("DOCTOR_SURNAME");
                        name.setText(DocName);
                        name.setEnabled(false);

                        EditText num = (EditText) findViewById(R.id.phoneNum);
                        String DocNum = infoDoc.getString("DOCTOR_PHONENUMBER");
                        num.setText(DocNum);

                        EditText address = (EditText) findViewById(R.id.addressInfo);
                        String DocAddress = infoDoc.getString("DOCTOR_ADDRESS");
                        address.setText(DocAddress);

                        EditText email = (EditText) findViewById(R.id.emailShow);
                        String DocEmail = infoDoc.getString("DOCTOR_EMAIL");
                        email.setText(DocEmail);

                        EditText hours = (EditText) findViewById(R.id.hoursInput);
                        String DocHours = infoDoc.getString("DOCTOR_WORKHOURS");
                        hours.setText(DocHours);

                        //everything disabled for now
                        address.setEnabled(false);
                        email.setEnabled(false);
                        hours.setEnabled(false);
                        num.setEnabled(false);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            asyncHTTPPost.execute();

/////////////////

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void saveInfo(View v){

        final   EditText hours = (EditText) findViewById(R.id.hoursInput);
        final   EditText email = (EditText) findViewById(R.id.emailShow);
        final   EditText address = (EditText) findViewById(R.id.addressInfo);
        final   EditText num = (EditText) findViewById(R.id.phoneNum);


        final String upNum = num.getText().toString();
        final String upAddress = address.getText().toString();
        final String upEmail = email.getText().toString();
        final String upHours = hours.getText().toString();

        //Display Updated info
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //check nothing is left empty
        if(upEmail.isEmpty()){
            builder.setTitle("App").setMessage("Please make sure email is not empty").setNeutralButton("Close", null).create().show();
            return;
        }
        else if (upAddress.isEmpty())
        {
            builder.setTitle("App").setMessage("Enter your address").setNeutralButton("Close", null).create().show();
            return;
        }
        else if (upHours.isEmpty())
        {
            builder.setTitle("App").setMessage("Please enter your work hours").setNeutralButton("Close", null).create().show();
            return;
        }
        else if (upNum.isEmpty()) {
            builder.setTitle("App").setMessage("Please enter your number").setNeutralButton("Close", null).create().show();
            return;
        }

        //code to update database
        ContentValues params = new ContentValues();
        params.put("Name",Name);
        params.put("Surname",Surname);
        params.put("Address",upAddress);
        params.put("Email",upEmail);
        params.put("Hours",upHours);
        params.put("Num",upNum);
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/updateDoc.php", params) {
            @Override
            protected void onPostExecute(String output) {
                if(output.equals("Success")){
                    // Successful update , Let Doctor View Info
                    builder.setTitle("App").setMessage("Information has been updated").setNeutralButton("Close", null).create().show();
                    afterUpdate();
                }
                else if(output.equals("Failed")){
                    builder.setTitle("App").setMessage("Information has not been updated").setNeutralButton("Close", null).create().show();
                    return;
                }
            }
        };
        asyncHTTPPost.execute();

    }


    public void afterUpdate(){

        final EditText hours = (EditText) findViewById(R.id.hoursInput);
        final EditText email = (EditText) findViewById(R.id.emailShow);
        final EditText address = (EditText) findViewById(R.id.addressInfo);
        final EditText num = (EditText) findViewById(R.id.phoneNum);
        address.setEnabled(false);
        email.setEnabled(false);
        hours.setEnabled(false);
        num.setEnabled(false);
        findViewById(R.id.buttonSave).setVisibility(View.INVISIBLE);
        findViewById(R.id.updateInfo).setVisibility(View.VISIBLE);

    }


    public void updateInfo(View v){

        EditText hours = (EditText) findViewById(R.id.hoursInput);
        EditText email = (EditText) findViewById(R.id.emailShow);
        EditText address = (EditText) findViewById(R.id.addressInfo);
        EditText num = (EditText) findViewById(R.id.phoneNum);

        // make edits available

        address.setEnabled(true);
        email.setEnabled(true);
        hours.setEnabled(true);
        num.setEnabled(true);
        findViewById(R.id.buttonSave).setVisibility(View.VISIBLE);
        findViewById(R.id.updateInfo).setVisibility(View.INVISIBLE);
    }
}
