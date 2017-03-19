package com.nodlee.theogony.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.nodlee.theogony.R;
import com.nodlee.theogony.ui.fragment.SettingsFragment;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getToolbar(R.drawable.ic_arrow_back, null);

        Fragment fragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.frag_container, fragment)
                .commit();
    }
}
