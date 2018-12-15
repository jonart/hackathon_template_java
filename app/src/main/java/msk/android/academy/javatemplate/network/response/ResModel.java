package msk.android.academy.javatemplate.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResModel implements Serializable {

    @SerializedName("imdb_id")
    private int mImdbId;
    @SerializedName("imdb_id_long")
    private String mImdbIdLong;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("year")
    private int mYear;

    public int getImdbId() {
        return mImdbId;
    }

    public void setImdbId(int imdbId) {
        mImdbId = imdbId;
    }

    public String getImdbIdLong() {
        return mImdbIdLong;
    }

    public void setImdbIdLong(String imdbIdLong) {
        mImdbIdLong = imdbIdLong;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }
}
