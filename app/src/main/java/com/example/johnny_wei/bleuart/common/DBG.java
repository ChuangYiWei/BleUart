package com.example.johnny_wei.bleuart.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

//----------------------------------------android

/**
 * this class use for show or save debug message
 */
public class DBG{
/*********************************************************************************************************************//**
     * DBG
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    //==================================================================
    //put those two string here for update
    // private final String versionx="2017/06/10 22:40:31";
    //put those two string here for update
    //DBG ==============================================================
//    private final String className=getClass().getName();
//    private final String dbgName=className.toLowerCase()+".dbg";
//    private final String TAG=getClass().getSimpleName();
    private static final String dbgExt=".dbg";
//  private DBG dbgMsg=new DBG(dbgName,0x01);
/*********************************************************************************************************************//**
     * boolean
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * byte
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * int
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    private int dbgEn=0;
    public static final int _ShowOnScreen_      =0x0001;
    public static final int _SaveFile_		    =0x0002;
    public static final int _ShowDeltaTime_	    =0x0004;
    public static final int _KeepFile_	        =0x0008;

    public static final int _SingleFile_	    =0x0010;
    public static final int _ShowLog_	        =0x0020;

    public static final int _NotShowInitTime_   =0x0080;
    //addTab
    private final int _TAB_SPACE_ =8;//1 tab=8 space
    private final int _TAB_MAX_   =4;//max tab
    //int ==============================================================
    private int FilelAreadyCreated=0;


    //    private int funEn=0;
    private final int _B_SHOW_VERSION_   =0x01;
/*********************************************************************************************************************//**
     * float
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * long
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    private final long dbgMaxSize=1000000;
/*********************************************************************************************************************//**
     * string
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
//----------------------------------------android_start
    private String PACKAGE=getClass().getPackage().getName();
    private static final String pathSD=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String pathDBG="00_dbg";//default folder,can update by R.string.dbg_folder
    private String dbgFolder=pathSD
            +"/"+pathDBG
            +"/"+PACKAGE;
    //----------------------------------------android
//----------------------------------------java_start
//    private String PACKAGE=getClass().getName();
//    private static final String pathSD=".";
//    private String dbgFolder=".";
//----------------------------------------java
//    String newLine=System.getProperty("line.separator");
//    private final String newLine=System.getProperty("line.separator");//force use in windows
    private final String newLine="\r\n";//force use in windows
    private String logHead="dbg";
/*********************************************************************************************************************//**
     * others
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    private StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    private String fileName0=stackTraceElements[3].getFileName()+".dbg";;
    private String fileName=fileName0.substring(0,fileName0.indexOf("."))+".dbg";;
    private Date date=new Date();
    private Locale locale=new Locale("en","US");
    private DateFormat mediumFormat=DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,locale);
    private long oldtime=System.currentTimeMillis();

    private File f;
    //----------------------------------------android_start
    private static Context context=null;
//----------------------------------------android



/*********************************************************************************************************************//**
     * FUNCTION
     *************************************************************************************************************************/
    /**
     * get class version
     *
     * @return      (String)    :class version(date+time)
     */
    // public String get_version(){
    //     return versionx;
    // }
    /**
     * get class version,can show extra message
     *
     * @param    fun    (int)       :show extra function
     *                               <br>_B_SHOW_VERSION_      =0x0001:show version control
     * @return          (String)    :class version(date+time)
     */
//	public String get_version(int fun){
//        String vString=versionx+"\r\n";
//
//        if((fun & _B_SHOW_VERSION_)==_B_SHOW_VERSION_){
//            vString+=getClass().getName()+".java\r\n";
//
//            vString+="v1.1 2015/11/03\r\n";
//            vString+="     1.dbgFolder can update by R.string.dbg_folder\r\n";
//            vString+="v1.0 2015/10/31\r\n";
//            vString+="     1.fix DBG message(can write to seperate or single file)\r\n";
//            vString+="v0.1 2015/08/27\r\n";
//            vString+="     1.add onlyShow() for only show on screen not save\r\n";
//            vString+="v0.0 2014/09/28\r\n";
//        	vString+="     1.fix checkString count should --\r\n";
//            vString+="2014/08/25\r\n";
//            vString+="     1.modify comment\r\n";
//            vString+="2014/08/19\r\n";
//            vString+="     1.sync\r\n";
//            vString+="2013/12/01\r\n";
//            vString+="     1.modify for format\r\n";
//            vString+="     2.set_dbgEn    ShowOnScreen    (0x01):show Msg on screen\r\n";
//            vString+="                    SaveFile        (0x02):save Msg to file\r\n";
//            vString+="                    ShowDeltaTime   (0x04):show Msg delta time\r\n";
//            vString+="                    KeepFile        (0x08):append Msg to file\r\n";
//        }
//
//		return vString;
//	}
    /**
     * get class debug setting
     *
     * @return          (int)   :class debug setting
     *                           <br>_ShowOnScreen_      =0x0001
     *                           <br>_SaveFile_          =0x0002
     *                           <br>_ShowDeltaTime_     =0x0004
     *                           <br>_KeepFile_          =0x0008
     */
    public int get_dbgEn(){
        return this.dbgEn;
    }



    //================================================================================
    //set dbgEn
    //================================================================================
    //by int------------------------------------------------------------
    /**
     * set class debug setting by int
     *
     * @param    dbg    (int)   :class debug setting
     *                           <br>_ShowOnScreen_      =0x0001
     *                           <br>_SaveFile_          =0x0002
     *                           <br>_ShowDeltaTime_     =0x0004
     *                           <br>_KeepFile_          =0x0008
     */
    public void set_dbgEn(int dbg){
        dbgEn= dbg;
    }
//     /*
//     * set class debug setting by int,set DBG setting by int
//     *
//     * @param    dbg            (int)   :class debug setting
//     * @param    dbgMsgEn       (int)   :DBG setting
//     *                                   <br>_ShowOnScreen_      =0x0001
//     *                                   <br>_SaveFile_          =0x0002
//     *                                   <br>_ShowDeltaTime_     =0x0004
//     *                                   <br>_KeepFile_          =0x0008
//     */
//  public void set_dbgEn(int dbg,int dbgMsgEn){
//      set_dbgEn(dbg);
//
//      if(dbgEn>0){
//          dbgMsg=new DBG(dbgName,dbgMsgEn);
//      }
//  }
    //by string---------------------------------------------------------
    /**
     * set class debug setting by string
     *
     * @param    fString    (String)   :class debug setting
     *                           <br>_ShowOnScreen_      =0x0001
     *                           <br>_SaveFile_          =0x0002
     *                           <br>_ShowDeltaTime_     =0x0004
     *                           <br>_KeepFile_          =0x0008
     */
//----------------------------------------android_start
    public void set_dbgEn(String fString){
        fString=fString.toLowerCase();

        dbgEn=0;//set 0 at first

        if(isHex(fString)){
            if(fString.toLowerCase().startsWith("0x"))
            {
                fString=fString.substring(2,fString.length());
            }

            dbgEn=Integer.parseInt(fString,16);
        }
        else{
            dbgEn=checkdbgEnString(fString);
        }
    }
//----------------------------------------android
//     /*
//     * set class debug setting by string,DBG setting by String
//     *
//     * @param    fString        (String)   :class debug setting
//     * @param    dbgMsgEn       (int)   :DBG setting
//     */
//
//  public void set_dbgEn(String fString,int dbgMsgEn){
//         fString=fString.toLowerCase();
//
//         dbgEn=0;//set 0 at first
//
//         if(isHex(fString)){
//             dbgEn=Integer.parseInt(fString,16);
//         }
//         else{
//             dbgEn=checkdbgEnString(fString);
//         }
//
//      if(dbgEn>0){
//          dbgMsg=new DBG(dbgName,dbgMsgEn);
//      }
//  }

    /**
     * parase class debug setting by string
     *
     * @param    fString        (String)   :class debug setting in string
     *                           <br>ShowOnScreen      =0x0001
     *                           <br>SaveFile          =0x0002
     *                           <br>ShowDeltaTime     =0x0004
     *                           <br>KeepFile          =0x0008
     *
     * @return                  (int)   :class debug setting in int
     */
//----------------------------------------android_start
    private int checkdbgEnString(String fString){
        int tmpdbgEn=0;
        String checkString="";
        if(isValid(fString)){
            for(int i=0;i<fString.length();i++){
                checkString="ShowOnScreen";
                if(fString.startsWith(checkString.toLowerCase(),i)){
                    tmpdbgEn|=_ShowOnScreen_;
                    i+=checkString.length()-1;
                }
                checkString="SaveFile";
                if(fString.startsWith(checkString.toLowerCase(),i)){
                    tmpdbgEn|=_SaveFile_;
                    i+=checkString.length()-1;
                }
                checkString="ShowDeltaTime";
                if(fString.startsWith(checkString.toLowerCase(),i)){
                    tmpdbgEn|=_ShowDeltaTime_;
                    i+=checkString.length()-1;
                }
                checkString="KeepFile";
                if(fString.startsWith(checkString.toLowerCase(),i)){
                    tmpdbgEn|=_KeepFile_;
                    i+=checkString.length()-1;
                }
            }
        }

        return tmpdbgEn;
    }
//----------------------------------------android
    /**
     * get class help
     *
     * @return          (String)    :help message
     */
//	public String getHelp(){
//        String hString=versionx+"\r\n";
//        hString+=getClass().getName()+".java\r\n";
//
//		hString+="     1.add setFun,getFun for function set,get\r\n";
//        hString+="           set_dbgEn(0x01):_ShowOnScreen_\r\n";
//        hString+="           set_dbgEn(0x02):_SaveFile_\r\n";
//        hString+="           set_dbgEn(0x04):_ShowDeltaTime_\r\n";
//        hString+="           set_dbgEn(0x08):_KeepFile_\r\n";
//        hString+="           set_dbgEn(0x10):_SingleFile_\r\n";
//
//		return hString;
//	}
    //================================================================================
    //set fun
    //================================================================================
    //by int------------------------------------------------------------
    /*
     * set class function by int
     *
     * @param    fun    (int)   :class function by int
     */
//	public void set_funEn(int fun){
//		funEn=fun;
//	}
    //by string---------------------------------------------------------
    /*
     * set class function by string
     *
     * @param    fString    (int)   :class function
     */
//	public void set_funEn(String fString){

//        fString=fString.toLowerCase();
//        funEn=0;//set 0 at first
//        //funEn
//        if(ct.isHex(fString)){
//            funEn=Integer.parseInt(fString,16);
//        }
//        else{
//            funEn=checkfunEnString(fString);
//        }
//	}


    public String getFileName(){
        return this.fileName;
    }
    //==============================================================================================
    public String getDbgFolder(){
        File f=new File(this.fileName);
        return f.getParent();
    }
    //==============================================================================================
    //save activity file
    //SD/Application/com.baboo.android.ble
    public String getFileFolder(){
//----------------------------------------android_start
        return getFileFolder(getContext().getPackageName());
//----------------------------------------android
//----------------------------------------java_start
//        return "";
//----------------------------------------java
    }
    //----------------------------------------android_start
    public String getFileFolder(String folder){
        //1:set first
        String fileFolder="Application";
        //2:read resource
        String tmpS0=getStringIfExist("file_folder");
        if(!tmpS0.equals("")){
            fileFolder=tmpS0;
        }
//dbgMsg.save(className + ".fileFolder.1="+fileFolder);
        fileFolder= pathSD
                +File.separator
                +fileFolder
                +File.separator
                +folder
        ;
        return fileFolder;
    }
    //----------------------------------------android
    //==============================================================================================
//----------------------------------------android_start
    private String getStringIfExist(String searchString) {
        String rS="";
        int sID = getContext().getResources().getIdentifier(searchString, "string", getContext().getPackageName());

        if (sID > 0) {
            rS = getContext().getResources().getString(sID);
        }

        return rS;
    }
    //----------------------------------------android
    //==============================================================================================
    public void setFile(String fName){
        String dFolder="";
        String[] tmpS=new String[1];
//Log.d("dbg","getContext().getPackageName()="+getContext().getPackageName());
//----------------------------------------android_start
        dbgFolder=pathSD
                +"/"+pathDBG
                +"/"+getContext().getPackageName();

        PACKAGE=getContext().getPackageName();
        //check res exist or not
        tmpS[0]=getStringIfExist("dbg_folder");
//----------------------------------------android
        if(isValid(tmpS[0])){
            dFolder=tmpS[0];
        }

        //update folder
        if(!dFolder.equals("")) {
            dbgFolder = pathSD
                    + "/" + dFolder
                    + "/" + PACKAGE;
        }
//----------------------------------------java_start
//dbgFolder=".";//force in root
//----------------------------------------java

        if((dbgEn & _SingleFile_)>0){
            this.fileName = dbgFolder
                    + "/" + PACKAGE+".dbg";
        }
        else {
            this.fileName = dbgFolder
                    + "/" + fName;
        }

        FilelAreadyCreated=0;

        File f=new File(this.fileName);

        if(f.exists()) {
            logHead = f.getName();

            if ((dbgEn & _KeepFile_) > 0) {
                if (f.length() > dbgMaxSize) {
                    f.delete();
                }
            }
        }

    }
    //----------------------------------------android_start
    public static Context getContext(){

        return context;
    }
//----------------------------------------android
    /*
     * parase class function string
     *
     * @param    fString    (String)   :class function
     * @return              (int)   :int
     */
//    private int checkfunEnString(String fString){
//        int tmpfunEn=0;
//        String checkString="";
//        if(ct.isValid(fString)){
//            for(int i=0;i<fString.length();i++){
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunEn|=;
////                        i+=checkString.length()-1;
////                    }
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunEn|=;
////                        i+=checkString.length()-1;
////                    }
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunEn|=;
////                        i+=checkString.length()-1;
////                    }
//            }
//        }
//          return tmpfunEn;
//    }

    /*
     * class initial
     */
//  private void initial(){
//    }
    /**
     * constructor
     */
    public DBG(){
        setFile(fileName);
//System.out.println("DBG:"+stackTraceElements[2].getClassName());
//System.out.println("0="+stackTraceElements[0]);
//System.out.println("1="+stackTraceElements[1]);
//System.out.println("2="+stackTraceElements[2]);
//System.out.println("3="+stackTraceElements[3]);
    }
    /**
     * constructor
     *
     * param    function       (int)   :class function
     *                           <br>_ShowOnScreen_      =0x0001
     *                           <br>_SaveFile_          =0x0002
     *                           <br>_ShowDeltaTime_     =0x0004
     *                           <br>_KeepFile_          =0x0008
     */
    public DBG(int dbg){
//----------------------------------------android_start
        dbg=getContext().getResources().getInteger(dbg);//android
//----------------------------------------android

        set_dbgEn(dbg);
        setFile(fileName);
    }
    public DBG(int dbg,String tmps){
        set_dbgEn(dbg);
        setFile(fileName);
    }
    /**
     * constructor
     *
     * @param    dbgS       (String)    :dbg string
     *
     */
//----------------------------------------android_start
    public DBG(String dbgS){
        set_dbgEn(dbgS);
        setFile(fileName);
    }
//----------------------------------------android
    /**
     * constructor
     *
     * @param    fileName       (String)    :save file name
     *
     */
//----------------------------------------java_start
//    public DBG(String fName){
//        this.fileName=fName;
//        dbgEn=0;
//System.out.println("DBG.02");
//    }
//----------------------------------------java
    /**
     * constructor
     *
     * param    fileName       (String)    :save file name
     * @param    dbg            (int)       :local function
     *                           <br>_ShowOnScreen_      =0x0001
     *                           <br>_SaveFile_          =0x0002
     *                           <br>_ShowDeltaTime_     =0x0004
     *                           <br>_KeepFile_          =0x0008
     */
    public DBG(String fName,int dbg){
        set_dbgEn(dbg);
        setFile(fName);
    }
    //----------------------------------------android_start
    public DBG(Context con,String fName){
        this.context=con;
        setFile(fName);
    }

    public DBG(Context con,int function){
        context = con;
        function=getContext().getResources().getInteger(function);
        set_dbgEn(function);
        setFile(fileName);
    }
//----------------------------------------android


    /**
     * constructor
     *
     * @param    fileName       (String)    :save file name
     * @param    fString        (String)    :local function
     *                           <br>ShowOnScreen      =0x0001
     *                           <br>SaveFile          =0x0002
     *                           <br>ShowDeltaTime     =0x0004
     *                           <br>KeepFile          =0x0008
     */
    // public DBG(String fileName,String fString){
    //     this.fileName=fileName;
    //     set_dbgEn(fString);
    // }
    /**
     * create file
     * <br>effect:dbgEn:_SaveFile_          =0x0002
     * <br>effect:dbgEn:_KeepFile_          =0x0008
     *
     * param    fileName       (String)    :save file name
     */
    private void createFile(String fName){
        if((dbgEn&_SaveFile_)==0)
            return;

        try{
            f=new File(fName);
//System.out.println("createFile.fileName="+fName);
            if(!f.exists())
            {

                File fFolder=new File(".");

                if(isValid(f.getParent())){
                    fFolder=new File(f.getParent());
                }

                if(!fFolder.exists()) {
                    fFolder.mkdirs();
                }
                f.createNewFile();
            }

            BufferedWriter bufferedwriter;

            if((dbgEn&_KeepFile_)>0)
            {
                bufferedwriter=new BufferedWriter(new FileWriter(fName,f.exists()));
            }
            else
            {
                bufferedwriter=new BufferedWriter(new FileWriter(fName,false));
            }


            if((dbgEn & _NotShowInitTime_)==0)
            {
                bufferedwriter.write(newLine);
                bufferedwriter.write("-------------------------");
                bufferedwriter.write(newLine);
                bufferedwriter.write(mediumFormat.format(date));
                bufferedwriter.write(newLine);
                bufferedwriter.write("-------------------------");
                bufferedwriter.write(newLine);
            }
            bufferedwriter.close();
        }
        catch(ArrayIndexOutOfBoundsException e){
            //e.printStackTrace();
            showMessage("DBG.createFile.0=" + e.toString());
        }
        catch(IOException e){
            //e.printStackTrace();
            showMessage("DBG.createFile.1=" + e.toString());
        }
    }

    /**
     * save DBG message in file and can add newline (force show)
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     *
     * @param    message    (String)    :message to save
     * @param    newline    (boolean)   :0:no append newline    1:append new line
     */
    public void show(String message,boolean newline){
        int d=this.get_dbgEn();
        this.set_dbgEn(d|0x01);
        this.save(message,newline);
        this.set_dbgEn(d);
    }
    /**
     * save DBG message in file and can add newline (only show)
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     *
     * @param    message    (String)    :message to save
     * @param    newline    (boolean)   :0:no append newline    1:append new line
     */
    public void onlyShow(String message,boolean newline){
        int d=this.get_dbgEn();
        this.set_dbgEn(0x01);
        this.save(message,newline);
        this.set_dbgEn(d);
    }
    /**
     * save DBG message in file
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     *
     * @param    message    (String)    :message to save
     */
    public void save(String message){
        if(FilelAreadyCreated==0){
            createFile(this.fileName);
            FilelAreadyCreated=1;
        }
        showMessage(message);
        saveMessage(message, true);
    }
    public void save(String message,int dbg){
        int d=this.get_dbgEn();

        this.set_dbgEn(dbg & d);

        this.save(message);

        this.set_dbgEn(d);
    }
    public void saveforce(String message,int dbg){
        int d=this.get_dbgEn();

        this.set_dbgEn(dbg);

        this.save(message);

        this.set_dbgEn(d);
    }
    /**
     * save DBG message in file and can add newline
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     *
     * @param    message    (String)    :message to save
     * @param    newline    (boolean)   :0:no append newline    1:append new line
     */
    public void save(String message,boolean newline){
        if(FilelAreadyCreated==0){
            createFile(this.fileName);
            FilelAreadyCreated=1;
        }
//----------------------------------------android_start
        showMessage(message);
//----------------------------------------android
//----------------------------------------java_start
//        showMessage(message, newline);
//----------------------------------------java
        saveMessage(message, newline);
    }
    public void save(String message,boolean newline,int dbg){
        int d=this.get_dbgEn();
        this.set_dbgEn(dbg & d);
        save(message, newline);
        this.set_dbgEn(d);
    }
    /**
     * save DBG message in file (forece show)
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     *
     * @param    message    (String)    :message to save
     */
    public void show(String message){
        int d=this.get_dbgEn();
        this.set_dbgEn(d|0x01);
        this.save(message);
        this.set_dbgEn(d);
    }
    /**
     * save DBG message in file (only show)
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     *
     * @param    message    (String)    :message to save
     */
    public void onlyShow(String message){
        int d=this.get_dbgEn();
        this.set_dbgEn(0x01);
        this.save(message);
        this.set_dbgEn(d);
    }
    /**
     * save new line in file
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     */
    public void save(){
        this.save("");
    }
    /**
     * save new line in file  (forece show)
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     */
    public void show(){
        int d=this.get_dbgEn();
        this.set_dbgEn(d|0x01);
        this.save();
        this.set_dbgEn(d);
    }
    /**
     * save new line in file  (only show)
     * <br>effect:dbgEn:_ShowOnScreen_      =0x0001
     * <br>effect:dbgEn:_ShowDeltaTime_     =0x0004
     */
    public void onlyShow(){
        int d=this.get_dbgEn();
        this.set_dbgEn(0x01);
        this.save();
        this.set_dbgEn(d);
    }
    /**
     * save double in file,convert to String frist than save
     *
     * @param    num    (double)    :message to save
     */
    public void save(double num){
//Double.MIN_VALUE
        save(String.valueOf(num));
//        save(String.valueOf(num),false);
    }
    /**
     * save double in file,convert to String frist than save
     *
     * @param    num        (double)    :message to save
     * @param    newline    (boolean)   :0:no append newline         1:append new line
     */
    public void save(double num,boolean newline){
        save(String.valueOf(num),newline);
    }
    /**
     * debug messsage add tab
     *
     * @param    tabL       (int)    :variabe string,use this to check tab number
     * @param    shiftL     (int)    :default string length,use this to check tab number
     */
    public void addTab(int tabL,int shiftL){
        int tmpCount=0;

        while(tmpCount<=_TAB_MAX_){
            if((tabL+shiftL)<_TAB_SPACE_*tmpCount){
                save("\t",false);
            }
            tmpCount++;
        }
    }
    /**
     * check String is valid or not
     * <br>String must length!=0 and not equal to "" or null
     *
     * @param    inString       (String)    :message to save
     * @return                  (boolen)    :0:true   1:false
     */
    private boolean isValid(String inString){

        if(inString==null || inString=="")
            return false;
        else if(inString.length()==0)
            return false;
        else
            return true;
    }
    /**
     * check is hex or not
     * <br>String must valid and value range 0x30~0x39/0x41~0x46/0x61~0x66
     *
     * @param    inString       (String)    :message to save
     * @return                  (boolen)    :0:true   1:false
     */
    private boolean isHex(String inString){
        boolean rboolean=true;

        if(!isValid(inString))
            return false;

        if(inString.toUpperCase().indexOf("0X")==0){
//dbgMsg.save("0x");
//dbgMsg.save("inString="+inString);
            inString=inString.substring(2,inString.length());
//dbgMsg.save("inString="+inString);
        }
        for(int i=0;i<inString.length();i++){
            if(     !(inString.charAt(i)>=0x30 && inString.charAt(i)<=0x39)  //0~9
                    &&   !(inString.charAt(i)>=0x41 && inString.charAt(i)<=0x46)  //A~F
                    &&   !(inString.charAt(i)>=0x61 && inString.charAt(i)<=0x66)  //a~f
                    ){
                rboolean=false;
            }
        }

        return rboolean;
    }
    Long dTime_0=System.currentTimeMillis(),dTime_1=System.currentTimeMillis();


    Long deltaTime(){

        Long rlong;

        dTime_1=System.currentTimeMillis();
        rlong=(dTime_1-dTime_0);

        dTime_0=dTime_1;

        return rlong;
    }



    void showDeltaTime(int timeCount){

        Long tLong=this.deltaTime();
        int tmpdbg=this.get_dbgEn();


        if(tLong>10){
            this.set_dbgEn(tmpdbg|0x02);
            this.save("deltaTime("+timeCount+")="+tLong);
            this.set_dbgEn(tmpdbg);
        }

    }






    void saveMessage(String msg,boolean newline){

        if((dbgEn&_SaveFile_)>0){
            try{
                BufferedWriter bufferedwriter=new BufferedWriter(new FileWriter(this.fileName,true));

                if((dbgEn&_ShowDeltaTime_)>0){
                    bufferedwriter.write("deltaTime="+String.valueOf(System.currentTimeMillis()-oldtime)+" ms");
                    oldtime=System.currentTimeMillis();
                    if(newline) {
                        bufferedwriter.write(newLine);
                    }
                }

                bufferedwriter.write(msg);

                if(newline) {
                    bufferedwriter.write(newLine);
                }
                bufferedwriter.close();

            }
            catch(ArrayIndexOutOfBoundsException e){
                showMessage("DBG.save.0." + e.toString());

            }
            catch(IOException e){
                showMessage("DBG.save.0." + e.toString());

            }
        }
    }
    //==============================================================================================
    void showMessage(String msg) {
//----------------------------------------android_start
        if(context!=null) {
            if((dbgEn&_ShowOnScreen_)>0) {
                Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
            }
            if((dbgEn&_ShowLog_)>0) {
                Log.d("dbg",msg);
            }
        }
//----------------------------------------android
//----------------------------------------java_start
//        System.out.println(msg);
//----------------------------------------java
    }
    //==============================================================================================
//----------------------------------------java_start
//    void showMessage(String msg,boolean newline) {
//        System.out.print(msg);
//        if(newline)
//            System.out.println();
//    }
//----------------------------------------java
    //==============================================================================================
}

