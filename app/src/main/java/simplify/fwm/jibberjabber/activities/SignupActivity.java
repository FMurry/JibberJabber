package simplify.fwm.jibberjabber.activities;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import simplify.fwm.jibberjabber.R;
import simplify.fwm.jibberjabber.data.User;

/**
 * A login screen that offers login via email/password.
 */
public class SignupActivity extends AppCompatActivity {

    @Bind(R.id.signup_firstname)AppCompatEditText _firstName;
    @Bind(R.id.signup_lastname)AppCompatEditText _lastName;
    @Bind(R.id.signup_email)AppCompatEditText _email;
    @Bind(R.id.signup_user_name)AppCompatEditText _userName;
    @Bind(R.id.signup_password)AppCompatEditText _password;
    @Bind(R.id.signup_button)AppCompatButton _signupButton;
    @Bind(R.id.signup_login) TextView _loginLink;

    Firebase ref = new Firebase("https://jibber-jabber.firebaseio.com/");

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

        String fName = _firstName.getText().toString();
        String lName = _lastName.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        if(fName.isEmpty() || fName.length()<2){
            _firstName.setError("Please enter valid name");
            isValid = false;
        }
        else{
            _firstName.setError(null);
        }

        if(lName.isEmpty() || lName.length()<2){
            _lastName.setError("Please Enter valid name");
            isValid = false;
        }
        else{
            _lastName.setError(null);
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _email.setError("Please enter valid email");
            isValid = false;
        }
        else {
            _email.setError(null);
        }

        if(password.isEmpty() || password.length() < 6 || password.length() >15){
            _password.setError("Between 6 and 12 alphanumeric characters");
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

        String fName = _firstName.getText().toString();
        String lName = _lastName.getText().toString();
        String email = _email.getText().toString();
        String userName = _userName.getText().toString();
        String password = _password.getText().toString();
        final User user = new User(fName,lName,email,userName,password);
        Firebase userDB = ref.child("users");


        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                result.put("displayName",user.getUserName());
                result.put("firstName",user.getFirstName());
                result.put("lastName",user.getLastName());
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        signupSuccess();
                        progressDialog.dismiss();
                    }
                },2000);
                Toast.makeText(getApplicationContext(), "Created User with username: " + result.get("displayName"),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(),firebaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
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

    }
}

