package deneme.codethebank.com.bank;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class MoneyRequest extends AppCompatActivity {
    private ToDoItemAdapter mAdapter;
    private ListView lv;
    //private EditText amountEditText;
    private double amount;
    private ArrayList<Integer> Id = new ArrayList<>();
    private ArrayList<Integer> altinDeger = new ArrayList<>();
    private ArrayList<String> string_array_list = new ArrayList<>();
    private MobileServiceClient mClient;//mobile service
    private MobileServiceTable<altinveboolean> mToDoTable;//tablo icin
    private Button btn;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private int gold = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_request);
        mPrefs = getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
        gold = mPrefs.getInt("key", 0);
        editor=mPrefs.edit();

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://orcatest.azure-mobile.net/",
                    "WwUMpQWcMbgYSeXpqABTApYDuXXhIN20",
                    this).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use

            mToDoTable = mClient.getTable(altinveboolean.class);

            // Offline Sync
            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);

            //Init local storage
            initLocalStore().get();
            mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);
            lv = new ListView(getBaseContext());
            lv.setAdapter(mAdapter);
            refreshItemsFromTable();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
       /*
*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
     /*

        //amountEditText = (EditText)findViewById(R.id.amountEditText);
        for(int i=0; i<Id.size(); i++){
            string_array_list.add(altinDeger.get(i)+" = "+ altinDeger.get(i)/100 +" ₺");
        }
    */


    }

    public void displayToast(String msg) {
        Toast.makeText(MoneyRequest.this, msg, Toast.LENGTH_SHORT).show();
    }

    private ProgressBar mProgressBar;

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }

    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("ToDoItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }

    /**
     * Mark an item as completed
     *
     * @param item The item to mark
     */
    public void checkItem(final altinveboolean item) {
        if (mClient == null) {
            return;
        }

        // Set the item as completed and update it in the table


        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    checkItemInTable(item);

                } catch (final Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        runAsyncTask(task);

    }

    public void checkItemInTable(altinveboolean item) throws ExecutionException, InterruptedException {
        item.setBakildimi(1);

        mToDoTable.update(item).get();//update
    }

    /**
     * Run an ASync task on the corresponding executor
     *
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    public void onClick(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final altinveboolean entity = addItemInTable();


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        runAsyncTask(task);
    }

    public altinveboolean addItemInTable() throws ExecutionException, InterruptedException {
        altinveboolean ab = new altinveboolean();
        String message="";
        if(gold >= 100){
            message+="Yeterli altın mevcut. Talebiniz gönderildi.";
            editor.putInt("key",gold-100);
            editor.apply();
            ab.setAltin(100);
            ab.setBakildimi(0);
        }
        else
            message="Yeterli altınınız yok!";
        gold= mPrefs.getInt("key", 0);
        final String msg="Ürün ücreti 100 altın. \n"+message+"\n"+"Kalan altın: "+gold;
        dialogtask(msg, "Ücret");

        altinveboolean entity = mToDoTable.insert(ab).get();//insert
        return entity;
    }

    public void onClick2(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final altinveboolean entity = addItemInTable2();


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        runAsyncTask(task);
    }

    public altinveboolean addItemInTable2() throws ExecutionException, InterruptedException {
        altinveboolean ab = new altinveboolean();

        String message="";
        if(gold >= 200){
            message+="Yeterli altın mevcut. Talebiniz gönderildi.";
            editor.putInt("key",gold-200);
            editor.apply();
            ab.setAltin(200);
            ab.setBakildimi(0);
        }
        else
            message="Yeterli altınınız yok!";
        gold= mPrefs.getInt("key", 0);
        final String msg="Ürün ücreti 200 altın. \n"+message+"\n"+"Kalan altın: "+gold;
        dialogtask(msg, "Ücret");

        altinveboolean entity = mToDoTable.insert(ab).get();//insert
        return entity;
    }

    ///////
    public void onClick3(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final altinveboolean entity = addItemInTable3();


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        runAsyncTask(task);
    }

    public altinveboolean addItemInTable3() throws ExecutionException, InterruptedException {
        altinveboolean ab = new altinveboolean();
        String message="";
        if(gold >= 300){
            message+="Yeterli altın mevcut. Talebiniz gönderildi.";
            editor.putInt("key",gold-300);
            editor.apply();
            ab.setAltin(300);
            ab.setBakildimi(0);
        }
        else
            message="Yeterli altınınız yok!";
        gold= mPrefs.getInt("key", 0);
        final String msg="Ürün ücreti 300 altın. \n"+message+"\n"+"Kalan altın: "+gold;
        dialogtask(msg, "Ücret");

        altinveboolean entity = mToDoTable.insert(ab).get();//insert
        return entity;
    }

    ///////
    public void onClick4(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final altinveboolean entity = addItemInTable4();


                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        runAsyncTask(task);
    }

    public altinveboolean addItemInTable4() throws ExecutionException, InterruptedException {
        altinveboolean ab = new altinveboolean();
        String message="";
        if(gold >= 400){
            message+="Yeterli altın mevcut. Talebiniz gönderildi.";
            editor.putInt("key",gold-400);
            editor.apply();
            ab.setAltin(400);
            ab.setBakildimi(0);
        }
        else
            message="Yeterli altınınız yok!";
        gold= mPrefs.getInt("key", 0);
        final String msg="Ürün ücreti 400 altın. \n"+message+"\n"+"Kalan altın: "+gold;
        dialogtask(msg, "Ücret");
        //dialogtask(gold+"","GOLD");

        altinveboolean entity = mToDoTable.insert(ab).get();//insert
        return entity;
    }

    private List<altinveboolean> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mToDoTable.where().field("bakildimi").
                eq(val(0)).execute().get();
    }

    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<altinveboolean> results = refreshItemsFromMobileServiceTable();

                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (altinveboolean item : results) {
                                mAdapter.add(item);
                                altinDeger.add(item.getAltin());
                            }
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    private void dialogtask(final String message,final String bilgi) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(message, bilgi);
            }
        });
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message The dialog message
     * @param title   The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
}


