package com.example.ivonneortega.ecommerceapp.MainActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.R;

/**
 * Created by ivonneortega on 4/6/17.
 */

public class MainActivityViewHolder extends RecyclerView.ViewHolder {

    //CardView mCardView;
    ImageView mImageView;
    TextView mTitle, mPrice, mBrand;
    View mRoot;


    public MainActivityViewHolder(View itemView) {
        super(itemView);
       // mCardView = (CardView) itemView.findViewById(R.id.card_view_main_activity);
        mImageView = (ImageView) itemView.findViewById(R.id.item_image_main_activity);
        mTitle = (TextView) itemView.findViewById(R.id.show_title_main_activity);
        mPrice = (TextView) itemView.findViewById(R.id.price_main_activity);
        mBrand = (TextView) itemView.findViewById(R.id.brand_main_activity);
        mRoot = itemView.findViewById(R.id.main_activity_rootLayout);
    }
}
