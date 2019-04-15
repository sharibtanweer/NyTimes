package com.NyTimes;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.NyTimes.Adapter.CustomArrayAdapter;
import com.NyTimes.Adapter.SimpleItemRecyclerViewAdapter;
import com.NyTimes.Model.MostPopularModel;
import com.NyTimes.RestClient.API;
import com.NyTimes.RestClient.ApiInterface;
import com.NyTimes.RestClient.RestClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    SimpleItemRecyclerViewAdapter adapter;
    String selectedDays="7";
    Boolean isTouch=false;
    MostPopularModel mostPopularModel;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.simpleSpinner);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("1 Day");
        spinnerArray.add("7 Day");
        spinnerArray.add("30 Day");
        CustomArrayAdapter adapter = new CustomArrayAdapter(this,
                R.layout.spinner_adapter, spinnerArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(isTouch) {
                    selectedDays = spinnerArray.get(pos).toString().replace("Day", "").trim();
                    setupRecyclerView();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isTouch=true;
                return false;
            }
        });
        if(selectedDays.equalsIgnoreCase("7"))
            spinner.setSelection(1);
        else if (selectedDays.equalsIgnoreCase("30"))
            spinner.setSelection(2);
        else if(selectedDays.equalsIgnoreCase("1"))
            spinner.setSelection(0);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiInterface apiService = RestClient.getClient().create(ApiInterface.class);
        Call<MostPopularModel> call = apiService.getMostPopularNewsData(Integer.parseInt(selectedDays), API.NYTIMES_API_KEY);
        call.enqueue(new Callback<MostPopularModel>() {
            @Override
            public void onResponse(Call<MostPopularModel> call, Response<MostPopularModel> response) {
                mostPopularModel = response.body();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
                //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
                progressDialog.dismiss();
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration( recyclerView.getContext(),
                        linearLayoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                adapter = new SimpleItemRecyclerViewAdapter(MainActivity.this,
                        mostPopularModel);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MostPopularModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
