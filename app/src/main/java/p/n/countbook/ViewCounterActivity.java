/*
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) Nicolas Parada, CMPUT 301, University of Alberta -- All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package p.n.countbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Noted Issue: This app uses gson to save all users Counters from the ArrayList in a stream.
 * Therefore, all Activities which allow user modification extend MasterCounterListControl, a superclass
 * with the methods saveInFile(ArrayList<Counters> Counters) and LoadFromFile(): ArrayList<Counters> Counters.
 * The former is used across all major activities to insure the users Counter edits/additions are always saved,
 * the latter is called from the CounterListActivity insuring the Counter List displayed is the one saved,
 * the users save file is defined through the static String "file.sav."
 *
 * ViewCounterActivity takes in the counters and the index of the counter selected via the intent.
 * It sets the textView boxes in the activity_view_counter.xml such that the name, count,
 * initial count (initiated at the Counter objects creation), date of last modification and comment are
 * displayed for the user. Buttons for upping and lowering or refreshing the Counter objects count to that initial value
 * are given methods, and buttons for deleting and directly editing these values are given methods which return the user
 * to the changed list in CounterListActivity or make the details editable in the EditCounterActivity, in that order.
 *
 * @author nparada
 * @version 1.0
 * @see Counter
 * @see MasterCounterListControl
 * @see EditCounterActivity
 * @see CounterListActivity
 * @since 1.0
 */

public class ViewCounterActivity extends MasterCounterListControl {
    public static final String EXTRA_MESSAGE = "com.int.countbook.MESSAGE";
    public static final String CNTR_STREAM = "com.int.countbook.STREAM";
    private int itemkey;
    private Counter counter;
    private TextView NameView;
    private TextView CountView;
    private TextView InitialView;
    private TextView CommentView;
    private TextView DateView;

    /**
     * Like the original onCreate() called at the activities start and takes in the savedInstanceState bundle,
     * but gets from the intent the serialized Counters and viewed Counter index from the CounterListActivity,
     * such that the counter object can be added to the ArrayList. It uses strings to format the Textviews
     * to appropriately display the name, count and initial count, the comment if it contains characters,
     * and the date variable of the object updated each time it is modified.
     * It sets the content view to the activity-view-counter xml.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_counter);
        NameView = (TextView) findViewById(R.id.NameView);
        CountView = (TextView) findViewById(R.id.CountView);
        InitialView = (TextView) findViewById(R.id.InitialView);
        CommentView = (TextView) findViewById(R.id.CommentView);
        DateView = (TextView) findViewById(R.id.DateView);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Counters = (ArrayList<Counter>) intent.getSerializableExtra(CNTR_STREAM);
        itemkey = Integer.parseInt(intent.getStringExtra(EXTRA_MESSAGE));
        counter = Counters.get(itemkey);

        String name = counter.getName() + " Counter" ;
        String count = "Count: " + counter.getCurrent_value();
        String initcount = "Start Count: " + counter.getInitial_value();
        String comment = "";
        if ( counter.getComment().trim().length() > 0 ) {
            comment = "Comment: " + counter.getComment();
        }
        String date = "Last modified: " + counter.getDate();

        //TextView NameView = (TextView) findViewById(R.id.NameView);
        NameView.setText(name);
        //TextView CountView = (TextView) findViewById(R.id.CountView);
        CountView.setText(count);
        //TextView InitialView = (TextView) findViewById(R.id.InitialView);
        InitialView.setText(initcount);
        //TextView CommentView = (TextView) findViewById(R.id.CommentView);
        CommentView.setText(comment);
        //TextView DateView = (TextView) findViewById(R.id.DateView);
        DateView.setText(date);
    }

    /**
     * Sends the Serializable Counters as an extra with a new intent along with the position
     * in counters of the Counter object viewed, such that the method which editCounter starts,
     * EditCounterActivity, may modify the Counter object within the current list of Counters.
     * This is called when editStart button (EDIT ALL) is pressed in the view.
     *
     * @param view
     * @see Counter
     * @see EditCounterActivity
     * @see MasterCounterListControl
     */

    public void editCounter(View view) {
        String key = (itemkey + "").trim();
        if (key.length() > 0 ) {
            Intent intenty = new Intent(this, EditCounterActivity.class);
            intenty.putExtra(CNTR_STREAM, Counters);
            intenty.putExtra(EXTRA_MESSAGE, key);
            startActivity(intenty);
        }
    }

    /**
     * Increases the count of Counter Object at position itemkey in Counters by one via the upCount()
     * method for Counters. Updates the CountView and DateView to show the changes made to the Counter.
     * clicking on the increase button calls this method. It saves the changed ArrayList to json.
     *
     * @param view
     * @see Counter
     * @see MasterCounterListControl
     */

    public void upCounter(View view) {
        counter.upCount();
        saveInFile(Counters);

        String count = "Count: " + counter.getCurrent_value();
        String date = "Last modified: " + counter.getDate();
        CountView.setText(count);
        DateView.setText(date);
    }

    /**
     * Decreases the count of Counter Object at position itemkey in Counters by one via the downCount()
     * method for Counters. Updates the CountView and DateView to show the changes made to the Counter.
     * clicking on the decrease button calls this method. It saves the changed ArrayList to json.
     *
     * @param view
     * @see Counter
     * @see MasterCounterListControl
     */

    public void downCounter(View view) {
        counter.downCount();
        saveInFile(Counters);

        String count = "Count: " + counter.getCurrent_value();
        String date = "Last modified: " + counter.getDate();
        CountView.setText(count);
        DateView.setText(date);
    }

    /**
     * Resets the int current_value, the count, of the viewed Counter Object to it's int initial_value.
     * It calls the resetCurrent_value() method for a counter, and then saves the changed ArrayList to json.
     * Updates the CountView and DateView to show the changes made to the Counter.
     * clicking on the resetCount button calls this method.
     *
     * @param view
     * @see Counter
     * @see MasterCounterListControl
     */

    public void resetCounter(View view) {
        counter.resetCurrent_value();
        saveInFile(Counters);
        String count = "Count: " + counter.getCurrent_value();
        String date = "Last modified: " + counter.getDate();
        CountView.setText(count);
        DateView.setText(date);
    }

    /**
     * Removes the current counter object from the Arraylist Counters before saving it to json before
     * returning to the CounterListActivity since the view can no longer display the now deleted Counter.
     *
     * @param view
     * @see CounterListActivity
     * @see MasterCounterListControl
     */

    public void deleteCounter(View view) {
        Intent intent = new Intent(this, CounterListActivity.class);
        Counters.remove(counter);
        saveInFile(Counters);
        startActivity(intent);
    }
}
