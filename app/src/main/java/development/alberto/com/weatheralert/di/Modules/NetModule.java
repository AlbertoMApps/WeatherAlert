package development.alberto.com.weatheralert.di.Modules;


import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import development.alberto.com.weatheralert.Constants.Constant;
import development.alberto.com.weatheralert.UI.MainActivity;
import io.realm.Realm;
import retrofit.RestAdapter;

@Module
public class NetModule {

    private RestAdapter restAdapt;
    private Realm mRealm;

    @Provides
    @Singleton
    Realm provideRealm() {
        Log.i("Realm", "provideRealm");

        //realm instance
        return mRealm = Realm.getDefaultInstance();
    }


    @Provides
    @Singleton
    RestAdapter provideRetrofit() {
        Log.i("Retrofit", "provideRetrofit");

        //restAdapt
        restAdapt = new RestAdapter.Builder()
                .setEndpoint(Constant.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapt;
    }
}
