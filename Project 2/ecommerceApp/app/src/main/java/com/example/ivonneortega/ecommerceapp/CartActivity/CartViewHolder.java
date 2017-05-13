package com.example.ivonneortega.ecommerceapp.CartActivity;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.R;

/**
 * Created by ivonneortega on 4/8/17.
 */

public class CartViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mModel,mBrand,mSize,mPrice;
    View mRoot, mSwipe;

    public CartViewHolder(View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.image_checkout);
        mModel = (TextView) itemView.findViewById(R.id.model_checkout);
        mBrand = (TextView) itemView.findViewById(R.id.brand_checkout);
        mSize = (TextView) itemView.findViewById(R.id.size_checkout);
        mPrice = (TextView) itemView.findViewById(R.id.price_checkout);
        mRoot = itemView.findViewById(R.id.root_recycler_view_cart_activity);
        mSwipe = itemView.findViewById(R.id.swipe_image);
    }
}
