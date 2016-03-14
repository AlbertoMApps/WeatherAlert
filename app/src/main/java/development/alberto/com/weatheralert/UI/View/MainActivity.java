package development.alberto.com.weatheralert.UI.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import development.alberto.com.weatheralert.Api.InterfaceDataSent;
import development.alberto.com.weatheralert.Api.WeatherApi;
import development.alberto.com.weatheralert.Constants.Constant;
import development.alberto.com.weatheralert.Models.Model.Weather;
import development.alberto.com.weatheralert.Models.Model.WeatherInfo;
import development.alberto.com.weatheralert.MyApp;
import development.alberto.com.weatheralert.R;
import development.alberto.com.weatheralert.UI.Presenter.MainPresenter;
import development.alberto.com.weatheralert.UI.Presenter.MainView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements InterfaceDataSent, MainView{

    //private RestAdapter mRestAdapt;
    @Inject RestAdapter restAdapter;
    //private Realm realm;
   // @Inject Realm realm;
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
    private MainPresenter mainPresenter;

    @OnClick(R.id.btnSearch)
    void btnSearch() { // in this case we send the information to the fragments with a setter
            dataEntered = textEntered.getText().toString().trim();
            progressDialog.show();
            if (!dataEntered.isEmpty()) {
                    getWeatherData(dataEntered);
                    btnAddData.setVisibility(View.VISIBLE); //set the button of the fragment1 visible..
            } else {
                textEntered.setError("Type a city name...");
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this);
//        if(savedInstanceState!=null){  //saved state
//            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//            mPager.setAdapter(myPagerAdapter);
//            mTabs.setViewPager(mPager);
//            weatherInfo2 = savedInstanceState.getParcelable(this.dataSaved);
//            MyFragment f =((MyFragment) myPagerAdapter.getItem(0));
//            f.setWeatherModel(weatherInfo2);
//            f.updateViews();
//        }

//        restAdapter of the model Weather---Done with dagger 2 now...
//        mRestAdapt = new RestAdapter.Builder()
//                .setEndpoint(Constant.BASE_URL)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .build();
//        mApi = mRestAdapt.create(WeatherApi.class);
        //progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading information...");

    }

    //Extra methods-------------------------------------------------------------------------------------
//    @Override
//    protected void onSaveInstanceState(Bundle outState) { //save the state if the data model is already there
//        super.onSaveInstanceState(outState);
//        if(weatherInfo2!=null){
//            outState.putParcelable(dataSaved, weatherInfo);
//        }
//
//    }

    private void getWeatherData(String cityName) { //get the data for the first time and when it has more data info
        getApi().getWeatherInfo(cityName, new Callback<WeatherInfo>() {
            @Override
            public void success(WeatherInfo weatherModel, Response response) {
                progressDialog.dismiss();
                Log.i("API", "success: COOL");
                MyFragment fragment1 = (MyFragment) getFragment1();
                fragment1.setWeatherModel(weatherModel);
                fragment1.updateViews();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.i("API", "failure: error: " + error.toString());

            }
        });
    }

    @Override //Send data to the fragment 2
    public void sendData(WeatherInfo weatherModel) {
        MyFragmentData fragment2= getFragment2();
        fragment2.infoReceived(weatherModel);
    }
    @Override
    public MyFragment getFragment1(){
        android.app.FragmentManager fragmentManager = getFragmentManager();
        MyFragment fragment1=(MyFragment) fragmentManager.findFragmentById(R.id.fragment1);
        return fragment1;
    }
    @Override
    public MyFragmentData getFragment2(){
        android.app.FragmentManager fragmentManager = getFragmentManager();
        MyFragmentData fragment2=(MyFragmentData) fragmentManager.findFragmentById(R.id.fragment2);
        return fragment2;
    }

    @Override
    public String getUserName() {
        return dataEntered;
    }
    @Override
    public void showUserNameError(int error) {
       this.textEntered.setText(getString(error));
    }

    public WeatherApi getApi(){
        ((MyApp) getApplicationContext()).getmNetComponent().inject(this); //we need to implement it to inject the object, although it works without it too
        restAdapter = ((MyApp) getApplicationContext()).getmNetComponent().provideRetrofit();
        return restAdapter.create(WeatherApi.class);
    }


}//End of class
