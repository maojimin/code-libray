/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年5月3日 下午1:10:17
 */
public class Ssh {
    private static final String hostname = "192.168.100.106";
    private static final String userid = "root";
    private static final String password = "yutian@hz";
    
    private static String keyBuf = "hogehogehoge";
    
    public static void main(String[] arg) throws Exception {
        Connection con = new Connection(hostname);
        con.connect();
        boolean ret = con.authenticateWithPassword(userid, password);
        if (!ret) {
            System.out.println("loggin error!");
        }
        Session session = con.openSession();
        session.requestDumbPTY();
        session.startShell();
        OutputStream os = session.getStdin();
        
        Thread t1 = new Std(session.getStdout());
        Thread t2 = new Std(session.getStderr());
        
        t1.start();
        t2.start();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            keyBuf = br.readLine();
            keyBuf = keyBuf += "\r";
            byte[] buf = keyBuf.getBytes();
            os.write(buf, 0, buf.length);
            if (keyBuf.equals("exit" + "\r")) {
                session.close();
                con.close();
                System.exit(0);
            }
        }
    }
    
    public static String getKeyBuf() {
        return keyBuf;
    }
}

class Std extends Thread {
    private InputStream is;
    private final byte[] buf1 = new byte[4096];
    private final byte[] buf2 = new byte[2048];
    
    public Std() {
    }
    
    public Std(InputStream is) {
        this.is = is;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Arrays.fill(buf1, (byte) 0x00);
                Arrays.fill(buf2, (byte) 0x00);
                int n = is.read(buf1);
                Thread.sleep(500);
                int len = is.read(buf2);
                System.arraycopy(buf2, 0, buf1, n, len);
                len += n;
                if (len > 0) {
                    String st = new String(buf1, "UTF-8");
                    st = st.replaceAll("\00", "");
                    System.out.print(st);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
