package com.nicekoh.musicplayer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.nicekoh.musicplayer.main.SectionsPagerAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
   // public static InterstitialAd mInterstitialAd=null;
    public static int countVid = 0;

    SharedPreferences sp;
   // AdView mAdView;
    ConnectivityManager connectivityManager;
    public static int reqBanner=0,impBanner=0;
    public static int reqInst=0,impInst=0;
    @SuppressLint({"MissingPermission", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


          /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
            startActivityForResult(intent, 30);

           *//*
      */
       getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bg));

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        countVid = sp.getInt("Count", 0);
        setContentView(R.layout.activity_main1);

      /*  Toolbar toolbar=findViewById(R.id.toolbarmain);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackground(getDrawable(R.drawable.bg));
      */  //setSupportActionBar(toolbar);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        if (savedInstanceState != null)
            countVid = savedInstanceState.getInt("CountVid", 0);


    }



    @Override
    public void onBackPressed() {

       /* if (mInterstitialAd != null) {

            mInterstitialAd.show(MainActivity.this);
            Log.i("AdsShowInst", "Show Ad Called in recylerAdapter");

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    impInst++;
                    Log.i("AdsImpresInst","Impression Instentital:"+impInst);
                     }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    mInterstitialAd = null;

                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Log.e("AdsFailedShowInstentit","Ads Failed to show Instentitial Ad");
                    mInterstitialAd = null;

                  }
            });
            }
       */


        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to Exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                        Toast.makeText(MainActivity.this, "BYE BYE!!! SEE YOU SOON", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }).create().show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CountVid", countVid);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();


/*
        if((countVid)%2==0 && mInterstitialAd==null) {
            countVid++;
            Log.d("Ads CountVid","Ads countvid:"+countVid+" in Main Activity");

            loadAds();
*/
        }

    @Override
    protected void onPause() {

        super.onPause();
    }
}



/*

    public void loadAds() {
        reqInst++;
        AdRequest adRequest = new AdRequest.Builder().build();
        Log.i("AdsRequInstentitial","Ads Request instentitital:"+reqInst);
        InterstitialAd.load(this, getString(R.string.Instentitial), adRequest,
                new InterstitialAdLoadCallback() {

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;

                           Log.i("AdsLoaded(Instentitial)", "Ad is Loaded Instentitial");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("No Load Ads", loadAdError.toString());
                        mInterstitialAd = null;
                        Log.e("ADsFailedLoadInstentiti","Ads Failed to load Instentitial Ad");
                    }
                });

    }
*/



