package queenskitchen.in.farali.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import queenskitchen.in.farali.R;

/**
 * Created by indianic on 29/08/16.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_test);
        TextView tvtest=(TextView)findViewById(R.id.textView);

        tvtest.setText(Html.fromHtml("<i>Pizza Base:<br><\\/i>"));
        tvtest.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
