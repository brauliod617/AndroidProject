package com.duarte.androidproject2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface LoadQuestionInteface {
    void questionDownloadSuccessfull(Task<QuerySnapshot> task);
    void questionDownloadFailed(Exception e);
    void onQuestionClicked(Questions currentQuestion);
}
