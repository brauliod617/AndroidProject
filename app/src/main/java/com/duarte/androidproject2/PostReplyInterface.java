package com.duarte.androidproject2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface PostReplyInterface {
    void onAddAnswerSuccess();
    void onAddAnswerFailed(Exception e);
}
