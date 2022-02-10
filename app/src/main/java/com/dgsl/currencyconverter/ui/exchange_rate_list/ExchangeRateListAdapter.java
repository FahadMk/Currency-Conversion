package com.dgsl.currencyconverter.ui.exchange_rate_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgsl.currencyconverter.R;
import com.dgsl.currencyconverter.data.model.ConversionRateListModel;
import com.dgsl.currencyconverter.util.CountryFlagUtil;

import java.util.List;

public class ExchangeRateListAdapter extends RecyclerView.Adapter<ExchangeRateListAdapter.MyViewHolder> {

    private Context mContext;
    private List<ConversionRateListModel> mData;
    private ExchangeRateOnItemClickListener cityOnItemClickListener;
    private CountryFlagUtil countryFlagUtil = new CountryFlagUtil();

    public ExchangeRateListAdapter(Context mContext, List<ConversionRateListModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
//        this.cityOnItemClickListener = cityOnItemClickListener;

    }

    public void Update(List<ConversionRateListModel> newData){
        this.mData.clear();
        this.mData = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.exchange_list_item,parent, false);

        return new MyViewHolder(v,cityOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mData.get(position).toString());
        holder.description.setText(""+mData.get(position).currency_rates);
        String flagCode = mData.get(position).toString().substring(0,2);
        holder.flag.setText(countryFlagUtil.getCountryFlagByCountryCode(flagCode));
//        Glide.with(mContext)
//                .load(mData.get(position).getImage_url())
//                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView description;
        ImageView image;
        TextView flag;
//        ExchangeRateOnItemClickListener exchangeRateOnItemClickListener;
        public MyViewHolder(@NonNull View itemView, ExchangeRateOnItemClickListener cityOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.exchangeCodeItem);
            description = itemView.findViewById(R.id.exchangeValueItem);
            flag = itemView.findViewById(R.id.flag_TV);
//            image = itemView.findViewById(R.id.image_view);
//            this.exchangeRateOnItemClickListener = cityOnItemClickListener;

        }

        @Override
        public void onClick(View view) {
            //position value starts from 0
            Log.d("ADAPTER_POSITION" ,"onClick " + getAdapterPosition() + 1);
//            exchangeRateOnItemClickListener.onAdapterItemClick(getAdapterPosition() + 1);
        }
    }
    public interface ExchangeRateOnItemClickListener {
        void onAdapterItemClick(int position);
    }
}

