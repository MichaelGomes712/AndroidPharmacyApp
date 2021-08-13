package com.example.pharmacist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pharmacist.Utils.Common;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout Drawer;
    NotificationBadge badge;
    boolean spinner;

    static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    private static String getSecurePassword(String passwordToHash) {
        String generatedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //------------------------------Nav drawer & toolbar----------------------------------------
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Drawer = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        View header = navView.getHeaderView(0);
        String Username = getIntent().getStringExtra("Cust_UN");
        TextView txtUName = header.findViewById(R.id.TxtUN);
        txtUName.setText(Username);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, Drawer, myToolbar, R.string.nav_draw_open, R.string.nav_draw_close);
        Drawer.addDrawerListener(toggle);
        toggle.syncState();

        //=========================================================================================


        //--------------------GUI Setup----------------------------------------------------------
        final EditText idEdit = (EditText) findViewById(R.id.edtID);
        idEdit.setEnabled(false);

        final EditText fnEdit = (EditText) findViewById(R.id.edtFName);
        fnEdit.setEnabled(false);

        final EditText snEdit = (EditText) findViewById(R.id.edtSName);
        snEdit.setEnabled(false);

        final EditText docEdit = (EditText) findViewById(R.id.edtDoc);
        docEdit.setEnabled(false);

        final EditText emEdit = (EditText) findViewById(R.id.edtMail);
        emEdit.setEnabled(false);

        final EditText unEdit = (EditText) findViewById(R.id.edtUserName);
        unEdit.setEnabled(false);

        final Button cancel = (Button) findViewById(R.id.btnCancel);
        cancel.setVisibility(View.GONE);

        final EditText pass = (EditText) findViewById(R.id.edtPass);
        pass.setVisibility(View.GONE);

        final EditText confirm = (EditText) findViewById(R.id.edtConfirm);
        confirm.setVisibility(View.GONE);

        final TextView Info = (TextView) findViewById(R.id.textView);

        final Button submit = (Button) findViewById(R.id.btnSubmit);
        submit.setVisibility(View.GONE);

        final Button History = (Button) findViewById(R.id.btnHisory);
        final Spinner doctor = findViewById(R.id.spinDoctor);
        doctor.setEnabled(false);
        //=========================================================================================

        //---------------------------------DB Stuff-----------------------------------------------
        final String ID = getIntent().getStringExtra("Cust_ID");
        ContentValues params = new ContentValues();
        params.put("id", ID);
        @SuppressLint("StaticFieldLeak") final AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                "http://lamp.ms.wits.ac.za/~s1644868/getCustomerData.php", params) {

            @Override
            protected void onPostExecute(String output) {

                showData(output);
            }

        };
        asyncHTTPPost.execute();

        //=========================================================================================


        //----------------------------------Button OnClicks----------------------------------------
        final AlertDialog.Builder message = new AlertDialog.Builder(this);
        final AlertDialog.Builder Newmessage = new AlertDialog.Builder(this);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String Fname;
                final String SName;
                final String Email;
                final String Password;
                final String ConfirmPass;
                final String[] Doctor = new String[1];
                Doctor[0] ="";
                final String Uname;
                final String[] DoctorID = new String[1];
                DoctorID[0] ="";
                final String[] Salt = new String[1];
                Fname = fnEdit.getText().toString();
                SName = snEdit.getText().toString();
                Email = emEdit.getText().toString();
                Uname = unEdit.getText().toString();
                String docChange = "";
                if (spinner == false) {
                    Doctor[0] = docEdit.getText().toString();
                } else {
                    if (!(doctor.getSelectedItem().toString().equals("No Doctor"))) {
                        docChange = doctor.getSelectedItem().toString().substring(4, doctor.getSelectedItem().toString().length());
                    } else {
                        docChange = doctor.getSelectedItem().toString();
                    }
                    String[] docNames = docChange.split(" ");
                    ContentValues params1 = new ContentValues();
                    params1.put("DoctorName", docNames[0]);
                    params1.put("DoctorSurname", docNames[1]);
                    AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDocID.php", params1) {
                        @Override
                        protected void onPostExecute(String output) {
                            DoctorID[0] = getDocId(output);
                        }
                    };
                    asyncHTTPPost.execute();
                }

                Password = pass.getText().toString();
                ConfirmPass = confirm.getText().toString();


                if (!Password.equals(ConfirmPass)) {
                    message.setTitle("Incorrect password");
                    message.setMessage("passwords do not match");
                    message.setCancelable(true);
                    message.setPositiveButton("close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    message.show();


                } else {

                    message.setTitle("Success");
                    message.setMessage("are you sure you want to update info");
                    message.setCancelable(true);
                    message.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues params = new ContentValues();
                            String salt = getAlphaNumericString(8);
                            if (Password.equals("") && ConfirmPass.equals("")) {
                                params.put("Pass", "");
                                params.put("Salt", "");
                            } else {
                                String HashedPass = getSecurePassword(salt + Password);
                                params.put("Pass", HashedPass);
                                params.put("Salt", salt);
                            }
                            params.put("id", ID);
                            params.put("Sname", SName);
                            params.put("Fname", Fname);
                            params.put("Email", Email);
                            params.put("Uname", Uname);
                            if (!spinner) {
                                Doctor[0] = docEdit.getText().toString();
                                params.put("doc", Doctor[0]);
                                docEdit.setVisibility(View.VISIBLE);
                                docEdit.setEnabled(false);
                            } else {
                                params.put("doc", DoctorID[0]);
                                doctor.setVisibility(View.INVISIBLE);
                                docEdit.setVisibility(View.VISIBLE);
                                docEdit.setEnabled(false);
                            }
                            @SuppressLint("StaticFieldLeak") final AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                                    "http://lamp.ms.wits.ac.za/~s1644868/EditCustomerData.php", params) {

                                @Override
                                protected void onPostExecute(String output) {

                                    if (output.equals("Success")) {
                                        Newmessage.setTitle("Success");
                                        Newmessage.setMessage("information successfully updated");
                                        Newmessage.setCancelable(true);
                                        Newmessage.setPositiveButton("close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                cancel.callOnClick();
                                            }
                                        });
                                        Newmessage.show();
                                    } else if (output.equals("Failed")) {
                                        Newmessage.setTitle("Failed");
                                        Newmessage.setMessage("information could not be updated updated");
                                        Newmessage.setCancelable(true);
                                        Newmessage.setPositiveButton("close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                final Button Edit = (Button) findViewById(R.id.btnEdit);
                                                cancel.setVisibility(View.GONE);
                                                Edit.setVisibility(View.VISIBLE);
                                                idEdit.setEnabled(false);
                                                fnEdit.setEnabled(false);
                                                snEdit.setEnabled(false);
                                                emEdit.setEnabled(false);
                                                unEdit.setEnabled(false);
                                                doctor.setEnabled(false);
                                                pass.setVisibility(View.GONE);
                                                pass.setText("");
                                                confirm.setVisibility(View.GONE);
                                                confirm.setText("");

                                                Info.setText("View Info");
                                                submit.setVisibility(View.GONE);
                                                History.setVisibility(View.VISIBLE);
                                            }
                                        });
                                        Newmessage.show();
                                    }
                                }

                            };
                            asyncHTTPPost.execute();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    message.show();
                }
            }

        });

        final Button Edit = (Button) findViewById(R.id.btnEdit);
        Edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                idEdit.setEnabled(true);
                fnEdit.setEnabled(true);
                snEdit.setEnabled(true);
                emEdit.setEnabled(true);
                unEdit.setEnabled(true);
                doctor.setEnabled(true);
                Edit.setVisibility(View.GONE);
                confirm.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                Info.setText("Edit Info");
                submit.setVisibility(View.VISIBLE);
                History.setVisibility(View.GONE);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ContentValues params = new ContentValues();
                params.put("id", ID);
                @SuppressLint("StaticFieldLeak") final AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                        "http://lamp.ms.wits.ac.za/~s1644868/getCustomerData.php", params) {

                    @Override
                    protected void onPostExecute(String output) {

                        showData(output);
                    }

                };
                asyncHTTPPost.execute();

                cancel.setVisibility(View.GONE);
                Edit.setVisibility(View.VISIBLE);
                idEdit.setEnabled(false);
                fnEdit.setEnabled(false);
                snEdit.setEnabled(false);
                docEdit.setEnabled(false);
                emEdit.setEnabled(false);
                unEdit.setEnabled(false);
                doctor.setEnabled(false);
                doctor.setEnabled(false);
                pass.setVisibility(View.GONE);
                pass.setText("");
                confirm.setVisibility(View.GONE);
                confirm.setText("");

                Info.setText("View Info");
                submit.setVisibility(View.GONE);
                History.setVisibility(View.VISIBLE);


            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), History.class);
                intent.putExtra("Cust_ID", ID);
                startActivity(intent);
            }
        });
    }
    //===========================================================================================


    //-------------------------------------------Nav Drawer--------------------------------------
    @Override
    public void onBackPressed() {
        if (Drawer.isDrawerOpen(GravityCompat.START)) {
            Drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        String Un = getIntent().getStringExtra("Cust_UN");
        final String ID = getIntent().getStringExtra("Cust_ID");
        switch (item.getItemId()) {
            case R.id.nav_profile:
                intent = new Intent(getBaseContext(), profile.class);
                intent.putExtra("Cust_ID", ID);
                intent.putExtra("Cust_UN", Un);
                startActivity(intent);
                break;
            case R.id.nav_history:
                intent = new Intent(getBaseContext(), History.class);
                intent.putExtra("Cust_ID", ID);
                intent.putExtra("Cust_UN", Un);
                startActivity(intent);
                break;
            case R.id.nav_Cart:
                intent = new Intent(getBaseContext(), stock.class);
                intent.putExtra("Cust_ID", ID);
                intent.putExtra("Cust_UN", Un);
                intent.putExtra("Class", "Customer");
                intent.setClass(profile.this, stock.class);
                startActivity(intent);
                break;
            case R.id.nav_Logout:
                intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("Cust_ID", ID);
                intent.putExtra("Cust_UN", Un);
                startActivity(intent);
                break;
        }
        Drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //=============================================================================================


    public void showData(String output) {
        try {
            JSONArray ja = new JSONArray(output);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);

                EditText idEdit = (EditText) findViewById(R.id.edtID);
                idEdit.setText(jo.getString("CUSTOMER_ID"));
                final EditText fnEdit = (EditText) findViewById(R.id.edtFName);
                fnEdit.setText(jo.getString("CUSTOMER_NAME"));

                final EditText snEdit = (EditText) findViewById(R.id.edtSName);
                snEdit.setText(jo.getString("CUSTOMER_SURNAME"));
                //Toast.makeText(getApplicationContext(),jo.getString("CUSTOMER_DOCTOR"), Toast.LENGTH_LONG).show();
                if (!(jo.getString("CUSTOMER_DOCTOR").equals("null"))) {
                    final EditText docEdit = (EditText) findViewById(R.id.edtDoc);
                    docEdit.setVisibility(View.VISIBLE);
                    docEdit.setText(jo.getString("CUSTOMER_DOCTOR"));
                    spinner = false;
                } else {
                    final Spinner doctor = findViewById(R.id.spinDoctor);
                    spinner =true;
                    doctor.setVisibility(View.VISIBLE);
                    doctor.setEnabled(false);
                    ContentValues params = new ContentValues();
                    String[] doctors = new String[]{"No Doctor"};
                    final List<String> doctorsList = new ArrayList<>(Arrays.asList(doctors));
                    @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getDoctors.php", params) {
                        @Override
                        protected void onPostExecute(String output) {
                            if (!output.equals("Doctors not found")) {
                                try {
                                    JSONArray ja = new JSONArray(output);
                                    for (int i = 0; i < ja.length(); i++) {
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
                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, doctorsList);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    doctor.setAdapter(spinnerArrayAdapter);
                }
                final EditText emEdit = (EditText) findViewById(R.id.edtMail);
                emEdit.setText(jo.getString("CUSTOMER_EMAIL"));

                final EditText unEdit = (EditText) findViewById(R.id.edtUserName);
                unEdit.setText(jo.getString("CUSTOMER_USERNAME"));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    //-----------------------------------Menu-----------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //useMenu = menu;
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        View view = menu.findItem(R.id.Cart_menu).getActionView();
        badge = (NotificationBadge) view.findViewById(R.id.badge);
        updateCartCount();
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Cart_menu) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateCartCount();
    }


    private void updateCartCount() {
        if (badge == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Common.cartRepository.countCartItems() == 0) {
                    badge.setVisibility(View.INVISIBLE);
                } else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                }
            }
        });

    }
//==========================================================================================

    public String getDocId(String output) {
        String DoctorID = "";
        try {
            JSONArray ja = new JSONArray(output);
            JSONObject jo = (JSONObject) ja.get(0);
            DoctorID = (jo.getString("DOCTOR_ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DoctorID;
    }
}