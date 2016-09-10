package queenskitchen.in.farali.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by indianic on 09/09/16.
 */
public class CustomeTextView extends TextView {


    public CustomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

        final Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Lohit-Gujarati.ttf");
        setTypeface(font);

    }
}
