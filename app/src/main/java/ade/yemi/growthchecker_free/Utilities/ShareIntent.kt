package ade.yemi.growthchecker_free.Utilities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun toshare(context: Context, card: CardView){
    Toast.makeText(context, "Please Wait!", Toast.LENGTH_SHORT).show()
    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
    val currentDateandTime = sdf.format(Date())
    val imageName = "/image_$currentDateandTime.jpg"
    val bitmap = getScreenShotFromV(context, card)

    // SAVE
    try {
        File(context.cacheDir, "images").deleteRecursively() // delete old images
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs() // don't forget to make the directory
        val stream = FileOutputStream("$cachePath$imageName")
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream) // can be png and any quality level
        stream.close()
    } catch (ex: Exception) {
        Toast.makeText(context, ex.javaClass.canonicalName, Toast.LENGTH_LONG).show() // You can replace this with Log.e(...)
    }

    // SHARE
    val imagePath = File(context.cacheDir, "images")
    val newFile = File(imagePath, imageName)
    val contentUri = FileProvider.getUriForFile(context, "ade.yemi.growthchecker_free.provider", newFile)
    if (contentUri != null) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
        shareIntent.type = "image/jpeg" // just assign type. we don't need to set data, otherwise intent will not work properly
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(context, Intent.createChooser(shareIntent, "Choose app") , Bundle())
    }
}
fun getScreenShotFromV(context: Context, v : View): Bitmap? {
    var screenshot: Bitmap? = null
    try {
        screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
        v.draw(canvas)
    }catch (e: Exception){
        Toast.makeText(context, "Could not capture view. Try again.", Toast.LENGTH_SHORT).show()
    }
    return screenshot
}
