package com.unisound.iot.controller.jdk.nio.transfer_mp3_test;

import java.io.*;

public class DefaultJniExtractor implements JniExtractor {
    private static boolean debug = false;
    private File jniDir = null;

    public DefaultJniExtractor() {
    }

    public File getJniDir() throws IOException {
        if (this.jniDir == null) {
            this.jniDir = new File(System.getProperty("java.library.tmpdir", "tmplib"));
            if (debug) {
                System.err.println("Initialised JNI library working directory to '" + this.jniDir + "'");
            }
        }

        if (!this.jniDir.exists() && !this.jniDir.mkdirs()) {
            throw new IOException("Unable to create JNI library working directory " + this.jniDir);
        } else {
            return this.jniDir;
        }
    }

    public File extractJni(String libname) throws IOException {
        String mappedlib = System.mapLibraryName(libname);
        return this.extractResource("META-INF/lib/" + mappedlib, mappedlib);
    }

    File extractResource(String resourcename, String outputname) throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourcename);
        if (in == null) {
            throw new IOException("Unable to find library " + resourcename + " on classpath");
        } else {
            File outfile = new File(this.getJniDir(), outputname);
            if (debug) {
                System.err.println("Extracting '" + resourcename + "' to '" + outfile.getAbsolutePath() + "'");
            }

            OutputStream out = new FileOutputStream(outfile);
            copy(in, out);
            out.close();
            in.close();
            return outfile;
        }
    }

    static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] tmp = new byte[8192];
        boolean var3 = false;

        while(true) {
            int len = in.read(tmp);
            if (len <= 0) {
                return;
            }

            out.write(tmp, 0, len);
        }
    }

    static {
        String s = System.getProperty("java.library.debug");
        if (s != null && (s.toLowerCase().startsWith("y") || s.startsWith("1"))) {
            debug = true;
        }

    }
}
