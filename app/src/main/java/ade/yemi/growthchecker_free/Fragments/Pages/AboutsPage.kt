package ade.yemi.growthchecker_free.Fragments.Pages


import ade.yemi.growthchecker_free.Activities.Activity2
import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Adapters.MoreAppsAdapter
import ade.yemi.growthchecker_free.Data.Preferencestuff
import ade.yemi.growthchecker_free.Fragments.AllAds
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.CheckEmpty
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import ade.yemi.moreapps.Network.RetrofitInterface
import ade.yemi.moreapps.Network.RetrofitInterface1
import ade.yemi.moreapps.models.AllAppDetails
import ade.yemi.moreapps.models.AppContent
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.billingclient.api.*
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val AD_UNIT_ID = "ca-app-pub-2144911759176506~5003122422"

class AboutsPage : Fragment() , PurchasesUpdatedListener {
    private var billingClient: BillingClient? = null
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var  myAdapter: RecyclerView.Adapter<*>

    //private var rewardedVideoAd: RewardedVideoAd? = null

    private var mIsLoading = false
    private var mRewardedAd: RewardedAd? = null

    private var adcheck = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_abouts_page, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.rv_moreapps)
        var retry = view.findViewById<TextView>(R.id.tv_retry)
        var loader = view.findViewById<ProgressBar>(R.id.lt_loader)
        var rateus = view.findViewById<Button>(R.id.btn_rateus)
        var adprogress = view.findViewById<ProgressBar>(R.id.adprogress)
       // var cancel = view.findViewById<ImageView>(R.id.pastquestionscancel)
        var adspace = view.findViewById<CardView>(R.id.adspace)
        var adimage = view.findViewById<ImageView>(R.id.adimage)
        var loadad = view.findViewById<Button>(R.id.Loadadd)
        var admessage = view.findViewById<TextView>(R.id.message)
        var ad2 = view.findViewById<Button>(R.id.Loadadd22)

        ad2.setOnClickListener {
            ad2.clicking()
            ad2.shortvibrate()
            (activity as Activity2).replacefragment("AllAds")
        }

        var buypoints = view.findViewById<Button>(R.id.buy3000Points)
        billingClient = BillingClient.newBuilder(requireContext()).enablePendingPurchases().setListener(this).build()
        billingClient!!.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val queryPurchase = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
                    val queryPurchases = queryPurchase.purchasesList
                    if (queryPurchases != null && queryPurchases.size > 0) {
                        handlePurchases(queryPurchases)
                    }
                }
            }

            override fun onBillingServiceDisconnected() {}
        })
        buypoints.setOnClickListener {
            buypoints.clicking()
            confirmtopurchase()
        }

        loadad.text = "Load Ad: +20 Tokens"


        getdata1(adspace, adimage, admessage)
        adspace.visibility = View.GONE
        adprogress.visibility = View.GONE
        loader.visibility = View.VISIBLE
        retry.visibility = View.GONE

        MobileAds.initialize(requireContext()) {}

        //initializing facebook ads
        // AudienceNetworkAds.initialize(requireContext())

        manager = LinearLayoutManager(requireContext())
        getdata(loader, retry, recyclerView)
        retry.setOnClickListener {
            retry.clicking()
            getdata(loader, retry, recyclerView)
        }
        rateus.setOnClickListener {
            rateus.clicking()
            val uriUri = Uri.parse("https://play.google.com/store/apps/details?id=ade.yemi.growthchecker_free")
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUri)
            startActivity(launchBrowser)
        }
        loadad.setOnClickListener {
            loadad.clicking()
            when(loadad.text){
                "Load Ad: +20 Tokens" -> {
                    loadRewardedAd(adprogress, loadad)
                    Toast.makeText(requireContext(), "Ad is loading. Please Wait", Toast.LENGTH_SHORT).show()
                }
                "Success!! View Ad" -> {
                    val animation = AnimationUtils.loadAnimation(context, R.anim.adstagnant)
                    loadad.startAnimation(animation)
                    showRewardedVideo(adprogress, loadad)
                    loadad.text = "Load Ad: +20 Tokens"
                }
                else->{

                }
            }
        }
//        cancel.setOnClickListener {
//            cancel.clicking()
//            startActivity(Intent(requireContext(), MainActivity::class.java))
//        }
        return view
    }
    //    private fun loadfacebookad(progressBar: ProgressBar){
//        rewardedVideoAd = RewardedVideoAd(requireContext(), "429889565196930_429890685196818")
//
//        val rewardedVideoAdListener: RewardedVideoAdListener = object : RewardedVideoAdListener {
//            override fun onError(ad: Ad?, error: com.facebook.ads.AdError) {
//                // Rewarded video ad failed to load
//                Toast.makeText(requireContext(), "Rewarded video ad failed to load: ", Toast.LENGTH_SHORT).show()
//               // Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage())
//            }
//
//            override fun onAdLoaded(ad: Ad) {
//                // Rewarded video ad is loaded and ready to be displayed
//                //Toast.makeText(requireContext(), "Rewarded video ad is loaded and ready to be displayed!", Toast.LENGTH_SHORT).show()
//                //Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!")
//                progressBar.visibility = View.GONE
//                rewardedVideoAd!!.show()
//
//            }
//
//            override fun onAdClicked(ad: Ad) {
//                // Rewarded video ad clicked
//                //Toast.makeText(requireContext(), "Rewarded video ad clicked!", Toast.LENGTH_SHORT).show()
//               // Log.d(TAG, "Rewarded video ad clicked!")
//            }
//
//            override fun onLoggingImpression(ad: Ad) {
//                // Rewarded Video ad impression - the event will fire when the
//                // video starts playing
//                //Toast.makeText(requireContext(), "Rewarded video ad impression logged!", Toast.LENGTH_SHORT).show()
//
//                //Log.d(TAG, "Rewarded video ad impression logged!")
//            }
//
//            override fun onRewardedVideoCompleted() {
//                // Rewarded Video View Complete - the video has been played to the end.
//                // You can use this event to initialize your reward
//                //Toast.makeText(requireContext(), "Rewarded video completed!", Toast.LENGTH_SHORT).show()
//                //Log.d(TAG, "Rewarded video completed!")
//                var prefereceStuffs = Preferencestuff(requireContext())
//                var newpoints = prefereceStuffs.getPoint() + 20
//                prefereceStuffs.setPoint(newpoints)
//                Toast.makeText(requireContext(), "20 Points Added", Toast.LENGTH_LONG).show()
//
//                // Call method to give reward
//                // giveReward();
//            }
//
//            override fun onRewardedVideoClosed() {
//                // The Rewarded Video ad was closed - this can occur during the video
//                // by closing the app, or closing the end card.
//                //Toast.makeText(requireContext(), "Rewarded video ad closed!", Toast.LENGTH_SHORT).show()
//                //Log.d(TAG, "Rewarded video ad closed!")
//            }
//        }
//        rewardedVideoAd!!.loadAd(
//                rewardedVideoAd!!.buildLoadAdConfig()
//                        .withAdListener(rewardedVideoAdListener)
//                        .build())
//    }
    private fun confirmtopurchase(){
        var view = Dialog(requireContext())
        view.setCancelable(true)
        view.setContentView(R.layout.paymentconfirm)
        view.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.show()

        var confirm = view.findViewById<Button>(R.id.buypoints)
        confirm.setOnClickListener {
            confirm.clicking()
            consume()
            view.dismiss()
        }
    }
    private fun confirmtopurchase2(){
        var view = Dialog(requireContext())
        view.setCancelable(true)
        view.setContentView(R.layout.paymentconfirm2)
        view.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.show()
    }
    fun consume() {
        //check if service is already connected
        if (billingClient!!.isReady) {
            initiatePurchase()
        } else {
            billingClient = BillingClient.newBuilder(requireContext()).enablePendingPurchases().setListener(this).build()
            billingClient!!.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase()
                    } else {
                        Toast.makeText(requireContext(), "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onBillingServiceDisconnected() {}
            })
        }
    }
    private fun initiatePurchase() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(PRODUCT_ID)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient!!.querySkuDetailsAsync(params.build()
        )
        { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (skuDetailsList != null && skuDetailsList.size > 0) {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetailsList[0])
                        .build()
                    billingClient!!.launchBillingFlow(requireActivity(), flowParams)
                }
                else {
                    //try to add item/product id "consumable" inside managed product in google play console
                    Toast.makeText(requireContext(), "Purchase Item not Found", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        //if item newly purchased
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases)
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchasesResult = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
            val alreadyPurchases = queryAlreadyPurchasesResult.purchasesList
            alreadyPurchases?.let { handlePurchases(it) }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(requireContext(), "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
        }
    }
    fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            //if item is purchased
            if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(requireContext(), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show()
                    return
                }
                // else purchase is valid
                //if item is purchased and not consumed
                if (!purchase.isAcknowledged) {
                    val consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    billingClient!!.consumeAsync(consumeParams, consumeListener)
                }
            } else if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                Toast.makeText(requireContext(), "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show()
            } else if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                Toast.makeText(requireContext(), "Purchase Status Unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }
    var consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
        var prefereceStuffs = Preferencestuff(requireContext())
        var currentpoints = prefereceStuffs.getPoint()
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            prefereceStuffs.setPoint(currentpoints + 300)
            confirmtopurchase2()
        }
    }
    /**
     * Verifies that the purchase was signed correctly for this developer's public key.
     *
     * Note: It's strongly recommended to perform such check on your backend since hackers can
     * replace this method with "constant true" if they decompile/rebuild your app.
     *
     */
    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            val base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmDnzSi/gsfXIrxVF8XI/gmVek1aPKPFaYBpdUdfbgoCFUqXysjgGy7vtz0aeS2tR4ouX+iz1/BA000vwOMiYXNkd3f0qZFysgddc6IHIKRyWUaf/pIZCTeHqqvYVDNGoRVd0qcjcPk6hvcLr3qdPdUwGZcbgF/A3hItacgoRr3HpIUyWQNLQe/6UTBW7s0MqqH7+RmC4P+bb0FyO3q6/E9qbch0wrL00hsagYIeqtxLo/fMSw/stbLHTpMW8ColQVesjRvz64ZdB93HO6l8SniHG65+4X8cqkfneXtzIaTt7PKYDqmzATxgx3q/CeY+lDmeO8ksUd+/uGPu2vllybwIDAQAB"
            ade.yemi.growthchecker_free.Utilities.Security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (billingClient != null) {
            billingClient!!.endConnection()
        }
    }
    companion object {
        const val PREF_FILE = "MyPref"
        const val PURCHASE_KEY = "consumable"
        const val PRODUCT_ID = "consumable"
    }
    private fun getdata(loader: ProgressBar, retry: TextView, recyclerView: RecyclerView){
        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface::class.java)
        var call =API.post


        call?.enqueue(object : Callback<AllAppDetails?> {
            override fun onResponse(
                call: Call<AllAppDetails?>,
                response: Response<AllAppDetails?>
            ) {
                var appDetails: AllAppDetails? = response.body() as AllAppDetails
                var applist = appDetails?.apps
                recyclerView.apply {
                    myAdapter = MoreAppsAdapter(applist!!)
                    layoutManager = manager
                    adapter = myAdapter
                }
                retry.visibility = View.GONE
                loader.visibility = View.GONE
            }

            override fun onFailure(call: Call<AllAppDetails?>, t: Throwable) {
                Toast.makeText(requireContext(), "Could not load Apps. Check connection", Toast.LENGTH_SHORT).show()
                retry.visibility = View.VISIBLE
                loader.visibility = View.GONE
            }
        })
    }
    private fun showRewardedVideo(progressBar: ProgressBar, load: Button) {
        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mRewardedAd = null
                    //loadRewardedAd(progressBar, load)
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Toast.makeText(requireContext(), "Ad failed to load, Check Connection", Toast.LENGTH_SHORT).show()
                    mRewardedAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is dismissed.
                }
            }
            mRewardedAd?.show(this.requireActivity(), OnUserEarnedRewardListener() {
                var prefereceStuffs = Preferencestuff(requireContext())
                var newpoints = prefereceStuffs.getPoint() + 20
                prefereceStuffs.setPoint(newpoints)
                Toast.makeText(requireContext(), "20 Tokens Added", Toast.LENGTH_LONG).show()
            })
        }
    }
    private fun loadRewardedAd(progressBar: ProgressBar, load: Button) {
        progressBar.visibility = View.VISIBLE
        if (mRewardedAd == null) {
            mIsLoading = true
            var adRequest = AdRequest.Builder().build()

            RewardedAd.load(
                requireContext(), AD_UNIT_ID, adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Toast.makeText(requireContext(), "Ad failed to load, check your connection.", Toast.LENGTH_SHORT).show()
                        //loadfacebookad(progressBar)
                        progressBar.visibility = View.GONE
                        mIsLoading = false
                        mRewardedAd = null
                    }

                    val animation = AnimationUtils.loadAnimation(context, R.anim.adscale)
                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Toast.makeText(requireContext(), "Ad is loaded, click the button to view", Toast.LENGTH_SHORT).show()
                        load.text = "Success!! View Ad"
                        load.startAnimation(animation)
                        progressBar.visibility = View.GONE
                        mRewardedAd = rewardedAd
                        mIsLoading = false
                    }
                }
            )
        }
    }
    private fun getdata1(adspace: CardView, adimage: ImageView, message: TextView){
        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface1.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface1::class.java)
        var call =API.post
        call?.enqueue(object : Callback<AppContent?> {
            override fun onResponse(
                call: Call<AppContent?>,
                response: Response<AppContent?>
            ) {
                var appContent: AppContent? = response.body() as AppContent
                var adslist = appContent?.ads

                if (adslist!![0].visible != false) {
                    adspace.visibility = View.VISIBLE
                    adcheck = true
                    if (adslist!![0].SmallImagelink!!.CheckEmpty() != true) {
                        Glide.with(requireContext()).load(adslist!![0].SmallImagelink).centerCrop()
                            .into(adimage)
                    } else {
                        adimage.visibility = View.GONE
                        adspace.visibility = View.VISIBLE
                    }
                    message.text = adslist!![0].message
                    val animation = AnimationUtils.loadAnimation(context, R.anim.adscale)
                    adspace.startAnimation(animation)
                    adspace.setOnClickListener {
                        adspace.clicking()
                        if (adslist!![0].Link != "") {
                            val uriUri = Uri.parse(adslist!![0].Link)
                            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUri)
                            startActivity(launchBrowser)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "${adslist!![0].Description}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<AppContent?>, t: Throwable) {
                adspace.visibility = View.GONE
            }
        })
    }
}