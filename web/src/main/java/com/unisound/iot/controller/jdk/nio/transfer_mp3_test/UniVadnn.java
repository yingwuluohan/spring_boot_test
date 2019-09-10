package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UniVadnn {

    private static String datPath = "";

    public UniVadnn() {
    }

    public static void init() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        String basePath = tmpDir + "/vadnn";
        File f = new File(basePath);
        if (!f.exists()) {
            f.mkdirs();
        }

        File datFile = new File(f.getAbsolutePath(), "vadnn.dat");
        copyFile("vadnn.dat", datFile);
        datPath = datFile.getAbsolutePath();
    }

    public static String getVadnnDatPath() {
        return datPath;
    }

    private static void copyFile(String path, File dest) {
        String resourceName = "/META-INF/" + path;

        try {
            copy(UniVadnn.class.getResourceAsStream(resourceName), dest.getAbsolutePath());
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }

    }

    private static boolean copy(InputStream source, String destination) {
        boolean success = true;

        try {
            Files.copy(source, Paths.get(destination), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        } catch (IOException var4) {
            success = false;
        }

        return success;
    }

    public static long uniVadInit() {
        return uniVadInit(datPath);
    }

    public static native long uniVadInit(String var0);

    public static native int uniVadSetOption(long var0, int var2, String var3);

    public static native int uniVadFree(long var0);

    public static native int uniVadProcess(long var0, byte[] var2, int var3);

    public static native int uniVadReset(long var0);

    static {
        try {
            NativeLoader.loadLibrary("vadnn");
        } catch (IOException var1) {
            var1.printStackTrace();
        }

        init();
    }
}
