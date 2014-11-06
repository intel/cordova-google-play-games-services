/* Copyright (c) 2014 Intel Corporation. All rights reserved.
* Use of this source code is governed by a MIT-style license that can be
* found in the LICENSE file.
* This makes calls to the https://github.com/Wizcorp/phonegap-plugin-gameCenter plugin
*/

var exec = require("cordova/exec");

var GooglePlayGamesPlugin = {
    authenticate: function(s, f) {
        cordova.exec(s, f, "GameCenterPlugin", "authenticateLocalPlayer", []);
    },

    showLeaderboard: function(category) {
        cordova.exec(null, null, "GameCenterPlugin", "showLeaderboard", [category]);
    },

    updateLeaderboardScore: function(category, score, s, f) {
        cordova.exec(s, f, "GameCenterPlugin", "reportScore", [category, score]);
    },

    showAchievements: function() {
        cordova.exec(null, null, "GameCenterPlugin", "showAchievements", []);
    },

    addAchievement: function(category, s, f) {
        cordova.exec(s, f, "GameCenterPlugin", "reportAchievementIdentifier", [category, 100]);
    },

    logout:function(){},
    addAchievement:function(){},
    connect:function(){}
}
module.exports = GooglePlayGamesPlugin;