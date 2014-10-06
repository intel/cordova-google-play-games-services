/* Copyright (c) 2014 Intel Corporation. All rights reserved.
* Use of this source code is governed by a MIT-style license that can be
* found in the LICENSE file.
*/

package com.intel;


import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import com.google.example.games.basegameutils.BaseGameActivity;


public class GooglePlayGamesService extends BaseGameActivity {

    @Override
    public void onSignInFailed() {
        // Sign-in failed
    	Intent i = new Intent("com.intel.googleplaygamesservice.loginerrror");
    	setResult(1,i);
    	finish();
    }

    @Override
    public void onSignInSucceeded() {

		Intent i = new Intent("com.intel.googleplaygamesservice.loginsuccess");
		setResult(-1,i);
		finish();
    }

    public void onCreate(Bundle b)
    {
    	super.onCreate(b);
    }



    public void beginUserInitiatedSignIn() {
        super.beginUserInitiatedSignIn();
    }
}
