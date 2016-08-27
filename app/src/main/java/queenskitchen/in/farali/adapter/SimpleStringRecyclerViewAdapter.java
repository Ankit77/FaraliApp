package queenskitchen.in.farali.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import queenskitchen.in.farali.R;
import queenskitchen.in.farali.activities.ReceipeDetailActivity;
import queenskitchen.in.farali.common.Const;
import queenskitchen.in.farali.common.Utils;
import queenskitchen.in.farali.model.RecipesModel;

/**
 * Created by indianic on 25/08/16.
 */
public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<RecipesModel> mValues;
    private String language;
    private Context mcontext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.list_item_imgImage);
            mTextView = (TextView) view.findViewById(R.id.list_item_tvTitle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public RecipesModel getValueAt(int position) {
        return mValues.get(position);
    }

    public SimpleStringRecyclerViewAdapter(Context context, List<RecipesModel> items) {
        mcontext = context;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.language = language;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBoundString = mValues.get(position).getTitle();
        if (mValues.get(position).getLanguage().equalsIgnoreCase(Const.LANG_ENG)) {
            holder.mTextView.setText(Utils.decodeUnicode(mValues.get(position).getTitle()));
        } else {
            holder.mTextView.setText(mValues.get(position).getTitle());
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ReceipeDetailActivity.class);
                intent.putExtra(ReceipeDetailActivity.RECIPE_NAME, mValues.get(position).getId());
                intent.putExtra(ReceipeDetailActivity.LANGUAGE_TYPE, mValues.get(position).getLanguage());
                mcontext.startActivity(intent);
            }
        });

        Glide.with(holder.mImageView.getContext())
                .load(mValues.get(position).getImage())
                .override(150, 150)
                .fitCenter()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
