package com.example.johnny_wei.bleuart.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class commonutil extends AppCompatActivity {
    commonutil() {
    }

    final static StackTraceElement[] ste = Thread.currentThread().getStackTrace();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static final String mERROR = "      [Err]";
    static final String mDEBUG = "";
    static final String mWARRING = "  [Warring]";

    static final String path_SD = Environment.getExternalStorageDirectory().getAbsolutePath();
    static int suffix = 0;
    static String debugfileName = "debug_" + Integer.toString(suffix);
    static final String debugFolderName = "D_DEBUG";
    static final String path_AbsDebugfolder = path_SD + File.separator + debugFolderName + File.separator;

    private static final String TAG = "commonutil";
    private static final String endLine = "\n";

    public static class config {
        public static final boolean BLE = true;
        public static final boolean UART = true;
    }

    public static void init() {
        createFoler(debugFolderName);

    }


    static void writeLog(String data) {
        if (!checkExternalMedia()) {
            return;
        }
        data = getCurrentTimeStr() + " : " + data + endLine;
        try {
            String absPath = path_AbsDebugfolder + debugfileName;
            FileOutputStream output = new FileOutputStream(absPath, true);
            output.write(data.getBytes());  //write()寫入字串，並將字串以byte形式儲存。利用getBytes()將字串內容換為Byte
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static String getCurrentTimeStr() {
        Date dt = new Date();
        String dts = sdf.format(dt);
        return dts;
    }

    public static void wErrLog(String data) {
        if (!checkExternalMedia()) {
            return;
        }
        data = getCurrentTimeStr() + " : " + mERROR + data + endLine;
        try {
            String absPath = path_AbsDebugfolder + debugfileName;
            FileOutputStream output = new FileOutputStream(absPath, true);
            output.write(data.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static void wdbgLog(String data) {
        if (!checkExternalMedia()) {
            return;
        }
        data = getCurrentTimeStr() + " : " + mDEBUG + data + endLine;
        try {
            String absPath = path_AbsDebugfolder + debugfileName;
            FileOutputStream output = new FileOutputStream(absPath, true);
            output.write(data.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static String wdbgLogcat(String TAG, int level, String data) {
        if (!checkExternalMedia()) {
            return "";
        }

        String prefix;
        switch (level) {
            case 0:
                Log.d(TAG, data);prefix = mDEBUG;
                break;
            case 1:
                Log.w(TAG, data);prefix = mWARRING;
                break;
            case 2:
                Log.e(TAG, data);prefix = mERROR;
                break;
            default:
                Log.d(TAG, data);prefix = mDEBUG;
        }

        if (!checkExternalMedia()) {
            return "";
        }

        String dataWrited = getCurrentTimeStr().trim() + " | ";
        dataWrited += data;
//        dataWrited = dataWrited + "  " + GetBrand() + "-" + GetDeviceModel() + "-" + GetSystemVersion() + endLine;
        data = getCurrentTimeStr().trim() + " : " + prefix + data + endLine;
        try {
            String absPath = path_AbsDebugfolder + debugfileName;
            FileOutputStream output = new FileOutputStream(absPath, true);
            output.write(data.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataWrited;
    }

    public static void writeToSDFile(String data) {
        Date dt = new Date();
        String dts = sdf.format(dt);
        data = dts + " : " + data;
        try {
            String absPath = path_SD + File.separator + debugFolderName + File.separator + debugfileName;
            FileOutputStream output = new FileOutputStream(absPath, true);
            output.write(data.getBytes());  //write()寫入字串，並將字串以byte形式儲存。利用getBytes()將字串內容換為Byte
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*create folder under sd path*/
    private static boolean createFoler(String folderName) {
        if (!checkExternalMedia()) {
            return false;
        }

        String absPath = path_SD + File.separator + folderName;
        Log.d(TAG, "createFoler path is " + absPath);
        File folder = new File(absPath);

        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (success) {
                Log.d(TAG, "create folder " + absPath + "success");
                return true;
            } else {
                Log.d(TAG, "create folder " + absPath + "fail");
                return false;
            }
        } else {
            Log.d(TAG, "folder exist");
        }

        return true;
    }

    static void writeLog(String filename, String data) {
        if (!checkExternalMedia()) {
            return;
        }
        writeToSDFile(filename, data);
        return;
    }

    public static void writeToSDFile(String filename, String data) {
        final String pathSD = Environment.getExternalStorageDirectory().getAbsolutePath();
        Date dt = new Date();
        String dts = sdf.format(dt);
        data = dts + " : " + data;
        try {
            FileOutputStream output = new FileOutputStream(pathSD + "/" + filename, true);
            output.write(data.getBytes());  //write()寫入字串，並將字串以byte形式儲存。利用getBytes()將字串內容換為Byte
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*check external storage permission*/
    private static boolean checkExternalMedia() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //Log.d(TAG, "sd card mounted with read/write access");
            return true;
        } else {
            Log.d(TAG, "mount fail state : " + state);
        }
        return false;
    }

    public static String bytes2String(byte[] inputbyte){
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

    public byte[] string2Bytes(String inString){
        return string2Bytes(inString,0x00);
    }

    public static final int _C_LOW_BYTE_FIRST_    =0x0001;
    public static final int _C_ADD_0_BACK_        =0x0002;

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

    private final byte[] bytearray={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F};
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

    public static String GetDeviceModel(){
        return Build.MODEL;
    }

    public static String GetSystemVersion(){
        return Build.VERSION.RELEASE;
    }

    public static String GetBrand(){
        return Build.BRAND;
    }

    public static int GetVersionSDK(){
        return Build.VERSION.SDK_INT;
    }


}
