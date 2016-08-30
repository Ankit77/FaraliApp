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

package queenskitchen.in.farali.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import queenskitchen.in.farali.FaraliApp;
import queenskitchen.in.farali.R;
import queenskitchen.in.farali.activities.MainActivity;
import queenskitchen.in.farali.adapter.SimpleStringRecyclerViewAdapter;
import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.model.RecipesModel;

public class ReceipeListFragment extends Fragment {

    private String type;
    private FaraliApp faraliApp;
    private RecyclerView rv;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    private TextView tvEmpty;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("LANGUAGE", Const.LANG_ENG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        faraliApp = (FaraliApp) getActivity().getApplicationContext();
        if(type.equalsIgnoreCase(Const.LANG_ENG)) {
            ((MainActivity) getActivity()).getToolbar().setTitle("Farali Recipes");
        }else
        {
            ((MainActivity) getActivity()).getToolbar().setTitle("ફરાળી વાનગીઓ");
        }

        view = inflater.inflate(R.layout.fragment_cheese_list, null);
        rv = (RecyclerView) view.findViewById(R.id.recyclerview);
        tvEmpty = (TextView) view.findViewById(R.id.tvempty);
        setupRecyclerView(rv);
        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ArrayList<RecipesModel> recipelist = faraliApp.getDatabaseHelper().getRecipeList(type);
        if (recipelist != null && recipelist.size() > 0) {
            simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), recipelist);
            recyclerView.setAdapter(simpleStringRecyclerViewAdapter);
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setText("No item available");
        }
    }

    public void showSearchList(String keyword) {
        ArrayList<RecipesModel> recipelist = faraliApp.getDatabaseHelper().getRecipeListUsingSearch(type, keyword);
        if (recipelist != null && recipelist.size() > 0) {
            simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), recipelist);
            rv.setAdapter(simpleStringRecyclerViewAdapter);
            tvEmpty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
            tvEmpty.setText("No item available");
        }
    }
}
