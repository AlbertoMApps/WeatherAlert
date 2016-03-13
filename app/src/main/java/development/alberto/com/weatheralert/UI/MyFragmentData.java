package development.alberto.com.weatheralert.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import development.alberto.com.weatheralert.Adapter.Fragment2Adapter;
import development.alberto.com.weatheralert.DataModel.SavedWeatherData;
import development.alberto.com.weatheralert.Model.WeatherInfo;
import development.alberto.com.weatheralert.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by alber on 11/03/2016.
 */
public class MyFragmentData extends Fragment{
    @Bind(R.id.rcSavedDataList) RecyclerView rcSavedDataList;
    @Bind(R.id.textDataAdded) TextView txtDataAddedNow;
    private Fragment2Adapter fragment2Adapter;
    private Realm mRealm;
    private RealmConfiguration realmConfiguration;
    private WeatherInfo weatherInfo;//data from the display option
    private SavedWeatherData weatherData; //data from the save db
    private ArrayList<SavedWeatherData> listSaved;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        ButterKnife.bind(this, view);
        //realmConfiguration
            realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();
            //Use of Realm realm for Android
        try{

            mRealm = Realm.getInstance(getActivity());
            this.clearDB(mRealm);
            mRealm.beginTransaction();
            if(weatherData==null) {
                weatherData = new SavedWeatherData();
            }
            weatherData = mRealm.createObject(SavedWeatherData.class); //It creates the constructor of the model class
            mRealm.commitTransaction();

        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                Realm.getInstance(realmConfiguration);

            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        mRealm.close();
    }





    ///Extra methods ------------------------------------------------------------------------------------

    private void getRecyclerView(RealmResults<SavedWeatherData> weatherData){ //add the information from the data saved to the recycler view
        rcSavedDataList.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 2));
        rcSavedDataList.setItemAnimator(new DefaultItemAnimator());

        // adapter set into recycler view
        //listSaved.add(weatherData); //-*****with a list, it is only going to work per session in the arrayList to configure to display for next sessions, we need to add a loop and select the data from db
        fragment2Adapter = new Fragment2Adapter(weatherData, R.layout.fragment2_row_layout,getActivity().getBaseContext());
        rcSavedDataList.setAdapter(fragment2Adapter);
    }


    //Realm objects
    public void setRealm(Realm realm) {
        mRealm = realm;
    }

    public void setRealmConfiguration(RealmConfiguration realmConfiguration) {
       this.realmConfiguration = realmConfiguration;
    }

    private void addIntoWeatherData(final int cont, final String cityName, final String temp, final String windSpeed, final String windDegree){
        mRealm.beginTransaction();
                weatherData.setCode(cont);
                weatherData.setCityName(cityName);
                weatherData.setTemperature(temp);
                weatherData.setWindSpeed(windSpeed);
                weatherData.setWindDegree(windDegree);
                 Log.i("model added ", "" + cont);
          mRealm.commitTransaction();
    }

    private void clearDB(Realm realm) { //Clear the db data
        realm.beginTransaction();
        realm.allObjects(SavedWeatherData.class).clear(); //specify the class you want to clear
        realm.commitTransaction();
    }

    //Testing data in fragment2
    public void infoReceived(WeatherInfo weatherModel){//we have got the weather info now!
     //Im going to check if theres previously any data saved, if so, compare if it is repeated with the data entered now...
        if (this.getAllFromSavedWeatherData()!=null) {
            //there are elements added before and we will check with the new dataModel entered if there are repeated or not...
            for (int i =0; i < this.getAllFromSavedWeatherData().size();i++) {
                //we will check if the element was already selected before and added to the list, so we can compare with the data upcoming...
                if (!weatherModel.getName().equals(this.getAllFromSavedWeatherData().get(i).getCityName())) {
                    this.txtDataAddedNow.setText("Current data for " + weatherModel.getName().toString() + " added... ");
                    //setting of the data obtained
                    setDataInfoToAdd(weatherModel);
                    //realm adding data...
                    mRealm.beginTransaction();
                    //int cont = mRealm.where(SavedWeatherData.class).max("code").intValue() + 1; // I increment the maximun pk +1. We always need at least one raw inserted..
                    mRealm.commitTransaction();
                    this.addIntoWeatherData(weatherModel.getId(), weatherModel.getName().toString(), weatherModel.getMain().getTemp().toString(), weatherModel.getWind().getSpeed().toString(), weatherModel.getWind().getDeg().toString());
                    addRecyclerViewFirst();
                    for (int j =0; j < this.getAllFromSavedWeatherData().size();j++){
                        Log.i("model added ", "" + this.getAllFromSavedWeatherData().get(j).getCityName());
                    }
                } else {
                    Toast.makeText(getActivity().getBaseContext(), "DATA ENTERED IS REPETED...Please, check the data added", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        } else { //its null
            this.txtDataAddedNow.setText("Current data for " + weatherModel.getName().toString() + " added... ");
            //setting of the data obtained
            setDataInfoToAdd(weatherModel);
            //realm adding data...
            mRealm.beginTransaction();
            int cont = mRealm.where(SavedWeatherData.class).max("code").intValue() + 1; // I increment the maximun pk +1. We always need at least one raw inserted..
            mRealm.commitTransaction();
            this.addIntoWeatherData(cont, weatherModel.getName().toString(), weatherModel.getMain().getTemp().toString(), weatherModel.getWind().getSpeed().toString(), weatherModel.getWind().getDeg().toString());
            addRecyclerViewFirst();
        }
    }

    private void addRecyclerViewFirst() {
        //recycler View
        //for (int i =0; i < this.getAllFromSavedWeatherData().size(); i++){
            getRecyclerView(this.getAllFromSavedWeatherData());


    }

    //Queries//...
    public RealmResults<SavedWeatherData> getAllFromSavedWeatherData (){
        //Queries
        mRealm.beginTransaction();
        RealmQuery<SavedWeatherData> query = mRealm.where(SavedWeatherData.class);
        RealmResults<SavedWeatherData> result = query.findAll();
        mRealm.commitTransaction();
        return result;
    }

    public void setDataInfoToAdd(WeatherInfo dataInfoToAdd){
            this.weatherInfo=dataInfoToAdd;
            }
    public WeatherInfo getWeatherInfo (){
            return this.weatherInfo;
        }
}

