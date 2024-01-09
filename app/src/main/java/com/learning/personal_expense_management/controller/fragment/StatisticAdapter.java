package com.learning.personal_expense_management.controller.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.CategoryAdapter;
import com.learning.personal_expense_management.controller.home.adapter.home.HomeRecentlyActivityAdapter;
import com.learning.personal_expense_management.controller.home.adapter.home.ObjectListener;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.CatStatistic;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.CategoryListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.OneCategoryListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatisticAdapter  extends RecyclerView.Adapter<StatisticAdapter.ViewHolder>{
    private Context context;
    private List<CatStatistic> list;
    public StatisticAdapter(Context context, List<CatStatistic> listCat) {
        this.context = context;
        this.list = listCat;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat_statistic, parent, false);
        return new StatisticAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatStatistic catStatistic = list.get(position);
        setUpData(catStatistic,holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setUpData (CatStatistic cat, @NonNull ViewHolder holder){
        FireStoreService.getOneCategory(cat.getCatId(), new OneCategoryListener() {
            @Override
            public void getCategory(Category category) {
                cat.setNameCat(category.getName());
                cat.setBackground(category.getBackGround());
                cat.setIcon(category.getIcon());

                int backgroundColor = context.getResources().getColor(cat.getBackground());
                holder.cardBG.setCardBackgroundColor(backgroundColor);
                holder.icon.setImageResource(cat.getIcon());
                holder.tvNameCat.setText(cat.getNameCat());
                int p = cat.getPrice().intValue();
                int pre = (int)cat.getPercent();
                holder.tvPrice.setText(String.valueOf(formatNumberWithDot(p)) + "đ");
                holder.tvPercent.setText(String.valueOf(formatNumberWithDot(pre))+"%");
            }
        });
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardBG;
        private ImageView icon;
        private TextView tvNameCat;
        private TextView tvPrice;
        private TextView tvPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBG = itemView.findViewById(R.id.backGround);
            icon = itemView.findViewById(R.id.iconCat);
            tvNameCat = itemView.findViewById(R.id.tvCatName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvPercent = itemView.findViewById(R.id.tvPercent);

        }
    }
    private static String formatNumberWithDot(int number) {
        StringBuilder result = new StringBuilder();
        String numberStr = String.valueOf(number);

        int count = 0;
        for (int i = numberStr.length() - 1; i >= 0; i--) {
            result.insert(0, numberStr.charAt(i));
            count++;

            if (count % 3 == 0 && i > 0) {
                result.insert(0, ".");
            }
        }

        return result.toString();
    }

}
