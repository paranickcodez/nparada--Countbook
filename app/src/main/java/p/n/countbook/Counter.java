
/*
 * Version: Version 1.0
 *
 * Date : October 1st, 2017
 *
 * Copyright (c) Nicolas Parada, CMPUT 301, University of Alberta -- All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 */

package p.n.countbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A counter is basic counting object which allows a name for an item, a count for an item (and its count on initialisation),
 * as well as a comment for that item and the date for when these variables were initialized or changed to be associated.
 * A comment may be blank, and the set count is also declared as it's initial count.
 * The MasterCounterListControl extension of AppcompatActivity allows saving/loading a list of such objects to a file through gson.
 * The count or current_value may be increased or decreased by one in the positive integer range.
 * The count can be reset to that initial value, and that date variable can be udated to the current date.
 *
 * @author nparada
 * @version 1.0
 * @see Counter
 * @see MasterCounterListControl
 * @see NewCounterActivity
 * @see EditCounterActivity
 * @see ViewCounterActivity
 * @see CounterListActivity
 * @since 1.0
 */

//http://crunchify.com/how-to-serialize-deserialize-list-of-objects-in-java-java-serialization-example/
public class Counter implements Serializable{
    private String name;
    private int initial_value;
    private int current_value;
    private String comment;
    private Date date;

    /**
     * Initializes a count with just a name and a count (Set as the intital and current count), but
     * with a blank comment.
     *
     * @param name
     * @param initial_value
     */

    public Counter (String name, int initial_value){
        this.name = name;
        this.initial_value = initial_value;
        this.current_value = initial_value;
        this.comment = "";
        date = new Date();
    }

    /**
     * Initializes a count with just a name and a count (Set as the intital and current count), but
     * with a user-defined comment.
     *
     * @param name
     * @param initial_value
     * @param comment
     */

    public Counter (String name, int initial_value, String comment){
        this.name = name;
        this.initial_value = initial_value;
        this.current_value = initial_value;
        this.comment = comment;
        date = new Date();
    }

    /**
     * Change the name String
     *
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the name String
     *
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Return the Initial value as an int.
     *
     * @return
     */

    public int getInitial_value() {
        return initial_value;
    }

    /**
     * Set the Initial value as an int , which is more like a default value I guess. calls update()
     *
     * @param initial_value
     */

    public void setInitial_value(int initial_value) {
        if (current_value >= 0) {
            this.initial_value = initial_value;
            this.update();
        }
    }

    /**
     * Increases the count (current_value) by one. Calls update()
     */

    public void upCount () {
        this.current_value += 1;
        this.update();
    }

    /**
     * Decreases the count (current_value) by one, down to at most zero. Calls update()
     */

    public void downCount () {
        if (this.current_value > 0) {
            this.current_value -= 1;
            this.update();
        } else {this.current_value = 0;}
    }

    /**
     * Sets current_value, takes in any new non-negative current value. Calls update()
     *
     * @param current_value
     */

    public void setCurrent_value(int current_value) {
        if (current_value >= 0) {
            this.current_value = current_value;
            this.update();
        }
    }

    /**
     * Resets current_value to initial_value. Calls update()
     */

    public void resetCurrent_value() {
        if (this.current_value != this.initial_value) {
            this.current_value = this.initial_value;
            this.update();
        }
    }

    /**
     * Return the current value integer.
     *
     * @return
     */

    public int getCurrent_value() {
        return current_value;
    }

    /**
     * Change the comment, it may be blank. Calls update()
     *
     * @param comment
     */

    public void setComment(String comment) {
        this.comment = comment;
        this.update();
    }

    /**
     * Returns the comment string.
     *
     * @return
     */

    public String getComment() {
        return comment;
    }

    /**
     * Returns the Objects date object in "yyyy-MM-dd" format as a string
     *
     * @return
     */

    public String getDate() {
        //https://stackoverflow.com/questions/18480633/java-util-date-format-conversion-yyyy-mm-dd-to-mm-dd-yyyy
        return new SimpleDateFormat("yyyy-MM-dd").format(this.date);
    }

    /**
     * Make the Counter objects date the current date
     */

    private void update() { this.date = new Date();}

    /**
     * When Counter is called to a string, it's toString simply returns the Counter Name and current count concatenated.
     *
     * @return
     */

    @Override
    public String toString() {
        return name + " Counter\n Count: " + current_value;
    }
}
