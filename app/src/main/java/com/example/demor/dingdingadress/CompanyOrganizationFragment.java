package com.example.demor.dingdingadress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 通讯录-公司组织架构
 */
public class CompanyOrganizationFragment extends Fragment  {
    //下面正常数据，头部部门数据
    private RecyclerView rv_list,rv_parts;
    private EditText et_content;
    private RelativeLayout rl_search_delete;
    private FragmentTransaction ft;
    private String name;
    private String groupname,group_id;
    private ArrayList<String> group_list;
    private ArrayList<AdressCompanyListBean> toplistBeans;
    private ArrayList<AdressCompanyListBean> listBeans;
    private AddressCompanyOrganizationAdapter organizationAdapter;
    private AddressCompanyTopAdapter topAdapter;

    public static CompanyOrganizationFragment instantiation(int position) {
        CompanyOrganizationFragment fragment = new CompanyOrganizationFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_company_organi,container,false);
        return view;
    }

    public void setListAdapter(BaseQuickAdapter adapter) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(linearLayoutManager);
    }

    public void setTopListAdapter(BaseQuickAdapter adapter) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv_parts.setAdapter(adapter);
        rv_parts.setLayoutManager(linearLayoutManager);
    }

    public void jumpItemchildL(String groupname) {
        ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        CompanyOrganizationFragment demoFragment = new CompanyOrganizationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("groupname",groupname);
        bundle.putString("fid",groupname);
        demoFragment.setArguments(bundle);
        ft.replace(R.id.rl_main, demoFragment,groupname);
        ft.addToBackStack("company");
        ft.commit();
    }

    public void jumpTopFragment(int position, String groupname) {
        int count=getActivity().getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count - position; i++) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv_list=getView().findViewById(R.id.rv_list);
        rv_parts=getView().findViewById(R.id.rv_parts);
        et_content = (EditText)getView().findViewById(R.id.et_content);
        rl_search_delete = (RelativeLayout)getView().findViewById(R.id.rl_search_delete);
        if (getArguments()!=null){
            group_id=getArguments().getString("fid","");
            groupname=getArguments().getString("groupname");
            group_list=getArguments().getStringArrayList("grouplist");
            Log.e("ss","group_id="+group_id+"-groupname"+groupname);
        }

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
//                    mPresenter.noSearchResult(R.string.txt_search_no_person);
                    rl_search_delete.setVisibility(View.GONE);
                    return;
                }
                rl_search_delete.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listBeans=new ArrayList<>();
        toplistBeans=new ArrayList<AdressCompanyListBean>();
        organizationAdapter=new AddressCompanyOrganizationAdapter(listBeans);
        setListAdapter(organizationAdapter);
        organizationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type=adapter.getItemViewType(position);
                String groupname=((AdressCompanyListBean)adapter.getItem(position)).getName();
                if (type==0){
                    jumpItemchildL(groupname);
                }else if (type==1){//跳转到个人详情页
                    Intent intent=new Intent(getActivity(),PersonDetailActivity.class);
                    intent.putExtra("name",groupname);
                    getActivity().startActivity(intent);
                }
            }
        });
        topAdapter=new AddressCompanyTopAdapter(R.layout.item_adress_company_organi_top,toplistBeans);
        setTopListAdapter(topAdapter);
        topAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String groupname=((AdressCompanyListBean)adapter.getItem(position)).getName();
                jumpTopFragment(position,groupname);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(group_id);
        EventBus.getDefault().postSticky(groupname);
    }
    //模拟获取数据，正常应该是请求后台返回数据
    public void getData(String groupid){
        listBeans.clear();
        toplistBeans.clear();
        if ("公司".equals(groupid)){
            AdressCompanyListBean bean1=new AdressCompanyListBean();
            bean1.setCount(0);
            bean1.setName("一级部门");
            AdressCompanyListBean bean4=new AdressCompanyListBean();
            bean4.setCount(1);
            bean4.setName("一级老板");
            listBeans.add(bean1);
            listBeans.add(bean4);

            AdressCompanyListBean bean2=new AdressCompanyListBean();
            bean2.setName("公司");
            toplistBeans.add(bean2);

        }else if ("一级部门".equals(groupid)){
            AdressCompanyListBean bean1=new AdressCompanyListBean();
            bean1.setCount(0);
            bean1.setName("二级部门");
            AdressCompanyListBean bean4=new AdressCompanyListBean();
            bean4.setCount(1);
            bean4.setName("二级用户");
            listBeans.add(bean1);
            listBeans.add(bean4);

            AdressCompanyListBean bean2=new AdressCompanyListBean();
            bean2.setName("公司");
            AdressCompanyListBean bean3=new AdressCompanyListBean();
            bean3.setName("一级部门");
            toplistBeans.add(bean2);
            toplistBeans.add(bean3);
        }else if ("二级部门".equals(groupid)){
            AdressCompanyListBean bean1=new AdressCompanyListBean();
            bean1.setCount(0);
            bean1.setName("三级部门");
            AdressCompanyListBean bean4=new AdressCompanyListBean();
            bean4.setCount(1);
            bean4.setName("三级用户");
            listBeans.add(bean1);
            listBeans.add(bean4);

            AdressCompanyListBean bean2=new AdressCompanyListBean();
            bean2.setName("公司");
            AdressCompanyListBean bean3=new AdressCompanyListBean();
            bean3.setName("一级部门");
            AdressCompanyListBean bean5=new AdressCompanyListBean();
            bean5.setName("二级部门");
            toplistBeans.add(bean2);
            toplistBeans.add(bean3);
            toplistBeans.add(bean5);
        }else if ("三级部门".equals(groupid)){
            AdressCompanyListBean bean4=new AdressCompanyListBean();
            bean4.setCount(1);
            bean4.setName("四级用户1");
            AdressCompanyListBean bean5=new AdressCompanyListBean();
            bean5.setCount(1);
            bean5.setName("四级用户2");
            listBeans.add(bean4);
            listBeans.add(bean5);

            AdressCompanyListBean bean2=new AdressCompanyListBean();
            bean2.setName("公司");
            AdressCompanyListBean bean3=new AdressCompanyListBean();
            bean3.setName("一级部门");
            AdressCompanyListBean bean6=new AdressCompanyListBean();
            bean6.setName("二级部门");
            AdressCompanyListBean bean7=new AdressCompanyListBean();
            bean7.setName("三级部门");
            toplistBeans.add(bean2);
            toplistBeans.add(bean3);
            toplistBeans.add(bean6);
            toplistBeans.add(bean7);
        }

        if (toplistBeans.size()>0 && toplistBeans.size()<=1){
            toplistBeans.get(0).setCount(0);
        }else {
            for (int i = 0; i <toplistBeans.size() ; i++) {
                if (i == toplistBeans.size()-1){
                    toplistBeans.get(i).setCount(0);
                }else {
                    toplistBeans.get(i).setCount(1);
                }
            }
        }

        organizationAdapter.setNewData(listBeans);
        topAdapter.setNewData(toplistBeans);

    }

}
