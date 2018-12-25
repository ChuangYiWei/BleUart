package com.example.johnny_wei.bleuart.common;

import android.content.Context;
import android.hardware.usb.UsbManager;

import com.example.johnny_wei.bleuart.R;
import com.example.johnny_wei.bleuart.driver_pl2303.PL2303Driver;

import java.util.HashMap;
import java.util.Map;


public class pUart{
    //version ==========================================================
    private static final String versionx="2017/04/28 19:36:58";
    //DBG ==============================================================
    private final String TAG =getClass().getSimpleName();
    public final static  String TAG_CLASS =
            "com.baboo.android.uart";

    private DBG dbgMsg;
    //String ===========================================================
    //fine item string array,seperate by ","
    public static final String DB_MATCH_SA  = "db_match_sa";    //ex:"item01=1,item02=1"
    public static final String DB_MATCH     = "db_match";       //ex:"item01=1"
    public static final String DB_SORT      = "db_sort";        //ex:"item01"

    //---------------------------------PL2303Driver.Chip
    public static final String UR_Chip
            = TAG_CLASS+".UR_Chip";
    public static final String UR_Chip_PL2303_
            = UR_Chip+".PL2303";
    //---------------------------------PL2303Driver.BaudRate
    public static final String UR_BaudRate
            = TAG_CLASS+".UR_BaudRate";
    public static final String UR_BaudRate_0
            = UR_BaudRate+".0";
    public static final String UR_BaudRate_75
            = UR_BaudRate+".75";
    public static final String UR_BaudRate_150
            = UR_BaudRate+".150";
    public static final String UR_BaudRate_300
            = UR_BaudRate+".300";
    public static final String UR_BaudRate_600
            = UR_BaudRate+".600";
    public static final String UR_BaudRate_1200
            = UR_BaudRate+".1200";
    public static final String UR_BaudRate_1800
            = UR_BaudRate+".1800";
    public static final String UR_BaudRate_2400
            = UR_BaudRate+".2400";
    public static final String UR_BaudRate_4800
            = UR_BaudRate+".4800";
    public static final String UR_BaudRate_9600
            = UR_BaudRate+".9600";
    public static final String UR_BaudRate_14400
            = UR_BaudRate+".14400";
    public static final String UR_BaudRate_19200
            = UR_BaudRate+".19200";
    public static final String UR_BaudRate_38400
            = UR_BaudRate+".38400";
    public static final String UR_BaudRate_57600
            = UR_BaudRate+".57600";
    public static final String UR_BaudRate_115200
            = UR_BaudRate+".115200";
    public static final String UR_BaudRate_230400
            = UR_BaudRate+".230400";
    public static final String UR_BaudRate_460800
            = UR_BaudRate+".460800";
    public static final String UR_BaudRate_614400
            = UR_BaudRate+".614400";
    public static final String UR_BaudRate_921600
            = UR_BaudRate+".921600";
    public static final String UR_BaudRate_1000000
            = UR_BaudRate+".1000000";
    public static final String UR_BaudRate_1228800
            = UR_BaudRate+".1228800";
    public static final String UR_BaudRate_2000000
            = UR_BaudRate+".2000000";
    public static final String UR_BaudRate_2457600
            = UR_BaudRate+".2457600";
    public static final String UR_BaudRate_3000000
            = UR_BaudRate+".3000000";
    public static final String UR_BaudRate_6000000
            = UR_BaudRate+".6000000";
    public static final String UR_BaudRate_12000000
            = UR_BaudRate+".12000000";
    //---------------------------------PL2303Driver.DataBits
    public static final String UR_DataBits
            = TAG_CLASS+".UR_DataBits";
    public static final String UR_DataBits_5
            = UR_DataBits+".5";
    public static final String UR_DataBits_6
            = UR_DataBits+".6";
    public static final String UR_DataBits_7
            = UR_DataBits+".7";
    public static final String UR_DataBits_8
            = UR_DataBits+".8";
    //---------------------------------PL2303Driver.StopBits
    public static final String UR_StopBits
            = TAG_CLASS+".UR_StopBits";
    public static final String UR_StopBits_1
            = UR_StopBits+".1";
    public static final String UR_StopBits_2
            = UR_StopBits+".2";
    //---------------------------------PL2303Driver.Parity
    public static final String UR_Parity
            = TAG_CLASS+".UR_Parity";
    public static final String UR_Parity_NONE
            = UR_Parity+".NONE";
    public static final String UR_Parity_ODD
            = UR_Parity+".ODD";
    public static final String UR_Parity_EVEN
            = UR_Parity+".EVEN";
    //---------------------------------PL2303Driver.FlowControl
    public static final String UR_FlowControl
            = TAG_CLASS+".UR_FlowControl";
    public static final String UR_FlowControl_OFF
            = UR_FlowControl+".OFF";
    public static final String UR_FlowControl_RTSCTS
            = UR_FlowControl+".RTSCTS";
    public static final String UR_FlowControl_RFRCTS
            = UR_FlowControl+".RFRCTS";
    public static final String UR_FlowControl_DTRDSR
            = UR_FlowControl+".DTRDSR";
    public static final String UR_FlowControl_RTSCTSDTRDSR
            = UR_FlowControl+".RTSCTSDTRDSR";
    public static final String UR_FlowControl_XONXOFF
            = UR_FlowControl+".XONXOFF";
    //---------------------------------rx type
    public static final String UR_Rx_Type
            = TAG_CLASS+".UR_RxType";
    public static final String UR_Rx_Type_Bin
            = UR_Rx_Type+".bin";
    public static final String UR_Rx_Type_Ascii
            = UR_Rx_Type+".ascii";
    //---------------------------------
    public static final String _S_MAP_s_
            = TAG_CLASS+"._S_MAP_s_";
    public static final String _S_MAP_Timeout_i_
            = TAG_CLASS+"._S_MAP_Timeout_i_";
    public static final String _S_MAP_CHIP_s_
            = UR_Chip;


    public static final String _S_MAP_BaudRate_e_
            = UR_BaudRate;
    public static final String _S_MAP_DataBits_e_
            = UR_DataBits;
    public static final String _S_MAP_StopBits_e_
            = UR_StopBits;
    public static final String _S_MAP_Parity_e_
            = UR_Parity;
    public static final String _S_MAP_FlowControl_e_
            = UR_FlowControl;
    public static final String _S_MAP_Rx_Type_s_
            = UR_Rx_Type;
    //==================================================================
    public static final String _ID = "_id";

    private PL2303Driver.BaudRate       mBaudrate       = PL2303Driver.BaudRate.B115200;
    private PL2303Driver.DataBits       mDataBits       = PL2303Driver.DataBits.D8;
    private PL2303Driver.StopBits       mStopBits       = PL2303Driver.StopBits.S1;
    private PL2303Driver.Parity         mParity         = PL2303Driver.Parity.NONE;
    private PL2303Driver.FlowControl    mFlowControl    = PL2303Driver.FlowControl.OFF;
    private static int                  mTimeout        =700;
    private static int                  mBaudratei      =115200;
    private static final String ACTION_USB_PERMISSION = "com.prolific.pl2303hxdsimpletest.USB_PERMISSION";

//    public Spinner PL2303HXD_BaudRate_spinner;
//    public int PL2303HXD_BaudRate;
//    public String PL2303HXD_BaudRate_str="B4800";

    PL2303Driver mSerial;
    //String[] =========================================================
    //int ==============================================================
    public static final int _ST_SUCCESS_                =0x00;
    public static final int _ST_LENGTH_0_               =0xF0;
    public static final int _ST_LENGTH_INVALID_         =0xF1;
    public static final int _ST_UST_HOST_NOT_SUPPORT_   =0xF2;
    public static final int _ST_NOT_ENMERATE_           =0xF3;
    public static final int _ST_NOT_CONNECTED_          =0xF4;
    public static final int _ST_OPEN_FAIL_              =0xF5;
    public static final int _ST_CHIP_NOT_SUPPORT_       =0xF6;
    public static final int _ST_WRITE_FAIL_             =0xF7;
    //---------------------------------
    public static final int _I_MAP_BaudRate_e_          =0x0001;
    public static final int _I_MAP_DataBits_e_          =0x0002;
    public static final int _I_MAP_StopBits_e_          =0x0004;
    public static final int _I_MAP_Parity_e_            =0x0008;
    public static final int _I_MAP_FlowControl_e_       =0x0010;
    public static final int _I_MAP_Rx_Type_s_           =0x0020;
    //---------------------------------

    //can't use byte,otherwise highest sign bit may cause if check fail
    int[] dbgEnArr={0x00,0x07,0x00,0x00};//only use [7:0]
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
    private Map<String, String> mapUart = new HashMap<String, String>();
    //==============================================================================================

    //==============================================================================================
    Context context;
    public pUart(){

        onCreate();
    }

    //==============================================================================================
    //@Override
    public boolean onCreate() {
        String[] tmpS=new String[2];
        // Context context = getContext();
//        String packageName = this.context.getPackageName();
        if((dbgEnArr[2] & _DBG_02_02_State_)>0) {
            dbgMsg.save(TAG + ".onCreate");
        }
        initClass();

        this.context=DBG.getContext();
        //-------------------------------------
        mapUart.clear();
        tmpS[0]=_S_MAP_BaudRate_e_;
        tmpS[1]=UR_BaudRate_115200;
        mapUart.put(tmpS[0],tmpS[1]);
        //--------------------
        tmpS[0]=_S_MAP_DataBits_e_;
        tmpS[1]=UR_DataBits_8;
        mapUart.put(tmpS[0],tmpS[1]);
        //--------------------
        tmpS[0]=_S_MAP_StopBits_e_;
        tmpS[1]=UR_StopBits_1;
        mapUart.put(tmpS[0],tmpS[1]);
        //--------------------
        tmpS[0]=_S_MAP_Parity_e_;
        tmpS[1]=UR_Parity_NONE;
        mapUart.put(tmpS[0],tmpS[1]);
        //--------------------
        tmpS[0]=_S_MAP_FlowControl_e_;
        tmpS[1]=UR_FlowControl_OFF;
        mapUart.put(tmpS[0],tmpS[1]);
        //--------------------
        if((dbgEnArr[2] & _DBG_02_02_State_)>0) {
            dbgMsg.save(TAG + ".onCreate.mapUart.toString(=" + mapUart.toString());
        }
        //-------------------------------------
        return true;
    }
    //==============================================================================================
    void initClass(){
        if(dbgMsg==null)
        {
            dbgMsg = new DBG(R.integer.pUART_dbg);//first need send context

            ct=new pConvertType();
            sp=new pString();

//dbgMsg.save(TAG + ".dbgIni" + "API_Level=" + ct.int2String(android.os.Build.VERSION.SDK_INT));
        }
    }

    //==============================================================================================
    public int open(String inS) {
//dbgMsg.save(TAG + ".openUsbSerial.0");
        String[] tmpS=new String[2];
        int result=-1;
//        mapUart.clear();
        if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
            dbgMsg.save(TAG + ".open.inS=" + inS);
            dbgMsg.save(TAG + ".open.mapUart.toString().0=" + mapUart.toString());
        }

        mapUart.putAll(ct.string2Map(inS));

        if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
            dbgMsg.save(TAG + ".open.mapUart.toString().1=" + mapUart.toString());
        }

        tmpS[0]=_S_MAP_CHIP_s_;
        tmpS[1]=mapUart.get(tmpS[0]);

//        if(tmpS[1].equals(UR_Chip_PL2303_ ))
        {
            //mapUart.put(tmpS[0],UR_Chip_PL2303_);

            mSerial = new PL2303Driver((UsbManager) this.context.getSystemService(Context.USB_SERVICE),
                    this.context, ACTION_USB_PERMISSION);

            if (mSerial == null) {
//dbgMsg.save(TAG + ".openUsbSerial.2");
                return _ST_UST_HOST_NOT_SUPPORT_;
            }
            // check USB host function.
            if (!mSerial.PL2303USBFeatureSupported()) {
//dbgMsg.save(TAG + ".openUsbSerial.3");
                return _ST_UST_HOST_NOT_SUPPORT_;
            }

            //connect need wait for awhile,>=2
            for (int i = 0; i < 10; i++) {
                if (mSerial.isConnected()) {
                    break;
                } else {
                    if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
                        dbgMsg.save(TAG + ".openUsbSerial.4");
                    }
                    if (!mSerial.enumerate()) {
//dbgMsg.save(TAG + ".openUsbSerial.5");
//                Toast.makeText(this, "no more devices found", Toast.LENGTH_SHORT).show();
//                return;
                        return _ST_NOT_ENMERATE_;
                    } else {
                        //Log.d(TAG, "onResume:enumerate succeeded!");
                    }
                }//if isConnected
            }

            if (mSerial.isConnected()) {
//dbgMsg.save(TAG + ".openUsbSerial.6");
                //-------------------------------------
                tmpS[0]=_S_MAP_BaudRate_e_;
                tmpS[1]=mapUart.get(tmpS[0]);

                if(ct.isValid(tmpS[1])){
                    if(tmpS[1].equals(UR_BaudRate_0)){
                        mBaudrate = PL2303Driver.BaudRate.B0;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_75)){
                        mBaudrate = PL2303Driver.BaudRate.B75;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_150)){
                        mBaudrate = PL2303Driver.BaudRate.B150;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_300)){
                        mBaudrate = PL2303Driver.BaudRate.B300;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_600)){
                        mBaudrate = PL2303Driver.BaudRate.B600;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_1200)){
                        mBaudrate = PL2303Driver.BaudRate.B1200;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_1800)){
                        mBaudrate = PL2303Driver.BaudRate.B1800;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_2400)){
                        mBaudrate = PL2303Driver.BaudRate.B2400;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_4800)){
                        mBaudrate = PL2303Driver.BaudRate.B4800;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_9600)){
                        mBaudrate = PL2303Driver.BaudRate.B9600;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_14400)){
                        mBaudrate = PL2303Driver.BaudRate.B14400;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_19200)){
                        mBaudrate = PL2303Driver.BaudRate.B19200;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_38400)){
                        mBaudrate = PL2303Driver.BaudRate.B38400;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_57600)){
                        mBaudrate = PL2303Driver.BaudRate.B57600;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_115200)){
                        mBaudrate = PL2303Driver.BaudRate.B115200;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_230400)){
                        mBaudrate = PL2303Driver.BaudRate.B230400;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_460800)){
                        mBaudrate = PL2303Driver.BaudRate.B460800;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_614400)){
                        mBaudrate = PL2303Driver.BaudRate.B614400;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_921600)){
                        mBaudrate = PL2303Driver.BaudRate.B921600;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_1000000)){
                        mBaudrate = PL2303Driver.BaudRate.B1000000;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_1228800)){
                        mBaudrate = PL2303Driver.BaudRate.B1228800;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_2000000)){
                        mBaudrate = PL2303Driver.BaudRate.B2000000;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_2457600)){
                        mBaudrate = PL2303Driver.BaudRate.B2457600;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_3000000)){
                        mBaudrate = PL2303Driver.BaudRate.B3000000;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_6000000)){
                        mBaudrate = PL2303Driver.BaudRate.B6000000;
                    }
                    else if(tmpS[1].equals(UR_BaudRate_12000000)){
                        mBaudrate = PL2303Driver.BaudRate.B12000000;
                    }
                    if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
                        dbgMsg.save(TAG + ".tmpS[1].0=" + tmpS[1]);
                    }
                    tmpS[1]=tmpS[1].substring(tmpS[1].lastIndexOf(".")+1,tmpS[1].length());

                    if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
                        dbgMsg.save(TAG + ".tmpS[1].1=" + tmpS[1]);
                    }

                    mBaudratei=ct.string2Int(tmpS[1]);

                    if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
                        dbgMsg.save(TAG + ".mBaudratei=" + ct.int2String(mBaudratei));
                    }
                }

//dbgMsg.save(TAG + ".openUsbSerial.7");
                //-------------------------------------
                tmpS[0]=_S_MAP_DataBits_e_;
                tmpS[1]=mapUart.get(tmpS[0]);

                //mDataBits = PL2303Driver.DataBits.D8;
                if(ct.isValid(tmpS[1])){
                    if(tmpS[1].equals(UR_DataBits_5)){
                        mDataBits = PL2303Driver.DataBits.D5;
                    }
                    else if(tmpS[1].equals(UR_DataBits_6)){
                        mDataBits = PL2303Driver.DataBits.D6;
                    }
                    else if(tmpS[1].equals(UR_DataBits_7)){
                        mDataBits = PL2303Driver.DataBits.D7;
                    }
                    else if(tmpS[1].equals(UR_DataBits_8)){
                        mDataBits = PL2303Driver.DataBits.D8;
                    }

                }
//dbgMsg.save(TAG + ".openUsbSerial.8");
                //-------------------------------------
                tmpS[0]=_S_MAP_StopBits_e_;
                tmpS[1]=mapUart.get(tmpS[0]);

                if(ct.isValid(tmpS[1])){
                    if(tmpS[1].equals(UR_StopBits_1)){
                        mStopBits = PL2303Driver.StopBits.S1;
                    }
                    else if(tmpS[1].equals(UR_StopBits_2)){
                        mStopBits = PL2303Driver.StopBits.S2;
                    }
                }
//dbgMsg.save(TAG + ".openUsbSerial.9");
                //-------------------------------------
                tmpS[0]=_S_MAP_Parity_e_;
                tmpS[1]=mapUart.get(tmpS[0]);

                if(ct.isValid(tmpS[1])){
                    if(tmpS[1].equals(UR_Parity_NONE)){
                        mParity = PL2303Driver.Parity.NONE;
                    }
                    else if(tmpS[1].equals(UR_Parity_ODD)){
                        mParity = PL2303Driver.Parity.ODD;
                    }
                    else if(tmpS[1].equals(UR_Parity_EVEN)){
                        mParity = PL2303Driver.Parity.EVEN;
                    }
                }

//dbgMsg.save(TAG + ".openUsbSerial.10");
                //-------------------------------------
                tmpS[0]=_S_MAP_FlowControl_e_;
                tmpS[1]=mapUart.get(tmpS[0]);

                if(ct.isValid(tmpS[1])){
                    if(tmpS[1].equals(UR_FlowControl_OFF)){
                        mFlowControl = PL2303Driver.FlowControl.OFF;
                    }
                    else if(tmpS[1].equals(UR_FlowControl_RTSCTS)){
                        mFlowControl = PL2303Driver.FlowControl.RTSCTS;
                    }
                    else if(tmpS[1].equals(UR_FlowControl_RFRCTS)){
                        mFlowControl = PL2303Driver.FlowControl.RFRCTS;
                    }
                    else if(tmpS[1].equals(UR_FlowControl_DTRDSR)){
                        mFlowControl = PL2303Driver.FlowControl.DTRDSR;
                    }
                    else if(tmpS[1].equals(UR_FlowControl_RTSCTSDTRDSR)){
                        mFlowControl = PL2303Driver.FlowControl.RTSCTSDTRDSR;
                    }
                    else if(tmpS[1].equals(UR_FlowControl_XONXOFF)){
                        mFlowControl = PL2303Driver.FlowControl.XONXOFF;
                    }
                }
//dbgMsg.save(TAG + ".openUsbSerial.11");
                //-------------------------------------
                tmpS[0]=_S_MAP_Timeout_i_;
                tmpS[1]=mapUart.get(tmpS[0]);

                if(ct.isValid(tmpS[1])){
                    mTimeout=ct.string2Int(tmpS[1],0);
                }
//dbgMsg.save(TAG + ".openUsbSerial.12");
                //-------------------------------------
//                if (!mSerial.InitByBaudRate(mBaudrate, 700))
//                if (!mSerial.InitByPortSetting(mBaudrate,mDataBits,mStopBits,mParity,mFlowControl))
                if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
                    dbgMsg.save(TAG + ".openUsbSerial.mBaudratei=" + ct.int2String(mBaudratei, 0));
                }

                result=mSerial.InitByPortSetting(mBaudratei,mDataBits,mStopBits,mParity,mFlowControl);

                if((dbgEnArr[1] & _DBG_01_01_open_)>0) {
                    dbgMsg.save(TAG + ".openUsbSerial.result=" + ct.int2String(result, 0));
                }
                if (result==0)
                {
//dbgMsg.save(TAG + ".openUsbSerial.13");

                    if (!mSerial.PL2303Device_IsHasPermission()) {
//                    Toast.makeText(this, R.string.err_uart_no_permission, Toast.LENGTH_SHORT)
//                            .show();
                        return _ST_OPEN_FAIL_;
                    }

                    if (mSerial.PL2303Device_IsHasPermission() && (!mSerial.PL2303Device_IsSupportChip())) {
//                    Toast.makeText(this, R.string.err_uart_chip_not_support, Toast.LENGTH_SHORT)
//                            .show();
                        return _ST_CHIP_NOT_SUPPORT_;
                    }
                } else {
//                Toast.makeText(this, R.string.usb_connected, Toast.LENGTH_SHORT)
//                        .show();
                }

                return _ST_SUCCESS_;
            }//isConnected

//dbgMsg.save(TAG + ".openUsbSerial.14");
            return _ST_NOT_CONNECTED_;
        }

        //return _ST_CHIP_NOT_SUPPORT_;
    }//openUsbSerial

    //==============================================================================================
    public int close() {
        if(mSerial!=null) {
            mSerial.end();
            mSerial = null;
        }
        return 0;
    }

    //==============================================================================================
    public byte[] rx() {
        int len;
        byte[] rByte=new byte[0];
        byte[] rByte1;

        if(null==mSerial)
            return rByte;

        if(!mSerial.isConnected())
            return rByte;

        rByte1=new byte[1024];//max=4096

        len = mSerial.read(rByte1);

/*
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        if (len >0) {
            rByte=new byte[len];

            for(int i=0;i<len;i++){
                rByte[i]=rByte1[i];
            }
            if((dbgEnArr[1] & _DBG_01_02_rx_)>0) {
                dbgMsg.save(TAG + ".rx.len=" + ct.int2String(rByte.length, 0));
                dbgMsg.save(TAG + ".rx.inByte=" + ct.bytes2String(rByte));
            }
        }

        return rByte;
    }
    //==============================================================================================
    public int tx(String inS) {
        return tx(ct.string2Bytes(inS));
    }
    //==============================================================================================
    public int tx(byte[] inByte) {

        if(null==mSerial)
            return _ST_UST_HOST_NOT_SUPPORT_;

        if(!mSerial.isConnected())
            return _ST_UST_HOST_NOT_SUPPORT_;

//        if((dbgEnArr[1] & _DBG_01_04_tx_)>0) {
//            dbgMsg.save(TAG + ".write.len=" + ct.int2String(inByte.length, 0));
//            dbgMsg.save(TAG + ".write.inByte=" + ct.bytes2String(inByte));
//        }
        int res = mSerial.write(inByte, inByte.length);

//        if((dbgEnArr[1] & _DBG_01_04_tx_)>0) {
//            dbgMsg.save(TAG + ".write.res=" + ct.int2String(res, 0));
//        }

        if( res<0 ) {
            return _ST_WRITE_FAIL_;
        }
        return _ST_SUCCESS_;
    }
    //==============================================================================================
    public String getParaS() {
        String rS="";

        if(null==mSerial)
            return rS;

        if(!mSerial.isConnected())
            return rS;

        rS=mapUart.toString();

        return rS;
    }
    //==============================================================================================
    public String getParaS_1() {

        return getParaS_1(0x3F);
    }
    //==============================================================================================
    public String getParaS_1(int mask) {
        String[] tmpS=new String[2];
        String rS="";
//        dbgMsg.save(TAG + ".getParaS_1.0");
        if(null==mSerial)
            return rS;
        //dbgMsg.save(TAG + ".getParaS_1.1");
        if(!mSerial.isConnected())
            return rS;

        //-------------------------------------
        if((mask & _I_MAP_BaudRate_e_)>0){
            tmpS[0] = _S_MAP_BaudRate_e_;
            tmpS[1] = mapUart.get(tmpS[0]);

            if (ct.isValid(tmpS[1])) {
                rS += tmpS[1].substring(tmpS[1].lastIndexOf(".") + 1, tmpS[1].length());
                rS += "/";
                //dbgMsg.save(TAG + ".getParaS_1.rS.1="+rS);
            }

//dbgMsg.save(TAG + ".openUsbSerial.7");
        }
        //-------------------------------------
        if((mask & _I_MAP_DataBits_e_)>0) {
            tmpS[0] = _S_MAP_DataBits_e_;
            tmpS[1] = mapUart.get(tmpS[0]);

            if (ct.isValid(tmpS[1])) {
                rS += tmpS[1].substring(tmpS[1].lastIndexOf(".") + 1, tmpS[1].length());
                rS += "/";
                //          dbgMsg.save(TAG + ".getParaS_1.rS.2="+rS);
            }

//dbgMsg.save(TAG + ".openUsbSerial.8");
        }
        //-------------------------------------
        if((mask & _I_MAP_StopBits_e_)>0) {
            tmpS[0] = _S_MAP_StopBits_e_;
            tmpS[1] = mapUart.get(tmpS[0]);

            if (ct.isValid(tmpS[1])) {
                rS += tmpS[1].substring(tmpS[1].lastIndexOf(".") + 1, tmpS[1].length());
                rS += "/";
                //        dbgMsg.save(TAG + ".getParaS_1.rS.3="+rS);
            }
//dbgMsg.save(TAG + ".openUsbSerial.9");
        }
        //-------------------------------------
        if((mask & _I_MAP_Parity_e_)>0) {
            tmpS[0] = _S_MAP_Parity_e_;
            tmpS[1] = mapUart.get(tmpS[0]);

            if (ct.isValid(tmpS[1])) {
                rS += tmpS[1].substring(tmpS[1].lastIndexOf(".") + 1, tmpS[1].length());
                rS += "/";
//            dbgMsg.save(TAG + ".getParaS_1.rS.4="+rS);
            }

//dbgMsg.save(TAG + ".openUsbSerial.10");
        }
        //-------------------------------------
        if((mask & _I_MAP_FlowControl_e_)>0) {
            tmpS[0] = _S_MAP_FlowControl_e_;
            tmpS[1] = mapUart.get(tmpS[0]);

            if (ct.isValid(tmpS[1])) {
                rS += tmpS[1].substring(tmpS[1].lastIndexOf(".") + 1, tmpS[1].length());
                rS += "/";

//            dbgMsg.save(TAG + ".getParaS_1.rS.5="+rS);
            }

//dbgMsg.save(TAG + ".openUsbSerial.11");
        }
        //-------------------------------------
        if((mask & _I_MAP_Rx_Type_s_)>0) {
            tmpS[0] = _S_MAP_Rx_Type_s_;
            tmpS[1] = mapUart.get(tmpS[0]);

            if (ct.isValid(tmpS[1])) {
                rS += tmpS[1].substring(tmpS[1].lastIndexOf(".") + 1, tmpS[1].length());
                rS += "/";

//            dbgMsg.save(TAG + ".getParaS_1.rS.5="+rS);
            }

//dbgMsg.save(TAG + ".openUsbSerial.11");
        }
        //-------------------------------------

//        dbgMsg.save(TAG + ".getParaS_1.rS.f="+rS);
        return rS;
    }
    //==============================================================================================
    public String getIdS() {
        String rS="";

        if(null==mSerial)
            return rS;

        if(!mSerial.isConnected())
            return rS;

        rS=mSerial.PL2303Device_GetSerialNumber();

        return rS;
    }
    //==============================================================================================
}


