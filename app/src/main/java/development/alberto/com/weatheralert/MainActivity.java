package development.alberto.com.weatheralert;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import development.alberto.com.weatheralert.Api.InterfaceDataSent;
import development.alberto.com.weatheralert.Api.WeatherApi;
import development.alberto.com.weatheralert.Constants.Constant;
import development.alberto.com.weatheralert.Model.WeatherInfo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements InterfaceDataSent {

    private RestAdapter mRestAdapt;
    @Bind(R.id.editText)
    EditText textEntered;
    @Bind(R.id.speed)
    TextView windSpeed;
    private ProgressDialog progressDialog;
    private WeatherApi mApi;
    private String dataEntered;
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.tabs)
    SlidingTabsLayout mTabs;

    @OnClick(R.id.btnSearch)
    void btnSearch() {
        if (textEntered != null) {
            dataEntered = textEntered.getText().toString();
            progressDialog.show();
            getWeatherData(dataEntered);
            Toast.makeText(getApplicationContext(), dataEntered, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    //Extra methods
    @Override
    public String sendData() {
        return dataEntered;
    }

    private String getDataEntered() {
        return dataEntered.toString();
    }

    private void getWeatherData(String cityName) {
        mApi.getWeatherInfo(cityName, new Callback<WeatherInfo>() {
            @Override
            public void success(WeatherInfo weatherModel, Response response) {
                progressDialog.dismiss();
                Log.i("API", "success: COOL");
                MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

//                Bundle args = new Bundle();
//                args.putParcelable("weatherModel",weatherModel);
                MyFragment fragmentInfo = new MyFragment();
                fragmentInfo.sendData(weatherModel);

                mPager.setAdapter(myPagerAdapter);
                mTabs.setViewPager(mPager);
                //myPagerAdapter.sendWeatherInfo(weatherModel);
                windSpeed.setText("Weather speed: " + weatherModel.getWind().getSpeed());
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.i("API", "failure: error: " + error.toString());

            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        Resources res = getResources();
        String[] tabs = res.getStringArray(R.array.tabs);
        //private  WeatherInfo weatherModel;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
//            MyFragment myFragment = new MyFragment();
//            myFragment.setPosition(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }


//        public void sendWeatherInfo(WeatherInfo weatherModel) {
//            this.weatherModel = weatherModel;
//        }
//        public WeatherInfo getWeatherInfo() {
//            return this.weatherModel;
//        }

    }

}
