package com.atmexplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.SearchView;
import com.atmexplorer.model.ATMItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 */

public class MainActivity extends Activity implements GoogleMapFragment.OnMapReadyListener {

    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ATMListFragment mATMListFragment;
    private MapFragment mMapFragment;
    private View mDrawerMenu;
    private LocationTracker mLocationTracker;
    private boolean isSearchActive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMapFragment = new GoogleMapFragment(this);
        mMapFragment.setRetainInstance(true);
        mLocationTracker = new LocationTracker(getApplicationContext());
        mATMListFragment = new ATMListFragment();
        Button mapButton = (Button)findViewById(R.id.show_on_map);
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mATMListFragment);
            fragmentTransaction.commit();
        }

        setUpActionBar();
        setUpDrawer();

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mMapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void setUpGoogleMap() {
        GoogleMap mGoogleMap = mMapFragment.getMap();
        if (mGoogleMap == null) {
            return;
        }
        double latitude = mLocationTracker.getLatitude();
        double longitude = mLocationTracker.getLongitude();

        for (ATMItem item : mATMListFragment.getSelectedItems()) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).
                    title(item.getBankName() + ", " + item.getAddress());
            mGoogleMap.addMarker(marker);
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        CameraPosition position = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).
                zoom(GoogleMapFragment.ZOOM_LEVEL).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private void setUpDrawer() {
        mDrawerMenu = findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open_title, R.string.drawer_close_title) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mActionBar.setIcon(getResources().getDrawable(R.drawable.ic_logo));
                mActionBar.setTitle(getString(R.string.drawer_close_title));
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                mActionBar.setTitle(getString(R.string.drawer_open_title));
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpActionBar() {
        mActionBar = getActionBar();
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        mATMListFragment.doSearch(query);
        isSearchActive = true;
    }

    public LocationTracker getLocationTracker() {
        return mLocationTracker;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                if (isSearchActive) {
                    ATMListFragment fragment = (ATMListFragment) getFragmentManager().findFragmentById(R.id.fragment_container);
                    fragment.onBackPressed();
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerMenu);
        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_show_on_map:
                mActionBar.hide();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mMapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onBackPressed() {
        if(!mActionBar.isShowing()){
            mActionBar.show();
        }
        if (mDrawerLayout.isDrawerOpen(mDrawerMenu)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady() {
        setUpGoogleMap();
    }
}
