package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;

import java.io.File;
import java.io.IOException;

public class NativeLoader {
    private static JniExtractor jniExtractor = new DefaultJniExtractor();

    public NativeLoader() {
    }

    public static void loadLibrary(String libname) throws IOException {
        File lib = jniExtractor.extractJni(libname);
        System.load(lib.getAbsolutePath());
    }

    public static JniExtractor getJniExtractor() {
        return jniExtractor;
    }

    public static void setJniExtractor(JniExtractor jniExtractor) {
        jniExtractor = jniExtractor;
    }
}
