package com.learning.personal_expense_management.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.TransactionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private String currentUser;

    private List<Transaction> LístTransactions;
    private List<Transaction> inCome;
    private List<Transaction> outCome;




    private int inComeNunm = 0 ;
    private int outComeNum =0;

    private Date firstDay ;
    private Date lastDay ;

    private TabLayout tabTime;

    private TextView tvBalanceMoney;
    private TextView tvIncomeNum;
    private TextView tvOutcomeNum;
    private BarChart barChartToTal;





    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
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
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        currentUser = mAuth.getInstance().getUid();
        firstDay = new Date();
        lastDay = new Date();

        tabTime = view.findViewById(R.id.tlFilterTime);
        tvBalanceMoney = view.findViewById(R.id.tvBalanceMoney);
        tvIncomeNum = view.findViewById(R.id.tvIncomeNum);
        tvOutcomeNum = view.findViewById(R.id.tvOutcomeNum);

        barChartToTal = view.findViewById(R.id.barChartToTal);

        LístTransactions  = new ArrayList<Transaction>();
        inCome  = new ArrayList<Transaction>();
        outCome  = new ArrayList<Transaction>();


        tabTime.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        //tuàn
                        getFistDateLastDate(0);
                        getTransaction (firstDay, lastDay);
                        break;
                    case 1:
                        //tháng
                        getFistDateLastDate(1);

                        break;
                    case 2:
                        //năm
                        getFistDateLastDate(2);

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }
    private void getFistDateLastDate(int a){
        switch (a){
            case 0:
                // Lấy ngày hiện tại
                Calendar firstDate0 = Calendar.getInstance();
                Calendar lastDate0 = Calendar.getInstance();


                // Lấy ngày đầu tiên trong tuần
                firstDate0.set(Calendar.DAY_OF_WEEK, firstDate0.getFirstDayOfWeek());
                resetTimeToMidnight(firstDate0);

                lastDate0.set(Calendar.DAY_OF_WEEK, lastDate0.getFirstDayOfWeek()+6);
                resetTimeToEndDay(lastDate0);

                firstDay = firstDate0.getTime() ;
                lastDay = lastDate0.getTime();
                Log.d("test", "first: " + firstDay);
                Log.d("test", "last: " + lastDay);


                break;
            case 1:
                Calendar firstDate1 = Calendar.getInstance();
                Calendar lastDate1 = Calendar.getInstance();
                // Lấy ngày đầu tiên trong tháng
                firstDate1.set(Calendar.DAY_OF_MONTH, 1);
                resetTimeToMidnight(firstDate1);

                lastDate1.set(Calendar.DAY_OF_MONTH, lastDate1.getActualMaximum(Calendar.DAY_OF_MONTH));
                resetTimeToEndDay(lastDate1);

                firstDay = firstDate1.getTime() ;
                lastDay = lastDate1.getTime();
                Log.d("test", "first: " + firstDay);
                Log.d("test", "last: " + lastDay);

                break;
            case 2:
                Calendar firstDate2 = Calendar.getInstance();
                Calendar lastDate2 = Calendar.getInstance();
                // Lấy ngày đầu tiên trong tháng
                firstDate2.set(Calendar.DAY_OF_YEAR, 1);
                resetTimeToMidnight(firstDate2);

                lastDate2.set(Calendar.DAY_OF_YEAR, lastDate2.getActualMaximum(Calendar.DAY_OF_YEAR));
                resetTimeToEndDay(lastDate2);

                firstDay = firstDate2.getTime() ;
                lastDay = lastDate2.getTime();
                Log.d("test", "first: " + firstDay);
                Log.d("test", "last: " + lastDay);
                break;
            default:
                break;


        }

    }
    private void getTransaction(Date fist, Date last)
    {
        FireStoreService.getTransactionForTimePeriod(currentUser, fist, last, new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                LístTransactions.clear();;
                inCome.clear();;
                outCome.clear();
                LístTransactions = transactions;
                for (Transaction transaction : LístTransactions) {
                    if (transaction.getTransactionType() == 0) {
                        inCome.add(transaction);
                    } else {
                        if (transaction.getTransactionType() == 1) {
                            outCome.add(transaction);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }



    private static Date parseDateString(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void resetTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    private static void resetTimeToEndDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

}