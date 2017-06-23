package com.animalhusbandry.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.adapters.AdapterSearchPetUsingFilters;
import com.animalhusbandry.model.SearchPetUsingFiltersRequest;
import com.animalhusbandry.model.SearchPetUsingFiltersResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.utils.EndlessRecyclerViewScrollListenerImplementation;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SearchActivity extends AppCompatActivity implements EndlessRecyclerViewScrollListenerImplementation.OnScrollPageChangeListener {
    private MenuInflater menuInflater;
    private String strSearchText, strUserId;
    private EditText etSearch;
    private RetroUtils retroUtils;
    private SharedPreferences sharedPreferences;
    private ArrayList<SearchPetUsingFiltersResponse.Result> searchPetUsingFiltersArrayList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private static final String FIRST_PAGE = "0";
    private EndlessRecyclerViewScrollListenerImplementation endlessScrollListener;
    private AdapterSearchPetUsingFilters adapter;
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private RadioButton radioButton, radioButtonName, radioButtonBreed, radioButtonLocation;
    private Button btnDisplay;
    private Subscription subscription1 = new Subscription() {
        @Override
        public void unsubscribe() {

        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_Search);
        setSupportActionBar(toolbar);
        etSearch = (EditText) toolbar.findViewById(R.id.etsearch);
        ImageButton backBtn = (ImageButton) toolbar.findViewById(R.id.backBtn);
        retroUtils = new RetroUtils(this);
        sharedPreferences = SearchActivity.this.getSharedPreferences("Options", MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_Ui);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterSearchPetUsingFilters(SearchActivity.this);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButtonLocation = (RadioButton) findViewById(R.id.radioButtonLocation);
        radioButtonBreed = (RadioButton) findViewById(R.id.radioButtonBreed);
        radioButtonName = (RadioButton) findViewById(R.id.radioButtonName);
        radioButtonName.setClickable(true);
        radioButtonBreed.setClickable(true);
        radioButtonLocation.setClickable(true);
        strUserId = sharedPreferences.getString("strUserId", "");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setAdapter(adapter);
        subscription1 = RxTextView.textChangeEvents(etSearch)//
                .debounce(200, TimeUnit.MILLISECONDS)// default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(_getSearchObserver());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dialog_filter, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.byName:
                return true;
            case R.id.byBreed:

                searchByBreed(FIRST_PAGE);
                return true;
            case R.id.byLocation:
                searchByLocation(FIRST_PAGE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initPaging() {

        if (endlessScrollListener == null)
            endlessScrollListener = new EndlessRecyclerViewScrollListenerImplementation(layoutManager, this);
        else
            endlessScrollListener.setmLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(endlessScrollListener);
    }

    private void searchByName(String pageNumber) {
        SearchPetUsingFiltersRequest searchPetUsingFiltersRequest = new SearchPetUsingFiltersRequest();
        searchPetUsingFiltersRequest.setUserId(strUserId);
        searchPetUsingFiltersRequest.setName(strSearchText);
        searchPetUsingFiltersRequest.setLocation("");
        searchPetUsingFiltersRequest.setBreed("");
        searchPetUsingFiltersRequest.setPage(pageNumber);
        searchPetUsingFiltersRequest.setSize("10");
        initPaging();

        retroUtils.getApiClient().SEARCH_PET_USING_FILTERS_RESPONSE_CALL(searchPetUsingFiltersRequest).enqueue(new Callback<SearchPetUsingFiltersResponse>() {
            @Override
            public void onResponse(Call<SearchPetUsingFiltersResponse> call, Response<SearchPetUsingFiltersResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response == null || response.body() == null) {
                    Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getResult().equals("")) {
                    Toast.makeText(getBaseContext(), "No pet found of searched name", Toast.LENGTH_SHORT).show();

                } else if (response.body().getResponse().getCode().equals("200")) {
                    if (endlessScrollListener.getCurrentPage() == 0) {
                        adapter.setData(response.body().getResponse().getResult());
                    } else {
                        adapter.addData(response.body().getResponse().getResult());
                    }
                    endlessScrollListener.resetState();
                    adapter.notifyDataSetChanged();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getBaseContext(), "You are not authorized", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchPetUsingFiltersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchByBreed(String pageNumber) {
        SearchPetUsingFiltersRequest searchPetUsingFiltersRequest = new SearchPetUsingFiltersRequest();
        strSearchText = etSearch.getText().toString();
        searchPetUsingFiltersRequest.setUserId(strUserId);
        searchPetUsingFiltersRequest.setName("");
        searchPetUsingFiltersRequest.setLocation("");
        searchPetUsingFiltersRequest.setBreed(strSearchText);
        searchPetUsingFiltersRequest.setPage(pageNumber);
        searchPetUsingFiltersRequest.setSize("10");
        initPaging();
        retroUtils.getApiClient().SEARCH_PET_USING_FILTERS_RESPONSE_CALL(searchPetUsingFiltersRequest).enqueue(new Callback<SearchPetUsingFiltersResponse>() {
            @Override
            public void onResponse(Call<SearchPetUsingFiltersResponse> call, Response<SearchPetUsingFiltersResponse> response) {

                progressBar.setVisibility(View.GONE);
                if (response == null || response.body() == null) {
                    Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getResult().equals("")) {
                    Toast.makeText(getBaseContext(), "No pet found of searched breed", Toast.LENGTH_SHORT).show();

                } else if (response.body().getResponse().getCode().equals("200")) {
                    if (endlessScrollListener.getCurrentPage() == 0) {
                        adapter.setData(response.body().getResponse().getResult());
                    } else {
                        adapter.addData(response.body().getResponse().getResult());
                    }endlessScrollListener.resetState();
                    adapter.notifyDataSetChanged();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getBaseContext(), "You are not authorized", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchPetUsingFiltersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchByLocation(String pageNumber) {
        SearchPetUsingFiltersRequest searchPetUsingFiltersRequest = new SearchPetUsingFiltersRequest();
        String strSearchText = etSearch.getText().toString();
        searchPetUsingFiltersRequest.setUserId(strUserId);
        searchPetUsingFiltersRequest.setName("");
        searchPetUsingFiltersRequest.setLocation(strSearchText);
        searchPetUsingFiltersRequest.setBreed("");
        searchPetUsingFiltersRequest.setPage(pageNumber);
        searchPetUsingFiltersRequest.setSize("10");
        initPaging();

        retroUtils.getApiClient().SEARCH_PET_USING_FILTERS_RESPONSE_CALL(searchPetUsingFiltersRequest).enqueue(new Callback<SearchPetUsingFiltersResponse>() {
            @Override
            public void onResponse(Call<SearchPetUsingFiltersResponse> call, Response<SearchPetUsingFiltersResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response == null || response.body() == null) {
                    Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getResult().equals("")) {
                    Toast.makeText(getBaseContext(), "No pet found in searched location", Toast.LENGTH_SHORT).show();

                } else if (response.body().getResponse().getCode().equals("200")) {
                    if (endlessScrollListener.getCurrentPage() == 0) {
                        adapter.setData(response.body().getResponse().getResult());
                    } else {
                        adapter.addData(response.body().getResponse().getResult());
                    }endlessScrollListener.resetState();
                    adapter.notifyDataSetChanged();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getBaseContext(), "You are not authorized", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchPetUsingFiltersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        searchByBreed(Integer.toString(page));
    }

    private rx.Observer<TextViewTextChangeEvent> _getSearchObserver() {
        return new rx.Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("", "on error");
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                strSearchText = etSearch.getText().toString();
                if (radioButtonName.isChecked()) {
                    searchByName(FIRST_PAGE);

                } else if (radioButtonBreed.isChecked()) {
                    searchByBreed(FIRST_PAGE);

                } else if (radioButtonLocation.isChecked()) {
                    searchByLocation(FIRST_PAGE);

                }

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription1.unsubscribe();

    }
}