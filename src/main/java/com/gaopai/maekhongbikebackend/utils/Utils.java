package com.gaopai.maekhongbikebackend.utils;

import com.gaopai.maekhongbikebackend.exception.DataFormatException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class Utils {
    private static SimpleDateFormat iso8601Formatter = getISO8601Formatter();
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    private static SimpleDateFormat getISO8601Formatter() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static boolean isBlank(String target) {
        boolean result = true;

        if (target != null && !"".equals(target)) {
            result = false;
        }

        return result;
    }

    public static boolean isNumber(String target) {
        boolean result = false;
        try {
            new Long(target);
            result = true;
        } catch (Exception e) {
            // error
        }
        return result;
    }

    public static int convertBahtToSubunit(int amount) {
        int value = 0;
        if (amount > 0)
            value =  amount * 100;

        return value;
    }

    public static boolean isDateFormat(String target, String pattern) {
        boolean result = false;
        try {
            DateUtil.convertStringToDate(target, pattern);
            result = true;
        } catch (Exception e) {
            // error
        }
        return result;
    }

    public static String isEmpty(String fieldName, String target) throws Exception {
        if (target != null && !"".equals(target)) {
            return target;
        }
        throw new Exception(fieldName + " is required field.");
    }

    public static boolean isEmptyParam(String fieldName, String target) throws Exception {
        if (target != null && !"".equals(target)) {
            return true;
        }
        throw new Exception(fieldName + " is required field.");
    }

    public static <T> boolean checkNotEmptyList(List<T> objects) {
        if (null != objects && objects.size() > 0) {
            return true;
        }
        return false;
    }

    public static String formatDateToISO86012004(Date date) {
        return iso8601Formatter.format(date);
    }

    public static boolean verifyAppToken(String appToken) throws Exception {
        boolean result = false;

        if (appToken.equals(Constant.APP_TOKEN)) {
            result = true;
        } else {
            throw new Exception("Invalid App Token.");
        }

        return result;
    }

    public static Boolean verifiedIsNullObject(Object object, String message) throws Exception {
        if (object != null) {
            return true;
        } else {
            throw new DataFormatException(message + " " + Constant.RESPONSE.MSG.DATA_NOT_FOUND);
        }
    }

    public static String randomStringByEmail(String email) throws NoSuchAlgorithmException {
        String temp = email + "|" + System.currentTimeMillis();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(temp.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString().toUpperCase();
    }

    public static Double calculatePercentAverageScore(Double score, Integer totalQuestion) {
        Double result = ((score * 100) / totalQuestion);
        String format = new DecimalFormat("0.00").format(result);
        return new Double(format);
    }
}
