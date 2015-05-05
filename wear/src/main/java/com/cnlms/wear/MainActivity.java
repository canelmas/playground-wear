package com.cnlms.wear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  watch view stub i.e. different layouts based on device specs
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                stub.findViewById(R.id.btn_cards).setOnClickListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "wow..", Toast.LENGTH_SHORT).show();

        startActivity(CardsActivity.newIntent(this));
    }
}
