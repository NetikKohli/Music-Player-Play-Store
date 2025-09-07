package com.nicekoh.musicplayer;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static com.nicekoh.musicplayer.MainActivity.countVid;

public class Music1 extends AppCompatActivity {
    static ExoPlayer exoPlayer;
    StyledPlayerView playerView;
    static long position;
    SharedPreferences sp;
    Uri uri;
    // int count=0;
    private long pos=0;
   // InterstitialAd mInterstitialAd=null;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        StyledPlayerView playerViewm = findViewById(R.id.musc);
//        mInterstitialAd=null;


        uri = getIntent().getData();

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        countVid=sp.getInt("Count",0);


        MediaItem  mediaItem = MediaItem.fromUri(uri);
        exoPlayer = new ExoPlayer.Builder(this).build();
        playerViewm.setPlayer(exoPlayer);
        exoPlayer.addMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Toast.makeText(Music1.this, "Error occur while playing your Music!!!", Toast.LENGTH_LONG).show();
            }
        });

        exoPlayer.setPlayWhenReady(true);
        playerViewm.setControllerShowTimeoutMs(0);
        playerViewm.setControllerHideOnTouch(false);
        MainActivity.countVid++;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //getSupportActionBar().hide();
        Fullscreen();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();

        getWindow().setFlags(1024, 1024);
    /*    ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.getClass();
        supportActionBar.hide();
*/     Fullscreen();
        editor.putInt("Count", countVid);
        editor.apply();
    }

    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.stop();
        exoPlayer.release();
          /*  if (mInterstitialAd != null) {
                mInterstitialAd.show(Music1.this);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        mInterstitialAd=null;
                        Log.e("AdsFailedInst","Ads Failed to show Instentitial Music1");
                    }


                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.i("AdsImpInst","Ads Impression Instentitial Music1");
                    }
                });
            }
       */ finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Fullscreen();
       /* if (countVid % 2 == 0) {
            loadAds();
            Log.i("AdsRequInstentitial","Ads Request instentitital Music1");
            }
       */


    }

    public void Fullscreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(getVisibility());
     /*   ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.getClass();
        supportActionBar.hide();*/
    }
    private int getVisibility() {
        return 5894;
    }

/*

    public void loadAds() {

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.Instentitial), adRequest,
                new InterstitialAdLoadCallback() {

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("AdsLoaded(Instentitial)", "Ad is Loaded Instentitial Music1");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("No Load Ads", loadAdError.toString());
                        mInterstitialAd = null;
                        Log.e("ADsFailedLoadInstentiti", "Ads Failed to load Instentitial Ad Music1");
                    }
                });

    }
*/

}



