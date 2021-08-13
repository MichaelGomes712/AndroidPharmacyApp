package com.example.pharmacist.Database.Database.DataSource;

import com.example.pharmacist.Database.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface IcartDataSource {
    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>> getCartItemById(String CartItemId);
    int countCartItems();
    void emptyCart();
    void insertToCart(Cart...carts);
    void updateCart(double total,int qty,String ID);
    void deleteCartItem(Cart cart);
}
