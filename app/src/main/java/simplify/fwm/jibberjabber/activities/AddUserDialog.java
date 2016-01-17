package simplify.fwm.jibberjabber.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.ButterKnife;
import simplify.fwm.jibberjabber.R;

/**
 * Created by fredericmurry on 1/16/16.
 */
public class AddUserDialog extends DialogFragment {

    private final Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");


    public  AddUserDialog()
    {
        //Requires empty Constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user,container);
        ButterKnife.bind(this,v);
        Firebase users = ref.child("users");

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
