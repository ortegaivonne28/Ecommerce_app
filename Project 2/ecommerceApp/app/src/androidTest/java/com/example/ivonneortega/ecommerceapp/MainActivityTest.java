package com.example.ivonneortega.ecommerceapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.ivonneortega.ecommerceapp.MainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by ivonneortega on 4/13/17.
 */

public class MainActivityTest {



    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testCartIcon() throws Exception {

    }

    @Test
    public void canViewCartIcon()
    {
        onView(withId(R.id.cart_main_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void canViewOverFlowIcon()
    {
        onView(withId(R.id.overflow_main_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnItemToMoveToDetailView()
    {

        //Clicking on item
        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Checking if one of the views is present then it means I correctly moved to my detailViewActivity
        onView(withId(R.id.detail_view_image)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnCartIconToMoveToCartActivity()
    {

        //Clicking on item
        onView(withId(R.id.cart_main_activity))
                .perform(click());
        //Checking if one of the views is present then it means I correctly moved to my detailViewActivity
        onView(withId(R.id.priceText_checkout)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnWishlistMenuToMoveToWishListActivity()
    {

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
    public void checkSearch()
    {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.search_button), withContentDescription("Search"),
                        withParent(allOf(withId(R.id.search_bar),
                                withParent(withId(R.id.search_editText)))),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("nike"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text), withText("nike"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        onView(withId(R.id.recycler_view_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_view_image)).check(matches(isDisplayed()));

    }



}
