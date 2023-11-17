package com.learning.personal_expense_management.services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class FireStoreService {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String addTransaction(Transaction transaction) {
        String[] result = {"Some thing went wrong"};

        try {
            DocumentReference docRef = db.collection(Constants.KEY_TRANSACTION).document();
            String id = docRef.getId();

            Map<String, String> transactionMap = new HashMap<>();
            transactionMap.put("ownerId", transaction.getOwnerId());
            transactionMap.put("id", id);
            transactionMap.put("transactionType", String.valueOf(transaction.getTransactionType()));
            transactionMap.put("amount", String.valueOf(transaction.getAmount()));
            transactionMap.put("note", transaction.getNote());
            transactionMap.put("transactionDate", String.valueOf(transaction.getTransactionDate()));
            transactionMap.put("transactionTime", String.valueOf(transaction.getTransactionTime()));
            transactionMap.put("sourceAccount", String.valueOf(transaction.getSourceAccount()));
            transactionMap.put("destinationAccount", String.valueOf(transaction.getDestinationAccount()));
            String rs = "error";
            db.collection(Constants.KEY_TRANSACTION)
                    .document(id)
                    .set(transactionMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                result[0] = "success";
                                Log.d("rs", result[0]);
                            } else {
                                result[0] = "error";
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
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


}
