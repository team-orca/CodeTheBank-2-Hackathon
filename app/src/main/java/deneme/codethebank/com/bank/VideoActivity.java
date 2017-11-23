package deneme.codethebank.com.bank;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity  {
    private boolean isWatched=false;
    private int temp_para=0;
    private static SharedPreferences.Editor editor;

    //private int para;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        SharedPreferences mPrefs = getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
        editor = mPrefs.edit();
        temp_para = mPrefs.getInt("key", -1);
        //displayToast(temp_para+"");
        tryVideo();


    }
    private void tryVideo() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        final VideoView vidView = (VideoView)findViewById(R.id.myVideo);
        //String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        //Uri vidUri = Uri.parse(vidAddress);
        Uri vidUri = Uri.parse("android.resource://deneme.codethebank.com.bank/raw/recycle");
        vidView.setVideoURI(vidUri);

        vidView.setMediaController(new MediaController(this));
        vidView.start();
        vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {

                if (!isWatched) {
                    temp_para+=1000;
                    editor.putString("wathced", "Geri Dönüşümün Önemi, Para Nedir?, FastPay nasıl Kullanılır?, Kim birinci?");
                    editor.putInt("key", temp_para);
                    editor.apply();


                }
                isWatched = true;
                Intent myInt = new Intent(VideoActivity.this, QuizActivity.class);
                startActivity(myInt);
                finish();
            }
        });
        //controller.hide();
    }
    @Override
    public void onBackPressed(){

    }
    private void displayToast(String msg) {
        Toast.makeText(VideoActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
