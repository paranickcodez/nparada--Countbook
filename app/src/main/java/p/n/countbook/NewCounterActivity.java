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
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Noted Issue: This app uses gson to save all users Counters from the ArrayList in a stream.
 * Therefore, all Activities which allow user modification extend MasterCounterListControl, a superclass
 * with the methods saveInFile(ArrayList<Counters> Counters) and LoadFromFile(): ArrayList<Counters> Counters.
 * The former is used across all major activities to insure the users Counter edits/additions are always saved,
 * the latter is called from the CounterListActivity insuring the Counter List displayed is the one saved,
 * the users save file is defined through the static String "file.sav."
 *
 * NewCounterActivity sets the text boxes in the activity_new_counter.xml such that when a user adds a name and a count,
 * with an optional comment, clicking the New Counter button in the view adds a new counter to Counters
 * and then savesInFile(Counters). It then returns the user to the CounterListViewActivity.
 *
 * @author nparada
 * @version 1.0
 * @see Counter
 * @see MasterCounterListControl
 * @see CounterListActivity
 * @since 1.0
 */

public class NewCounterActivity extends MasterCounterListControl {
    public static final String CNTR_STREAM = "com.int.countbook.STREAM";

    /**
     * Like the original onCreate() called at the activities start and takes in the savedInstanceState bundle,
     * but gets from the intent the serialized Counters from the CounterListActivity such that the new counter object can be added to the ArrayList.
     * It sets the content view  to the activity-new-counter xml.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Counters = (ArrayList<Counter>) intent.getSerializableExtra(CounterListActivity.CNTR_STREAM);
    }

    /**
     * When the AddButton Button (declared from the xml selected on onCreate()) the text taken from the Name,
     * Count, and Comment EditText boxes are used to declare a new counter object and add it to the ArrayList,
     * which is then saved. First though, the name and count text boxes are checked to have an input or length.
     * The validity of count as a numerical input is assured by InputType.
     * The only parameter is the view which allows the Addbutton to call this method.
     *
     * @param view
     * @see Counter
     * @see CounterListActivity
     */

    public void startApp(View view) {

        EditText nameET = (EditText) findViewById(R.id.Name);
        EditText countET = (EditText) findViewById(R.id.Count);
        EditText commentET = (EditText) findViewById(R.id.Comment);
        String name = nameET.getText().toString().trim();
        //https://stackoverflow.com/questions/15037465/converting-edittext-to-int-android
        String count = countET.getText().toString().trim();
        String comment = commentET.getText().toString().trim();

        if ((count.length() > 0 ) && (name.length() > 0 )){
            //loadFromFile();
            Intent intent = new Intent(this, CounterListActivity.class);

            Counter counter = new Counter(name, Integer.parseInt(count), comment);
            Counters.add(counter);

            saveInFile(Counters);
            startActivity(intent);
        }else{}
    }

}
