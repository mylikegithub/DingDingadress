package com.example.demor.dingdingadress;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 通讯录适配器
 */
public class AddressCompanyTopAdapter extends BaseQuickAdapter<AdressCompanyListBean, BaseViewHolder> {

    public AddressCompanyTopAdapter(int layoutResId,@Nullable List<AdressCompanyListBean> data) {
        super(layoutResId,data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, AdressCompanyListBean item) {
        helper.setText(R.id.tv_name,item.getName());
        if (item.getCount()==0){
            helper.setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.black));
        }else if (item.getCount()==1){//蓝色
            helper.setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.c_4d7cfe));
        }
    }
}
