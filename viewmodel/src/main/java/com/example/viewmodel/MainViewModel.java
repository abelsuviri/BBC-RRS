package com.example.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.data.model.Item;
import com.example.data.network.INewsService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Abel Suviri
 */

public class MainViewModel extends ViewModel {

    private final INewsService newsService;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFailed = new MutableLiveData<>();

    @Inject
    MainViewModel(INewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * This method calls the server to retrieve the latest news from the BBC RRS.
     * @return news list
     */
    public LiveData<List<Item>> getNews() {
        MutableLiveData<List<Item>> news = new MutableLiveData<>();
        loading.setValue(true);
        isFailed.setValue(false);
        newsService.getFeed()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedModel -> {
                    loading.setValue(false);
                    news.setValue(feedModel.getChannel().getItem());
                }, error -> {
                    loading.setValue(false);
                    isFailed.setValue(true);
                });

        return news;
    }

    /**
     * This method will trigger the loading layer in the view
     * @return boolean isLoading
     */
    public LiveData<Boolean> isLoading() {
        return loading;
    }

    /**
     * This method will trigger the retry dialog in the view
     * @return boolean isFailed
     */
    public LiveData<Boolean> isFailed() {
        return isFailed;
    }
}
