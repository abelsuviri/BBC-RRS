package com.example.abel.newsfeed.di.module;

import com.example.data.network.INewsService;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author Abel Suviri
 */

@Module
public class ServiceModule {
    @Provides
    @Singleton
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://feeds.bbci.co.uk/")
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public INewsService newsService(Retrofit retrofit) {
        return retrofit.create(INewsService.class);
    }
}
