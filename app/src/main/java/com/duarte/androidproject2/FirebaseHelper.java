package com.duarte.androidproject2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    FirebaseFirestore db;
    final String studentCollection = "students";
    FirebaseHelper(){
        db = FirebaseFirestore.getInstance();
    }

    public void registerStudentFirebase(Student student){
        Map<String, String> mapStudent = new HashMap<>();

        mapStudent.put("userName", student.getUserName());
        mapStudent.put("email", student.getEmail());
        mapStudent.put("password", student.getPassWord());

        db.collection(studentCollection)
                .add(mapStudent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error adding document", e);
                    }
                });
    }

    public Student getStudentFirebase(String email){
        Student student = new Student();

        db.collection(studentCollection).
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Firebase", document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.w("Firebase", "Error getting documents.", task.getException());

                        }
                    }
                });
        return student;
    }
}
