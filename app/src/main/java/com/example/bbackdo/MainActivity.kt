package com.example.bbackdo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bbackdo.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kakao.sdk.common.util.Utility

import splitties.activities.start

const val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

class MainActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private var mAdIsLoading: Boolean = false
    private var TAG = "MainActivity"

    private val main by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(main.root)

        //Log.d("gaeun", "oncreate")
        //loadAd()

        main.testButton.setOnClickListener {
            startActivity(intent)
            start<TeamNumActivity>(){
                loadAd()
            }
        }

        main.kakaoLoginBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "준비중인 서비스 입니다", Toast.LENGTH_SHORT).show()
            //start<LoginActivity>()
        }

    }


    override fun onBackPressed() {
        //showInterstitial()
        super.onBackPressed()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this, AD_UNIT_ID, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.message)
                    mInterstitialAd = null
                    mAdIsLoading = false
                    val error = "domain: ${adError.domain}, code: ${adError.code}, " +
                            "message: ${adError.message}"
                    Log.d("gaeun", "$error")
                    /*Toast.makeText(
                        this@MainActivity,
                        "onAdFailedToLoad() with error $error",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    mAdIsLoading = false
                    Log.d("gaeun", "onadloaded")
                    //Toast.makeText(this@MainActivity, "onAdLoaded()", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    private fun showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Log.d(TAG, "Ad failed to show.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
            mInterstitialAd?.show(this@MainActivity)
        } else {
            Log.d("gaeun", "ad wasn't loaded")
            //Toast.makeText(this@MainActivity, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()
            //if (!mAdIsLoading && mInterstitialAd == null) {
              //  mAdIsLoading = true
                //loadAd()
            //}
        }
    }
}