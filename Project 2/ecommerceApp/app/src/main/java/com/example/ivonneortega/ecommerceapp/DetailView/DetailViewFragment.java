package com.example.ivonneortega.ecommerceapp.DetailView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ivonneortega.ecommerceapp.CartActivity.CartViewActivity;
import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;
import com.example.ivonneortega.ecommerceapp.R;
import com.example.ivonneortega.ecommerceapp.Shoe;
import com.example.ivonneortega.ecommerceapp.Shoes_DBHelper;
import com.example.ivonneortega.ecommerceapp.Wishlist.WishlistActivity;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SHOE_ID = "param1";
    private int mId;
    private TextView mBrand,mModel,mDescription,mPrice,mTypeOfShoe;
    private ImageView mImage;
    private Spinner mSize;
    private String mSelectedSize;
    private Button mAddToCart,mAddToCart2,mSize6,mSize7,mSize8,mSize9,mSize10,mSize11,mWishlist;
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mSelectedShoeId;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment DetailViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailViewFragment newInstance(int selectedShoeId) {
        DetailViewFragment fragment = new DetailViewFragment();
        Bundle args = new Bundle();
        args.putInt(SHOE_ID, selectedShoeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedShoeId = getArguments().getInt(SHOE_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_view, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartOnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViews(view);

        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clickAddtoCart(mSelectedShoeId);
            }
        });
        mAddToCart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clickAddtoCart(mSelectedShoeId);

            }
        });
        mWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clickAddToWishlist(mSelectedShoeId);
            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void clickAddtoCart(int id);
        void clickAddToWishlist(int id);
    }

    public void setViews(View view)
    {
        Shoe shoe = Shoes_DBHelper.getInstance(view.getContext()).getShoeById(mSelectedShoeId);
        mBrand = (TextView) view.findViewById(R.id.brand_detail_view);
        mModel = (TextView) view.findViewById(R.id.model_detail_view);
        mDescription = (TextView) view.findViewById(R.id.description_detail_view);
        mPrice = (TextView) view.findViewById(R.id.price_detail_view);
        mImage = (ImageView) view.findViewById(R.id.detail_view_image);
        mTypeOfShoe = (TextView) view.findViewById(R.id.type_of_shoe_detail_view);
        // mSize = (Spinner) findViewById(R.id.size_spinner);
        mAddToCart = (Button) view.findViewById(R.id.detail_view_add_to_cart);
        mAddToCart2 = (Button) view.findViewById(R.id.addToCart_detail_view2);
        mWishlist = (Button) view.findViewById(R.id.addToWishlist_detail_view);
        mSize6 = (Button) view.findViewById(R.id.size6);
        mSize7 = (Button) view.findViewById(R.id.size7);
        mSize8 = (Button) view.findViewById(R.id.size8);
        mSize9 = (Button) view.findViewById(R.id.size9);
        mSize10 = (Button) view.findViewById(R.id.size10);
        mSize11 = (Button) view.findViewById(R.id.size11);
//        mSize6.setOnClickListener(this);
//        mSize7.setOnClickListener(view.getContext());
//        mSize8.setOnClickListener(this);
//        mSize9.setOnClickListener(this);
//        mSize10.setOnClickListener(this);
//        mSize11.setOnClickListener(this);
      //  mAddToCart.setOnClickListener(this);
        //mAddToCart2.setOnClickListener(this);
       // mWishlist.setOnClickListener(this);

//        view.findViewById(R.id.remove).setVisibility(View.GONE);

        mBrand.setText(shoe.getBrand());
        mTypeOfShoe.setText(shoe.getType());
        mModel.setText(shoe.getModel());
        mPrice.setText("$"+String.valueOf(shoe.getPrice()));
        mPrice.setClickable(false);
        mDescription.setText(shoe.getDescription());
        Picasso.with(view.getContext())
                .load(shoe.getPicture_Name())
                .resize(480, 400)
                .into(mImage);

    }
}
