package com.cnlms.wear;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by can on 04/04/15.
 */
public final class ViewDetailsActivity extends AppCompatActivity {

    private TextView txtViewTitle;
    private TextView txtViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        txtViewContent  = (TextView) findViewById(R.id.txt_view_content);
        txtViewTitle    = (TextView) findViewById(R.id.txt_view_title);

    }
}
