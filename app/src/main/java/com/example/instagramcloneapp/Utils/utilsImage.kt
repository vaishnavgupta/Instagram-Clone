package com.example.instagramcloneapp.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


//creating a function that takes uri from gallery and store it in Storage in folderName
//this function will also return a url of the image that we have uploaded

fun uploadImage(uri: Uri,folderName:String,callback:(String?)->Unit){
    var imgUrl:String?=null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imgUrl=it.toString()
                callback(imgUrl)
            }
        }
}

fun uploadVideo(uri: Uri, folderName:String, progressDialog: ProgressDialog ,callback:(String?)->Unit){
    var imgUrl:String?=null
    progressDialog.setTitle("Uploading . . .")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imgUrl=it.toString()
                progressDialog.dismiss()
                callback(imgUrl)
            }
        }
        .addOnProgressListener {
            val uploadedValue:Long=(it.bytesTransferred/it.totalByteCount)*100
            progressDialog.setMessage("Uploaded $uploadedValue %")
        }
}