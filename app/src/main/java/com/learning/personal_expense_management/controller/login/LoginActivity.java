package com.learning.personal_expense_management.controller.login;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.databinding.ActivityLoginBinding;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.UserProfileListener;
import com.learning.personal_expense_management.utilities.Constants;
import com.learning.personal_expense_management.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private static final int REQUEST_CODE = 1;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private PreferenceManager preferenceManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }

    private void init() {

        preferenceManager = new PreferenceManager(LoginActivity.this);

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Log.d("checkstatus", "key, true");
        } else if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN) == false || preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN) == null) {
            Log.d("checkstatus", "key, false");
        }

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang xử lý dữ liệu...");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        storage = FirebaseStorage.getInstance();
    }

    private void setListeners() {
        binding.btnLoginGoogle.setOnClickListener(v -> {
            loginWithGoogle();
        });

        binding.btnSkipLogin.setOnClickListener(v -> {
            progressDialog.show();
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                Intent intent = new Intent(LoginActivity.this, RootActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, uid, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Some thing error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        });
    }

    private void loginWithGoogle() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(LoginActivity.this, gso);
        signInWithGoogle();
    }

    private void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        //startActivityForResult(signInIntent, 100);
        callIntentGoogle.launch(signInIntent);
    }

    private ActivityResultLauncher<Intent> callIntentGoogle = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                progressDialog.show();
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                if (signInAccountTask.isSuccessful()) {
                    progressDialog.show();
                    Log.d("status", "Receive data from GG successfully");
                    try {
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        if (googleSignInAccount != null) {
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            mAuth.signInWithCredential(authCredential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.show();
                                        // Cập nhật thông tin người dùng lên Firebase Authentication
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        preferenceManager.putString("imageProfile", user.getPhotoUrl().toString());
                                        //updateUserInfo(user);
                                        // them thong tin tai khoan vao realtime
                                        if (user != null) {
                                            UserProfile userProfile = new UserProfile(
                                                    user.getUid(),
                                                    user.getDisplayName(),
                                                    user.getEmail(),
                                                    "VND",
                                                    "Vietnamese",
                                                    "NO",
                                                    false,
                                                    false,
                                                    false
                                            );

                                            FireStoreService.isExistAccount(userProfile, new UserProfileListener() {
                                                @Override
                                                public void onExist(boolean isExist) {
                                                    if(isExist) {
                                                        FireStoreService.addUserProfile(userProfile);
                                                    }
                                                    startActivity(new Intent(LoginActivity.this, RootActivity.class));
                                                    finish();
                                                }

                                                @Override
                                                public void onUserProfilesLoaded(List<UserProfile> userProfiles) {

                                                }

                                                @Override
                                                public void onError(String errorMessage) {
                                                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                                }
                                            });



                                        }
                                    } else {
                                        // Đăng nhập thất bại
                                        //pushFailerNotification();
                                        //CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Thất bại", R.drawable.ic_phobien_1);
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                                        //Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
}