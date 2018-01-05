package com.quiz;

import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {

    private RadioGroup radioGroup1,radioGroup2;
    private RadioButton radioButton,radioButton1,radioButton2,radioButton3,radioButton4,radioButton5;
    CheckBox checkBox,checkBox1,checkBox2,checkBox3;
    String ans1 = "", ans2 = "", ans3 = "", ans4 = "";
    EditText editText;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);

        radioButton1 = (RadioButton) findViewById(R.id.radioButton5);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton9);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton10);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox5);

        editText = (EditText) findViewById(R.id.editText2);

        if(savedInstanceState != null){

            String quest1 =  savedInstanceState.getString("quest1");

            Log.d(getLocalClassName(),quest1);

            if(quest1.equals(R.string.quest_1_1)){
                radioButton1.setChecked(true);
            }else if(quest1.equals(R.string.quest_1_2)){
                radioButton2.setChecked(true);
            }else if(quest1.equals(R.string.quest_1_3)){
                radioButton3.setChecked(true);
            }else if(quest1.equals(R.string.quest_1_4)){
                radioButton4.setChecked(true);
            }

            String quest2 =  savedInstanceState.getString("quest2");
            String quest3 =  savedInstanceState.getString("quest3");
            String quest4 =  savedInstanceState.getString("quest4");

        }



    }

    public void checkAnswers(View view){

        ans1 = "";
        ans2 = "";
        ans3 = "";
        ans4 = "";
        int marks = 0;
        int selectedId=radioGroup1.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(selectedId);

        try {
            ans1 = radioButton.getText() + "";
        }catch (Exception e){
            ans1 = "";
        }

        if(checkBox.isChecked()){

            if(ans2.equals("")){
                ans2 = 1983+"";
            }else{
                ans2 = ans2 +" "+1983;
            }


        }

        if(checkBox1.isChecked()){

            if(ans2.equals("")){
                ans2 = 1999+"";
            }else{
                ans2 = ans2 +" "+1999;
            }

        }

        if(checkBox2.isChecked()){

            if(ans2.equals("")){
                ans2 = 2007+"";
            }else{
                ans2 = ans2 +" "+2007;
            }

        }

        if(checkBox3.isChecked()){

            if(ans2.equals("")){
                ans2 = 2011+"";
            }else{
                ans2 = ans2 +" "+2011;
            }

        }

        ans3 = editText.getText()+"";

        int selectedId1=radioGroup2.getCheckedRadioButtonId();
        radioButton5 = (RadioButton)findViewById(selectedId1);

        try {
            ans4 = radioButton5.getText() + "";
        }catch (Exception e){
            ans4 = "";
        }

        if(ans1.equals("")||ans2.equals("")||ans3.equals("")||ans4.equals("")){

            message = "Please answer for all questions";

        }else {

            if (!ans1.equals("") && ans1.equals("West Indies")) {
                marks++;
            }

            if (!ans2.equals("")) {
                String[] ans = ans2.split(" ");


                if (ans.length == 2) {

                    if (((ans[0].equals("1983")) || (ans[0].equals("2011"))) && ((ans[1].equals("1983")) || (ans[1].equals("2011")))) {
                        marks++;
                    }

                }
            }

            if (!ans3.equals("") && ans3.equals("2007")) {
                marks++;
            }

            if (!ans4.equals("") && ans4.equals("Anil Kumble")) {
                marks++;
            }

            message = "Your score "+marks;

        }






        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //Toast.makeText(Quiz.this,ans2.toString(),Toast.LENGTH_LONG).show();
                            }
                        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("quest1",ans1);
        outState.putString("quest2",ans2);
        outState.putString("quest3",ans3);
        outState.putString("quest4",ans4);

    }
}
