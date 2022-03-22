package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Adapters.getScreenShotFromView
import ade.yemi.growthchecker.Adapters.saveMediaToStorage
import ade.yemi.growthchecker.Data.AllQuotes
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.subpages.*
import ade.yemi.growthchecker.Fragments.Pages.subpages.checks.analytic1check
import ade.yemi.growthchecker.Fragments.Pages.subpages.checks.analytic2check
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.*
import ade.yemi.roomdatabseapp.Data.Challenge
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.FocusFinder
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule

class TipsPage : Fragment() {

    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_tips_page, container, false)
        var initialimage = view.findViewById<CardView>(R.id.cd_tipsinitial)

        var scrolltotop = view.findViewById<LottieAnimationView>(R.id.scrolltotopview)
        var scrollView = view.findViewById<NestedScrollView>(R.id.tipspagescroll)

        var quotetext = view.findViewById<TextView>(R.id.tv_quote)
        var authourtext = view.findViewById<TextView>(R.id.tv_author)

        var back = view.findViewById<CardView>(R.id.cd_quoteback)
        var next = view.findViewById<CardView>(R.id.cd_quoteNext)
        var share = view.findViewById<CardView>(R.id.cd_sharequote)
        var shareimae = view.findViewById<CardView>(R.id.cd_sharequoteimage)


        var overview = view.findViewById<CardView>(R.id.cd_overviewcard)
        var addictions = view.findViewById<CardView>(R.id.cd_addictionscard)

        var t1 = view.findViewById<TextView>(R.id.tv_tips1)
        var t2 = view.findViewById<TextView>(R.id.tv_tips2)

        var image1 = view.findViewById<ImageView>(R.id.iv_tips1)
        var image2 = view.findViewById<ImageView>(R.id.iv_tips2)

        var cards = listOf<CardView>(overview, addictions)
        var images = listOf<ImageView>(image1, image2)
        var texts = listOf<TextView>(t1, t2)



        scrolltotop.visibility = View.GONE

        var quotes = AllQuotes()

        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getInt(it, "currentquote") }
            }
            counter = pushresult1.await()!!
            quotetext.text = AllQuotes()[counter].quote
            authourtext.text ="-${AllQuotes()[counter].Author}"


            next.setOnClickListener {
                next.clicking()
                next.shortvibrate()
                counter++
                if (counter > quotes.size-1){
                    counter = 0
                    quotetext.text = AllQuotes()[counter].quote
                    authourtext.text = "-${AllQuotes()[counter].Author}"
                    savedata()
                }else{
                    quotetext.text = AllQuotes()[counter].quote
                    authourtext.text = "-${AllQuotes()[counter].Author}"
                    savedata()
                }
            }
            back.setOnClickListener {
                back.clicking()
                back.shortvibrate()
                counter--
                if (counter < 0){
                    counter = quotes.size-1
                    quotetext.text = AllQuotes()[counter].quote
                    authourtext.text = AllQuotes()[counter].Author
                    savedata()
                }else{
                    quotetext.text = AllQuotes()[counter].quote
                    authourtext.text = AllQuotes()[counter].Author
                    savedata()
                }

            }

            share.setOnClickListener {
                share.clicking()
                share.shortvibrate()
                Toast.makeText(requireContext(), "Please Wait", Toast.LENGTH_LONG).show()
                val word = "${quotetext.text} \nby ${authourtext.text}. \n\nSee More on the Growth chacker app via https://play.google.com/store/apps/details?id=ade.yemi.growthchecker"
                val shareintent = Intent()
                shareintent.action = Intent.ACTION_SEND
                shareintent.type = "text/plain"
                shareintent.putExtra(Intent.EXTRA_TEXT,word )
                startActivity(Intent.createChooser(shareintent, "Share Quote Via"))
            }

            shareimae.setOnClickListener {
                shareimae.clicking()
                shareimae.shortvibrate()
                shareimage(AllQuotes()[counter].quote, AllQuotes()[counter].Author)
            }
        }

        overview.setOnClickListener {
            overview.clicking()
            overview.shortvibrate()

                initialimage.visibility = View.GONE
                changecolours(cards, images, texts)
                singlestate(overview,t1, image1 )
                replacefragment(Overview())
        }

        scrolltotop.setOnClickListener {
            scrolltotop.clicking()
            scrolltotop.shortvibrate()
           scrollView.smoothScrollTo(0,0)
        }


        scrollView.viewTreeObserver.addOnScrollChangedListener {
             var scrolly = scrollView.scrollY

            if (scrolly > 300){
                scrolltotop.visibility = View.VISIBLE
            }else{
                scrolltotop.visibility = View.GONE
            }
        }


        addictions.setOnClickListener {
            addictions.clicking()
            addictions.shortvibrate()
                initialimage.visibility = View.GONE
                changecolours(cards, images, texts)
                singlestate(addictions,t2, image2 )
                replacefragment(Addictions())
        }

        return view
    }
    private fun shareimage(quote1: String, author1: String){
        var popup = Dialog(requireContext())
        popup.setCancelable(true)
        popup.setContentView(R.layout.sharequotee)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        var quote = popup.findViewById<TextView>(R.id.quoteshare)
        var author = popup.findViewById<TextView>(R.id.authorshare)
        var share = popup.findViewById<Button>(R.id.quotesharebutton)
        var cardtoshare = popup.findViewById<CardView>(R.id.cardquotetoshare)
        var cancel = popup.findViewById<CardView>(R.id.quotecancel)

        quote.text = quote1
        author.text = "-$author1"
        cancel.setOnClickListener {
            cancel.clicking()
            cancel.shortvibrate()
            popup.dismiss()
        }

        share.setOnClickListener {
            share.clicking()
            share.shortvibrate()
            val bitmap = getScreenShotFromV(cardtoshare)
            if (bitmap != null){
                saveMediaToStore(bitmap)
            }
        }

    }

    private fun saveMediaToStore(bitmap: Bitmap) {

        val filename = "GrowthcheckerApp-quote-${System.currentTimeMillis()}.jpg"
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
            Toast.makeText(requireContext(), "Saved successfully\nImage available after device restarts.", Toast.LENGTH_LONG).show()
        }
    }

    private fun getScreenShotFromV(v : View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        }catch (e: Exception){
            Toast.makeText(requireContext(), "Could not save. Enable permission", Toast.LENGTH_SHORT).show()
        }
        return screenshot
    }

    private fun changecolours(cards: List<CardView>, images: List<ImageView>, texts: List<TextView> ){
        for (i in cards){
            i.setCardBackgroundColor(resources.getColor(R.color.white))
        }
        for (i in images){
            i.setImageResource(R.drawable.arrow)
        }
        for (i in texts){
            i.setTextColor(ContextCompat.getColor(requireContext(), R.color.primarycolor2))
        }
    }

    private fun singlestate(card: CardView, text:TextView, image: ImageView){
        card.setCardBackgroundColor(resources.getColor(R.color.primarycolor2))
        text.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        image.setImageResource(R.drawable.arrow6)
    }


    private fun replacefragment(fragment: Fragment){
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fr_tipspagefrag, fragment)
            fragmentTransaction.commit()
    }

    private fun savedata(){
        lifecycleScope.launch {
            context?.let { DataStoreManager.saveInt(it, "currentquote", counter) }
        }
    }
}