package com.nicekoh.musicplayer;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class VideoModal implements Parcelable {
    public String duration;
    String name;
    String size;
    String date;
    String path;
    String resolution;
    public Uri uri;
    Bitmap thumbnail;

    public VideoModal(String name){
        this.name=name;
    }

    public VideoModal(String name, String path, Uri uriVidMus, String duration, String size, String date, String reslution) {
        this.name = name;
        this.size = size;
        this.path=path;
        this.uri=uriVidMus;
        this.duration = duration;
        this.date = date;
        this.resolution=reslution;
    }

    public VideoModal(String name, String path) {
        this.name=name;
        this.path=path;
    }


    protected VideoModal(Parcel in) {
        duration = in.readString();
        name = in.readString();
        size = in.readString();
        date = in.readString();
        path = in.readString();
        resolution = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<VideoModal> CREATOR = new Creator<VideoModal>() {
        @Override
        public VideoModal createFromParcel(Parcel in) {
            return new VideoModal(in);
        }

        @Override
        public VideoModal[] newArray(int size) {
            return new VideoModal[size];
        }
    };

    public String getName() {
        return name;
    }

    public VideoModal setName(String names) {
        name = names;

        return null;
    }

    public String getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }
    public String getPath(){return path;}

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(duration);
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(date);
        dest.writeString(path);
        dest.writeString(resolution);
        dest.writeParcelable(uri, flags);
        dest.writeParcelable(thumbnail, flags);
    }
}
