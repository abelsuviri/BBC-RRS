package com.example.abel.newsfeed.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.abel.newsfeed.R;
import com.example.abel.newsfeed.ui.adapter.NewsAdapter;
import com.example.abel.newsfeed.ui.adapter.viewholder.ViewHolderInterface;
import com.example.viewmodel.MainViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements ViewHolderInterface {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.newsList)
    RecyclerView newsList;

    @BindView(R.id.loadingLayout)
    RelativeLayout loadingLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_menu_my_calendar, null));
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        //If the device is not a tablet, it sets the orientation to be always portrait
        if(!getResources().getBoolean(R.bool.isTablet)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(this::loadNews);

        loadNews();
        checkIsLoading();
        checkIsFailed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }

    /**
     * This method does the setup of the RecyclerView where the news are displayed.
     * If the device is in portrait mode then it will display a list of news in a single column.
     * If the device is in landscape mode then it will display the list of news in two columns.
     */
    private void setupList() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            newsList.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsList.getContext(),
                    LinearLayoutManager.VERTICAL);
            newsList.addItemDecoration(dividerItemDecoration);
        } else {
            newsList.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    /**
     * This method observes the {@link MainViewModel} news LiveData method to retrieve the latest news
     * from the server.
     */
    private void loadNews() {
        mainViewModel.getNews().observe(this, news -> {
            newsList.setAdapter(new NewsAdapter(news, this));
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    /**
     * This is the callback method for the RecyclerView click event.
     * This method will open the selected news in a web browser.
     * If the device has a web browser installed then it will open a custom in-app browser,
     * otherwise it will open a WebView in {@link BrowserActivity}
     * @param link The URL to open in the browser
     */
    @Override
    public void onItemClick(String link) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        builder.setStartAnimations(this, R.anim.appear_from_bottom, R.anim.hold);
        builder.setExitAnimations(this, R.anim.hold, R.anim.dissapear_to_bottom);
        CustomTabsIntent customTabsIntent = builder.build();
        try {
            customTabsIntent.launchUrl(this, Uri.parse(link));
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(this, BrowserActivity.class);
            intent.putExtra(BrowserActivity.URL, link);
            startActivity(intent);
            overridePendingTransition(R.anim.appear_from_bottom, R.anim.hold);
        }

    }

    /**
     * This method will observe for changes in the isLoading variable.
     * If a network call is running the value will be true.
     * If true then the loading layer will be visible, if false it will be hidden.
     */
    private void checkIsLoading() {
        mainViewModel.isLoading().observe(this, isLoading -> loadingLayout.setVisibility((isLoading) ? View.VISIBLE : View.GONE));
    }

    /**
     * This method will observe for changes in the isFailed variable.
     * If there is any error retrieving the data from the server the value will be true
     * If true then the retry dialog will be visible.
     */
    private void checkIsFailed() {
        mainViewModel.isFailed().observe(this, isFailed -> {
            if (isFailed) {
                showRetryDialog();
            }
        });
    }

    /**
     * This method displays an AlertDialog to try again to call the server.
     */
    private void showRetryDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getResources().getString(R.string.request_error));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.retry), (dialog, i) -> {
            dialog.dismiss();
            loadNews();
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            //Do nothing
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
