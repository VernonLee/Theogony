package com.nodlee.theogony.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.nodlee.theogony.R;

/**
 * Created by Vernon Lee on 15-11-26.
 */
public class AboutAppActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        getToolbar(R.drawable.ic_arrow_back_black, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
