package yatish.com.mvp;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SayHelloContract.View, View.OnClickListener{

    SayHelloContract.Presenter presenter;

    private TextView messageView;
    private EditText firstNameView;
    private EditText lastNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        presenter = new SayHelloPresenter(this);

    }

    public void initViews(){

        messageView = (TextView)findViewById(R.id.message);
        firstNameView = (EditText)findViewById(R.id.firstName);
        lastNameView = (EditText)findViewById(R.id.lastName);

        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.showMessage).setOnClickListener(this);

    }

    @Override
    public void showMessage(String message) {
        messageView.setText(message);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.update:
                presenter.saveMessage(firstNameView.getText().toString(),lastNameView.getText().toString());
                break;

            case R.id.showMessage:
                presenter.loadMessage();
                break;

        }
    }
}
