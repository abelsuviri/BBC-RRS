package com.example.abel.newsfeed.di.module;

import com.example.abel.newsfeed.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Abel Suviri
 */

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
