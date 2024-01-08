package com.learning.personal_expense_management.services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreService {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void isExistAccount(UserProfile userProfile, UserProfileListener listener) {
        db.collection(Constants.KEY_USER_PROFILE).whereEqualTo("id", userProfile.getId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean exists = !task.getResult().isEmpty();
                listener.onExist(exists);
            } else {
                listener.onError("Error checking account existence: " + task.getException().getMessage());
            }
        });
    }

    public static String addUserProfile(UserProfile userProfile) {
        String[] result = {"Some thing went wrong"};
        try {
            Map<String, Object> userProfiletMap = new HashMap<>();
            userProfiletMap.put("id", userProfile.getId());
            userProfiletMap.put("name", userProfile.getName());
            userProfiletMap.put("email", userProfile.getEmail());
            userProfiletMap.put("defaultCurrency", userProfile.getDefaultCurrency());
            userProfiletMap.put("language", userProfile.getLanguage());
            userProfiletMap.put("securityMethod", userProfile.getSecurityMethod());
            userProfiletMap.put("tip", userProfile.isTip());
            userProfiletMap.put("lowBalanceAlert", userProfile.isLowBalanceAlert());
            userProfiletMap.put("dailyReminders", userProfile.isDailyReminders());

            db.collection(Constants.KEY_USER_PROFILE).document(userProfile.getId()).set(userProfiletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void getUserProfile(String id, UserProfileListener listener) {
        List<UserProfile> userProfileList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_USER_PROFILE).whereEqualTo("id", id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserProfile userProfile = new UserProfile(document);
                        userProfileList.add(userProfile);
                        Log.d("rs", document.getData().toString());
                    }
                    listener.onUserProfilesLoaded(userProfileList);
                } else {
                    listener.onError("Failed to fetch transactions");
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static String updateUserProfile(UserProfile userProfile) {
        String[] result = {"Some thing went wrong"};
        try {
            Map<String, Object> userProfiletMap = new HashMap<>();
            userProfiletMap.put("name", userProfile.getName());
            userProfiletMap.put("email", userProfile.getEmail());

            db.collection(Constants.KEY_USER_PROFILE).document(userProfile.getId())
                    .update(userProfiletMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            result[0] = "success";
                            Log.d("rs", result[0]);
                        } else {
                            result[0] = "error";
                            Log.d("rs", result[0]);
                        }
                    });

        } catch (Exception e) {
            result[0] = "General Exception: " + e.getMessage();
        }
        return result[0];
    }



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
            transactionMap.put("categoryId", String.valueOf(transaction.getCategoryId()));
            transactionMap.put("timeStamp", transaction.getTimeStamp());
            transactionMap.put("month", transaction.getMonth());
            transactionMap.put("year", transaction.getYear());
            transactionMap.put("isFuture", transaction.isFuture());
            //transactionMap.put("timeStamp", FieldValue.serverTimestamp());

            db.collection(Constants.KEY_TRANSACTION).document(id).set(transactionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onCallback("success");
     

     

        } else {
                        callback.onCallback("error");
                        result[0] = "error";
                        Log.d("addTransaction - rs", "error");
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

        return 


    }

    public static void getAllTransaction(String ownerId, TransactionListener listener) {
        List<Transaction> transactionList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId)
                    //.orderBy("amount", Query.Direction.DESCENDING)
                    .orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Transaction transaction = new Transaction(document);
                                transactionList.add(transaction);
                                Log.d("getAllTransaction", document.getData().toString());
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

    public static void getTransaction(String ownerId, String month, String year, int transactionType, int isHighest, int isNewest,

                                      TransactionListener listener) {
        List<Transaction> transactionList = new ArrayList<>();
        Log.d("month - year", month + " - " + year);

        try {
            if (transactionType == -1) {
                if (isHighest == -1) {
    
    
                    if (isNewest == -1 || isNewest == 1) {
                        db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
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
                        db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.ASCENDING).get().addOnCompleteListener(task -> {
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
                    db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year).orderBy("amount", Query.Direction.ASCENDING)
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
                    db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year).orderBy("amount", Query.Direction.DESCENDING)
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
                        db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year).whereEqualTo("transactionType", transactionType)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
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
                        db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year).whereEqualTo("transactionType", transactionType)
                                //.orderBy("amount", Query.Direction.DESCENDING)
                                .orderBy("timeStamp", Query.Direction.ASCENDING).get().addOnCompleteListener(task -> {
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
                    db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year).whereEqualTo("transactionType", transactionType).orderBy("amount", Query.Direction.ASCENDING)
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
                    db.collection(Constants.KEY_TRANSACTION).whereEqualTo("ownerId", ownerId).whereEqualTo("month", month).whereEqualTo("year", year).whereEqualTo("transactionType", transactionType).orderBy("amount", Query.Direction.DESCENDING)
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

    public static void getOneTransaction(String id, OneTransactionListener listener) {
        try {
            db.collection(Constants.KEY_TRANSACTION).whereEqualTo("id", id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Transaction transaction = new Transaction((QueryDocumentSnapshot) documentSnapshot);
                        listener.getTransaction(transaction);
                        break;
                    }
                    Log.e("getOneTransaction - rs", "success");
                } else {
                    Log.d("getOneTransaction - rs", "Error getting document: " + task.getException());

                }
            });
        } catch (Exception e) {

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
                            
                                    listener.onTransaction
                                    se {
                                        ener.onError("Failed to fetch transactions");
                             
                                        
                                
                                    .onError(e.getMessage());
                         
                                    
                        
                    tatic void updateTransaction(Transaction transaction, FirestoreCallback callback) {
        try {

            Map<String, Object> transactionMap = new HashMap<>();
            transactionMap.put("ownerId", transaction.getOwnerId());
            transactionMap.put("id", transaction.getId());
            transactionMap.put("transactionType", transaction.getTransactionType());
            transactionMap.put("amount", transaction.getAmount());
            transactionMap.put("note", transaction.getNote());
            transactionMap.put("transactionDate", String.valueOf(transaction.getTransactionDate()));
            transactionMap.put("transactionTime", String.valueOf(transaction.getTransactionTime()));
            transactionMap.put("sourceAccount", String.valueOf(transaction.getSourceAccount()));
            transactionMap.put("destinationAccount", String.valueOf(transaction.getDestinationAccount()));
            transactionMap.put("categoryId", String.valueOf(transaction.getCategoryId()));
            transactionMap.put("timeStamp", transaction.getTimeStamp());
            transactionMap.put("month", transaction.getMonth());
            transactionMap.put("year", transaction.getYear());
            transactionMap.put("isFuture", transaction.isFuture());

            db.collection(Constants.KEY_TRANSACTION).document(transaction.getId()).update(transactionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onCallback("success");

                    } else {
                        callback.onCallback("error");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onCallback("er");
                }
            });
        } catch (Exception e) {e.printStackTrace();}}

    public static void getTransactionWallet(String ownerId, String walletName, TransactionListener listener) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            db.collection(Constants.KEY_TRANSACTION)
                    .whereEqualTo("sourceAccount", walletName)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Transaction transaction = new Transaction(documentSnapshot);
                        transactionList.add(transaction);
                        Log.d("parentTransactionWallet", documentSnapshot.getData().toString());
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
        db.collection(Constants.KEY_TRANSACTION).document(transactionId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("rs - delete - transaction", "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("rs - delete - transaction", "Error deleting document", e);
            }
        });
    }
                
                    
                    tatic String addWallet(Wallet walle
                        
                     
                            
                             
                            D
                            String id = docRef.getId();
                     
                            Map<String, Object> walletMap = new HashMap<>
                        etMap.put("ownerId", wallet.getOwnerId());
                    w
                    walletMap.put("walletName", wallet.getWalletName());
            walletMap.put("lowBalanceAlert", wallet.isLowBalanceAlert());
            walletMap.put("minimumBalance", wallet.getMinimumBalance());
            walletMap.put("goalSavingsEnabled", wallet.isGoalSavingsEnabled());
            walletMap.put("goalAmount", wallet.getGoalAmount());
            walletMap.put("savingsDeadline", wallet.getSavingsDeadline());
            walletMap.put("frequency", wallet.getFrequency());
            walletMap.put("currentMoney", wallet.getCurrentMoney());
            //transactionMap.put("timeStamp", FieldValue.serverTimestamp());

            db.collection(Constants.KEY_WALLET).document(id).set(walletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        result[0] = "success";
                        Log.d("rs", result[0]);
                    } else {
                        result[0] = "er

    or";
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

    public static void getOneWallet(String id, OneWalletListener listener) {
        try {
            db.collection(Constants.KEY_WALLET).whereEqualTo("id", id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Wallet wallet = new Wallet((QueryDocumentSnapshot) documentSnapshot);
                        listener.getWallet(wallet);
                        break;
                    }
                    Log.e("getOneWallet - rs", "success");
                } else {
                    Log.d("getOneWallet - rs", "Error getting document: " + task.getException());

                }
            });
        } catch (Exception e) {

        }
    }

    public static String editWallet(Wallet wallet) {
        String[] result = {"Some thing went wrong"};

        try {
            String walletId = wallet.getId();


    
    

        walletMap.put("walletName", wallet.getWalletName());
            walletMap.put("lowBalanceAlert", wallet.isLowBalanceAlert());
            walletMap.put("minimumBalance", wallet.getMinimumBalance());
           wale
  
    tMap.put("go
    l

        walletMap.put("savingsDeadline", wallet.getSavingsDeadline());
            walletMap.put("frequency", wallet.getFrequency());
            walletMap.put("currentMoney", wallet.getCurrentMoney());
            //transactionMap.put("timeStamp", FieldValue.serverTimestamp());
  
            // Thực hiện cập nhật dữ liệu trong Firebase
            db.collection(Constants.KEY_WALLET).document(walletId).update(walletMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    result[0] = "success";
                    Log.d("rs", result[0]);
                 } else {

                    Log.d("rs", result[0]);
                }
            });

        } catch (Exception e) {
            result[0] = "General Exception: " + e.getMessage();
        } 

            

    public static void getWallet(String ownerId, WalletListener listener) {
        List<Wallet> walletList = new ArrayList<>();

        t
            db.collection(Constants.KEY_WALLET).whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Wallet wallet = new Wallet(document);
                        walletList.add(wallet);
                        Log.d("rs", document.getData().toString());
                    }
         
                } else {
                    listener.onError("Failed to fetch wallets");
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void deleteWallet(String ownerId, String walletId) {
        db.collection(Constants.KEY_WALLET).document(walletId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("rs - delete - wallet", "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                
                        Log.w
                    }
                        
                    
                
                    tatic Str
                    ng[] result = {"Some thing went wrong"};
                        
                     
                            {
                    DocumentReference docRef = db.colle
                        ng id = docRef.getId();
                    
                    Map<String, Object> accountMap = new HashMap<
                    accountMa
                    accountMap.put("id", id);
                        untMap.put("accountType", account.getAccountType());
                    a
                    accountMap.put("cardNumber", account.getCardNumber());
            accountMap.put("expirationDate", account.getExpirationDate());
            accountMap.put("currentBalance", account.getCurrentBalance());

            docRef.set(accountMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void editAccount(Account account, FirestoreCallback callback) {
        ty {

    
    
            Map<String, Object> accountMap = new HashMap<>();
             accountMap.put("id", account.getId());
            accountMap.put("accountType", account.getAccountType());
            accountMap.put("cardName", account.getCardName());
            accountMap.put("cardNumber", account.getCardNumber());
            accountMap.put("expirationDate", account.getExpirationDate());
            accountMap.put("currentBalance", account.getCurrentBalance());
           Logd

    ("editAccoun
     - account", account.toString());
            db.collection(Constants.KEY_ACCOUNT).document(account.getId()).update(accountMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                 public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onCallback("success");
                    } else {
                        callback.onCallback("error");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onCallback("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAccount(String ownerId, AccountListener listener) {
        List<Account> accountList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_ACCOUNT).whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Account account = new Account(document);
                        accountList.add(account);
                        Log.d("rs", document.getData().toString());
                    }
                    listener.onAccountsLoaded(accountList);
                } else {
                    listener.onError("Failed to fetch transactions");
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    
        
    tatty {
    
    
        db.collection(Constants.KEY_ACCOU
            if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Account account = new Account((QueryDocumentSnapshot) documentSnapshot);
                        listener.getAccount(account);
                     

     
        }
               
       Log.d("ge
    O
        
            }
            });
        } catch (Exception e) {

        }
    }

    public static void deleteAccount(String ownerId, String accountId) {
        db.collection(Constants.KEY_ACCOUNT).document(accountId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("rs - delete - account", "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("rs - delete - account", "Error deleting document", e);
            }
        });
    }

    public static String addCategory(Category category, FirestoreCallback callback) {
        String[] result = {"Some thing went wrong"};

        try {
            DocumentReference docRef = db.collection(Constants.KEY_CATEGORY).document();
            String id = docRef.getId();
            category.setId(id);

            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("ownerId", category.getOwnerId());
            categoryMap.put("id", id);
            categoryMap.put("categoryName", category.getName());
            categoryMap.put("background", category.getBackGround());
            categoryMap.put("icon", category.getIcon());
            categoryMap.put("colorIcon", category.getColorIcon());
            categoryMap.put("isIncome", category.getIsIncome());


            db.collection(Constants.KEY_CATEGORY).document(category.getId()).set(categoryMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onCallback("success");
                        result[0] = "success";
                    } else {
                        callback.onCallback("error");
                        result[0] = "error";
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

    public static void getCategory(String ownerId, CategoryListener listener) {
        List<Category> categoryList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_CATEGORY).whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Category category = new Category(document);
                        categoryList.add(category);
                    }
                    listener.onCategoryLoaded(categoryList);
                } else {
                    listener.onError("Failed to fetch transactions");
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void getOneCategory(String categoryId, OneCategoryListener listener) {
        try {
            db.collection(Constants.KEY_CATEGORY).whereEqualTo("id", categoryId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Category category = new Category((QueryDocumentSnapshot) documentSnapshot);
                        listener.getCategory(category);
                        break;
                    }
                    Log.e("getOneCategory - rs", "success");
                } else {
                    Log.d("getOneCategory - rs", "Error getting document: " + task.getException());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateCategory(Category category, FirestoreCallback callback) {
        try {


    
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("ownerId", category.getOwnerId());
            categoryMap.put("id", id);
            categoryMap.put("categoryName", category.getName());
            categoryMap.put("background", category.getBackGround());
            categoryMap.put("icon", category.getIcon());
            categoryMap.put("colorIcon", category.getColorIcon());
            categoryMap.put("isIncome", category.getIsIncome());

     

                    if (task.isSuccessful()) {
                        callback.onCallback("success");

                    } else {
                        callback.onCallback("error");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onCallback("er");
                }
            });
        } catch (Exception e) {

        }
    }

    public static void deleteCategory(String ownerId, String categoryId, FirestoreCallback callback) {
        db.collection(Constants.KEY_CATEGORY).document(categoryId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("rs - delete - category", "DocumentSnapshot successfully deleted!");
                callback.onCallback("success");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("rs - delete - category", "Error deleting document", e);
                callback.onCallback("error");

            }
        });
    }

    public static String addLoan(Loan loan) {
        String[] result = {"Some thing went wrong"};

        try {
            DocumentReference docRef = db.collection(Constants.KEY_LOAN).document();
            String id = docRef.getId();

            Map<String, Object> loanMap = new HashMap<>();
            loanMap.put("ownerId", loan.getOwnerId());
            loanMap.put("id", id);
            loanMap.put("borrowerName", loan.getBorrowerName());
            loanMap.put("amount", loan.getAmount());
            loanMap.put("note", loan.getNote());
            loanMap.put("transactionDate", loan.getTransactionDate());
            loanMap.put("transactionTime", loan.getTransactionTime());
            loanMap.put("repaymentPeriod", loan.getRepaymentPeriod());


            db.collection(Constants.KEY_LOAN).document(loan.getId()).set(loanMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void getLoan(String ownerId, LoanListener listener) {
        List<Loan> loanList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_LOAN).whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Loan loan = new Loan(document);
                        loanList.add(loan);
                        Log.d("rs", document.getData().toString());
                    }
                    listener.onLoanLoaded(loanList);
                } else {
                    listener.onError("Failed to fetch transactions");
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void deleteLoan(String ownerId, String loanId) {
        db.collection(Constants.KEY_LOAN).document(loanId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("rs - delete - loan", "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("rs - delete - loan", "Error deleting document", e);
            }
        });
    }

}
