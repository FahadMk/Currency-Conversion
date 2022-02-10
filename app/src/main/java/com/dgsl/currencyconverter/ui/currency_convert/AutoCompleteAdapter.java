package com.dgsl.currencyconverter.ui.currency_convert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dgsl.currencyconverter.R;
import com.dgsl.currencyconverter.data.model.ConversionRateListModel;
import com.dgsl.currencyconverter.util.CountryFlagUtil;

import java.util.ArrayList;

public class AutoCompleteAdapter extends ArrayAdapter<ConversionRateListModel> {
    private ArrayList<ConversionRateListModel> dataList;
    private Context mContext;
    private int itemLayout;
    private CountryFlagUtil countryFlagUtil = new CountryFlagUtil();

    public AutoCompleteAdapter(Context context, int resource, ArrayList<ConversionRateListModel> storeDataList) {
        super(context, resource, storeDataList);
        this.dataList = storeDataList;
        this.mContext= context;
        this.itemLayout = resource;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public ConversionRateListModel getItem(int position) {
        return dataList.get(position);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            view.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }
        ConversionRateListModel model = getItem(position);
        if(model != null){
            TextView itemTextView = view.findViewById(R.id.textView);
            TextView flagTextView = view.findViewById(R.id.dropdown_flag);
            itemTextView.setText(model.currency_code.toString());
            String flag = model.toString().substring(0,2);
            // System.out.println("ValueIssue  ---- "+(CharSequence) model.toString());
            flagTextView.setText(countryFlagUtil.getCountryFlagByCountryCode(flag));
        }
        return view;
    }

}
