package yatish.com.mvp;

/**
 * Created by yatish on 24/11/17.
 */

public interface SayHelloContract {

    interface View{
        void showMessage(String message);
        void showError(String error);
    }

    interface Presenter{
        void loadMessage();
        void saveMessage(String firstName, String lastName);
    }

}
