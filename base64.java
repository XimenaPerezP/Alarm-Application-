package com.example.rick_hp.myapplication;

import android.util.Base64;

public class base64 {
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key,Base64.DEFAULT);
    }
}
