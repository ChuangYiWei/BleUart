package com.example.johnny_wei.bleuart.driver_pl2303;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build.VERSION;

import com.example.johnny_wei.bleuart.R;
import com.example.johnny_wei.bleuart.common.DBG;
import com.example.johnny_wei.bleuart.common.pConvertType;
import com.example.johnny_wei.bleuart.common.pString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PL2303Driver {
    //version ==========================================================
    private static final String versionx="2016/12/26 21:30:27";
    //DBG ==============================================================
    private final String TAG =getClass().getSimpleName();
    public final static  String TAG_CLASS =
            "com.baboo.android.uart";

    private DBG dbgMsg;
    //String ===========================================================
    //String[] =========================================================
    //int ==============================================================
    //can't use byte,otherwise highest sign bit may cause if check fail
    int[] dbgEnArr={0,0,0,0};//only use [7:0]
    //--------------------0:dbgMsg
    //--------------------1:class function
    static final int _DBG_01_01_open_                   =0x01;//
    static final int _DBG_01_02_rx_                     =0x02;//
    static final int _DBG_01_04_tx_                     =0x04;//
    static final int _DBG_01_08_                        =0x08;//
    static final int _DBG_01_10_                        =0x10;//
    static final int _DBG_01_20_                        =0x20;//
    static final int _DBG_01_40_                        =0x40;//
    static final int _DBG_01_80_                        =0x80;//
    //--------------------2:
    static final int _DBG_02_01_                        =0x01;//
    static final int _DBG_02_02_State_                  =0x02;//
    static final int _DBG_02_04_                        =0x04;//
    static final int _DBG_02_08_                        =0x08;//
    static final int _DBG_02_10_                        =0x10;//
    static final int _DBG_02_20_                        =0x20;//
    static final int _DBG_02_40_                        =0x40;//
    static final int _DBG_02_80_                        =0x80;//
    //--------------------3:
    static final int _DBG_03_01_                        =0x01;//
    static final int _DBG_03_02_                        =0x02;//
    static final int _DBG_03_04_                        =0x04;//
    static final int _DBG_03_08_initClass_              =0x08;//
    static final int _DBG_03_10_                        =0x10;//
    static final int _DBG_03_20_                        =0x20;//
    static final int _DBG_03_40_                        =0x40;//
    static final int _DBG_03_80_                        =0x80;//
    //-----------------------------------

    //==================================================================
    private pConvertType ct;
    private pString sp;
//    private Map<String, String> mapUart = new HashMap<String, String>();
    //==============================================================================================

    private boolean e = false;
    private static final boolean f = false;
    private static final boolean g = false;
    private static final boolean h = false;
    private static String i = "2.0.12.31";
    public static final int BAUD0 = 0;
    public static final int BAUD75 = 75;
    public static final int BAUD150 = 150;
    public static final int BAUD300 = 300;
    public static final int BAUD600 = 600;
    public static final int BAUD1200 = 1200;
    public static final int BAUD1800 = 1800;
    public static final int BAUD2400 = 2400;
    public static final int BAUD4800 = 4800;
    public static final int BAUD9600 = 9600;
    public static final int BAUD14400 = 14400;
    public static final int BAUD19200 = 19200;
    public static final int BAUD38400 = 38400;
    public static final int BAUD57600 = 57600;
    public static final int BAUD115200 = 115200;
    public static final int BAUD230400 = 230400;
    public static final int BAUD460800 = 460800;
    public static final int BAUD614400 = 614400;
    public static final int BAUD921600 = 921600;
    public static final int BAUD1228800 = 1228800;
    public static final int BAUD2000000 = 2000000;
    public static final int BAUD2457600 = 2457600;
    public static final int BAUD3000000 = 3000000;
    public static final int BAUD6000000 = 6000000;
    public static final int BAUD12000000 = 12000000;
    public static final int PL2303HXD_DCD_ON = 1;
    public static final int PL2303HXD_DSR_ON = 2;
    public static final int PL2303HXD_RI_ON = 8;
    public static final int PL2303HXD_CTS_ON = 128;
    private byte[] j = new byte[7];
    static final int a = 11;
    static final int b = 12;
    private static final int k = 33;
    private static final int l = 32;
    private static final int m = 33;
    private static final int n = 35;
    private static final int o = 0;
    private static final int p = 161;
    private static final int q = 33;
    private static final int r = 64;
    private static final int s = 1;
    private static final int t = 192;
    private static final int u = 1;
    private static final int v = 33;
    private static final int w = 34;
    private static final int x = 0;
    private static final int y = 1;
    private static final int z = 2;
    private static final int A = 2056;
    private static final int B = 2313;
    private int C = 0;
    private byte D = 0;
    private static final int E = 1;
    private static final int F = 2;
    private static final int G = 32;
    public static final int PL_MAX_INTERFACE_NUM = 4;
    private final int H = 64;
    private UsbManager I;
    private UsbDevice J;
    private UsbDeviceConnection K;
    private UsbInterface L;
    private UsbEndpoint M;
    private UsbEndpoint N;
    private UsbEndpoint O;
    public static final int READBUF_SIZE = 4096;
    public static final int WRITEBUF_SIZE = 4096;
    private int P;
    private int Q;
    byte[] c = new byte[4096];
    private int R;
    private int S;
    private int T;
    private ArrayBlockingQueue<Integer> U = new ArrayBlockingQueue(4096, true);
    public static Object ReadQueueLock = new Object();
    private PL2303Driver.a V;
    private boolean W;
    private int X = 0;
    private int Y = 0;
    private boolean Z = false;
    private boolean aa = false;
    private String ab;
    private ArrayList<String> ac = new ArrayList();
    private int ad;
    Context d;
    private int ae = 0;
    private final int af = 2;
    private final int ag = 3;
    private final int ah = 4;
    private final int ai = 5;
    private final int aj = 6;
    private final int ak = 7;
    private final int al = 8;
    private final int am = 9;
    private boolean an;
    private boolean ao;
    private boolean ap;
    private PL2303Driver.FlowControl aq;
    private boolean ar;
    private final boolean as = true;
    private final boolean at = false;
    private final int au = 17;
    private final int av = 19;
    public final String PLUART_MESSAGE = "tw.PL2303USBMessage";
    public final String PLUART_DETACHED = "USB.Detached";
    public final int PLDETACHED_VALUE = 255;
    private int aw;
    private int ax;
    private int ay;
    private int az;
    private int aA;
    private int aB;
    private int aC;
    private int aD;
    private boolean aE = false;
    private final BroadcastReceiver aF = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String var3 = intent.getAction();
            UsbDevice var4 = (UsbDevice)intent.getParcelableExtra("device");
            if(!"android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(var3)) {
                if("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(var3)) {
                    String var5 = var4.getDeviceName();
                    if(PL2303Driver.this.J != null && PL2303Driver.this.J.equals(var5)) {
                        Intent var6 = new Intent("tw.PL2303USBMessage");
                        var6.putExtra("USB.Detached", String.valueOf(255));
                        PL2303Driver.this.d.sendBroadcast(var6);
                        PL2303Driver.this.end();
                    }
                } else if(var3.equals(PL2303Driver.this.ab)) {
                    synchronized(this) {
                        if(intent.getBooleanExtra("permission", false) && var4 != null) {
                            for(int var8 = 0; var8 < PL2303Driver.this.ad; ++var8) {
                                if(String.format("%04X:%04X", new Object[]{Integer.valueOf(var4.getVendorId()), Integer.valueOf(var4.getProductId())}).equals(PL2303Driver.this.ac.get(var8))) {
                                    PL2303Driver.this.b(var4);
                                    return;
                                }
                            }
                        }
                    }
                }
            }

        }
    };
    public static UsbDevice sDevice = null;
    private Runnable aG = new Runnable() {
        public void run() {
            UsbDevice var1 = PL2303Driver.sDevice;
            if(!PL2303Driver.this.isConnected()) {
                PL2303Driver.this.a(var1);
                PL2303Driver.this.an = true;
            }

        }
    };

    private void a(UsbManager var1, Context var2, String var3, boolean var4) {
        this.I = var1;
        this.J = null;
        this.M = null;
        this.N = null;
        this.O = null;
        this.P = 0;
        this.Q = 0;
        this.an = false;
        this.W = false;
        this.ao = false;
        this.d = var2;
        this.ap = var4;
        this.ab = var3;
        this.ar = true;
        this.aq = PL2303Driver.FlowControl.OFF;
        this.b("067B:2303");
        this.b("067B:2304");
        this.b("067B:2551");
        this.b("067B:2503");
        this.b("067B:A100");
        this.b("067B:AAA5");
        this.b("05AD:0FBA");
        this.ad = this.ac.size();
        this.aw = 0;
        this.ax = 15;
        this.ay = 3;
        this.az = 0;
        this.aA = 0;
        this.aB = 0;
        this.aC = 0;
        this.aD = 0;
        this.R = 100;
        this.S = 100;
        this.T = 100;
    }

    public PL2303Driver(UsbManager manager, Context mContext, String sAppName) {
        this.a(manager, mContext, sAppName, true);
//        initClass();
    }

    public PL2303Driver(UsbManager manager, Context mContext, String sAppName, boolean bWithQueue) {
        this.a(manager, mContext, sAppName, bWithQueue);
//        initClass();
    }

    //==============================================================================================
    void initClass(){
        if(dbgMsg==null)
        {
            dbgMsg = new DBG(com.example.johnny_wei.bleuart.R.integer.PL2303Driver_dbg);//first need send context

            ct=new pConvertType();
            sp=new pString();

//dbgMsg.save(TAG + ".dbgIni" + "API_Level=" + ct.int2String(android.os.Build.VERSION.SDK_INT));
        }
    }
    //==============================================================================================

    private void a(UsbDevice var1) {
        int var2 = 0;
        if(this.K != null) {
            if(this.L != null) {
                this.K.releaseInterface(this.L);
                this.L = null;
            }

            this.K.close();
            this.J = null;
            this.K = null;
        }

        if(var1 != null) {
            for(int var3 = 0; var3 < var1.getInterfaceCount(); ++var3) {
                UsbInterface var4 = var1.getInterface(var3);
                if(255 == var4.getInterfaceClass() && var4.getInterfaceProtocol() == 0 && var4.getInterfaceSubclass() == 0) {
                    var2 = var3;
                    break;
                }
            }

            UsbInterface var5 = var1.getInterface(var2);
            if(var1 != null && var5 != null) {
                UsbDeviceConnection var6 = this.I.openDevice(var1);
                if(var6 != null) {
                    if(var6.claimInterface(var5, true)) {
                        this.J = var1;
                        this.K = var6;
                        this.L = var5;
                        if(this.a(this.L)) {
                            return;
                        }
                    } else {
                        var6.close();
                    }
                }
            }

        }
    }

    boolean a(String var1) {
        String var2 = "";
        boolean var4 = true;
        if(VERSION.SDK_INT >= 21) {
            return true;
        } else {
            try {
                String var3 = "toolbox ls " + var1;
                Process var5 = Runtime.getRuntime().exec(var3);
                BufferedReader var6 = new BufferedReader(new InputStreamReader(var5.getInputStream()));

                for(String var7 = null; (var7 = var6.readLine()) != null; var2 = var2 + var7) {
                    ;
                }

                if(var1.compareTo(var2) != 0) {
                    var4 = false;
                }
            } catch (IOException var8) {
                var8.printStackTrace();
                var4 = false;
            }

            return var4;
        }
    }

    private boolean b(String var1) {
        this.ac.add(var1);
        this.ad = this.ac.size();
        return true;
    }

    public boolean enumerate() {
        this.I = (UsbManager)this.d.getSystemService("usb");
        HashMap var1 = this.I.getDeviceList();
        Iterator var2 = var1.values().iterator();
        PendingIntent var3 = PendingIntent.getBroadcast(this.d, 0, new Intent(this.ab), 0);

        while(var2.hasNext()) {
            UsbDevice var4 = (UsbDevice)var2.next();

            for(int var5 = 0; var5 < this.ad; ++var5) {
                if(String.format("%04X:%04X", new Object[]{Integer.valueOf(var4.getVendorId()), Integer.valueOf(var4.getProductId())}).equals(this.ac.get(var5)) && this.a(var4.getDeviceName())) {
                    IntentFilter var6 = new IntentFilter(this.ab);
                    var6.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
                    this.d.registerReceiver(this.aF, var6);
                    if(this.I.hasPermission(var4)) {
                        this.b(var4);
                        if(String.format("%04X:%04X", new Object[]{Integer.valueOf(var4.getVendorId()), Integer.valueOf(var4.getProductId())}).equals("067B:2551") || String.format("%04X:%04X", new Object[]{Integer.valueOf(var4.getVendorId()), Integer.valueOf(var4.getProductId())}).equals("067B:2503") || String.format("%04X:%04X", new Object[]{Integer.valueOf(var4.getVendorId()), Integer.valueOf(var4.getProductId())}).equals("067B:A100")) {
                            this.ao = true;
                        }

                        return true;
                    }

                    this.I.requestPermission(var4, var3);
                }
            }
        }

        return false;
    }

    private void b(UsbDevice var1) {
        sDevice = var1;
        (new Thread(this.aG)).start();
    }

    private boolean f() {
        if(!this.an) {
            return false;
        } else if(this.J == null) {
            return false;
        } else {
            int var1 = this.a(this.K);
            if(var1 < 0) {
                return false;
            } else if(this.ae != 4 && this.ae != 6) {
                return false;
            } else {
                if(this.ap) {
                    this.V = new PL2303Driver.a();
                } else {
                    this.V = null;
                }

                return true;
            }
        }
    }

//    public boolean InitByDefualtValue() {
//        if(!this.f()) {
//            return false;
//        } else {
//            if(this.ap) {
//                this.g();
//            }
//
//            return true;
//        }
//    }

//    public boolean InitByBaudRate(PL2303Driver.BaudRate R) {
//        if(!this.f()) {
//            return false;
//        } else {
//            int var2 = 0;
//
//            try {
//                var2 = this.setup(R, PL2303Driver.DataBits.D8, PL2303Driver.StopBits.S1, PL2303Driver.Parity.NONE, PL2303Driver.FlowControl.OFF);
//            } catch (IOException var4) {
//                var4.printStackTrace();
//            }
//
//            if(var2 < 0) {
//                return false;
//            } else {
//                if(this.ap) {
//                    this.g();
//                }
//
//                return true;
//            }
//        }
//    }

//    public boolean InitByBaudRate(PL2303Driver.BaudRate R, int TimeoutConstant) {
//        if(!this.f()) {
//            return false;
//        } else {
//            int var3 = 0;
//
//            try {
//                var3 = this.setup(R, PL2303Driver.DataBits.D8, PL2303Driver.StopBits.S1, PL2303Driver.Parity.NONE, PL2303Driver.FlowControl.OFF);
//            } catch (IOException var5) {
//                var5.printStackTrace();
//            }
//
//            if(var3 < 0) {
//                return false;
//            } else if(!this.PL2303Device_SetCommTimeouts(TimeoutConstant)) {
//                return false;
//            } else {
//                if(this.ap) {
//                    this.g();
//                }
//
//                return true;
//            }
//        }
//    }

    //    public boolean InitByPortSetting(PL2303Driver.BaudRate R, PL2303Driver.DataBits D, PL2303Driver.StopBits S, PL2303Driver.Parity P, PL2303Driver.FlowControl F) {
//        if(!this.f()) {
//            return false;
//        } else {
//            int var6 = 0;
//
//            try {
//                var6 = this.setup(R, D, S, P, F);
//            } catch (IOException var8) {
//                var8.printStackTrace();
//            }
//
//            if(var6 < 0) {
//                return false;
//            } else {
//                if(this.ap) {
//                    this.g();
//                }
//
//                return true;
//            }
//        }
//    }
    public int InitByPortSetting(int bardrate, PL2303Driver.DataBits D, PL2303Driver.StopBits S, PL2303Driver.Parity P, PL2303Driver.FlowControl F) {
        int result=-1;

        if(!this.f()) {
            result=-2;
        } else {
            int var6 = 0;

            try {
                var6 = this.setup(bardrate, D, S, P, F);

                //dbgMsg.save(TAG + ".InitByPortSetting.var6="+ct.int2String(var6));

            } catch (IOException var8) {
//                dbgMsg.save(TAG + ".InitByPortSetting.IOException");
                //var8.printStackTrace();
            }

            if(var6 < 0) {
                result=-3;
            } else {
                if(this.ap) {
                    this.g();
                }

                result=0;
            }
        }

        return result;
    }

    public void end() {
        if(this.J != null) {
            if(this.ap) {
                this.h();
            }

            this.ao = false;
            this.d.unregisterReceiver(this.aF);
            this.a((UsbDevice)null);
        }

    }

    public boolean isConnected() {
        return this.J != null && this.M != null && this.N != null;
    }

    private boolean a(UsbInterface var1) {
        if(var1 == null) {
            return false;
        } else {
            for(int var2 = 0; var2 < var1.getEndpointCount(); ++var2) {
                if(var1.getEndpoint(var2).getType() == 2) {
                    if(var1.getEndpoint(var2).getDirection() == 128) {
                        this.M = var1.getEndpoint(var2);
                    } else {
                        this.N = var1.getEndpoint(var2);
                    }
                } else if(var1.getEndpoint(var2).getType() == 3 && var1.getEndpoint(var2).getDirection() == 128) {
                    this.O = var1.getEndpoint(var2);
                }
            }

            return true;
        }
    }

    private void g() {
        if(!this.W) {
            this.V.start();
            this.W = this.V.isAlive();
        }

    }

    private void h() {
        if(this.W && this.V != null) {
            this.V.b();
            this.W = this.V.isAlive();
            this.V = null;
        }

    }

    //    private void a(PL2303Driver.BaudRate var1) {
//        int[] var2 = new int[]{3, 5, 10, 25, 100, 200};
//        int var3 = var2[3];
//        //a()[]
//        switch(var1) {
//        case B0:
//            var3 = 10000;
//            break;
//        case B75:
//        case B150:
//            var3 = var2[5];
//            break;
//        case B300:
//        case B600:
//            var3 = var2[4];
//            break;
//        case B1200:
//        case B1800:
//        case B2400:
//        case B4800:
//        case B9600:
//            var3 = var2[3];
//            break;
//        case B14400:
//        case B19200:
//        case B38400:
//        case B57600:
//            var3 = var2[2];
//            break;
//        case B115200:
//        case B230400:
//        case B460800:
//        case B614400:
//        case B921600:
//            var3 = var2[1];
//            break;
//        case B1228800:
//        case B2000000:
//        case B2457600:
//        case B3000000:
//        case B6000000:
//            var3 = var2[0];
//            break;
//        default:
//            return;
//        }
//
//        if(this.V != null) {
//            this.V.b(var3);
//        }
//
//    }
    private void threadYield(int baudrate) {
        int[] var2 = new int[]{3, 5, 10, 25, 100, 200};
        int var3 = var2[3];
//      dbgMsg.save(TAG + ".threadYield.baudrate="+ct.int2String(baudrate));

        if(baudrate<0){
            return;
        }
        else if(baudrate==0){
            var3=10000;
        }
        else if(baudrate<=150){
            var3=var2[5];
        }
        else if(baudrate<=600){
            var3=var2[4];
        }
        else if(baudrate<=9600){
            var3 = var2[3];
        }
        else if(baudrate<=57600){
            var3 = var2[2];
        }
        else if(baudrate<=921600){
            var3 = var2[1];
        }
        else if(baudrate<=6000000){
            var3 = var2[0];
        }
//        switch(var1) {
//            case B0:
//                var3 = 10000;
//                break;
//            case B75:
//            case B150:
//                var3 = var2[5];
//                break;
//            case B300:
//            case B600:
//                var3 = var2[4];
//                break;
//            case B1200:
//            case B1800:
//            case B2400:
//            case B4800:
//            case B9600:
//                var3 = var2[3];
//                break;
//            case B14400:
//            case B19200:
//            case B38400:
//            case B57600:
//                var3 = var2[2];
//                break;
//            case B115200:
//            case B230400:
//            case B460800:
//            case B614400:
//            case B921600:
//                var3 = var2[1];
//                break;
//            case B1228800:
//            case B2000000:
//            case B2457600:
//            case B3000000:
//            case B6000000:
//                var3 = var2[0];
//                break;
//            default:
//                return;
//        }


        if(this.V != null) {
//          dbgMsg.save(TAG + ".threadYield.var3="+ct.int2String(var3));
            this.V.b(var3);
        }

    }
    public int read(byte[] buf) {
        int var2 = 0;
        int var4 = buf.length;
        if(var4 == 0) {
            return 0;
        } else {
            if(var4 > 4096) {
                buf = new byte[4096];
            }

            int var5;
            if(this.ap) {
                Object var7 = ReadQueueLock;
                synchronized(ReadQueueLock) {
                    var5 = this.U.size();
                    if(var5 > 0) {
                        if(var4 >= var5) {
                            var2 = var5;
                        } else {
                            var2 = var4;
                        }

                        for(int var3 = 0; var3 < var2; ++var3) {
                            Integer var6 = (Integer)this.U.poll();
                            if(var6 == null) {
                                break;
                            }

                            buf[var3] = (byte)(var6.intValue() & 255);
                        }
                    } else {
                        var2 = 0;
                    }
                }
            } else {
                var5 = this.a(buf, var4);
                if(var5 > 0) {
                    if(var4 >= var5) {
                        var2 = var5;
                    } else {
                        var2 = var4;
                    }
                }
            }

            if(var2>0) {
//                dbgMsg.save(TAG + ".read.var2=" + ct.int2String(var2));
//                for (int i = 0; i < var2; i++) {
//                    dbgMsg.save(TAG + ".read.buf[" + i + "]=" + buf[i]);
//                }
            }

            return var2;
        }
    }

    private int a(byte[] var1, int var2) {
//        for(int i=0;i< var1.length;i++) {
//            dbgMsg.save(TAG + ".a.c.var1["+i+"]=" + ct.int2String(var1[i]));
//        }
//        dbgMsg.save(TAG + ".a.c.var2=" + ct.int2String(var2));

        if(var1.length != 0 && var2 != 0) {
            int var3;
            if(this.Q > 0 && var2 <= this.Q) {
                if(!this.e) {
                    System.arraycopy(this.c, this.P, var1, 0, var2);
                } else {
                    for(var3 = 0; var3 < var2; ++var3) {
                        var1[var3] = this.c[this.P++];
                        ++this.X;

                        while((this.X - 1) % 10 != Byte.valueOf(var1[var3]).byteValue() - 48) {
                            ++this.X;
                        }
                    }

                    this.Y += var2;
                    this.Z = true;
                }

                this.Q -= var2;
                return var2;
            } else {
                var3 = 0;
                int var4 = var2;
                if(this.Q > 0) {
                    var4 = var2 - this.Q;
                    System.arraycopy(this.c, this.P, var1, var3, this.Q);
                }

                int var5 = this.K.bulkTransfer(this.M, this.c, this.c.length, this.R);
                if(var5 < 0) {
                    return var5;
                } else if(var5 == 0) {
                    return 0;
                } else {
                    int var6 = var5 / 64;
                    int var7 = var5 % 64;
                    if(var7 > 0) {
                        ++var6;
                    }

                    this.Q = var5;
                    int var8 = 0;

                    for(int var9 = 0; var9 < var6; ++var9) {
                        int var10 = var9 * 64;

                        for(int var11 = 0; var11 < 64; ++var11) {
                            this.c[var8++] = this.c[var10 + var11];
                        }
                    }

                    for(this.P = 0; this.Q > 0 && var4 > 0; --var4) {
                        var1[var3++] = this.c[this.P++];
                        if(this.e) {
                            ++this.X;

                            while((this.X - 1) % 10 != Byte.valueOf(var1[var3 - 1]).byteValue() - 48) {
                                ++this.X;
                            }
                        }

                        --this.Q;
                    }

                    if(this.e) {
                        if(var3 > 0) {
                            this.Y += var3;
                            this.Z = true;
                        }

                        if(this.Z) {
                            this.Z = false;
                        }
                    }

                    return var3;
                }
            }
        } else {
            return 0;
        }
    }

    //return tx success byte number
    public int write(byte[] buf) {
        return this.write(buf, buf.length);
    }

    public int write(byte[] buf, int wlength) {
        int var3 = 0;
        byte[] var5 = new byte[4096];
        if(PL2303Driver.FlowControl.XONXOFF == this.aq && !this.ar) {
            return 0;
        } else {
//            dbgMsg.save(TAG + ".write.wlength="+ct.int2String(wlength));
//            for(int i=0;i<wlength;i++){
//                dbgMsg.save(TAG + ".write.buf["+i+"]="+buf[i]);
//            }

            while(var3 < wlength) {
                int var6 = 4096;
                if(var3 + var6 > wlength) {
                    var6 = wlength - var3;
                }

                System.arraycopy(buf, var3, var5, 0, var6);
                int var4 = this.K.bulkTransfer(this.N, var5, var6, this.S);
                if(var4 < 0) {
                    return -1;
                }

                var3 += var4;
            }
//            dbgMsg.save(TAG + ".write.var3="+ct.int2String(var3));
            return var3;
        }
    }

    //    public int setup(PL2303Driver.BaudRate R, PL2303Driver.DataBits D, PL2303Driver.StopBits S, PL2303Driver.Parity P, PL2303Driver.FlowControl F) throws IOException {
//        boolean var6 = false;
//        if(this.K == null) {
//            return -1;
//        } else if(PL2303Driver.FlowControl.XONXOFF == this.aq && !this.ar) {
//            return 0;
//        } else {
//            for(int i=0;i<j.length;i++) {
//                dbgMsg.save(TAG + ".setup.this.j[" + i + "].0=" + ct.int2String(this.j[i],ct._E_NO_SIGN_));
//            }
//
//            //int var8 = this.K.controlTransfer(161, 33, 0, 0, this.j, 7, this.T);
//            int var8 = this.K.controlTransfer(0xA1, 0x21, 0, 0, this.j, 7, this.T);
//
//            if(var8 < 0) {
//                return var8;
//            } else {
//                boolean var7 = false;
//                int var9;
//
////R=PL2303Driver.BaudRate.B2000000;
//                //a()[]
//                switch(R) {
//                case B0:
//                    var9 = 0;
//                    break;
//                case B75:
//                    var9 = 75;
//                    break;
//                case B150:
//                    var9 = 150;
//                    break;
//                case B300:
//                    var9 = 300;
//                    break;
//                case B600:
//                    var9 = 600;
//                    break;
//                case B1200:
//                    var9 = 1200;
//                    break;
//                case B1800:
//                    var9 = 1800;
//                    break;
//                case B2400:
//                    var9 = 2400;
//                    break;
//                case B4800:
//                    var9 = 4800;
//                    break;
//                case B9600:
//                    var9 = 9600;
//                    break;
//                case B14400:
//                    var9 = 14400;
//                    break;
//                case B19200:
//                    var9 = 19200;
//                    break;
//                case B38400:
//                    var9 = 38400;
//                    break;
//                case B57600:
//                    var9 = 57600;
//                    break;
//                case B115200:
//                    var9 = 115200;
//                    break;
//                case B230400:
//                    var9 = 230400;
//                    break;
//                case B460800:
//                    var9 = 460800;
//                    break;
//                case B614400:
//                    var9 = 614400;
//                    break;
//                case B921600:
//                    var9 = 921600;
//                    break;
//                case B1228800:
//                    var9 = 1228800;
//                    break;
//                case B2000000:
//                    var9 = 2000000;
//                    break;
//                case B2457600:
//                    var9 = 2457600;
//                    break;
//                case B3000000:
//                    var9 = 3000000;
//                    break;
//                case B6000000:
//                    var9 = 6000000;
//                    break;
//                case B12000000:
//                    var9 = 12000000;
//                    break;
//                default:
//                    return -2;
//                }
//
//                if(var9 > 1228800 && this.ae == 0) {
//                    return -2;
//                } else {
//
//
//                    if(this.V != null) {
//                        this.a(R);
//                    }
////var9 = 2000000;
//                    this.j[0] = (byte)((var9     )  & 255);
//                    this.j[1] = (byte)((var9 >> 8)  & 255);
//                    this.j[2] = (byte)((var9 >> 16) & 255);
//                    this.j[3] = (byte)((var9 >> 24) & 255);
//
//                    //b()[]
//                    switch(S) {
//                    case S1:
//                        this.j[4] = 0;
//                        break;
//                    case S2:
//                        this.j[4] = 2;
//                        break;
//                    default:
//                        return -3;
//                    }
////c()[]
//                    switch(P) {
//                    case NONE:
//                        this.j[5] = 0;
//                        break;
//                    case ODD:
//                        this.j[5] = 1;
//                        break;
//                    case EVEN:
//                        this.j[5] = 2;
//                        break;
//                    default:
//                        return -4;
//                    }
////d()[
//                    switch(D) {
//                    case D5:
//                        this.j[6] = 5;
//                        break;
//                    case D6:
//                        this.j[6] = 6;
//                        break;
//                    case D7:
//                        this.j[6] = 7;
//                        break;
//                    case D8:
//                        this.j[6] = 8;
//                        break;
//                    default:
//                        return -5;
//                    }
//
//                    var8 = this.K.controlTransfer(33, 32, 0, 0, this.j, 7, this.T);
//                    if(var8 < 0) {
//                        return var8;
//                    } else {
//                        var8 = this.K.controlTransfer(33, 35, 0, 0, (byte[])null, 0, this.T);
//                        if(var8 < 0) {
//                            return var8;
//                        } else {
//                            //e()[
//                            switch(F) {
//                            case OFF:
//                                var8 = this.K.controlTransfer(64, 1, 0, 0, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.K.controlTransfer(64, 1, 1, 0, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//                                break;
//                            case RTSCTS:
//                                var8 = this.K.controlTransfer(64, 1, 0, 97, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.K.controlTransfer(64, 1, 1, 0, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//                            case RFRCTS:
//                                break;
//                            case DTRDSR:
//                                if(this.ae == 4) {
//                                    var8 = this.K.controlTransfer(64, 1, 0, 73, (byte[])null, 0, this.T);
//                                    if(var8 < 0) {
//                                        return var8;
//                                    }
//
//                                    var8 = this.K.controlTransfer(64, 1, 1, 5, (byte[])null, 0, this.T);
//                                    if(var8 < 0) {
//                                        return var8;
//                                    }
//
//                                    var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//                                    if(var8 < 0) {
//                                        return var8;
//                                    }
//                                }
//                                break;
//                            case RTSCTSDTRDSR:
//                                if(this.ae == 4) {
//                                    var8 = this.K.controlTransfer(64, 1, 0, 105, (byte[])null, 0, this.T);
//                                    if(var8 < 0) {
//                                        return var8;
//                                    }
//
//                                    var8 = this.K.controlTransfer(64, 1, 1, 7, (byte[])null, 0, this.T);
//                                    if(var8 < 0) {
//                                        return var8;
//                                    }
//
//                                    var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//                                    if(var8 < 0) {
//                                        return var8;
//                                    }
//                                }
//                                break;
//                            case XONXOFF:
//                                var8 = this.K.controlTransfer(64, 1, 0, 193, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.K.controlTransfer(64, 1, 1, 0, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//                                break;
//                            default:
//                                return -6;
//                            }
//
//                            this.aq = F;
//                            if(this.aa) {
//                                var8 = this.a(0, 49);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//
//                                var8 = this.a(1, 8);
//                                if(var8 < 0) {
//                                    return var8;
//                                }
//                            }
//
//                            return 0;
//                        }
//                    }
//                }
//            }
//        }
//    }
    public int setup(int baudrate, PL2303Driver.DataBits D, PL2303Driver.StopBits S, PL2303Driver.Parity P, PL2303Driver.FlowControl F) throws IOException {
        boolean var6 = false;
//dbgMsg.save(TAG + ".setup.i");
        if(this.K == null) {
            return -1;
        } else if(PL2303Driver.FlowControl.XONXOFF == this.aq && !this.ar) {
            return 0;
        } else {
            //int var8 = this.K.controlTransfer(161, 33, 0, 0, this.j, 7, this.T);
            int var8 = this.K.controlTransfer(0xA1, 0x21, 0, 0, this.j, 7, this.T);

            if(var8 < 0) {
                return var8;
            } else {
                boolean var7 = false;
//                int var9;

//R=PL2303Driver.BaudRate.B2000000;
                //a()[]
//                switch(R) {
//                    case B0:
//                        var9 = 0;
//                        break;
//                    case B75:
//                        var9 = 75;
//                        break;
//                    case B150:
//                        var9 = 150;
//                        break;
//                    case B300:
//                        var9 = 300;
//                        break;
//                    case B600:
//                        var9 = 600;
//                        break;
//                    case B1200:
//                        var9 = 1200;
//                        break;
//                    case B1800:
//                        var9 = 1800;
//                        break;
//                    case B2400:
//                        var9 = 2400;
//                        break;
//                    case B4800:
//                        var9 = 4800;
//                        break;
//                    case B9600:
//                        var9 = 9600;
//                        break;
//                    case B14400:
//                        var9 = 14400;
//                        break;
//                    case B19200:
//                        var9 = 19200;
//                        break;
//                    case B38400:
//                        var9 = 38400;
//                        break;
//                    case B57600:
//                        var9 = 57600;
//                        break;
//                    case B115200:
//                        var9 = 115200;
//                        break;
//                    case B230400:
//                        var9 = 230400;
//                        break;
//                    case B460800:
//                        var9 = 460800;
//                        break;
//                    case B614400:
//                        var9 = 614400;
//                        break;
//                    case B921600:
//                        var9 = 921600;
//                        break;
//                    case B1228800:
//                        var9 = 1228800;
//                        break;
//                    case B2000000:
//                        var9 = 2000000;
//                        break;
//                    case B2457600:
//                        var9 = 2457600;
//                        break;
//                    case B3000000:
//                        var9 = 3000000;
//                        break;
//                    case B6000000:
//                        var9 = 6000000;
//                        break;
//                    case B12000000:
//                        var9 = 12000000;
//                        break;
//                    default:
//                        return -2;
//                }

//dbgMsg.save(TAG + ".setup.baudrate.0="+ct.int2String(baudrate));

                if(baudrate>12000000 || baudrate<0){
                    return -2;
                }

                if(baudrate > 1228800 && this.ae == 0) {
                    return -2;
                } else {

//                  dbgMsg.save(TAG + ".setup.baudrate.1="+ct.int2String(baudrate));
                    if(this.V != null) {
                        this.threadYield(baudrate);
                    }
//var9 = 2000000;
                    this.j[0] = (byte)((baudrate) & 255);
                    this.j[1] = (byte)((baudrate >> 8) & 255);
                    this.j[2] = (byte)((baudrate >> 16) & 255);
                    this.j[3] = (byte)((baudrate >> 24) & 255);

                    //b()[]
                    switch(S) {
                        case S1:
                            this.j[4] = 0;
                            break;
                        case S2:
                            this.j[4] = 2;
                            break;
                        default:
                            return -3;
                    }
//c()[]
                    switch(P) {
                        case NONE:
                            this.j[5] = 0;
                            break;
                        case ODD:
                            this.j[5] = 1;
                            break;
                        case EVEN:
                            this.j[5] = 2;
                            break;
                        default:
                            return -4;
                    }
//d()[
                    switch(D) {
                        case D5:
                            this.j[6] = 5;
                            break;
                        case D6:
                            this.j[6] = 6;
                            break;
                        case D7:
                            this.j[6] = 7;
                            break;
                        case D8:
                            this.j[6] = 8;
                            break;
                        default:
                            return -5;
                    }
//                    for(int i=0;i<j.length;i++) {
//                        dbgMsg.save(TAG + ".setup.this.j[" + i + "].1=" + ct.int2String(this.j[i],ct._E_NO_SIGN_));
//                    }
//dbgMsg.save(TAG + ".setup.this.T=" + ct.int2String(this.T,0));
//                    var8 = this.K.controlTransfer(33, 32, 0, 0, this.j, 7, this.T);
                    var8 = this.K.controlTransfer(0x21, 0x20, 0, 0, this.j, 7, this.T);
//dbgMsg.save(TAG + ".setup.var8.0="+ct.int2String(var8));

                    if(var8 < 0) {
                        return var8;
                    } else {
//                        var8 = this.K.controlTransfer(33, 35, 0, 0, (byte[])null, 0, this.T);
                        var8 = this.K.controlTransfer(0x21, 0x23, 0, 0, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.1="+ct.int2String(var8));

                        if(var8 < 0) {
                            return var8;
                        } else {
                            //e()[
                            switch(F) {
                                case OFF:
//                                    var8 = this.K.controlTransfer(64, 1, 0, 0, (byte[])null, 0, this.T);
                                    var8 = this.K.controlTransfer(0x40, 1, 0, 0, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.2="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }

//                                    var8 = this.K.controlTransfer(64, 1, 1, 0, (byte[])null, 0, this.T);
                                    var8 = this.K.controlTransfer(0x40, 1, 1, 0, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.3="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }

//                                    var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
                                    var8 = this.K.controlTransfer(0x40, 1, 2, 0x44, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.4="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }
                                    break;
                                case RTSCTS:
                                    var8 = this.K.controlTransfer(64, 1, 0, 97, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.5="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }

                                    var8 = this.K.controlTransfer(64, 1, 1, 0, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.6="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }

                                    var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.7="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }
                                case RFRCTS:
                                    break;
                                case DTRDSR:
                                    if(this.ae == 4) {
                                        var8 = this.K.controlTransfer(64, 1, 0, 73, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.8="+ct.int2String(var8));
                                        if(var8 < 0) {
                                            return var8;
                                        }

                                        var8 = this.K.controlTransfer(64, 1, 1, 5, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.9="+ct.int2String(var8));
                                        if(var8 < 0) {
                                            return var8;
                                        }

                                        var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.A="+ct.int2String(var8));
                                        if(var8 < 0) {
                                            return var8;
                                        }
                                    }
                                    break;
                                case RTSCTSDTRDSR:
                                    if(this.ae == 4) {
                                        var8 = this.K.controlTransfer(64, 1, 0, 105, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.B="+ct.int2String(var8));
                                        if(var8 < 0) {
                                            return var8;
                                        }

                                        var8 = this.K.controlTransfer(64, 1, 1, 7, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.C="+ct.int2String(var8));
                                        if(var8 < 0) {
                                            return var8;
                                        }

                                        var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.D="+ct.int2String(var8));
                                        if(var8 < 0) {
                                            return var8;
                                        }
                                    }
                                    break;
                                case XONXOFF:
                                    var8 = this.K.controlTransfer(64, 1, 0, 193, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.E="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }

                                    var8 = this.K.controlTransfer(64, 1, 1, 0, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.F="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }

                                    var8 = this.K.controlTransfer(64, 1, 2, 68, (byte[])null, 0, this.T);
//dbgMsg.save(TAG + ".setup.var8.10="+ct.int2String(var8));
                                    if(var8 < 0) {
                                        return var8;
                                    }
                                    break;
                                default:
                                    return -6;
                            }

                            this.aq = F;
                            if(this.aa)
                            {
                                var8 = this.a(0, 49);
//dbgMsg.save(TAG + ".setup.var8.11="+ct.int2String(var8));
                                if(var8 < 0) {
                                    return var8;
                                }

                                var8 = this.a(1, 8);
//dbgMsg.save(TAG + ".setup.var8.12="+ct.int2String(var8));
                                if(var8 < 0) {
                                    return var8;
                                }
                            }

                            return 0;
                        }
                    }
                }
            }
        }
    }

    public int setDTR(boolean state) {
        if(state && (this.C & 1) != 1) {
            ++this.C;
        }

        if(!state && (this.C & 1) == 1) {
            --this.C;
        }

        int var2 = this.K.controlTransfer(33, 34, this.C, 0, (byte[])null, 0, this.T);
        return var2 < 0?var2:0;
    }

    public int setRTS(boolean state) {
        if(state && (this.C & 2) != 2) {
            this.C += 2;
        }

        if(!state && (this.C & 2) == 2) {
            this.C -= 2;
        }

        int var2 = this.K.controlTransfer(33, 34, this.C, 0, (byte[])null, 0, this.T);
        return var2 < 0?var2:0;
    }

    private int a(UsbDeviceConnection var1) {
        int var3 = 0;
        int[] var4 = new int[]{0, 0};

        if(this.ao) {
            this.ae = 4;
        } else {
            if(!this.l() && (var3 = this.m()) < 0) {
//dbgMsg.save(TAG + ".a.var3.0="+ct.int2String(var3));
                return var3;
            }

            if(var1.getRawDescriptors()[13] == 4) {
                this.ae = 4;
            }

            if((var3 = this.j()) < 0) {
//dbgMsg.save(TAG + ".a.var3.1="+ct.int2String(var3));
                return var3;
            }

            if(this.aE && (var3 = this.k()) < 0) {
//dbgMsg.save(TAG + ".a.var3.2="+ct.int2String(var3));
                return var3;
            }

            if(var1.getRawDescriptors()[13] == 5 && (var3 = this.i()) < 0) {
//dbgMsg.save(TAG + ".a.var3.3="+ct.int2String(var3));
                return var3;
            }
        }
//        dbgMsg.save(TAG + ".a.ao="+ct.boolean2String(this.ao));
//        dbgMsg.save(TAG + ".a.aE="+ct.boolean2String(this.aE));
//        dbgMsg.save(TAG + ".a.ae="+ct.int2String(this.ae));

        if(this.ae != 4 && this.ae != 6) {
            return -1;
        } else {
            for(int var2 = 128; var2 <= 130; ++var2) {
                var4 = this.b(var2);
//                for(int i=0;i<var4.length;i++) {
//                    dbgMsg.save(TAG + ".a.var4["+i+"].3=" + ct.int2String(var4[i], 0));
//                }
                if(var4[0] < 0) {
                    var4 = this.b(var2);
//                    for(int i=0;i<var4.length;i++) {
//                        dbgMsg.save(TAG + ".a.var4["+i+"].4=" + ct.int2String(var4[i], 0));
//                    }
                    return var4[0];
                }
            }

            try {
//                var3 = this.setup(PL2303Driver.BaudRate.B19200, PL2303Driver.DataBits.D8, PL2303Driver.StopBits.S1, PL2303Driver.Parity.NONE, PL2303Driver.FlowControl.OFF);
                var3 = this.setup(19200, PL2303Driver.DataBits.D8, PL2303Driver.StopBits.S1, PL2303Driver.Parity.NONE, PL2303Driver.FlowControl.OFF);

//                dbgMsg.save(TAG + ".a.var3="+ct.int2String(var3));
            } catch (IOException var6) {
                var6.printStackTrace();
            }
//            dbgMsg.save(TAG + ".a.result="+ct.int2String(var4[0] < 0 ? var3 : 0));
            return var4[0] < 0 ? var3 : 0;
        }
    }

    private int i() {
        int[] var1 = new int[2];
        short var2 = 148;
        var1 = this.b(148);

//        dbgMsg.save(TAG + ".i.var1.0="+ct.ints2String(var1,0));

        if(var1[0] < 0) {
            return var1[0];
        } else {
            if((var1[1] & var2) == var2) {
                this.ae = 6;
            } else {
                this.ae = 2;
            }
//            dbgMsg.save(TAG + ".i.this.ae="+ct.int2String(this.ae,0));
            return 0;
        }
    }

    private int j() {
        int[] var1 = new int[2];
        short var2 = 255;
        int[] var3 = new int[2];
        var1 = this.b(129);
//        dbgMsg.save(TAG + ".j.var1.0="+ct.ints2String(var1,0));

        if(var1[0] < 0) {
            return var1[0];
        } else {
            int var4 = var1[1];
            var1[0] = this.a(1, var2);
//            dbgMsg.save(TAG + ".j.var1.1="+ct.int2String(var1[0]));

            if(var1[0] < 0) {
                return var1[0];
            } else {
                var1 = this.b(129);
//                dbgMsg.save(TAG + ".j.var1.2="+ct.ints2String(var1,0));

                if(var1[0] < 0) {
                    return var1[0];
                } else {
                    if((var1[1] & 15) == 15) {
                        this.ae = 4;
                        var1 = this.c(250);
//                        dbgMsg.save(TAG + ".j.var1.3="+ct.ints2String(var1,0));

                        if(var1[0] < 0) {
                            return var1[0];
                        }

                        var3[0] = var1[1];
                        var1 = this.c(251);
//                        dbgMsg.save(TAG + ".j.var1.4="+ct.ints2String(var1,0));

                        if(var1[0] < 0) {
                            return var1[0];
                        }

                        var3[1] = var1[1];
                        if(var3[0] == 1 && var3[1] == 4) {
                            this.ae = 2;
                        } else if((var3[0] != 2 || var3[1] != 4) && (var3[0] != 3 || var3[1] != 4) && var3[0] == 1 && var3[1] == 3) {
                            this.ae = 2;
                        }
                    } else {
                        this.ae = 2;
                    }
//                    dbgMsg.save(TAG + ".j.this.ae="+ct.int2String(this.ae,0));
                    var1[0] = this.a(1, var4);
//                    dbgMsg.save(TAG + ".j.var1.5="+ct.int2String(var1[0]));

                    return var1[0] < 0?var1[0]:0;
                }
            }
        }
    }

    private String a(int var1) {
//        dbgMsg.save(TAG + ".k.var1="+ct.int2String(var1));
        char[] var2 = new char[]{Character.forDigit(var1 >> 4 & 15, 16), Character.forDigit(var1 & 15, 16)};
//        for(int i=0;i<var2.length;i++) {
//            dbgMsg.save(TAG + ".k.var2["+i+"]=" + ct.int2String(var2[i]));
//        }
        String var3 = new String(var2);

//        dbgMsg.save(TAG + ".k.var3="+var3);

        return var3;
    }

    private static String b(byte[] var0, int var1) {
        StringBuffer var3 = null;

        try {
            MessageDigest var2 = MessageDigest.getInstance("SHA-512");
            var2.reset();
            byte[] var4 = var2.digest(var0);
            var3 = new StringBuffer();
            byte[] var8 = var4;
            int var7 = var4.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                byte var5 = var8[var6];
                var3.append(String.format("%02X", new Object[]{Byte.valueOf(var5)}));
            }
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
        }

        return var3.toString();
    }

    private int k() {
        byte var1 = 9;
        int[] var2 = new int[2];
        var2 = this.c(var1);
//        dbgMsg.save(TAG + ".k.var2.0="+ct.ints2String(var2,0));

        if(var2[0] < 0) {
            return var2[0];
        } else {
            if((var2[1] & 8) == 8) {
                var2[0] = this.a(0, 49);
//                dbgMsg.save(TAG + ".k.var2.1="+ct.int2String(var2[0]));

                if(var2[0] < 0) {
                    return var2[0];
                }

                var2[0] = this.a(1, 8);
//                dbgMsg.save(TAG + ".k.var2.2="+ct.int2String(var2[0]));

                if(var2[0] < 0) {
                    return var2[0];
                }

                this.aa = true;
            }

            return var2[0];
        }
    }

    public int PL2303TB_Set_PWM(int PWM_IO_Num, byte Frequency_value, byte Duty_value) {
        int[] var4 = new int[2];
        boolean var5 = false;
        if(this.K == null) {
            return -1;
        } else {
            int var7 = Duty_value & 255;
            var7 <<= 8;
            var7 += Frequency_value;
            byte var6;
            switch(PWM_IO_Num) {
                case 0:
                    var4[0] = this.a(2, 0);
                    if(var4[0] < 0) {
                        return var4[0];
                    }

                    var6 = 16;
                    break;
                case 1:
                    var6 = 17;
                    break;
                case 2:
                    var6 = 18;
                    break;
                case 3:
                    var6 = 19;
                    break;
                default:
                    return -1;
            }

            var4[0] = this.a(var6, var7);
            return var4[0] < 0?var4[0]:0;
        }
    }

    public int PL2303TB_Enable_GPIO(int GPIO_Num, boolean Enable) {
        int[] var3 = new int[2];
        if(this.K == null) {
            return -1;
        } else {
            if(GPIO_Num == 6 || GPIO_Num == 7 || GPIO_Num == 9) {
                var3[0] = this.a(2, 0);
                if(var3[0] < 0) {
                    return var3[0];
                }
            }

            switch(GPIO_Num) {
                case 0:
                    if(Enable) {
                        this.aC |= 1;
                    } else {
                        this.aC &= -2;
                    }
                    break;
                case 1:
                    if(Enable) {
                        this.aC |= 2;
                    } else {
                        this.aC &= -3;
                    }
                    break;
                case 2:
                    if(Enable) {
                        this.aC |= 4;
                    } else {
                        this.aC &= -5;
                    }
                    break;
                case 3:
                    if(Enable) {
                        this.aC |= 8;
                    } else {
                        this.aC &= -9;
                    }
                    break;
                case 4:
                    if(Enable) {
                        this.aC |= 16;
                    } else {
                        this.aC &= -17;
                    }
                    break;
                case 5:
                    if(Enable) {
                        this.aC |= 32;
                    } else {
                        this.aC &= -33;
                    }
                    break;
                case 6:
                    if(Enable) {
                        this.aC |= 64;
                    } else {
                        this.aC &= -65;
                    }
                    break;
                case 7:
                    if(Enable) {
                        this.aC |= 128;
                    } else {
                        this.aC &= -129;
                    }
                    break;
                case 8:
                    if(Enable) {
                        this.aC |= 256;
                    } else {
                        this.aC &= -257;
                    }
                    break;
                case 9:
                    if(Enable) {
                        this.aC |= 512;
                    } else {
                        this.aC &= -513;
                    }
                    break;
                case 10:
                    if(Enable) {
                        this.aC |= 1024;
                    } else {
                        this.aC &= -1025;
                    }
                    break;
                case 11:
                    if(Enable) {
                        this.aC |= 2048;
                    } else {
                        this.aC &= -2049;
                    }
                    break;
                default:
                    return -1;
            }

            var3[0] = this.a(14, this.aC);
            return var3[0] < 0?var3[0]:0;
        }
    }

    public int PL2303HXD_Enable_GPIO(int GPIO_Num, boolean Enable) {
        int[] var3 = new int[2];
        boolean var4 = false;
        if(this.K == null) {
            return -1;
        } else {
            int var5;
            switch(GPIO_Num) {
                case 0:
                    var3 = this.b(129);
                    if(var3[0] < 0) {
                        return var3[0];
                    }

                    if(Enable) {
                        var5 = var3[1] |= 16;
                    } else {
                        var5 = var3[1] &= -17;
                    }

                    var3[0] = this.a(1, var5);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 1:
                    var3 = this.b(129);
                    if(var3[0] < 0) {
                        return var3[0];
                    }

                    if(Enable) {
                        var5 = var3[1] |= 32;
                    } else {
                        var5 = var3[1] &= -33;
                    }

                    var3[0] = this.a(1, var5);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 2:
                    if(Enable) {
                        this.ax |= 3;
                    } else {
                        this.ax &= -4;
                    }

                    var3[0] = this.a(12, this.ax);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 3:
                    if(Enable) {
                        this.ax |= 12;
                    } else {
                        this.ax &= -13;
                    }

                    var3[0] = this.a(12, this.ax);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 4:
                    if(Enable) {
                        this.az |= 3;
                    } else {
                        this.az &= -4;
                    }

                    var3[0] = this.a(6, this.az);
                    break;
                case 5:
                    if(Enable) {
                        this.az |= 12;
                    } else {
                        this.az &= -13;
                    }

                    var3[0] = this.a(6, this.az);
                    break;
                case 6:
                    if(Enable) {
                        this.az |= 48;
                    } else {
                        this.az &= -49;
                    }

                    var3[0] = this.a(6, this.az);
                    break;
                case 7:
                    if(Enable) {
                        this.az |= 192;
                    } else {
                        this.az &= -193;
                    }

                    var3[0] = this.a(6, this.az);
                    break;
                default:
                    return -1;
            }

            return 0;
        }
    }

    public int PL2303TB_Set_GPIO_Value(int GPIO_Num, int val) {
        int[] var3 = new int[2];
        byte var4 = 0;
        if(this.K == null) {
            return -1;
        } else {
            var3 = this.b(143);
            if(var3[0] < 0) {
                return var3[0];
            } else {
                int var5;
                switch(GPIO_Num) {
                    case 0:
                        if(val == 1) {
                            var5 = var4 | 1;
                        } else {
                            var5 = var4 & -2;
                        }
                        break;
                    case 1:
                        if(val == 1) {
                            var5 = var4 | 2;
                        } else {
                            var5 = var4 & -3;
                        }
                        break;
                    case 2:
                        if(val == 1) {
                            var5 = var4 | 4;
                        } else {
                            var5 = var4 & -5;
                        }
                        break;
                    case 3:
                        if(val == 1) {
                            var5 = var4 | 4;
                        } else {
                            var5 = var4 & -5;
                        }
                        break;
                    case 4:
                        if(val == 1) {
                            var5 = var4 | 16;
                        } else {
                            var5 = var4 & -17;
                        }
                        break;
                    case 5:
                        if(val == 1) {
                            var5 = var4 | 32;
                        } else {
                            var5 = var4 & -33;
                        }
                        break;
                    case 6:
                        if(val == 1) {
                            var5 = var4 | 64;
                        } else {
                            var5 = var4 & -65;
                        }
                        break;
                    case 7:
                        if(val == 1) {
                            var5 = var4 | 128;
                        } else {
                            var5 = var4 & -129;
                        }
                        break;
                    case 8:
                        if(val == 1) {
                            var5 = var4 | 256;
                        } else {
                            var5 = var4 & -257;
                        }
                        break;
                    case 9:
                        if(val == 1) {
                            var5 = var4 | 512;
                        } else {
                            var5 = var4 & -513;
                        }
                        break;
                    case 10:
                        if(val == 1) {
                            var5 = var4 | 1024;
                        } else {
                            var5 = var4 & -1025;
                        }
                        break;
                    case 11:
                        if(val == 1) {
                            var5 = var4 | 2048;
                        } else {
                            var5 = var4 & -2049;
                        }
                        break;
                    default:
                        return -1;
                }

                var3[0] = this.a(15, var5);
                return var3[0] < 0?var3[0]:0;
            }
        }
    }

    public int PL2303HXD_Set_GPIO_Value(int GPIO_Num, int val) {
        int[] var3 = new int[2];
        boolean var4 = false;
        if(this.K == null) {
            return -1;
        } else {
            int var5;
            switch(GPIO_Num) {
                case 0:
                    var3 = this.b(129);
                    if(var3[0] < 0) {
                        return var3[0];
                    }

                    if(val == 1) {
                        var5 = var3[1] |= 64;
                    } else {
                        var5 = var3[1] &= -65;
                    }

                    var3[0] = this.a(1, var5);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 1:
                    var3 = this.b(129);
                    if(var3[0] < 0) {
                        return var3[0];
                    }

                    if(val == 1) {
                        var5 = var3[1] |= 128;
                    } else {
                        var5 = var3[1] &= -129;
                    }

                    var3[0] = this.a(1, var5);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 2:
                    var3 = this.b(141);
                    if(var3[0] < 0) {
                        return var3[0];
                    }

                    if(val == 1) {
                        var5 = var3[1] |= 1;
                    } else {
                        var5 = var3[1] &= -2;
                    }

                    var3[0] = this.a(13, var5);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 3:
                    var3 = this.b(141);
                    if(var3[0] < 0) {
                        return var3[0];
                    }

                    if(val == 1) {
                        var5 = var3[1] |= 2;
                    } else {
                        var5 = var3[1] &= -3;
                    }

                    var3[0] = this.a(13, var5);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 4:
                    if(this.aB == 0) {
                        this.aA = 0;
                    } else {
                        this.aA = this.aB;
                    }

                    if(val == 1) {
                        this.aA |= 1;
                    } else {
                        this.aA &= -2;
                    }

                    this.aB = this.aA;
                    var3[0] = this.a(7, this.aA);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 5:
                    if(this.aB == 0) {
                        this.aA = 0;
                    } else {
                        this.aA = this.aB;
                    }

                    if(val == 1) {
                        this.aA |= 2;
                    } else {
                        this.aA &= -3;
                    }

                    this.aB = this.aA;
                    var3[0] = this.a(7, this.aA);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 6:
                    if(this.aB == 0) {
                        this.aA = 0;
                    } else {
                        this.aA = this.aB;
                    }

                    if(val == 1) {
                        this.aA |= 4;
                    } else {
                        this.aA &= -5;
                    }

                    this.aB = this.aA;
                    var3[0] = this.a(7, this.aA);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                case 7:
                    if(this.aB == 0) {
                        this.aA = 0;
                    } else {
                        this.aA = this.aB;
                    }

                    if(val == 1) {
                        this.aA |= 8;
                    } else {
                        this.aA &= -9;
                    }

                    this.aB = this.aA;
                    var3[0] = this.a(7, this.aA);
                    if(var3[0] < 0) {
                        return var3[0];
                    }
                    break;
                default:
                    return -1;
            }

            return 0;
        }
    }

    public int[] PL2303TB_Get_GPIO_Value(int GPIO_Num) {
        int[] var2 = new int[2];
        if(this.K == null) {
            var2[0] = -1;
            return var2;
        } else {
            var2 = this.b(143);
            if(var2[0] < 0) {
                return var2;
            } else {
                switch(GPIO_Num) {
                    case 0:
                        if((var2[1] & 1) == 1) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 1:
                        if((var2[1] & 2) == 2) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 2:
                        if((var2[1] & 4) == 4) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 3:
                        if((var2[1] & 8) == 8) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 4:
                        if((var2[1] & 16) == 16) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 5:
                        if((var2[1] & 32) == 32) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 6:
                        if((var2[1] & 64) == 64) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 7:
                        if((var2[1] & 128) == 128) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 8:
                        if((var2[1] & 256) == 256) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 9:
                        if((var2[1] & 512) == 512) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 10:
                        if((var2[1] & 1024) == 1024) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    case 11:
                        if((var2[1] & 2048) == 2048) {
                            var2[1] = 1;
                        } else {
                            var2[1] = 0;
                        }
                        break;
                    default:
                        var2[0] = -1;
                        return var2;
                }

                return var2;
            }
        }
    }

    public int[] PL2303HXD_Get_GPIO_Value(int GPIO_Num) {
        int[] var2 = new int[2];
        if(this.K == null) {
            var2[0] = -1;
            return var2;
        } else {
            switch(GPIO_Num) {
                case 0:
                    var2 = this.b(129);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 64) == 64) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 1:
                    var2 = this.b(129);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 128) == 128) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 2:
                    var2 = this.b(141);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 1) == 1) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 3:
                    var2 = this.b(141);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 2) == 2) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 4:
                    var2 = this.b(135);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 1) == 1) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 5:
                    var2 = this.b(135);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 2) == 2) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 6:
                    var2 = this.b(135);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 4) == 4) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                case 7:
                    var2 = this.b(135);
                    if(var2[0] < 0) {
                        return var2;
                    }

                    if((var2[1] & 8) == 8) {
                        var2[1] = 1;
                    } else {
                        var2[1] = 0;
                    }
                    break;
                default:
                    var2[0] = -1;
                    return var2;
            }

            return var2;
        }
    }

    private int a(int var1, int var2) {
        boolean var3 = false;
        if(this.K == null) {
            return -1;
        } else {

//            dbgMsg.save(TAG + ".a.var1.a="+ct.int2String(var1));
//            dbgMsg.save(TAG + ".a.var2.a="+ct.int2String(var2));

            int var4 = this.K.controlTransfer(64, 1, var1, var2, (byte[])null, 0, this.T);

//            dbgMsg.save(TAG + ".a.var4.a="+ct.int2String(var4));

            return var4 < 0?var4:var4;
        }
    }

    private int[] b(int var1) {
        int[] var2 = new int[2];
        byte[] var3 = new byte[1];
        var2[0] = 0;
        if(this.K == null) {
            var2[0] = -1;
//            dbgMsg.save(TAG + ".b.var2[0].a0="+ct.int2String(var2[0]));

            return var2;
        } else {

//            dbgMsg.save(TAG + ".b.var1="+ct.int2String(var1));
//            dbgMsg.save(TAG + ".b.var3="+ct.bytes2String(var3));

            var2[0] = this.K.controlTransfer(192, 1, var1, 0, var3, 1, this.T);
//            dbgMsg.save(TAG + ".b.var2[0].a1="+ct.int2String(var2[0]));

            if(var2[0] < 0) {
                return var2;
            } else {
                var2[1] = var3[0];
//                for(int i=0;i<var2.length;i++) {
//                    dbgMsg.save(TAG + ".b.var2["+i+"].a2=" + ct.int2String(var2[i]));
//                }
                return var2;
            }
        }
    }

    private int[] c(int var1) {
//        dbgMsg.save(TAG + ".c.var1.b0="+ct.int2String(var1,0));

        int[] var2 = new int[]{0, 0};
        var2 = this.b(132);

//        dbgMsg.save(TAG + ".c.var2.b0="+ct.ints2String(var2,0));

        if(var2[0] < 0) {
            return var2;
        } else {
            var2[0] = this.a(4, var1);
//            dbgMsg.save(TAG + ".c.var2.b1="+ct.int2String(var2[0]));

            if(var2[0] < 0) {
                return var2;
            } else {
                var2 = this.b(132);
//                dbgMsg.save(TAG + ".c.var2.b2="+ct.ints2String(var2,0));

                if(var2[0] < 0) {
                    return var2;
                } else {
                    var2 = this.b(131);
                    return var2[0] < 0?var2:var2;
                }
            }
        }
    }

    private boolean l() {
        int[] var1 = new int[2];
        int[] var2 = new int[2];

        for(int var3 = 0; var3 < 2; ++var3) {
            var1 = this.c(var3);
//            dbgMsg.save(TAG + ".l.var1.c0="+ct.ints2String(var1,0));

            if(var1[0] < 0) {
                return this.aE;
            }

            var2[var3] = var1[1];
        }
//        dbgMsg.save(TAG + ".l.var2[0].c1="+ct.int2String(var2[0]));
//        dbgMsg.save(TAG + ".l.var2[1].c1="+ct.int2String(var2[1]));

        if(var2[0] == 123 && var2[1] == 6) {
            this.aE = true;
        }

        return this.aE;
    }

    private int m() {
        int[] var1 = new int[]{1, 0, 68};
        int[] var2 = new int[]{this.a(2056, 0), 0};

//        for(int i=0;i<var2.length;i++) {
//            dbgMsg.save(TAG + ".m.var2["+i+"]=" + ct.int2String(var2[i]));
//        }

        if(var2[0] < 0) {
            return var2[0];
        } else {
            var2[0] = this.a(2313, 0);

            if(var2[0] < 0) {
                return var2[0];
            } else {
                for(int var3 = 0; var3 <= 2; ++var3) {
                    var2[0] = this.a(var3, var1[var3]);
                    if(var2[0] < 0) {
                        return var2[0];
                    }
                }

                for(int var4 = 128; var4 <= 130; ++var4) {
                    var2 = this.b(var4);
                    if(var2[0] < 0) {
                        return var2[0];
                    }
                }

                return var2[0];
            }
        }
    }

//    private int d(int var1) {
//
//
//        byte[] var2 = new byte[2];
//        byte[] var3 = new byte[2];
//        boolean var4 = false;
//        boolean var5 = false;
//        if(this.K == null) {
//            return -1;
//        } else {
//            var3[0] = (byte)(var1 & 255);
//            var3[1] = (byte)(var1 >> 8 & 255);
//            int var8 = this.K.controlTransfer(161, 32, 0, 0, var3, 2, this.S);
//            if(var8 < 0) {
//                return var8;
//            } else {
//                try {
//                    Thread.sleep(100L);
//                } catch (InterruptedException var7) {
//                    var7.printStackTrace();
//                }
//
//                var8 = this.K.bulkTransfer(this.O, var2, var2.length, this.R);
//                if(var8 < 0) {
//                    return 0;
//                } else {
//                    int var9 = var2[1] << 8 | var2[0];
//                    return var9;
//                }
//            }
//        }
//    }

    private int[] n() {
        int[] var1 = new int[2];
        short var2 = 135;
        var1[0] = 0;
        var1 = this.b(var2);
        return var1[0] < 0?var1:var1;
    }

    public int[] PL2303HXD_GetCommModemStatus() {
        int[] var1 = new int[2];
        byte var2 = 0;
        var1 = this.n();
        if(var1[0] < 0) {
            return var1;
        } else {
            int var3;
            if((var1[1] & 1) == 1) {
                var3 = var2 & -9;
            } else {
                var3 = var2 | 8;
            }

            if((var1[1] & 2) == 2) {
                var3 &= -2;
            } else {
                var3 |= 1;
            }

            if((var1[1] & 4) == 4) {
                var3 &= -3;
            } else {
                var3 |= 2;
            }

            if((var1[1] & 8) == 8) {
                var3 &= -129;
            } else {
                var3 |= 128;
            }

            var1[1] = var3;
            return var1;
        }
    }

    public void PL2303LibGetVersion(byte[] byVersion) {
        boolean var2 = false;
        int var5;
        if(byVersion.length < i.length()) {
            var5 = byVersion.length;
        } else {
            var5 = i.length();
        }

        char[] var3 = i.toCharArray();

        for(int var4 = 0; var4 < var5; ++var4) {
            byVersion[var4] = (byte)var3[var4];
        }

    }

    public boolean PL2303USBFeatureSupported() {
        boolean var1 = this.d.getPackageManager().hasSystemFeature("android.hardware.usb.host");
        return var1;
    }

    public String PL2303Device_GetSerialNumber() {
        return this.isConnected()?this.K.getSerial():null;
    }

    public boolean PL2303Device_IsHasPermission() {
        return this.an;
    }

    public boolean PL2303Device_IsSupportChip() {
        boolean var1 = false;
        if(this.ae == 4) {
            var1 = true;
        }

        return var1;
    }

    public boolean PL2303Device_SetCommTimeouts(int TimeoutConstant) {
        if(TimeoutConstant < 0) {
            return false;
        } else {
            this.R = TimeoutConstant;
            this.S = TimeoutConstant;
            return true;
        }
    }

    public boolean PL2303Device_GetCommTimeouts(int TimeoutConstant) {
        TimeoutConstant = this.S;
        return true;
    }

    private static void a(Object var0) {
    }

    public static enum BaudRate {
        B0,
        B75,
        B150,
        B300,
        B600,
        B1200,
        B1800,
        B2400,
        B4800,
        B9600,
        B14400,
        B19200,
        B38400,
        B57600,
        B115200,
        B230400,
        B460800,
        B614400,
        B921600,
        B1000000,
        B1228800,
        B2000000,
        B2457600,
        B3000000,
        B6000000,
        B12000000;

        private BaudRate() {
        }
    }

    public static enum DataBits {
        D5,
        D6,
        D7,
        D8;

        private DataBits() {
        }
    }

    public static enum FlowControl {
        OFF,
        RTSCTS,
        RFRCTS,
        DTRDSR,
        RTSCTSDTRDSR,
        XONXOFF;

        private FlowControl() {
        }
    }

    public static enum Parity {
        NONE,
        ODD,
        EVEN;

        private Parity() {
        }
    }
    public static enum StopBits {
        S1,
        S2;

        private StopBits() {
        }
    }


    class a extends Thread {
        private int b;
        private int c;
        private boolean d = true;
        private boolean e = false;
        private AtomicInteger f = new AtomicInteger(500);

        a() {
        }

        public void a() {
            this.c = 0;
            this.b = 0;
            PL2303Driver.this.U.clear();
        }

        public void a(int var1) {
            this.a();
            this.b(var1);
        }

        public void b(int var1) {
            this.f.set(var1);
        }

        public void b() {
            this.e = true;

            while(this.isAlive()) {
                ;
            }

            PL2303Driver.this.U.clear();
        }

        private void c(int var1) {
            if(var1 != 0) {
                long var2 = System.currentTimeMillis();

                long var4;
                do {
                    var4 = System.currentTimeMillis();
                    Thread.yield();
                } while(var4 - var2 <= (long)var1);

            }
        }

        public void run() {
            try {
                byte[] var1 = new byte[4096];

                while(!this.e) {
                    this.b = PL2303Driver.this.a(var1, var1.length);
                    if(this.b > 0) {
                        Object var3 = PL2303Driver.ReadQueueLock;
                        synchronized(PL2303Driver.ReadQueueLock) {
                            this.c = PL2303Driver.this.U.size();
                            if(4096 != this.c) {
                                for(int var4 = 0; var4 < this.b && this.c < 4096; ++var4) {
                                    int var2 = Integer.valueOf(var1[var4]).intValue();
                                    if(PL2303Driver.FlowControl.XONXOFF == PL2303Driver.this.aq) {
                                        if(19 == var2) {
                                            PL2303Driver.this.ar = false;
                                            continue;
                                        }

                                        if(17 == var2) {
                                            PL2303Driver.this.ar = true;
                                            continue;
                                        }
                                    }

                                    this.d = PL2303Driver.this.U.offer(Integer.valueOf(var2));
                                    if(!this.d) {
                                        break;
                                    }

                                    this.c = PL2303Driver.this.U.size();
                                }
                            }
                        }
                    }

                    int var7 = this.f.get();
                    this.c(var7);
                }
            } catch (Exception var6) {
                ;
            }

        }
    }


}

