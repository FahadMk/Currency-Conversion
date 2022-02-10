package com.dgsl.currencyconverter.data.roomModal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CurrencyRepository {

    // below line is the create a variable
    // for dao and list for all courses.
    private CurrencyDao dao;
    private LiveData<List<CurrencyDataModel>> allCurrencyData;
    private LiveData<CurrencyDataModel> findCurrencyData;

    // creating a constructor for our variables
    // and passing the variables to it.
    public CurrencyRepository(Application application) {
        CurrencyDatabase database = CurrencyDatabase.getInstance(application);
        dao = database.Dao();
        allCurrencyData = dao.getAllCurrencyData();
        findCurrencyData = dao.findCurrencyData("");
    }

    // creating a method to insert the data to our database.
    public void insert(CurrencyDataModel model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(CurrencyDataModel model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(CurrencyDataModel model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<CurrencyDataModel>> getAllCourses() {
        return allCurrencyData;
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<CurrencyDataModel, Void, Void> {
        private CurrencyDao dao;

        private InsertCourseAsyncTask(CurrencyDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CurrencyDataModel... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateCourseAsyncTask extends AsyncTask<CurrencyDataModel, Void, Void> {
        private CurrencyDao dao;

        private UpdateCourseAsyncTask(CurrencyDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CurrencyDataModel... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<CurrencyDataModel, Void, Void> {
        private CurrencyDao dao;

        private DeleteCourseAsyncTask(CurrencyDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CurrencyDataModel... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CurrencyDao dao;
        private DeleteAllCoursesAsyncTask(CurrencyDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllCourses();
            return null;
        }
    }
}

