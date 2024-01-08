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
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.UserProfile;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.FirestoreCallback;
import com.learning.personal_expense_management.services.UserProfileListener;
import com.learning.personal_expense_management.utilities.Constants;
import com.learning.personal_expense_management.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                                Log.e("uid", user.getUid());
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
                                        if (!isExist) {
                                            Log.d("noti", "vao day");
                                            FireStoreService.addUserProfile(userProfile);
                                            List<Category> categoryList = createDefaultLisCategories(user.getUid());
                                            for (int i = 0; i < categoryList.size(); i++) {
                                                FireStoreService.addCategory(categoryList.get(i), new FirestoreCallback() {
                                                    @Override
                                                    public void onCallback(String result) {
                                                        if (result.equals("success")) {

                                                        }
                                                    }
                                                });
                                            }
                                            Toast.makeText(LoginActivity.this, "Đã thêm Category mặc định", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, RootActivity.class));
                                            finish();
                                        } else {
                                            startActivity(new Intent(LoginActivity.this, RootActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onUserProfilesLoaded(List<UserProfile> userProfiles) {

                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, "Some thing error", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });


        });
    }

    private void loginWithGoogle() {
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        gsc = GoogleSignIn.getClient(LoginActivity.this, gso);
//        signInWithGoogle();
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
                                                    if (!isExist) {
                                                        Log.d("noti", "vao day");
                                                        FireStoreService.addUserProfile(userProfile);
                                                        List<Category> categoryList = createDefaultLisCategories(user.getUid());
                                                        for (int i = 0; i < categoryList.size(); i++) {
                                                            FireStoreService.addCategory(categoryList.get(i), new FirestoreCallback() {
                                                                @Override
                                                                public void onCallback(String result) {
                                                                    if (result.equals("success")) {

                                                                    }
                                                                }
                                                            });
                                                        }
                                                        Toast.makeText(LoginActivity.this, "Đã thêm Category mặc định", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(LoginActivity.this, RootActivity.class));
                                                        finish();
                                                    } else {
                                                        startActivity(new Intent(LoginActivity.this, RootActivity.class));
                                                        finish();
                                                    }
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
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
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
            progressDialog.dismiss();
        }
    });

    private List<Category> createDefaultLisCategories(String userID) {
        List<Category> listCats = new ArrayList<Category>();
        listCats.add(new Category(userID, "Thưởng", R.color.colorItem1, R.drawable.ic_money, R.color.colorIcon1, 1));
        listCats.add(new Category(userID, "Làm đẹp", R.color.colorItem2, R.drawable.ic_beauty_heart, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Máy bay", R.color.colorItem1, R.drawable.ic_airplane, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Sửa chữa", R.color.colorItem3, R.drawable.ic_repair, R.color.colorIcon3, 0));
        listCats.add(new Category(userID, "Du lịch", R.color.colorItem6, R.drawable.ic_case_travel, R.color.colorIcon4, 0));
        listCats.add(new Category(userID, "Tàu điện", R.color.colorItem3, R.drawable.ic_train_metro, R.color.colorIcon3, 0));
        listCats.add(new Category(userID, "Hóa đơn", R.color.colorItem5, R.drawable.ic_fee_tax, R.color.colorIcon5, 0));
        listCats.add(new Category(userID, "Thư", R.color.colorItem4, R.drawable.ic_envelope, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Mua sắm", R.color.colorItem1, R.drawable.icon_shopping, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Két sắt", R.color.colorItem5, R.drawable.ic_safe_saving, R.color.colorIcon5, 1));
        listCats.add(new Category(userID, "Lương", R.color.colorItem8, R.drawable.icon_wallet_earnings, R.color.green, 1));
        listCats.add(new Category(userID, "Thức ăn", R.color.colorItem7, R.drawable.ic_restaurant_fork, R.color.colorIcon7, 0));
        listCats.add(new Category(userID, "Thuốc", R.color.colorItem2, R.drawable.icon_drugs, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Nước uống", R.color.colorItem6, R.drawable.ic_coffee_bistro, R.color.colorIcon4, 0));
        listCats.add(new Category(userID, "Thời gian chờ", R.color.colorItem3, R.drawable.ic_pending_time_wait_transaction_clock, R.color.colorIcon3, 0));
        listCats.add(new Category(userID, "Nước", R.color.colorItem1, R.drawable.ic_water_tap, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Lãi", R.color.colorItem8, R.drawable.ic_interest, R.color.green, 1));
        listCats.add(new Category(userID, "Em bé", R.color.colorItem7, R.drawable.ic_kid_care_stroller, R.color.colorIcon7, 0));
        listCats.add(new Category(userID, "Bảo hiểm", R.color.colorItem5, R.drawable.ic_insurance_shield, R.color.colorIcon5, 0));
        listCats.add(new Category(userID, "Nhà", R.color.colorItem4, R.drawable.ic_home, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Sở thích", R.color.colorItem4, R.drawable.ic_kite_hobby, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Y tế", R.color.colorItem2, R.drawable.ic_hospital, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Thể thao", R.color.colorItem1, R.drawable.ic_sport_gym, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Quần áo", R.color.colorItem1, R.drawable.ic_tshirt, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Quà tặng", R.color.colorItem2, R.drawable.ic_gift_box, R.color.colorIcon2, 1));
        listCats.add(new Category(userID, "Trái cây", R.color.colorItem8, R.drawable.ic_apple, R.color.green, 0));
        listCats.add(new Category(userID, "Giải trí", R.color.colorItem6, R.drawable.ic_mask, R.color.colorIcon4, 0));
        listCats.add(new Category(userID, "Di chuyển", R.color.colorItem1, R.drawable.ic_car_drive, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Xăng dầu", R.color.colorItem4, R.drawable.ic_station_fuel, R.color.colorIcon6, 0));
        listCats.add(new Category(userID, "Thiết bi", R.color.colorItem1, R.drawable.ic_computer, R.color.colorIcon1, 0));
        listCats.add(new Category(userID, "Từ thiện", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        listCats.add(new Category(userID, "Từ thiện", R.color.colorItem2, R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2, 0));
        return listCats;

    }
}