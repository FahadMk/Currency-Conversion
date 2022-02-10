package com.dgsl.currencyconverter.data.model;

public class Query{
	private String baseCurrency;
	private String apikey;
	private int timestamp;

	public String getBaseCurrency(){
		return baseCurrency;
	}

	public String getApikey(){
		return apikey;
	}

	public int getTimestamp(){
		return timestamp;
	}
}
