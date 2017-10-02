
/*
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) Nicolas Parada, CMPUT 301, University of Alberta -- All Rights Reserved. You may use,
 * distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package p.n.countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 *
 * This app gives a user a application framework for keeping track of items which they have counted.
 * They can add a named counter for their item, set the current count and optionally add comment for the current state.
 * They can edit their count either by counting up or down, resetting it to that initial value, or going into
 * edit mode from the view screen. There they can modify the intial or current count directly.
 * The last of any of these edits is noted through the date or can be through any modification to the comment.
 *
 * MainActivity starts on the apps initialization, onCreate is called and displays the logo of CountBook:
 * the Count and the title of the app. The method startApp is assigned to the image which is wrapped to the entire view
 * and when clicked/touched calls startActivity for the CounterListActivity,
 * the activity for displaying the user's Counters.
 * This class inherits from AppCompatActivity and stops the app from being boring.
 *

 * @author nparada
 * @version 1.0
 * @see CounterListActivity
 * @see AppCompatActivity
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     *onCreate() is called at the start of the Activities runtime,
     * and uses the saveInstanceState bundle to bring in any previous data, it derives from the superclass.
     * It sets the view to the activity_main xml, which displays the Count A-ha-ha.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     *This method is assigned to the Count via the activity-main xml design framework, it is called
     * when the image is clicked and uses the startActivity method to starts CounterListActivity.class
     * This  brings the user to their Counters saved with gson.
     *
     * @param view
     * @see CounterListActivity
     */

    public void startApp(View view) {
        /** Called when the user taps the Count Ah-ha-ha */
        Intent intent = new Intent(this, CounterListActivity.class);
        startActivity(intent);
    }

}
