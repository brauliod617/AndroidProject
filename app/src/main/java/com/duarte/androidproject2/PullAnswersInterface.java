package com.duarte.androidproject2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface PullAnswersInterface {
    void onPullAnswersSuccess(Task<QuerySnapshot> task);
    void onPullAnswersFailed(Exception e);
}
