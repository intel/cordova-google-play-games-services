/* Copyright (c) 2014 Intel Corporation. All rights reserved.
* Use of this source code is governed by a MIT-style license that can be
* found in the LICENSE file.
*/

package com.intel;

import com.intel.GooglePlayGamesService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.Games.GamesOptions;


/**
 *
 * @author Ian Maffet
 * @copyright 2014
 * @file ggamesPlugin for Cordova
 *
 */
public class GooglePlayGamesPlugin extends CordovaPlugin {

    private String TAG = "GooglePlayGamesPlugin";
    private GooglePlayGamesService playGamesService;
    private static final int SIGNIN_ACTIVITY=1;


	private CallbackContext cb;


    /**
     * strings for actions from the plugin
     */
    public static final String PLAY_SERVICES_MESSAGE="com.intel.googleplayservices.action";
    public static final String PLAY_SERVICES_LOGIN="com.intel.googleplayservices.login";
    public static final String PLAY_SERVICES_LOGOUT="com.intel.googleplayservices.logout";
    private static int REQUEST_ACHIEVEMENTS=1001;
    private static int REQUEST_LEADERBOARD=1002;

    private Activity mainActivity;
    private GoogleApiClient mGoogleApiClient;

    GamesOptions mGamesApiOptions = GamesOptions.builder().build();

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
      super.initialize(cordova, webView);

      mainActivity = cordova.getActivity();
      getApiClient();
    }

    protected GoogleApiClient getApiClient(){
    	if(!isSignedIn()){
    		 mGoogleApiClient = new GoogleApiClient.Builder(mainActivity)
    		 .addApi(Games.API, mGamesApiOptions)
             .addScope(Games.SCOPE_GAMES)
             .build();
    		 mGoogleApiClient.connect();
    	}
    	return mGoogleApiClient;

    }

    public boolean isSignedIn() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        String intentAction="";
        // Check for compatible Google Play services APK
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.cordova.getActivity()) != 0) {
            // Google Play Services is missing, return error
            Log.e(TAG, "Google Play Services are unavailable");
            callbackContext.error(GGSError.UNAVAILABLE);
            return true;
        } else {
            Log.d(TAG, "** Google Play Services are available **");
        }

        if (playGamesService == null) {
            playGamesService = new GooglePlayGamesService();
        }
        cb=callbackContext;

        if(action.equals("authenticate")){
        	if(isSignedIn()){
        		cb.sendPluginResult(new PluginResult(PluginResult.Status.OK,"-1"));
        		return true;
        	}
        	getApiClient();//Make the connection here
            intentAction=PLAY_SERVICES_LOGIN;
            Context context=this.cordova.getActivity().getApplicationContext();        //
            Intent intent=new Intent(context,GooglePlayGamesService.class);
            intent.putExtra(PLAY_SERVICES_MESSAGE, intentAction);
            cordova.startActivityForResult(this, intent, SIGNIN_ACTIVITY);
            return true;
        }
        else if(action.equals("logout"))
        {
        	if(mGoogleApiClient!=null||mGoogleApiClient.isConnected()){
        		getApiClient().disconnect();
        	}

        	cb.sendPluginResult(new PluginResult(PluginResult.Status.OK,"-1"));
    		return true;
        }
        else if(action.equals("achievements")){
        	if(!isSignedIn()){
        		cb.sendPluginResult(new PluginResult(PluginResult.Status.ERROR,"1"));
        		return true;
        	}
        	cordova.startActivityForResult(this,Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);
        	return true;
        }
        else if(action.equals("addAchievement")){
        	if(!isSignedIn()){
        		cb.sendPluginResult(new PluginResult(PluginResult.Status.ERROR,"1"));
        		return true;
        	}
        	if(args.length()==0) return false;
    		String achievementId="";
    		try {
    			achievementId=args.get(0).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return false;
			}
    		Games.Achievements.unlock(getApiClient(), achievementId);
    		cb.sendPluginResult(new PluginResult(PluginResult.Status.OK,"-1"));
    		return true;
        }
        else if(action.equals("showLeaderboard"))
        {
        	if(!isSignedIn()){
        		cb.sendPluginResult(new PluginResult(PluginResult.Status.ERROR,"1"));
        		return true;
        	}
        	if(args.length()==0) return false;
    		String leaderboardId="";
    		try {
    			leaderboardId=args.get(0).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return false;
			}
    		cordova.startActivityForResult(this,Games.Leaderboards.getLeaderboardIntent(getApiClient(), leaderboardId), REQUEST_LEADERBOARD);
        }
        else if(action.equals("updateLeaderboardScore")){
        	if(!isSignedIn()){
        		cb.sendPluginResult(new PluginResult(PluginResult.Status.ERROR,"1"));
        		return true;
        	}
        	if(args.length()==0) return false;
    		String leaderboardId="";
    		Long score;
    		try {
    			leaderboardId=args.get(0).toString();
    			score=Long.parseLong(args.get(1).toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return false;
			}
    		Games.Leaderboards.submitScore(getApiClient(), leaderboardId, score);
             cb.sendPluginResult(new PluginResult(PluginResult.Status.OK,"-1"));
             return true;
        }
        else if(action.equals("connect"))
        {
        	cb.sendPluginResult(new PluginResult(PluginResult.Status.OK,"-1"));
        	return true;
        }

        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    	cb.sendPluginResult(new PluginResult(PluginResult.Status.OK,Integer.toString(resultCode)));
    }
}

// A simple static error handler, prevent us messing up string names manually
final class GGSError {
    // When Google Play is not installed on device return this error
    public static String UNAVAILABLE = "UNAVAILABLE";

}
