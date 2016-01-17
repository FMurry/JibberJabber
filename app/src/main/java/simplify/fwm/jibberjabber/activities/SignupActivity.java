package simplify.fwm.jibberjabber.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import simplify.fwm.jibberjabber.R;
import simplify.fwm.jibberjabber.data.User;

/**
 * A login screen that offers login via email/password.
 */
public class SignupActivity extends AppCompatActivity {

    @Bind(R.id.signup_firstname) AppCompatEditText _fName;
    @Bind(R.id.signup_lastname) AppCompatEditText _lName;
    @Bind(R.id.signup_email)AppCompatEditText _email;
    @Bind(R.id.signup_user_name) AppCompatEditText _userName;
    @Bind(R.id.signup_password)AppCompatEditText _password;
    @Bind(R.id.signup_button)AppCompatButton _signupButton;
    @Bind(R.id.signup_login) TextView _loginLink;

    private Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");
    private static final String TAG = "SignupActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

    }

    public boolean validate(){
        boolean isValid = true;

        String fName = _fName.getText().toString();
        String lName = _lName.getText().toString();
        String email = _email.getText().toString();
        String userName = _userName.getText().toString();
        String password = _password.getText().toString();

        if(fName.isEmpty() || fName.length()<2){
            _fName.setError("Enter a name");
            isValid = false;
        }
        else{
            _fName.setError(null);
        }

        if(lName.isEmpty()){
            _lName.setError("Enter a name");
            isValid = false;
        }
        else{
            _lName.setError(null);
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _email.setError("Please enter valid email");
            isValid = false;
        }
        else {
            _email.setError(null);
        }

        if(userName.isEmpty()){
            _userName.setError("Enter a username");
            isValid = false;
        }
        else {
            _userName.setError(null);
        }

        if(password.isEmpty() || password.length() < 6 || password.length() >15){
            _password.setError("Between 6 and 12 alphanumeric characters");
            isValid = false;
        }
        else{
            _password.setError(null);
        }



        return isValid;
    }

    public void signUp(){

        if(!validate()){
            signupFailed();
            return;
        }

        _signupButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing Up....");
        progressDialog.show();


        String email = _email.getText().toString();
        String password = _password.getText().toString();


        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        signupSuccess();

                    }
                }, 1500);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                signupFailed();
            }
        });
    }

    /**
     * Click Listener for Signup
     * @param v
     */
    public void SignupOnClick(View v){
        switch (v.getId()){
            case R.id.signup_button:
                signUp();
                break;
            case R.id.signup_login:
                finish();
                break;
        }
    }

    public void signupFailed(){

    }

    public void signupSuccess(){
        ref.authWithPassword(_email.getText().toString(), _password.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Map<String,String> map = new HashMap<String, String>();
                map.put("email",_email.getText().toString());
                map.put("firstName",_fName.getText().toString());
                map.put("lastName",_lName.getText().toString());
                map.put("userName",_userName.getText().toString());
                ref.child("users").child(authData.getUid()).setValue(map);
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.d(TAG,firebaseError.getMessage());
            }
        });
    }


}

