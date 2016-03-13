package development.alberto.com.weatheralert.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import development.alberto.com.weatheralert.Api.InterfaceDataSent;
import development.alberto.com.weatheralert.Model.WeatherInfo;
import development.alberto.com.weatheralert.R;

/**
 * Created by alber on 11/03/2016.
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.cityName) TextView txtView;
    @Bind (R.id.windSpeed) TextView txtViewSpeed;
    @Bind (R.id.windDegress) TextView txtViewDegress;
    @Bind(R.id.temperature) TextView txtTemperature;
    private WeatherInfo weatherModel;
    @Bind(R.id.btnAddData) Button btnAddData;
    private InterfaceDataSent interfaceDataSent;

    public MyFragment() {
        super();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        interfaceDataSent = (InterfaceDataSent) getActivity();//ESTO SOLO ES POSIBLE SI MainActivity es una subclase de Inteface por lo tanto implementa IntefaceDataSent: Polimorfismo
        //btn = (Button) getActivity().findViewById(R.id.btn_fragA);
        btnAddData.setVisibility(View.INVISIBLE);
        btnAddData.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }


    //Extra methods --------------------------------------------------------------------------------

    public void setWeatherModel(WeatherInfo weatherModel){
        this.weatherModel = weatherModel;
    }
    public WeatherInfo getWeatherModel (){
        return this.weatherModel;
    }

    public void updateViews() {
        txtView.setText("Current wind forecast in  "+getWeatherModel().getName().toString());
        txtTemperature.setText("Temperature : " + Double.parseDouble(getWeatherModel().getMain().getTemp().toString()));
        txtViewDegress.setText("Wind speed: " + getWeatherModel().getWind().getDeg().toString());
        txtViewSpeed.setText("Wind degress: "+getWeatherModel().getWind().getSpeed().toString());
    }



    @Override
    public void onClick(View v) {
        interfaceDataSent.sendData(getWeatherModel());
    }
}
