package sdk.facecamera.sdk.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class StringUtil {
    public static String byte2String(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8").trim();
    }
    public static byte[] String2byte(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
