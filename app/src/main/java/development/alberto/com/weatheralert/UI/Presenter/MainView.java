package development.alberto.com.weatheralert.UI.Presenter;

import development.alberto.com.weatheralert.UI.View.MyFragment;
import development.alberto.com.weatheralert.UI.View.MyFragmentData;

public interface MainView {


    String getUserName();

    void showUserNameError(int login_user_name_error);

    MyFragment getFragment1();

    MyFragmentData getFragment2();
}
