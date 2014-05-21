package com.atmexplorer;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by m.kukushkin on 20.05.2014.
 */
public class ATMListFragment extends ListFragment {

    //data for test
    String [] data = {"one", "two", "three", "four"};

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }
}
