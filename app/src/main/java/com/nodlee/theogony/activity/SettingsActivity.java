package com.nodlee.theogony.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.nodlee.theogony.R;
import com.nodlee.theogony.fragment.SettingsFragment;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getToolbar(R.drawable.ic_arrow_back_black, null);

        Fragment fragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.frag_container, fragment)
                .commit();
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
