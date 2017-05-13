package com.example.ivonneortega.ecommerceapp;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ivonneortega on 4/5/17.
 */

public class Shoe {
    private int mId,mUnits_available;
    float mPrice;
    String mSize;

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    private String mBrand,mModel,mType, mCategory,mPicture_Name,mDescription;
    private boolean mCheckout, mWishlist,mIsSelected;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public static int FALSE = 0;
    public static int TRUE = 1;


    public Shoe(int id, int units_available, String brand, String model, String type, String category, String picture_Name,int checkout, int wishlist, float price,String description) {
        mId = id;
        mUnits_available = units_available;
        mBrand = brand;
        mModel = model;
        mType = type;
        mCategory = category;
        mDescription = description;
        mPicture_Name = picture_Name;
        mIsSelected=false;
        mPrice = price;
        if(checkout==FALSE)
            mCheckout=false;
        else
            mCheckout=true;
        if(wishlist==FALSE)
            mWishlist=false;
        else
            mWishlist=true;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    public Shoe(int id, int units_available, String brand, String model, String type, String category, int checkout, int wishlist, float price, String description) {
        mId = id;
        mUnits_available = units_available;
        mBrand = brand;
        mModel = model;
        mType = type;
        mCategory = category;
        mPrice = price;
        mIsSelected=false;

        mDescription = description;
        if(checkout==FALSE)
            mCheckout=false;
        else
            mCheckout=true;
        if(wishlist==FALSE)
            mWishlist=false;
        else
            mWishlist=true;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public boolean isCheckout() {
        return mCheckout;
    }

    public void setCheckout(boolean checkout) {
        mCheckout = checkout;
    }

    public boolean isWishlist() {
        return mWishlist;
    }

    public void setWishlist(boolean wishlist) {
        mWishlist = wishlist;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getUnits_available() {
        return mUnits_available;
    }

    public void setUnits_available(int units_available) {
        mUnits_available = units_available;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getPicture_Name() {
        return mPicture_Name;
    }

    public void setPicture_Name(String picture_Name) {
        mPicture_Name = picture_Name;
    }

    public static class SortByBrand implements Comparator<Shoe>
    {


        @Override
        public int compare(Shoe o1, Shoe o2) {
            return o1.getBrand().compareTo(o2.getBrand());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Shoe)
        {
            if(((Shoe) obj).getId() == this.getId())
                return true;

        }
        return false;
    }

//    public int getIndex(List<Shoe> list,Shoe shoe)
//    {
//        for(int i=0;i<list.size();i++)
//        {
//            if(list.get(i).getId()==shoe.getId())
//                return i;
//        }
//        return -1;
//    }

}
