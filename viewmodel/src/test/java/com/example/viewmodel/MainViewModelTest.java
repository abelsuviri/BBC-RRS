package com.example.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.data.model.Item;
import com.example.data.model.Rss;
import com.example.data.network.INewsService;
import com.example.viewmodel.mocks.MockXml;
import com.example.viewmodel.rules.RxSchedulerRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author Abel Suviri
 */
public class MainViewModelTest {
    @Rule
    public final RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private INewsService newsService;

    @Mock
    private Observer<List<Item>> observer;

    private Rss rss;

    private MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainViewModel = new MainViewModel(newsService);
    }

    @Test
    public void testGetNews_behaves_correct() {
        try {
            Serializer serializer = new Persister();
            SimpleXmlConverterFactory.createNonStrict(serializer);
            rss = serializer.read(Rss.class, MockXml.mockXml);
        } catch (Exception ignore) {}

        Mockito.when(newsService.getFeed()).thenReturn(Observable.just(rss));
        mainViewModel.getNews().observeForever(observer);
        List<Item> item = mainViewModel.getNews().getValue();
        Assert.assertEquals(item, rss.getChannel().getItem());
    }

    @Test(expected = NullPointerException.class)
    public void testGetNews_behaves_incorrect() {
        rss = new Rss();

        Mockito.when(newsService.getFeed()).thenReturn(Observable.just(rss));
        mainViewModel.getNews().observeForever(observer);
        List<Item> item = mainViewModel.getNews().getValue();
        Assert.assertNotEquals(item, rss.getChannel().getItem());
    }
}