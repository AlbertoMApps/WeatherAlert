package development.alberto.com.weatheralert.Api;

import development.alberto.com.weatheralert.Constants.Constant;
import development.alberto.com.weatheralert.Model.WeatherInfo;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alber on 10/03/2016.
 */
public interface WeatherApi {
    @GET(Constant.WEATHER_QUERY)
    public void getWeatherInfo(@Query("q")String cityName, Callback<WeatherInfo> weatherModelCallback);
}
