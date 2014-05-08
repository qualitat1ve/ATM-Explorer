package com.atmexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ATMExplorerActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerLeftList;
    private ListView mDrawerRightList;
    private String[] mSampleItems = {"First Item", "Second Item", "Third Item"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.top_content_layout);
//        mDrawerLeftList = (ListView) findViewById(R.id.left_drawer);
//        mDrawerRightList = (ListView) findViewById(R.id.right_drawer);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_item_layout, mSampleItems);
//        mDrawerRightList.setAdapter(adapter);
//        mDrawerLeftList.setAdapter(adapter);
//        mDrawerRightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mDrawerLayout.closeDrawer(mDrawerRightList);
//            }
//        });
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
    }
}
