package simplify.fwm.jibberjabber.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import simplify.fwm.jibberjabber.R;

/**
 * Created by fredericmurry on 1/16/16.
 */
public class ChangeUserDialog extends DialogFragment {

    @Bind(R.id.change_username_username)EditText _input;
    @Bind(R.id.change_username_ok)TextView _ok;
    @Bind(R.id.change_username_cancel)TextView _cancel;

    private final Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");

    private String userName;
    private boolean taken;


    public ChangeUserDialog(){
        //Empty constructor required for DialogFragment
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_change_username,container);
        ButterKnife.bind(this,v);
        taken = false;
        ref.child("users").child(ref.getAuth().getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue().toString();
                setUserName(username);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        _ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
            }
        });

        _cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });


        return v;
    }

    public void setUserName(String name){
        userName = name;
    }

    public void setTaken(boolean taken){
        this.taken = taken;
    }
    public void changeName(){
        Firebase list = ref.child("userNamesTaken");
        list.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: Fix User Search
                setTaken(dataSnapshot.hasChild(_input.getText().toString()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if(!(_input.getText().toString().isEmpty()) && (isConnected()) && taken == false){
            Firebase name = ref.child("userNamesTaken").child(userName);
            name.setValue(null);

            Firebase userName = ref.child("users").child(ref.getAuth().getUid()).child("userName");

            userName.setValue(_input.getText().toString());
            name = ref.child("userNamesTaken").child(_input.getText().toString());
            name.setValue(true);
            close();
        }
        else if(!(isConnected())){
            Toast.makeText(getContext(),"No Connection",Toast.LENGTH_LONG).show();
        }
        else if(taken){
            Toast.makeText(getContext(),"Username taken",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Enter new Username",Toast.LENGTH_LONG).show();

        }
    }

    public void close(){
        this.dismiss();
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }
}
