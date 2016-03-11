package development.alberto.com.weatheralert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import development.alberto.com.weatheralert.Model.WeatherInfo;

/**
 * Created by alber on 11/03/2016.
 */
public class MyFragment extends Fragment {
    private TextView txtView, txtViewSpeed, txtViewDegress, txtViewSpeedStore, txtViewDegressStore;
    private int fragmentPosition;
    private WeatherInfo weatherModel;

    public MyFragment() {
        super();
    }

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }

//        args.putString("windSpeed", Stringing.valueOf(weatherModel.getWind().getSpeed()));
//        args.putString("windDegress", String.valueOf(weatherModel.getWind().getDeg()));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_layout, container, false);
        txtView = (TextView) layout.findViewById(R.id.position);
        txtViewSpeed = (TextView) layout.findViewById(R.id.windSpeed);
        txtViewDegress = (TextView) layout.findViewById(R.id.windDegress);
        txtViewSpeedStore = (TextView) layout.findViewById(R.id.windSpeedStorage);
        txtViewDegressStore = (TextView) layout.findViewById(R.id.windDegressStorage);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int pos = bundle.getInt("position");
            //WeatherInfo weatherModel = bundle.getParcelable("weatherModel");
            String dataWindSpeed = weatherModel.getWind().getSpeed().toString();
            String dateWindDegress = weatherModel.getWind().getDeg().toString();
            txtView.setText(" User entered : " + dataWindSpeed);
            if (fragmentPosition == 0) {
                txtViewDegress.setText("degress");
                txtViewSpeed.setText("speed");
            } else {
                txtViewDegressStore.setText("degress storaged");
                txtViewSpeedStore.setText("speed straged");
            }
        }
        return layout;
    }

    public void sendData(WeatherInfo weatherModel) {
        this.weatherModel = weatherModel;
    }
}
