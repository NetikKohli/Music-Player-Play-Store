package com.nicekoh.musicplayer.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nicekoh.musicplayer.R;
import com.nicekoh.musicplayer.adapters.Recycler_adapter_music;
import com.nicekoh.musicplayer.VideoModal;


public class MusicFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    int lastSelectedOption=2;
    String sortOrder;
    public static final String MY_PREF = "my pref";
    public ArrayList<VideoModal> arrayLists;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences preferences;
    Recycler_adapter_music adapter;
    LinearLayout no_music;
    Button refreshMusic;
    int impInst=0;
    int min;
    int hour;
    int sec;
    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        assert container != null;
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_music, container, false);
        recyclerView = view.findViewById(R.id.recyclermusic);
        swipeRefreshLayout = view.findViewById(R.id.swipemusicrefresh);

       refreshMusic=view.findViewById(R.id.refreshMusic);
        no_music=view.findViewById(R.id.nomusic);
        preferences = requireActivity().getSharedPreferences(MY_PREF, MODE_PRIVATE);
//        refreshMusic.setOnClickListener(this);
        setHasOptionsMenu(true);
        arrayLists = new ArrayList<VideoModal>();
        adapter = new Recycler_adapter_music(getActivity(),arrayLists);
        recyclerView.setAdapter(adapter);
        recyclerView.hasFixedSize();
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemViewCacheSize(2000);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        swipeCalling();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getMusic(requireContext());
       // activateReview();

        refreshMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Refreshing ...", Toast.LENGTH_SHORT).show();
                getMusic(requireContext());
                getFragmentManager().beginTransaction().detach(MusicFragment.this).attach(MusicFragment.this).commit();

            }
        });
        return view;
    }



    @Override
    public void onResume() {
        swipeCalling();
        super.onResume();
    }

    public void swipeCalling() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().detach(MusicFragment.this).attach(MusicFragment.this).commit();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1500);

            }
        });

    }

    @SuppressLint({"Range", "NotifyDataSetChanged"})
    private void getMusic(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        /*  Cursor cursor = contentResolver.query(uri, null, null, null, MediaStore.Video.Media.DATE_MODIFIED + " DESC");
         */

        String sort_value = preferences.getString("sort","abcd");
        switch (sort_value) {
            case "sortSizeD":
                sortOrder = MediaStore.MediaColumns.SIZE + " DESC";
                break;
            case "sortLengthD":
                sortOrder = MediaStore.Audio.Media.DURATION + " DESC";
                break;
            case "sortSizeA":
                sortOrder = MediaStore.MediaColumns.SIZE + " ASC";
                break;
            case "sortLengthA":
                sortOrder = MediaStore.Audio.Media.DURATION + " ASC";
                break;
            case "sortDateA":
                sortOrder = MediaStore.MediaColumns.DATE_MODIFIED + " ASC";
                break;
            default:
                sortOrder = MediaStore.MediaColumns.DATE_MODIFIED + " DESC";
                break;
        }

        //String[] selectionArg = new String[]{"%"+folderName+"%"};
        Cursor cursor = contentResolver.query(uri, null,
                null, null, sortOrder);
        String name = null;
        String path = null;
        float size;
        String date;
        Bitmap thumbnail;
        String vidDuration;
        long duration;
        long id;
        if (cursor.moveToFirst()) {
            do {

                name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                size = cursor.getFloat(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                //   date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));
                //id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

                vidDuration = newconverter(duration);
                String sizeformatted = sizeFormat(size);

                arrayLists.add(new VideoModal(name,path,null,vidDuration,sizeformatted,null,null));
            } while (cursor.moveToNext());
        }

        if (arrayLists.size() == 0) {
            no_music.setVisibility(View.VISIBLE);
        } else {
            no_music.setVisibility(View.GONE);
       }
        adapter.notifyDataSetChanged();
        cursor.close();
        if (sort_value.equals("sortNameA")) {
            sortingMusic("A");
        }
        else if (sort_value.equals("sortNameD")) {
            sortingMusic("D");
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    void sortingMusic(String order){

            Collections.sort(arrayLists, new Comparator<VideoModal>() {
                @Override
                public int compare(VideoModal videoModal, VideoModal t1) {
                    return videoModal.getName().compareToIgnoreCase(t1.getName());
                }
            });
        if(order.equals("D"))
           Collections.reverse(arrayLists);
         adapter.notifyDataSetChanged();
    }
    private String newconverter(long duration) {
        String durationFormatted = "";

        sec= (int) (((duration)/1000))%60;
        min= (int) (((duration)/(1000*60)))%60;
        hour= (int) ((duration)/(1000*60*60));
        if(hour==0)
        { if(min<10 && min!=0)
            durationFormatted="   "+" 0"+min+":"+ (String.format(Locale.UK,"%02d",sec));
        else if(min==0) durationFormatted="    "+"00"+":"+ (String.format(Locale.UK,"%02d",sec));
        else durationFormatted="     "+min+":"+ (String.format(Locale.UK,"%02d",sec));
        }
        else if(min==0) durationFormatted="  "+"00:"+String.format(Locale.UK,"%02d",sec);
        else if(sec==0) durationFormatted="  00";
        else {
            if(hour<10)
                durationFormatted=" ";

            durationFormatted += hour + ":" + min + ":" + String.format(Locale.UK, "%02d", sec);
        }
        return (durationFormatted);
    }
    private String sizeFormat(float size) {
        String size1 = null;
        if(size<1024)
            size1=String.format("%.2f",size)+"BYTES";
        else if(size<Math.pow(1024,2))
            size1= String.format("%.2f",size/1024)+" KB";
        else if(size<Math.pow(1024,3))
            size1= String.format("%.2f",size/Math.pow(1024,2))+" MB";
        else
            size1= String.format("%.2f",size/Math.pow(1024,3))+" GB";

        return size1;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.refreshMusic){
            swipeCalling();
            Toast.makeText(getContext(), "Refreshing........", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.music_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_music);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = getActivity().getSharedPreferences(MY_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = item.getItemId();
        switch (id) {
            case R.id.refresh_files:
                getActivity().finish();
                startActivity(getActivity().getIntent());
                break;
            case R.id.sort_by:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Sort By");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.apply();
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().setReorderingAllowed(true).detach(MusicFragment.this).commit();
                        getMusic(requireContext());
                        getFragmentManager().beginTransaction().attach(MusicFragment.this).commit();

                        dialog.dismiss();
                    }
                });

                lastSelectedOption=preferences.getInt("lastOption",2);
                String[] items = {"Name (A  ->  Z)","Name (Z  ->  A)","Date Modified(Newer  ->   Older)","Date Modified (Older  ->  Newer)","Size (Bigger  ->  Smaller)","Size (Smaller  ->  Bigger)",
                        "Duration (Long  ->  Short)",
                        "Duration (Short  ->  Long)"};
                alertDialog.setSingleChoiceItems(items, lastSelectedOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                editor.putString("sort","sortNameA");
                                editor.putInt("lastOption",0);
                                break;
                            case 1:
                                editor.putString("sort","sortNameD");
                                editor.putInt("lastOption",1);
                                break;
                            case 2:
                                editor.putString("sort","sortDateD");
                                editor.putInt("lastOption",2);
                                break;
                            case 3:
                                editor.putString("sort","sortDateA");
                                editor.putInt("lastOption",3);
                                break;
                            case 4:
                                editor.putString("sort","sortSizeD");
                                editor.putInt("lastOption",4);
                                break;
                            case 5:
                                editor.putString("sort","sortSizeA");
                                editor.putInt("lastOption",5);
                                break;
                            case 6:
                                editor.putString("sort","sortLengthD");
                                editor.putInt("lastOption",6);
                                break;
                            case 7:
                                editor.putString("sort","sortLengthA");
                                editor.putInt("lastOption",7);
                                break;
                        }
                    }
                });
                alertDialog.create().show();

                break;

            case R.id.more_apps:

               //startReviewflow();

              /*  try{
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+getActivity().getPackageName())));
                }
                catch (Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id="+getActivity().getPackageName())));
                }*/
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/developer?id=NiceTone")));

                break;

            case R.id.helpfeed:

                        Intent intent=new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"nicetone.feedback@gmail.com"});

                        startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String inputs = newText.toLowerCase();
        ArrayList<VideoModal> mediaFiles = new ArrayList<>();
        for (VideoModal media : arrayLists) {
            if ((media.getName().toLowerCase()).contains(inputs)) {
                mediaFiles.add(media);

            }
        }

        adapter.updateVideoFiles(mediaFiles);

        return true;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }



/*
    public void onRefreshing(Context context, ArrayList<VideoModal> oldList) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        Cursor cursor = contentResolver.query(uri, null, null, null, MediaStore.Video.Media.DATE_MODIFIED + " DESC");
        String name = null, path = null, size, date;
        long id;
        int i = 0;
        if (cursor.moveToFirst()) {
            do {

                name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                //   date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));
                id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));

                arrayList.set(i, new VideoModal(name, oldList.get(i).getThumbnail()));
                i++;
            } while (cursor.moveToNext());

            adapter.notifyDataSetChanged();
            cursor.close();
        }
    }*/
}