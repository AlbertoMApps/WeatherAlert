package development.alberto.com.weatheralert.UI;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import development.alberto.com.weatheralert.Api.InterfaceDataSent;
import development.alberto.com.weatheralert.Api.WeatherApi;
import development.alberto.com.weatheralert.Constants.Constant;
import development.alberto.com.weatheralert.DataModel.SavedWeatherData;
import development.alberto.com.weatheralert.Model.Weather;
import development.alberto.com.weatheralert.Model.WeatherInfo;
import development.alberto.com.weatheralert.R;
import development.alberto.com.weatheralert.Tabs.SlidingTabsLayout;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements InterfaceDataSent{

    private RestAdapter mRestAdapt;
    private Realm realm;
    private RealmConfiguration realmConfiguration;
    @Bind(R.id.editText) EditText textEntered;
    private ProgressDialog progressDialog;
    private WeatherApi mApi;
    private String dataEntered;
//    @Bind(R.id.pager) ViewPager mPager;
//    @Bind(R.id.tabs) SlidingTabsLayout mTabs;
    @Bind(R.id.btnAddData) Button btnAddData;
    private WeatherInfo weatherInfo, weatherInfo2;
   // private MyPagerAdapter myPagerAdapter;
    private final String dataSaved = "dataSavedState";

    @OnClick(R.id.btnSearch)
    void btnSearch() { // in this case we send the information to the fragments with a setter
            dataEntered = textEntered.getText().toString().trim();
            progressDialog.show();
            if (!dataEntered.isEmpty()) {
                //if (myPagerAdapter == null) {
                    getWeatherData(dataEntered);
                    btnAddData.setVisibility(View.VISIBLE); //set the button of the fragment1 visible..
               // } else {
                    //updateWeather(dataEntered);
               // }
                //Toast.makeText(getApplicationContext(), dataEntered, Toast.LENGTH_SHORT).show();
            } else {
                textEntered.setError("Type a city name...");
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Realm configuration...
//        realmConfiguration = new RealmConfiguration.Builder(this).build();
//
//        try {
//            //Use of Realm realm for Android
//            realm = Realm.getInstance(this);
//            updateData(realm);
//
//        } catch (RealmMigrationNeededException e) {
//            try {
//                Realm.deleteRealm(realmConfiguration);
//                //Realm file has been deleted.
//                Realm.getInstance(realmConfiguration);
//            } catch (Exception ex) {
//                throw ex;
//                //No Realm file to remove.
//            }
//
//        }

//        if(savedInstanceState!=null){  //saved state
//            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//            mPager.setAdapter(myPagerAdapter);
//            mTabs.setViewPager(mPager);
//            weatherInfo2 = savedInstanceState.getParcelable(this.dataSaved);
//            MyFragment f =((MyFragment) myPagerAdapter.getItem(0));
//            f.setWeatherModel(weatherInfo2);
//            f.updateViews();
//        }

//        Retrofit data example
//        restAdapter of the model Weather
        mRestAdapt = new RestAdapter.Builder()
                .setEndpoint(Constant.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mApi = mRestAdapt.create(WeatherApi.class);
        //progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading information...");

    }

    //Extra methods-------------------------------------------------------------------------------------

 /**   private void updateWeather(String cityName) { ///update the data infor in case it is already displaying in the tabs before and new data is entered by the user...
        mApi.getWeatherInfo(cityName, new Callback<WeatherInfo>() { //recreate the fragments
            @Override
            public void success(WeatherInfo weatherInfo, Response response) {
                progressDialog.dismiss();
                weatherInfo2 = weatherInfo;
                MyFragment f = ((MyFragment) myPagerAdapter.getItem(0));
                f.setWeatherModel(weatherInfo);
                f.updateViews();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
            }
        });
    }
**/
//    @Override
//    protected void onSaveInstanceState(Bundle outState) { //save the state if the data model is already there
//        super.onSaveInstanceState(outState);
//        if(weatherInfo2!=null){
//            outState.putParcelable(dataSaved, weatherInfo);
//        }
//
//    }

    private void getWeatherData(String cityName) { //get the data for the first time and when it has more data info
        mApi.getWeatherInfo(cityName, new Callback<WeatherInfo>() {
            @Override
            public void success(WeatherInfo weatherModel, Response response) {
                progressDialog.dismiss();
                Log.i("API", "success: COOL");
//                myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//                mPager.setAdapter(myPagerAdapter);
//                mTabs.setViewPager(mPager);
               // MyFragment f = ((MyFragment) myPagerAdapter.getItem(0));
                MyFragment fragment1 = (MyFragment) getFragment1();
                fragment1.setWeatherModel(weatherModel);
                fragment1.updateViews();
                // windSpeed.setText("Weather speed: " + weatherModel.getWind().getSpeed());//test retrofit
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.i("API", "failure: error: " + error.toString());

            }
        });
    }

    @Override
    public void sendData(WeatherInfo weatherModel) {
        MyFragmentData fragment2= getFragment2();
        fragment2.infoReceived(weatherModel);
    }

    public MyFragment getFragment1(){
        android.app.FragmentManager fragmentManager = getFragmentManager();
        MyFragment fragment1=(MyFragment) fragmentManager.findFragmentById(R.id.fragment1);
        return fragment1;
    }
    public MyFragmentData getFragment2(){
        android.app.FragmentManager fragmentManager = getFragmentManager();
        MyFragmentData fragment2=(MyFragmentData) fragmentManager.findFragmentById(R.id.fragment2);
        return fragment2;
    }

//    private void updateData(Realm realm){
//        MyFragmentData fd = ((MyFragmentData) myPagerAdapter.getItem(1));
//        fd.setRealm(realm);
//        fd.setRealmConfiguration(realmConfiguration);
//    }

    ///---------------------------------------------------------------------------------------------------

    //inner class for the pagerAdapter...
 /**   public class MyPagerAdapter extends FragmentPagerAdapter {
        Resources res = getResources();
        String[] tabs = res.getStringArray(R.array.tabs);
        MyFragment mFragment;
        MyFragmentData mFragmentData;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            //fm.beginTransaction().addToBackStack("null");
            mFragment = new MyFragment();
            mFragmentData = new MyFragmentData();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
//            MyFragment myFragment = MyFragment.getInstance(position);
//            return myFragment;
            switch (position) {
                case 0:
                    return mFragment;
                case 1:
                    return mFragmentData;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

    }**/

}//End of class
