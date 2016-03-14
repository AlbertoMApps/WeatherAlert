package development.alberto.com.weatheralert.UI.Presenter;

import development.alberto.com.weatheralert.R;

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
}
