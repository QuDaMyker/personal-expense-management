package com.learning.personal_expense_management.controller;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Category> cats;

    public CategoryAdapter(Context context) {
        this.context = context;
    }
    public void setData (ArrayList<Category> cats)
    {
        this.cats = cats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category cat = this.cats.get(position);
        if (cat == null)
        {
            return;
        }
        int backgroundColor = context.getResources().getColor(cat.getBackGround());
        holder.cardBG.setCardBackgroundColor(backgroundColor);
        holder.imIcon.setImageResource(cat.getIcon());
        int colorIcon =  context.getResources().getColor(cat.getColorIcon());
        holder.imIcon.setColorFilter(colorIcon);
        holder.textTitle.setText(cat.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailCatDialog(cat);
            }
        });
    }

    private void showDetailCatDialog(Category cat) {
        final Dialog dialogDetail = new Dialog(context);
        dialogDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDetail.setContentView(R.layout.custom_dialog_detail_category);

        Window window = dialogDetail.getWindow();
        if(window== null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);

        dialogDetail.setCancelable(true);

        CardView cardbG = dialogDetail.findViewById(R.id.cardBGDetail);
        TextView title = dialogDetail.findViewById(R.id.textTitleDetail);
        ImageView icon = dialogDetail.findViewById(R.id.iconCatDetail) ;
        Button btnUpdate = dialogDetail.findViewById(R.id.btnUpdateDetail) ;
        Button btnDelete = dialogDetail.findViewById(R.id.btnDeleteDetail) ;
        Button btnOK = dialogDetail.findViewById(R.id.btnOkDetail) ;

        int backgroundColor = context.getResources().getColor(cat.getBackGround());
        cardbG.setCardBackgroundColor(backgroundColor);
        btnUpdate.setBackgroundColor(backgroundColor);
        btnDelete.setBackgroundColor(backgroundColor);
        btnOK.setBackgroundColor(backgroundColor);
        icon.setImageResource(cat.getIcon());
        int colorIcon =  context.getResources().getColor(cat.getColorIcon());
        icon.setColorFilter(colorIcon);
        title.setText(cat.getName());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDetail.dismiss();
            }
        });

        dialogDetail.show();

    }


    @Override
    public int getItemCount() {
        if (cats != null)
        {
            return cats.size();
        }
        return 0;
    }

    public  class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CardView cardBG;
        private AppCompatImageView imIcon;
        private TextView textTitle;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBG = itemView.findViewById(R.id.backGround);
            imIcon = itemView.findViewById(R.id.icon);
            textTitle = itemView.findViewById(R.id.title);
        }


    }
}
