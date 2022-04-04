package ade.yemi.growthchecker_free.Adapters

import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.*
import ade.yemi.roomdatabseapp.Data.Challenge
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.awardlayout.view.*
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


    var mAdView : AdView
    MobileAds.initialize(context) {}
    mAdView = popup.findViewById<AdView>(R.id.adView)
    val adRequest = AdRequest.Builder().build()
    mAdView.loadAd(adRequest)

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
        toshare(context, cardtoshare)
    }

}

