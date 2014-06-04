package com.atmexplorer;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.atmexplorer.adapter.ATMItemAdapter;
import com.atmexplorer.model.ATMItem;

import java.util.ArrayList;

/**
 * Created by m.kukushkin on 20.05.2014.
 */
public class ATMListFragment extends ListFragment {

    private ArrayList<ATMItem> mItemsList = new ArrayList<ATMItem>();
    private OnItemSelectedListener mCallback;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.atm_list_fragment_layout, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillData();

        ATMItemAdapter adapter = new ATMItemAdapter(getActivity().getApplicationContext(), mItemsList);
        setListAdapter(adapter);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnItemSelectedListener");
        }
    }

    private void fillData() {
        mItemsList.add(new ATMItem("ул. Богдана Хмельницкого, 10", "Дельта", R.drawable.delta));
        mItemsList.add(new ATMItem("ул. Горького, 5", "Креди Агриколь", R.drawable.credit_agricole));
        mItemsList.add(new ATMItem("ул. Киевская, 1 Б", "Кредит Пром", R.drawable.credit_prom));
        mItemsList.add(new ATMItem("ул. Центральная, 1", "Золотые ворота", R.drawable.golden_gate));
        mItemsList.add(new ATMItem("ул. Шевченко, 33", "Киев", R.drawable.kiev));
        mItemsList.add(new ATMItem("ул. Киевская, 24", "Легбанк", R.drawable.leg_bank));

        mItemsList.add(new ATMItem("ул. Богдана Хмельницкого, 10", "Дельта", R.drawable.delta));
        mItemsList.add(new ATMItem("ул. Горького, 5", "Креди Агриколь", R.drawable.credit_agricole));
        mItemsList.add(new ATMItem("ул. Киевская, 1 Б", "Кредит Пром", R.drawable.credit_prom));
        mItemsList.add(new ATMItem("ул. Центральная, 1", "Золотые ворота", R.drawable.golden_gate));
        mItemsList.add(new ATMItem("ул. Шевченко, 33", "Киев", R.drawable.kiev));
        mItemsList.add(new ATMItem("ул. Киевская, 24", "Легбанк", R.drawable.leg_bank));
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallback.onItemSelected(mItemsList.get(position).getIconId(), mItemsList.get(position).getBankName());
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(int iconId, String bankName);
    }
}
