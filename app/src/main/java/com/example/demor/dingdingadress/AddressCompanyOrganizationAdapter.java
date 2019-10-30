package com.example.demor.dingdingadress;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 通讯录适配器
 */
public class AddressCompanyOrganizationAdapter extends BaseQuickAdapter<AdressCompanyListBean, BaseViewHolder> {

    private final int TYPE_BUMEN=0;//部门类型的
    private final int TYPE_PERSON=1;//个人的

    public AddressCompanyOrganizationAdapter(@Nullable List<AdressCompanyListBean> data) {
        super(data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        AdressCompanyListBean adressCompanyListBean=mData.get(position);
        if (adressCompanyListBean.getCount()==0){
            return TYPE_BUMEN;
        }else if (adressCompanyListBean.getCount()==1){
            return TYPE_PERSON;
        }
        return 0;
    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0){
            return new BumenViewHolder(getItemView(R.layout.item_adress_company_bumen, parent));
        }if (viewType==1){
            return new BumenViewHolder(getItemView(R.layout.item_adress_company_bumen_person, parent));
        }
        return new BumenViewHolder(getItemView(R.layout.item_adress_company_bumen, parent));
    }

    @Override
    protected void convert(final BaseViewHolder helper, AdressCompanyListBean item) {
        if (helper instanceof BumenViewHolder){
            BumenViewHolder bumenViewHolder=(BumenViewHolder)helper;
            bumenViewHolder.setData(helper,item);
        }else if (helper instanceof BumenPersonViewHolder){
            BumenPersonViewHolder personViewHolder=(BumenPersonViewHolder)helper;
            personViewHolder.setData(helper,item);
        }
    }

    /**
     * bumen
     */
    class BumenViewHolder extends BaseViewHolder {

        public BumenViewHolder(View view) {
            super(view);

        }
        public void setData(BaseViewHolder helper, AdressCompanyListBean bean){
            TextView tv_name = helper.getView(R.id.tv_name);
            tv_name.setText(bean.getName());
        }
    }
    /**
     * person
     */
    class BumenPersonViewHolder extends BaseViewHolder {
        public BumenPersonViewHolder(View view) {
            super(view);
        }
        public void setData(BaseViewHolder helper, AdressCompanyListBean bean){
            TextView tv_name = helper.getView(R.id.tv_name);
            tv_name.setText(bean.getName());
        }
    }
}
