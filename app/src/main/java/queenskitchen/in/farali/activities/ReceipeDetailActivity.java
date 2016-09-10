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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import queenskitchen.in.farali.FaraliApp;
import queenskitchen.in.farali.R;
import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.model.RecipesModel;


public class ReceipeDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String RECIPE_NAME = "recipe_name";
    public static final String LANGUAGE_TYPE = "lan_type";
    private String recipeId;
    private RecipesModel recipesModel;
    private FaraliApp faraliApp;
    private TextView tvCookingTime;
    private TextView tvServing;
    private TextView tvPreparation;
    private TextView tvLevel;
    private TextView tvIngredients;
    private TextView tvMethod;
    private TextView tvIngredientslbl;
    private TextView tvMethodlbl;
    private FloatingActionButton fabFav;
    private FloatingActionButton fabShare;
    private ImageView imgRecipe;
    private String language;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        faraliApp = (FaraliApp) getApplicationContext();
        init();
        if (getIntent() != null) {
            Intent intent = getIntent();
            recipeId = intent.getStringExtra(RECIPE_NAME);
            language = intent.getStringExtra(LANGUAGE_TYPE);
            final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_recipe_detail_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            if (!TextUtils.isEmpty(recipeId)) {
                recipesModel = faraliApp.getDatabaseHelper().getRecipeDetail(recipeId);
                if (recipesModel != null) {
                    final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lohit-Gujarati.ttf");
                    collapsingToolbar.setCollapsedTitleTypeface(font);
                    collapsingToolbar.setExpandedTitleTypeface(font);
                    collapsingToolbar.setTitle(recipesModel.getTitle());
                    loadBackdrop(recipesModel);
                }

            }
        }

    }

    private void init() {
        tvCookingTime = (TextView) findViewById(R.id.activity_recipe_detail_tvcookingtime);
        tvServing = (TextView) findViewById(R.id.activity_recipe_detail_tvserving);
        tvLevel = (TextView) findViewById(R.id.activity_recipe_detail_tvLevel);
        tvPreparation = (TextView) findViewById(R.id.activity_recipe_detail_tvpreproration);
        tvIngredients = (TextView) findViewById(R.id.activity_recipe_detail_tvingredients);
        tvMethod = (TextView) findViewById(R.id.activity_recipe_detail_tvpreparation);
        tvIngredientslbl = (TextView) findViewById(R.id.activity_recipe_detail_tvingredients_lbl);
        tvMethodlbl = (TextView) findViewById(R.id.activity_recipe_detail_tvpreparation_lbl);
        fabFav = (FloatingActionButton) findViewById(R.id.activity_recipe_detail_fabfav);
        fabShare = (FloatingActionButton) findViewById(R.id.activity_recipe_detail_fabShare);
        imgRecipe = (ImageView) findViewById(R.id.activity_recipe_detail_imgrecipeimage);
        fabFav.setOnClickListener(this);
        fabShare.setOnClickListener(this);
    }

    private void loadBackdrop(RecipesModel recipesModel) {
        Glide.with(this).load(recipesModel.getImage()).fitCenter().into(imgRecipe);
        if (recipesModel.getLanguage().equalsIgnoreCase(Const.LANG_ENG)) {
            tvCookingTime.setText("Cooking Time : " + recipesModel.getCookingtime());
            tvServing.setText("Servings : " + recipesModel.getServings());
            tvPreparation.setText("Pre-properation : " + recipesModel.getPreproperation());
            tvLevel.setText("Level of Cooking : " + recipesModel.getLevel());
            tvIngredientslbl.setText("Ingredients");
            tvMethodlbl.setText("Preparation Method");
        } else {
            tvCookingTime.setText("બનાવવામાં લાગતો સમય : " + recipesModel.getCookingtime());
            tvServing.setText("સર્વીગ : " + recipesModel.getServings());
            tvPreparation.setText("પુર્વતૈયારી : " + recipesModel.getPreproperation());
            tvLevel.setText("લેવલ ઓફ કુકીંગ : " + recipesModel.getLevel());
            tvIngredientslbl.setText("સામગ્રી");
            tvMethodlbl.setText("બનાવવાની રીત");
        }
        String ingredients = recipesModel.getIngredients().replaceAll("\\\\", "");
        tvIngredients.setText(Html.fromHtml(ingredients));
        String method = recipesModel.getContent().replaceAll("\\\\", "");
        tvMethod.setText(Html.fromHtml(method));
        tvMethod.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Only handle with DrawerToggle if the drawer indicator is enabled.

        // Handle action buttons
        switch (item.getItemId()) {
            // Handle home button in non-drawer mode
            case android.R.id.home:
                onBackPressed();
                return true;
//            case R.id.action_share:
//                Utils.shareApplication(ReceipeDetailActivity.this);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == fabFav) {
            if (recipesModel.getIsfav() == Const.FAV) {
                recipesModel.setIsfav(Const.UNFAV);
                Snackbar.make(v, recipesModel.getTitle() + " is removed from your favouritelist !!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                recipesModel.setIsfav(Const.FAV);
                Snackbar.make(v, recipesModel.getTitle() + " is added from your favouritelist !!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            faraliApp.getDatabaseHelper().updateContact(recipeId, recipesModel.getIsfav());

        } else if (v == fabShare) {
            shareRecipe();
        }
    }


    public void shareRecipe() {
        if (recipesModel != null) {
            String message = "i want to share really amazing Farali Recipe with you : " + recipesModel.getLink();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    message);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
}
