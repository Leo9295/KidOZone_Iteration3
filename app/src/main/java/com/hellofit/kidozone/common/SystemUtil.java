package com.hellofit.kidozone.common;

/***
 *  This class include all common methods
 *  Can be update in the next iterations
 *  @author Mingzhe Liu
 *  @version 1.0
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.hellofit.kidozone.entity.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SystemUtil {

    /**
     * The name of SharedPreferences
     */
    private static final String SP_NAME = "sp_config";

    /**
     * To calculate the distance bewteen user and school
     *
     * @param userCoordinate
     * @param schoolCoordinate
     * @return result
     * @author Mingzhe Liu
     */
    public String distance4UserAndSchoolCal(Coordinate userCoordinate, Coordinate schoolCoordinate) {
        String result = "";

        double longAbs = Math.abs(userCoordinate.getLongtitude() - schoolCoordinate.getLongtitude());
        double latAbs = Math.abs(userCoordinate.getLatitude() - schoolCoordinate.getLatitude());

        double dis = Math.sqrt(Math.pow(longAbs, 2) + Math.pow(latAbs, 2)) * 0.030478;

        result = new java.text.DecimalFormat("#.00").format(dis);
        return result;
    }

    /**
     * To decode the json string from BASE64 to bitmap
     *
     * @param encodeStr
     * @return
     */
    public Bitmap decode(String encodeStr) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(encodeStr, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Save the object which is Serializable into the sharedPreferences
     *
     * @param context
     * @param object
     * @param stringName
     */
    public static void saveObject(Context context, Object object, String stringName) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            // write Serializable Entity into outputStream
            os.writeObject(object);
            // save as HexString
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            editor.putString(stringName, bytesToHexString);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the object which is Serializable from the sharedPreferences
     *
     * @param context
     * @param keyword
     * @return
     */
    public static Object readObject(Context context, String keyword) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences.contains(keyword)) {
                String string = sharedPreferences.getString(keyword, "");
                if (!(string.isEmpty() || string == null)) {
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    Object readObject = is.readObject();
                    return readObject;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHexString(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        if (byteArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(byteArray.length);
        String sTemp;
        for (int i = 0; i < byteArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & byteArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] StringToBytes(String string) {
        String hexString = string.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;
            char hex_char1 = hexString.charAt(i);
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16;
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i);
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48);
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55;
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;
        }
        return retData;
    }
}
