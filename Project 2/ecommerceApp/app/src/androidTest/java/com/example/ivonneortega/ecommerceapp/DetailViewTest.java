package com.example.ivonneortega.ecommerceapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.ivonneortega.ecommerceapp.DetailView.DetailViewActivity;
import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by ivonneortega on 4/13/17.
 */

public class DetailViewTest {

    private int mCart = 0;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void clickOnTitleToMoveToMainActivity()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.title_custom_toolbar))
                .perform(click());
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_view_image)).check(matches(isDisplayed()));
    }


    @Test
    public void clickOnCartIconToMoveToCartActivity()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clicking on item
        onView(withId(R.id.cart_main_activity))
                .perform(click());
        //Checking if one of the views is present then it means I correctly moved to my detailViewActivity
        onView(withId(R.id.priceText_checkout)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnWishlistMenuToMoveToWishListActivity()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Clicking on item
        onView(withId(R.id.overflow_main_activity))
                .perform(click());


        //Clicking on item Wishlist from Popup menu
        onView(withText("Wishlist"))
                .perform(click());
        //Checking if one of the views is present then it means I correctly moved to my wishlist Activity
        onView(withId(R.id.wishlist_text_empty)).check(matches(isDisplayed()));
    }

    @Test
    public void addToCartFromDetailViewButtonOne()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clicking on item
        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Total: $120.0")))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE)));

        mCart++;
        onView(withId(R.id.cart_quantity_main_activity)).check(matches(withText("1")));

        onView(withId(R.id.cart_main_activity))
                .perform(click());



    }

    @Test
    public void addToCartFromDetailViewButtonTwo()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clicking on item
        onView(withId(R.id.addToCart_detail_view2))
                .perform(scrollTo(),click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Total: $120.0")))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE)));

        mCart++;
        onView(withId(R.id.cart_quantity_main_activity)).check(matches(withText("1")));

        onView(withId(R.id.cart_main_activity))
                .perform(click());



    }

    @Test
    public void addToWishlistFromDetailView()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clicking on item
        onView(withId(R.id.addToWishlist_detail_view))
                .perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Shoe added to wishlist")))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.overflow_main_activity))
                .perform(click());

        //Clicking on item Wishlist from Popup menu
        onView(withText("Wishlist"))
                .perform(click());
        //Checking if one of the views is present then it means I correctly moved to my wishlist Activity
        onView(withId(R.id.wishlist_text_empty)).check(matches(isDisplayed()));

    }
}
