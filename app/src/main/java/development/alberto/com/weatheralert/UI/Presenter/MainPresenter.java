package development.alberto.com.weatheralert.UI.Presenter;

import development.alberto.com.weatheralert.R;
import development.alberto.com.weatheralert.UI.View.MyFragment;
import development.alberto.com.weatheralert.UI.View.MyFragmentData;

public class MainPresenter {
    private final MainView view;

    public MainPresenter(MainView v) {
        view= v;
    }

    public void onLoginButtonClicked() {
        String userName = view.getUserName();
        if (userName.isEmpty()) {
            view.showUserNameError(R.string.login_user_name_error);
            return;
        }
    }
    public MyFragment getFragment1 (){
        MyFragment f1= view.getFragment1();
        return f1;
    }
    public MyFragmentData getFragment2 (){
        MyFragmentData f2= view.getFragment2();
        return f2;
    }
}
