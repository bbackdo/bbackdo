package com.bback.bbackdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bback.bbackdo.databinding.ActivityTeamNumBinding
import com.bback.bbackdo.databinding.DialogYutBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import splitties.activities.start


class TeamNumActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    private var mAdIsLoading: Boolean = false
    private var TAG = "MainActivity"

    private val bind by lazy {
        ActivityTeamNumBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(bind) {
            setContentView(root)
            loadAd()

            //editText 선택시, hint 제거
            editTeamNumSolo.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editTeamNumSolo.hint = ""
                    }
                }

            editPenaltySolo1.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editPenaltySolo1.hint = ""
                    }
                }
            editPenaltySolo2.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editPenaltySolo2.hint = ""
                    }
                }
            editPenaltySolo3.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editPenaltySolo3.hint = ""
                    }
                }
            editPenaltySolo4.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editPenaltySolo4.hint = ""
                    }
                }
            editPenaltySolo5.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editPenaltySolo5.hint = ""
                    }
                }

            warningTextSolo.isSelected = true

            buttonFinish.setOnClickListener {


                if (editTeamNumSolo.text.isEmpty() || editPenaltySolo1.text.isEmpty() || editPenaltySolo2.text.isEmpty() || editPenaltySolo3.text.isEmpty() || editPenaltySolo4.text.isEmpty() || editPenaltySolo5.text.isEmpty()) {
                    Toast.makeText(this@TeamNumActivity, "설정이 완료되지 않았습니다", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    val intent = Intent(this@TeamNumActivity, com.bback.bbackdo.UnityPlayerActivity::class.java)
                    intent.putExtra("mode", 0)
                    intent.putExtra("TeamNum", editTeamNumSolo.text.toString())
                    intent.putExtra("Penalty1", editPenaltySolo1.text.toString())
                    intent.putExtra("Penalty2", editPenaltySolo2.text.toString())
                    intent.putExtra("Penalty3", editPenaltySolo3.text.toString())
                    intent.putExtra("Penalty4", editPenaltySolo4.text.toString())
                    intent.putExtra("Penalty5", editPenaltySolo5.text.toString())
                    
                    startActivity(intent)
                    showInterstitial()



                }
            }

            buttonCancel.setOnClickListener {
                finish()
            }


            checkimgSolo.setOnClickListener {
                val builder = DialogYutBinding.inflate(layoutInflater)
                with(builder){
                    AlertDialog.Builder(this@TeamNumActivity, R.style.MyDialogTheme)
                        .setView(root)
                        .setPositiveButton("확인", null)
                        .show()
                }
            }
            checkDevelop.setOnClickListener {
                start<DeveloperActivity>()
            }

            rulebutton.setOnClickListener {
                start<RuleActivity>()
            }
        }
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
            mInterstitialAd?.show(this@TeamNumActivity)
        } else {
            Log.d("gaeun", "ad wasn't loaded")
            //Toast.makeText(this@MainActivity, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()
            if (!mAdIsLoading && mInterstitialAd == null) {
              mAdIsLoading = true
            loadAd()
            }
        }
    }
}