package com.example.abel.newsfeed.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Abel Suviri
 */

@Module(includes = {ActivityBuilderModule.class, ViewModelModule.class})
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
