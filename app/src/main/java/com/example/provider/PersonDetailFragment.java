package com.example.provider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.provider.database.DatabaseHandler;
import com.example.provider.database.Person;

public class PersonDetailFragment extends Fragment {

    public static final String TAG = PersonDetailFragment.class.getName();

    public static final String ARG_ITEM_ID = "item_id";

    private Person person;

    private TextView textFirstName;
    private TextView textLastName;
    private TextView textBirthday;

    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Should use the contentprovider here ideally
            person = DatabaseHandler.getInstance(getActivity()).getPerson(getArguments().getLong(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        if (person != null) {
            textFirstName = ((TextView) rootView.findViewById(R.id.textFirstName));
            textFirstName.setText(person.getFirstName());

            textLastName = ((TextView) rootView.findViewById(R.id.textLastName));
            textLastName.setText(person.getLastName());

            textBirthday = ((TextView) rootView.findViewById(R.id.textBio));
            textBirthday.setText(person.getBirth());
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        updatePersonFromUI();
    }

    private void updatePersonFromUI() {
        if (person != null) {
            person.setFirstName(textFirstName.getText().toString());
            person.setLastName(textLastName.getText().toString());
            person.setBirth(textBirthday.getText().toString());
            DatabaseHandler.getInstance(getActivity()).putPerson(person);
        }
    }
}
