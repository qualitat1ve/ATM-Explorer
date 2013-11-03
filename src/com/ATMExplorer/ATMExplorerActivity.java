package com.ATMExplorer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ATMExplorerActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mSampleItems = {"First Item", "Second Item", "Third Item"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_content_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.top_content_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_view_item_layout,mSampleItems));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }
}
