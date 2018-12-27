package com.example.johnny_wei.bleuart;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.johnny_wei.bleuart.driver_pl2303.PL2303Driver;
import com.example.johnny_wei.bleuart.util.commonutil;
import com.example.johnny_wei.bleuart.xmlpull.BLE_testItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import static com.example.johnny_wei.bleuart.common.pUart._ST_SUCCESS_;


public class AllTestclass extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    Context mcontext;

    LinkedList<BDAutoTest> AutoTestList;

    //UUID
    final String SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb";
    final String NOTIFY_CHARA_UUID = "0000fff1-0000-1000-8000-00805f9b34fb";
    final String WRITE_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";
    final String WRITE_CHARA_UUID = "0000fff2-0000-1000-8000-00805f9b34fb";

    final String BATTERY_SERVICE_UUID = "0000180f-0000-1000-8000-00805f9b34fb";
    final String BATTERY_LEVEL_CHARA_UUID = "00002a19-0000-1000-8000-00805f9b34fb";

    //mode
    final int SPI_MODE = 0;
    final int UART_MODE = 1;
    final int AIR_UART_MODE = 2;
    int TESTMODE;

    final String FAIL = "FAIL";

    final long PASS = 0;
    private String Current_TEST_NAME = "";
    private String BD_TEST_INTV_WRITE = "BD_TEST_INTV_WRITE";
    private String BD_TEST_NAME_WRITE = "BD_TEST_NAME_WRITE";
    private String BD_TEST_ADDR_WRITE = "BD_TEST_ADDR_WRITE";

    private String BD_TEST_ADVDATA_WRITE = "BD_TEST_ADVDATA_WRITE";
    private String BD_TEST_WHITELIST_WRITE = "BD_TEST_WHITELIST_WRITE";
    private String BD_TEST_TXPOWER_WRITE = "BD_TEST_TXPOWER_WRITE";
    private String BD_TEST_BATTERYLEVEL_WRITE = "BD_TEST_BATTERYLEVEL_WRITE";
    private String BD_TEST_INTVLATENCY_WRITE = "BD_TEST_INTVLATENCY_WRITE";
    private String BD_TEST_ADVDATA2_WRITE = "BD_TEST_ADVDATA2_WRITE";
    private String BD_TEST_SCANRESDATA_WRITE = "BD_TEST_SCANRESDATA_WRITE";
    private String BD_TEST_DISCONN_WRITE = "BD_TEST_DISCONN_WRITE";
    private String BD_TEST_DATA_PAYLOAD_WRITE = "BD_TEST_DATA_PAYLOAD_WRITE";
    private String BD_TEST_PHY = "BD_TEST_PHY";
    private String BD_TEST_CHANGE_BAUDRATE = "BD_TEST_CHANGE_BAUDRATE";

    private String BD_Loopback = "BD_Loopback";

    //air--------------------------------------------------------
    private String BD_AIR_TEST_NAME_WRITE = "BD_AIR_TEST_NAME_WRITE";

    final int RETRYMAX = 3;
    private boolean mScanning;
    private boolean findBleData = false;
    private String ManufacturerData = "";
    private String adv_datawrite2str = "";
    private final String PHY_FUNC_ADDR = "94042000";

    //ble
    String defaultBleAddr = "01:23:45:67:89:0A";
    String defaultBleAddr2018 = "20:18:11:30:18:34";
    String defaultBleAddr2018_10_02 = "20:18:99:99:99:99";
    String curBleAddr = defaultBleAddr2018;

    //test
//    String curBleAddr = defaultBleAddr2018;
//    String defaultBleAddr = curBleAddr;

    private static final long SCAN_PERIOD = 5000;
    private Handler m_userHandler;
    private BluetoothAdapter mBluetoothAdapter;

    //adv intv test
    long previous_time = 0;
    long current_time = 0;
    List<Long> advIntvList = new ArrayList<Long>();

    //tx power
    List<Integer> rssiList = new ArrayList<Integer>();

    //baud rate
    LinkedList<Integer> BaudRateList;
    //baud rate
    private static final int mBaudratei2400 = 2400;
    private static final int mBaudratei9600 = 9600;
    private static final int mBaudratei14400 = 14400;
    private static final int mBaudratei19200 = 19200;
    private static final int mBaudratei38400 = 38400;
    private static final int mBaudratei57600 = 57600;
    private static final int mBaudratei115200 = 115200;
    private static final int mBaudratei256000 = 256000;//not support
    private static int mCurrentBaudrate = mBaudratei115200;
    //uart setting
    PL2303Driver mSerial;
    private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B115200;
    private PL2303Driver.DataBits mDataBits = PL2303Driver.DataBits.D8;
    private PL2303Driver.StopBits mStopBits = PL2303Driver.StopBits.S1;
    private PL2303Driver.Parity mParity = PL2303Driver.Parity.NONE;
    private PL2303Driver.FlowControl mFlowControl = PL2303Driver.FlowControl.OFF;


    static bleService bleServiceInstance;

    AllTestclass(Context context) {
        mcontext = context;

        init();
    }

    private void init() {
        AutoTestList = new LinkedList<BDAutoTest>();
        BaudRateList = new LinkedList<>();
        HandlerThread ht_thread = new HandlerThread("name");
        ht_thread.start();
        m_userHandler = new Handler(ht_thread.getLooper());
        mBluetoothAdapter = ((MainActivity) mcontext).GetBluetoothAdapter();
        TESTMODE = ((MainActivity) mcontext).GetTestMode();
    }


    private void scanLeDevice(final boolean enable, final long scanTime) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            m_userHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, scanTime);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi,
                                     byte[] scanRecord) {
                    checkAdvData(device, rssi, scanRecord);
                }
            };

    public void checkAdvData(final BluetoothDevice device, final int rssi,
                             byte[] scanRecord) {

        String name = device.getName();
        //Log.d(TAG, "device name:" + device.getName() + ", device address:" + device.getAddress() + Integer.toString(rssi));
        //jjj for test
        if (name != null && device.getAddress().equals("20:18:10:31:17:56")) {
            parseAdv(scanRecord);
        }


        if (Current_TEST_NAME.equals(BD_TEST_ADDR_WRITE) &&
                curBleAddr.equals(device.getAddress())) {
            setFindBleDataFlag();
        }


        //the last test item of test_addr will set default
        if (name != null && device.getAddress().equals(defaultBleAddr)) {
            //write2MainUI("scan AD Data");
            //Log.d(TAG, "device name:" + device.getName() + ", device address:" + device.getAddress() + Integer.toString(rssi));
            //parseAdv(scanRecord);
            //name write check
            if (Current_TEST_NAME.equals(BD_TEST_NAME_WRITE)) {
                if (getBleData().equals(name)) {
                    setFindBleDataFlag();
                }
            }
            //manufacture data write check
            else if (Current_TEST_NAME.equals(BD_TEST_ADVDATA_WRITE)) {
                //parse adv data
                parseAdv(scanRecord);
                if (getBleData().equals(getManufacturerData())) {
                    setFindBleDataFlag();
                }
            } else if (Current_TEST_NAME.equals(BD_TEST_TXPOWER_WRITE)) {
                //add rssi to list for calculate average
                addRssi(rssi);
            } else if (Current_TEST_NAME.equals(BD_TEST_ADVDATA2_WRITE) ||
                    Current_TEST_NAME.equals(BD_TEST_SCANRESDATA_WRITE)) {
                //set adv packet
                commonutil.wdbgLogcat(TAG, 0, "ad total data :" + byteArrayToHex(scanRecord));
                if (byteArrayToHex(scanRecord).contains(getBleData())) {
                    setFindBleDataFlag();
                }
            } else if (Current_TEST_NAME.equals(BD_TEST_INTV_WRITE)) {
                //add interval for calculating
                AddAdvIntvTime();
                Log.d(TAG, "device name:" + device.getName() + ", device address:" + device.getAddress() + Integer.toString(rssi));
            }
        }
    }

    private String GetTestxml(int mode) {
        String filename = "bd_autotest.xml";
        if (SPI_MODE == mode) {
            filename = "bd_autotest.xml";
        } else if (UART_MODE == mode) {
            filename = "bd_uart_autotest.xml";
        }

        return filename;
    }

    private void getTestlist(String testname) {
        //entry of test mode
        TESTMODE = ((MainActivity) mcontext).GetTestMode();
        try {
            AutoTestList.clear();
            InputStream input = mcontext.getAssets().open(GetTestxml(TESTMODE));
            List<BDAutoTest> list = ParserAutotest.getAutoTestItems(input);
            for (BDAutoTest item : list) {
                if (item.gettestName().equals(testname)) {
                    AutoTestList.add(item);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (AutoTestList.size() == 0) {
            commonutil.wdbgLogcat(TAG, 2, "empty test list !!!");
        }
    }

    public void BD_IntvWrite() {
        Current_TEST_NAME = BD_TEST_INTV_WRITE;
        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        ((MainActivity) mcontext).writeStatus(Current_TEST_NAME);
        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            setDefaultAdvIntv();

            BDAutoTest testitem = AutoTestList.poll();//get test item

            if (!test_IntvWrite(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wdbgLogcat(TAG, 2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLogcat(TAG, 0, Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }

        }

        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);

    }

    public boolean test_IntvWrite(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        //adv interval
        long advIntv = Long.parseLong(testitem.getdataStr());
        commonutil.wdbgLogcat(TAG, 0, "*****" + ",advIntv is " + advIntv);

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {
            // pass if adIntv is 0
            if (advIntv == PASS) {
                return true;
            } else {
                //make sure f/w really changes ad interval
                write2MainUI("wait AD change");
                long sleep_time = advIntv * 5;
                SystemClock.sleep(sleep_time);
            }

            long scanTime = advIntv * 10;
            write2MainUI("start scan AD interval:" + scanTime + " ms");
            commonutil.wdbgLogcat(TAG, 0, "start scan AD interval:" + scanTime + " ms");
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);
            //check average adv interval

            if (!checkAdvAvgIntv(advIntv)) {
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;
    }

    private void setDefaultAdvIntv() {
        current_time = 0;
        previous_time = 0;
        advIntvList.clear();
    }


    private void AddAdvIntvTime() {
        if (current_time == 0) {
            current_time = System.currentTimeMillis();
            previous_time = current_time;
        } else {
            current_time = System.currentTimeMillis();
            long diff_time = current_time - previous_time;
            advIntvList.add(diff_time);
            //Log.d(TAG, "diff time:" + Long.toString(diff_time));
            previous_time = current_time;
        }
    }

    //if avg value < msec than return false
    private boolean checkAdvAvgIntv(long msec) {
        if (advIntvList.size() == 0) {
            commonutil.wdbgLogcat(TAG, 2, "list size is 0");
            return false;
        }
        long sum = 0;
        for (long x : advIntvList) {
            //Log.d(TAG, Long.toString(x));
            sum = sum + x;
        }
        long avgAdvIntv = sum / (advIntvList.size());

        write2_MainUI_Log(0, "average adv Interval:" + avgAdvIntv);

        //if avg value < msec
        if (avgAdvIntv < (msec * 0.8)) {
            String ErrStr = avgAdvIntv + " should be : " + msec + "ms";
            write2_MainUI_Log(2, ErrStr);
            return false;
        }

        return true;
    }


    public void BD_NameWrite() {
        Current_TEST_NAME = BD_TEST_NAME_WRITE;
        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            setDefaultBleData();
            BDAutoTest testitem = AutoTestList.getFirst();//get test item

            if (!test_NameWrite(testitem)) {
                write2MainUI(Current_TEST_NAME + " test fail");
                commonutil.wdbgLogcat(TAG, 2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                write2MainUI(Current_TEST_NAME + " test success");
                commonutil.wdbgLogcat(TAG, 0, Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
            AutoTestList.removeFirst();//remove test item
        }

        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_NameWrite(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //set ble name from config
        setBleData(testitem.getdataStr());

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {

            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find ble name:" + testitem.getdataStr());
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;
    }


    private String ble_data_str;

    public void setBleData(String name) {
        ble_data_str = name;
    }

    public String getBleData() {
        return ble_data_str;
    }

    public void setDefaultBleData() {
        findBleData = false;
        ble_data_str = "";
        ManufacturerData = "";
        adv_datawrite2str = "";
    }

    public void setFindBleDataFlag() {
        findBleData = true;
    }

    public boolean getFindBleDataFlag() {
        return findBleData;
    }

    public void BD_AddrWrite() {
        Current_TEST_NAME = BD_TEST_ADDR_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            setDefaultBleData();

            BDAutoTest testitem = AutoTestList.poll();

            if (!test_AddrWrite(testitem)) {
                write2MainUI(Current_TEST_NAME + " test fail");
                commonutil.wdbgLogcat(TAG, 2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                write2MainUI(Current_TEST_NAME + " test success");
                commonutil.wdbgLogcat(TAG, 0, Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }

        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_AddrWrite(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //set ble name from config
        setBleData(testitem.getdataStr());


        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {

            curBleAddr = testitem.getdataStr();

            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find ble addr:" + curBleAddr);
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;
    }

    int advWriteTestOnlyOnce = 0;

    public void BD_AdvDataWrite() {
        if (advWriteTestOnlyOnce == 0) {
            Current_TEST_NAME = BD_TEST_ADVDATA_WRITE;

            getTestlist(Current_TEST_NAME);

            commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

            while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
                setDefaultBleData();

                BDAutoTest testitem = AutoTestList.poll();

                if (!test_AdvDataWrite(testitem)) {
                    ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                    commonutil.wErrLog(Current_TEST_NAME + " test fail");
                    Log.e(TAG, Current_TEST_NAME + " test fail");
                    ((MainActivity) mcontext).updateFailCnt();
                } else {
                    ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                    commonutil.wdbgLog(Current_TEST_NAME + " test success");
                    ((MainActivity) mcontext).updatesuccesstv();
                }
            }

            commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
        }

        //only test once because advdatawirte function will not work after we call advdatawrite2
        advWriteTestOnlyOnce = 1;
    }

    public boolean test_AdvDataWrite(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //set ble name from config
        setBleData(testitem.getdataStr());

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {

            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find ble manufacture data:" + testitem.getdataStr());
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;
    }

    public void BD_WhiteListWrite() {
        Current_TEST_NAME = BD_TEST_WHITELIST_WRITE;
        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            setDefaultBleData();

            BDAutoTest testitem = AutoTestList.poll();

            if (!test_WhiteList(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }

            clearRxbuffer();


        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_WhiteList(BDAutoTest testitem) {

        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //set ble name from config
        setBleData(testitem.getdataStr());

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                write2_MainUI_Log(1, "retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                Log.e(TAG, "bleServiceInstance is null, ble service not start yet ?");
                commonutil.wErrLog("bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //if already connected, disconnect, android will auto reconnect if whitelist change to 0
            if (bleServiceInstance.isConnected()) {
                commonutil.wdbgLog("disconnect exist connection before test");
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }

            //connect
            if (!bleServiceInstance.connect(curBleAddr)) {
                Log.e(TAG, "connect fail");
                return false;
            }

            //wait for connection
            SystemClock.sleep(5000);

            //all external devices can connect to the BLE device
            if (testitem.getdataStr().equals("ALLPASS")) {
                int idx = -1;
                while (idx < 5) {
                    idx++;
                    if (bleServiceInstance.isConnected()) {
                        break;
                    }
                    if (idx <= 2) {
                        //if fail reconnect
                        Log.w(TAG, "ble not connected," + "retry:" + Integer.toString(idx));
                        commonutil.wdbgLog("ble not connected," + "retry:" + Integer.toString(idx));
                        bleServiceInstance.connect(curBleAddr);
                    } else if (idx == 3) {
                        Log.w(TAG, "reboot bluetooth" + "retry:" + Integer.toString(idx));
                        commonutil.wdbgLog("reboot bluetooth" + "retry:" + Integer.toString(idx));
                        bleServiceInstance.rebootBluetooth();
                        bleServiceInstance.connect(curBleAddr);
                    } else if (idx == 4) {
                        Log.e(TAG, "retry fail ");
                        commonutil.wErrLog("retry fail ");
                        return false;
                    }
                    SystemClock.sleep(5000);
                }
            } else {
                //only specific device can connect, could not connect is right behavior here
                int idx = -1;
                while (idx < 5) {
                    idx++;
                    if (bleServiceInstance.isConnected()) {
                        commonutil.wErrLog("connect to ble device is fail here !!!");
                        break;
                    }
                    if (idx <= 2) {
                        //if fail reconnect
                        Log.w(TAG, "ble not connected," + "retry:" + Integer.toString(idx));
                        commonutil.wdbgLog("ble not connected," + "retry:" + Integer.toString(idx));
                        bleServiceInstance.connect(curBleAddr);
                    } else if (idx == 3) {
                        Log.d(TAG, "could not connect is right behavior here");
                        commonutil.wdbgLog("could not connect is right behavior here");
                        break;//break here because android will try reconnect if we do not diconnect
                    }
                    SystemClock.sleep(5000);
                }
            }

            //disconnect, sleep few secs for decreasing fail reconnect rate
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);
        }

        return true;
    }

    public void BD_TxPowerWrite() {
        Current_TEST_NAME = BD_TEST_TXPOWER_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            setDefaultBleData();

            BDAutoTest testitem = AutoTestList.poll();

            if (!test_TxPowerWrite(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    List<Integer> db_List = new ArrayList<Integer>();

    public boolean test_TxPowerWrite(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("------" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        db_List.clear();

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                //3 mean first cmd set has finished, then calculate power
                //1 mean second cmd set has finished, then calculate power
                if (testitem.cmdSize() == 3 || testitem.cmdSize() == 1) {
                    //=====================BLE======================
                    if (commonutil.config.BLE) {

                        long scanTime = 5000;
                        //start scan
                        scanLeDevice(true);
                        //wait finish
                        SystemClock.sleep(scanTime);
                        //stop scan
                        scanLeDevice(false);
                        //add to list for comparing
                        db_List.add(getAvgRssi());
                        clearRssiList();

                        SystemClock.sleep(500);
                    }
                }

                testitem.popCmd();
            }
        }

        //compare two power level
        if (db_List.size() == 2) {
            if (db_List.get(0) > db_List.get(1)) {
                commonutil.wErrLog(db_List.get(0).toString() + " should not bigger than" + db_List.get(1));
                Log.d(TAG, db_List.get(0).toString() + " should not bigger than" + db_List.get(1));
                return false;
            }
        } else {
            commonutil.wErrLog("invalid size:" + db_List.size());
            Log.d(TAG, "invalid size:" + db_List.size());
            return false;
        }

        return true;
    }

    public void BD_BatteryLevelWrite() {
        Current_TEST_NAME = BD_TEST_BATTERYLEVEL_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            setDefaultBleData();

            BDAutoTest testitem = AutoTestList.poll();

            if (!test_BatteryWrite(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);

        //disconnect, sleep few secs for decreasing fail reconnect rate
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);
    }

    public boolean test_BatteryWrite(BDAutoTest testitem) {
        String battery_data = testitem.getdataStr();
        commonutil.wdbgLogcat(TAG, 0, "------" + testitem.gettestName() + ",battery_data is " + battery_data);

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            if (!bleServiceInstance.BT_Enable()) {
                Log.e(TAG, "bluetooth not enable !");
                commonutil.wdbgLogcat(TAG, 2, "bluetooth not enable !");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //clear
            bleServiceInstance.clearCharReadstr();
            //notify
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);

            SystemClock.sleep(1000);

            clearRxbuffer();
        }

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                write2_MainUI_Log(1, "retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                //=====================BLE======================
                bleServiceInstance.readCharacteristic2(BATTERY_SERVICE_UUID, BATTERY_LEVEL_CHARA_UUID);
                SystemClock.sleep(1000);

                String revCharReadStr = bleServiceInstance.getCharReadstr();
                if (battery_data.equals(revCharReadStr)) {
                    ((MainActivity) mcontext).writeLog2View("OK evt ,revEvt:" + revCharReadStr);
                    commonutil.wdbgLogcat(TAG, 0, "OK evt ,revEvt:" + revCharReadStr);
                } else {
                    ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revCharReadStr);
                    commonutil.wErrLog("fail evt ,revEvt:" + revCharReadStr);
                    return false;
                }

                testitem.popCmd();
            }
        }

        return true;
    }

    public void BD_AdvDataWrite2() {
        Current_TEST_NAME = BD_TEST_ADVDATA2_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);
        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            //reset to default
            setDefaultBleData();
            BDAutoTest testitem = AutoTestList.poll();

            if (!test_AdvDataWrite2(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_AdvDataWrite2(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //set ble name from config
        setBleData(testitem.getdataStr());

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {

            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find adv data:" + testitem.getdataStr());
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;

    }


    public void BD_ScanResDataWrite() {
        Current_TEST_NAME = BD_TEST_SCANRESDATA_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            //reset to default
            setDefaultBleData();
            BDAutoTest testitem = AutoTestList.poll();

            //use test_AdvDataWrite2 because the behavior is same
            if (!test_AdvDataWrite2(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public void BD_DisconnectWrite() {
        Current_TEST_NAME = BD_TEST_DISCONN_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            //reset to default
            setDefaultBleData();
            BDAutoTest testitem = AutoTestList.poll();

            if (!test_DisconnectWrite(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }

        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);

        //check connection status
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null != bleServiceInstance) {
                if (bleServiceInstance.isConnected()) {
                    commonutil.wdbgLogcat(TAG, 1, "disconnect because Current_TEST_NAME not success ");
                    bleServiceInstance.disconnect();
                    SystemClock.sleep(3000);
                }
            }
        }

        //clear rx
        clearRxbuffer();
    }

    public boolean test_DisconnectWrite(BDAutoTest testitem) {
        //1. connection
        //2. disconnection by uart cmd
        //3. check connecition status
        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                Log.e(TAG, "bleServiceInstance is null, ble service not start yet ?");
                commonutil.wErrLog("bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //if already connected, disconnect, disconnect
            if (bleServiceInstance.isConnected()) {
                commonutil.wdbgLog("disconnect exist connection before test");
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }

            //connect

            if (!bleServiceInstance.connect(curBleAddr)) {
                Log.e(TAG, "connect fail");
                return false;
            }

            //wait for connection
            SystemClock.sleep(5000);

            int idx = -1;
            while (idx < 5) {
                idx++;
                if (bleServiceInstance.isConnected()) {
                    break;
                }
                if (idx <= 2) {
                    //if fail reconnect
                    Log.w(TAG, "ble not connected," + "retry:" + Integer.toString(idx));
                    commonutil.wdbgLog("ble not connected," + "retry:" + Integer.toString(idx));
                    bleServiceInstance.connect(curBleAddr);
                } else if (idx == 3) {
                    Log.w(TAG, "reboot bluetooth" + "retry:" + Integer.toString(idx));
                    commonutil.wdbgLog("reboot bluetooth" + "retry:" + Integer.toString(idx));
                    bleServiceInstance.rebootBluetooth();
                    bleServiceInstance.connect(curBleAddr);
                } else if (idx == 4) {
                    Log.e(TAG, "retry fail ");
                    commonutil.wErrLog("retry fail ");
                    return false;
                }
                SystemClock.sleep(5000);
            }
        }

        //=====================UART======================
        //clear connection event
        clearRxbuffer();

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    //TODO:check
                    //wait 1 sec for android disconnection event, 500ms may too short ?
                    SystemClock.sleep(1000);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //there is no need to retry since it has already disconnected
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);

                    //if rev byte loss first byte, the rev byte will be 265F0026FF10 in SPI,5F0026FF10 in UART
                    if ((revEvt.contains("265F00") && revEvt.contains("26FF10")) ||
                            (revEvt.contains("5F00") && revEvt.contains("26FF10"))) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }
        //=====================BLE======================
        if (commonutil.config.BLE) {
            //if connected, return fail
            if (bleServiceInstance.isConnected()) {
                commonutil.wdbgLogcat(TAG, 2, "fail, connection is still exist");
                return false;
            } else {
                bleServiceInstance.resetConnectState();
            }
        }

        return true;
    }


    public void BD_IntvLatencyWrite() {
        Current_TEST_NAME = BD_TEST_INTVLATENCY_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {

            BDAutoTest testitem = AutoTestList.poll();

            if (!test_IntvLatency(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }
        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);

        //disconnect after test
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            } else {
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }
        }

        //clear rx
        clearRxbuffer();
    }

    public boolean test_IntvLatency(BDAutoTest testitem) {
        commonutil.wdbgLogcat(TAG, 0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }
        }

        //=====================UART======================

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            //clear connection evt if exist
            clearRxbuffer();
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);

                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    //sleep longer to make sure interval really change
                    SystemClock.sleep(5000);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLogcat(TAG, 1, "retry " + Integer.toString(retryNum) + ", wrong rev:" + commonutil.bytes2String(rev));
                                //sleep longer to make sure interval really change
                                SystemClock.sleep(5000);
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        commonutil.wdbgLogcat(TAG, 2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    //different os may choose different interval
                    if (revEvt.contains("213004")) {
                        ((MainActivity) mcontext).writeLog2View("OK evt ,choose revEvt:" + revEvt);
                        commonutil.wdbgLogcat(TAG, 0, "OK evt ,choose revEvt:" + revEvt);
                    } else if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                        commonutil.wdbgLogcat(TAG, 0, "OK evt ,revEvt:" + revEvt);
                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        commonutil.wdbgLogcat(TAG, 2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }

                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    commonutil.wdbgLogcat(TAG, 2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        return true;
    }

    public void BD_DataPayloadWrite() {
        Current_TEST_NAME = BD_TEST_DATA_PAYLOAD_WRITE;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);
        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {
            //reset to default
            setDefaultBleData();
            BDAutoTest testitem = AutoTestList.poll();

            if (!test_DataPayload(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wErrLog(Current_TEST_NAME + " test fail");
                Log.e(TAG, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }

        }

        //disconnect after test
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            } else {
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }
        }

        //clear event in buffer
        if (commonutil.config.UART) {
            clearRxbuffer();
        }


        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_DataPayload(BDAutoTest testitem) {
        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //clear
            bleServiceInstance.clearCharChagedstr();

            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        }
        //=====================UART=====================
        //clear connection event
        clearRxbuffer();
        int retryNum = 0;
        int retryMax = 3;

        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ", evt:" + evt);

                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);

                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(evt)) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);

                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }
                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }

        //=====================check data=====================

        SystemClock.sleep(3000);
        //check data
        String charChagedstr = bleServiceInstance.getCharChagedstr();
        Log.d(TAG, "ble chara changed got:" + charChagedstr);
        if (!charChagedstr.equals(testitem.getdataStr())) {
            commonutil.wdbgLogcat(TAG, 2, "data not match:" + charChagedstr);
            return false;
        }

        return true;
    }


    public void BD_phy() {
        Current_TEST_NAME = BD_TEST_PHY;

        getTestlist(Current_TEST_NAME);

        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        while (AutoTestList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {

            BDAutoTest testitem = AutoTestList.poll();

            if (!test_phy(testitem)) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wdbgLogcat(TAG, 2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
        }

        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_phy(BDAutoTest testitem) {
        //=====================UART======================

        Log.d(TAG, "--test name--:" + testitem.gettestName());
        Log.d(TAG, "--data--:" + testitem.getdataStr());
        commonutil.wdbgLog("*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        int retryNum = 0;
        int retryMax = 3;
        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                ((MainActivity) mcontext).writeLog2View("cmd:" + writeSubStrCmd[0]);
                commonutil.wdbgLog("cmd:" + writeSubStrCmd[0]);
                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }

                    //uart rx <--- BLE
                    SystemClock.sleep(500);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //check if uart rx recv incorrect size
                        if (!checkRevLen(rev, TESTMODE)) {
                            //work around, try three times if rev length not correct
                            if (retryNum < retryMax) {
                                commonutil.wdbgLog("retry " + Integer.toString(retryNum));
                                retryNum++;
                                continue;
                            } else {
                                String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                                write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                                return false;
                            }
                        }
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    commonutil.wdbgLogcat(TAG, 0, "revEvt:" + revEvt);
                    ((MainActivity) mcontext).writeLog2View("revEvt:" + revEvt);

                    //read phy addr start with 0x57
                    if (revEvt.startsWith("6C57") || revEvt.startsWith("57")) {
                        if (checkSubstrOfReadPhy(revEvt, evt, TESTMODE)) {
                            String strsubaddr = GetReadPhyAddr(revEvt, TESTMODE);
                            String strsubdata = GetReadPhyData(revEvt, TESTMODE);
                            commonutil.wdbgLogcat(TAG, 0, "addr:" + strsubaddr + ",data:" + strsubdata);
                            ((MainActivity) mcontext).writeLog2View("addr:" + strsubaddr + ",data:" + strsubdata);
                            if (strsubaddr.equals(PHY_FUNC_ADDR)) {
                                if (!checkFunctioDatanbit(strsubdata)) {
                                    commonutil.wdbgLogcat(TAG, 2, "fail Function revEvt:" + strsubaddr);
                                    ((MainActivity) mcontext).writeLog2View("fail Function revEvt:" + strsubaddr);
                                    return false;
                                }
                            }
                        }
                    } else if (evt.equals("FF")) {
                        // note: it is evt not revEvt,just for passing to next cmd
                        commonutil.wdbgLogcat(TAG, 0, "FF pass");
                    } else {
                        ((MainActivity) mcontext).writeLog2View("fail evt ,revEvt:" + revEvt);
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }
                } else {
                    ((MainActivity) mcontext).writeLog2View("fail tx ,cmd:" + writeSubStrCmd[0]);
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }
        return true;
    }

    public void BD_changeBaudRate() {
        //TODO:open if 7611 has this feature
        if (TESTMODE == SPI_MODE) {
            //7611 can't modify baud rate, so skip
            return;
        }

        if (!commonutil.config.UART) {
            ((MainActivity) mcontext).writeLog2View("UART config is not enable !");
            commonutil.wdbgLogcat(TAG, 2, "UART config is not enable !");
            return;
        }

        Current_TEST_NAME = BD_TEST_CHANGE_BAUDRATE;
        getTestlist(Current_TEST_NAME);
        commonutil.wdbgLogcat(TAG, 0, "S========================" + Current_TEST_NAME);

        setupBRList();
        while (BaudRateList.size() > 0 && ((MainActivity) mcontext).isThreadRunning()) {

            if (!test_changeBaudRate()) {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test fail");
                commonutil.wdbgLogcat(TAG, 2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                ((MainActivity) mcontext).writeLog2View(Current_TEST_NAME + " test success");
                commonutil.wdbgLog(Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }
            BaudRateList.removeFirst();
        }

        commonutil.wdbgLogcat(TAG, 0, "E========================" + Current_TEST_NAME);
    }

    public boolean test_changeBaudRate() {
        /*
        1. write baud rate
        2. update baud rate
        3. set buad rate
        4. read buad rate value 3 time to check success or note
        */
        int newBaudRate = BaudRateList.getFirst();
        commonutil.wdbgLogcat(TAG, 0, "newBaudRate is" + Integer.toString(newBaudRate));
        ((MainActivity) mcontext).writeLog2View("newBaudRate is" + Integer.toString(newBaudRate));

        TESTMODE = ((MainActivity) mcontext).GetTestMode();

        /*baud rate write-------------------------------*/
        int result = -1;
        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(GetBRwriteCmd(newBaudRate, TESTMODE))) {
            commonutil.wdbgLogcat(TAG, 2, "write cmd fail:" + GetBRwriteCmd(newBaudRate, TESTMODE));
            ((MainActivity) mcontext).writeLog2View("write cmd fail:" + GetBRwriteCmd(newBaudRate, TESTMODE));
            return false;
        }

        SystemClock.sleep(500);

        //263200 baud rate write success
        ((MainActivity) mcontext).uart_rx();

        SystemClock.sleep(500);
        /*baud rate update------------------------------*/
        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(GetBRupdateCmd(newBaudRate, TESTMODE))) {
            commonutil.wdbgLogcat(TAG, 2, "update cmd fail:" + GetBRupdateCmd(newBaudRate, TESTMODE));
            ((MainActivity) mcontext).writeLog2View("update cmd fail:" + GetBRupdateCmd(newBaudRate, TESTMODE));
            return false;
        }

        SystemClock.sleep(500);

        byte[] rev = ((MainActivity) mcontext).uart_rx();
        String revEvt = commonutil.bytes2String(rev);
        if (rev.length != 0 && revEvt.equals(GetBRupdateFailEvt(newBaudRate, TESTMODE))) {
            //there is no need to change baud rate if we got update fail evt, if update really success,ble return new baud rate
            commonutil.wdbgLogcat(TAG, 2, "update evt fail:" + revEvt);
            ((MainActivity) mcontext).writeLog2View("update evt fail:" + revEvt);
            return false;
        }

        /*set buad rate setting------------------------------*/
        if (_ST_SUCCESS_ != ((MainActivity) mcontext).setBaudRate(newBaudRate)) {
            commonutil.wdbgLogcat(TAG, 2, "setBaudRate fail");
            ((MainActivity) mcontext).writeLog2View("setBaudRate fail");
            return false;
        }

        if (checkBaudRate(mCurrentBaudrate, newBaudRate)) {
            mCurrentBaudrate = newBaudRate;
        } else {
            //TODO:we need to change back to old baud rate?
            commonutil.wdbgLogcat(TAG, 2, "change baud rate fail");
            ((MainActivity) mcontext).writeLog2View("change baud rate fail");
            return false;
        }

        return true;
    }

    private void clearRxbuffer() {
        SystemClock.sleep(500);
        //clear connection event,read uart rx buffer
        if (commonutil.config.UART) {
            //clear buffer,don't care the content of buffer
            ((MainActivity) mcontext).uart_rx();
        }
    }


    public boolean jjj() {
        //=====================connection======================
        bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
        if (null == bleServiceInstance) {
            commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            return false;
        }

        //check connect or not, retry connect 3 times if connection fail
        if (!bleServiceInstance.isConnected()) {
            if (!connectRetry()) {
                return false;
            }
        }

        //=====================enable notify======================
        bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        SystemClock.sleep(1000);

        //write data ten times
        //clear last event

        byte[] bytes = new byte[1];
        Long diffSum = 0L;
        int maxsize = 5;
        for (int i = 0; i < maxsize; i++) {
            bytes[0] = (byte) ((i + 10) & 0xff);
            Log.d("jjj", "send:" + String.format("%02x", bytes[0]));
            bleServiceInstance.clearCharChagedstr();
            bleServiceInstance.clearCbUnixTime();

            long startUnixTime = System.currentTimeMillis();
            ;
            bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
            SystemClock.sleep(1000);
            long endUnixTime = bleServiceInstance.GetchangeCbUnixTime();
            long diffTime = endUnixTime - startUnixTime;
            diffSum = diffSum + diffTime;
            String revEvt = bleServiceInstance.getCharChagedstr();
            Log.d("jjj", "startUnixTime :" + startUnixTime + ",endUnixTime:" + endUnixTime);
            Log.d("jjj", "diff time : +" + diffTime + ",revEvt:" + revEvt);
        }
        Log.d("jjj", "avg diff time:" + diffSum / maxsize);
        return true;
    }

    private boolean connectRetry() {
        int idx = -1;
        while (idx < 5) {
            idx++;
            if (bleServiceInstance.isConnected()) {
                break;
            }
            if (idx <= 2) {
                //if fail reconnect
                write2_MainUI_Log(1, "ble not connected," + "retry:" + Integer.toString(idx));
                bleServiceInstance.connect(curBleAddr);
            } else if (idx == 3) {
                write2_MainUI_Log(1, "reboot bluetooth" + "retry:" + Integer.toString(idx));
                bleServiceInstance.rebootBluetooth();
                bleServiceInstance.connect(curBleAddr);
            } else if (idx == 4) {
                write2_MainUI_Log(2, "retry fail ");
                return false;
            }
            SystemClock.sleep(5000);
        }
        return true;
    }

    //new version of invoke
    public int BD_AddrWrite(BLE_testItem testItem) {
        //todo:use tag name
        Current_TEST_NAME = BD_TEST_ADDR_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_AddrWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public boolean test_AddrWrite2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem, RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================BLE======================
        curBleAddr = testitem.getdataStr();

        long scanTime = 5000;
        //start scan
        scanLeDevice(true);
        //wait finish
        SystemClock.sleep(scanTime);
        //stop scan
        scanLeDevice(false);

        //check if the name we modify exist
        if (!getFindBleDataFlag()) {
            write2_MainUI_Log(2, "can not find ble addr:" + curBleAddr);
            return false;
        }

        SystemClock.sleep(500);


        return true;
    }

    public int BD_IntvWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_INTV_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_IntvWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public boolean test_IntvWrite2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());
        //adv interval
        long advIntv = Long.parseLong(testitem.getdataStr());
        commonutil.wdbgLogcat(TAG, 0, "*****" + ",advIntv is " + advIntv);

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {
            // pass if adIntv is 0
            if (advIntv == PASS) {
                return true;
            } else {
                //make sure f/w really changes ad interval
                write2_MainUI_Log(0, "wait AD change");
                long sleep_time = advIntv * 5;
                SystemClock.sleep(sleep_time);
            }

            long scanTime = advIntv * 10;
            write2_MainUI_Log(0, "start scan AD interval:" + scanTime + " ms");

            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);
            //check average adv interval

            if (!checkAdvAvgIntv(advIntv)) {
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;
    }

    public int BD_NameWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_NAME_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_NameWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public boolean test_NameWrite2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {

            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                write2_MainUI_Log(2, "can not find ble name:" + testitem.getdataStr());
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;
    }

    public int BD_AdvDataWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_ADVDATA_WRITE;

        if (advWriteTestOnlyOnce == 0) {
            write2_MainUI_Log(0, "S=====" + testItem.gettestName());

            if (!test_AdvDataWrite2(testItem)) {
                write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                write2_MainUI_Log(0, Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }

            write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        }


        //only test once because advdatawirte function will not work after we call advdatawrite2
        advWriteTestOnlyOnce = 1;
        return 0;
    }


    public boolean test_AdvDataWrite2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================BLE======================

        long scanTime = 5000;
        //start scan
        scanLeDevice(true);
        //wait finish
        SystemClock.sleep(scanTime);
        //stop scan
        scanLeDevice(false);

        //check if the name we modify exist
        if (!getFindBleDataFlag()) {
            commonutil.wErrLog("can not find ble manufacture data:" + testitem.getdataStr());
            return false;
        }

        SystemClock.sleep(500);

        return true;
    }

    public int BD_WhiteListWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_WHITELIST_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_WhiteList2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        clearRxbuffer();

        return 0;
    }

    public boolean test_WhiteList2(BLE_testItem testitem) {

        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //if already connected, disconnect, android will auto reconnect if whitelist change to 0
            if (bleServiceInstance.isConnected()) {
                commonutil.wdbgLogcat(TAG, 0, "disconnect exist connection before test");
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }

            //connect
            if (!bleServiceInstance.connect(curBleAddr)) {
                write2_MainUI_Log(2, "connect fail");
                return false;
            }

            //wait for connection
            SystemClock.sleep(5000);

            //all external devices can connect to the BLE device
            if (testitem.getdataStr().equals("ALLPASS")) {
                if (!connectRetry()) {
                    return false;
                }
            } else {
                //only specific device can connect, could not connect is right behavior here
                int idx = -1;
                while (idx < 5) {
                    idx++;
                    if (bleServiceInstance.isConnected()) {
                        write2_MainUI_Log(2, "connect to ble device is fail here !!!");
                        break;
                    }
                    if (idx <= 2) {
                        //if fail reconnect
                        write2_MainUI_Log(1, "ble not connected," + "retry:" + Integer.toString(idx));
                        bleServiceInstance.connect(curBleAddr);
                    } else if (idx == 3) {
                        write2_MainUI_Log(0, "could not connect is right behavior here");
                        break;//break here because android will try reconnect if we do not diconnect
                    }
                    SystemClock.sleep(5000);
                }
            }

            //disconnect, sleep few secs for decreasing fail reconnect rate
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);
        }

        return true;
    }

    public int BD_TxPowerWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_TXPOWER_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_TxPowerWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public boolean test_TxPowerWrite2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        db_List.clear();

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }

            //3 mean first cmd set has finished, then calculate power
            //1 mean second cmd set has finished, then calculate power
            if (testitem.cmdSize() == 3 || testitem.cmdSize() == 1) {
                //=====================BLE======================
                if (commonutil.config.BLE) {

                    long scanTime = 5000;
                    //start scan
                    scanLeDevice(true);
                    //wait finish
                    SystemClock.sleep(scanTime);
                    //stop scan
                    scanLeDevice(false);
                    //add to list for comparing
                    db_List.add(getAvgRssi());
                    clearRssiList();

                    SystemClock.sleep(500);
                }
            }

            testitem.popCmd();
        }

        //compare two power level
        if (db_List.size() == 2) {
            if (db_List.get(0) > db_List.get(1)) {
                write2_MainUI_Log(2, db_List.get(0).toString() + " should not bigger than" + db_List.get(1));
                return false;
            }
        } else {
            commonutil.wdbgLogcat(TAG, 2, "invalid size:" + db_List.size());
            return false;
        }

        return true;
    }

    public int BD_BatteryLevelWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_BATTERYLEVEL_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_BatteryWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        //disconnect, sleep few secs for decreasing fail reconnect rate
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        clearRxbuffer();

        return 0;
    }

    public boolean test_BatteryWrite2(BLE_testItem testitem) {
        String battery_data = testitem.getdataStr();

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + battery_data);
        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            if (!bleServiceInstance.BT_Enable()) {
                write2_MainUI_Log(2, "bluetooth not enable !");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //notify
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);

            SystemClock.sleep(1000);

            clearRxbuffer();
        }

        while (testitem.cmdSize() > 0) {
            //clear
            bleServiceInstance.clearCharReadstr();

            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            bleServiceInstance.readCharacteristic2(BATTERY_SERVICE_UUID, BATTERY_LEVEL_CHARA_UUID);
            SystemClock.sleep(1000);

            //check expected string is correct or not
            String revCharReadStr = bleServiceInstance.getCharReadstr();
            if (battery_data.equals(revCharReadStr)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revCharReadStr);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revCharReadStr);
                return false;
            }

            testitem.popCmd();
        }

        return true;
    }


    public int BD_IntvLatencyWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_INTVLATENCY_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_IntvLatency2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        //disconnect after test
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            } else {
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }
        }

        //clear rx
        clearRxbuffer();

        return 0;
    }

    public boolean test_IntvLatency2(BLE_testItem testitem) {
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }
        }

        //clear connection evt if exist
        clearRxbuffer();
        //=====================UART======================
        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem, RETRYMAX, 5000);

            //different os may choose different interval
            if (revEvt.contains("213004")) {
                write2_MainUI_Log(0,"OK evt ,OS choose revEvt:" + revEvt);
            } else if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        return true;
    }

    public int BD_AdvDataWrite2(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_ADVDATA2_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_AdvData2Write2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public boolean test_AdvData2Write2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());
        //reset to default
        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================BLE======================
        if (commonutil.config.BLE) {

            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                write2_MainUI_Log(2, "can not find adv data:" + testitem.getdataStr());
                return false;
            }

            SystemClock.sleep(500);
        }

        return true;

    }

    public int BD_ScanResDataWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_SCANRESDATA_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        //use test_AdvDataWrite2 because the behavior is same
        if (!test_AdvData2Write2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public int BD_DisconnectWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_DISCONN_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_DisconnectWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        //check connection status
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null != bleServiceInstance) {
                if (bleServiceInstance.isConnected()) {
                    commonutil.wdbgLogcat(TAG, 1, "disconnect because Current_TEST_NAME not success ");
                    bleServiceInstance.disconnect();
                    SystemClock.sleep(3000);
                }
            }
        }

        //clear rx
        clearRxbuffer();

        return 0;
    }

    public boolean test_DisconnectWrite2(BLE_testItem testitem) {
        //1. connection
        //2. disconnection by uart cmd
        //3. check connecition status
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //if already connected, disconnect, disconnect
            if (bleServiceInstance.isConnected()) {
                write2_MainUI_Log(0, "disconnect exist connection before test");
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }
        }

        //=====================UART======================
        //clear connection event
        clearRxbuffer();

        if (commonutil.config.UART) {
            while (testitem.cmdSize() > 0) {
                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

                //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
                String writeSubStrCmd[] = {""};
                if (cmd.contains("+")) {
                    writeSubStrCmd = cmd.split("\\+");
                } else {
                    writeSubStrCmd[0] = cmd;
                }

                //----uart tx-----> BLE
                SystemClock.sleep(500);
                if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                    //if tx length over 32 byte, transmit until finish
                    int i;
                    for (i = 1; i < writeSubStrCmd.length; i++) {
                        SystemClock.sleep(500);
                        if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                            write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                            return false;
                        }
                    }
                    //uart rx <--- BLE
                    //TODO:check
                    //wait 1 sec for android disconnection event, 500ms may too short ?
                    SystemClock.sleep(1000);
                    byte[] rev = ((MainActivity) mcontext).uart_rx();

                    if (rev.length != 0) {
                        //there is no need to retry since it has already disconnected
                    } else {
                        write2_MainUI_Log(2, "rev length is 0");
                    }

                    //check expected string is correct or not
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);

                    //if rev byte loss first byte, the rev byte will be 265F0026FF10 in SPI,5F0026FF10 in UART
                    if ((revEvt.contains("265F00") && revEvt.contains("26FF10")) ||
                            (revEvt.contains("5F00") && revEvt.contains("26FF10"))) {
                        write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                    } else {
                        write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                        return false;
                    }
                } else {
                    write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                    return false;
                }

                testitem.popCmd();
            }
        }
        //=====================BLE======================
        if (commonutil.config.BLE) {
            //if connected, return fail
            if (bleServiceInstance.isConnected()) {
                write2_MainUI_Log(2, "fail, connection is still exist");
                return false;
            } else {
                bleServiceInstance.resetConnectState();
            }
        }

        return true;
    }

    public int BD_DataPayloadWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_DATA_PAYLOAD_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_DataPayload2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        //disconnect after test
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            } else {
                bleServiceInstance.disconnect();
                SystemClock.sleep(3000);
            }
        }

        clearRxbuffer();

        return 0;
    }

    public boolean test_DataPayload2(BLE_testItem testitem) {
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        //=====================BLE======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        }
        //=====================UART=====================
        //clear connection event
        clearRxbuffer();

        //clear and incase of retry
        bleServiceInstance.clearCharChagedstr();

        //todo:handel retry
        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem,  RETRYMAX);

            //check expected string is correct or not
            if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }
            testitem.popCmd();
        }

        //=====================check data=====================

        SystemClock.sleep(3000);
        //check data
        String charChagedstr = bleServiceInstance.getCharChagedstr();
        Log.d(TAG, "ble chara changed got:" + charChagedstr);
        if (!charChagedstr.equals(testitem.getdataStr())) {
            write2_MainUI_Log(2,"data not match:" + charChagedstr);
            return false;
        }

        return true;
    }


    public int BD_phy(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_PHY;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_phy2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    public boolean test_phy2(BLE_testItem testitem) {
        //=====================UART======================

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is:" + testitem.getdataStr());

        while (testitem.cmdSize() > 0) {
            String evt = testitem.getCmd().split(":")[1];
            String revEvt = send_uart_Cmd(testitem, RETRYMAX);

            write2_MainUI_Log(0, "revEvt:" + revEvt);

            //read phy addr start with 0x57
            if (revEvt.startsWith("6C57") || revEvt.startsWith("57")) {
                if (checkSubstrOfReadPhy(revEvt, evt, TESTMODE)) {
                    String strsubaddr = GetReadPhyAddr(revEvt, TESTMODE);
                    String strsubdata = GetReadPhyData(revEvt, TESTMODE);
                    write2_MainUI_Log(0, "addr:" + strsubaddr + ",data:" + strsubdata);

                    if (strsubaddr.equals(PHY_FUNC_ADDR)) {
                        if (!checkFunctioDatanbit(strsubdata)) {
                            write2_MainUI_Log(2, "fail Function revEvt:" + strsubaddr);
                            return false;
                        }
                    }
                }
            } else if (evt.equals("FF")) {
                // note: it is evt not revEvt,just for passing to next cmd
                commonutil.wdbgLogcat(TAG, 0, "FF pass");
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }

            testitem.popCmd();
        }

        return true;
    }

    public int BD_changeBaudRate(BLE_testItem testItem) {
        //TODO:open if 7611 has this feature
        if (((MainActivity) mcontext).GetTestMode() == SPI_MODE) {
            write2_MainUI_Log(0, "7611 not support changeBaudRate yet");
            //7611 can't modify baud rate, so skip
            return 0;
        }

        Current_TEST_NAME = BD_TEST_CHANGE_BAUDRATE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());
        setupBRList();
        if (!test_changeBaudRate()) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());
        return 0;
    }

    //air
    public int AIR_BD_AddrWrite(BLE_testItem testItem) {
        //todo:use tag name
        Current_TEST_NAME = BD_TEST_ADDR_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_addrwrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_addrwrite2(BLE_testItem testitem) {
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());
        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        //=====================connection======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //=====================enable notify======================
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
            SystemClock.sleep(2000);

            while (testitem.cmdSize() > 0) {
                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + "evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not
                String revEvt = bleServiceInstance.getCharChagedstr();
                if (revEvt.equals(evt)) {
                    write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                } else {
                    write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                    return false;
                }

                testitem.popCmd();
            }

            //---------------------disconnect
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);

            //set new addr
            curBleAddr = testitem.getdataStr();

            //---------------------scan
            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //-------------------stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                write2_MainUI_Log(2, "can not find ble name:" + testitem.getdataStr());
                return false;
            }
        }
        return true;
    }

    public int AIR_BD_IntvWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_INTV_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_advIntvwrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_advIntvwrite2(BLE_testItem testitem) {
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //=====================connection======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //=====================enable notify======================
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
            SystemClock.sleep(2000);

            while (testitem.cmdSize() > 0) {
                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + "evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not
                String revEvt = bleServiceInstance.getCharChagedstr();
                if (revEvt.equals(evt)) {
                    write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                } else {
                    write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                    return false;
                }

                testitem.popCmd();
            }

            //---------------------disconnect
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);

            long advIntvData = Long.parseLong(testitem.getdataStr());

            //only test driver, bypass adv interval test
            if (advIntvData == PASS) {//PASS is 0
                return true;
            }

            long scanTime = advIntvData * 10;
            setDefaultAdvIntv();
            write2_MainUI_Log(0, "start scan AD interval:" + scanTime + " ms");

            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //stop scan
            scanLeDevice(false);

            //check average adv interval
            if (!checkAdvAvgIntv(advIntvData)) {
                return false;
            }

        }
        return true;
    }


    public int AIR_BD_NameWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_NAME_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_namewrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_namewrite2(BLE_testItem testitem) {

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());
        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        //=====================connection======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //=====================enable notify======================
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
            SystemClock.sleep(2000);

            while (testitem.cmdSize() > 0) {
                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + "evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not
                String revEvt = bleServiceInstance.getCharChagedstr();
                if (revEvt.equals(evt)) {
                    write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                } else {
                    write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                    return false;
                }

                testitem.popCmd();
            }

            //disconnect
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);

            //-------------------scan
            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //-------------------stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find ble name:" + testitem.getdataStr());
                return false;
            }

        }
        return true;
    }

    public int AIR_BD_AdvDataWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_ADVDATA_WRITE;

        if (advWriteTestOnlyOnce == 1) {
            write2_MainUI_Log(0, "AdvDataWrite 1 only test once");
        }

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());
        if (advWriteTestOnlyOnce == 0) {
            if (!test_air_advdatawrite2(testItem)) {
                write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
                ((MainActivity) mcontext).updateFailCnt();
            } else {
                write2_MainUI_Log(0, Current_TEST_NAME + " test success");
                ((MainActivity) mcontext).updatesuccesstv();
            }

            //disconnect no matter success or fail
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);
        }

        advWriteTestOnlyOnce = 1;

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_advdatawrite2(BLE_testItem testitem) {

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());
        setDefaultBleData();
        //set ble name from config
        setBleData(testitem.getdataStr());

        //=====================connection======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //=====================enable notify======================
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
            SystemClock.sleep(2000);

            while (testitem.cmdSize() > 0) {
                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + "evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not
                String revEvt = bleServiceInstance.getCharChagedstr();
                if (revEvt.equals(evt)) {
                    write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                } else {
                    write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                    return false;
                }

                testitem.popCmd();
            }

            //disconnect
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);

            //-------------------scan
            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //-------------------stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find ble name:" + testitem.getdataStr());
                return false;
            }

        }
        return true;
    }

    public int AIR_BD_TxPowerWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_TXPOWER_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_txpowerwrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_txpowerwrite2(BLE_testItem testitem) {
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        db_List.clear();

        while (testitem.cmdSize() > 0) {
            //=====================connection======================
            if (commonutil.config.BLE) {
                bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
                if (null == bleServiceInstance) {
                    commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                    return false;
                }

                //check connect or not, retry connect 3 times if connection fail
                if (!bleServiceInstance.isConnected()) {
                    if (!connectRetry()) {
                        return false;
                    }
                }

                //=====================enable notify======================
                bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
                SystemClock.sleep(2000);


                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + "evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not
                String revEvt = bleServiceInstance.getCharChagedstr();
                if (revEvt.equals(evt)) {
                    write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                } else {
                    write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                    return false;
                }

                //3 mean first cmd set has finished, then calculate power
                //1 mean second cmd set has finished, then calculate power
                if (testitem.cmdSize() == 3 || testitem.cmdSize() == 1) {
                    //=====================BLE======================
                    //disconnect
                    bleServiceInstance.disconnect();
                    SystemClock.sleep(3000);
                    //-------------------scan
                    long scanTime = 5000;
                    //start scan
                    scanLeDevice(true);
                    //wait finish
                    SystemClock.sleep(scanTime);
                    //-------------------stop scan
                    scanLeDevice(false);
                    //add to list for comparing
                    db_List.add(getAvgRssi());
                    clearRssiList();

                    SystemClock.sleep(500);
                }
                testitem.popCmd();
            }
        }

        //compare two power level
        if (db_List.size() == 2) {
            if (db_List.get(0) > db_List.get(1)) {
                commonutil.wErrLog(db_List.get(0).toString() + " should not bigger than" + db_List.get(1));
                Log.d(TAG, db_List.get(0).toString() + " should not bigger than" + db_List.get(1));
                return false;
            }
        } else {
            commonutil.wErrLog("invalid size:" + db_List.size());
            Log.d(TAG, "invalid size:" + db_List.size());
            return false;
        }

        return true;
    }

    public int AIR_BD_BatteryLevelWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_BATTERYLEVEL_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_BatteryWrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_BatteryWrite2(BLE_testItem testitem) {
        String BatteryData = testitem.getdataStr();

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + BatteryData);
        //=====================connection======================
        bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
        if (null == bleServiceInstance) {
            commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            return false;
        }

        //check connect or not, retry connect 3 times if connection fail
        if (!bleServiceInstance.isConnected()) {
            if (!connectRetry()) {
                return false;
            }
        }

        //=====================enable notify======================
        bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        SystemClock.sleep(2000);

        while (testitem.cmdSize() > 0) {
            //clear
            bleServiceInstance.clearCharReadstr();

            String cmd = testitem.getCmd().split(":")[0];
            String evt = testitem.getCmd().split(":")[1];

            commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ",evt:" + evt);
            byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

            //write command
            bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);

            SystemClock.sleep(1000);

            //read char value
            bleServiceInstance.readCharacteristic2(BATTERY_SERVICE_UUID, BATTERY_LEVEL_CHARA_UUID);
            SystemClock.sleep(1000);

            String revCharReadStr = bleServiceInstance.getCharReadstr();
            if (BatteryData.equals(revCharReadStr)) {
                write2_MainUI_Log(0, "OK evt ,rev chara change:" + revCharReadStr);
            } else {
                write2_MainUI_Log(2, "fail evt ,rev chara change:" + revCharReadStr);
                return false;
            }

            testitem.popCmd();
        }

        //disconnect, sleep few secs for decreasing fail reconnect rate
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        return true;
    }

    public int AIR_BD_IntvLatencyWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_INTVLATENCY_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_IntvLantencywrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_IntvLantencywrite2(BLE_testItem testitem) {
        String IntvData = testitem.getdataStr();
        commonutil.wdbgLogcat(TAG, 0, "*****" + testitem.gettestName() + ",data is " + IntvData);

        //=====================connection======================
        bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
        if (null == bleServiceInstance) {
            commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            return false;
        }

        //check connect or not, retry connect 3 times if connection fail
        if (!bleServiceInstance.isConnected()) {
            if (!connectRetry()) {
                return false;
            }
        }

        //=====================enable notify======================
        bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        SystemClock.sleep(1000);

        while (testitem.cmdSize() > 0) {
            //clear last event
            bleServiceInstance.clearCharChagedstr();

            String cmd = testitem.getCmd().split(":")[0];
            String evt = testitem.getCmd().split(":")[1];

            commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ",evt:" + evt);
            byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

            //write command
            bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
            SystemClock.sleep(5000);

            //check write evt string is correct or not
            String revEvt = bleServiceInstance.getCharChagedstr();
            //different os may choose different interval
            if (revEvt.contains("213004")) {
                write2_MainUI_Log(0, "OK evt ,OS choose revEvt:" + revEvt);
            } else if (revEvt.equals(evt)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }

            testitem.popCmd();
        }


        //----------------caculate avg connection interval
        byte[] bytes = new byte[1];
        Long diffSum = 0L;
        int maxsize = 5;
        for (int i = 0; i < maxsize; i++) {
            bytes[0] = (byte) ((i + 10) & 0xff);
            bleServiceInstance.clearCharChagedstr();
            bleServiceInstance.clearCbUnixTime();

            long startUnixTime = System.currentTimeMillis();
            ;
            bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
            SystemClock.sleep(2000);
            long endUnixTime = bleServiceInstance.GetchangeCbUnixTime();
            long diffTime = endUnixTime - startUnixTime;
            diffSum = diffSum + diffTime;
            commonutil.wdbgLogcat(TAG, 0, "diff time:" + diffTime);
        }

        Long avgConnInvtVal = diffSum / maxsize;
        Long TheoryIntvData = Long.parseLong(IntvData);
        commonutil.wdbgLogcat(TAG, 0, "theory val is:" + TheoryIntvData + "\n avgConnInvtVal:" + avgConnInvtVal);

        if (avgConnInvtVal < TheoryIntvData) {
            commonutil.wdbgLogcat(TAG, 2, "avgConnInvtVal smaller than is impossible !");
            return false;
        }

        return true;
    }

    public int AIR_BD_AdvDataWrite2(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_ADVDATA2_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_advdata2write2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_advdata2write2(BLE_testItem testitem) {
        String advdata2 = testitem.getdataStr();

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + advdata2);
        setDefaultBleData();
        //set ble name from config
        setBleData(advdata2);

        //=====================connection======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //=====================enable notify======================
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
            SystemClock.sleep(2000);

            while (testitem.cmdSize() > 0) {
                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + "evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not
                String revEvt = bleServiceInstance.getCharChagedstr();
                if (revEvt.equals(evt)) {
                    write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
                } else {
                    write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                    return false;
                }

                testitem.popCmd();
            }

            //disconnect
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);

            //-------------------scan
            long scanTime = 5000;
            //start scan
            scanLeDevice(true);
            //wait finish
            SystemClock.sleep(scanTime);
            //-------------------stop scan
            scanLeDevice(false);

            //check if the name we modify exist
            if (!getFindBleDataFlag()) {
                commonutil.wErrLog("can not find ble name:" + testitem.getdataStr());
                return false;
            }
        }

        return true;
    }


    public int AIR_BD_ScanResDataWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_SCANRESDATA_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        //use test_air_advdata2write because the behavior is same
        if (!test_air_advdata2write2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public int AIR_BD_DisconnectWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_DISCONN_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_disconnectwrite2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_disconnectwrite2(BLE_testItem testitem) {

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());
        //=====================connection======================
        if (commonutil.config.BLE) {
            bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
            if (null == bleServiceInstance) {
                commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
                return false;
            }

            //check connect or not, retry connect 3 times if connection fail
            if (!bleServiceInstance.isConnected()) {
                if (!connectRetry()) {
                    return false;
                }
            }

            //=====================enable notify======================
            bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
            SystemClock.sleep(2000);

            while (testitem.cmdSize() > 0) {
                //clear last event
                bleServiceInstance.clearCharChagedstr();

                String cmd = testitem.getCmd().split(":")[0];
                String evt = testitem.getCmd().split(":")[1];

                commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ",evt:" + evt);
                byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

                //write command
                bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
                SystemClock.sleep(1000);

                //check write evt string is correct or not, there is no need to check revEvt, check connection
                //staus below
                String revEvt = bleServiceInstance.getCharChagedstr();

                write2_MainUI_Log(0, "revEvt:" + revEvt);
                testitem.popCmd();
            }

            //------------------check is connection or not
            if (!bleServiceInstance.isConnected()) {
                write2_MainUI_Log(0, "disconnect is right behavior !");
            } else {
                write2_MainUI_Log(2, "still connect is not right behavior here!");
            }

            //disconnect
            bleServiceInstance.disconnect();
            SystemClock.sleep(3000);

        }

        return true;
    }

    public int AIR_BD_DataPayloadWrite(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_DATA_PAYLOAD_WRITE;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_datapayload2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_datapayload2(BLE_testItem testitem) {
        String payloadData = testitem.getdataStr();

        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + payloadData);
        //=====================connection======================
        bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
        if (null == bleServiceInstance) {
            commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            return false;
        }

        //check connect or not, retry connect 3 times if connection fail
        if (!bleServiceInstance.isConnected()) {
            if (!connectRetry()) {
                return false;
            }
        }

        //=====================enable notify======================
        bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        SystemClock.sleep(2000);

        while (testitem.cmdSize() > 0) {
            //clear last event
            bleServiceInstance.clearCharChagedstr();

            String cmd = testitem.getCmd().split(":")[0];
            String evt = testitem.getCmd().split(":")[1];

            commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ",evt:" + evt);
            byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

            //write command
            bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
            SystemClock.sleep(1000);

            //check write evt string is correct or not
            String revEvt = bleServiceInstance.getCharChagedstr();
            if (revEvt.equals(payloadData)) {
                write2_MainUI_Log(0, "OK evt ,revEvt:" + revEvt);
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }

            testitem.popCmd();
        }
        return true;
    }

    public int AIR_BD_phy(BLE_testItem testItem) {
        Current_TEST_NAME = BD_TEST_PHY;

        write2_MainUI_Log(0, "S=====" + testItem.gettestName());

        if (!test_air_phy2(testItem)) {
            write2_MainUI_Log(2, Current_TEST_NAME + " test fail");
            ((MainActivity) mcontext).updateFailCnt();
        } else {
            write2_MainUI_Log(0, Current_TEST_NAME + " test success");
            ((MainActivity) mcontext).updatesuccesstv();
        }

        //disconnect no matter success or fail
        bleServiceInstance.disconnect();
        SystemClock.sleep(3000);

        write2_MainUI_Log(0, "E=====" + testItem.gettestName());

        return 0;
    }

    public boolean test_air_phy2(BLE_testItem testitem) {
        write2_MainUI_Log(0, "*****" + testitem.gettestName() + ",data is " + testitem.getdataStr());

        //=====================connection======================
        bleServiceInstance = ((MainActivity) mcontext).getBleServiceInstance();
        if (null == bleServiceInstance) {
            commonutil.wdbgLogcat(TAG, 2, "bleServiceInstance is null, ble service not start yet ?");
            return false;
        }

        //check connect or not, retry connect 3 times if connection fail
        if (!bleServiceInstance.isConnected()) {
            if (!connectRetry()) {
                return false;
            }
        }

        //=====================enable notify======================
        bleServiceInstance.setCharacteristicNotify(SERVICE_UUID, NOTIFY_CHARA_UUID, WRITE_DESCRIPTOR_UUID);
        SystemClock.sleep(2000);

        while (testitem.cmdSize() > 0) {
            //clear last event
            bleServiceInstance.clearCharChagedstr();

            String cmd = testitem.getCmd().split(":")[0];
            String evt = testitem.getCmd().split(":")[1];

            commonutil.wdbgLogcat(TAG, 0, "cmd:" + cmd + ",evt:" + evt);
            byte[] bytes = ((MainActivity) mcontext).string2Bytes(cmd);

            //write command
            bleServiceInstance.writeCharaval(SERVICE_UUID, WRITE_CHARA_UUID, bytes);
            SystemClock.sleep(1000);

            //check write evt string is correct or not
            String revEvt = bleServiceInstance.getCharChagedstr();

            //read phy addr start with 0x57
            if (revEvt.startsWith("6C57") || revEvt.startsWith("57")) {
                if (checkSubstrOfReadPhy(revEvt, evt, AIR_UART_MODE)) {
                    String strsubaddr = GetReadPhyAddr(revEvt, AIR_UART_MODE);
                    String strsubdata = GetReadPhyData(revEvt, AIR_UART_MODE);
                    write2_MainUI_Log(0, "addr:" + strsubaddr + ",data:" + strsubdata);
                    if (strsubaddr.equals(PHY_FUNC_ADDR)) {
                        if (!checkFunctioDatanbit(strsubdata)) {
                            write2_MainUI_Log(2, "fail Function revEvt:" + strsubaddr);
                            return false;
                        }
                    }
                }
            } else if (evt.equals("FF")) {
                // note: it is evt not revEvt,just for passing to next cmd
                write2_MainUI_Log(0, "FF pass, write phy has no event");
            } else {
                write2_MainUI_Log(2, "fail evt ,revEvt:" + revEvt);
                return false;
            }

            testitem.popCmd();
        }
        return true;
    }



    private void parseAdv(byte[] adv_data) {
        if (adv_data.length == 0) {
            Log.e(TAG, "adv_data len is 0");
            return;
        }

        int ptr = 0;
        int len = adv_data[ptr];

        Log.d(TAG, "--adv_data total len: " + Integer.toString(adv_data.length));

        while (len > 0) {
            byte type = adv_data[ptr + 1];
            Log.d(TAG, "ad len: " + Integer.toString(len) + ",ad type:" + String.format("0x%02x", type));

            int dataLen = len - 1;// minus type(1 byte) length
            //allocate byte array
            byte[] data = new byte[dataLen];
            //copy data , skip len(1 byte) and type(1 byte),so ptr + 2
            System.arraycopy(adv_data, ptr + 2, data, 0, dataLen);
            Log.d(TAG, "ad data :" + byteArrayToHex(data));
            switch (type) {
                case 0x01: // Flags
                    break;
                case (byte) 0xFF: // “Manufacturer Specific Data”
                    setManufacturerData(byteArrayToHex(data));
                    break;
                default: // skip
                    break;
            }
            int nextAD = ptr + len + 1;
            ptr = nextAD;

            //next AD length
            len = adv_data[ptr];
        }
    }

    public void setManufacturerData(String ManufacturerSpecificData) {
        ManufacturerData = ManufacturerSpecificData;
    }

    public String getManufacturerData() {
        return ManufacturerData;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString().toUpperCase();
    }

    private void clearRssiList() {
        rssiList.clear();
    }

    private void addRssi(int rssi) {
        rssiList.add(rssi);
    }

    private int getAvgRssi() {
        if (rssiList.size() == 0) {
            Log.d(TAG, "list size is 0");
            return -1;
        }
        int sum = 0;
        for (int rssi : rssiList) {
            //Log.d(TAG, Integer.toString(rssi));
            sum = sum + rssi;
        }
        int avgRssi = sum / (rssiList.size());

        write2_MainUI_Log(0, "avgRssi:" + Integer.toString(avgRssi));
        return avgRssi;
    }


    boolean checkSPIRevLen(byte[] rev) {
        if (rev.length != 0) {
            int length = (rev[0] & 0xF | ((rev[0] & 0x10))) + 1;//+1 means header itself
            if (rev.length != length) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean checkUARTRevLen(byte[] rev) {
        if (rev.length != 0) {
            //rev event only have 0x21 and 0x26, Read Physical Address Return 0x57
            if (rev[0] == 0x21 || rev[0] == 0x26 || rev[0] == 0x57) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    boolean checkRevLen(byte[] rev, int mode) {

        mode = ((MainActivity) mcontext).GetTestMode();
        if (SPI_MODE == mode) {
            if (rev.length != 0 && rev.length > 1) {
                if (rev[1] == 0x21 || rev[1] == 0x26 || rev[1] == 0x57) {
                    return true;
                } else {
                    return false;
                }
                /*
                int length = (rev[0] & 0xF | ((rev[0] & 0x10))) + 1;//+1 means header itself
                if (rev.length != length) {
                    return false;
                } else {
                    return true;
                }
                */
            }
        } else if (UART_MODE == mode || AIR_UART_MODE == mode) {
            if (rev.length != 0) {
                //rev event only have 0x21 and 0x26, Read Physical Address Return 0x57
                if (rev[0] == 0x21 || rev[0] == 0x26 || rev[0] == 0x57) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean checkSubstrOfReadPhy(String revStr, String expectedstr, int mode) {
        if (revStr.length() < 10 || expectedstr.length() < 10) {
            return false;
        }
        String revStrsub1 = null;
        String revSubaddr = null;
        String expStrsub1 = null;
        String expSubaddr = null;
        switch (mode) {
            case SPI_MODE:
                // only compare first 6 byte and addr, we don't know data content
                revStrsub1 = revStr.substring(0, 6);//0x6C5701
                revSubaddr = GetReadPhyAddr(revStr, mode);//skip two byte reserved length
                expStrsub1 = expectedstr.substring(0, 6);
                expSubaddr = GetReadPhyAddr(expectedstr, mode);//skip two byte reserved length
                break;
            case UART_MODE:
            case AIR_UART_MODE:
                revStrsub1 = revStr.substring(0, 4);//0x6C5701
                revSubaddr = GetReadPhyAddr(revStr, mode);//skip two byte reserved length
                expStrsub1 = expectedstr.substring(0, 4);
                expSubaddr = GetReadPhyAddr(expectedstr, mode);//skip two byte reserved length
                break;
            default:
                break;
        }

        //if same
        if ((revStrsub1.equals(expStrsub1)) && (revSubaddr.equals(expSubaddr))) {
            return true;
        } else {
            commonutil.wdbgLogcat(TAG, 2, "fail revStrsub1:" + revStrsub1);
            commonutil.wdbgLogcat(TAG, 2, "fail revSubaddr:" + revSubaddr);
            commonutil.wdbgLogcat(TAG, 2, "fail expStrsub1:" + expStrsub1);
            commonutil.wdbgLogcat(TAG, 2, "fail expSubaddr:" + expSubaddr);
            return false;
        }
    }

    private String GetReadPhyAddr(String revStr, int mode) {
        //ex:6C57010000 | FC5F0000 | 00000099, addr = 0x0000_5FFC
        String addr = null;
        switch (mode) {
            case SPI_MODE:
                addr = revStr.substring(10, 18);//skip two byte reserved length
                break;
            case UART_MODE:
            case AIR_UART_MODE:
                addr = revStr.substring(8, 16);//skip two byte reserved length
                break;
            default:
                break;
        }
        return addr;
    }

    private String GetReadPhyData(String revStr, int mode) {
        //ex:6C57010000 | FC5F0000 | 00000099, data = 0x99000000
        String addr = null;
        switch (mode) {
            case SPI_MODE:
                addr = revStr.substring(18, revStr.length());//skip two byte reserved length
                break;
            case UART_MODE:
            case AIR_UART_MODE:
                addr = revStr.substring(16, revStr.length());//skip two byte reserved length
                break;
            default:
                break;
        }
        return addr;
    }

    private boolean checkFunctioDatanbit(String strsubdata) {
        if (strsubdata.equals("")) {
            return false;
        }

        byte[] bytes = ((MainActivity) mcontext).string2Bytes(strsubdata);
        if ((bytes[0] & (byte) 0x1) == 0x0) {
            return true;
        } else {
            return false;
        }

    }

    private void setupBRList() {
        BaudRateList.add(mBaudratei2400);
        BaudRateList.add(mBaudratei9600);
        BaudRateList.add(mBaudratei14400);
        BaudRateList.add(mBaudratei19200);
        BaudRateList.add(mBaudratei38400);
        BaudRateList.add(mBaudratei57600);
        BaudRateList.add(mBaudratei115200);
    }

    private String GetBRwriteCmd(int BaudRate, int mode) {
        String br;
        switch (BaudRate) {
            case mBaudratei2400:
                br = "00000000";
                break;
            case mBaudratei9600:
                br = "01000000";
                break;
            case mBaudratei14400:
                br = "02000000";
                break;
            case mBaudratei19200:
                br = "03000000";
                break;
            case mBaudratei38400:
                br = "04000000";
                break;
            case mBaudratei57600:
                br = "05000000";
                break;
            case mBaudratei115200:
                br = "06000000";
                break;
            case mBaudratei256000:
                br = "07000000";
                break;
            default:
                br = "06000000";
                break;
        }
        final String A7 = "A7";
        String cmd = "253204";
        switch (mode) {
            case SPI_MODE:
                cmd = A7 + cmd + br;
                break;
            case UART_MODE:
                cmd = cmd + br;
                break;
            default:
                cmd = cmd + br;
                break;

        }
        return cmd;
    }

    private String GetBRwriteEvt(int BaudRate, int mode) {
        final String hdr = "63";
        String cmd = "263200";
        switch (mode) {
            case SPI_MODE:
                cmd = hdr + cmd;
                break;
            case UART_MODE:
                break;
            default:
                break;
        }
        return cmd;
    }

    private String GetBRreadCmd(int BaudRate, int mode) {
        final String hdr = "A2";
        String cmd = "2032";
        switch (mode) {
            case SPI_MODE:
                cmd = hdr + cmd;
                break;
            case UART_MODE:
                break;
            default:
                break;
        }
        return cmd;
    }

    private String GetBRreadEvt(int BaudRate, int mode) {
        String br;
        switch (BaudRate) {
            case mBaudratei2400:
                br = "00000000";
                break;
            case mBaudratei9600:
                br = "01000000";
                break;
            case mBaudratei14400:
                br = "02000000";
                break;
            case mBaudratei19200:
                br = "03000000";
                break;
            case mBaudratei38400:
                br = "04000000";
                break;
            case mBaudratei57600:
                br = "05000000";
                break;
            case mBaudratei115200:
                br = "06000000";
                break;
            case mBaudratei256000:
                br = "07000000";
                break;
            default:
                br = "06000000";
                break;
        }
        final String hdr = "67";
        String cmd = "213204";
        switch (mode) {
            case SPI_MODE:
                cmd = hdr + cmd + br;
                break;
            case UART_MODE:
                cmd = cmd + br;
                break;
            default:
                cmd = cmd + br;
                break;

        }
        return cmd;
    }

    private String GetBRupdateCmd(int BaudRate, int mode) {
        final String hdr = "A3";
        String cmd = "253F00";
        switch (mode) {
            case SPI_MODE:
                cmd = hdr + cmd;
                break;
            case UART_MODE:
                break;
            default:
                break;
        }
        return cmd;
    }

    private String GetBRupdateEvt(int BaudRate, int mode) {
        final String hdr = "63";
        String cmd = "263F00";
        switch (mode) {
            case SPI_MODE:
                cmd = hdr + cmd;
                break;
            case UART_MODE:
                break;
            default:
                break;
        }
        return cmd;
    }

    private String GetBRupdateFailEvt(int BaudRate, int mode) {
        final String hdr = "63";
        String cmd = "263F01";
        switch (mode) {
            case SPI_MODE:
                cmd = hdr + cmd;
                break;
            case UART_MODE:
                break;
            default:
                break;
        }
        return cmd;
    }

    public boolean checkBaudRate(int oldbaudRate, int newbaudRate) {
        int result = -1;
        //TODO:check
//        if (oldbaudRate == newbaudRate) {
//            return true;
//        }

        try {
            int loopCheckCnt;
            for (loopCheckCnt = 0; loopCheckCnt < 3; loopCheckCnt++) {
                commonutil.wdbgLogcat(TAG, 0, "[checkBaudRate] retry: " + Integer.toString(loopCheckCnt));

                //read baud rate for three times to know we change baud rate success or not
                result = ((MainActivity) mcontext).uart_tx_command(GetBRreadCmd(newbaudRate, TESTMODE));
                if (_ST_SUCCESS_ != result) {
                    commonutil.wdbgLogcat(TAG, 0, "[checkBaudRate] read cmd fail:" + GetBRreadCmd(newbaudRate, TESTMODE));
                    ((MainActivity) mcontext).writeLog2View("[checkBaudRate] read cmd fail:" + GetBRreadCmd(newbaudRate, TESTMODE));
                }

                SystemClock.sleep(500);
                byte[] rev = ((MainActivity) mcontext).uart_rx();
                if (rev.length != 0) {
                    String revEvt = ((MainActivity) mcontext).bytes2String(rev);
                    if (revEvt.equals(GetBRreadEvt(newbaudRate, TESTMODE))) {
                        ((MainActivity) mcontext).writeLog2View("change from <" + Integer.toString(oldbaudRate) + "> to <" + Integer.toString(newbaudRate)
                                + ">success !");
                        commonutil.wdbgLogcat(TAG, 0, "change from <" + Integer.toString(oldbaudRate) + "> to <" + Integer.toString(newbaudRate)
                                + ">success !");
                        return true;
                    }
                }
                SystemClock.sleep(500);
            }

            //retry fail
            commonutil.wdbgLogcat(TAG, 2, "change from <" + Integer.toString(oldbaudRate) + "> to <" + Integer.toString(newbaudRate)
                    + ">fail !");
            ((MainActivity) mcontext).writeLog2View("change from " + Integer.toString(oldbaudRate) + " to " + Integer.toString(newbaudRate)
                    + "fail !");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            commonutil.wdbgLogcat(TAG, 2, e.toString());
        }
        return false;
    }

    void write2MainUI(final String log) {
        ((MainActivity) mcontext).writeLog2View(log);
    }

    private void write2_MainUI_Log(int level, final String log) {
        String writedlog = commonutil.wdbgLogcat(TAG, level, log);
        ((MainActivity) mcontext).writeLog2View(writedlog);
    }

    private void dumpitem(BLE_testItem testItem) {
        Log.d(TAG, "gettestName:" + testItem.gettestName());
        Log.d(TAG, "data:" + testItem.getdataStr());
        Log.d(TAG, "cmd" + testItem.getCmd());
        testItem.popCmd();
        Log.d(TAG, "cmd:" + testItem.getCmd());
    }

    String send_uart_Cmd(BLE_testItem testitem, final int retryMax) {

        byte[] rev = {};
        boolean flag = false;
        int current_retryNum = 0;
        while (!flag) {
            String cmd = testitem.getCmd().split(":")[0];
            String evt = testitem.getCmd().split(":")[1];

            write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

            //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
            String writeSubStrCmd[] = {""};
            if (cmd.contains("+")) {
                writeSubStrCmd = cmd.split("\\+");
            } else {
                writeSubStrCmd[0] = cmd;
            }

            //----uart tx-----> BLE
            SystemClock.sleep(500);

            if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                //if tx length over 32 byte, transmit until finish
                int i;
                for (i = 1; i < writeSubStrCmd.length; i++) {
                    SystemClock.sleep(500);
                    if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                        write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                        return FAIL;
                    }
                }

                //uart rx <--- BLE
                SystemClock.sleep(500);
                rev = ((MainActivity) mcontext).uart_rx();

                if (rev.length != 0) {
                    //check if uart rx recv incorrect size
                    if (!checkRevLen(rev, TESTMODE)) {
                        //work around, try three times if rev length not correct
                        if (current_retryNum < retryMax) {
                            write2_MainUI_Log(1, "retry " + Integer.toString(current_retryNum));
                            current_retryNum++;
                        } else {
                            String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                            write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                            return FAIL;
                        }
                    }
                    else
                    {
                        //success
                        flag = true;
                    }
                } else {
                    write2_MainUI_Log(2, "rev length is 0");
                    return FAIL;
                }
            } else {
                write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                return FAIL;
            }
        }//end while

        return ((MainActivity) mcontext).bytes2String(rev);
    }

    String send_uart_Cmd(BLE_testItem testitem, final int retryMax,long wait_rx_time_ms) {
        byte[] rev = {};
        boolean flag = false;
        int current_retryNum = 0;
        while (!flag) {
            String cmd = testitem.getCmd().split(":")[0];
            String evt = testitem.getCmd().split(":")[1];

            write2_MainUI_Log(0, "cmd:" + cmd + ", evt:" + evt);

            //packet length more than 32 byte use "+" , ex:A0xxx+A2xxxx
            String writeSubStrCmd[] = {""};
            if (cmd.contains("+")) {
                writeSubStrCmd = cmd.split("\\+");
            } else {
                writeSubStrCmd[0] = cmd;
            }

            //----uart tx-----> BLE
            SystemClock.sleep(500);

            if (_ST_SUCCESS_ == ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[0])) {
                //if tx length over 32 byte, transmit until finish
                int i;
                for (i = 1; i < writeSubStrCmd.length; i++) {
                    SystemClock.sleep(500);
                    if (_ST_SUCCESS_ != ((MainActivity) mcontext).uart_tx_command(writeSubStrCmd[i])) {
                        write2_MainUI_Log(2, "tx fail,cmd:" + writeSubStrCmd[i]);
                        return FAIL;
                    }
                }

                //uart rx <--- BLE
                SystemClock.sleep(wait_rx_time_ms);
                rev = ((MainActivity) mcontext).uart_rx();

                if (rev.length != 0) {
                    //check if uart rx recv incorrect size
                    if (!checkRevLen(rev, TESTMODE)) {
                        //work around, try three times if rev length not correct
                        if (current_retryNum < retryMax) {
                            write2_MainUI_Log(1, "retry " + Integer.toString(current_retryNum));
                            current_retryNum++;
                        } else {
                            String errEvtStr = ((MainActivity) mcontext).bytes2String(rev);
                            write2_MainUI_Log(2, "retry fail errEvtStr:" + errEvtStr);
                            return FAIL;
                        }
                    } else {
                        //success
                        flag = true;
                    }
                } else {
                    write2_MainUI_Log(2, "rev length is 0");
                    return FAIL;
                }
            } else {
                write2_MainUI_Log(2, "fail tx ,cmd:" + writeSubStrCmd[0]);
                return FAIL;
            }
        }//end while

        return ((MainActivity) mcontext).bytes2String(rev);
    }
}
