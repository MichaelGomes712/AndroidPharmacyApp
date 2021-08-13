package com.example.pharmacist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pharmacist.Utils.Common;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class History extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout Drawer;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        //---------------------------Nav Drawer & Toolbar-----------------------------------------
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Drawer  = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        View header = navView.getHeaderView(0);
        String Username = getIntent().getStringExtra("Cust_UN");
        TextView txtUName = header.findViewById(R.id.TxtUN);
        txtUName.setText(Username);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,Drawer,myToolbar,R.string.nav_draw_open,R.string.nav_draw_close);
        Drawer.addDrawerListener(toggle);
        toggle.syncState();

        //========================================================================================


        //----------------------------------DB Stuff---------------------------------------------
        String CustomerID = getIntent().getStringExtra("Cust_ID");
        ContentValues params = new ContentValues();
        params.put("id",CustomerID);
        @SuppressLint("StaticFieldLeak") AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(
                "http://lamp.ms.wits.ac.za/~s1644868/getCustomerHistory.php", params) {

            @Override
            protected void onPostExecute(String output) {

                showData(output);
            }

        };
        asyncHTTPPost.execute();
        //========================================================================================

    }
    public void showData(String output) {
        TextView TxtNum, TxtPharma, TxtPrice,TxtDO,TxtDC;
        TableLayout TblView;
        TableRow TblRow;

        TblView = (TableLayout) findViewById(R.id.TblHistory);
        TblView.setColumnStretchable(0,true);
        TblView.setColumnStretchable(1,true);
        TblView.setColumnStretchable(2,true);
        TblView.setColumnStretchable(3,true);
        TblView.setColumnStretchable(4,true);
        try {
            JSONArray ja = new JSONArray(output);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                TblRow = new TableRow(this);

                TxtNum = new TextView(this);
                TxtPharma = new TextView(this);
                TxtPrice= new TextView(this);
                TxtDO = new TextView(this);
                TxtDC = new TextView(this);

                TxtNum.setText(jo.getString("ORDER_NUMBER"));
                TxtNum.setTextSize(13);
                TxtNum.setGravity(Gravity.CENTER);

                TxtPharma.setText(jo.getString("ORDER_PHARMACEUTICALS"));
                TxtPharma.setTextSize(13);
                TxtPharma.setGravity(Gravity.CENTER);

                TxtPrice.setText(jo.getString("ORDER_PRICE"));
                TxtPrice.setTextSize(13);
                TxtPrice.setGravity(Gravity.CENTER);

                TxtDO.setText(jo.getString("ORDER_DATEORDERED"));
                TxtDO.setTextSize(13);
                TxtDO.setGravity(Gravity.CENTER);

                TxtDC.setText(jo.getString("ORDER_DATECOMPLETED"));
                TxtDC.setTextSize(13);
                TxtDC.setGravity(Gravity.CENTER);

                TblRow.addView(TxtNum);
                TblRow.addView(TxtPharma);
                TblRow.addView(TxtPrice);
                TblRow.addView(TxtDO);
                TblRow.addView(TxtDC);
                TblView.addView(TblRow);

            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
    //---------------------------------------Nav Drawer--------------------------------------------
    @Override
    public void onBackPressed(){
        if (Drawer.isDrawerOpen(GravityCompat.START)){
            Drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected( MenuItem item){
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
                intent.setClass(History.this, stock.class);
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
    //===========================================================================================
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
        if (badge == null) {return;}
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
}
