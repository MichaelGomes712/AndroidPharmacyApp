package com.example.pharmacist;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateDoctorActivity extends ToDoctorIndexActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_page);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Button logOut = findViewById(R.id.logOutBtnCreateDoctorPage);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDoctorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Button create = findViewById(R.id.createDoctorConfirmButon);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_id = findViewById(R.id.createDoctorEditTextEnterId);
                EditText edit_name = findViewById(R.id.createDoctorEditTextEnterName);
                EditText edit_surname = findViewById(R.id.createDoctorEditTextEnterSurname);
                EditText edit_phonenumber = findViewById(R.id.createDoctorEditTextEnterPhonenumber);
                EditText edit_email = findViewById(R.id.createDoctorEditTextEnterEmail);
                EditText edit_address = findViewById(R.id.createDoctorEditTextEnterAddress);
                EditText edit_workhours = findViewById(R.id.createDoctorEditTextEnterWorkhours);
                EditText edit_username = findViewById(R.id.createDoctorEditTextEnterUsername);
                EditText edit_password = findViewById(R.id.createDoctorEditTextEnterPassword);
                EditText edit_passwordConfirm = findViewById(R.id.createDoctorEditTextEnterPasswordConfirm);

                String id = edit_id.getText().toString();
                String name = edit_name.getText().toString();
                String surname = edit_surname.getText().toString();
                String phonenumber = edit_phonenumber.getText().toString();
                String email = edit_email.getText().toString();
                String address = edit_address.getText().toString();
                String workhours = edit_workhours.getText().toString();
                String username = edit_username.getText().toString();
                String password1 = edit_password.getText().toString();
                String passwordConfirm = edit_passwordConfirm.getText().toString();


                if (id.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor ID cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (name.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor's first name cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (surname.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor's surname  cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (phonenumber.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor's phone number cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (email.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor's email cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (address.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor's address cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (workhours.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor's work hours cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (username.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor username cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (password1.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Doctor password cannot be empty").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (passwordConfirm.isEmpty()) {
                    builder.setTitle("Create Doctor").setMessage("Please retype your password").setNeutralButton(
                            "Close",null).create().show();
                }
                else if (!(password1.equals(passwordConfirm)) ) {
                    builder.setTitle("Create Doctor").setMessage("The passwords do not match").setNeutralButton(
                            "Close",null).create().show();
                }


                //else if ( !(id.isEmpty()) && !(username.isEmpty()) && (password1.equals(passwordConfirm)) ) {
                else {

                    String salt = getAlphaNumericString(8);
                    String dbPassword =  getSecurePassword(salt + password1);

                    ContentValues params = new ContentValues();
                    params.put("Id", id);
                    params.put("Name", name);
                    params.put("Surname", surname);
                    params.put("Phonenumber", phonenumber);
                    params.put("Email", email);
                    params.put("Address", address);
                    params.put("Workhours", workhours);
                    params.put("Username", username);
                    params.put("Password", dbPassword);
                    params.put("docSalt", salt);

                    AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                            "http://lamp.ms.wits.ac.za/~s1644868/createDoctor.php",params) {
                        @Override
                        protected void onPostExecute(String output) {
                            if (output.equals("Success")) {
                                builder.setTitle("Create Doctor").setMessage("New doctor successfully created").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Taken Username")) {
                                builder.setTitle("Create Doctor").setMessage("That username is already taken").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Taken ID")) {
                                builder.setTitle("Create Doctor").setMessage("That ID is already taken").setNeutralButton(
                                        "Close",null).create().show();
                            }
                            else if (output.equals("Failed")) {
                                builder.setTitle("Create Doctor").setMessage("You have an error in the provided information. Please try again").setNeutralButton(
                                        "Close",null).create().show();
                            }
                        }
                    };
                    asyncHTTPPost.execute();

                }

            }
        });


    }

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

}
