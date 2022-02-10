package com.dgsl.currencyconverter.data.model;

public class ConversionRateListModel {
    public String  currency_code;
    public double currency_rates;

    public ConversionRateListModel(String name, double rate){
        this.currency_code = name;
        this.currency_rates = rate;
    }

    public String toString() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public double getCurrency_rates() {
        return currency_rates;
    }

    public void setCurrency_rates(double currency_rates) {
        this.currency_rates = currency_rates;
    }
}
