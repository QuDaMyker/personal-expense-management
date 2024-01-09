package com.learning.personal_expense_management.controller.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.OneCategoryListener;
import com.learning.personal_expense_management.services.TransactionListener;
import com.learning.personal_expense_management.utilities.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    private List<Transaction> lístTransactions;
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

    private TextView tvTotalIncomeMoney;
    private TextView tvTotalOutcomeMoney;

    private BarChart barChartToTal;
    private PieChart pieChartIncome;
    private PieChart pieChartOutcome;






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
        tvTotalIncomeMoney = view.findViewById(R.id.tvTotalIncomeMoney);
        tvTotalOutcomeMoney = view.findViewById(R.id.tvTotalOutcomeMoney);

        barChartToTal = view.findViewById(R.id.barChartToTal);
        pieChartIncome = view.findViewById(R.id.pieChartIncome);
        pieChartOutcome = view.findViewById(R.id.pieChartOutcome);

        lístTransactions  = new ArrayList<Transaction>();
        inCome  = new ArrayList<Transaction>();
        outCome  = new ArrayList<Transaction>();


        tabTime.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
//                    case 0:
//                        //tuàn
//                        getFistDateLastDate(0);
//                        getTransaction (firstDay, lastDay);
//                        break;
                    case 0:
                        //tháng
                        inComeNunm = 0;
                        outComeNum = 0;
                        lístTransactions.clear();
                        inCome.clear();;
                        outCome.clear();
                        getFistDateLastDate(1);
                        getTransactionMonth(firstDay, lastDay);


                        break;
                    case 1:
                        //năm
                        inComeNunm = 0;
                        outComeNum = 0;
                        lístTransactions.clear();
                        inCome.clear();;
                        outCome.clear();
                        getFistDateLastDate(2);
                        getTransactionYear(firstDay, lastDay);

                        ArrayList<Entry> yvalues = new ArrayList<>();
                        yvalues.add(new Entry(25f, 0));
                        yvalues.add(new Entry(50f, 1));
                        yvalues.add(new Entry(75f, 2));



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
    private void getTransactionMonth(Date fist, Date last)
    {
        Log.d("test", "Bắt đầu lấy transaction");
        FireStoreService.getTranSactionInMoth(currentUser, fist, new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                lístTransactions= transactions;
                for (Transaction tran : lístTransactions)
                {
                    if(tran.getTransactionType() == 0 )
                    {
                        inComeNunm+= tran.getAmount();
                        inCome.add(tran);

                    } else if (tran.getTransactionType() == 1) {
                        outComeNum += tran.getAmount();
                        outCome.add(tran);
                    }
                }
                //vẽ bieieur đồ
                //setupBarChartMonth(fist);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("test", "k lấy được");

            }
        });

    }
    private void getTransactionYear(Date fist, Date last)
    {
        FireStoreService.getTranSactionInYear(currentUser, fist, new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                lístTransactions= transactions;
                for (Transaction tran : lístTransactions)
                {
                    if(tran.getTransactionType() == 0 )
                    {
                        inComeNunm+= tran.getAmount();
                        inCome.add(tran);

                    } else if (tran.getTransactionType() == 1) {
                        outComeNum += tran.getAmount();
                        outCome.add(tran);
                    }
                }
                Log.e("lengthOutYearCbi", outCome.size()+"");
                Log.e("lengthInYearCbi", inCome.size()+"");
                //set số liệu vào các textView
                tvIncomeNum.setText("+" +formatNumberWithDot(inComeNunm) + "d");
                tvOutcomeNum.setText("-"+formatNumberWithDot(outComeNum) + "d");

                tvTotalIncomeMoney.setText(formatNumberWithDot(inComeNunm) + " d");
                tvTotalOutcomeMoney.setText(formatNumberWithDot(outComeNum) + " d");

                setupBarChartYear();
                setUpPieChartInCome(inCome);
                setUpPieChartOutCome(outCome);

            }

            @Override
            public void onError(String errorMessage) {

            }
        });


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

    private void setupBarChartMonth(Date date)
    {
        Log.e("lengthOutMonth", outCome.size()+"");
        Log.e("lengthInMonth", inCome.size()+"");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Lấy số ngày trong tháng => limit
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Xử lý dữ liệu để tạo dữ liệu cho biểu đồ
        List<BarEntry> outcomeEntries = processTransactionsForMonth(outCome,daysInMonth);
        List<BarEntry> incomeEntries = processTransactionsForMonth(inCome,daysInMonth);

        // Tạo BarDataSet cho chi tiêu và thu nhập
        BarDataSet expenseDataSet = new BarDataSet(outcomeEntries, "Chi Tiêu");
        int primary40Color = ContextCompat.getColor(getContext(), R.color.primary40);
        expenseDataSet.setColor(primary40Color);

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Thu Nhập");
        int green = ContextCompat.getColor(getContext(), R.color.green);
        incomeDataSet.setColor(green);

        // Tạo BarData và thiết lập nó cho biểu đồ
        BarData barData = new BarData(expenseDataSet,incomeDataSet);
        barData.setValueTextSize(11f);
        barData.setBarWidth(1f);

        XAxis xAxis = barChartToTal.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChartToTal.setDrawBarShadow(false);
        barChartToTal.setDrawGridBackground(false);
        barChartToTal.getDescription().setEnabled(false);
        barChartToTal.groupBars(0.5f, 1f, 0f);
        barChartToTal.setDragEnabled(true);
        barChartToTal.setData(barData);

        barChartToTal.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String text = String.valueOf(value) + "k";
                return text;
            }
        });
        barChartToTal.invalidate();

    }
    private void setupBarChartYear()
    {
        Log.e("lengthOutYear", outCome.size()+"");
        Log.e("lengthInYear", inCome.size()+"");

         //Xử lý dữ liệu để tạo dữ liệu cho biểu đồ
        List<BarEntry> outcomeEntries = processTransactionsForYear(outCome);
        List<BarEntry> incomeEntries = processTransactionsForYear(inCome);

        // Tạo BarDataSet cho chi tiêu và thu nhập
        BarDataSet expenseDataSet = new BarDataSet(outcomeEntries, "Chi Tiêu");
        int primary40Color = ContextCompat.getColor(getContext(), R.color.primary40);
        expenseDataSet.setColor(primary40Color);

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Thu Nhập");
        int green = ContextCompat.getColor(getContext(), R.color.green);
        incomeDataSet.setColor(green);

        // Tạo BarData và thiết lập nó cho biểu đồ
        BarData barData = new BarData(expenseDataSet,incomeDataSet);
        barData.setValueTextSize(11f);
        barData.setBarWidth(0.3f);

        XAxis xAxis = barChartToTal.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(0.5f);

        barChartToTal.setDrawBarShadow(false);
        barChartToTal.setDrawGridBackground(false);
        barChartToTal.getDescription().setEnabled(false);

        barChartToTal.setData(barData);
        barChartToTal.groupBars(0.5f, 1f, 0f);
        barChartToTal.setDragEnabled(true);
        barChartToTal.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String value1 = String.valueOf(value) + "K";
                return value1;
            }
        });
        barChartToTal.getAxisRight().setDrawLabels(false);
        barChartToTal.invalidate();
    }

    //xử lý dweux liệu -> từ danh sách trans đưa ra tổng cho 12 tháng
    private List<BarEntry> processTransactionsForYear(List<Transaction> transactions) {
        Log.e("lengh", "processTransactions: " + transactions.size() );
        List<BarEntry> entries = new ArrayList<>();
        float[] monthlySums = new float[12];
        for (int i = 0; i < monthlySums.length; i++) {
            monthlySums[i] = 0f;
        }

        for (Transaction transaction : transactions) {
            int month = Integer.parseInt(transaction.getMonth());
            int index = month -1;
            monthlySums[index] += transaction.getAmount();
        }

        List<BarEntry> barEntryList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int index = (int) (i+1);
            int value = (int)monthlySums[i]/1000;
            barEntryList.add(new BarEntry(index, value));
        }
        Log.d("TAG", "processTransactions: " + barEntryList);

        return barEntryList;
    }
    private List<BarEntry> processTransactionsForMonth(List<Transaction> transactions, int limit) {
        Log.e("lengh", "processTransactions: " + transactions.size() );
        List<BarEntry> entries = new ArrayList<>();
        float[] monthlySums = new float[limit];
        for (int i = 0; i < monthlySums.length; i++) {
            monthlySums[i] = 0f;
        }

        for (Transaction transaction : transactions) {
            Date date = new Date();
            Timestamp timestamp = transaction.getTimeStamp();
            date = timestamp.toDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            monthlySums[day] += transaction.getAmount();
        }

        List<BarEntry> barEntryList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            int index = (int) (i+1);
            int value = (int)(monthlySums[i]/1000);
            barEntryList.add(new BarEntry(index, value));
        }
        Log.d("TAG", "processTransactions: " + barEntryList);

        return barEntryList;
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

    private void setUpPieChartInCome(List<Transaction> transactions)
    {
        Map<String, Float> categoryTotals = new HashMap<>();

        // Tính tổng của từng danh mục từ danh sách giao dịch
        for (Transaction transaction : transactions) {
            String categoryId = transaction.getCategoryId();
            float amount = transaction.getAmount();
            // Kiểm tra xem danh mục đã tồn tại trong Map hay chưa
            if (categoryTotals.containsKey(categoryId)) {
                // Nếu đã tồn tại, cộng thêm số tiền vào tổng
                float currentTotal = categoryTotals.get(categoryId);
                categoryTotals.put(categoryId, currentTotal + amount);
            } else {
                // Nếu chưa tồn tại, thêm danh mục mới vào Map với số tiền là số tiền của giao dịch đó
                categoryTotals.put(categoryId, amount);
            }
        }
        // In tổng của từng danh mục
        for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
            Log.e("Danh mục", entry.getKey() + ", Tổng: " + entry.getValue());
        }

        //lấy thông tin danh mục : Danh mục : tiền
        HashMap<Category, Float> listCat = new HashMap<>();
        for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
            FireStoreService.getOneCategory(entry.getKey(), new OneCategoryListener() {
                @Override
                public void getCategory(Category category) {
                    listCat.put(category, entry.getValue());
                }
            });
        }
        //set dữ liệu vẽ hình tròn
        double total = 0.0;
        for (Float value : categoryTotals.values()) {
            total += value;
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
            float percentage = (float) (entry.getValue() / total) * 100;
            entries.add(new PieEntry(percentage, entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Chi tiêu");
        Log.d("TAG", "setUpPieChartInCome: " + dataSet );
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChartIncome));
        pieData.setValueTextSize(12f);

        pieChartIncome.setData(pieData);
        pieChartIncome.setUsePercentValues(true);
        pieChartIncome.getDescription().setEnabled(false);
        pieChartIncome.invalidate();
    }
    private void setUpPieChartOutCome(List<Transaction> transactions)
    {
        Map<String, Float> categoryTotals = new HashMap<>();

        // Tính tổng của từng danh mục từ danh sách giao dịch
        for (Transaction transaction : transactions) {
            String categoryId = transaction.getCategoryId();
            float amount = transaction.getAmount();
            // Kiểm tra xem danh mục đã tồn tại trong Map hay chưa
            if (categoryTotals.containsKey(categoryId)) {
                // Nếu đã tồn tại, cộng thêm số tiền vào tổng
                float currentTotal = categoryTotals.get(categoryId);
                categoryTotals.put(categoryId, currentTotal + amount);
            } else {
                // Nếu chưa tồn tại, thêm danh mục mới vào Map với số tiền là số tiền của giao dịch đó
                categoryTotals.put(categoryId, amount);
            }
        }
        // In tổng của từng danh mục
        for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
            Log.e("Danh mục Out", entry.getKey() + ", Tổng: " + entry.getValue());
        }
    }




}