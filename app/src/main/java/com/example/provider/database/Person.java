package com.example.provider.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

/**
 * A class representation of a row in table "Person".
 */
public class Person {

    // SQL convention says Table name should be "singular", so not Persons
    public static final String TABLE_NAME = "PersonTable";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_ID = "_id";
    // These fields can be anything you want.
    public static final String COL_FIRSTNAME = "firstName";
    public static final String COL_LASTNAME = "lastName";

    public Person(String firstName, String lastName, String birth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
    }

    public Person() {
    }

    public static final String COL_BIO = "bio";
    // For database projection so order is consistent
    public static final String[] FIELDS = {COL_ID, COL_FIRSTNAME, COL_LASTNAME,
            COL_BIO};

    /*
     * The SQL code that creates a Table for storing Persons in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY,"
                    + COL_FIRSTNAME + " TEXT NOT NULL DEFAULT '',"
                    + COL_LASTNAME + " TEXT NOT NULL DEFAULT '',"
                    + COL_BIO + " TEXT NOT NULL DEFAULT ''"
                    + ")";
    public static String TAG = "Person";
    public long id = -1;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirth() {
        return birth;
    }

    private String lastName;
    private String birth;

    public Person(final Cursor cursor) {
        Log.i(TAG, "cursor=" + cursor);
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.firstName = cursor.getString(1);
        this.lastName = cursor.getString(2);
        this.birth = cursor.getString(3);
    }

    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_FIRSTNAME, firstName);
        values.put(COL_LASTNAME, lastName);
        values.put(COL_BIO, birth);
        return values;
    }
}
