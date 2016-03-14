package development.alberto.com.weatheralert.di.Modules;


import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import development.alberto.com.weatheralert.Constants.Constant;
import io.realm.Realm;
import retrofit.RestAdapter;

@Module
public class NetModule {

    @Provides
    @Singleton
    Realm provideRealm() {
        Log.i("Realm", "provideRealm");

        //realm instance
        return Realm.getDefaultInstance();
    }


    @Provides
    @Singleton
    RestAdapter provideRetrofit() {
        Log.i("Retrofit", "provideRetrofit");

        //restAdapt
        return new RestAdapter.Builder()
                .setEndpoint(Constant.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

    }
}
