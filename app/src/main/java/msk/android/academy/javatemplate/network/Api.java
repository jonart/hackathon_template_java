package msk.android.academy.javatemplate.network;

import java.util.List;

import io.reactivex.Observable;
import msk.android.academy.javatemplate.network.request.ReqModel;
import msk.android.academy.javatemplate.network.response.FilmModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/")
    Observable<FilmModel> getString();

//    @POST("/test")
//    Observable<FilmModel> sendString(@Body ReqModel reqModel);

    @GET("/gbd")
    Observable<List<FilmModel>> sendSound(@Query("text") String request);
}
