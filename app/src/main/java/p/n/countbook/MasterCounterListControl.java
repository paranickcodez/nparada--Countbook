
/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package p.n.countbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created/**
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
 * edit mode from the view screen. There they can modify the initial or current count directly.
 * The last of any of these edits is noted through the date or can be through any modification to the comment.
 *
 * MasterCounterListControl saves and loads via gson as noted above, but extends AppcompatActivity
 * and therefore serves as an intermediary for running this app but in association with gson. If removed,
 * All load and save calls must be removed from the the classes which extend this class and replaced with
 * a different data system. The arrayList is still passed as an intent outside of the CounterListActivity,
 * therefore the data will have a lifetime across all child activities of the list view.
 *
 * @author nparada
 * @version 1.0
 * @see Counter
 * @see ViewCounterActivity
 * @see EditCounterActivity
 * @see NewCounterActivity
 * @since 1.0 by N on 9/23/2017.
 */

public class MasterCounterListControl extends AppCompatActivity{
    protected static final String FILENAME = "file.sav";
    protected ArrayList<Counter> Counters = new ArrayList<Counter>();

    /**
     *Used to Return in any subclass the counter object Arraylist saved in json to the document file.save. If non can be retrieved,
     * the fileNotFound exception declares catches the absence and declares a new empty ArrayList for
     * the app and returns it. If any error opening or closing the stream is made Runtime errors are called.
     *
     * @return ArrayList Counter
     * @throws new ArrayList
     * @throws RuntimeException
     * @see Counter
     */

    protected ArrayList<Counter> loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            return gson.fromJson(in, listType);
            //https://github.com/google/gson/blob/master/UserGuide.md#TOC-Collections-Examples

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return new ArrayList<Counter>();
        } catch ( IOException e) {
            //TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    /**
     * Takes in a Counter Array and then opens the stream to rewrite the savefile with the new Counters.
     * Called whenever any modification or addition to the users counters is made.
     * If any error opening or closing the stream is made Runtime errors are called.
     *
     * @param Counters
     * @throws RuntimeException
     * @see Counter
     */

    protected void saveInFile(ArrayList<Counter> Counters) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(Counters, writer);
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}