package development.alberto.com.weatheralert.DataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alber on 11/03/2016.
 */
public class SavedWeatherData extends RealmObject{
    @PrimaryKey
    private int code;
    private String cityName;
    private String windSpeed;
    private String windDegree;
    private String temperature;


    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {

        return temperature;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public String getCityName() {

        return cityName;

    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(String windDegree) {
        this.windDegree = windDegree;
    }
}
