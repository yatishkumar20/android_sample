package yatish.com.mvp;

/**
 * Created by yatish on 24/11/17.
 */

public class SayHelloPresenter implements SayHelloContract.Presenter {

    private Person person;
    SayHelloContract.View view;


    public SayHelloPresenter(SayHelloContract.View view){

        this.person = new Person();
        this.view = view;

    }

    @Override
    public void loadMessage() {

        if(person.getFirstName() == null && person.getLastName() == null){
            view.showError("No person Found");
            return;
        }

        String msg = "Hi"+person.getFirstName()+" "+person.getLastName();
        view.showMessage(msg);

    }

    @Override
    public void saveMessage(String firstName, String lastName) {

        person.setFirstName(firstName);
        person.setLastName(lastName);

    }
}
