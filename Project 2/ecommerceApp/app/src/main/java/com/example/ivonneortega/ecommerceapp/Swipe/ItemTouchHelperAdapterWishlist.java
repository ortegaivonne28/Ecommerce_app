package com.example.ivonneortega.ecommerceapp.Swipe;

/**
 * Created by ivonneortega on 4/11/17.
 */

public interface ItemTouchHelperAdapterWishlist {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
    void onItemAddToCart(int position);
}
