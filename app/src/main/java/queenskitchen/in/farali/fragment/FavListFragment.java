package queenskitchen.in.farali.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import queenskitchen.in.farali.FaraliApp;
import queenskitchen.in.farali.R;
import queenskitchen.in.farali.activities.MainActivity;
import queenskitchen.in.farali.adapter.SimpleStringRecyclerViewAdapter;
import queenskitchen.in.farali.model.RecipesModel;

/**
 * Created by indianic on 27/08/16.
 */
public class FavListFragment extends Fragment {

    private FaraliApp faraliApp;
    private RecyclerView rv;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        faraliApp = (FaraliApp) getActivity().getApplicationContext();
        ((MainActivity) getActivity()).getToolbar().setTitle("Farali Recipes");
        rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ArrayList<RecipesModel> recipelist = faraliApp.getDatabaseHelper().getFavRecipeList();
        if (recipelist != null && recipelist.size() > 0) {
            simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), recipelist);
            recyclerView.setAdapter(simpleStringRecyclerViewAdapter);
        }
    }

    public void showSearchList(String keyword) {
        ArrayList<RecipesModel> recipelist = faraliApp.getDatabaseHelper().getFavRecipeListUsingSearch(keyword);
        if (recipelist != null && recipelist.size() > 0) {
            simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), recipelist);
            rv.setAdapter(simpleStringRecyclerViewAdapter);
        }
    }
}
