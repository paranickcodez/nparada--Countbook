
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
 * EditCounterActivity takes in the counters and the index of the counter selected to via the intent from ViewCounterActivity.
 * It sets the EditText boxes in the activity_edit_counter.xml such that the name,
 * count, initial count (initiated at the Counter objects creation), and comment are
 * displayed and made editable for the user. Pressing buttonSave pulls from these textboxes,
 * using the Counter object's setters to update the variables modified here, set the current date,
 * and save the updated Counters via json.
 *
 * @author nparada
 * @version 1.0
 * @see Counter
 * @see MasterCounterListControl
 * @see ViewCounterActivity
 * @since 1.0
 */

public class EditCounterActivity extends MasterCounterListControl {
    public static final String EXTRA_MESSAGE = "com.int.countbook.MESSAGE";
    public static final String CNTR_STREAM = "com.int.countbook.STREAM";
    protected int itemkey;
    private Counter counter;
    private EditText NameEdit;
    private EditText CountEdit;
    private EditText InitialEdit;
    private EditText CommentEdit;

    /**
     * Like the original onCreate() called at the activities start and takes in the savedInstanceState bundle,
     * but gets from the intent the serialized Counters and viewed Counter index from the ViewCounterActivity,
     * such that the counter object can be edited in the ArrayList. It uses strings to format the EditText's
     * to appropriately display the name, count and initial count, and the comment for modification.
     * It sets the content view to the activity-edit-counter xml.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);
        NameEdit = (EditText) findViewById(R.id.name);
        CountEdit = (EditText) findViewById(R.id.count);
        InitialEdit = (EditText) findViewById(R.id.initial);
        CommentEdit = (EditText) findViewById(R.id.comment);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //loadFromFile(); No longer needed--intent
        Counters = (ArrayList<Counter>) intent.getSerializableExtra(CounterListActivity.CNTR_STREAM);
        itemkey = Integer.parseInt(intent.getStringExtra(ViewCounterActivity.EXTRA_MESSAGE));
        counter = Counters.get(itemkey);
        //Add EditText settexts, using getters. Then using, with checks, setters with startapp to change name, countx2, comments and then update date.

        String name = counter.getName();
        String count = "" + counter.getCurrent_value();
        String initcount = "" + counter.getInitial_value();
        String comment = counter.getComment();

        //EditText NameEdit = (EditText) findViewById(R.id.name);
        NameEdit.setText(name);
        //EditText CountEdit = (EditText) findViewById(R.id.count);
        CountEdit.setText(count.trim());
        //EditText InitialEdit = (EditText) findViewById(R.id.initial);
        InitialEdit.setText(initcount.trim());
        //EditText CommentEdit = (EditText) findViewById(R.id.comment);
        CommentEdit.setText(comment);
    }

    /**
     * Called when the ButtonSave Button "SAVE CHANGES" (declared from the xml selected on onCreate())
     * is clicked, the text taken from the NameEdit, CountEdit, initialEdit, and CommentEdit
     * EditText boxes are used to set the counter object and save the ArrayList to json.
     * which is then saved. First though, the name, count and initial count text boxes
     * (and key in Counters of the counter) are checked to have a valid input, aka a length.
     * The validity of count and initial count as a numerical input is assured by InputType.
     * The only parameter is the view which allows the buttonSave button to call this method.
     * The updated Counters is passed via intent, along with the key,
     * to show the changes to the Counter object after startActivity calls ViewCounterActivity.
     *
     * @param view
     * @see Counter
     * @see ViewCounterActivity
     */

    public void startApp(View view) {

        String key = (itemkey + "").trim();
        String name = NameEdit.getText().toString().trim();
        //https://stackoverflow.com/questions/15037465/converting-edittext-to-int-android
        String count = CountEdit.getText().toString().trim();
        String initcount = InitialEdit.getText().toString().trim();
        String comment = CommentEdit.getText().toString().trim();

        if ((name.length() > 0 ) && (count.length() > 0 ) && (initcount.length() > 0 ) && (key.length() > 0 )){
            Intent intent = new Intent(this, ViewCounterActivity.class);
            intent.putExtra(CNTR_STREAM, Counters);
            intent.putExtra(EXTRA_MESSAGE, key);

            counter.setName(name);
            counter.setCurrent_value(Integer.parseInt(count));
            counter.setInitial_value(Integer.parseInt(initcount));
            counter.setComment(comment);

            saveInFile(Counters);
            startActivity(intent);
        }
    }
}
