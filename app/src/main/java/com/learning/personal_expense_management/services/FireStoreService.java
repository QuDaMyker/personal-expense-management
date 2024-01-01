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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.utilities.Constants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreService {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static boolean isExistAccount(UserProfile userProfile, UserProfileListener listener) {
        boolean result = false;

        try {
            db.collection(Constants.KEY_USER_PROFILE).whereEqualTo("id", userProfile.getId()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    listener.onExist(true);
                } else {
                    listener.onExist(false);
                }
            });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
        return result;
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
            db.collection(Constants.KEY_USER_PROFILE).whereEqualTo("id", id)
                    .get().addOnCompleteListener(task -> {
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

    public static String addWallet(Wallet wallet) {
        String[] result = {"Some thing went wrong"};

        try {
            DocumentReference docRef = db.collection(Constants.KEY_TRANSACTION).document();
            String id = docRef.getId();

            Map<String, Object> walletMap = new HashMap<>();
            walletMap.put("ownerId", wallet.getOwnerId());
            walletMap.put("id", wallet.getId());
            walletMap.put("walletName", wallet.getWalletName());
            walletMap.put("lowBalanceAlert", wallet.isLowBalanceAlert());
            walletMap.put("minimumBalance", wallet.getMinimumBalance());
            walletMap.put("goalSavingsEnabled", wallet.isGoalSavingsEnabled());
            walletMap.put("goalAmount", wallet.getGoalAmount());
            walletMap.put("savingsDeadline", wallet.getSavingsDeadline());
            walletMap.put("frequency", wallet.getFrequency());
            //transactionMap.put("timeStamp", FieldValue.serverTimestamp());

            db.collection(Constants.KEY_WALLET).document(id).set(walletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void getWallet(String ownerId, WalletListener listener) {
        List<Wallet> walletList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_WALLET).whereEqualTo("ownerId", ownerId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Wallet wallet = new Wallet(document);
                                walletList.add(wallet);
                                Log.d("rs", document.getData().toString());
                            }
                            listener.onWalletsLoaded(walletList);
                        } else {
                            listener.onError("Failed to fetch transactions");
                        }
                    });
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    public static void deleteWallet(String ownerId, String walletId) {
        db.collection(Constants.KEY_WALLET).document(walletId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("rs - delete - wallet", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("rs - delete - wallet", "Error deleting document", e);
                    }
                });
    }

    public static String addAccount(Account account) {
        String[] result = {"Some thing went wrong"};

        try {
            DocumentReference docRef = db.collection(Constants.KEY_TRANSACTION).document();
            String id = docRef.getId();

            Map<String, Object> accountMap = new HashMap<>();
            accountMap.put("ownerId", account.getOwnerId());
            accountMap.put("id", id);
            accountMap.put("accountType", account.getAccountType());
            accountMap.put("cardName", account.getCardName());
            accountMap.put("cardNumber", account.getCardNumber());
            accountMap.put("expirationDate", account.getExpirationDate());
            accountMap.put("currentBalance", account.getCurrentBalance());

            db.collection(Constants.KEY_ACCOUNT).document(account.getId()).set(accountMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void getAccount(String ownerId, AccountListener listener) {
        List<Account> accountList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_ACCOUNT).whereEqualTo("ownerId", ownerId)
                    .get().addOnCompleteListener(task -> {
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
    }

    public static void deleteAccount(String ownerId, String accountId) {
        db.collection(Constants.KEY_ACCOUNT).document(accountId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("rs - delete - account", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("rs - delete - account", "Error deleting document", e);
                    }
                });
    }

    public static String addCategory(Category category) {
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

    public static void getCategory(String ownerId, CategoryListener listener) {
        List<Category> categoryList = new ArrayList<>();

        try {
            db.collection(Constants.KEY_CATEGORY).whereEqualTo("ownerId", ownerId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = new Category(document);
                                categoryList.add(category);
                                Log.d("rs", document.getData().toString());
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

    public static void deleteCategory(String ownerId, String categoryId) {
        db.collection(Constants.KEY_CATEGORY).document(categoryId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("rs - delete - category", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("rs - delete - category", "Error deleting document", e);
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
            db.collection(Constants.KEY_LOAN).whereEqualTo("ownerId", ownerId)
                    .get().addOnCompleteListener(task -> {
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
        db.collection(Constants.KEY_LOAN).document(loanId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("rs - delete - loan", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("rs - delete - loan", "Error deleting document", e);
                    }
                });
    }

}
