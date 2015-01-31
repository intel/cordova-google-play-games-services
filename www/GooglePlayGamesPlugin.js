/* Copyright (c) 2014 Intel Corporation. All rights reserved.
* Use of this source code is governed by a MIT-style license that can be
* found in the LICENSE file.
*/

var exec = require("cordova/exec");

var GooglePlayGamesPlugin = {
    authenticate: function(s, f) {
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "authenticate",[]);
    },
    connect: function(s, f) {
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "connect",[]);
    },
    logout: function(s, f) {
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "logout",[]);
    },
    showAchievements:function(s,f) {
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "achievements",[]);
    },
    addAchievement:function(achievement_id,s,f) {
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "addAchievement",[achievement_id]);
    },
	incrementAchievement:function(achievement_id,value,s,f) {
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "incrementAchievement",[achievement_id,value]);
    },
    showLeaderboard:function(leaderboard_id,s,f){
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "showLeaderboard",[leaderboard_id]);
    },
    updateLeaderboardScore:function(leaderboard_id,score,s,f){
        return cordova.exec(s, f, "GooglePlayGamesPlugin", "updateLeaderboardScore",[leaderboard_id,score]);
    }
}

module.exports = GooglePlayGamesPlugin;