package com.example.abel.newsfeed.di.component;

import android.app.Application;

import com.example.abel.newsfeed.NewsApp;
import com.example.abel.newsfeed.di.module.AppModule;
import com.example.abel.newsfeed.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author Abel Suviri
 */

@Singleton
@Component(modules = {AppModule.class, ServiceModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(NewsApp app);
}
