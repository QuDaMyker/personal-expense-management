package com.learning.personal_expense_management.utilities;

public class Enum {
    public enum AccountType {
        Cash {
            @Override
            public String AccountTypeName() {
                return "Tiền mặt";
            }
        },
        Visa {
            @Override
            public String AccountTypeName() {
                return "Thẻ Visa";
            }
        },
        Mastercard{
            @Override
            public String AccountTypeName() {
                return "Thẻ Mastercard";
            }
        },
        JCB{
            @Override
            public String AccountTypeName() {
                return "Thẻ JCB";
            }
        },
        MoMo{
            @Override
            public String AccountTypeName() {
                return "Ví điện tử MoMo";
            }
        },
        ShopeePay{
            @Override
            public String AccountTypeName() {
                return "Ví điện tử ShopeePay";
            }
        },
        ZaloPay{
            @Override
            public String AccountTypeName() {
                return "Ví điện tử ZaloPay";
            }
        },
        EWallet{
            @Override
            public String AccountTypeName() {
                return "Ví điện tử khác";
            }
        },
        ATM{
            @Override
            public String AccountTypeName() {
                return "Thẻ ngân hàng";
            }
        };
        public abstract String AccountTypeName();

    }

}
