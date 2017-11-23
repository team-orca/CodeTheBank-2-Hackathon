package deneme.codethebank.com.bank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private RadioButton correct1, correct2;
    private static SharedPreferences.Editor editor;
    private int temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences mPrefs = getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
        editor = mPrefs.edit();
        temp=mPrefs.getInt("key",-1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        correct1 = (RadioButton)findViewById(R.id.correct1);
        correct2= (RadioButton)findViewById(R.id.correct2);
        //wrong = (RadioButton)findViewById(R.id.wrong);


    }
    public void onClick(View v){
        if(v.getId()==R.id.btn1) {
            if(correct1.isChecked()){
                temp+=210;

            }
            if(correct2.isChecked()){
                temp+=210;
            }
            if(!correct1.isChecked() )
                displayToast("Incorrect answer at Question 1!");
            if(!correct2.isChecked() )
                displayToast("Incorrect answer at Question 2!");


            editor.putInt("key",temp );
            editor.apply();
            finish();
        }
    }
    private void displayToast(String msg) {
        Toast.makeText(QuizActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
