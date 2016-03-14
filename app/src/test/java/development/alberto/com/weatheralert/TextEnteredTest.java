package development.alberto.com.weatheralert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import development.alberto.com.weatheralert.UI.Presenter.MainPresenter;
import development.alberto.com.weatheralert.UI.Presenter.MainView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alber on 14/03/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TextEnteredTest {

    @Mock
    private MainView view;
    @Mock
    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(view);

    }

    @Test
    public void displayErrorWhenUserNameIsEmpty() throws Exception {
        when(view.getUserName()).thenReturn("");
        presenter.onLoginButtonClicked();

        verify(view).showUserNameError(R.string.login_user_name_error);
    }

    @Test
     public void getFragment1()throws Exception {
        presenter.getFragment1();
    }
    @Test
    public void getFragment2()throws Exception {
        presenter.getFragment2();
    }


}
