package net.nevowolfman.mipo.Repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.nevowolfman.mipo.DB.SQLiteHelper;
import net.nevowolfman.mipo.Model.Organization;

import java.util.LinkedList;
import java.util.List;

//an interface that handles the connection between the app and it's database
public class Repository {
    //TODO: add a remote database such as MySQL or FireBase
    private SQLiteHelper sqLiteHelper;

    private FirebaseFirestore db;

    final String ORGS_COLLECTION = "orgs";

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
        db.collection(ORGS_COLLECTION).document(org.getName()).set(org);
    }

    public void getOrg(String name, GetOrgListener listener) {
        DocumentReference docRef = db.collection(ORGS_COLLECTION).document(name);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                listener.onComplete(documentSnapshot.toObject(Organization.class));
            }
        });
    }

    interface GetOrgListener {
        public void onComplete(Organization org);
    }

    public void getAllOrgs(GetAllOrgsListener listener) {
        db.collection(ORGS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Organization> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.toObject(Organization.class));
                            }
                            listener.onComplete(list);
                        }
                    }
                });
    }

    interface GetAllOrgsListener {
        public void onComplete(List<Organization> orgs);
    }
}
