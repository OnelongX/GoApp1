package com.ways2u.android.goapp.meizi.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;


import com.trello.rxlifecycle2.android.FragmentEvent;
import com.ways2u.android.goapp.R;
import com.ways2u.android.goapp.base.BaseFragment;
import com.ways2u.android.goapp.meizi.adapter.BaseRecyclerOnScrollListener;
import com.ways2u.android.goapp.meizi.adapter.MainAdapter;
import com.ways2u.android.goapp.meizi.api.MeiziApi;
import com.ways2u.android.goapp.meizi.model.Meizi;
import com.ways2u.android.goapp.meizi.view.BaseRecyclerView;
import com.ways2u.android.goapp.util.ImageLoader;
import com.ways2u.android.net.util.JsonCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private BaseRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private BaseRecyclerOnScrollListener scrollListener;
    private MainAdapter mainAdapter;
    private List<Meizi.ResultsBean> datas = new ArrayList<>();


    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        recyclerView = find(R.id.recyclerView);
        swipeRefreshLayout = find(R.id.swipeRefreshLayout);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainAdapter = new MainAdapter();
        mainAdapter.setData(datas);
        recyclerView.setAdapter(mainAdapter);

        scrollListener = new BaseRecyclerOnScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData(Bundle bundle) {
        datas.clear();
        mainAdapter.notifyDataSetChanged();

        scrollListener.clearPage();
        scrollListener.onLoadMore(1);
    }

    @Override
    public void onRefresh() {
        datas.clear();
        mainAdapter.notifyDataSetChanged();

        scrollListener.clearPage();
        scrollListener.onLoadMore(1);
    }


    private void getData(final int p) {
        MeiziApi.getMeiziList(activity, p, new JsonCallback() {
            @Override
            public void onFailure(Throwable e, String errorResponse) {
                scrollListener.loadingError();
            }

            @Override
            public void onSuccess(JSONObject response) {
                if(response==null){
                    return;
                }
                Meizi m = new Meizi(response);
                // 加载图片缓存，并保存尺寸数据到meizi
                Observable.just(m)
                        .compose(MainFragment.this.<Meizi>bindUntilEvent(FragmentEvent.DESTROY))
                        .map(new Function<Meizi, Meizi>() {
                            @Override
                            public Meizi apply(Meizi meizi) {
                                for (final Meizi.ResultsBean bean : meizi.getResults()) {
                                    Bitmap bitmap = ImageLoader.loadImageBitmap(bean.getUrl(),
                                            activity);
                                    if (bitmap != null) {
                                        bean.setWidth(bitmap.getWidth());
                                        bean.setHeight(bitmap.getHeight());
                                    }
                                }
                                return meizi;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Meizi>() {
                            @Override
                            public void accept(Meizi meizi) throws Exception {

                                datas.addAll(meizi.getResults());

                                if (datas.size() >= 10) {
                                    mainAdapter.notifyItemRangeInserted(
                                            datas.size() - 10 ,
                                            meizi.getResults().size());
                                }else {

                                    mainAdapter.notifyItemRangeInserted(
                                            0 ,
                                            meizi.getResults().size());

                                    //mainAdapter.notifyDataSetChanged();
                                }
                            }
                        });


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
