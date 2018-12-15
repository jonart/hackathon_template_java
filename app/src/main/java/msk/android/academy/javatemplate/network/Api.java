package msk.android.academy.javatemplate.network;

import retrofit2.http.GET;

public interface Api {

    @GET
    String getString();
}
