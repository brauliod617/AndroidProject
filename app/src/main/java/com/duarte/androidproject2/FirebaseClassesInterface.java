package com.duarte.androidproject2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface FirebaseClassesInterface {
    void onGetClassesSuccess(Task<QuerySnapshot> data, Classes classes);
    void onGetClassesFailed(Task<QuerySnapshot> data);
}
