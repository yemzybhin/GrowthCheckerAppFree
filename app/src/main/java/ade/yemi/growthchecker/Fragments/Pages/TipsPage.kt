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
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TipsPage : Fragment() {

    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_tips_page, container, false)
        var initialimage = view.findViewById<CardView>(R.id.cd_tipsinitial)

        var quotetext = view.findViewById<TextView>(R.id.tv_quote)
        var authourtext = view.findViewById<TextView>(R.id.tv_author)

        var back = view.findViewById<CardView>(R.id.cd_quoteback)
        var next = view.findViewById<CardView>(R.id.cd_quoteNext)
        var share = view.findViewById<CardView>(R.id.cd_sharequote)


        var overview = view.findViewById<CardView>(R.id.cd_overviewcard)
        var addictions = view.findViewById<CardView>(R.id.cd_addictionscard)
        var aftermath = view.findViewById<CardView>(R.id.cd_aftermathcard)

        var t1 = view.findViewById<TextView>(R.id.tv_tips1)
        var t2 = view.findViewById<TextView>(R.id.tv_tips2)
        var t3 = view.findViewById<TextView>(R.id.tv_tips3)

        var image1 = view.findViewById<ImageView>(R.id.iv_tips1)
        var image2 = view.findViewById<ImageView>(R.id.iv_tips2)
        var image3 = view.findViewById<ImageView>(R.id.iv_tips3)

        var cards = listOf<CardView>(overview, addictions, aftermath)
        var images = listOf<ImageView>(image1, image2, image3)
        var texts = listOf<TextView>(t1, t2, t3)


        var quotes = AllQuotes()

        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getInt(it, "currentquote") }
            }
            counter = pushresult1.await()!!
            quotetext.text = AllQuotes()[counter].quote
            authourtext.text = AllQuotes()[counter].Author


            next.setOnClickListener {
                next.clicking()
                next.shortvibrate()
                counter++
                if (counter > quotes.size-1){
                    counter = 0
                    quotetext.text = AllQuotes()[counter].quote
                    authourtext.text = AllQuotes()[counter].Author
                    savedata()
                }else{
                    quotetext.text = AllQuotes()[counter].quote
                    authourtext.text = AllQuotes()[counter].Author
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

        addictions.setOnClickListener {
            addictions.clicking()
            addictions.shortvibrate()
            initialimage.visibility = View.GONE
            changecolours(cards, images, texts)
            singlestate(addictions,t2, image2 )
            replacefragment(Addictions())
        }

        aftermath.setOnClickListener {
            aftermath.clicking()
            aftermath.shortvibrate()
            initialimage.visibility = View.GONE
            changecolours(cards, images, texts)
            singlestate(aftermath,t3, image3 )
            replacefragment(Aftermaths())
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
