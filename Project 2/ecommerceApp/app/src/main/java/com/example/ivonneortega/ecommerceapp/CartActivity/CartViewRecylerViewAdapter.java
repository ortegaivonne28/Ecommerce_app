package com.example.ivonneortega.ecommerceapp.CartActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ivonneortega.ecommerceapp.DetailView.DetailViewActivity;
import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Swipe.ItemTouchHelperAdapterCartView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by ivonneortega on 4/8/17.
 */

public class CartViewRecylerViewAdapter extends RecyclerView.Adapter<CartViewHolder> implements ItemTouchHelperAdapterCartView {

    List<Shoe> mList;
    CartChanged mContext;

    public CartViewRecylerViewAdapter(List<Shoe> list,CartChanged context)
    {
        mList = list;
        mContext = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_checkout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position)
    {
        final Shoe shoe = mList.get(position);

        holder.mPrice.setText("$"+String.valueOf(shoe.getPrice()));
        holder.mBrand.setText(shoe.getBrand());
        holder.mModel.setText(shoe.getModel());
        holder.mSize.setText(shoe.getSize());

        Picasso.with(holder.mImage.getContext())
                .load(shoe.getPicture_Name())
                .resize(100, 100)
                .into(holder.mImage);

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), DetailViewActivity.class);
                intent.putExtra(MainActivity.GOTODETAILVIEW,shoe.getId());
                v.getContext().startActivity(intent);
            }
        });

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!shoe.isSelected())
                {
                    shoe.setSelected(true);
                    holder.mRoot.setBackgroundColor(v.getResources().getColor(R.color.colorAuxProductPage));
                    //holder.mSwipe.setVisibility(View.VISIBLE);
                    mContext.handlePressToRemove(shoe.getId(),true);
                }
                else
                {
                    shoe.setSelected(false);
                    holder.mRoot.setBackgroundColor(Color.WHITE);
                    //holder.mSwipe.setVisibility(View.GONE);
                    mContext.handlePressToRemove(shoe.getId(),false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(mList, i, i + 1);
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(mList, i, i - 1);
//            }
//        }
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }


    @Override
    public void onItemDismiss(int position) {
        mContext.removeFromDatabase(position);

    }

    @Override
    public void onItemAddToWishlist(int position) {
        mContext.moveToWishlist(position);
    }

    public void updateList(List<Shoe> list)
    {
        mList = list;
        notifyDataSetChanged();
    }
}
