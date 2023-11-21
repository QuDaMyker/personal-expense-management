package com.learning.personal_expense_management.services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.utilities.Constants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreService {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String addUserProfile(UserProfile userProfile) {
        String[] result = {"Some thing went wrong"};
        try {
            Map<String, Object> accountMap = new HashMap<>();
            accountMap.putAll(accountMap);

            db.collection(Constants.KEY_PREFERENCE_ACCOUNTS).document(userProfile.getId()).set(accountMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        result[0] = "success";
                        Log.d("rs", result[0]);
                    } else {
                        result[0] = "error";
                        Log.d("rs", result[0]);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    result[0] = "error";
                }
            });

        } catch (Exception e) {

        }

        return result[0];
    }

    public static String addTransaction(Transaction transaction) {
        String[] result = {"Some thing went wrong"};

        try {
            DocumentReference docRef = db.collection(Constants.KEY_TRANSACTION).document();
            String id = docRef.getId();

            Map<String, Object> transactionMap = new HashMap<>();
            transactionMap.put("ownerId", transaction.getOwnerId());
            transactionMap.put("id", id);
            transactionMap.put("transactionType", transaction.getTransactionType());
            transactionMap.put("amount", transaction.getAmount());
            transactionMap.put("note", transaction.getNote());
            transactionMap.put("transactionDate", String.valueOf(transaction.getTransactionDate()));
            transactionMap.put("transactionTime", String.valueOf(transaction.getTransactionTime()));
            transactionMap.put("sourceAccount", String.valueOf(transaction.getSourceAccount()));
            transactionMap.put("destinationAccount", String.valueOf(transaction.getDestinationAccount()));
            transactionMap.put("timeStamp", transaction.getTimeStamp());
            transactionMap.put("month", transaction.getMonth());
            transactionMap.put("year", transaction.getYear());
            //transactionMap.put("timeStamp", FieldValue.serverTimestamp());

            db.collection(Constants.KEY_TRANSACTION).document(id).set(transactionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        result[0] = "success";
                        Log.d("rs", result[0]);
                    } else {
                        result[0] = "error";
                        Log.d("rs", result[0]);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    result[0] = "error";
                }
            });
        } catch (Exception e) {
            result[0] = "General Exception: " + e.getMessage();
        }

        return result[0];
    }

    public static void getAllTransaction(String ownerId, TransactionListener listener) {
        List<Transaction> transactionList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId)
                    //.orderBy("amount", Query.Direction.DESCENDING)
                    .orderBy("timeStamp", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Transaction transaction = new Transaction(document);
                                transactionList.add(transaction);
                                Log.d("rs", document.getData().toString());
                            }
                            listener.onTransactionsLoaded(transactionList);
                        } else {
                            listener.onError("Failed to fetch transactions");
                        }
                    });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void getTransaction(
            String ownerId,
            String month,
            String year,
            int transactionType,
            int isHighest,
            int isNewest,

            TransactionListener listener) {
        List<Transaction> transactionList = new ArrayList<>();
        Log.d("month - year", month + " - " + year);

        try {
            if (transactionType == -1) {
                if (isHighest == -1) {
                    if (isNewest == -1 || isNewest == 1) {
                        db.collection(Constants.KEY_TRANSACTION)
                                .whereEqualTo("ownerId", ownerId)
                                .whereEqualTo("month", month)
                                .whereEqualTo("year", year)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.DESCENDING)
                                .get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("result fetch", "successful");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Transaction transaction = new Transaction(document);
                                            transactionList.add(transaction);
                                            Log.d("rs - trasaction", document.getData().toString());
                                        }
                                        listener.onTransactionsLoaded(transactionList);
                                    } else {
                                        listener.onError("Failed to fetch transactions");
                                    }
                                });
                    } else {
                        db.collection(Constants.KEY_TRANSACTION)
                                .whereEqualTo("ownerId", ownerId)
                                .whereEqualTo("month", month)
                                .whereEqualTo("year", year)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.ASCENDING)
                                .get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("result fetch", "successful");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Transaction transaction = new Transaction(document);
                                            transactionList.add(transaction);
                                            Log.d("rs - trasaction", document.getData().toString());
                                        }
                                        listener.onTransactionsLoaded(transactionList);
                                    } else {
                                        listener.onError("Failed to fetch transactions");
                                    }
                                });
                    }
                } else if (isHighest == 0) {
                    db.collection(Constants.KEY_TRANSACTION)
                            .whereEqualTo("ownerId", ownerId)
                            .whereEqualTo("month", month)
                            .whereEqualTo("year", year)
                            .orderBy("amount", Query.Direction.ASCENDING)
                            //.orderBy("timeStamp", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("result fetch", "successful");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Transaction transaction = new Transaction(document);
                                        transactionList.add(transaction);
                                        Log.d("rs - trasaction - thap nhat", document.getData().toString());
                                    }
                                    listener.onTransactionsLoaded(transactionList);
                                } else {
                                    listener.onError("Failed to fetch transactions");
                                }
                            });
                } else {
                    db.collection(Constants.KEY_TRANSACTION)
                            .whereEqualTo("ownerId", ownerId)
                            .whereEqualTo("month", month)
                            .whereEqualTo("year", year)
                            .orderBy("amount", Query.Direction.DESCENDING)
                            //.orderBy("timeStamp", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("result fetch", "successful");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Transaction transaction = new Transaction(document);
                                        transactionList.add(transaction);
                                        Log.d("rs - trasaction", document.getData().toString());
                                    }
                                    listener.onTransactionsLoaded(transactionList);
                                } else {
                                    listener.onError("Failed to fetch transactions");
                                }
                            });
                }

            } else {
                if (isHighest == -1) {
                    if (isNewest == -1 || isNewest == 1) {
                        db.collection(Constants.KEY_TRANSACTION)
                                .whereEqualTo("ownerId", ownerId)
                                .whereEqualTo("month", month)
                                .whereEqualTo("year", year)
                                .whereEqualTo("transactionType", transactionType)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.DESCENDING)
                                .get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("result fetch", "successful");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Transaction transaction = new Transaction(document);
                                            transactionList.add(transaction);
                                            Log.d("rs - trasaction", document.getData().toString());
                                        }
                                        listener.onTransactionsLoaded(transactionList);
                                    } else {
                                        listener.onError("Failed to fetch transactions");
                                    }
                                });
                    } else {
                        db.collection(Constants.KEY_TRANSACTION)
                                .whereEqualTo("ownerId", ownerId)
                                .whereEqualTo("month", month)
                                .whereEqualTo("year", year)
                                .whereEqualTo("transactionType", transactionType)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.ASCENDING)
                                .get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("result fetch", "successful");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Transaction transaction = new Transaction(document);
                                            transactionList.add(transaction);
                                            Log.d("rs - trasaction", document.getData().toString());
                                        }
                                        listener.onTransactionsLoaded(transactionList);
                                    } else {
                                        listener.onError("Failed to fetch transactions");
                                    }
                                });
                    }

                } else if (isHighest == 0) {
                    db.collection(Constants.KEY_TRANSACTION)
                            .whereEqualTo("ownerId", ownerId)
                            .whereEqualTo("month", month)
                            .whereEqualTo("year", year)
                            .whereEqualTo("transactionType", transactionType)
                            .orderBy("amount", Query.Direction.ASCENDING)
                            //.orderBy("timeStamp", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("result fetch", "successful");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Transaction transaction = new Transaction(document);
                                        transactionList.add(transaction);
                                        Log.d("rs - trasaction", document.getData().toString());
                                    }
                                    listener.onTransactionsLoaded(transactionList);
                                } else {
                                    listener.onError("Failed to fetch transactions");
                                }
                            });
                } else {
                    db.collection(Constants.KEY_TRANSACTION)
                            .whereEqualTo("ownerId", ownerId)
                            .whereEqualTo("month", month)
                            .whereEqualTo("year", year)
                            .whereEqualTo("transactionType", transactionType)
                            .orderBy("amount", Query.Direction.DESCENDING)
                            //.orderBy("timeStamp", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("result fetch", "successful");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Transaction transaction = new Transaction(document);
                                        transactionList.add(transaction);
                                        Log.d("rs - trasaction", document.getData().toString());
                                    }
                                    listener.onTransactionsLoaded(transactionList);
                                } else {
                                    listener.onError("Failed to fetch transactions");
                                }
                            });
                }

            }

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void getParentTransaction(String ownerId, String date, TransactionListener listener) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Transaction transaction = new Transaction(documentSnapshot);
                        transactionList.add(transaction);
                        Log.d("parentTransaction", documentSnapshot.getData().toString());
                    }
                    listener.onTransactionsLoaded(transactionList);
                } else {
                    listener.onError("Failed to fetch transactions");
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void deleteTransaction(String ownerId, String transactionId) {
        db.collection(Constants.KEY_TRANSACTION).document(transactionId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("rs - delete - transaction", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("rs - delete - transaction", "Error deleting document", e);
                    }
                });
    }


}
