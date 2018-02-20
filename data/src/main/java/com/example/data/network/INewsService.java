package com.example.data.network;

import com.example.data.model.Rss;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Abel Suviri
 */

public interface INewsService {
    @GET("news/world/rss.xml")
    Observable<Rss> getFeed();
}
