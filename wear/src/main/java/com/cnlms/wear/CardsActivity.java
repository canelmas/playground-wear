package com.cnlms.wear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by can on 05/05/15.
 */
public class CardsActivity extends Activity implements View.OnClickListener {

    public static Intent newIntent(final Context context) {
        return new Intent(context, CardsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // shape aware layout i.e. BoxInLayout
        setContentView(R.layout.activity_cards);

        findViewById(R.id.btn_card_fragment).setOnClickListener(this);
        findViewById(R.id.btn_card_scrollview).setOnClickListener(this);
        findViewById(R.id.btn_card_listview).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_card_fragment:
                startActivity(CardFragmentActivity.newIntent(this));
                break;

            case R.id.btn_card_scrollview:
                startActivity(CardScrollViewActivity.newnIntent(this));
                break;

            case R.id.btn_card_listview:
                startActivity(ListActivity.newIntent(this));
                break;

            default:
                break;
        }

    }
}
