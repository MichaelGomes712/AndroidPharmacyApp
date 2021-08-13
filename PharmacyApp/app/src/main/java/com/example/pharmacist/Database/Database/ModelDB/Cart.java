package com.example.pharmacist.Database.Database.ModelDB;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//import io.reactivex.annotations.NonNull;
import android.support.annotation.NonNull;

@Entity(tableName = "Cart")
public class Cart {


    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull public String id="";

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "Price")
    public double price;
    @ColumnInfo(name = "Total")
    public double total;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "quantityAvail")
    public int quantityAvailable;
}
