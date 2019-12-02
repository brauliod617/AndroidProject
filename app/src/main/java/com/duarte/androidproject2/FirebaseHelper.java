package com.duarte.androidproject2;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private FirebaseFirestore db;

    private final String studentCollection = "students";
    private final String classesCollection = "classes";
    private final String isInClassCollection = "isInClass";

    FirebaseHelper(){
        db = FirebaseFirestore.getInstance();
    }

/********************************STUDENT REGISTRATION**********************************************/

    public void registerStudentFirebase(final Student student){
        //TODO: changed to Object have not tested it, might cause problems
        Map<String, Object> mapStudent = new HashMap<>();

        mapStudent.put("userName", student.getUserName());
        mapStudent.put("email", student.getEmail());
        mapStudent.put("password", student.getPassWord());
        mapStudent.put("classesList", student.getClassesList());

        //TODO check if student exists before registering

        db.collection(studentCollection)
                .add(mapStudent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //TODO: add dialog with success message, might have to use interface, see
                        //      validate student for example
                        Log.d("Firebase", "DocumentSnapshot of student added with ID: "
                                + documentReference.getId());
                        Log.println(Log.DEBUG, "Firebase", "Register success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error adding student document", e);
                        Log.println(Log.DEBUG, "Firebase", "Register Failure");
                    }
                });
    }

//dont think we need these anymore but just in case
//
//    public Student getStudentFirebase(String email){
//        Student student = new Student();
//        Log.println(Log.DEBUG, "Firebase", "STARTED");
//        Object studentobj;
//        db.collection(studentCollection).
//                get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            processStudentData(task);
//                        } else {
//                            Log.w("Firebase", "Error getting documents.", task.getException());
//                            Log.println(Log.DEBUG, "Firebase", "Error getting documents. " + task.getException());
//
//                        }
//                    }
//                });
//        return student;
//    }
//
//    public void processStudentData(Task<QuerySnapshot> task) {
//        Object studentobj;
//        for(QueryDocumentSnapshot document : task.getResult()) {
//            Log.d("Firebase", document.getId() + " => " + document.getData());
//            Log.println(Log.DEBUG, "Firebase", document.getId() + " => " + document.getData());
//            studentobj = document.getData();
//        }
//        Log.println(Log.DEBUG, "Firebase", "END");
//    }

    public void validateStudentLogin(final FirebaseInterface firebaseInterface){
        Log.println(Log.DEBUG, "Firebase", "STARTED");
        //Gets database student document
        db.collection(studentCollection).
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //because this is a asynchronous process, the execution flow that called
                        //this function will continue executing before this function finishes
                        //we must use interface that will call onGetClassesSuccess or onFailure function
                        //once this process is complete. These two functions are implemented in
                        //Main Activity
                        if(task.isSuccessful()){
                            firebaseInterface.onSuccess(task);
                        } else {
                            firebaseInterface.onFailed(task);
                        }
                    }
                });
    }

    public void addClassToStudentClassList(Student student){
        Map<String, Object> classList = new HashMap<>();
        classList.put("classesList", student.getClassesList());

        db.collection(studentCollection).document(student.getDocId())
                .set(classList, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "ClassesList successfully update");
                        Log.println(Log.DEBUG, "Firebase", "ClassesList successfully update");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firebase", "ClassesList update failed");
                        Log.println(Log.DEBUG, "Firebase", "ClassesList update failed");
                    }
                });
    }
/********************************END OF STUDENT REGISTRATION***************************************/

/********************************CLASSES***********************************************************/
    public void registerClass(Classes classes){
        Map<String, Object> mapClass = new HashMap<>();

        mapClass.put("classDay", classes.getClassDay());
        mapClass.put("classLocation", classes.getClassLocation());
        mapClass.put("className", classes.getClassName());
        mapClass.put("notification", classes.getNotification());
        mapClass.put("sectionNumber", classes.getSectionNumber());

        db.collection(classesCollection)
                .add(mapClass)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //TODO: add dialog with success message, might have to use interface, see
                        //      validate student for example
                        Log.d("Firebase", "DocumentSnapshot of Classes added with ID: "
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error adding classes document", e);
                    }
                });
    }
    //used for creating class
    public void getClasses(final FirebaseClassesInterface firebaseClassesInterface,
                           final Classes classes){
        db.collection(classesCollection).
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //see validateStudentLogin's comments for explanation
                        //functions implemented in Homepage
                        if(task.isSuccessful()){
                            firebaseClassesInterface.onGetClassesSuccess(task, classes);
                        }else {
                            firebaseClassesInterface.onGetClassesFailed(task);
                        }
                    }
                });
    }

    //used for loading classes student is registerd in
    public void loadClasses(final FirebaseClassesInterface firebaseClassesInterface) {
        db.collection(classesCollection).
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //see validateStudentLogin's comments for explanation
                        //functions implemented in Homepage
                        if(task.isSuccessful()){
                            firebaseClassesInterface.onLoadClassesSuccess(task);
                        }else {
                            firebaseClassesInterface.onLoadClassesFailed(task);
                        }
                    }
                });
    }

/********************************END OF CLASSES****************************************************/

/********************************IS IN CLASSES*****************************************************/

    public void registerIsInClass(IsInClass isInClass) {
        Map<String, Object> mapIsInClass = new HashMap<>();

        mapIsInClass.put("email", isInClass.getEmail());
        mapIsInClass.put("className", isInClass.getClassName());
        mapIsInClass.put("sectionNumber", isInClass.getSectionNumber());

        db.collection(isInClassCollection)
                .add(mapIsInClass)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //TODO: add dialog with success message, might have to use interface, see
                        //      validate student for example
                        Log.d("Firebase", "DocumentSnapshot of isInClass added with ID: "
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error adding student document", e);
                    }
                });
    }

    public void getisInClasses(final FirebaseClassesInterface firebaseClassesInterface,
                               final IsInClass isInClass, final Classes classes){
        db.collection(isInClassCollection).
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //see validateStudentLogin's comments for explanation
                        //functions implemented in Homepage
                        if(task.isSuccessful()){
                            firebaseClassesInterface.onGetIsInClassesSuccess(task, isInClass, classes);
                        }else {
                            firebaseClassesInterface.onGetIsInClassesFailed(task);
                        }
                    }
                });
    }


/********************************END OF IS IN CLASSES**********************************************/
}
