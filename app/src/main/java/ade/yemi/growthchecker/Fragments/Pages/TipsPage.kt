package ade.yemi.growthchecker.Fragments.Pages

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
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.setOnSingleClickListener
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
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