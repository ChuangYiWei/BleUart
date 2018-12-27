package com.example.johnny_wei.bleuart;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnny_wei.bleuart.customview.RowItem;
import com.example.johnny_wei.bleuart.customview.customAdapter;
import com.example.johnny_wei.bleuart.driver_pl2303.PL2303Driver;
import com.example.johnny_wei.bleuart.util.commonutil;
import com.example.johnny_wei.bleuart.xmlpull.BLE_testItem;
import com.example.johnny_wei.bleuart.xmlpull.XmlParser;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.johnny_wei.bleuart.common.pConvertType._C_ADD_0_BACK_;
import static com.example.johnny_wei.bleuart.common.pConvertType._C_LOW_BYTE_FIRST_;
import static com.example.johnny_wei.bleuart.common.pConvertType._E_ADD_0_FRONT_;
import static com.example.johnny_wei.bleuart.common.pConvertType._E_DECIMAL_;
import static com.example.johnny_wei.bleuart.common.pConvertType._E_NO_SIGN_;
import static com.example.johnny_wei.bleuart.common.pConvertType._E_REMOVE_0_;
import static com.example.johnny_wei.bleuart.common.pConvertType._E_REMOVE_0_KEEP_LAST_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_CHIP_NOT_SUPPORT_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_NOT_CONNECTED_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_NOT_ENMERATE_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_OPEN_FAIL_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_SUCCESS_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_UST_HOST_NOT_SUPPORT_;
import static com.example.johnny_wei.bleuart.common.pUart._ST_WRITE_FAIL_;

public class MainActivity extends AppCompatActivity {

    //service
    private LoaclServiceConnection mLoaclServiceConnection;
    static bleService mbleService = null;

    //ble
    static final int PERMISSION_REQUEST_COARSE_LOCATION = 101;
    static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 102;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter mBluetoothAdapter;

    //common
    String TAG = getClass().getSimpleName();
    private Handler m_userHandler;
    private Handler mUIHandler;
    Activity thisActivity = this;

    //layout
    TextView tv_success;
    TextView tv_fail;
    TextView tv_time;
    TextView tv_status;
    EditText ed_uart;
    static int successCnt;
    static int failCnt;

    //log
    LinkedList<String> logString;
    ListView listView;
    ArrayAdapter adapter;
    customAdapter mAdapter;
    LinkedList<RowItem> rowItems;

    //for test
    LinkedList<String> testFuncList;
    private static AllTestclass bd_obj;

    //uart setting
    PL2303Driver mSerial;
    private PL2303Driver.BaudRate       mBaudrate       = PL2303Driver.BaudRate.B115200;
    private PL2303Driver.DataBits       mDataBits       = PL2303Driver.DataBits.D8;
    private PL2303Driver.StopBits       mStopBits       = PL2303Driver.StopBits.S1;
    private PL2303Driver.Parity         mParity         = PL2303Driver.Parity.NONE;
    private PL2303Driver.FlowControl    mFlowControl    = PL2303Driver.FlowControl.OFF;
    private static final int                       mBaudratei = 115200;
    private static final int           mBaudratei115200 = 115200;

    public final String[] NibbleHex={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

    private static final String ACTION_USB_PERMISSION = "com.prolific.pl2303hxdsimpletest.USB_PERMISSION";
    private final byte[] bytearray={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //screen on forever
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        setupView();

        //storage permission
        if (shouldAskPermissions()) {
            askPermissions();
        }

        enableBluetooth();

        init();

        openBleService();

    }

    private void init() {

        initPL2303();
        mUIHandler = new Handler();
        HandlerThread ht_thread = new HandlerThread("name");
        ht_thread.start();
        m_userHandler = new Handler(ht_thread.getLooper());

        //functions under test
        testFuncList = new LinkedList<String>();

        //invoke class
        bd_obj = new AllTestclass(this);

        //write sd
        commonutil.init();

    }


    private void setupView() {
        tv_success = findViewById(R.id.tv_success);
        tv_fail = findViewById(R.id.tv_fail);
        ed_uart = findViewById(R.id.ed_uart);
        tv_time = findViewById(R.id.tv_time);
        tv_status = findViewById(R.id.tv_status);
        listView = findViewById(R.id.id_list);
        logString = new LinkedList<String>();
        adapter = new ArrayAdapter<String>(thisActivity, android.R.layout.simple_list_item_1, logString);
        rowItems = new LinkedList<RowItem>();
        mAdapter = new customAdapter(this, rowItems);
//        listView.setAdapter(adapter);
        listView.setAdapter(mAdapter);
        setupSpinner();
    }


    private String[] testModeArray = {"SPI mode","UART mode", "AIR CMD mode"};
    int modeIdx = 0;
    public void setupSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //建立一個ArrayAdapter物件，並放置下拉選單的內容

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,testModeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                modeIdx = position;
                Toast.makeText(MainActivity.this, "You choose "+adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(MainActivity.this, "You did not choose", Toast.LENGTH_LONG).show();
            }
        });
    }

    //return mode index,//0:spi, 1:uart, 2:air_uart
    public int GetTestMode(){
        return modeIdx;
    }

    public void clearView() {
        successCnt = 0;
        failCnt = 0;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_success.setText("0");
                tv_fail.setText("0");
            }
        });
        tv_time.setText("--:--:--");
        tv_status.setText("0");
    }

    public void updatesuccesstv() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                successCnt = successCnt + 1;
                tv_success.setText(String.format("%d", successCnt));
            }
        });
    }

    public void updateFailCnt() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                failCnt = failCnt + 1;
                tv_fail.setText(String.format("%d", failCnt));
            }
        });
    }

    private void enableBluetooth() {

        //if API level > 23, we need to ask permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            //未取得權限，向使用者要求允許權限
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }

//      Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to// BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) this.getSystemService(this.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    BluetoothAdapter GetBluetoothAdapter()
    {
        return mBluetoothAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK){
            Log.d(TAG,"open bluetooth OK");
        }else if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED){
            Toast.makeText(MainActivity.this,"please open bluetooth !! ",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length == 0) {
            Log.e(TAG, "grantResults size is 0");
            return;
        }
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                break;
            }
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "storage location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("storage access has not been granted, debug log disable");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
            }
            break;
        }

    }


    private int initPL2303() {
        mSerial = new PL2303Driver((UsbManager) this.getSystemService(Context.USB_SERVICE),
                this, ACTION_USB_PERMISSION);
        if (mSerial == null) {
            commonutil.wdbgLogcat(TAG, 2, "open pl2303 fail");
            writeLog2View("open pl2303 fail");
            return _ST_UST_HOST_NOT_SUPPORT_;
        }
        if (!mSerial.PL2303USBFeatureSupported()) {
            commonutil.wdbgLogcat(TAG, 2, "usb feature not support");
            writeLog2View("usb feature not support");
            return _ST_UST_HOST_NOT_SUPPORT_;
        }
        //connect need wait for a while,>=2
        int i;
        for (i = 0; i < 10; i++) {
            if (mSerial.isConnected()) {
                break;
            } else {
                if (!mSerial.enumerate()) {
                    commonutil.wdbgLogcat(TAG, 2, "\"no more devices found\"");
                    writeLog2View("\"no more devices found\"");
                    return _ST_NOT_ENMERATE_;
                } else {
                    Log.d(TAG, "onResume:enumerate succeeded!");
                }
            }
        }

        return 0;
    }

    public int setBaudRate(int baudRate) {
        Log.d(TAG, "init port setting");
        commonutil.wdbgLogcat(TAG,0,"[setBaudRate]" + Integer.toString(baudRate));
        int result = -1;
        if (mSerial.isConnected()) {
            result = mSerial.InitByPortSetting(baudRate, mDataBits, mStopBits, mParity, mFlowControl);
            if (result == 0) {
                if (!mSerial.PL2303Device_IsHasPermission()) {
                    writeLog2View("have no permission");
                    commonutil.wdbgLogcat(TAG,2,"[setBaudRate]have no permission");
                    return _ST_OPEN_FAIL_;
                }
                if (mSerial.PL2303Device_IsHasPermission() && (!mSerial.PL2303Device_IsSupportChip())) {
                    writeLog2View("not support chip");
                    commonutil.wdbgLogcat(TAG,2,"[setBaudRate]not support chip");
                    return _ST_CHIP_NOT_SUPPORT_;
                }
                writeLog2View("InitByPortSetting success");
                return _ST_SUCCESS_;
            } else {
                writeLog2View("InitByPortSetting fail");
                commonutil.wdbgLogcat(TAG,2,"[setBaudRate]InitByPortSetting fail");
                return _ST_OPEN_FAIL_;
            }
        } else {
            writeLog2View("serial not connected");
            commonutil.wdbgLogcat(TAG,2,"[setBaudRate]serial not connected");
            return _ST_NOT_CONNECTED_;
        }
    }

    public void clk_tx(View view) {
        int result = -1;
        ed_uart = (EditText) findViewById(R.id.ed_uart);
        String txString = ed_uart.getText().toString();

        result = uart_tx_command(txString);
        if(_ST_SUCCESS_ != result)
        {
            Log.d(TAG,"tx fail");
            writeLog2View("tx fail");
        }
    }

    public int uart_tx_command(String command)
    {
        byte[] txByte = string2Bytes(command);
        if (null == mSerial) {
            write2_MainUI_Log(2,"mSerial null");
            return _ST_UST_HOST_NOT_SUPPORT_;
        }

        if (!mSerial.isConnected()) {
            write2_MainUI_Log(2,"mSerial not connected");
            return _ST_NOT_CONNECTED_;
        }

        int res = mSerial.write(txByte, txByte.length);
        if (res < 0) {
            write2_MainUI_Log(2,"write fail");
            return _ST_WRITE_FAIL_;
        }

        return _ST_SUCCESS_;
    }

    public void clk_rx(View view) {
        uart_rx();
    }

    public byte[] uart_rx() {
        byte[] rByte = new byte[0];

        if (null == mSerial)
            return rByte;
        if (!mSerial.isConnected())
            return rByte;

        int len;
        byte[] rByte1;
        rByte1 = new byte[1024];//max=4096

        len = mSerial.read(rByte1);
        if (len > 0) {
            rByte = new byte[len];
            for (int i = 0; i < len; i++) {
                rByte[i] = rByte1[i];
            }
            Log.d(TAG, "rx:" + "len :0x" + int2String(rByte.length, 0) + " byte : " + bytes2String(rByte));
        }

        writeLog2View("rx:" + "len :0x" + int2String(rByte.length, 0) + " byte : " + bytes2String(rByte));
        return rByte;
    }

    public String bytes2String(byte[] inputbyte){
        String back = "";
        if (inputbyte != null) {
            if (inputbyte.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(inputbyte.length);
                for (byte byteChar : inputbyte) {
                    stringBuilder.append(String.format("%02X", byteChar));
                }
                back = stringBuilder.toString();
            }
        }
        return back;
    }


    public String int2String(int inI,int fun){
        int digi = 16, digix2 = 256;
        long tmpI0 = 0;
        String rString = "";
        boolean minus = false;

        {
            if (inI < 0) {
                minus = true;
                if ((fun & _E_NO_SIGN_) > 0) {
                    inI &= 0x7FFFFFFF;
                } else {
                    inI = -inI;
                }
            }
        }
        if ((fun & _E_DECIMAL_) > 0) {
            digi = 10;
            digix2 = 100;
        }

        do {
            tmpI0 = (inI % (digix2));

            if (rString.length() == 6 && (fun & _E_NO_SIGN_) > 0)//MSB
            {
                rString = NibbleHex[(int) ((tmpI0 / digi) | 0x8)] + NibbleHex[(int) (tmpI0 % digi)] + rString;
            } else {
                rString = NibbleHex[(int) (tmpI0 / digi)] + NibbleHex[(int) (tmpI0 % digi)] + rString;
            }
//Log.d("dbg","rString.1="+rString);
//dbgMsg.save("pConvertType.int2String.rString.0="+rString);
            inI /= (digix2);
//dbgMsg.save("pConvertType.int2String.inI.0="+inI);
//System.out.println("ins="+ins);
        }
        while (inI > 0);


        if ((fun & _E_ADD_0_FRONT_) > 0) {
            while ((rString.length() % 2) != 0) {
                rString = "0" + rString;
            }
        }


        if ((fun & _E_REMOVE_0_KEEP_LAST_) > 0) {
            while (rString.indexOf("0") == 0 && rString.length() > 1) {
                rString = rString.substring(1, rString.length());
            }
        } else if ((fun & _E_REMOVE_0_) > 0) {
            while (rString.indexOf("0") == 0) {
                rString = rString.substring(1, rString.length());
            }
        }

//dbgMsg.save("pConvertType.int2String.rString.1="+rString);
        if (minus) {
            if ((fun & _E_NO_SIGN_) > 0) {
                while (rString.length() < 8) {
                    if (rString.length() == 7)//MSB
                    {
                        rString = NibbleHex[8] + rString;
                    } else {
                        rString = NibbleHex[0] + rString;
                    }
                }
            } else {
                rString = "-" + rString;
            }
        }
        return rString;

    }

    private class LoaclServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mbleService = ((bleService.MyBinder)service).getService();
            if (!mbleService.initialize()) {
                Log.e("bleServiceList", "Unable to initialize Bluetooth");
                finish();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //service 物件設為null
            mbleService = null;
        }
    }

    public bleService getBleServiceInstance()
    {
        if(mbleService != null) {
            return mbleService;
        }
        return null;
    }

    private void openBleService() {
        mLoaclServiceConnection = new LoaclServiceConnection();
        Intent gattServiceIntent = new Intent(this, bleService.class);
        bindService(gattServiceIntent, mLoaclServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {

        super.onResume();
        mUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBaudRate(mBaudratei115200);
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        m_userHandler.removeCallbacks(invokeRun);
        unbindService(mLoaclServiceConnection);
        if (mSerial != null) {
            mSerial.end();
            mSerial = null;
        }

        super.onDestroy();
    }


    public void clk_start(View view) {
        threadStart();
    }

    public void clk_stop(View view) {
        threadStop();
    }

    private void threadStart() {
        if (GetThreadState() == THREAD_CLOSED) {
            clearView();
        }

        if (GetThreadState() == THREAD_CLOSED) {
            SetThreadState(THREAD_RUNNING);
            CountTimeThread();
            m_userHandler.post(invokeRun);
        }
    }

    private void threadStop() {
        if (GetThreadState() == THREAD_RUNNING) {
            SetThreadState(THREAD_CLOSING);
        }
    }

    public boolean isThreadRunning() {
        if (GetThreadState() == THREAD_RUNNING) {
            return true;
        } else {
            return false;
        }
    }

    public int GetThreadState() {
        return thread_State;
    }

    public void SetThreadState(int threadState) {
        thread_State = threadState;
    }

    final int THREAD_RUNNING = 1;
    final int THREAD_CLOSING = 0;
    final int THREAD_CLOSED = -1;
    int thread_State = THREAD_CLOSED;

    private Runnable invokeRun = new Runnable() {
        @Override
        public void run() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Log.d(TAG, "run on ui thread");
            }
            try {//TODO
                for (int i = 0; i < 1 && isThreadRunning() ;) {
                    Log.w(TAG, "invokeAllTest:" + Integer.toString(i));
                    //invokeTest();
                    invokeTest2();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //all work done
            SetThreadState(THREAD_CLOSED);
            writeStatus("stop");
        }
    };

    private void invokeTest2() throws IllegalAccessException {
        AssetManager asset = getAssets();
        Log.d(TAG, "invokeTest start");
        try {
            InputStream in_stream = asset.open("76xx.xml");
            String mode = "";
            if (0 == GetTestMode()) {
                mode = "SPI";
            } else if(1 == GetTestMode()){
                mode = "UART";
            }else {
                mode = "AIR_CMD";
            }

            List<BLE_testItem> list = XmlParser.getTestItems(in_stream, mode);
            for (BLE_testItem item : list) {
                if (!isThreadRunning()) {
                    break;
                }
                Method method;
                try {
                    //call function in bd_obj class
                    Log.w(TAG, "start test:" + item.gettestName());
                    method = bd_obj.getClass().getDeclaredMethod(item.gettestName(), BLE_testItem.class);
                    int ret = (int) method.invoke(bd_obj, item);
                    Log.d(TAG, "ret:" + ret);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            list.clear();
            commonutil.wdbgLogcat(TAG, 0,"one round done");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void invokeTest() throws IllegalAccessException {
        AssetManager asset = getAssets();
        Log.d(TAG, "invokeTest start");
        //parse test function
        testFuncList.clear();
        try {
            InputStream input = asset.open("bd_testfuncs.xml");
            List<BDTestitem> list = Parserfuncs.getTestFunctions(input);
            for (BDTestitem item : list) {
                testFuncList.add(item.getName());
                Log.d(TAG,item.getName());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        while(testFuncList.size() > 0 && isThreadRunning()) {
            Log.d(TAG, "---------------------" + testFuncList.getFirst());
            writeStatus(testFuncList.getFirst());
            Method method;
            try {
                //call function in bd_obj class
                method = bd_obj.getClass().getDeclaredMethod(testFuncList.getFirst());
                method.invoke(bd_obj);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            testFuncList.removeFirst();
        }
        Log.d(TAG, "test done");

    }

    public byte[] string2Bytes(String inString){
        return string2Bytes(inString,0x00);
    }

    public byte[] string2Bytes(String inString,int fun){
        byte[] newbyte=new byte[0];
        byte[] tmpbyte=new byte[0];

        if(inString==null || inString=="")//return len=0 array
            return tmpbyte;

        if(inString.length()%2==1){ //odd
            if((fun & _C_ADD_0_BACK_)>0){
                inString=inString+"0";
            }
            else{
                inString="0"+inString;
            }
        }

        newbyte=new byte[(inString.length()/2)];
        tmpbyte=inString.getBytes();
        int k=0;
        for(int i=0;i<newbyte.length;i++){
            if((fun&_C_LOW_BYTE_FIRST_)==_C_LOW_BYTE_FIRST_){
                k=(newbyte.length-1)-i;
            }
            else if(i>0){
                k++;
            }
            newbyte[k]|=getHalfHex(tmpbyte[i*2]);
            newbyte[k]<<=4;
            newbyte[k]|=getHalfHex(tmpbyte[i*2+1]);
//System.out.println("0:newbyte["+i+"]:"+newbyte[i]);
        }

        return newbyte;
    }

    public byte getHalfHex(byte inputbyte){

        if (inputbyte >= 0x30 && inputbyte <= 0x39) {//0~9
            return (byte) (inputbyte - 0x30);
        } else if (inputbyte >= 0x41 && inputbyte <= 0x46) {//A~F

            return (byte) bytearray[inputbyte - 0x41 + 10];
        } else if (inputbyte >= 0x61 && inputbyte <= 0x66) {//a~f

            return (byte) bytearray[inputbyte - 0x61 + 10];
        } else {

            return (byte) 0x00;
        }
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        int permission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //未取得權限，向使用者要求允許權限
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    synchronized public void writeLog2View(final String log) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rowItems.size() > 20) {
                    rowItems.removeFirst();
                }
                RowItem item = new RowItem(log, "", "");
                rowItems.add(item);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void writeStatus(final String log) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_status.setText(log);
            }
        });
    }

    private void CountTimeThread() {
        Thread thread = new Thread() {
            int timeElapse = 0;
            @Override
            public void run() {
                try {
                    while (isThreadRunning()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeElapse = timeElapse + 1000;
                                tv_time.setText(getElapsedTimeMinutesSecondsString(timeElapse));
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
                Log.d(TAG, "thread stop");
            }
        };

        thread.start();
    }

    public static String getElapsedTimeMinutesSecondsString(int miliseconds) {
        int elapsedTime = miliseconds;
        String format = String.format("%%0%dd", 2);
        elapsedTime = elapsedTime / 1000;
        int mins = elapsedTime / 60;
        int hrs = elapsedTime / 3600;
        String seconds = String.format(format, elapsedTime % 60);
        String minutes = String.format(format, mins % 60);
        String hours = String.format(format, hrs);
        return hours + ":" + minutes + ":" + seconds;
    }

    private void write2_MainUI_Log(int level, final String log) {
        String writedlog = commonutil.wdbgLogcat(TAG, level, log);
        writeLog2View(writedlog);
    }
}
