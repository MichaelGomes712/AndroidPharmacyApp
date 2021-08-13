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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pharmacist.Database.Database.DataSource.CartRepository;
import com.example.pharmacist.Database.Database.Local.CartDataSource;
import com.example.pharmacist.Database.Database.Local.cartDatabase;
import com.example.pharmacist.Database.Database.ModelDB.Cart;
import com.example.pharmacist.Utils.Common;
import com.google.gson.Gson;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.text.TextUtils.split;

public class stock extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout Drawer;
    NotificationBadge badge;
    ImageView cartIcon;
    itemAdapter RAdapt;
    JSONObject infoDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        //---------------------------------Local DB STuff---------------------------------------

        initDB();

        //=====================================================================================

        //--------------------Navigation drawer & toolbar ---------------------------------
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        String className = getIntent().getStringExtra("Class");
        Drawer = findViewById(R.id.drawer_layout);
        if (className.equals("Customer")) {
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
        }else{
            Drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        //===================================================================================


        //---------------------------DB Stuff----------------------------------------------------


        className = getIntent().getStringExtra("Class");
        if (className.equals("Customer")) {
            final String ID = getIntent().getStringExtra("Cust_ID");
            ContentValues params = new ContentValues();
            params.put("id", ID);
            @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                    "http://lamp.ms.wits.ac.za/~s1644868/getCustomerStock.php", params) {

                @Override
                protected void onPostExecute(String output) {

                    showData(output);
                    getPres();
                }

            };
            asyncHTTPPost.execute();

        }else{
            try {
                infoDoc = new JSONObject(getIntent().getStringExtra("docInfo"));

                ContentValues params = new ContentValues();
                //params.put("id", infoDoc.getString("DOCTOR_SURNAME"));
                @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                        "http://lamp.ms.wits.ac.za/~s1644868/getDoctorStock.php", params) {

                    @Override
                    protected void onPostExecute(String output) {

                        showData(output);
                    }

                };
                asyncHTTPPost.execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }//========================================================================================



    }
    //================Onclick End
    //------------------------------------Local Db Stuff------------------------------------------


    private void initDB() {
        Common.CartDB = cartDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(
                CartDataSource.getInsatnce(
                        Common.CartDB.cartDAO()));

    }

    //==========================================================================================


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
                intent.setClass(stock.this, stock.class);
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


    public void showData(String output) {
        RecyclerView RView;

        RecyclerView.LayoutManager RLay;

        String className = getIntent().getStringExtra("Class");
        final ArrayList<StockItem> ItemList = new ArrayList<>();
        if (className.equals("Customer")) {
            try {
                JSONArray ja = new JSONArray(output);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    ItemList.add(new StockItem(jo.getString("PHARMACEUTICAL_NAME"),
                            jo.getString("PHARMACEUTICAL_SELLINGPRICE"),
                            jo.getString("PHARMACEUTICAL_AVAILABLEQUANTITY"),
                            jo.getString("PHARMACEUTICAL_SCHEDULE"),
                            jo.getString("PHARMACEUTICAL_ID")));
                }

                RView = findViewById(R.id.Shop_View);
                RView.setHasFixedSize(true);
                RLay = new LinearLayoutManager(this);
                RAdapt = new itemAdapter(ItemList);

                RView.setLayoutManager(RLay);
                RView.setAdapter(RAdapt);

                RAdapt.setOnItemClickListener(new itemAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(final int pos) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(stock.this);
                        final View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_to_cart_layout, null);
                        final TextView cartName = (TextView) itemView.findViewById(R.id.cart_name);
                        final TextView cartPrice = (TextView) itemView.findViewById(R.id.Cart_price);
                        final ElegantNumberButton Cartcount = (ElegantNumberButton) itemView.findViewById(R.id.Txt_count);
                        Cartcount.setRange(1, Integer.parseInt(ItemList.get(pos).getsQty()));
                        final TextView txtTotal = (TextView) itemView.findViewById(R.id.Total);
                        txtTotal.setText("R" + ItemList.get(pos).getsPrice());
                        final double[] Total = new double[1];
                        Total[0] = Double.parseDouble(ItemList.get(pos).getsPrice());

                        Cartcount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                            @Override
                            public void onValueChange(ElegantNumberButton view, int oldValue, final int newValue) {
                                Log.d(null, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                                Total[0] = Math.round(Double.parseDouble(ItemList.get(pos).getsPrice()) * newValue * 100.00) / 100.00;
                                txtTotal.setText("R" + Total[0]);
                            }
                        });


                        cartName.setText(ItemList.get(pos).getsName());
                        cartPrice.setText("R" + ItemList.get(pos).getsPrice());

                        builder.setView(itemView);
                        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                try {
                                    Cart cartItem = new Cart();
                                    cartItem.id = ItemList.get(pos).getsID();
                                    cartItem.name = ItemList.get(pos).getsName();
                                    cartItem.total = Total[0];
                                    cartItem.price = Double.parseDouble(ItemList.get(pos).getsPrice());
                                    cartItem.quantity = Integer.parseInt(Cartcount.getNumber());
                                    cartItem.quantityAvailable = Integer.parseInt(ItemList.get(pos).getsQty());
                                    Common.cartRepository.insertToCart(cartItem);

                                    Log.d("Happy_Pills_Debug", new Gson().toJson(cartItem));
                                    updateCartCount();
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                        builder.show();
                    }
                });

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }else{
            try {
                JSONArray ja = new JSONArray(output);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    ItemList.add(new StockItem(jo.getString("PHARMACEUTICAL_NAME"),
                            jo.getString("PHARMACEUTICAL_SELLINGPRICE"),
                            jo.getString("PHARMACEUTICAL_AVAILABLEQUANTITY"),
                            jo.getString("PHARMACEUTICAL_SCHEDULE"),
                            jo.getString("PHARMACEUTICAL_ID")));
                }

                RView = findViewById(R.id.Shop_View);
                RView.setHasFixedSize(true);
                RLay = new GridLayoutManager(this,2);
                RAdapt = new itemAdapter(ItemList);

                RView.setLayoutManager(RLay);
                RView.setAdapter(RAdapt);

                RAdapt.setOnItemClickListener(new itemAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(final int pos) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(stock.this);
                        final View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_to_cart_layout, null);
                        final TextView cartName = (TextView) itemView.findViewById(R.id.cart_name);
                        final TextView cartPrice = (TextView) itemView.findViewById(R.id.Cart_price);
                        final ElegantNumberButton Cartcount = (ElegantNumberButton) itemView.findViewById(R.id.Txt_count);
                        Cartcount.setRange(1, Integer.parseInt(ItemList.get(pos).getsQty()));
                        final TextView txtTotal = (TextView) itemView.findViewById(R.id.Total);
                        txtTotal.setText("R" + ItemList.get(pos).getsPrice());
                        final double[] Total = new double[1];
                        Total[0] = Double.parseDouble(ItemList.get(pos).getsPrice());

                        Cartcount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                            @Override
                            public void onValueChange(ElegantNumberButton view, int oldValue, final int newValue) {
                                Log.d(null, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                                Total[0] = Math.round(Double.parseDouble(ItemList.get(pos).getsPrice()) * newValue * 100.00) / 100.00;
                                txtTotal.setText("R" + Total[0]);
                            }
                        });


                        cartName.setText(ItemList.get(pos).getsName());
                        cartPrice.setText("R" + ItemList.get(pos).getsPrice());

                        builder.setView(itemView);
                        builder.setNegativeButton("ADD TO PRESCRIPTION", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                try {
                                    Cart cartItem = new Cart();
                                    cartItem.id = ItemList.get(pos).getsID();
                                    cartItem.name = ItemList.get(pos).getsName();
                                    cartItem.total = Total[0];
                                    cartItem.price = Double.parseDouble(ItemList.get(pos).getsPrice());
                                    cartItem.quantity = Integer.parseInt(Cartcount.getNumber());
                                    cartItem.quantityAvailable = Integer.parseInt(ItemList.get(pos).getsQty());
                                    Common.cartRepository.insertToCart(cartItem);

                                    Log.d("Happy_Pills_Debug", new Gson().toJson(cartItem));
                                    updateCartCount();
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                        builder.show();
                    }
                });

            } catch (JSONException e) {

                e.printStackTrace();
            }
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
        cartIcon =(ImageView) view.findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = getIntent().getStringExtra("Class");
                if (className.equals("Customer")) {
                    Intent intent = new Intent(getBaseContext(), cart_order.class);
                    final String ID = getIntent().getStringExtra("Cust_ID");
                    intent.putExtra("Cust_ID", ID);
                    intent.putExtra("Class","Customer");
                    intent.setClass(stock.this, cart_order.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getBaseContext(), cart_order.class);
                    intent.putExtra("docInfo", infoDoc.toString());
                    intent.putExtra("Class","Doctor");
                    intent.setClass(stock.this, cart_order.class);
                    startActivity(intent);
                }
            }
        });
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                RAdapt.getFilter().filter(s);
                return false;
            }
        });
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


    void updateCartCount() {
        if (badge == null) return;
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
    public void getPres() {
        final String ID = getIntent().getStringExtra("Cust_ID");
        ContentValues Presparams = new ContentValues();
        Presparams.put("id", ID);
        @SuppressLint("StaticFieldLeak") AsyncHTTPPost PresasyncHTTPPost = new AsyncHTTPPost(
                "http://lamp.ms.wits.ac.za/~s1644868/getCustomerPres.php", Presparams) {

            @Override
            protected void onPostExecute(String output) {
                if (!(output.equals("none"))) {
                    try {


                        JSONArray ja = new JSONArray(output);
                        for (int i = 0; i < ja.length(); i++) {
                            final JSONObject jo = (JSONObject) ja.get(i);

                            String drugs[] = split(jo.getString("PRESCRIPTION_PHARMACEUTICALS"), ",");
                            final int Repeats = Integer.parseInt(jo.getString("PRESCRIPTIONS_REPEATSLEFT"));
                            for (int j = 0; j < drugs.length; j++) {
                                String Item[] = split(drugs[j], "\\*");
                                final int qty = Integer.parseInt(Item[0]);
                                final String sName = Item[1];
                                final ArrayList<StockItem> ItemList = new ArrayList<>();

                                ContentValues params = new ContentValues();
                                params.put("name", sName);
                                @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                                        "http://lamp.ms.wits.ac.za/~s1644868/fillCartPres.php", params) {

                                    @Override
                                    protected void onPostExecute(String output) {
                                        JSONArray ja = null;
                                        try {
                                            ja = new JSONArray(output);
                                            JSONObject jo = (JSONObject) ja.get(0);
                                            Cart cartItem = new Cart();
                                            cartItem.id = jo.getString("PHARMACEUTICAL_ID");
                                            cartItem.name = jo.getString("PHARMACEUTICAL_NAME");
                                            cartItem.total = Double.parseDouble(jo.getString("PHARMACEUTICAL_SELLINGPRICE")) * qty;
                                            cartItem.price = Double.parseDouble(jo.getString("PHARMACEUTICAL_SELLINGPRICE"));
                                            cartItem.quantity = qty;
                                            cartItem.quantityAvailable = Integer.parseInt(jo.getString("PHARMACEUTICAL_AVAILABLEQUANTITY"));

                                            Common.cartRepository.insertToCart(cartItem);
                                            //DecreaseRepeats(ID,Repeats);

                                            Log.d("Happy_Pills_Debug", new Gson().toJson(cartItem));
                                            updateCartCount();
                                            final View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_to_cart_layout, null);
                                            final ElegantNumberButton Cartcount = (ElegantNumberButton) itemView.findViewById(R.id.Txt_count);
                                            Cartcount.setRange(1, Integer.parseInt(jo.getString("PHARMACEUTICAL_AVAILABLEQUANTITY")));
                                            Cartcount.setNumber(String.valueOf(qty));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                };
                                asyncHTTPPost.execute();

                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        PresasyncHTTPPost.execute();

    }
    /*
    public void DecreaseRepeats(String id,int num) {
        ContentValues params = new ContentValues();
        params.put("id", id);
        @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                "http://lamp.ms.wits.ac.za/~s1644868/DecreaseRepeats.php", params) {

            @Override
            protected void onPostExecute(String output) {
                if (output.equals("yeah")){
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
                }

            }

        };
        asyncHTTPPost.execute();
        }
        */

}

