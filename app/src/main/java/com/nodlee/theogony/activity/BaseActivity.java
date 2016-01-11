/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nodlee.theogony.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nodlee.theogony.R;
import com.nodlee.theogony.utils.AndroidUtils;

/**
 * Created by Vernon Lee on 15-11-24.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;


    protected Toolbar initToolbar() {
        return mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected Toolbar initToolbar(int navResId, int titleResId) {
        return initToolbar(navResId, getString(titleResId));
    }

    protected Toolbar initToolbar(int navResId, String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (navResId != 0) {
            mToolbar.setNavigationIcon(navResId);
        }
        if (title != null) {
            mToolbar.setTitle(title);
        }
        setSupportActionBar(mToolbar);
        return mToolbar;
    }
}
