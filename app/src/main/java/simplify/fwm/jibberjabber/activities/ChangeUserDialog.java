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

import com.firebase.client.Firebase;

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


    public ChangeUserDialog(){
        //Empty constructor required for DialogFragment
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_change_username,container);
        ButterKnife.bind(this,v);

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

    public void changeName(){
        if(!(_input.getText().toString().isEmpty()) && (isConnected())){
            Firebase userName = ref.child("users").child(ref.getAuth().getUid()).child("userName");
            userName.setValue(_input.getText().toString());
            close();
            this.getActivity().recreate();
        }
        else if(!(isConnected())){
            Toast.makeText(getContext(),"No Connection",Toast.LENGTH_LONG).show();
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
