package com.dgsl.currencyconverter.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dgsl.currencyconverter.data.roomModal.CurrencyDataModel;
import com.dgsl.currencyconverter.data.roomModal.CurrencyRepository;

import java.util.List;

public class CurrencyConvertViewModel extends AndroidViewModel {

    // creating a new variable for course repository.
    private CurrencyRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<CurrencyDataModel>> allCourses;

    // constructor for our view modal.
    public CurrencyConvertViewModel(@NonNull Application application) {
        super(application);
        repository = new CurrencyRepository(application);
        allCourses = repository.getAllCourses();
    }

    // below method is use to insert the data to our repository.
    public void insert(CurrencyDataModel model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(CurrencyDataModel model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(CurrencyDataModel model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllCurrencyData() {
        repository.deleteAllCourses();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<CurrencyDataModel>> getAllCurrencyData() {
        return allCourses;
    }
}
