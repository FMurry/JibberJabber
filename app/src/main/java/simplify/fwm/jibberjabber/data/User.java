package simplify.fwm.jibberjabber.data;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by fredericmurry on 1/14/16.
 */
public class User {

    private static final String TAG = "User";

    private String uID;
    private String firstName;
    private String lastName;
    private String displayName;

    public User(){

    }

    public User(String uID){
        this.uID = uID;
        FirebaseSet(uID);

    }
    public User(String firstName, String lastName,String userName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = userName;

    }

    //          Accessors
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }


    public String getUserName(){
        final Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");
        Firebase user = ref.child("users").child(uID);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               setUserName(dataSnapshot.child("userName").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return displayName;
    }


    //         Mutators
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setUserName(String userName){
        this.displayName = userName;
    }


    public void FirebaseSet(String userID){
        final Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");
        Firebase user = ref.child("users").child(userID);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setFirstName(dataSnapshot.child("firstName").getValue().toString());
                setLastName(dataSnapshot.child("lastName").getValue().toString());
                setUserName(dataSnapshot.child("userName").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG,firebaseError.getMessage());
            }
        });


    }
}
