package com.shx.lawwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.lawwh.R;
import com.shx.lawwh.adapter.LawBaseAdapter;
import com.shx.lawwh.adapter.SpinnerAdapter;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.dao.MyLawItemDao;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.LawResponse;
import com.shx.lawwh.entity.response.ResponseLevelList;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.message.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.shx.lawwh.R.id.tabLayout;

/**
 * 首页的Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class LawSearchActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, TabLayout.OnTabSelectedListener, TextWatcher {
    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;
    private LawBaseAdapter mAdapter;
    private List<LawResponse> lawList = new ArrayList<>();
    private SwipeRefreshLayout mRefreshLayout;
    private int mPage = 1;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private EventMessage mEventMessage;
    private ImageView mSearche;
    private EditText mKeyword;
    private String[] mTabTitle;
    private ListView mKeywordTypeSpinner;
    private LinearLayout mKeywordType;
    //关键字筛选 和结果筛选选项文字
    private TextView mKeywordTypeTV, mFilterTV;
    private LinearLayout mlayoutEmpty;

    //结果筛选布局
//    private LinearLayout mIndustryLayout;
    private LawRequest mRequest;
    private String keywordType = "title";
    private List<ResponseLevelList> levelDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_search);
        mRequest = new LawRequest();
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();
        initTabs();
    }

    private void initData() {
        mRequest.setPage(mPage);
        mRequest.setPageSize(pageSize);
        //DialogManager.getInstance().showProgressDialog(this);
        RequestCenter.getLawList(mRequest, this);
    }

    private void initTabs() {
        levelDatas=new ArrayList<>();
        String type = getIntent().getStringExtra("typeCode");
        String title=getIntent().getStringExtra("title");
        mRequest.setTypeCode(type);
        getTopbar().setTitle(title);
        RequestCenter.getLevelList(type,this);
    }



    /**
     * 重新计算需要滚动的距离
     *
     * @param index 选择的tab的下标
     */
    private void recomputeTlOffset1(int index) {
//        if (mTabLayout.getTabAt(index) != null) mTabLayout.getTabAt(index).select();
        final int width = (int) (getTablayoutOffsetWidth(index) * getResources().getDisplayMetrics().density);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.smoothScrollTo(width, 0);
            }
        });
    }
//重中之重是这个计算偏移量的方法，各位看官看好了。

    /**
     * 根据字符个数计算偏移量
     *
     * @param index 选中tab的下标
     * @return 需要移动的长度
     */
    private int getTablayoutOffsetWidth(int index) {
        String str = "";
        for (int i = 0; i < index; i++) {
            //取出直到index的tab的文字，计算长度
            str += mTabTitle[i];
        }
        return str.length() * 14 + index * 12;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }


    private void loadMoreData() {
        if (isLastPage) {
            setFooterView();
            mAdapter.loadMoreEnd();
            return;
        }
        mPage++;
        LogGloble.d("loadMoreData", mPage + "");
        mRequest.setPage(mPage);
        RequestCenter.getLawList(mRequest, this);


    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.getData().clear();
        initData();
    }

    private void initView() {
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTabLayout = (TabLayout) findViewById(tabLayout);
        mTabLayout.setOnTabSelectedListener(this);

//        mSearche = (ImageView) findViewById(R.id.iv_search);
//        mSearche.setOnClickListener(this);

        mKeyword = (EditText) findViewById(R.id.et_keyworld);
        mKeyword.addTextChangedListener(this);
//        mKeywordType = (LinearLayout) findViewById(R.id.layout_keyworldtype);
//        mKeywordType.setOnClickListener(this);

//        mKeywordTypeTV = (TextView) findViewById(R.id.tv_keywordtype);
//        mFilterTV = (TextView) findViewById(R.id.tv_filter);

//        mIndustryLayout = (LinearLayout) findViewById(R.id.layout_industry);
//        mIndustryLayout.setOnClickListener(this);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_laws);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new LawBaseAdapter(lawList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_empty_view);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogGloble.d("setOnLoadMoreListener", "==============");
                loadMoreData();

            }
        }, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                isLastPage = false;
                mPage = 1;
                lawList.clear();
                mAdapter.getData().clear();
                initData();
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mAdapter.setNewData(lawList);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void setFooterView() {
        View footer = LayoutInflater.from(this).inflate(R.layout.layout_footer, null);
        mAdapter.setFooterView(footer);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                mPage = 1;
                isLastPage = false;

                String keyword = mKeyword.getText().toString();
                if(TextUtils.isEmpty(keyword)){
                    mRequest.setName("");
                    mRequest.setIssue_no("");
                    mRequest.setDescription("");

                }
                if (keywordType.equals("title")) {
                    mRequest.setName(keyword);
                }
                if (keywordType.equals("desc")) {
                    mRequest.setDescription(keyword);
                }
                if (keywordType.equals("issue_no")) {
                    mRequest.setIssue_no(keyword);
                }
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                initData();
                mAdapter.setLight(true, mRequest);

                break;
            case R.id.layout_keyworldtype:
                View keyworldView = DialogManager.getInstance().showPopupWindow(this, mKeywordType, R.layout.layout_spinner);
                mKeywordTypeSpinner = (ListView) keyworldView.findViewById(R.id.lv_spinner);
                final List<String> list = new ArrayList();
                list.add("标题");
                list.add("内容");
                list.add("令号");
                SpinnerAdapter adapter = new SpinnerAdapter(list, this);
                mKeywordTypeSpinner.setAdapter(adapter);
                mKeywordTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            keywordType = "title";
                        }
                        if (position == 1) {
                            keywordType = "desc";
                        }
                        if (position == 2) {
                            keywordType = "issue_no";
                        }
                        mKeywordTypeTV.setText(list.get(position));
                        DialogManager.getInstance().dissMissPopupWindow();
                    }
                });
                break;
            case R.id.layout_industry:
//                View IndustryLayoutView = DialogManager.getInstance().showPopupWindow(this, mIndustryLayout, R.layout.layout_spinnerlist);
//                mKeywordTypeSpinner = (ListView) IndustryLayoutView.findViewById(R.id.lv_spinner);
//                MyLawItemDao dao = new MyLawItemDao();
//                final List<String> filterList = dao.selctLawTypes();
//                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(filterList, this);
//                mKeywordTypeSpinner.setAdapter(spinnerAdapter);
//                mKeywordTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        mRequest.setTypeName(filterList.get(position));
//                        mFilterTV.setText(filterList.get(position));
//                        isLastPage = false;
//                        mPage = 0;
//                        lawList = loadData();
//                        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
//                        mAdapter.setNewData(lawList);
//                        mAdapter.notifyDataSetChanged();
//                        mAdapter.setEnableLoadMore(true);
//                        DialogManager.getInstance().dissMissPopupWindow();

//                    }
//                });

                break;
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab){
        mRequest.setLevel(levelDatas.get(tab.getPosition()).getCode());
        isLastPage = false;
        mPage = 1;
        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
        lawList.clear();
        initData();
        mAdapter.setNewData(lawList);
        mAdapter.notifyDataSetChanged();
        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        LogGloble.d("Tab", "onTabUnselected===");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        LogGloble.d("Tab", "onTabReselected===");
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LawResponse item = (LawResponse) adapter.getData().get(position);
        LogGloble.d("MainFragment", item.getFilePath() + "");
        if (TextUtils.isEmpty(item.getFilePath())||item.getStatus()==-1) {
            ToastUtil.getInstance().toastInCenter(this, "该文件不存在！");
            return;
        }
        if (item.getFileFrom().equals("pdf")) {
            Intent intent = new Intent(this, PdfViewActivity.class);
            intent.putExtra("URL", item.getFilePath());
            intent.putExtra("typeCode",item.getTypeCode());
            intent.putExtra("lawId",item.getId());
            intent.putExtra("is_favorite",item.getIs_favorite());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("URL", item.getFilePath());
            intent.putExtra("typeCode",item.getTypeCode());
            intent.putExtra("lawId",item.getId());
            intent.putExtra("is_favorite",item.getIs_favorite());
            startActivity(intent);
        }
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        DialogManager.getInstance().dissMissProgressDialog();
        JSONObject object = respose.getMainData();
        if (requestUrl.equals(RequestCenter.GET_LAWLIST)) {
            if (object.size() > 0) {
                lawList = MyJSON.parseArray(object.getString("lawList"), LawResponse.class);
                if (lawList.size() < pageSize) {
                    isLastPage = true;
                    setFooterView();
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                mAdapter.addData(lawList);
                mAdapter.notifyDataSetChanged();
            }
        }else  if(requestUrl.equals(RequestCenter.GET_LEVELLIST)){
            levelDatas= MyJSON.parseArray(object.getString("levelList"),ResponseLevelList.class);
            for(int i=0;i<levelDatas.size();i++){
                mTabLayout.addTab(mTabLayout.newTab().setText(levelDatas.get(i).getName()));
            }
            mRequest.setLevel(levelDatas.get(0).getCode());
        }
        return super.doSuccess(respose, requestUrl);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mPage = 1;
        isLastPage = false;

        String keyword =editable.toString();
        if(TextUtils.isEmpty(keyword)){
            mRequest.setName("");
            mRequest.setDescription("");
        }else{
            mRequest.setName(keyword);
            mRequest.setDescription(keyword);
        }

        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        initData();
        mAdapter.setLight(true, mRequest);
    }
}
