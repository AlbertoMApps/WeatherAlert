package development.alberto.com.weatheralert;

import android.app.Application;

import development.alberto.com.weatheralert.di.Components.DaggerNetComponent;
import development.alberto.com.weatheralert.di.Components.NetComponent;
import development.alberto.com.weatheralert.di.Modules.AppModule;
import development.alberto.com.weatheralert.di.Modules.NetModule;

public class MyApp extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent
                .builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }


}
