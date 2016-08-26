package queenskitchen.in.farali.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import queenskitchen.in.farali.R;

/**
 * Created by ANKIT on 8/21/2016.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private List<String> mValues;
    private MenuClickListner onMeniClickListner;

    public void setOnMeniClickListner(MenuClickListner onMeniClickListner) {
        this.onMeniClickListner = onMeniClickListner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.nav_list_item_tvTitle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }


    public NavigationAdapter(Context context, List<String> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mTextView.setText(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (onMeniClickListner != null) {
                    onMeniClickListner.onMenuClick(position);
                }
//                Intent intent = new Intent(context, ReceipeDetailActivity.class);
//                intent.putExtra(ReceipeDetailActivity.EXTRA_NAME, holder.mBoundString);
//                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface MenuClickListner {
        public void onMenuClick(int position);
    }
}
