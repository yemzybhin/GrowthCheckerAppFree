package ade.yemi.growthchecker.Adapters

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.*
import ade.yemi.roomdatabseapp.Data.Challenge
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.awardlayout.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import kotlin.collections.ArrayList

class Awardadapter (): RecyclerView.Adapter<Awardadapter.MyViewHolder>() {

    var items  = ArrayList<Challenge>()

    fun setListData(data: ArrayList<Challenge>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Awardadapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.awardlayout, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Awardadapter.MyViewHolder, position: Int) {

        val context = holder.itemView.context
        holder.bind(items[position])
    }

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        val awardimage = view.iv_awardimage
        val challengetext = view.tv_awardschallengetype
        val challengeimage = view.iv_awardschallengeimage
        val pointsearned = view.tv_awardpoint
        val attainable = view.tv_awardpointattainable
        val ranking = view.tv_awardranking
        val shareimage = view.shareaward

        fun bind(data: Challenge) {
            var awardpoints = getpoints(data.Point, data)
            challengetext.text = data.ChallengeType
            when(data.Days){
                "14" -> challengeimage.setImageResource(R.drawable.forteendays)
                "30" -> challengeimage.setImageResource(R.drawable.thirtydays)
                "60" -> challengeimage.setImageResource(R.drawable.sixtydays)
                "100" -> challengeimage.setImageResource(R.drawable.hundreddays)
                "200" -> challengeimage.setImageResource(R.drawable.twohundreddays)
            }
            pointsearned.text = "Total Points Earned: ${awardpoints.attained}"
            attainable.text = "Total Attainable Points: ${awardpoints.attainable}"
            ranking.text = setranking(awardpoints.attained, awardpoints.attainable)

            shareimage.setOnClickListener {
                shareimage.clicking()
                shareimage.shortvibrate()
               showMenupopup(view.context, data)
            }
            setrankimage(awardpoints.attained, awardpoints.attainable, data.Days, awardimage)
        }
    }
}

fun showMenupopup(context: Context,data: Challenge){
        var popup = Dialog(context)
        popup.setCancelable(true)
        popup.setContentView(R.layout.awardshare)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

    var challengeimage = popup.findViewById<ImageView>(R.id.aimage1)
    var challengetype = popup.findViewById<TextView>(R.id.achallengetype)
    var t1 = popup.findViewById<TextView>(R.id.at1)
    var t2 = popup.findViewById<TextView>(R.id.at2)
    var t3 = popup.findViewById<TextView>(R.id.at3)
    var awardimage = popup.findViewById<ImageView>(R.id.aimage2)
    var cancel = popup.findViewById<CardView>(R.id.acancel)
    var share = popup.findViewById<Button>(R.id.ashareimage)
    var cardtoshare = popup.findViewById<CardView>(R.id.cardtoshare)

    var awardpoints = getpoints(data.Point, data)
    challengetype.text = data.ChallengeType
    when(data.Days){
        "14" -> challengeimage.setImageResource(R.drawable.forteendays)
        "30" -> challengeimage.setImageResource(R.drawable.thirtydays)
        "60" -> challengeimage.setImageResource(R.drawable.sixtydays)
        "100" -> challengeimage.setImageResource(R.drawable.hundreddays)
        "200" -> challengeimage.setImageResource(R.drawable.twohundreddays)
    }
    t1.text = "Total Points Earned: ${awardpoints.attained}"
    t2.text = "Total Attainable Points: ${awardpoints.attainable}"
    t3.text = setranking(awardpoints.attained, awardpoints.attainable)
    setrankimage(awardpoints.attained, awardpoints.attainable, data.Days, awardimage)




    cancel.setOnClickListener {
        cancel.clicking()
        cancel.shortvibrate()
        popup.dismiss()
    }

    share.setOnClickListener {
        share.clicking()
        share.shortvibrate()
        val bitmap = getScreenShotFromView(cardtoshare, context)
        if (bitmap != null){
            saveMediaToStorage(bitmap, context)
        }
    }

}

fun saveMediaToStorage(bitmap: Bitmap, context : Context) {

    val filename = "GrowthcheckerApp-${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        MainActivity().contentResolver?.also { resolver ->

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let {
                resolver.openOutputStream(it)
            }
        }
    }else{
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }
    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        Toast.makeText(context, "Saved successfully\nImage available after device restarts.", Toast.LENGTH_LONG).show()
    }
}

fun getScreenShotFromView(v : View, context: Context): Bitmap? {
var screenshot: Bitmap? = null
    try {
        screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
        v.draw(canvas)
    }catch (e: Exception){
        Toast.makeText(context, "Could not save. Enable permission", Toast.LENGTH_SHORT).show()
    }
    return screenshot
}

