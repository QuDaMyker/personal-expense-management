package com.learning.personal_expense_management.controller.loan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.AddAccountActivity;
import com.learning.personal_expense_management.databinding.ActivityLoanBinding;
import com.learning.personal_expense_management.databinding.ActivityNewLoanBinding;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.FirestoreCallback;
import com.learning.personal_expense_management.utilities.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class NewLoanActivity extends AppCompatActivity {

    private ActivityNewLoanBinding binding;
    private Loan inputLoan = new Loan();
    Map<Integer, String> periodMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inputLoan.setLend(false);
        inputLoan.setInterestRateType(true);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.typeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == binding.lendRadioBtn.getId()){
                    inputLoan.setLend(true);
                }
                else {
                    inputLoan.setLend(false);
                }
            }
        });

        binding.dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Chọn ngày giao dịch")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setNegativeButtonText("Hủy")
                        .setPositiveButtonText("Chọn");

                MaterialDatePicker datePicker = builder.build();
                datePicker.show(getSupportFragmentManager(), "DatePicker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((Long) selection);
                        int selectedMonth = calendar.get(Calendar.MONTH) + 1;

                        String formattedDate = calendar.get(Calendar.DATE) + "/"
                                + selectedMonth + "/"
                                + calendar.get(Calendar.YEAR);
                        binding.dateEdt.setText(formattedDate);

                        if(binding.deadlineLayout.getVisibility() == View.VISIBLE){
                            String date = binding.dateEdt.getText().toString();
                            int month = Utils.getKeyByValue(periodMap, binding.periodEdt.getText().toString());
                            try {
                                String deadline = calculateDeadline(date, month);
                                binding.deadlineTv.setText(deadline);
                                inputLoan.setDeadline(deadline);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        binding.timeEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                        .setTitleText("Chọn thời gian")
                        .setNegativeButtonText("Hủy")
                        .setPositiveButtonText("Chọn");

                MaterialTimePicker timePicker = builder.build();
                timePicker.show(getSupportFragmentManager(), "timePicker");

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();

                        String time = String.format("%02d:%02d", hour, minute);
                        binding.timeEdt.setText(time);
                    }
                });
            }
        });

        binding.periodEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.dateEdt.getText().toString().isEmpty()){
                    periodMap.put(1, "1 tháng");
                    periodMap.put(2, "2 tháng");
                    periodMap.put(3, "3 tháng");
                    periodMap.put(4, "4 tháng");
                    periodMap.put(5, "5 tháng");
                    periodMap.put(6, "6 tháng");
                    periodMap.put(7, "7 tháng");
                    periodMap.put(8, "8 tháng");
                    periodMap.put(9, "9 tháng");
                    periodMap.put(10, "10 tháng");
                    periodMap.put(11, "11 tháng");
                    periodMap.put(12, "1 năm");
                    periodMap.put(24, "2 năm");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewLoanActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(periodMap.values()));
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoanActivity.this);
                builder.setTitle("Thời hạn");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.periodEdt.setText(adapter.getItem(which));
                        binding.deadlineLayout.setVisibility(View.VISIBLE);
                        String date = binding.dateEdt.getText().toString();
                        int month = Utils.getKeyByValue(periodMap, adapter.getItem(which));
                        try {
                            String deadline = calculateDeadline(date, month);
                            binding.deadlineTv.setText(deadline);
                            inputLoan.setDeadline(deadline);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                builder.show();
            }
        });

        binding.interestRateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.interestRateLayout.setVisibility(View.VISIBLE);
                }
                else{
                    binding.interestRateLayout.setVisibility(View.GONE);
                }
                inputLoan.setHasInterestRate(b);
            }
        });

        binding.interestRateTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == binding.principalRadioBtn.getId()){
                    inputLoan.setInterestRateType(true);
                }
                else {
                    inputLoan.setInterestRateType(false);
                }
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputLoan.setBorrowerName(binding.borrowerNameEdt.getText().toString());
                inputLoan.setAmount(Integer.parseInt(binding.amountEdit.getText().toString()));
                inputLoan.setNote(binding.noteEdt.getText().toString());
                inputLoan.setTimestamp(
                        Utils.convertToTimestamp(binding.dateEdt.getText().toString(),
                                binding.timeEdt.getText().toString()));

                if(inputLoan.isHasInterestRate()){
                    inputLoan.setInterestRate(Integer.parseInt(binding.interestRateEdt.getText().toString()));
                }
                else{
                    inputLoan.setInterestRate(0);
                }
                inputLoan.setOwnerId(FirebaseAuth.getInstance().getUid());
                int month = Utils.getKeyByValue(periodMap, binding.periodEdt.getText().toString());
                inputLoan.setRepaymentPeriod(month);
                inputLoan.setPredictTransactions(new ArrayList<>());
                inputLoan.setReturnTransactions(new ArrayList<>());
                inputLoan.setInitialTransaction("");

                String res = FireStoreService.addLoan(inputLoan, new FirestoreCallback(){
                    @Override
                    public void onCallback(String result) {
                        if(result != "error"){
                            inputLoan.setId(result);
                            double interest = 0;
                            try {
                                interest = calculateInterest(inputLoan);

                                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

                                Date c = Calendar.getInstance().getTime();
                                String date = formatDate.format(c);
                                String time = formatTime.format(c);

                                Transaction newTransaction = new Transaction(
                                        FirebaseAuth.getInstance().getUid(),
                                        "idLater",
                                        inputLoan.isLend() ? 1 : 0,
                                        inputLoan.getAmount(),
                                        inputLoan.isLend() ? "Xuất tiền cho vay " + inputLoan.getBorrowerName() : "Nhận tiền vay " + inputLoan.getBorrowerName(),
                                        date,
                                        time,
                                        "",
                                        "",
                                        "YiLGeaX0u3NmfRAZcrea",
                                        new com.google.firebase.Timestamp(c),
                                        false
                                );
                                FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                                    @Override
                                    public void onCallback(String result) {
                                        if (!result.equals("error")) {
                                            FireStoreService.updateLoan(inputLoan.getId(), "initialTransaction", result);
                                        }
                                    }
                                });
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            FireStoreService.updateLoan(result, "interest", interest);
                        }
                    }
                });

                //add outcome/income transaction
                Toast.makeText(NewLoanActivity.this, "Thêm khoản vay thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    String calculateDeadline(String date, int month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date ngay = sdf.parse(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ngay);
        calendar.add(Calendar.MONTH, month);

        Date deadline = calendar.getTime();

        return sdf.format(deadline);
    }

    double calculateInterest(Loan loan) throws ParseException {
        int amount = loan.getAmount();
        int sumInterest = 0;
        List<String> predictTransactions = new ArrayList<>();
        if(loan.isInterestRateType()){
            int monthlyPrincipal = amount/loan.getRepaymentPeriod();
            for (int i = 1; i <= loan.getRepaymentPeriod(); i++) {
                double monthlyInterest = amount*loan.getInterestRate()/(100*loan.getRepaymentPeriod());
                double monthlyAmount = monthlyPrincipal + monthlyInterest;
                //add predict transaction
                String date  = calculateDeadline(Utils.convertTimestampToDateString(Long.parseLong(loan.getTimestamp())), i);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Transaction newTransaction = new Transaction(
                        FirebaseAuth.getInstance().getUid(),
                        "idLater",
                        inputLoan.isLend() ? 0 : 1,
                        (int) monthlyAmount,
                        loan.isLend() ? "Nhận lãi cho vay " + loan.getBorrowerName() : "Trả lãi vay " + loan.getBorrowerName(),
                        date,
                        "00:00",
                        "",
                        "",
                        "YiLGeaX0u3NmfRAZcrea",
                        new com.google.firebase.Timestamp(format.parse(date)),
                        true
                );
                FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                    @Override
                    public void onCallback(String result) {
                        if (!result.equals("error")) {
                            predictTransactions.add(result);
                            FireStoreService.updateLoan(loan.getId(), "predictTransactions", predictTransactions);
                        }
                    }
                });
                sumInterest += monthlyInterest;
            }
        }
        else {
            int monthlyPrincipal = amount/loan.getRepaymentPeriod();
            for (int i = 1; i <= loan.getRepaymentPeriod(); i++) {
                double monthlyInterest = amount*loan.getInterestRate()/(100*loan.getRepaymentPeriod());
                double monthlyAmount = monthlyPrincipal + monthlyInterest;
                amount -= monthlyPrincipal;
                //add predict transaction
                String date  = calculateDeadline(Utils.convertTimestampToDateString(Long.parseLong(loan.getTimestamp())), i);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Transaction newTransaction = new Transaction(
                        FirebaseAuth.getInstance().getUid(),
                        "idLater",
                        inputLoan.isLend() ? 0 : 1,
                        (int) monthlyAmount,
                        loan.isLend() ? "Nhận lãi cho vay " + loan.getBorrowerName()  : "Trả lãi vay " + loan.getBorrowerName(),
                        date,
                        "00:00",
                        "",
                        "",
                        "YiLGeaX0u3NmfRAZcrea",
                        new com.google.firebase.Timestamp(format.parse(date)),
                        true
                );
                FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                    @Override
                    public void onCallback(String result) {
                        if (!result.equals("error")) {
                            predictTransactions.add(result);
                            FireStoreService.updateLoan(loan.getId(), "predictTransactions", predictTransactions);
                        }
                    }
                });
                sumInterest += monthlyInterest;
            }
        }
        return sumInterest;
    }
}