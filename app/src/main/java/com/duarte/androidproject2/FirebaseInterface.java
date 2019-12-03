package com.duarte.androidproject2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface FirebaseInterface {
    void validateStundetHelper(Task<QuerySnapshot> data, final String email);
    void onFailed(Task<QuerySnapshot> data);
}