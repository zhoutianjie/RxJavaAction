package com.kedacom.rxjavaactiondemo.model.net;

import com.kedacom.rxjavaactiondemo.model.entity.NewsEntity;
import com.kedacom.rxjavaactiondemo.model.entity.NewsResultApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhoutianjie on 2019/4/17.
 */

public interface NewsApi {

    @GET("api/data/{category}/{count}/{page}")
    Observable<NewsResultApi> getNews(@Path("category") String category, @Path("count") int count, @Path("page") int page);


}
