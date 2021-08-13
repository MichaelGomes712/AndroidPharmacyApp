package com.example.pharmacist.Database.Database.DataSource;

import com.example.pharmacist.Database.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartRepository implements IcartDataSource{

    private IcartDataSource icartDataSource;

    public CartRepository(IcartDataSource icartDataSource){
        this.icartDataSource = icartDataSource;
    }

    private static CartRepository instance;

    public static CartRepository getInstance(IcartDataSource icartDataSource) {
        if (instance == null)
            instance= new CartRepository((icartDataSource));
        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCartItems() {
        return icartDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(String CartItemId) {
        return icartDataSource.getCartItemById(CartItemId);
    }

    @Override
    public int countCartItems() {
        return icartDataSource.countCartItems();
    }

    @Override
    public void emptyCart() {
        icartDataSource.emptyCart();
    }

    @Override
    public void insertToCart(Cart... carts) {
        icartDataSource.insertToCart(carts);
    }

    @Override
    public void updateCart(double total,int qty,String ID) {
        icartDataSource.updateCart(total,qty,ID);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        icartDataSource.deleteCartItem(cart);
    }
}
