package com.learning.personal_expense_management.controller.activity.login;

import static com.learning.personal_expense_management.utilities.Constants.KEY_PREFERENCE_ACCOUNTS;

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
import com.learning.personal_expense_management.controller.activity.RootActivity;
import com.learning.personal_expense_management.databinding.ActivityLoginBinding;
import com.learning.personal_expense_management.model.HoSoNguoiDung;
import com.learning.personal_expense_management.utilities.Constants;
import com.learning.personal_expense_management.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        progressDialog.setMessage("Loading...");

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
            startActivity(new Intent(this, RootActivity.class));
            Toast.makeText(this, "not login", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loginWithGoogle() {
        binding.btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                gsc = GoogleSignIn.getClient(LoginActivity.this, gso);
                signInWithGoogle();
            }
        });
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
                                        //updateUserInfo(user);
                                        // them thong tin tai khoan vao realtime
                                        if (user != null) {
                                            String personalID = user.getUid();
                                            String personName = user.getDisplayName();
                                            String personEmail = user.getEmail();
                                            Uri personPhoto = user.getPhotoUrl();

                                            Date now = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String formattedDate = dateFormat.format(now);
                                            HoSoNguoiDung hoSoNguoiDung = new HoSoNguoiDung(personalID, personName, personEmail, "VND", "Vietnamese", "PIN", false, false, false);
                                            String emailToCheck = personEmail;


                                            preferenceManager.putString(Constants.KEY_EMAIL, hoSoNguoiDung.getGmail());
                                            preferenceManager.putString(Constants.KEY_FULLNAME, hoSoNguoiDung.getTen());

                                            preferenceManager.putString(Constants.KEY_DATECREATEDACCOUNT, formattedDate);

                                            DatabaseReference accountsRef = reference.child(KEY_PREFERENCE_ACCOUNTS);

                                            Query emailQuery = accountsRef.orderByChild("gmail").equalTo(emailToCheck);

                                            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (!dataSnapshot.exists()) {
                                                        Toast.makeText(LoginActivity.this, "not exist", Toast.LENGTH_SHORT).show();
                                                        DatabaseReference newChildRef = reference.child(KEY_PREFERENCE_ACCOUNTS).push();
                                                        String generatedKey = newChildRef.getKey();
                                                        hoSoNguoiDung.setId(generatedKey);
                                                        preferenceManager.putString(Constants.KEY_USER_ID, generatedKey);

                                                        newChildRef.setValue(hoSoNguoiDung)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            // Data successfully saved
                                                                            preferenceManager.putString(Constants.KEY_FULLNAME, hoSoNguoiDung.getTen());
                                                                            preferenceManager.putString(Constants.KEY_EMAIL, hoSoNguoiDung.getGmail());
                                                                            preferenceManager.putString(Constants.KEY_DON_VI_TIEN_MAC_DINH, hoSoNguoiDung.getDonViTienMacDinh());
                                                                            preferenceManager.putString(Constants.KEY_NGON_NGU, hoSoNguoiDung.getNgonNgu());
                                                                            preferenceManager.putString(Constants.KEY_PHUONG_THUC_BAO_MAT, hoSoNguoiDung.getPhuongThucBaoMat());
                                                                            preferenceManager.putBoolean(Constants.KEY_MEO_TIEU_VAT, hoSoNguoiDung.isMeoTieuVat());
                                                                            preferenceManager.putBoolean(Constants.KEY_CANH_BAO_CAN_VI_TIEN, hoSoNguoiDung.isCanhBaoCanViTien());
                                                                            preferenceManager.putBoolean(Constants.KEY_NHAC_NHO_HANG_NGAY, hoSoNguoiDung.isNhacNhoHangNgay());
                                                                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                                            preferenceManager.putBoolean(Constants.KEY_FIRST_INSTALL, true);

                                                                            // n(getApplicationContext(), "Thông báo", "Chào mừng bạn đến với chúng tôi, hãy lựa chọn căn phòng ưng ý mình nhé", R.drawable.ic_phobien_1);
                                                                            progressDialog.dismiss();


                                                                            startActivity(new Intent(LoginActivity.this, RootActivity.class));
                                                                        } else {
                                                                            // Handle the error here
                                                                            Toast.makeText(LoginActivity.this, "Lỗi cập nhật RealtimeDB", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
//                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                                    AccountClass checkAcc = snapshot.getValue(AccountClass.class);
//                                                                    preferenceManager.putString(Constants.KEY_USER_KEY, snapshot.getKey());
//                                                                    preferenceManager.putString(Constants.KEY_IMAGE, checkAcc.getImage());
//                                                                    preferenceManager.putBoolean(Constants.KEY_IS_BLOCKED, checkAcc.getBlocked());
//                                                                    if (checkAcc.getBlocked()) {
//                                                                        progressDialog.dismiss();
//                                                                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
//
//                                                                        startActivity(new Intent(ActivityLogIn.this, ActivityBlocked.class));
//                                                                    } else {
//                                                                        CommonUtils.showNotification(getApplicationContext(), "Thông báo", "Chào mừng bạn trở lại, hãy lựa chọn căn phòng ưng ý mình nhé", R.drawable.ic_phobien_1);
//                                                                        progressDialog.dismiss();
//                                                                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
//                                                                        preferenceManager.putBoolean(Constants.KEY_FIRST_INSTALL, true);
//
//                                                                        startActivity(new Intent(ActivityLogIn.this, ActivityMain.class));
//                                                                    }
//                                                                }
                                                        Toast.makeText(LoginActivity.this, "Tai Khoan Ton Tai", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(LoginActivity.this, RootActivity.class));


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Handle the error here
                                                    //CommonUtils.showNotification(getApplicationContext(), "Trạng thái đăng nhập", "Thất bại", R.drawable.ic_phobien_1);
                                                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
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