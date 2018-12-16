package msk.android.academy.javatemplate.network.request;

import com.google.gson.annotations.SerializedName;

public class ReqModel {

    @SerializedName("string")
    private String string;

    public ReqModel(String request) {
        this.string = request;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
