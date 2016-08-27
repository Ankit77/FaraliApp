package queenskitchen.in.farali.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ayar.oktay.advancedtextview.AdvancedTextView;
import queenskitchen.in.farali.R;
import queenskitchen.in.farali.activities.MainActivity;

/**
 * Created by ANKIT on 8/27/2016.
 */
public class AboutUsFragment extends Fragment {

    String content = "India is a land of diversity. One can observe variations in culture, language, cuisine etc. Especially Indian cuisine is like a rainbow of different tastes and flavours. We here at Queen's Kitchen try to create a platform where one can have access to various Vegetarian Indian cuisines like Gujarati, Marathi, Rajasthani, Punjabi etc. Vegetarian cuisine holds a special place in food Industry. It is healthy, tasty and full of flavours.\n" +
            "\n" +
            "Through this Queen’s Kitchen Application you can easily learn to how to make different type of  Farali recipes, for you and your love ones.\n" +
            "\n" +
            "For Help and Support : mail@queenskitchen.in\n" +
            "\n" +
            "Application Version : <app version>\n" +
            "\n" +
            "All content and images on this application - unless stated otherwise - are © 2015 - <currnet year> queenskitchen.in.\n";
    private AdvancedTextView tvContent;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aboutus, null);
        ((MainActivity) getActivity()).getToolbar().setTitle("About");
        tvContent = (AdvancedTextView) view.findViewById(R.id.fragment_aboutus_tvContent);
        tvContent.setText(content);
        return view;
    }
}
