package simplify.fwm.jibberjabber.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import simplify.fwm.jibberjabber.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FIREBASE_REF = "https://jibber-jabber.firebaseio.com/";

    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.main_add)FloatingActionButton _addButton;
    @Bind(R.id.main_recycler_view)RecyclerView _recycler;

    private RecyclerView.LayoutManager rvLayout;
    private RecyclerView.Adapter rvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Jibber Jabber");
        setSupportActionBar(toolbar);
        _recycler.setHasFixedSize(true);
        rvLayout = new LinearLayoutManager(this);
        _recycler.setLayoutManager(rvLayout);

        if(isConnected()){
            startActivity(new Intent(this, LoginActivity.class));
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


    public void MainOnClick(View v){

    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }
}
