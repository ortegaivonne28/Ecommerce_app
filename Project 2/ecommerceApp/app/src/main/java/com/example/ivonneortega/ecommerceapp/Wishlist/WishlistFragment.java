package com.example.ivonneortega.ecommerceapp.Wishlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Swipe.SimpleItemTouchHelperCallback;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WishlistOnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends Fragment implements WishlistChanged {

    RecyclerView mRecyclerView;
    WishlistRecyclerViewAdapter mAdapter;
    List<Shoe> mShoeList;
    Shoe mLastShoe;
    View mRoot, mView;
    private WishlistOnFragmentInteractionListener mListener;

    public WishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishlistFragment newInstance() {
        WishlistFragment fragment = new WishlistFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WishlistOnFragmentInteractionListener) {
            mListener = (WishlistOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartOnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_wishlist_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        mShoeList = Shoes_DBHelper.getInstance(view.getContext()).shoesInWishList();
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRoot = view.findViewById(R.id.root_wishlist);
        mView = view;



    }

    @Override
    public void removeFromDatabase(final int position) {

        Shoes_DBHelper.getInstance(mRoot.getContext()).removeFromWishlist(mShoeList.get(position).getId());
        mLastShoe = mShoeList.get(position);
        mShoeList.remove(position);
        mAdapter.notifyItemRemoved(position);
        mListener.wishlistUpdateIconCart();

        Snackbar.make(mRoot, "Shoe removed from wishlist", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShoeList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).addToWishlist(mShoeList.get(position).getId());
                        mListener.wishlistUpdateIconCart();


                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }

    @Override
    public void addToCart(final int position) {
        Shoes_DBHelper.getInstance(mRoot.getContext()).removeFromWishlist(mShoeList.get(position).getId());
        Shoes_DBHelper.getInstance(mRoot.getContext()).addToCart(mShoeList.get(position).getId());
        mLastShoe = mShoeList.get(position);
        mShoeList.remove(position);
        mAdapter.notifyItemRemoved(position);
        mListener.wishlistUpdateIconCart();


        Snackbar.make(mRoot, "Shoe added to Cart", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShoeList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).removeFromCheckout(mShoeList.get(position).getId());
                        Shoes_DBHelper.getInstance(v.getContext()).addToWishlist(mShoeList.get(position).getId());
                        mListener.wishlistUpdateIconCart();


                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface WishlistOnFragmentInteractionListener {
        // TODO: Update argument type and name
        void wishlistUpdateIconCart();
    }


    @Override
    public void onResume()
    {
            super.onResume();
            mAdapter = new WishlistRecyclerViewAdapter(mShoeList,this);
            mRecyclerView.setAdapter(mAdapter);

            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(mAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(mRecyclerView);

            mListener.wishlistUpdateIconCart();

    }

}
