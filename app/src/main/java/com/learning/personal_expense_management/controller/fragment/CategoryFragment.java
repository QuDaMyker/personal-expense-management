package com.learning.personal_expense_management.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.CategoryAdapter;
import com.learning.personal_expense_management.model.Category;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Category> listDeCats;
    private ArrayList<Category> listCats;
    private CategoryAdapter categoryAdapter;

    private RecyclerView rcvCats;
    private RecyclerView.LayoutManager rcvLayoutManager;
    private TabLayout tabInOut;
    private Button btnNewCat;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //dataInitialize();
        rcvCats = view.findViewById(R.id.rcvCategories);
        btnNewCat = view.findViewById(R.id.btnNewCat);
        rcvLayoutManager = new GridLayoutManager(getActivity(),4);
        rcvCats.setLayoutManager(rcvLayoutManager);
        categoryAdapter = new CategoryAdapter(this.getContext());
        categoryAdapter.setData(listCats);
        rcvCats.setAdapter(categoryAdapter);

    }

    //một số danh mục mặc định
    private void createDefaultLisCategories(String userID) {
        listCats = new ArrayList<Category>();
        listCats.add(new Category(userID, "Thưởng", R.color.colorItem1, R.drawable.ic_money, R.color.colorIcon1));
        listCats.add(new Category(userID,"Làm đẹp", R.color.colorItem2, R.drawable.ic_beauty_heart, R.color.colorIcon2));
        listCats.add(new Category(userID, "Máy bay", R.color.colorItem1,R.drawable.ic_airplane, R.color.colorIcon1));
        listCats.add(new Category(userID, "Sửa chữa", R.color.colorItem3,R.drawable.ic_repair, R.color.colorIcon3));
        listCats.add(new Category(userID, "Du lịch", R.color.colorItem6,R.drawable.ic_case_travel, R.color.colorIcon4));
        listCats.add(new Category(userID, "Tàu điện", R.color.colorItem3,R.drawable.ic_train_metro, R.color.colorIcon3));
        listCats.add(new Category(userID, "Hóa đơn", R.color.colorItem5,R.drawable.ic_fee_tax, R.color.colorIcon5));
        listCats.add(new Category(userID, "Thư", R.color.colorItem4,R.drawable.ic_envelope, R.color.colorIcon6));
        listCats.add(new Category(userID, "Mua sắm", R.color.colorItem1,R.drawable.icon_shopping, R.color.colorIcon1));
        listCats.add(new Category(userID, "Két sắt", R.color.colorItem5,R.drawable.ic_safe_saving, R.color.colorIcon5));
        listCats.add(new Category(userID, "Lương", R.color.colorItem8,R.drawable.icon_wallet_earnings, R.color.green));
        listCats.add(new Category(userID, "Thức ăn", R.color.colorItem7,R.drawable.ic_restaurant_fork, R.color.colorIcon7));
        listCats.add(new Category(userID, "Thuốc", R.color.colorItem2,R.drawable.icon_drugs, R.color.colorIcon2));
        listCats.add(new Category(userID, "Nước uống", R.color.colorItem6,R.drawable.ic_coffee_bistro, R.color.colorIcon4));
        listCats.add(new Category(userID, "Thời gian chờ", R.color.colorItem3,R.drawable.ic_pending_time_wait_transaction_clock, R.color.colorIcon3));
        listCats.add(new Category(userID, "Nước", R.color.colorItem1,R.drawable.ic_water_tap, R.color.colorIcon1));
        listCats.add(new Category(userID, "Lãi", R.color.colorItem8,R.drawable.ic_interest, R.color.green));
        listCats.add(new Category(userID, "Em bé", R.color.colorItem7,R.drawable.ic_kid_care_stroller, R.color.colorIcon7));
        listCats.add(new Category(userID, "Bảo hiểm", R.color.colorItem5,R.drawable.ic_insurance_shield, R.color.colorIcon5));
        listCats.add(new Category(userID, "Nhà", R.color.colorItem4,R.drawable.ic_home, R.color.colorIcon6));
        listCats.add(new Category(userID, "Sở thích", R.color.colorItem4,R.drawable.ic_kite_hobby, R.color.colorIcon6));
        listCats.add(new Category(userID, "Y tế", R.color.colorItem2,R.drawable.ic_hospital, R.color.colorIcon2));
        listCats.add(new Category(userID, "Thể thao", R.color.colorItem1,R.drawable.ic_sport_gym, R.color.colorIcon1));
        listCats.add(new Category(userID, "Quần áo", R.color.colorItem1,R.drawable.ic_tshirt, R.color.colorIcon1));
        listCats.add(new Category(userID, "Quà tặng", R.color.colorItem2,R.drawable.ic_gift_box, R.color.colorIcon2));
        listCats.add(new Category(userID, "Trái cây", R.color.colorItem8,R.drawable.ic_apple, R.color.green));
        listCats.add(new Category(userID, "Giải trí", R.color.colorItem6,R.drawable.ic_mask, R.color.colorIcon4));
        listCats.add(new Category(userID, "Di chuyển", R.color.colorItem1,R.drawable.ic_car_drive, R.color.colorIcon1));
        listCats.add(new Category(userID, "Xăng dầu", R.color.colorItem4,R.drawable.ic_station_fuel, R.color.colorIcon6));
        listCats.add(new Category(userID, "Thiết bi", R.color.colorItem1,R.drawable.ic_computer, R.color.colorIcon1));
        listCats.add(new Category(userID, "Từ thiện", R.color.colorItem2,R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2));
        listCats.add(new Category(userID, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt", R.color.colorItem2,R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2));
        listCats.add(new Category(userID, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt", R.color.colorItem2,R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2));
        listCats.add(new Category(userID, "Từ thiện", R.color.colorItem2,R.drawable.ic_social_heart_donation_care_calendar, R.color.colorIcon2));

    }
}