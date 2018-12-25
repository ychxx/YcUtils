package com.yc.ycutils;

import com.yc.yclibrary.YcInit;
import com.yc.ycutilslibrary.YcUtilsInit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 *
 */

public interface ApiService {
//    @Headers(YcInit.OTHER_BASE_URL+":"+"https://bg.fnpsy.com/")
    @GET("api/content/info/19")
    Observable<String> getHtml();
}
