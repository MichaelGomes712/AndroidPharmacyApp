package com.example.pharmacist.Database.Database.Local;

import com.example.pharmacist.Database.Database.DataSource.IcartDataSource;
import com.example.pharmacist.Database.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartDataSource implements IcartDataSource {

    private CartDAO cartDAO;
    private static CartDataSource insatnce;

    public CartDataSource(CartDAO cartDAO){
        this.cartDAO = cartDAO;
    }

    public static CartDataSource getInsatnce(CartDAO cartDAO) {
        if (insatnce ==null)
            insatnce = new CartDataSource(cartDAO);
        return insatnce;
    }

    @Override
    public Flowable<List<Cart>> getCartItems() {
        return cartDAO.getCarttIems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(String CartItemId) {
        return cartDAO.getCartItemById(CartItemId);
    }

    @Override
    public int countCartItems() {
        return cartDAO.countCartItems();
    }

    @Override
    public void emptyCart() {
        cartDAO.emptyCart();
    }

    @Override
    public void insertToCart(Cart... carts) {
        cartDAO.insertToCart(carts);
    }

    @Override
    public void updateCart(double total,int qty,String ID) {
        cartDAO.updateCart( total,qty,ID);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        cartDAO.deletCartItem(cart);
    }
}
