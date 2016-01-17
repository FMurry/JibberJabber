package simplify.fwm.jibberjabber.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String FIREBASE_REF = "https://jibber-jabber.firebaseio.com/";

    @Bind(R.id.login_email)EditText _email;
    @Bind(R.id.login_password)EditText _password;
    @Bind(R.id.login_button)AppCompatButton _login;
    @Bind(R.id.login_signup)TextView _signup;
    @Bind(R.id.login_forgot_password)TextView _forgot;
    @Bind(R.id.login_post_email)TextView _post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        _post.setVisibility(View.GONE);

    }


    public boolean validate(){

        boolean isValid = true;

        if(_email.getText().toString().isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(_email.getText().toString()).matches()){
            _email.setError("Enter valid username");
            isValid = false;
        }
        else{
            _email.setError(null);
        }

        if(_password.getText().toString().isEmpty()){
            _password.setError("Enter valid password");
            isValid = false;
        }
        else {
            _password.setError(null);
        }

        return isValid;
    }

    public void login(){

        if(!validate()){
            loginFailed();
            return;
        }

        if(!isConnected()){
            loginFailed();
            Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
            return;
        }

        _login.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in....");
        progressDialog.show();

        String email = _email.getText().toString();
        String password = _password.getText().toString();

        final Firebase ref = new Firebase(FIREBASE_REF);

        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        loginSuccess();
                    }
                },1500);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(),firebaseError.getMessage(),Toast.LENGTH_LONG).show();
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        loginFailed();
                    }
                },1500);
            }
        });
    }


    public void LoginOnClick(View v){
        switch (v.getId()){
            case R.id.login_button:
                login();
                break;
            case R.id.login_forgot_password:

                break;
            case R.id.login_signup:
                startActivity(new Intent(this,SignupActivity.class));
                finish();
                break;
        }
    }

    /**
     * Determines whether android device has an internet connection
     * @return
     */
    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }

    public void loginFailed(){
        _login.setEnabled(true);
    }

    public void loginSuccess(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}

