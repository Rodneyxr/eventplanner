package cs3773.com.eventplanner.controller;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rodney on 4/4/2015.
 * <p/>
 * This class will contain several tools to be used throughout the project.
 */
public final class Tools {

    public static String sha256Base64(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] sha256Result = digest.digest(s.getBytes());
        return Base64.encodeToString(sha256Result, Base64.URL_SAFE).substring(0, 44); // BUG
    }

}
