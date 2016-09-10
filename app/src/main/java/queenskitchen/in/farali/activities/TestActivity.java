package queenskitchen.in.farali.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.apache.commons.lang.StringEscapeUtils;

import queenskitchen.in.farali.R;

/**
 * Created by indianic on 29/08/16.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_test);
        TextView tvtest=(TextView)findViewById(R.id.textView);
        String unicode = "\u0aa2\u0acb\u0a82\u0ab8\u0abe";
        String Title = StringEscapeUtils.unescapeJava(unicode);
        tvtest.setText(Title);
        tvtest.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
