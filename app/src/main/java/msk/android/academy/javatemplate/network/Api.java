package msk.android.academy.javatemplate.network;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import msk.android.academy.javatemplate.network.request.ReqModel;
import msk.android.academy.javatemplate.network.response.FilmModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/")
    Observable<FilmModel> getString();

//    @POST("/test")
//    Observable<FilmModel> sendString(@Body ReqModel reqModel);

    @GET("/gbd")
    Observable<List<FilmModel>> sendSound(@Query("text") String request);

    @Multipart
    @POST("/gbp")
    Observable<FilmModel> uploadPhoto(@Part MultipartBody.Part file);
}
