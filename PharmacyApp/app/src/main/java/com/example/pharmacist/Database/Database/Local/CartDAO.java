package com.example.pharmacist.Database.Database.Local;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pharmacist.Database.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
    Flowable<List<Cart>> getCarttIems();

    @Query("SELECT * FROM Cart WHERE id=:CartItemId")
    Flowable<List<Cart>> getCartItemById(String CartItemId);

    @Query("SELECT COUNT(*) FROM Cart")
    int countCartItems();

    @Query("DELETE FROM Cart")
    void emptyCart();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToCart(Cart...carts);

    @Query("UPDATE Cart SET Total =:total,quantity =:qty WHERE id =:ID" )
    void updateCart(double total,int qty,String ID);

    @Delete
    void deletCartItem(Cart cart);

}
