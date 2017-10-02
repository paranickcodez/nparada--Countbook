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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static p.n.countbook.R.id.parent;

/**
 * Noted Issue: This app uses gson to save all users Counters from the ArrayList in a stream.
 * Therefore, all Activities which allow user modification extend MasterCounterListControl, a superclass
 * with the methods saveInFile(ArrayList<Counters> Counters) and LoadFromFile(): ArrayList<Counters> Counters.
 * The former is used across all major activities to insure the users Counter edits/additions are always saved,
 * the latter is called from the CounterListActivity insuring the Counter List displayed is the one saved,
 * the users save file is defined through the static String "file.sav."
 *
 * Note: This app gives a user a application framework for keeping track of items which they have counted.
 * They can add a named counter for their item, set the current count and optionally add comment for the current state.
 * They can edit their count either by counting up or down, resetting it to that initial value, or going into
 * edit mode from the view screen. There they can modify the intial or current count directly.
 * The last of any of these edits is noted through the date or can be through any modification to the comment.
 *
 * CounterListActivity is the key activity for displaying a users lists, which calls the method LoadFromFile()
 * from the superclass to display the users current Counters saved externally though json.
 * This class sets the view to activity_counter_list.xml which contains a ListView set to display
 * the data from the loaded ArrayList of Counters stored through an ArrayAdapter.
 * This class gives the user the option to add a new counter or view any previous counter's details
 * through selecting one in the adapter.
 *
 * @author nparada
 * @version 1.0
 * @see Counter
 * @see MasterCounterListControl
 * @see ViewCounterActivity
 * @see EditCounterActivity
 * @see NewCounterActivity
 * @since 1.0
 */

public class CounterListActivity extends MasterCounterListControl{
    public static final String EXTRA_MESSAGE = "com.int.countbook.MESSAGE";
    public static final String CNTR_STREAM = "com.int.countbook.STREAM";
    private ListView CounterlistView;
    private TextView CounterCountView;
    private ArrayAdapter<Counter> adapter;

    /**
     *Overriding the superclass, this method cis called when the Activity is started, and after
     * important the savedInstanceState parameters, it sets the appropriate view, while also defining
     * the textview Counters within to display the length of the list or amount of counter objects.
     * An OnItemClickListener checks and returns the position in the adapter/array of the Counter clicked on.
     * This calls the ViewCounter method which calls the ViewCounterActivity class for that counter.
     *
     * @param savedInstanceState
     * @see this.viewCounter
     * @see ViewCounterActivity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        CounterlistView = (ListView) findViewById(R.id.CounterlistView);
        CounterCountView = (TextView) findViewById(R.id.Counters);

        /**https://stackoverflow.com/questions/8126175/android-how-to-add-an-item-click-method-to-an-arrayadapter?rq=1*/
        CounterlistView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                viewCounter(view, position);
            }
        });

    }

    /**
     * Sends the Serializable Counters as an extra with a new intent such that the method which newcounter starts,
     * NewCounterActivity, may modify the current list of Counters by adding a new one before saving the
     * new total list to the file. This is called when newCounterMenu (newCounter +) pressed in the view.
     *
     * @param view
     * @see NewCounterActivity
     */

    public void newCounter(View view) {
        Intent intenty = new Intent(this, NewCounterActivity.class);
        intenty.putExtra(CNTR_STREAM, Counters);
        startActivity(intenty);
    }

    /**
     *
     * Once called from the array adapter, checks to see if a valid counter position key has been selected,
     * the itemkey parameter is then sent as an extra along with the current list of counters such that
     * the counter can be modified within the list in the ViewCounterActivity called by this method.
     * ViewCounter is called by onItemClick method for the CounterlistView.setOnItemClickListener.
     *
     * @param view
     * @param itemkey
     * @see ViewCounterActivity
     */

    public void viewCounter(View view, int itemkey) {
        String key = (itemkey + "").trim();
        if (key.length() > 0 ) {
            Intent intent = new Intent(this, ViewCounterActivity.class);
            intent.putExtra(EXTRA_MESSAGE, key);
            intent.putExtra(CNTR_STREAM, Counters);
            startActivity(intent);
        }
    }

    /**
     *Called after onCreate, overridden from the super class this still uses AppCompatActivities
     * onstart() actions, but further assigns Counters the list previously saved in json,
     * unless not then that method returns a new ArrayList<Counter>, sets the adapter to that ArrayList,
     * and adds the size of Counters--the amount of user made Counter objects--to the Counters TextView in the xml.
     *
     * @see ArrayAdapter
     * @see MasterCounterListControl
     */

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Counters = loadFromFile();
        adapter = new ArrayAdapter<Counter>(this, R.layout.listformat, Counters); //new stuff
        CounterlistView.setAdapter(adapter);// new stuff
        String CountCount = Counters.size() + " Counters";
        CounterCountView.setText(CountCount);
    }
}
