package com.example.pharmacist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pharmacist.Database.Database.ModelDB.Cart;
import com.example.pharmacist.Utils.Common;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class cart_order extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout Drawer;
    RecyclerView cart_view;
    NotificationBadge badge;
    ImageView cartIcon;
    cartAdapter cAdapter;
    JSONObject infoDoc;
    String ID;

    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        final LinearLayout linPopUp = findViewById(R.id.linPopUp);
        linPopUp.setVisibility(View.INVISIBLE);

        String className = getIntent().getStringExtra("Class");
        if (className.equals("Customer")) {
             ID = getIntent().getStringExtra("Cust_ID");
            Button Order  = (Button)findViewById(R.id.btnPlace);
            Order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Common.cartRepository.countCartItems()>0) {
                        final String Meds = cAdapter.getNames();
                        final Double Price = cAdapter.getTotal();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        final String Date = dateFormat.format(date);
                        ContentValues params = new ContentValues();
                        params.put("Customer", ID);
                        params.put("Meds", Meds);
                        params.put("Price", Price);
                        params.put("Date", Date);
                        @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                                "http://lamp.ms.wits.ac.za/~s1644868/placeOrder.php", params) {

                            @Override
                            protected void onPostExecute(String output) {
                                if (output.equals("Success")) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        asyncHTTPPost.execute();

                        for (int i = 0; i < Common.cartRepository.countCartItems(); ++i) {
                            decreaseQuantity(cAdapter.getName(i), cAdapter.getAvailQuantity(i) - cAdapter.getQuantity(i));
                        }
                        Common.cartRepository.emptyCart();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No items added to cart!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            try {
                infoDoc = new JSONObject(getIntent().getStringExtra("docInfo"));
                final Button btnPrescription  = (Button)findViewById(R.id.btnPlace);
                final RecyclerView cartRecycler = (RecyclerView)findViewById((R.id.cartRecycler));
                btnPrescription.setText("ADD PRESCRIPTION");
                btnPrescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Common.cartRepository.countCartItems()>0) {
                            btnPrescription.setVisibility(View.INVISIBLE);
                            cartRecycler.setVisibility(View.INVISIBLE);
                            linPopUp.setVisibility(View.VISIBLE);
                            Button btnCancel = (Button)findViewById(R.id.btnCancel2);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnPrescription.setVisibility(View.VISIBLE);
                                    cartRecycler.setVisibility(View.VISIBLE);
                                    linPopUp.setVisibility(View.INVISIBLE);

                                }
                            });

                            Button btnAddPrescription2 =(Button)findViewById(R.id.btnAddPrescription2);
                            btnAddPrescription2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ContentValues params = new ContentValues();
                                    String docID = null;
                                    try {
                                        docID = infoDoc.getString("DOCTOR_ID");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if(docID == null){
                                        Toast.makeText(getApplicationContext(), "No doctor ID found", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    params.put("id", docID);
                                    EditText edtName = findViewById(R.id.edtName);
                                    if(edtName.toString() == null){
                                        Toast.makeText(getApplicationContext(), "No patient name and surname entered", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String[] names = edtName.getText().toString().split(" ");
                                    params.put("name",names[0]);
                                    params.put("surname",names[1]);
                                    @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/getVerPat.php", params) {
                                        @Override
                                        protected void onPostExecute(String output) {
                                            if (!output.equals("No patient found")) {
                                                try {
                                                    JSONArray ja1 = new JSONArray(output);
                                                    JSONObject jo1 = (JSONObject) ja1.get(0);
                                                    String custID = jo1.getString("CUSTOMER_ID");
                                                    insertPrescription(custID);
                                                    Common.cartRepository.emptyCart();
                                                } catch (Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Patient not found", Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Patient not found", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    };
                                    asyncHTTPPost.execute();

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No items added to prescription", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //--------------------Navigation drawer & toolbar ---------------------------------

        Drawer = findViewById(R.id.drawer_layout);
        if (className.equals("Customer")) {
            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);
            NavigationView navView = findViewById(R.id.nav_view);
            navView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
            View header = navView.getHeaderView(0);
            String Username = getIntent().getStringExtra("Cust_UN");
            TextView txtUName = header.findViewById(R.id.TxtUN);
            txtUName.setText(Username);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, Drawer, myToolbar, R.string.nav_draw_open, R.string.nav_draw_close);
            Drawer.addDrawerListener(toggle);
            toggle.syncState();
        }else{
            Drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }


        //===================================================================================

        cart_view = (RecyclerView)findViewById(R.id.cartRecycler);
        cart_view.setLayoutManager(new LinearLayoutManager(this));
        cart_view.setHasFixedSize(true);

        compositeDisposable = new CompositeDisposable();

        loadCartItems();

    }

    private void decreaseQuantity(String sName,int iQuantity){
        ContentValues params2 = new ContentValues();
        params2.put("Name", sName);
        params2.put("Quantity", iQuantity);
        @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost2 = new AsyncHTTPPost(
                             "http://lamp.ms.wits.ac.za/~s1644868/decreaseQuantityAvailable.php", params2) {
            @Override
            protected void onPostExecute(String output) {

            }
        };
        asyncHTTPPost2.execute();
    }

    private void insertPrescription(String custID){
        final String Meds = cAdapter.getNames();
        final Double Price = cAdapter.getTotal();
        final TextView txtRepeats = (TextView) findViewById(R.id.Repeats);
        final ElegantNumberButton count = (ElegantNumberButton) findViewById(R.id.Txt_count);

        count.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
           @Override
           public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                txtRepeats.setText("Repeats: "+newValue);
            }
        });
        ContentValues params3 = new ContentValues();
        params3.put("ID",custID);
        params3.put("Meds",Meds);
        params3.put("Price",Price);
        params3.put("Repeats",Integer.parseInt(count.getNumber()));

        @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost3 = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1644868/placePrescription.php",params3) {
            @Override
            protected void onPostExecute(String output) {
                if(output.equals("Success")) {
                        Toast.makeText(getApplicationContext(), "Prescription has been placed", Toast.LENGTH_SHORT).show();
                }
                else if (output.equals("Failed")){
                    Toast.makeText(getApplicationContext(), "Prescription has failed", Toast.LENGTH_SHORT).show();
                }
            }
        };
        asyncHTTPPost3.execute();
        Intent intent = new Intent(cart_order.this,DoctorOptions.class);
        intent.putExtra("docInfo",infoDoc.toString());
        startActivity(intent);
    }

    private void loadCartItems() {

        compositeDisposable.add(Common.cartRepository.getCartItems()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<List<Cart>>() {
                                    @Override
                                    public void accept(List<Cart> carts) throws Exception {
                                        displayCartItem(carts);
                                    }
                                })
        );
    }

    private void displayCartItem(final List<Cart> carts) {
        cAdapter = new cartAdapter(this,carts);
        cart_view.setAdapter(cAdapter);

    }

    @Override
    protected void onDestroy() {

        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    //-------------------------for navigation drawer------------------------------------------------
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
                intent.putExtra("Class","Customer");
                intent.setClass(cart_order.this, stock.class);
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
    //==============================================================================================


}
