package com.example.instagramcloneapp.ModelClass

class Post {
    var postUrl:String=""
    var caption:String=""
    var uid:String=""
    var time:String=""

    //first constructor
    constructor()


    //parameterized constructor
    constructor(caption: String, postUrl: String) {
        this.caption = caption
        this.postUrl = postUrl
    }

    constructor(time: String, uName: String, caption: String, postUrl: String) {
        this.time = time
        this.uid = uid
        this.caption = caption
        this.postUrl = postUrl
    }

    //constructor



}