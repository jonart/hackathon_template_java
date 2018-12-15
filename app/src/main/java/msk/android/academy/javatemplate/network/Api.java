package msk.android.academy.javatemplate.network;

import java.util.List;

import io.reactivex.Observable;
import msk.android.academy.javatemplate.network.request.ReqModel;
import msk.android.academy.javatemplate.network.response.ResModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("/")
    Observable<ResModel> getString();

//    @POST("/test")
//    Observable<ResModel> sendString(@Body ReqModel reqModel);

    @POST("/gbd")
    Observable<List<ResModel>> sendSound(@Body ReqModel reqModel);
}
