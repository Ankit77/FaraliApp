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

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import queenskitchen.in.farali.R;
import queenskitchen.in.farali.adapter.NavigationAdapter;
import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.fragment.ReceipeListFragment;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity implements NavigationAdapter.MenuClickListner {

    private DrawerLayout mDrawerLayout;
    private RecyclerView rv;
    private  Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.lst_menu_items);
        setupRecyclerView(rv);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        callFragment(Const.LANG_ENG);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ArrayList<String> list = new ArrayList<>();
        list.add("ENGLISH RECIPES");
        list.add("GUJRATI RECIPES");
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
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_share:
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
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
        } else if (position == 3) {
        } else if (position == 4) {
        } else if (position == 5) {
        } else if (position == 6) {
        }
        mDrawerLayout.closeDrawers();
    }

    private void callFragment(String language) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReceipeListFragment receipeListFragment = new ReceipeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("LANGUAGE", language);
        receipeListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.viewpager, receipeListFragment, ReceipeListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
