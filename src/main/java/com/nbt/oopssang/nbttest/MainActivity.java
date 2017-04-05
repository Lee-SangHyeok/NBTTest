package com.nbt.oopssang.nbttest;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nbt.oopssang.nbttest.control.STGVAdapter;
import com.nbt.oopssang.nbttest.data.NewYorkDataSet;
import com.nbt.oopssang.nbttest.network.NewYorkService;
import com.nbt.oopssang.nbttest.network.RetrofitManager;
import com.nbt.oopssang.nbttest.view.StaggeredGridView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sang on 2017-04-04.
 * 엔비티 과제
 * NewYorkTimes API 연동하여 핀터레스트 UI로 화면에 표시.
 * Title 클릭 시, WebView연동.
 */
public class MainActivity extends AppCompatActivity {
    private StaggeredGridView mGridview;
    private STGVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();
        startRetrofit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 레이아웃 구성
     */
    private void setLayout(){
        findViewById(R.id.loading).setVisibility(View.VISIBLE);

        mGridview = (StaggeredGridView) findViewById(R.id.gridview);
        int margin = getResources().getDimensionPixelSize(R.dimen.stgv_margin);
        mGridview.setItemMargin(margin);
        mGridview.setPadding(margin, 0, margin, 0);

        mAdapter = new STGVAdapter(this, getApplication());
        mGridview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mGridview.setOnLoadmoreListener(new StaggeredGridView.OnLoadmoreListener() {
            @Override
            public void onLoadmore() {
                new LoadMoreTask().execute();
            }
        });
    }

    /**
     * NewYorTimes API 연동
     */
    private void startRetrofit(){
        NewYorkService service = RetrofitManager.getNewYorkService();
        service.getdata().enqueue(new Callback<NewYorkDataSet>() {
            @Override
            public void onResponse(Call<NewYorkDataSet> call, Response<NewYorkDataSet> response) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                NewYorkDataSet result = response.body();
                if(result != null){
                    mAdapter.setData(result.getResults());
                    mAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "onResponse Data null", Toast.LENGTH_SHORT).show();
                    Log.e("sang", "onResponse Data null ");
                }
            }

            @Override
            public void onFailure(Call<NewYorkDataSet> call, Throwable t) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.d("sang", "sang test onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public class LoadMoreTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mAdapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }
    }
}
