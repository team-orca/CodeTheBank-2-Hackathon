package deneme.codethebank.com.bank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WatchedActivity extends AppCompatActivity {

    private ListView lv;
    private String temporary;
    private String[] split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched);


        SharedPreferences mPrefs = getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
        temporary = mPrefs.getString("wathced", "");
        split = temporary.split(", ");

        lv = (ListView) findViewById(R.id.listView);
        ArrayList<String> your_array_list = new ArrayList<String>();


        for(int k=0; k<split.length; k++){
            your_array_list.add(split[k]);
        }


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);
    }
    @Override
    public void onBackPressed(){
        finish();
    }
    private void displayToast(String msg) {
        Toast.makeText(WatchedActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
