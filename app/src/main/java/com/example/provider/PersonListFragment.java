package com.example.provider;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.provider.database.Person;
import com.example.provider.database.PersonProvider;

public class PersonListFragment extends ListFragment {

    public interface Callbacks {
        void onItemSelected(long l);
    }

    public static final String TAG = PersonListFragment.class.getName();

    private Callbacks dummyCB = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
            Log.i(TAG, "id=" + id);
        }
    };
    private Callbacks cb = dummyCB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new SimpleCursorAdapter(getActivity(),
                R.layout.person_listitem, null, new String[]{
                Person.COL_FIRSTNAME, Person.COL_LASTNAME,
                Person.COL_BIRTH}, new int[]{R.id.cardFirstName,
                R.id.cardLastName, R.id.cardDescription}, 0));

        // Load the content
        getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(getActivity(),
                        PersonProvider.URI_PERSONS, Person.FIELDS, null, null,
                        null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
                ((SimpleCursorAdapter) getListAdapter()).swapCursor(c);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> arg0) {
                ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        } else {
            cb = (Callbacks) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cb = dummyCB;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);
        cb.onItemSelected(getListAdapter().getItemId(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
