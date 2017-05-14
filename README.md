# Ecommerce App

**Ecommerce App** is an app that allows users to browse a selection of shoes, do a search by brand name or model, filter shoes by category, price, brand, and model, save their favorites to a wishlist, add to cart and proceed to checkout.

This app was built as a project for **General Assembly’s Android development Bootcamp**.

## Project Details

The list of shoes were stored in a custom database, including name, brand, mode, type of shoe, id, quantity and price. The shoes table also included two columns named isCheck and isWishlist, if this columns were 0 it meant the show wasn't in the cart or in the wishlist.

The toolbar is a custom toolbar created to be able to have a cart icon with a textview that corresponds to the number of items in the cart. Each time a shoe is added or removed from the cart that custom textview is updated.

To be able to swipe left and right in the recycler view, the class ItemTouchHelper.Callback was extended and with the help of a interface the shoe was either removed or moved depending on the swipe. Also, the method onChildDraw was extended to draw a paint below the item to show the corresponding icon.

Each activity has a fragment that is used for master detail flow when using tablets. When on tablets, Main Activity is the host for all the fragments, therefore it implements all the interfaces the fragments need to perform each action.

## Features include:

- The user can search through the brand name or model name; results are updated on each key press.
- The user can filter results; the funnel icon opens a dialog with a drop down of place categories User can sort the results by price, model, category, type of shoe or model name.
- User can add to cart or to wishlist from the detail screen.
- User can remove from the cart or move to wishlist from the cart screen. A user can remove from wishlist or move to cart from the wishlist screen.

##### Skills/languages/tools: Java, Android SDK, SQLite.

## Screenshots

![image](/screenshots/ecommerceApp-1.jpg)
![image](/screenshots/ecommerceApp-2.jpg)
![image](/screenshots/ecommerceApp-3.jpg)



