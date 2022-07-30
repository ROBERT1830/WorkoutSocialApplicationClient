package com.robertconstantindinescu.woutapp.feature_create_post.presentation.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

//to get the filename of an uri
fun ContentResolver.getFileName(uri: Uri): String{
    //perfomr a query to at content resolver to get our uri
    val returnCursor = query(uri, null, null, null, null) ?: return ""
    //get the index of the column where the name of our uri is saved
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    //get the fileName
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}