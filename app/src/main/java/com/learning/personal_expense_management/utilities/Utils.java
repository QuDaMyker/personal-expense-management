package com.learning.personal_expense_management.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Utils {
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String convertToTimestamp(String date, String time) {
        String ngayGioString = date + " " + time;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        try {
            Date dateParsed = sdf.parse(ngayGioString);
            long timestamp = dateParsed.getTime();
            return String.valueOf(timestamp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
