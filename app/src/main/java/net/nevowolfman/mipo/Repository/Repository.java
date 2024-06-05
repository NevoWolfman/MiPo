package net.nevowolfman.mipo.Repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.nevowolfman.mipo.DB.SQLiteHelper;
import net.nevowolfman.mipo.Model.Organization;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

//an interface that handles the connection between the app and it's database
public class Repository {
    //TODO: add a remote database such as MySQL or FireBase
    private SQLiteHelper sqLiteHelper;

    private FirebaseFirestore db;

    final String ORGS_COLLECTION = "orgs";
    final String VERSION_COLLECTION = "versions";

    public Repository(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
        this.db = FirebaseFirestore.getInstance();
    }

    public long addUser(UserModel user) {
        return sqLiteHelper.addUser(user);
    }

    public UserModel getUserByEmail(String email)
    {
        return sqLiteHelper.getUserByEmail(email);
    }

    public long deleteUserByEmail(String email)
    {
        return sqLiteHelper.deleteUserByEmail(email);
    }

    public long updateUserByEmail(String email, UserModel user) {
        return sqLiteHelper.updateUserByEmail(email, user);
    }




    public void addOrg(Organization org){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        db.collection(ORGS_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(VERSION_COLLECTION).document(sdf.format(Calendar.getInstance().getTime())).set(org);
    }

    public void getOrg(GetOrgListener listener) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DocumentReference docRef = db.collection(ORGS_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(VERSION_COLLECTION).document(sdf.format(Calendar.getInstance().getTime()));
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    listener.onComplete(documentSnapshot.toObject(Organization.class));
                }
                else {
                    listener.onComplete(null);
                }
            }
        });
    }

    public interface GetOrgListener {
        public void onComplete(Organization org);
    }
}
