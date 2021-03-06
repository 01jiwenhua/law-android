//package com.shx.lawwh.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.shx.lawwh.R;
//import com.shx.lawwh.activity.PdfViewActivity;
//import com.shx.lawwh.activity.WebActivity;
//import com.shx.lawwh.adapter.LawBaseAdapter;
//import com.shx.lawwh.adapter.SpinnerAdapter;
//import com.shx.lawwh.common.LogGloble;
//import com.shx.lawwh.dao.LawItem;
//import com.shx.lawwh.dao.MyLawItemDao;
//import com.shx.lawwh.libs.dialog.DialogManager;
//import com.shx.lawwh.libs.dialog.ToastUtil;
//import com.shx.lawwh.message.EventMessage;
//import com.shx.lawwh.utils.StringUtils;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.shx.lawwh.R.id.tabLayout;
//
///**
// * 首页的Fragment
// * Created by 邵鸿轩 on 2016/12/1.
// */
//
//public class SearchFragment extends Fragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, TabLayout.OnTabSelectedListener {
//    private RecyclerView mRecyclerView;
//    private TabLayout mTabLayout;
//    private LawBaseAdapter mAdapter;
//    private List<LawItem> lawList = new ArrayList<>();
//    private SwipeRefreshLayout mRefreshLayout;
//    private int mPage = 0;
//    private final int pageSize = 10;
//    private boolean isLastPage = false;
//    private EventMessage mEventMessage;
//    private ImageView mSearche;
//    private EditText mKeyword;
//    public LawRequest mRequest;
//    private String[] mTabTitle = new String[]{"全部", "安全标准", "部门规章", "地方法规", "国家标准", "国家法律", "行业标准", "行政法规"};
//    private ListView mKeywordTypeSpinner;
//    private LinearLayout mKeywordType;
//    //关键字筛选 和结果筛选选项文字
//    private TextView mKeywordTypeTV, mFilterTV;
//    private LinearLayout mlayoutEmpty;
//    //结果筛选布局
//    private LinearLayout mIndustryLayout;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_law_search, null);
//        mRequest = new LawRequest();
//        setDefaultRequest();
//        initView(view);
//        for (String tab : mTabTitle) {
//            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
//        }
//        return view;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    private void setDefaultRequest() {
//        mRequest.setTypeCode("");
//        mRequest.setTypeName("");
//        mRequest.setKeyword("");
//        mRequest.setKeywordType("标题");
//        mRequest.setLevel("");
//        mRequest.setRequestType("");
//    }
//
//    @Subscribe(sticky = true)
//    public void onMessageEvent(EventMessage message) {
//        if (message.getFrom().equals("SelectFragment")) {
//            int index = 0;
//            //如果是三司则只查三司
//            if ("三司".equals(message.getSearchType())) {
//                mRequest.setLevel("");
//                mRequest.setTypeCode(message.getSelectMenu());
//                mRequest.setTypeName(message.getSearchType());
//            } else {
//                mRequest.setLevel(message.getSelectMenu());
//                index = StringUtils.indexOfArr(mTabTitle, message.getSelectMenu());
//            }
//            mTabLayout.getTabAt(index).select();
//            recomputeTlOffset1(index);
//            lawList = loadData();
//            if (mAdapter != null) {
//                mAdapter.setNewData(lawList);
//                mAdapter.notifyDataSetChanged();
//            }
//            return;
//        } else if (message.getFrom().equals("SSActivity")) {
//            mRequest.setLevel(message.getSelectMenu());
//            int index = StringUtils.indexOfArr(mTabTitle, message.getSelectMenu());
//            mTabLayout.getTabAt(index).select();
//            recomputeTlOffset1(index);
//            lawList = loadData();
//            if (mAdapter != null) {
//                mAdapter.setNewData(lawList);
//                mAdapter.notifyDataSetChanged();
//            }
//            return;
//        }
//    }
//
//    /**
//     * 重新计算需要滚动的距离
//     *
//     * @param index 选择的tab的下标
//     */
//    private void recomputeTlOffset1(int index) {
////        if (mTabLayout.getTabAt(index) != null) mTabLayout.getTabAt(index).select();
//        final int width = (int) (getTablayoutOffsetWidth(index) * getContext().getResources().getDisplayMetrics().density);
//        mTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mTabLayout.smoothScrollTo(width, 0);
//            }
//        });
//    }
////重中之重是这个计算偏移量的方法，各位看官看好了。
//
//    /**
//     * 根据字符个数计算偏移量
//     *
//     * @param index 选中tab的下标
//     * @return 需要移动的长度
//     */
//    private int getTablayoutOffsetWidth(int index) {
//        String str = "";
//        for (int i = 0; i < index; i++) {
//            //取出直到index的tab的文字，计算长度
//            str += mTabTitle[i];
//        }
//        return str.length() * 14 + index * 12;
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//
//    }
//
//
//    private List<LawItem> loadData() {
//        MyLawItemDao dao = new MyLawItemDao();
//        return dao.selctLawItemsByParam(mRequest, mPage, pageSize);
//    }
//
//    private void loadMoreData() {
//        if (isLastPage) {
//            setFooterView();
//            mAdapter.loadMoreEnd();
//            return;
//        }
//        mPage++;
//        List<LawItem> list = loadData();
//        LogGloble.d("loadMoreData", mPage + "");
////        if (list.size() > 0) {
////            mAdapter.loadMoreComplete();
////        }
//        if (list.size() < pageSize) {
//            isLastPage = true;
//            setFooterView();
//            mAdapter.loadMoreEnd();
//        }else{
//            mAdapter.loadMoreComplete();
//        }
//        mAdapter.addData(list);
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    private void initView(View view) {
//        mTabLayout = (TabLayout) view.findViewById(tabLayout);
//        mTabLayout.setOnTabSelectedListener(this);
//
//        mSearche = (ImageView) view.findViewById(R.id.iv_search);
//        mSearche.setOnClickListener(this);
//
//        mKeyword = (EditText) view.findViewById(R.id.et_keyworld);
//        mKeyword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mRequest.setKeyword(s.toString());
//                if (TextUtils.isEmpty(s.toString())) {
//                    mAdapter.setLight(false, mRequest);
//                    lawList = loadData();
//                    mAdapter.setNewData(lawList);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        mKeywordType = (LinearLayout) view.findViewById(R.id.layout_keyworldtype);
//        mKeywordType.setOnClickListener(this);
//
//        mKeywordTypeTV = (TextView) view.findViewById(R.id.tv_keywordtype);
//        mFilterTV = (TextView) view.findViewById(R.id.tv_filter);
//
//        mIndustryLayout = (LinearLayout) view.findViewById(R.id.layout_industry);
//        mIndustryLayout.setOnClickListener(this);
//
//        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_laws);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mAdapter = new LawBaseAdapter(lawList);
//        mAdapter.bindToRecyclerView(mRecyclerView);
//        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setEmptyView(R.layout.layout_empty_view);
//        mAdapter.disableLoadMoreIfNotFullPage();
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                LogGloble.d("setOnLoadMoreListener","==============");
//                loadMoreData();
//                mAdapter.notifyDataSetChanged();
//            }
//        }, mRecyclerView);
//        mAdapter.setOnItemClickListener(this);
//        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            public void onRefresh() {
//                isLastPage = false;
//                mPage = 0;
//                lawList = loadData();
//                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
//                mAdapter.setNewData(lawList);
//                mAdapter.notifyDataSetChanged();
//                mRefreshLayout.setRefreshing(false);
//            }
//        });
//    }
//
//    private void setFooterView() {
//        View footer = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null);
//        mAdapter.setFooterView(footer);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_search:
//                DialogManager.getInstance().showProgressDialog(getContext());
//                mPage = 0;
//                isLastPage = false;
//                mRequest.setKeyword(mKeyword.getText().toString());
//                lawList = loadData();
//                DialogManager.getInstance().dissMissProgressDialog();
//                mAdapter.setNewData(lawList);
//                mAdapter.setLight(true, mRequest);
//                mAdapter.notifyDataSetChanged();
//                break;
//            case R.id.layout_keyworldtype:
//                View keyworldView = DialogManager.getInstance().showPopupWindow(getContext(), mKeywordType, R.layout.layout_spinner);
//                mKeywordTypeSpinner = (ListView) keyworldView.findViewById(R.id.lv_spinner);
//                List<String> list = new ArrayList();
//                list.add("标题");
//                list.add("内容");
//                SpinnerAdapter adapter = new SpinnerAdapter(list, getContext());
//                mKeywordTypeSpinner.setAdapter(adapter);
//                mKeywordTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if (position == 0) {
//                            mRequest.setKeywordType("标题");
//                        }
//                        if (position == 1) {
//                            mRequest.setKeywordType("内容");
//                        }
//                        mKeywordTypeTV.setText(mRequest.getKeywordType());
//                        DialogManager.getInstance().dissMissPopupWindow();
//                    }
//                });
//                break;
//            case R.id.layout_industry:
//                View IndustryLayoutView = DialogManager.getInstance().showPopupWindow(getContext(), mIndustryLayout, R.layout.layout_spinnerlist);
//                mKeywordTypeSpinner = (ListView) IndustryLayoutView.findViewById(R.id.lv_spinner);
//                MyLawItemDao dao = new MyLawItemDao();
//                final List<String> filterList = dao.selctLawTypes();
//                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(filterList, getContext());
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
//
//                    }
//                });
//
//                break;
//        }
//    }
//
//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        LogGloble.d("Tab", "onTabSlected==="+tab.getText().toString());
//        if (TextUtils.isEmpty(tab.getText())) {
//            mIndustryLayout.setVisibility(View.GONE);
//            mRequest.setLevel("");
//            return;
//        }
//        if (tab.getText().toString().equals("全部")) {
//            mRequest.setLevel("");
//        }else{
//            mRequest.setLevel(tab.getText().toString());
//        }
////        else if (tab.getText().toString().equals("行业标准")) {
////            mRequest.setLevel("行业标准");
////            if("三司".equals(mRequest.getTypeName())){
////                mIndustryLayout.setVisibility(View.GONE);
////            }else{
////                mIndustryLayout.setVisibility(View.VISIBLE);
////            }
////        } else {
////            mIndustryLayout.setVisibility(View.GONE);
////            mRequest.setLevel(tab.getText().toString());
////        }
//        if ("三司".equals(mRequest.getTypeName())) {
//            mIndustryLayout.setVisibility(View.GONE);
//        } else {
//            mIndustryLayout.setVisibility(View.VISIBLE);
//        }
////        mIndustryLayout.setVisibility(View.VISIBLE);
//
//        isLastPage = false;
//        mPage = 0;
//        lawList = loadData();
//        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
//        mAdapter.setNewData(lawList);
//        mAdapter.notifyDataSetChanged();
//        mAdapter.setEnableLoadMore(true);
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//        LogGloble.d("Tab", "onTabUnselected===");
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//        LogGloble.d("Tab", "onTabReselected===");
//    }
//
//
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        LawItem item = (LawItem) adapter.getItem(position);
//        LogGloble.d("MainFragment", item.getFile_path() + "");
//        if (TextUtils.isEmpty(item.getFile_path())) {
//            ToastUtil.getInstance().toastInCenter(getContext(), "该文件不存在！");
//            return;
//        }
//        if (item.getFile_path().endsWith(".pdf")) {
//            Intent intent = new Intent(getContext(), PdfViewActivity.class);
//            intent.putExtra("URL", item.getFile_path());
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(getContext(), WebActivity.class);
//            intent.putExtra("URL", item.getFile_path());
//            startActivity(intent);
//        }
//    }
//}
