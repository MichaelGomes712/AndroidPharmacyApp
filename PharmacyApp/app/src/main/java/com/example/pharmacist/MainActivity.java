package com.example.pharmacist;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {


    static String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    private static String getSecurePassword(String passwordToHash)
    {
        String generatedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //change display to show form to sign up
    public void createAccount(View v)
    {
        final EditText name = findViewById(R.id.edtName);
        final EditText surname = findViewById(R.id.edtSurname);
        final EditText IDnumber = findViewById(R.id.edtIDNum);
        final EditText Email = findViewById(R.id.edtE_mail);
        final EditText username = findViewById(R.id.edtSignUsername);
        final EditText password1 = findViewById(R.id.edtSignPassword);
        final EditText password2 = findViewById(R.id.edtSignPassword2);
        final LinearLayout linLogIn = findViewById(R.id.linLogIn);
        name.setText("");
        surname.setText("");
        IDnumber.setText("");
        Email.setText("");
        username.setText("");
        password1.setText("");
        password2.setText("");
        linLogIn.setVisibility(View.INVISIBLE);
        final LinearLayout linSignUp = findViewById(R.id.linSignUp);
        linSignUp.setVisibility(View.VISIBLE);
    }

    //change display from sign up form to log in form
    //*****************must change activity view************
    public void backToLogIn(View v){
        final EditText username = findViewById(R.id.edtUsername);
        final EditText password = findViewById(R.id.edtPassword);
        username.setText("");
        password.setText("");
        final Spinner doctor = findViewById(R.id.spiDoctor);
        ContentValues params = new ContentValues();
        String[] doctors = new String[]{"No Doctor"};
        final List<String> doctorsList = new ArrayList<>(Arrays.asList(doctors));
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDoctors.php",params) {
            @Override
            protected void onPostExecute(String output) {
                if(!output.equals("Doctors not found")){
                    try {
                        JSONArray ja = new JSONArray(output);
                        for (int i=0; i<ja.length(); i++) {
                            JSONObject jo = (JSONObject) ja.get(i);
                            doctorsList.add("Dr. " + jo.getString("DOCTOR_NAME") + " " + jo.getString("DOCTOR_SURNAME"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncHTTPPost.execute();
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,doctorsList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        doctor.setAdapter(spinnerArrayAdapter);
        final LinearLayout linSignUp = findViewById(R.id.linSignUp);
        linSignUp.setVisibility(View.INVISIBLE);
        final LinearLayout linLogIn = findViewById(R.id.linLogIn);
        linLogIn.setVisibility(View.VISIBLE);
    }

    //user presses button to log in
    public void LogIn(View v){
        final EditText username = findViewById(R.id.edtUsername);
        final EditText password = findViewById(R.id.edtPassword);
        final RadioGroup rgpType = findViewById(R.id.rgpTypeUser);
        int radioId = rgpType.getCheckedRadioButtonId();
        RadioButton selectedRB = findViewById(radioId);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(username.getText().toString().isEmpty()){
            builder.setTitle("App").setMessage("Please enter a username").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(password.getText().toString().isEmpty()){
            builder.setTitle("App").setMessage("Please enter a password").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(radioId == -1){
            builder.setTitle("App").setMessage("Please select an account type").setNeutralButton("Close", null).create().show();
            return;
        }
        else {
            ContentValues params = new ContentValues();
            params.put("Username", username.getText().toString());
            if (selectedRB.getText().toString().equals("CUSTOMER")) {
                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getCustomerSalt.php", params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if (output.equals("Username not found")) {
                            builder.setTitle("App").setMessage("A matching username has not been found. Maybe sign up first").setNeutralButton("Close", null).create().show();
                            return;
                        } else {
                            try {
                                JSONArray ja = new JSONArray(output);
                                JSONObject jo = (JSONObject) ja.get(0);
                                String salt = jo.getString("CUSTOMER_SALT");
                                String comparePass = getSecurePassword(salt + password.getText().toString());
                                if (comparePass.equals(jo.getString("CUSTOMER_PASSWORD"))) {
                                    // builder.setTitle("Welcome " + jo.getString("CUSTOMER_NAME")).setMessage("You have logged on successfully!").setNeutralButton("Close", null).create().show();
                                    //go to customer home page
                                    String ID = jo.getString("CUSTOMER_ID");
                                    String Un = jo.getString("CUSTOMER_USERNAME");
                                    Intent intent = new Intent(getBaseContext(), stock.class);
                                    intent.putExtra("Cust_ID", ID);
                                    intent.putExtra("Cust_UN", Un);
                                    intent.putExtra("Class","Customer");
                                    intent.setClass(MainActivity.this, stock.class);
                                    startActivity(intent);


                                } else {
                                    builder.setTitle("App").setMessage("Invalid username and password entered").setNeutralButton("Close", null).create().show();
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                asyncHTTPPost.execute();
            } else if (selectedRB.getText().toString().equals("ADMIN")){
                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getAdminSalt.php", params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if (output.equals("Username not found")) {
                            builder.setTitle("App").setMessage("A matching username has not been found.").setNeutralButton("Close", null).create().show();
                            return;
                        } else {
                            try {
                                JSONArray ja = new JSONArray(output);
                                JSONObject jo = (JSONObject) ja.get(0);
                                String salt = jo.getString("ADMIN_SALT");
                                String comparePass = getSecurePassword(salt + password.getText().toString());
                                if (comparePass.equals(jo.getString("ADMIN_PASSWORD"))) {
                                    //  builder.setTitle("Welcome " + jo.getString("ADMIN_NAME")).setMessage("You have logged on successfully!").setNeutralButton("Close", null).create().show();
                                    //go to admin home page
                                    Intent intent = new Intent(MainActivity.this, AdminMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    builder.setTitle("App").setMessage("Invalid username and password entered").setNeutralButton("Close", null).create().show();
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                asyncHTTPPost.execute();
            } else if (selectedRB.getText().toString().equals("DOCTOR")) {
                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDoctorSalt.php", params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if (output.equals("Username not found")) {
                            builder.setTitle("App").setMessage("A matching username has not been found.").setNeutralButton("Close", null).create().show();
                            return;
                        } else {
                            try {
                                JSONArray ja = new JSONArray(output);
                                JSONObject jo = (JSONObject) ja.get(0);
                                String salt = jo.getString("DOCTOR_SALT");
                                String comparePass = getSecurePassword(salt + password.getText().toString());
                                if (comparePass.equals(jo.getString("DOCTOR_PASSWORD"))) {
                                    // builder.setTitle("Welcome " + jo.getString("DOCTOR_NAME")).setMessage("You have logged on successfully!").setNeutralButton("Close", null).create().show();
                                    //go to doctor home page
                                    //code go to doctor options
                                    openDoctorOptions(jo); //pass JSONObject

                                } else {
                                    builder.setTitle("App").setMessage("Invalid username and password entered").setNeutralButton("Close", null).create().show();
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                asyncHTTPPost.execute();
            }
        }
    }

    //Customer presses button to make account and sign up
    public void signUp(View v)
    {
        final EditText name = findViewById(R.id.edtName);
        final EditText surname = findViewById(R.id.edtSurname);
        final EditText IDnumber = findViewById(R.id.edtIDNum);
        final EditText Email = findViewById(R.id.edtE_mail);
        final EditText username = findViewById(R.id.edtSignUsername);
        final EditText password1 = findViewById(R.id.edtSignPassword);
        final EditText password2 = findViewById(R.id.edtSignPassword2);
        final Spinner doctor = findViewById(R.id.spiDoctor);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(name.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please input a name").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(surname.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please input a surname").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(IDnumber.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please input an ID number").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(IDnumber.length() != 13){
            builder.setTitle("Apotheware").setMessage("ID number is not 13 digits").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(Email.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please enter your email").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(username.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please enter a username").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(password1.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please enter a password").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(password2.getText().toString().isEmpty()){
            builder.setTitle("Apotheware").setMessage("Please enter the same password in the second box").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(!IDnumber.getText().toString().matches("[0-9]+")){
            builder.setTitle("Apotheware").setMessage("ID number is supposed to include numbers only").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(parseInt(IDnumber.getText().toString().substring(2,4)) > 12){
            builder.setTitle("Apotheware").setMessage("ID number is invalid").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(!name.getText().toString().matches("[a-zA-Z]+")){
            builder.setTitle("Apotheware").setMessage("Name is supposed to contain letters only").setNeutralButton("Close", null).create().show();
            return;
        }
        else if(!surname.getText().toString().matches("[a-zA-Z]+")){
            builder.setTitle("Apotheware").setMessage("Surname is supposed to contain letters only").setNeutralButton("Close", null).create().show();
            return;
        }
        if(password1.getText().toString().equals(password2.getText().toString())) {
            String salt = getAlphaNumericString(8);
            String dbpassword = getSecurePassword(salt + password1.getText().toString());
            String docChange = "";
            ContentValues params1 = new ContentValues();
            if(!doctor.getSelectedItem().toString().equals("No Doctor")){
                docChange = doctor.getSelectedItem().toString().substring(4,doctor.getSelectedItem().toString().length());
            }
            else{
                docChange = doctor.getSelectedItem().toString();
            }
            String[] docNames = docChange.split(" ");
            params1.put("DoctorName",docNames[0]);
            params1.put("DoctorSurname",docNames[1]);
            final ContentValues params = new ContentValues();
            params.put("ID",IDnumber.getText().toString());
            params.put("Name",name.getText().toString());
            params.put("Surname",surname.getText().toString());
            params.put("Email",Email.getText().toString());
            params.put("Username",username.getText().toString());
            params.put("Password",dbpassword);
            AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDocID.php", params1) {
                @Override
                protected void onPostExecute(String output) {
                    if(output.equals("No Doctor")){
                        params.put("DocID",output);
                    }
                    else{
                        try {
                            JSONArray ja = new JSONArray(output);
                            JSONObject jo = (JSONObject) ja.get(0);
                            params.put("DocID",jo.getString("DOCTOR_ID"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            asyncHTTPPost.execute();

            params.put("PasswordSalt",salt);
            AsyncHTTPPost asyncHTTPPost2 = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/signUpCustomer.php", params) {
                @Override
                protected void onPostExecute(String output) {
                    if(output.equals("Success")){
                        builder.setTitle("Apotheware").setMessage("You have successfully signed up!").setNeutralButton("Close", null).create().show();
                        //go to customer homepage
                        String ID = IDnumber.getText().toString();
                        String Un = username.getText().toString();
                        Intent intent = new Intent(getBaseContext(), stock.class);
                        intent.putExtra("Cust_ID", ID);
                        intent.putExtra("Cust_UN", Un);
                        intent.putExtra("Class","Customer");
                        intent.setClass(MainActivity.this, stock.class);
                        startActivity(intent);


                    }
                    else if(output.equals("Taken Username")){
                        builder.setTitle("Apotheware").setMessage("This username has been taken already. Please try a different one").setNeutralButton("Close", null).create().show();
                        return;
                    }
                    else if(output.equals("Taken ID")){
                        builder.setTitle("Apotheware").setMessage("A customer has signed up using this ID number already. Sorry").setNeutralButton("Close", null).create().show();
                        return;
                    }
                    else if(output.equals("Failed")){
                        builder.setTitle("Apotheware").setMessage("You have not signed up successfully. Please try again.").setNeutralButton("Close", null).create().show();
                        return;
                    }
                }
            };
            asyncHTTPPost2.execute();
        }
        else{
            builder.setTitle("Apotheware").setMessage("The two password entered do not match. Please try again.").setNeutralButton("Close", null).create().show();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner doctor = findViewById(R.id.spiDoctor);
        ContentValues params = new ContentValues();
        String[] doctors = new String[]{"No Doctor"};
        final List<String> doctorsList = new ArrayList<>(Arrays.asList(doctors));
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDoctors.php",params) {
            @Override
            protected void onPostExecute(String output) {
                if(!output.equals("Doctors not found")){
                    try {
                        JSONArray ja = new JSONArray(output);
                        for (int i=0; i<ja.length(); i++) {
                            JSONObject jo = (JSONObject) ja.get(i);
                            doctorsList.add("Dr. " + jo.getString("DOCTOR_NAME") + " " + jo.getString("DOCTOR_SURNAME"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncHTTPPost.execute();
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,doctorsList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        doctor.setAdapter(spinnerArrayAdapter);
    }

    public void openDoctorOptions(JSONObject objJo) throws InterruptedException {
        Intent intent = new Intent(this,DoctorOptions.class);
        //add Json Object to activity
        intent.putExtra("docInfo" , objJo.toString());
        startActivity(intent);
    }


}
