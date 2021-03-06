package com.xy.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.main.SearchContract;
import com.xy.wanandroid.data.main.SearchHot;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.main.SearchPresenter;
import com.xy.wanandroid.ui.main.adapter.SearchHistoryAdapter;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.widget.CommonDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseRootActivity<SearchPresenter> implements SearchContract.View,
        SearchHistoryAdapter.OnItemChildClickListener, SearchHistoryAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_search)
    Toolbar mToolBarSearch;
    @BindView(R.id.flow_search)
    TagFlowLayout mFlowSearch;
    @BindView(R.id.tv_clear)
    TextView mTvClear;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private List<SearchHot> hotList;
    private List<String> historyList;
    private SearchHistoryAdapter mAdapter;
    private CommonDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolBarSearch);
        mToolBarSearch.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @OnClick(R.id.tv_clear)
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                if (historyList.size() > 0) {
                    dialog = new CommonDialog.Builder(activity)
                            .setTitle(getString(R.string.clear_history))
                            .setMessage(getString(R.string.delete_history_sure))
                            .setNegativeButton(getString(R.string.cancel), v -> dialog.dismiss())
                            .setPositiveButton(getString(R.string.sure), v -> {
                                historyList.clear();
                                mAdapter.notifyDataSetChanged();
                                mPresenter.saveSearchHistory(context, historyList);
                                dialog.dismiss();
                            }).create();
                    dialog.show();
                }
                break;
        }
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        hotList = new ArrayList<>();
        historyList = new ArrayList<>();
        mPresenter.getSearchHot();
        mAdapter = new SearchHistoryAdapter(R.layout.item_search_history, historyList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRvHistory.setAdapter(mAdapter);
        mPresenter.readSearchHistory(context, historyList);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 使用SearchView设置ToolBar搜索栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setQueryHint(getString(R.string.input_search_content));
        mSearchView.setIconified(false);
        mSearchView.setOnCloseListener(() -> {
            onBackPressedSupport();
            return true;
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!historyList.contains(query)) {
                    historyList.add(query);
                    mAdapter.notifyDataSetChanged();
                    mPresenter.saveSearchHistory(context, historyList);
                    jumpToResult(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 获取热搜关键词
     */
    @Override
    public void getSearchHotOk(List<SearchHot> dataBean) {
        hotList.clear();
        hotList.addAll(dataBean);
        mAdapter.notifyDataSetChanged();
        initFlowLayout();
    }

    private void initFlowLayout() {
        TagAdapter<SearchHot> tagAdapter = new TagAdapter<SearchHot>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHot searchHot) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.item_flow_layout, null);
                String name = searchHot.getName();
                text.setText(name);
                text.setTextColor(CommonUtil.randomColor());
                return text;
            }
        };
        mFlowSearch.setAdapter(tagAdapter);
        mFlowSearch.setOnTagClickListener((view, position1, parent1) -> {
            String name = hotList.get(position1).getName();
            if (!historyList.contains(name)) {
                historyList.add(name);
                mPresenter.saveSearchHistory(context, historyList);
            }
            jumpToResult(name);
            return true;
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        historyList.remove(position);
        adapter.notifyDataSetChanged();
        mPresenter.saveSearchHistory(context, historyList);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        jumpToResult(mAdapter.getData().get(position));
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        CommonUtil.hideKeyBoard();
    }

    private void jumpToResult(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SEARCH_RESULT_TITLE, key);
        JumpUtil.overlay(context, SearchResultActivity.class, bundle);
    }
}
