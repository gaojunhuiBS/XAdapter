package com.xadaptersimple;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.widget.XFooterLayout;

import java.util.ArrayList;
import java.util.List;

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoActionBar);
        setContentView(R.layout.activity_collapsing_toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<MainBean> mainBeen = new ArrayList<>();
        DataUtils.getData(mainBeen);
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .onXBind(new XBaseAdapter.OnXBindListener<MainBean>() {
                            @Override
                            public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
                                holder.setTextView(R.id.tv_name, mainBean.getName());
                                holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
                            }
                        })
                        .setLoadingListener(new XBaseAdapter.LoadingListener() {
                            @Override
                            public void onRefresh() {
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        xRecyclerViewAdapter.refreshComplete(BaseRefreshHeader.STATE_DONE);
//                                    }
//                                }, 1500);
                            }

                            @Override
                            public void onLoadMore() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRecyclerViewAdapter.loadMoreComplete(XFooterLayout.STATE_NOMORE);
                                    }
                                }, 1500);
                            }
                        })
        );
    }
}
