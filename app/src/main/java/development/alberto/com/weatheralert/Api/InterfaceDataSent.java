package development.alberto.com.weatheralert.Api;

import development.alberto.com.weatheralert.Models.Model.WeatherInfo;
import io.realm.Realm;

/**
 * Created by alber on 11/03/2016.
 */
public interface InterfaceDataSent {
    public void sendData(WeatherInfo weatherModel);
}
