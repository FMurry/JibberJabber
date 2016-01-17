package simplify.fwm.jibberjabber.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import simplify.fwm.jibberjabber.R;
import simplify.fwm.jibberjabber.data.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");

    private List<User> users;

    @Bind(R.id.main_recycler_view) RecyclerView _rv;
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.main_add)FloatingActionButton _addButton;

    private RecyclerView.LayoutManager rvLayout;
    private RecyclerView.Adapter rvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Jibber Jabber");
        _rv.setHasFixedSize(true);
        rvLayout = new LinearLayoutManager(this);
        _rv.setLayoutManager(rvLayout);

        //rvAdapter = new ;

        if(ref.getAuth()==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else{
            final Firebase userNode = ref.child("users").child(ref.getAuth().getUid()).child("userName");
            userNode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String)dataSnapshot.getValue();
                    toolbar.setTitle(name);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        setSupportActionBar(toolbar);
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
        if (id == R.id.action_accout) {
            return true;
        }

        if (id == R.id.action_logout){
            ref.unauth();
            recreate();
            return true;
        }

        if (id == R.id.action_change_username){
            FragmentManager fragmentManager = getSupportFragmentManager();
            ChangeUserDialog changeUserDialog = new ChangeUserDialog();
            changeUserDialog.show(fragmentManager,"fragment_change_username");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void MainOnClick(View v){
        switch (v.getId()){
            case R.id.main_add:
                FragmentManager fragmentManager = getSupportFragmentManager();

                break;
        }
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }

    public void reload(){

    }
}
