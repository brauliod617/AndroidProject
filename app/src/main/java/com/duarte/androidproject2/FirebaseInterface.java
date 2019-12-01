package com.duarte.androidproject2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface FirebaseInterface {
    public void onSuccess(Task<QuerySnapshot> data);
    public void onFailed(Task<QuerySnapshot> data);
}
