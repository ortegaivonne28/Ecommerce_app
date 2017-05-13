package com.example.ivonneortega.ecommerceapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ivonneortega on 4/13/17.
 */

public class WishlistTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickOnCartIconToMoveToCartActivity()
    {

        //Clicking on item
        onView(withId(R.id.overflow_main_activity))
                .perform(click());


        //Clicking on item Wishlist from Popup menu
        onView(withText("Wishlist"))
                .perform(click());

        //Clicking on item
        onView(withId(R.id.cart_main_activity))
                .perform(click());
        //Checking if one of the views is present then it means I correctly moved to my detailViewActivity
        onView(withId(R.id.priceText_checkout)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnTitleToMoveToMainActivity()
    {

        //Clicking on item
        onView(withId(R.id.overflow_main_activity))
                .perform(click());


        //Clicking on item Wishlist from Popup menu
        onView(withText("Wishlist"))
                .perform(click());

        onView(withId(R.id.title_custom_toolbar))
                .perform(click());
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_view_image)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnItemToMoveToDetailView()
    {

        //Clicking on item
        onView(withId(R.id.overflow_main_activity))
                .perform(click());


        //Clicking on item Wishlist from Popup menu
        onView(withText("Wishlist"))
                .perform(click());

        onView(withId(R.id.recycler_view_wishlist_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clicking on item
        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());

        onView(withId(R.id.cart_main_activity))
                .perform(click());

        //Clicking on item
        onView(withId(R.id.recycler_view_cart_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Checking if one of the views is present then it means I correctly moved to my detailViewActivity
        onView(withId(R.id.detail_view_image)).check(matches(isDisplayed()));
    }

}
