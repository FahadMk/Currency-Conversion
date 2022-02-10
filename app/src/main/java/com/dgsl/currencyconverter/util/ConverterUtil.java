package com.dgsl.currencyconverter.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

import com.dgsl.currencyconverter.data.model.ConversionRateListModel;
import com.dgsl.currencyconverter.data.roomModal.CurrencyDataModel;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ConverterUtil {

    Context context;
    public ConverterUtil(Context context){
        this.context = context;
    }

    /**
     * @param selectedValue currency rate to which need to converted
     * @param toInputValue user entered amount to be converted
     * @return returns the converted value which is formatted to decimal value
     * calculate the conversion rate for given amount
     */
    public static double convertValue(double selectedValue, double toInputValue){
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(toInputValue*selectedValue));
    }


    /**
     * @param searchCode search the currency code with in the list of object
     * @param dataList list of currency value object
     * @return returns the base currency rate of the search code
     *
     * get the conversion rate from given base code from the list
     */
    public static double baseValue(String searchCode, ArrayList<ConversionRateListModel> dataList){
        for(ConversionRateListModel d : dataList){
            if(d.toString() != null && d.toString().contains(searchCode)){
               return d.currency_rates;
            }
        }
        return 0.0;
    }

    /**
     * @param code string input for searching the currency code in the list.
     * @param models list of room data object which is stored in the device.
     * @return returns the currency object of the specified code.
     */
    public static String findCurrencyDataHelper(String code,List<CurrencyDataModel> models){
        for (CurrencyDataModel model : models) {
            if (model.getCurrencyCodeName().equals(code)) {
                return model.getCurrencyDataObject();
            }
        }
        return null;
    }


    /**
     * @param jsonObj the value received from API pass the json Object
     * @return converts the jsonObject to a list of object and returns the list.
     * @throws JSONException for handling exception in case of empty object
     *
     * to convert the json object values inside to list
     */
    public static ArrayList<ConversionRateListModel> convertJsonObject(JsonObject jsonObj) throws JSONException {
        ArrayList<ConversionRateListModel> rateListModels = new ArrayList<>();
        for (Object key : jsonObj.keySet()) {
            String keyStr = (String) key;
            Object keyValue = jsonObj.get(keyStr);

            if (!(keyValue instanceof JSONObject)) {
                rateListModels.add(new ConversionRateListModel(keyStr,new Double(keyValue.toString())));
            }
        }
        return rateListModels;
    }

    /**
     * @return returns boolean after checking whether the device is network available
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
