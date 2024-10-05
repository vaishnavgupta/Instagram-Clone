package com.example.instagramcloneapp.ModelClass

class Reels {
    var reelUrl:String=""
    var caption:String=""
    var profileLink:String?=null

    //first constructor
    constructor()


    //parameterized constructor
    constructor(caption: String, reelUrl: String) {
        this.caption = caption
        this.reelUrl = reelUrl
    }

    constructor(profileLink: String, caption: String, reelUrl: String) {
        this.profileLink = profileLink
        this.caption = caption
        this.reelUrl = reelUrl
    }


}