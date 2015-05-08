#Cordova Google Play Games Services + iOS Game Center

This plugin is for Cordova Android apps to enable limitted Google Play Games Services API and iOS Game Center

##iOS support

iOS support has been added through a dependency of <a href="https://github.com/Wizcorp/phonegap-plugin-gameCenter">https://github.com/Wizcorp/phonegap-plugin-gameCenter</a> .  We have wrapped the API calls to match below.  See the plugin for configuration details.

##Usage

Follow the instructions here on setting up your app in Google Play Dashboard.  Make sure you have your application ID for installation.

Please see <a href="https://developers.google.com/games/services/console/enabling" target="_blank">the instructions</a> online for getting the app id.

##Short version

Go into the Google Play Developer Console -> Game Services -> Add New Game

Fill out the information and type and hit "Save".  On the next page, you will see the title and a number next to it.  That number is your App Id

##Installing plugin

```
cordova plugin add com.intel.googleplaygameservices --variable GPSAPPID=“_APPID_”
```

This will install the plugin, and dependencies in your app.  The GPSAPPID is the Application ID you get from Google Play Dashboard when authorizing your application.


To install in the Intel® XDK add the following to your intelxdk.config.additions.xml

```
<intelxdk:plugin intelxdk:name="googleplaygameservices" intelxdk:value="http://...">
    <intelxdk:param intelxdk:name="GPSAPPID" intelxdk:value="_APPID_" />
</intelxdk:plugin>

```

###API
The JavaScript API is below.  Everything is called on the GooglePlayGamesPlugin object

```
connect()                      call on device ready . This connects the native api

authenticate(success,failure)   Authenticate the user against a google play
                                account.  Will call success or failure functions.
                                If fail, a message will be passed from the google play

logout(success,failure) 		 Sign the user out of Google Play Games Services
								 for your app

addAchievement(achievement_id,success,failure)  unlock the achievement for the user

showAchievements(success,failure)  Show the achievements for the user.
									If there is an error (not logged in),
									the message will be passed in.  This is a
									new activity that gets launched

updateLeaderboardScore(leaderboard_id,score,success,failure)
									Update the score for the selected
									leaderboard for the user

showLeaderboardScore(leaderboard_id,success,failure)
                                    Show the leaderboard.
                                    If there is an error (not logged in),
                                    the message will be passed in the error.  This is a
                                    new activity that gets launched


saveGame(id,data)
                            Save a game to local storage.  Id is the key and data is JSON data
                            If no id is specficied, we use only one save game slot named "default"
                            If an entry exists, we overwrite the data

loadSavedGame(id)
                            Load a saved games data.
                            If no id is specified, we load the "default" saved game state.

deleteSavedGame(id)
                            Delete a saved game from local storage.
                            If no id is specified, we delete the "default" saved game state.

getAllSavedGames()
                            Returns an object with all the saved games.
                            The key is the id used to save.


```


###NOTES

Achievements and leaderboards are created in the Google Play Dashboard.  They will give you a string parameter for you to call the functions with.


###Testing

To test your app, you must build your application and submit it as an Alpha APK.  Next go to go to Google Play Developer Console -> Game Services  and link your application to the game service you created.  After they are linked, you can enable testing for both.

https://developers.google.com/games/services/console/testpub#enabling_accounts_for_testing

Make sure in the game service you created, you have also added the user under "Testing"


###Adding API's

This is a small subset of the Google Play Games Services API, but can easily be extended.  Feel free to fork and contribute extended the API's.  Any API's that reuqire a new activity, like logging in, should be implemented in GooglePlayGamesServices.java .  All other API's can be added to GooglePlayGamesPlugin.java

