package com.example.ivonneortega.ecommerceapp.CartActivity;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Swipe.SimpleItemTouchHelperCallback;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartOnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartChanged {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Shoe> mList;
    CartViewRecylerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    TextView mTotal;
    ImageView mIcon_delete;
    Shoe mLastShoe;
    View mRoot;
    ViewGroup mViewGroup;
    List<Integer> mList_Selected;
    View mView;

    private CartOnFragmentInteractionListener mListener;

    public CartFragment() {
        // Required empty public constructor
    }


    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartOnFragmentInteractionListener) {
            mListener = (CartOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartOnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;
        LayoutTransition l = new LayoutTransition();
        l.enableTransitionType(LayoutTransition.CHANGING);
        mViewGroup = (ViewGroup) view.findViewById(R.id.linear_layout_cart_view);
        mViewGroup.setLayoutTransition(l);

        mList_Selected = new ArrayList<>();
        mRoot = view.findViewById(R.id.scroll_view_cart_activity);

        mList = Shoes_DBHelper.getInstance(view.getContext()).getShoesInCheckout();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_cart_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //  mAdapter= new CartViewRecylerViewAdapter(mList,this);
        // mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        mTotal  = (TextView) view.findViewById(R.id.priceText_checkout);
        setTotalCart();

        view.findViewById(R.id.checkout_button_cart_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(v.getContext());
                View promptsView = li.inflate(R.layout.checkout_alert_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        checkout(mTotal);
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface CartOnFragmentInteractionListener {
        // TODO: Update argument type and name
        void updateCartIconCount();
        void CarthandlePressToRemove(int id, boolean selected,CartViewRecylerViewAdapter adapter);
    }

    public float cartTotal()
    {
        float sum=0;
        for(int i=0;i<mList.size();i++)
        {
            if(mList.get(i).isCheckout()== true)
            {
                sum+=mList.get(i).getPrice();
            }
        }

        DecimalFormat format = new DecimalFormat("#.##");
        sum = Float.valueOf(format.format(sum));
        return sum;
    }

    public void checkout(View view)
    {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        setTotalCart();
        Shoes_DBHelper.getInstance(view.getContext()).removeAllFromCart();
    }

    @Override
    public void removeFromDatabase(final int position) {
        Shoes_DBHelper.getInstance(mView.getContext()).removeFromCheckout(mList.get(position).getId());
        mLastShoe = mList.get(position);
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        setTotalCart();
        mListener.updateCartIconCount();


        Snackbar.make(mRoot, "Shoe removed from cart", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).addToCart(mList.get(position).getId());
                        setTotalCart();
                        mListener.updateCartIconCount();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();

    }

    public void setTotalCart() {
        mTotal.setText("$"+String.valueOf(cartTotal()));
    }

    @Override
    public void handlePressToRemove(int id, boolean selected) {
        mListener.CarthandlePressToRemove(id,selected,mAdapter);
    }

    @Override
    public void moveToWishlist(final int position) {
        Shoes_DBHelper.getInstance(mRecyclerView.getContext()).removeFromCheckout(mList.get(position).getId());
        Shoes_DBHelper.getInstance(mRecyclerView.getContext()).addToWishlist(mList.get(position).getId());
        mLastShoe = mList.get(position);
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        setTotalCart();
        mListener.updateCartIconCount();


        Snackbar.make(mRoot, "Shoe added to wishlit", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.add(position,mLastShoe);
                        mAdapter.notifyItemInserted(position);
                        Shoes_DBHelper.getInstance(v.getContext()).addToCart(mList.get(position).getId());
                        Shoes_DBHelper.getInstance(v.getContext()).removeFromWishlist(mList.get(position).getId());
                        setTotalCart();
                        mListener.updateCartIconCount();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDarkCheckoutPage))
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter = new CartViewRecylerViewAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        setTotalCart();
    }

    public void removeAllSelectedFromCart()
    {
        for(int i=0;i<mList_Selected.size();i++)
        {
            System.out.println(mList_Selected.get(i));
            Shoes_DBHelper.getInstance(mView.getContext()).removeFromCheckout(mList_Selected.get(i));
            Shoe shoe = Shoes_DBHelper.getInstance(mView.getContext()).getShoeById(mList_Selected.get(i));
            int index = mList.indexOf(shoe);
            mList.remove(shoe);
            mAdapter.notifyItemRemoved(index);
            mListener.updateCartIconCount();
        }
        setTotalCart();
    }
}
