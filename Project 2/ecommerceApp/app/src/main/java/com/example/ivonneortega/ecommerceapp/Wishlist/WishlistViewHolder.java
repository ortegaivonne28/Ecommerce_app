package com.example.ivonneortega.ecommerceapp.Wishlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.R;

/**
 * Created by ivonneortega on 4/11/17.
 */

public class WishlistViewHolder extends RecyclerView.ViewHolder {

    public TextView mBrand,mModel,mPrice;
    ImageView mImage;
    View mRoot;

    public WishlistViewHolder(View itemView) {
        super(itemView);
        mBrand = (TextView) itemView.findViewById(R.id.brand_wishlist);
        mModel = (TextView) itemView.findViewById(R.id.model_wishlist);
        mPrice = (TextView) itemView.findViewById(R.id.price_wishlist);
        mImage = (ImageView) itemView.findViewById(R.id.image_wishlist);
        mRoot = itemView.findViewById(R.id.root_recycler_view_wishlist_activity);
    }
}
