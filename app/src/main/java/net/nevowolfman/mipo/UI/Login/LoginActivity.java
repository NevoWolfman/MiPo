package net.nevowolfman.mipo.UI.Login;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.UI.SignUp.SignUpActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ModuleLogin moduleLogin;

    private EditText etEmail, etPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;
    private TextView tvSignUp, tvForgot;
    public final String SAVED_CREDENTIALS = "credentials";


    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    if(!moduleLogin.onSignInResult(result)){
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        startSignIn();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        moduleLogin = new ModuleLogin(this);
        String email = moduleLogin.checkCredentials();
        if(email != null)
        {
            moduleLogin.logIn(email);
        }


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRemeberMe);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgot = findViewById(R.id.tvForgot);

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgot.setOnClickListener(this);


        startSignIn();
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin)
        {
            long id = moduleLogin.userLogin(etEmail.getText().toString(), etPassword.getText().toString());
            if (id == -1)
            {
                Toast.makeText(this, "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(cbRememberMe.isChecked())
                {
                    moduleLogin.rememberMe(etEmail.getText().toString(), etPassword.getText().toString());
                }
                moduleLogin.logIn(etEmail.getText().toString());
            }
        }
        else if(view == tvSignUp)
        {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        else if(view == tvForgot)
        {
            //TODO: add the password recovery thing
            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();
        }
    }

    public void startSignIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }
}