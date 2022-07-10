package edu.birzeit.houserentals;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigInteger;

public class SignIn extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        EditText email = (EditText) findViewById(R.id.signInEmail);
        EditText password = (EditText) findViewById(R.id.signInPassword);
        Button signIn = (Button) findViewById(R.id.btnSignIn);
        Button signUp = (Button) findViewById(R.id.btnSignUp);
        CheckBox rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignIn.this,SignUpChoice.class);
                startActivity(signUpIntent);
            }
        });
        email.setText(sharedPrefManager.readString("Email", ""));
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                byte [] pass = password.getText().toString().getBytes();
                if(mail.length() == 0 || pass.length == 0){
                    Toast toast =Toast.makeText(SignIn.this, " Please Enter both email and password",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    BigInteger mdData = null;
                    String hashedPass=null;
                    try {
                        mdData = new BigInteger(1, PasswordEnc.encryptMD5(pass));
                        hashedPass = mdData.toString(16);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (hashedPass.length() > 500)
                        hashedPass = hashedPass.substring(0, 498);
                    DataBaseHelper db = new DataBaseHelper(SignIn.this,"HomeRent", null,1);
                    Cursor user = db.getUser(mail);
                    if(user == null || user.getCount() == 0){
                        Toast toast =Toast.makeText(SignIn.this, "Wrong Email Or Password Please try Again",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        user.moveToNext();
                        String correctPassword = user.getString(4);
                        if(hashedPass.equals(correctPassword)) {
                            if(rememberMe.isChecked()) {
                                sharedPrefManager.writeString("Email", mail);
                                Toast.makeText(SignIn.this, "Email saved to shared Preferences successfully", Toast.LENGTH_SHORT).show();
                            }
                            SessionManager sessionManager = new SessionManager(SignIn.this);
                            sessionManager.saveSession(user.getString(0));
                            Intent homeIntent = new Intent(SignIn.this, HomeLayout.class);
                            startActivity(homeIntent);
                        }
                        else {
                            Toast toast =Toast.makeText(SignIn.this, "Wrong Email Or Password",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }

            }
        });
        SignIn.this.setTitle("Sign in");
    }

    @Override
    public void onStart(){
        super.onStart();
        SessionManager session = new SessionManager(SignIn.this);
        String email = session.getSession();
        if(email != null){
            Intent homeIntent = new Intent(SignIn.this, HomeLayout.class);
            startActivity(homeIntent);
        }
    }
}