package com.dgsl.currencyconverter.ui.currency_convert;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dgsl.currencyconverter.data.roomModal.CurrencyDataModel;
import com.dgsl.currencyconverter.model.CurrencyConvertViewModel;
import com.dgsl.currencyconverter.util.ConverterUtil;
import com.dgsl.currencyconverter.R;
import com.dgsl.currencyconverter.data.api.ApiClient;
import com.dgsl.currencyconverter.data.api.ApiInterface;
import com.dgsl.currencyconverter.data.model.ConversionRateListModel;
import com.dgsl.currencyconverter.data.model.Data;
import com.dgsl.currencyconverter.util.CurrencyDBHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyConvertFragment extends Fragment {
    public ArrayList<ConversionRateListModel> listModel = new ArrayList<>();
    View progressView;
    EditText inputValue,resultField;
    AutoCompleteTextView editTextFilledExposedDropDownToConvert;
    AutoCompleteTextView editTextFilledExposedDropdownFromConvert;
    AutoCompleteAdapter adapter;
    Button convertBtn;
    public static boolean toggle = false;
    public static boolean oneTimeDisplayToggle = true;
    CurrencyConvertViewModel viewModel;
    ConverterUtil converterUtil;
    View swapView;
    List<CurrencyDataModel> allCurrencyData = new ArrayList<>();

    public CurrencyConvertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideKeyboard(getView().getContext());
        converterUtil = new ConverterUtil(getView().getContext());
        progressView = getView().findViewById(R.id.progressView);
        View refreshBtn = getView().findViewById(R.id.refreshBtn);
        TextView updateTimeDate_TV = getView().findViewById(R.id.updateTimeDate_TV);
        inputValue = getView().findViewById(R.id.inputValue_TV);
        convertBtn = getView().findViewById(R.id.convert_Btn);
        resultField = getView().findViewById(R.id.result_EditText);
        resultField.setFocusable(false);
        resultField.setEnabled(false);
        swapView = getView().findViewById(R.id.swap_ImageView);

        //room
        viewModel  = new ViewModelProvider(this).get(CurrencyConvertViewModel.class);

        //dummy value
//        dummyData();
        if(converterUtil.isNetworkAvailable()){
            if(!toggle){
                getCurrencyDataAPICAll("INR");
                toggle = true;
            }
        }else{
            viewModel.getAllCurrencyData().observe(getViewLifecycleOwner(), new Observer<List<CurrencyDataModel>>() {
                @Override
                public void onChanged(List<CurrencyDataModel> models) {
                    // when the data is changed in our models we are
                    // adding that list to our adapter class.
                    allCurrencyData.clear();
                    allCurrencyData = models;
                    getCurrencyOfflineData("INR",allCurrencyData);
                }
            });
        }



        //first list to choose from value to convert
        editTextFilledExposedDropDownToConvert =
                getView().findViewById((R.id.filled_exposed_dropdown_convert_menu));


            //second list to convert to value
        editTextFilledExposedDropdownFromConvert =
                getView().findViewById((R.id.filled_exposed_dropdown_main));

        editTextFilledExposedDropdownFromConvert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(converterUtil.isNetworkAvailable()){
                        getCurrencyDataAPICAll(editTextFilledExposedDropdownFromConvert.getText().toString());
                    }else{
                        getCurrencyOfflineData(editTextFilledExposedDropdownFromConvert.getText().toString(),allCurrencyData);
                    }

                }
            });

        //set default value selected INR

        //get current time from device
        Date currentTime = Calendar.getInstance().getTime();
        updateTimeDate_TV.setText(""+currentTime);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTimeDate_TV.setText(""+currentTime);
            }
        });



        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getView().getContext());
                String baseCode = editTextFilledExposedDropdownFromConvert.getText().toString();
                double baseCurrencyCode = ConverterUtil.baseValue(baseCode,listModel);
                String toConvertCode = editTextFilledExposedDropDownToConvert.getText().toString();
                double toCurrencyCode = ConverterUtil.baseValue(toConvertCode,listModel);


                if(!toConvertCode.isEmpty() && !baseCode.isEmpty() && !inputValue.getText().toString().isEmpty()){
                    double resultValue = ConverterUtil.convertValue(Double.parseDouble(inputValue.getText().toString()),toCurrencyCode);
                    resultField.setText(""+resultValue);
                }else{
                    Toast.makeText(getView().getContext(), "Please enter required field", Toast.LENGTH_SHORT).show();
                }

            }
        });

        swapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editText1 = editTextFilledExposedDropdownFromConvert.getText().toString();
                String editText2 = editTextFilledExposedDropDownToConvert.getText().toString();
                if(!editText1.isEmpty() && !editText2.isEmpty()){
                    editTextFilledExposedDropdownFromConvert.setText(editText2,false);
                    editTextFilledExposedDropDownToConvert.setText(editText1,false);
                    getCurrencyDataAPICAll(editText1);
                }
            }
        });
    }


    private void hideKeyboard(Context context){
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency_convert, container, false);
    }

    private void dummyData(){
//        List<Data> currencyListData = new ArrayList<>();
//        Data
//        currencyListData.add()
        Data currencyData = new Data();
        currencyData.setaED(0.049671);
        currencyData.setaFN(1.412883);
        currencyData.setaLL(1.432517);
        currencyData.setaMD(6.475528);
        currencyData.setaOA(7.408681);
        currencyData.setaRS(1.401953);
        currencyData.setaUD(0.01856);

        listModel.add(new ConversionRateListModel("INR",1.0));
        listModel.add(new ConversionRateListModel("AED",0.049671));
        listModel.add(new ConversionRateListModel("AFN",1.412883));
        listModel.add(new ConversionRateListModel("ALL",1.432517));
        listModel.add(new ConversionRateListModel("AMD",0.049671));
        listModel.add(new ConversionRateListModel("AOA",6.475528));
        listModel.add(new ConversionRateListModel("ARS",1.401953));
        listModel.add(new ConversionRateListModel("AUD",0.01856));
        listModel.add(new ConversionRateListModel("JPY",1.542053));
        listModel.add(new ConversionRateListModel("CNY",0.085533));
        listModel.add(new ConversionRateListModel("CHF",0.012312));
        listModel.add(new ConversionRateListModel("CAD",0.016868));
        listModel.add(new ConversionRateListModel("MXN",0.273829));
        listModel.add(new ConversionRateListModel("BRL",0.073978));
        listModel.add(new ConversionRateListModel("RUB",1.032858));
        listModel.add(new ConversionRateListModel("KRW",16.000906));
        listModel.add(new ConversionRateListModel("IDR",192.689383));



    }

    private void getCurrencyDataAPICAll(String name){
        try{
            CurrencyDBHelper db = new CurrencyDBHelper(getContext());
            progressView.setVisibility(View.VISIBLE);
            ApiInterface apiInterface;
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            ApiClient apiClient = new ApiClient();
            Call<JsonObject> CurrencyDataCall = apiInterface.getCurrencyConversion(apiClient.apiKey, name);

            CurrencyDataCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject obj = response.body().getAsJsonObject("data");
                    try {
                        listModel.clear();
                        listModel = ConverterUtil.convertJsonObject(obj);
                        putDataInDropDownMenu(listModel);
                        viewModel.insert(new CurrencyDataModel(name,obj.toString()));
                        progressView.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    Log.e("TAG", "Success: " + response.body());
                }
                @Override
                public void onFailure(Call <JsonObject> callback, Throwable t) {
                    Log.e("TAG", "onFailure: " + t.getLocalizedMessage() );
                    progressView.setVisibility(View.GONE);
                }
            });
        }catch (Exception e){
            Log.e("ERROR_here", "onFailure: " + e.getLocalizedMessage() );
            progressView.setVisibility(View.GONE);
        }

    }

    private void getCurrencyOfflineData(String code, List<CurrencyDataModel> models) {
        try{
            listModel.clear();
            String json = ConverterUtil.findCurrencyDataHelper(code,models);
            if(!json.isEmpty() && !models.isEmpty()){
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                listModel = ConverterUtil.convertJsonObject(jsonObject);
                putDataInDropDownMenu(listModel);
            }else{
                Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "no offline data available", Snackbar.LENGTH_LONG);
                snackBar.show();
//              getCurrencyDataAPICAll("INR");
            }

        }catch(Exception e){

        }
    }

    private void putDataInDropDownMenu(ArrayList<ConversionRateListModel> models){
        adapter = new AutoCompleteAdapter(getContext(),
                R.layout.drop_down_menu_item,models);
        editTextFilledExposedDropdownFromConvert.setAdapter(adapter);
        editTextFilledExposedDropDownToConvert.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(oneTimeDisplayToggle){
            ConversionRateListModel conversionRateListModel = (ConversionRateListModel) editTextFilledExposedDropDownToConvert.getAdapter().getItem(6);
            editTextFilledExposedDropdownFromConvert.setText(conversionRateListModel.currency_code, false);
            oneTimeDisplayToggle = false;
        }
    }

}