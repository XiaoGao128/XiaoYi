package com.example.xiaoyi_test_2.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipStringUtils {
        /**
       * @param input 需要压缩的字符串
       * @return 压缩后的字符串
       * @throws IOException IO
       */


    public static String compress(String input) throws IOException {
    if (input == null || input.length() == 0) {
    return input;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzipOs = new GZIPOutputStream(out);
    gzipOs.write(input.getBytes());
    gzipOs.close();
    return out.toString("ISO-8859-1");
    }
    /**
   * @param zippedStr 压缩后的字符串
   * @return 解压缩后的
   * @throws IOException IO
   */


    public static String uncompress(String zippedStr) throws IOException {
    if (zippedStr == null || zippedStr.length() == 0) {
    return zippedStr;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(zippedStr
                .getBytes("ISO-8859-1"));
    GZIPInputStream gzipIs = new GZIPInputStream(in);

    byte[] buffer = new byte[256];
    int n;
    while ((n = gzipIs.read(buffer)) >= 0) {
    out.write(buffer, 0, n);
    }
    // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
    return out.toString();
    }
}
