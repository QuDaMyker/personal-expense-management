package com.learning.personal_expense_management.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.learning.personal_expense_management.R;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public ImageButton cashBtn;
    public ImageButton visaBtn;
    public ImageButton mastercardBtn;
    public ImageButton jcbBtn;
    public ImageButton momoBtn;
    public ImageButton shopeepayBtn;
    public ImageButton zalopayBtn;
    public ImageButton ewalletBtn;
    public ImageButton creditCardBtn;

    public static interface MyDialogListener
    {
        public void userSelectedAValue(String value);
        public void userCanceled();
    }

    MyDialogListener listener ;
    public MyDialogListener getListener() {
        return listener;
    }

    public void setListener(MyDialogListener listener) {
        this.listener = listener;
    }

    public CustomDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_account_type);

        cashBtn = findViewById(R.id.cash_btn);
        visaBtn = findViewById(R.id.visa_btn);
        mastercardBtn = findViewById(R.id.mastercard_btn);
        jcbBtn = findViewById(R.id.jcb_btn);
        momoBtn = findViewById(R.id.momo_btn);
        shopeepayBtn = findViewById(R.id.shopeepay_btn);
        zalopayBtn = findViewById(R.id.zalopay_btn);
        ewalletBtn = findViewById(R.id.ewallet_btn);
        creditCardBtn = findViewById(R.id.credit_card_btn);

        cashBtn.setOnClickListener(this);
        visaBtn.setOnClickListener(this);
        mastercardBtn.setOnClickListener(this);
        jcbBtn.setOnClickListener(this);
        momoBtn.setOnClickListener(this);
        shopeepayBtn.setOnClickListener(this);
        zalopayBtn.setOnClickListener(this);
        ewalletBtn.setOnClickListener(this);
        creditCardBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cash_btn:
                listener.userSelectedAValue("Tiền mặt");
                break;
            case R.id.visa_btn:
                listener.userSelectedAValue("Thẻ Visa");

                break;
            case R.id.mastercard_btn:
                listener.userSelectedAValue("Thẻ Mastercard");

                break;
            case R.id.jcb_btn:
                listener.userSelectedAValue("Thẻ JCB");

                break;
            case R.id.momo_btn:
                listener.userSelectedAValue("Ví điện tử MoMo");

                break;
            case R.id.shopeepay_btn:
                listener.userSelectedAValue("Ví ShopeePay");

                break;
            case R.id.zalopay_btn:
                listener.userSelectedAValue("Ví ZaloPay");

                break;
            case R.id.ewallet_btn:
                listener.userSelectedAValue("Ví điện tử khác");

                break;
            case R.id.credit_card_btn:
                listener.userSelectedAValue("Thẻ ngân hàng");

                break;
            default:
                break;
        }
        dismiss();
    }
}
