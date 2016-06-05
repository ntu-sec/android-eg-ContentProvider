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

public class MainActivity extends FragmentActivity implements
        PersonListFragment.Callbacks {

    @Override
    public void onItemSelected(long id) {
        Intent detailIntent = new Intent(this, PersonDetailActivity.class);
        detailIntent.putExtra(PersonDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    public static final String TAG = MainActivity.class.getSimpleName();

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
        Log.i(TAG, "onCreate:" + TAG);

        // TODO: If exposing deep links into your app, handle intents here.
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
            Person p = new Person();
            DatabaseHandler.getInstance(this).putPerson(p);
            onItemSelected(p.id);
        }
        return result;
    }
}
