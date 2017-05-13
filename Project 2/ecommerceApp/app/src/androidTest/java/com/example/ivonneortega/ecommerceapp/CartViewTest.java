package com.example.ivonneortega.ecommerceapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by ivonneortega on 4/13/17.
 */

public class CartViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickOnTitleToMoveToMainActivity()
    {
        //Get to detail view
        onView(withId(R.id.cart_main_activity)).perform(click());

        onView(withId(R.id.title_custom_toolbar))
                .perform(click());
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_view_image)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnItemToMoveToDetailView()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
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

    @Test
    public void checkOrderTotal()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());

        onView(withId(R.id.title_custom_toolbar))
                .perform(click());

        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());


        onView(withId(R.id.cart_main_activity))
                .perform(click());


        onView(withId(R.id.priceText_checkout)).check(matches(withText("$219.49")));


        onView(withId(R.id.checkout_button_cart_activity)).perform(click());

        onView(withText("OK")).perform(click());

    }

    @Test
    public void checkoutButton()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());

        onView(withId(R.id.title_custom_toolbar))
                .perform(click());

        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());


        onView(withId(R.id.cart_main_activity))
                .perform(click());


        onView(withId(R.id.checkout_button_cart_activity)).perform(click());

        onView(withText("OK")).perform(click());

        onView(withId(R.id.priceText_checkout)).check(matches(withText("$0.0")));

    }

    @Test
    public void checkoutRemoveFromCart()
    {
        //Get to detail view
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_view_add_to_cart))
                .perform(click());

        onView(withId(R.id.cart_main_activity))
                .perform(click());

//        onView(withId(R.id.recycler_view_cart_activity))
//                .perform(RecyclerViewActions.actionOnItem(
//                        hasDescendant(withId(R.id.image_checkout)), click()));


        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image_checkout),
                        withParent(allOf(withId(R.id.root_recycler_view_cart_activity),
                                withParent(withId(R.id.recycler_view_cart_activity))))));
        appCompatImageView.perform(scrollTo(), click());
        //onView(withId(R.id.image_checkout)).perform(click());

        onView(withId(R.id.remove))
                .perform(click());



        onView(withId(R.id.priceText_checkout)).check(matches(withText("$0.0")));

    }

    @Test
    public void clickOnWishlistMenuToMoveToWishListActivity()
    {
        //Get to detail view
        onView(withId(R.id.cart_main_activity))
                .perform(click());
        //Clicking on item
        onView(withId(R.id.overflow_main_activity))
                .perform(click());


        //Clicking on item Wishlist from Popup menu
        onView(withText("Wishlist"))
                .perform(click());
        //Checking if one of the views is present then it means I correctly moved to my wishlist Activity
        onView(withId(R.id.wishlist_text_empty)).check(matches(isDisplayed()));
    }


}
