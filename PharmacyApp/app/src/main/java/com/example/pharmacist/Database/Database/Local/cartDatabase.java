package com.example.pharmacist.Database.Database.Local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.pharmacist.Database.Database.ModelDB.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class cartDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();

    private static cartDatabase instance;

    public static cartDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, cartDatabase.class, "HappyPillsDB").allowMainThreadQueries().build();
        return instance;

    }
}
