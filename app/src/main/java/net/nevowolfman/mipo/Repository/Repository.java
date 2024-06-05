package net.nevowolfman.mipo.Repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.nevowolfman.mipo.Model.Organization;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//an interface that handles the connection between the app and it's database
public class Repository {
    private FirebaseFirestore db;

    final String ORGS_COLLECTION = "orgs";
    final String VERSION_COLLECTION = "versions";
    final String ORG_DOC = "org";

    public Repository(Context context) {
        this.db = FirebaseFirestore.getInstance();
    }

    public void setOrg(Organization org){
        DocumentReference docRef =  db.collection(ORGS_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(VERSION_COLLECTION).document(ORG_DOC);
        docRef.set(org);
    }

    public void getOrg(GetOrgListener listener) {
        DocumentReference docRef = db.collection(ORGS_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(VERSION_COLLECTION).document(ORG_DOC);
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

    public void setCheckedOrg(Organization org){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DocumentReference docRef = db.collection(ORGS_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(VERSION_COLLECTION).document(sdf.format(Calendar.getInstance().getTime()));
        docRef.set(org);
    }

    public void getCheckedOrg(GetOrgListener listener) {
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
