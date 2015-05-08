/* Copyright (c) 2015 Intel Corporation. All rights reserved.
* Use of this source code is governed by a MIT-style license that can be
* found in the LICENSE file.
*/

var exec = require("cordova/exec");

var savedGames = {
    default:null,
};

if( window.localStorage.getItem("GooglePlaySavedGames")){
    try{
        savedGames=JSON.parse( window.localStorage.getItem("GooglePlaySavedGames"));
    }
    catch(e){}
}

function writeSavedGameData(){
    window.localStorage.setItem("GooglePlaySavedGames",JSON.stringify(savedGames));
}
var GooglePlayGamesPluginCore = {
    saveGame:function(id,data){
        if(!id&&!data) return;
        if(!data){
            data=id;
            id="default";
        }
        savedGames[id]=data;
        writeSavedGameData();
    },
    loadSavedGame:function(id){
        if(savedGames[id])
            return savedGames[id];
        return null;
    },
    getAllSavedGames:function(){
        return savedGames;
    },
    deleteSavedGame:function(id){
        if(savedGames[id])
            delete savedGames[id];
        writeSavedGameData();
    }
};

module.exports = GooglePlayGamesPluginCore;