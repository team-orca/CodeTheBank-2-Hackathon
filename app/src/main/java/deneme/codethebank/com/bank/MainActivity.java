package deneme.codethebank.com.bank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final String idE = "Enes Günday";
    final String idO= "Ozan Onur Tek";
    private String coming="";
    private TextView textAltın, username, userid;
    public static final String PREFS_NAME = "gold";
    public static int para=0;
    public static SharedPreferences.Editor editor;
    private SharedPreferences settings;
    private int temp;
    private boolean onDoubleBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textAltın  = (TextView)findViewById(R.id.altınView);

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        settings.getInt(PREFS_NAME, 0);
        // Writing data to SharedPreferences
        editor = settings.edit();

        // Reading from SharedPreferences
        String value = Integer.toString(para);
        textAltın.setText(value);
        Intent gelen_intent = getIntent();

        coming = gelen_intent.getStringExtra("id");
        //Toast.makeText(getBaseContext(),coming,Toast.LENGTH_LONG).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        username= (TextView)header.findViewById(R.id.drawer_username);
        userid= (TextView)header.findViewById(R.id.drawer_userid);
        username.setText("a");
        userid.setText(coming);
        if(coming.equals("12")){
            username.setText(idO);
        }
        if(coming.equals("123"))
            username.setText(idE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        update();
        displayToast("resume");
        onDoubleBackPressed=false;
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bilimButton:

                temp = settings.getInt("key", 0);
                temp+=20;
                Toast.makeText(getBaseContext(),temp+"",Toast.LENGTH_SHORT).show();
                editor.putInt("key", temp);
                //editor.apply();
                update();
                break;

            case R.id.mathButton:

                break;

            case R.id.dunyaButton:
                temp=0;
                editor.putInt("key", temp);
                //editor.apply();
                update();
                break;

            case R.id.genelkulturButton:
                attemptVideo();
                //Toast.makeText(getBaseContext(),"ayarlar",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if(onDoubleBackPressed==true){
                super.onBackPressed();
            }
        else if(!drawer.isDrawerOpen(GravityCompat.START) && onDoubleBackPressed==false){
                onDoubleBackPressed=true;
                displayToast("Çıkmak için tekrar geri tuşuna basın!");
            }

        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //TODO list previous videos
            Intent intent = new Intent(MainActivity.this,WatchedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent myInt = new Intent(MainActivity.this, MoneyRequest.class);
            startActivity(myInt);
        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void attemptVideo(){
    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        startActivity(intent);
    }
    private void displayToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
    public void update(){
        editor.apply();
        temp = settings.getInt("key", 0 );
        String value = Integer.toString(temp);
        textAltın.setText(value);
    }
}
