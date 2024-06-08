package net.nevowolfman.mipo.Repository;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.nevowolfman.mipo.Model.Organization;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
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

    private CollectionReference getVersionCollection() {
        return db.collection(ORGS_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(VERSION_COLLECTION);
    }

    public void setOrg(Organization org){
        DocumentReference docRef =  getVersionCollection().document(ORG_DOC);
        docRef.set(org);
    }

    public void getOrg(GetOrgListener listener) {
        DocumentReference docRef = getVersionCollection().document(ORG_DOC);
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
        DocumentReference docRef = getVersionCollection().document(sdf.format(Calendar.getInstance().getTime()));
        docRef.set(org);
    }

    public void getCheckedOrg(GetOrgListener listener) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DocumentReference docRef = getVersionCollection().document(sdf.format(Calendar.getInstance().getTime()));
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    listener.onComplete(documentSnapshot.toObject(Organization.class));
                }
                else {
                    getOrg(listener);
                }
            }
        });
    }

    public void getVersions(GetVersionsListener listener) {
        getVersionCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<Organization> organizations = new LinkedList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        String id = documentSnapshot.getId();
                        if(!id.equals(ORG_DOC)) {
                            Organization org = documentSnapshot.toObject(Organization.class);
                            org.setName(id);
                            organizations.add(org);
                        }
                    }
                    listener.onComplete(organizations);
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

    public interface GetVersionsListener {
        public void onComplete(List<Organization> org);
    }
}
