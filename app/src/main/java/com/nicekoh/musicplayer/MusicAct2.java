package com.nicekoh.musicplayer;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nicekoh.musicplayer.utils.NotificationUtils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MusicAct2 extends AppCompatActivity {
    ExoPlayer exoPlayer;
   SharedPreferences sharedPreferences;
    NotificationUtils notificationUtils;
    SharedPreferences.Editor myEdit;
    ArrayList<VideoModal> arrayList;
int cardPostion;
    /* access modifiers changed from: protected */
    protected void onCreate(Bundle savedInstanceState) {
        MediaItem mediaItem;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music2);
        StyledPlayerView playerViewm = findViewById(R.id.musc);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //mediaItem = MediaItem.fromUri(uri1);
        Bundle i = getIntent().getExtras();
        if (i != null) {
            cardPostion = i.getInt("POSI");
            myEdit = sharedPreferences.edit();
            // write all the data entered by the user in SharedPreference and apply
            myEdit.putInt("POSI", cardPostion);
            arrayList= i.getParcelableArrayList("ArrayList");

            myEdit.apply();

        } else {
            cardPostion = sharedPreferences.getInt("POSI", 0);
        }
        this.exoPlayer = new SimpleExoPlayer.Builder(this).build();


        for (int x = 0; x < arrayList.size(); x++){
            mediaItem = MediaItem.fromUri(arrayList.get(x).path);
            this.exoPlayer.addMediaItem(mediaItem);
    }

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "app"));
       ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
      /*  for (int x = 0; x < MusicFragment.arrayLists.size(); x++) {
            new File(String.valueOf(MusicFragment.arrayLists.get(x)));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(String.valueOf(MusicFragment.arrayLists.get(cardPostion).path))));
            concatenatingMediaSource.addMediaSource(mediaSource);
        }*/
        playerViewm.setPlayer(this.exoPlayer);
        exoPlayer.seekToDefaultPosition(cardPostion);


        //countVid++;
       // Log.d("Ads CountVid","Ads countvid:"+ countVid+" in Fragment Video");

        this.exoPlayer.prepare();
        notificationUtils=new NotificationUtils();
        notificationUtils.createNotification(this);

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Toast.makeText(MusicAct2.this, "Error occur while playing your Video!!!", Toast.LENGTH_LONG).show();
            }
        });
        this.exoPlayer.setPlayWhenReady(true);
        playerViewm.setControllerShowTimeoutMs(0);
        playerViewm.setControllerHideOnTouch(false);
        Fullscreen();

        notificationUtils.createNotification(this);

    }

    public void Fullscreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(getVisibility());
      /*  ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;

        supportActionBar.hide();
    */}
    private int getVisibility() {
        return 5894;
    }


    public void onBackPressed() {
      /*  if (mInterstitialAd != null) {

            mInterstitialAd.show(MusicAct2.this);
            Log.i("AdsShowInst", "Show Ad Called in recylerAdapter");

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdImpression() {
                    super.onAdImpression();

                    Log.i("AdsImpresInst","Impression Instentital:");
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
        exoPlayer.stop();
        exoPlayer.release();
        super.onBackPressed();
        notificationUtils.cancelNotifaction(this);
        finish(); }

    @Override
    protected void onResume() {
        super.onResume();
        Fullscreen();
    }
}
