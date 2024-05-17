package net.nevowolfman.mipo.UI.Login;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.Repository.UserModel;
import net.nevowolfman.mipo.UI.Main.MainActivity;

public class ModuleLogin {
    private LoginActivity activity;
    private SharedPreferences prefs;

    public ModuleLogin(LoginActivity activity) {
        this.activity = activity;
        this.prefs = activity.getSharedPreferences(activity.SAVED_CREDENTIALS, Activity.MODE_PRIVATE);
    }

    public int userLogin(String email, String password)
    {
        Repository repository = new Repository(activity);
        UserModel user = repository.getUserByEmail(email);
        if (user == null || !user.getPassword().equals(password))
        {
            return -1;
        }
        return 0;
    }

    public void rememberMe(String email, String password)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("pass", password);
        editor.apply();
    }

    public String checkCredentials()
    {
        return prefs.getString("email", null);
    }

    public void logIn(String email)
    {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }

    public boolean onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (response != null && result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            logIn(user.getEmail());
            return true;
        }
        return false;
    }
}
