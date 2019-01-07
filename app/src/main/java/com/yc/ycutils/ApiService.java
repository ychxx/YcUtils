package com.yc.ycutils;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 *
 */

public interface ApiService {
//    @Headers(YcInit.OTHER_BASE_URL+":"+"https://bg.fnpsy.com/")
    @GET("api/content/info/19")
    Observable<String> getHtml();
}
