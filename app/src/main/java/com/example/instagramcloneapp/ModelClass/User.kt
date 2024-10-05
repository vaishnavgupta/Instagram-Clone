package com.example.instagramcloneapp.ModelClass

class User {
    var image:String?=null
    var name:String?=null
    var email:String?=null
    var password:String?=null
    constructor()
    constructor(password: String?,image: String?, name: String?, email: String) {
        this.password = password
        this.image = image
        this.name = name
        this.email = email
    }
    constructor(password: String?, name: String?, email: String) {
        this.password = password
        this.name = name
        this.email = email
    }
    //constructor required for login activity
    constructor(password: String?, email: String) {
        this.password = password
        this.email = email
    }


}