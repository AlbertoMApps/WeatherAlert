package development.alberto.com.weatheralert.di.Components;


import android.app.Application;

import javax.inject.Singleton;
import dagger.Component;
import development.alberto.com.weatheralert.UI.View.MainActivity;
import development.alberto.com.weatheralert.di.Modules.AppModule;
import development.alberto.com.weatheralert.di.Modules.NetModule;
import development.alberto.com.weatheralert.di.Scope.UserScope;
import io.realm.Realm;
import retrofit.RestAdapter;

@Singleton
@Component(modules={NetModule.class, AppModule.class})
public interface NetComponent {
    Realm provideRealm();
    RestAdapter provideRetrofit();
    void inject(MainActivity main);
}
