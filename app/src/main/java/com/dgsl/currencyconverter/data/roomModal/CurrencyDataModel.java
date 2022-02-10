package com.dgsl.currencyconverter.data.roomModal;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency_table")
public class CurrencyDataModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "currency_code_name")
    private String currencyCodeName;

    @ColumnInfo(name = "currency_object_name")
    private String currencyDataObject;

    public CurrencyDataModel(String currencyCodeName, String currencyDataObject) {
        this.currencyCodeName = currencyCodeName;
        this.currencyDataObject = currencyDataObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyCodeName() {
        return currencyCodeName;
    }

    public void setCurrencyCodeName(String currencyCodeName) {
        this.currencyCodeName = currencyCodeName;
    }

    public String getCurrencyDataObject() {
        return currencyDataObject;
    }

    public void setCurrencyDataObject(String currencyDataObject) {
        this.currencyDataObject = currencyDataObject;
    }
}
