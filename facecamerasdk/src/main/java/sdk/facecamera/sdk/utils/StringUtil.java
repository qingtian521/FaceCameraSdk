package sdk.facecamera.sdk.utils;

import java.io.UnsupportedEncodingException;

public class StringUtil {
    public static String byte2String(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8").trim();
    }
    public static byte[] String2byte(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }
}
