package com.example.ivonneortega.ecommerceapp.CartActivity;

/**
 * Created by ivonneortega on 4/10/17.
 */

public interface CartChanged {

    public void removeFromDatabase(int position);
    public void setTotalCart();
    public void handlePressToRemove(int id,boolean selected);
    public void moveToWishlist(int position);
}
