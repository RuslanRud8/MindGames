package com.developer.rrd_projects

import android.content.Intent
import android.os.Bundle
import com.developer.rrd_projects.games.end_game_screen.EndGameScreen
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

class Reclam : MyGameActivity() {

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclam)

        loadAndShowAdd()
    }

    private fun loadAndShowAdd() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9050823804847454/7198827424"
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                mInterstitialAd.show()
            }

            override fun onAdClosed() {
                loadEndGameScreen()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                loadEndGameScreen()
            }

        }
    }

    private fun loadEndGameScreen(){
        val i = Intent(this,EndGameScreen::class.java)

        val gameEnded:String? = intent.getStringExtra("game_ended")
        val gameScore:Int = intent.getIntExtra("game_score",0)

        i.putExtra("game_ended", gameEnded)
        i.putExtra("game_score", gameScore)

        startActivity(i)

        finish()
    }
}
