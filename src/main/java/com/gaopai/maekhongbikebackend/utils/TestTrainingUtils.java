package com.gaopai.maekhongbikebackend.utils;

import com.gaopai.maekhongbikebackend.exception.DataFormatException;

public class TestTrainingUtils {
    public static Boolean verifiedIsNullObject(Object object, String message) throws Exception {
        if (object != null) {
            return true;
        } else {
            throw new DataFormatException(message + " " + Constant.RESPONSE.MSG.DATA_NOT_FOUND);
        }
    }

}
