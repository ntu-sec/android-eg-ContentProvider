package com.example.provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.provider.database.DatabaseHandler;
import com.example.provider.database.Person;

public class PersonListActivity extends FragmentActivity implements
        PersonListFragment.Callbacks {

    public static final String TAG = PersonDetailActivity.class.getName();

    public void dumpDB() {
        for (String db : databaseList()) {
            deleteDatabase(db);
            Log.d(TAG, "DB: " + getDatabasePath(db));
        }
    }

    @Override
    protected void onResume() {
        if (BuildConfig.DEBUG && !getApplicationContext().getClass().getCanonicalName().equals("android.app.Application")) {
            throw new RuntimeException("context not android.app.Application");
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        dumpDB();


        if (findViewById(R.id.person_detail_container) != null) {
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((PersonListFragment) getSupportFragmentManager().findFragmentById(
                    R.id.person_list)).setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link PersonListFragment.Callbacks} indicating that
     * the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(long id) {
        Intent detailIntent = new Intent(this, PersonDetailActivity.class);
        detailIntent.putExtra(PersonDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        if (R.id.newPerson == item.getItemId()) {
            result = true;
            // Create a new person.
            Person p = new Person();
            DatabaseHandler.getInstance(this).putPerson(p);
            // Open a new fragment with the new id
            onItemSelected(p.id);
        }
        return result;
    }
}
