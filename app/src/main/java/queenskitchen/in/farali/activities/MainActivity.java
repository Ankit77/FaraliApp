/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package queenskitchen.in.farali.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import queenskitchen.in.farali.FaraliApp;
import queenskitchen.in.farali.R;
import queenskitchen.in.farali.adapter.NavigationAdapter;
import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.common.Utils;
import queenskitchen.in.farali.fragment.AboutUsFragment;
import queenskitchen.in.farali.fragment.FavListFragment;
import queenskitchen.in.farali.fragment.ReceipeListFragment;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity implements NavigationAdapter.MenuClickListner {

    private DrawerLayout mDrawerLayout;
    private RecyclerView rv;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private ActionBar ab;
    private FaraliApp faraliApp;
    private TextView tvTitle;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        callFragment(Const.LANG_ENG);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle=(TextView)findViewById(R.id.toolbar_titile);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        rv = (RecyclerView) findViewById(R.id.lst_menu_items);
        setupRecyclerView(rv);
        loadSearchView();
    }

    private void loadSearchView() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ReceipeListFragment receipeListFragment = (ReceipeListFragment) getSupportFragmentManager().findFragmentByTag(ReceipeListFragment.class.getSimpleName());
                if (receipeListFragment != null && receipeListFragment.isVisible()) {
                    receipeListFragment.showSearchList(query);
                } else {

                    FavListFragment favListFragment = (FavListFragment) getSupportFragmentManager().findFragmentByTag(FavListFragment.class.getSimpleName());
                    if (favListFragment != null && favListFragment.isVisible()) {
                        favListFragment.showSearchList(query);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ArrayList<String> list = new ArrayList<>();
        list.add("RECIPES IN ENGLISH");
        list.add("ગુજરાતીમાં વાનગીઓ");
        list.add("MY FAVOURITES");
        list.add("SHARE WITH FRIENDS");
        list.add("RATE THIS APPLICATION");
        list.add("VISIT WEBSITE");
        list.add("ABOUT US");

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        NavigationAdapter navigationAdapter = new NavigationAdapter(MainActivity.this,
                list);
        navigationAdapter.setOnMeniClickListner(this);
        recyclerView.setAdapter(navigationAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_share:
                Utils.shareApplication(MainActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public void onMenuClick(int position) {
        if (position == 0) {
            callFragment(Const.LANG_ENG);
        } else if (position == 1) {
            callFragment(Const.LANG_GUJ);
        } else if (position == 2) {
            callFavListFragment();
        } else if (position == 3) {
            Utils.shareApplication(MainActivity.this);
        } else if (position == 4) {
            launchMarket();
        } else if (position == 5) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://queenskitchen.in"));
            startActivity(browserIntent);
        } else if (position == 6) {
            callAboutFragment();
        }
        mDrawerLayout.closeDrawers();
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
    }

    private void callFragment(String language) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReceipeListFragment receipeListFragment = new ReceipeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("LANGUAGE", language);
        bundle.putString("ISFAV", language);
        receipeListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.viewpager, receipeListFragment, ReceipeListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void callFavListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FavListFragment favListFragment = new FavListFragment();
        fragmentTransaction.replace(R.id.viewpager, favListFragment, FavListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void callAboutFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        fragmentTransaction.replace(R.id.viewpager, aboutUsFragment, AboutUsFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }


    private void showAlertDialog(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(message);

        String positiveText = context.getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                        finish();

                    }
                });

        String negativeText = context.getString(android.R.string.no);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isSearchOpen()) {
            ReceipeListFragment receipeListFragment = (ReceipeListFragment) getSupportFragmentManager().findFragmentByTag(ReceipeListFragment.class.getSimpleName());
            if (receipeListFragment != null && receipeListFragment.isVisible()) {
                showAlertDialog(MainActivity.this, "Do you really want to close App?");
            } else {
                callFragment(Const.LANG_ENG);
            }
        } else {
            searchView.closeSearch();
        }

    }
}
