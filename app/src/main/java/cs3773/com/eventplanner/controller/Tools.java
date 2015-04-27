package cs3773.com.eventplanner.controller;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    public static String easyBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.URL_SAFE);
    }

    public static String blobify(Serializable o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            bytes = bos.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null)
                    out.close();
                bos.close();
            } catch (IOException ioe) {
                return null;
            }
        }
        return Tools.easyBase64(bytes);
    }

    public static Object deblobify(String blob) {
        byte[] bytes = Base64.decode(blob, Base64.URL_SAFE);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Object o;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        } finally {
            try {
                bis.close();
                if (in != null)
                    in.close();
            } catch (IOException ioe) {
                return null;
            }
        }
        return o;
    }

}
