package com.example.ivonneortega.ecommerceapp.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.ecommerceapp.DetailView.DetailViewActivity;
import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Swipe.ItemTouchHelperAdapterCartView;
import com.example.ivonneortega.ecommerceapp.Swipe.ItemTouchHelperAdapterWishlist;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by ivonneortega on 4/11/17.
 */

public class WishlistRecyclerViewAdapter extends RecyclerView.Adapter<WishlistViewHolder> implements ItemTouchHelperAdapterWishlist {

    private List<Shoe> mList;
    private WishlistChanged mWishlistChanged;

    public WishlistRecyclerViewAdapter(List<Shoe> list, WishlistChanged context) {

        mList = list;
        mWishlistChanged = context;
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_wishlist, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WishlistViewHolder holder, int position)
    {

        final Shoe shoe = mList.get(position);
        System.out.println(shoe.getBrand());
        Picasso.with(holder.mImage.getContext())
                .load(shoe.getPicture_Name())
                .resize(170, 150)
                .into(holder.mImage);

        holder.mBrand.setText(shoe.getBrand());
        holder.mModel.setText(shoe.getModel());
        holder.mPrice.setText(String.valueOf(shoe.getPrice()));

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(),DetailViewActivity.class);
                intent.putExtra(MainActivity.GOTODETAILVIEW,shoe.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    public void moveToCart(int position, Context context)
    {
        Shoes_DBHelper.getInstance(context).addToCart(mList.get(position).getId());
        Shoes_DBHelper.getInstance(context).removeFromWishlist(mList.get(position).getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mWishlistChanged.removeFromDatabase(position);
    }

    @Override
    public void onItemAddToCart(int position) {
        mWishlistChanged.addToCart(position);
    }
}
