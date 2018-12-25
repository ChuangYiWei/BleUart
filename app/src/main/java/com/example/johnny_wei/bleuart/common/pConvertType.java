package com.example.johnny_wei.bleuart.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pConvertType{
/*********************************************************************************************************************//**
     * DBG
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    //==================================================================
    // private final String versionx="2017/04/26 00:25:43";
    //DBG ==============================================================
    private final String TAG=getClass().getSimpleName();
    private final String PACKAGE=getClass().getName();
    private final String dbgExt=".dbg";
    private DBG dbgMsg;
/*********************************************************************************************************************//**
     * boolean
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * byte
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    private final byte[] bytearray={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F};
/*********************************************************************************************************************//**
     * int
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
//  private final int _ShowOnScreen_    =0x01;
//  private final int _SaveFile_        =0x02;
//  private final int _ShowDeltaTime_   =0x04;
//  private final int _KeepFile_        =0x08;

    private int dbgEn=0;
    final int _DBG_getHalfHex_    =0x01;
    final int _DBG_string2Map_    =0x02;
    //int ==============================================================
    //get_version
    public static final int _B_SHOW_VERSION_      =0x0001;
    public static final int _C_LOW_BYTE_FIRST_    =0x0001;
    public static final int _C_ADD_0_BACK_        =0x0002;
    //bin2Hex
    public static final int _D_LOW_BIT_FIRST_     =0x0001;
    public static final int _D_NIBBLE_SWITCH_     =0x0002;
    public static final int _D_PADDING_FRONT_     =0x0004;//0001->000000001

    //int2String
    public static final int _E_DECIMAL_                =0x0001;
    public static final int _E_REMOVE_0_               =0x0002;//remove all 0 infront
    public static final int _E_NO_SIGN_                =0x0004;
    public static final int _E_HEXADECIMAL_            =0x0008;
    public static final int _E_NULL_RETURN_0_          =0x0010;
    public static final int _E_REMOVE_0_KEEP_LAST_     =0x0020;//keep last 0 infront
    public static final int _E_ADD_0_FRONT_            =0x0040;//padding 0 at first
/*********************************************************************************************************************//**
     * float
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * long
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * string
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
//    private static final String pathSD=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String pathSD="";
    private static final String pathDBG="dbg";

    private final String dbgFolder=pathSD
            +"/"+pathDBG
            +"/"+PACKAGE;

    private final String dbgPath=dbgFolder
            +"/"+TAG+dbgExt
            ;
    //String ===========================================================
    private final String _ITEM_SEP_=",",_VALUE_SEP_="/";
    private final String _MAP_HEAD_="{",_MAP_TAIL_="}";
    //String[] ===========================================================
    public final String[] NibbleHex={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public final String[] NibbleBin={  "0000","0001","0010","0011",
            "0100","0101","0110","0111",
            "1000","1001","1010","1011",
            "1100","1101","1110","1111",};
/*********************************************************************************************************************//**
     * others
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    StringBuilder convertStringBuilder0=new StringBuilder(100);

/*********************************************************************************************************************//**
     * FUNCTION
     *************************************************************************************************************************/
    /**
     * get class version
     *
     * @return      (String)    :class version(date+time)
     */
    // public String get_version(){
    // 	return versionx;
    // }

    /**
     * get class version,can show extra message
     *
     * param    fun    (int)       :show extra function
     *                               <br>_B_SHOW_VERSION_      =0x0001:show version control
     * @return          (String)    :class version(date+time)
     */
//     public String get_version(int fun){
//         String vString=versionx+"\r\n";
//         if((fun & _B_SHOW_VERSION_)==_B_SHOW_VERSION_){
//             vString+=getClass().getName()+".java\r\n";

//             vString+="v1.4 2016/05/23\r\n";
//             vString+="     1.add dbgExt\r\n";
//             vString+="     2.add bytes2Ints()\r\n";
//             vString+="     3.modify string2Map()\r\n";
//             vString+="v1.3 2016/03/17\r\n";
//             vString+="     1.modify string2Map()\r\n";
//             vString+="v1.2 2016/02/19\r\n";
//             vString+="     1.update unsigned int convert\r\n";

//             vString+="v1.1 2015/03/11\r\n";
//             vString+="     1.add int2Bytes for select digi\r\n";
// //            vString+="v1.0 2014/09/28\r\n";
// //            vString+="     1.fix checkString count should --\r\n";
// //            vString+="2014/08/25\r\n";
// //            vString+="     1.modify comment\r\n";
// //            vString+="2014/08/19\r\n";
// //            vString+="     1.sync\r\n";
// //            vString+="2013/12/01\r\n";
// //            vString+="     1.initial\r\n";
//         }
//         return vString;
//     }


    /*
     * get class debug setting
     *
     * @return          (int)   :class debug setting
     */

    public int get_dbgEn(){
        return dbgEn;
    }
    public int get_dbgMsgEn(){
        return dbgMsg.get_dbgEn();
    }
    //================================================================================
    //note:set debug,can be by int or string
    //in :-
    //out:-
    //================================================================================
    //by int------------------------------------------------------------
    /**
     * set class debug setting by int
     *
     * @param    dbg    (int)   :class debug setting
     */
    public void set_dbgEn(int dbg){
        dbgEn=dbg;

        if(dbgEn>0){
            if(dbgMsg==null){
                dbgMsg=new DBG(TAG+dbgExt,dbgEn);
            }
            else{
                dbgMsg.set_dbgEn(dbgEn);
            }
        }
    }

    /*
     * set class debug setting by int,set DBG setting by int
     *
     * @param    dbg            (int)   :class debug setting
     * @param    dbgMsgEn       (int)   :DBG setting
     *                                   <br>_ShowOnScreen_      =0x0001
     *                                   <br>_SaveFile_          =0x0002
     *                                   <br>_ShowDeltaTime_     =0x0004
     *                                   <br>_KeepFile_          =0x0008
     */

    public void set_dbgEn(int dbg,int dbgMsgEn){
        set_dbgEn(dbg);

        if(dbgEn>0){
            dbgMsg=new DBG(TAG+dbgExt,dbgMsgEn);
        }
    }

    //by string---------------------------------------------------------
    /*
     * set class debug setting by string
     *
     * @param    fString    (String)   :class debug setting
     */
/*
    public void set_dbgEn(String fString){

        fString=fString.toLowerCase();

        dbgEn=0;//set 0 at first
        //dbgEn
        if(isHex(fString)){
            dbgEn=Integer.parseInt(fString,16);
        }
        else{
            dbgEn=checkdbgEnString(fString);
        }
        //dbgMsgEn
        if(dbgEn>0){
            dbgMsg=new DBG(dbgName,0x01);
        }
    }
*/
    /*
     * set class debug setting by string,DBG setting by String
     *
     * @param    fString        (String)   :class debug setting
     *                                       <br>ByteModifyTx      =0x0001
     *                                       <br>ParseString       =0x0002
     * @param    dbgMsgEn       (int)   :DBG setting
     */
/*
    public void set_dbgEn(String fString,int dbgMsgEn){
        fString=fString.toLowerCase();

        dbgEn=0;//set 0 at first

        if(isHex(fString)){
            dbgEn=Integer.parseInt(fString,16);
        }
        else{
            dbgEn=checkdbgEnString(fString);
        }

        if(dbgEn>0){
            dbgMsg=new DBG(dbgName,dbgMsgEn);
        }
    }
*/
    /*
     * parase class debug setting by string
     *
     * @param    fString        (String)   :class debug setting in string
     * @return                  (int)   :class debug setting in int
     */
/*
    private int checkdbgEnString(String fString){
        int tmpdbgEn=0;

        String checkString="";
        if(isValid(fString)){
            for(int i=0;i<fString.length();i++){
                checkString="ShowOnScreen";
                    if(fString.startsWith(checkString.toLowerCase(),i)){
                        tmpdbgEn|=;
                        i+=checkString.length();
                    }
                checkString="SaveFile";
                    if(fString.startsWith(checkString.toLowerCase(),i)){
                        tmpdbgEn|=;
                        i+=checkString.length();
                    }
                checkString="ShowDeltaTime";
                    if(fString.startsWith(checkString.toLowerCase(),i)){
                        tmpdbgEn|=;
                        i+=checkString.length();
                    }
                checkString="KeepFile";
                    if(fString.startsWith(checkString.toLowerCase(),i)){
                        tmpdbgEn|=;
                        i+=checkString.length();
                    }
            }
        }

        return tmpdbgEn;
    }
*/
    /*
     * get class help
     *
     * @return          (String)    :help message
     */
/*
    public String getHelp(){
        String hString=versionx+"\r\n";
        hString+=getClass().getName()+".java\r\n";

        hString+="     1.add setFun,getFun for function set,get\r\n";
        hString+="           set_dbgEn(0x00):_ShowOnScreen_\r\n";
        hString+="           set_dbgEn(0x01):_SaveFile_\r\n";
        hString+="           set_dbgEn(0x04):_ShowDeltaTime_\r\n";
        hString+="           set_dbgEn(0x08):_KeepFile_\r\n";
        hString+="-dbgMsg   :dbg message setting\r\n";
        hString+="           0x0001:_ShowOnScreen_\r\n";
        hString+="           0x0002:_SaveFile_\r\n";
        hString+="           0x0004:_ShowDeltaTime_\r\n";
        hString+="           0x0008:_KeepFile_\r\n";
        return hString;
    }
*/
    //================================================================================
    //set fun
    //================================================================================
    //by int------------------------------------------------------------
    /*
     * set class function by int
     *
     * @param    fun    (int)   :class function by int
     */
//  public void set_funEn(int fun){
//      funEn=fun;
//  }
    //by string---------------------------------------------------------
    /*
     * set class function by string
     *
     * @param    fString    (int)   :class function
     */
//  public void set_funEn(String fString){

//        fString=fString.toLowerCase();
//        funEn=0;//set 0 at first
//        //funEn
//        if(ct.isHex(fString)){
//            funEn=Integer.parseInt(fString,16);
//        }
//        else{
//            funEn=checkfunEnString(fString);
//        }
//  }
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
////                        i+=checkString.length();
////                    }
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunEn|=;
////                        i+=checkString.length();
////                    }
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunEn|=;
////                        i+=checkString.length();
////                    }
//            }
//        }
//          return tmpfunEn;
//    }


    /**
     * class initial
     */
    // private void initial(){
    // }
    /**
     * constructor
     */
    public pConvertType(){
        // initial();
        set_dbgEn(0);

//        dbgMsg=new DBG(DBG.getContext().getResources().getInteger(R.integer.dbgPara));
//        dbgMsg=new DBG();
    }
/*
    public pConvertType(Context context){
        initial();
        set_dbgEn(0);

        dbgMsg=new DBG(DBG.getContext(),DBG.getContext().getResources().getInteger(R.integer.dbgPara));
    }
*/
    /**
     * check String is valid or not
     * <br>inString must be has length and not be "" or null
     * @param    inString       (String)    :String for check
     * @return                  (boolen)    :true:valid   false:not valid
     */
    public boolean isValid(String inString){

        if(inString==null || inString=="")
            return false;
        else if(inString.length()==0)
            return false;
        else
            return true;
    }
    public boolean isValid(String[] inString){

        if(inString==null)
            return false;
        else if(inString.length==0)
            return false;
        else
            return true;
    }

    public boolean isValid(StringBuilder inSB){

        if(inSB.length()==0)
            return false;
        else
            return true;
    }
    /**
     * check byte[] is valid or not
     * <br>inbyte[] must be has length and not be null
     *
     * @param    inbyte         (byte[])    :byte[] for check
     * @return                  (boolen)    :true:valid   false:not valid
     */
    public boolean isValid(byte[] inbyte){

        if(inbyte==null)
            return false;
        else if(inbyte.length==0)
            return false;
        else
            return true;
    }
    /**
     * check int is valid or not
     *
     * @param    inInt         (int)        :inInt for check
     * @return                 (boolen)    :true:valid   false:not valid
     */
    public boolean isValid(int inInt){
        return (inInt>=0) ? true:false;
    }
    /**
     * check int is valid or not
     *
     * @param    inLong         (long)        :inLong for check
     * @return                 (boolen)    :true:valid   false:not valid
     */
    public boolean isValid(long inLong){
        return (inLong>=0) ? true:false;
    }
    /**
     * check String is int format or not
     * <br>inString must be valid and value range between 0x30~0x39
     *
     * @param    inString       (String)    :String for check
     * @return                  (boolen)    :true:int   false:not int
     */
    public boolean isInt(String inString){
        boolean rboolean=true;

        if(!isValid(inString))
            return false;

        for(int i=0;i<inString.length();i++){
            if(inString.charAt(i)>0x39 || inString.charAt(i)<0x30){
                rboolean=false;
            }
        }

        return rboolean;
    }
    /**
     * check byte is int format or not
     * <br>inByte nibble must less than 9
     *
     * @param    inByte         (byte)      :byte for check
     * @return                  (boolen)    :true:int   false:not int
     */
    public boolean isInt(byte inByte){

        boolean rboolean=true;

        if((inByte>>4)>0x9 || (inByte&0x0F)>0x9)
        {
            rboolean=false;
        }

        return rboolean;
    }
    /**
     * check String is hex format or not
     * <br>inString must valid and value range between 0x30~0x39/0x41~0x43/0x61~0x66
     *
     * @param    inString       (String)    :String for check
     * @return                  (boolen)    :true:hex   false:not hex
     */

    public boolean isHexS(String inString){
//dbgMsg.save("isHexS.inString.0="+inString);
        return isHexS(string2Bytes(inString));
    }
    public boolean isHexS(byte[] inByte){
//dbgMsg.save("isHexS.inByte="+bytes2String(inByte));
        return isHex(new String(inByte));
    }

    public boolean isCharS(String inString){
//dbgMsg.save("isHexS.inString.0="+inString);
        return isCharS(string2Bytes(inString));
    }
    public boolean isCharS(byte[] inByte){
//dbgMsg.save("isHexS.inByte="+bytes2String(inByte));
        return isChar(new String(inByte));
    }

    /**
     * check String is hex format or not
     * <br>inString must valid and value range between 0x30~0x39/0x41~0x43/0x61~0x66
     *
     * @param    inString       (String)    :String for check
     * @return                  (boolen)    :true:hex   false:not hex
     */
    public boolean isHex(String inString){
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
//dbgMsg.save("inString.charAt(i)="+inString.charAt(i));
            if(     !(inString.charAt(i)>=0x30 && inString.charAt(i)<=0x39)  //0~9
                    &&   !(inString.charAt(i)>=0x41 && inString.charAt(i)<=0x46)  //A~F
                    &&   !(inString.charAt(i)>=0x61 && inString.charAt(i)<=0x66)  //a~f
                    ){
                rboolean=false;
                break;
            }
        }
//dbgMsg.save("rboolean="+rboolean);
        return rboolean;
    }

    public boolean isChar(String inString){
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
//dbgMsg.save("inString.charAt(i)="+inString.charAt(i));
            if(     !(inString.charAt(i)>=0x30 && inString.charAt(i)<=0x39)  //0~9
                    &&   !(inString.charAt(i)>=0x41 && inString.charAt(i)<=0x5A)  //A~Z
                    &&   !(inString.charAt(i)>=0x61 && inString.charAt(i)<=0x7A)  //a~z
                    ){
                rboolean=false;
                break;
            }
        }
//dbgMsg.save("rboolean="+rboolean);
        return rboolean;
    }

    public boolean isPrintableASCII(byte inByte){
        boolean rboolean=true;

        if(!isValid(inByte))
            return false;

//dbgMsg.save("----------");
//dbgMsg.save("pConvertType.isPrintableASCII.inByte.i="+inByte);

        if(!(inByte>=0x20 && inByte<0x7F)  //
                ){
            rboolean=false;
        }
//dbgMsg.save("rboolean="+rboolean);
        return rboolean;
    }

    public boolean isPrintableASCII(byte[] inByteA){
        boolean rboolean=true;

        if(!isValid(inByteA))
            return false;

        if(inByteA.length==0)
            return false;

        for(int i=0;i<inByteA.length;i++){
            if(!this.isPrintableASCII(inByteA[i])){
                return false;
            }
        }
//dbgMsg.save("rboolean="+rboolean);
        return rboolean;
    }

    public boolean isPrintableASCII(String inString){

        byte[] byteA=string2Bytes(inString,0x02);

        return isPrintableASCII(byteA);
    }
    /** ************************************************************************************************************
     * @brief    string to int
     *
     * //@param    [IN]    inI        (int):input int
     * //@param    [IN]    fun        (int):_E_DECIMAL_   =0x0001              <br>
     *
     * @note     ("0x457")=1111
     ***************************************************************************************************************/
    public int string2Int(String inString){
        return this.string2Int(inString, _E_DECIMAL_);
    }

    /** ************************************************************************************************************
     * @brief    string to int
     *
     * //@param    [IN]    inString   (String):input String
     * param    [IN]    fun        (int   ):_E_DECIMAL_   =0x0001              <br>
     *
     * @note     ("0x457",0)=0457,("0x457",1)=1111,("0x457",2)=457,(0x8000000A,0)=-2147483638
     ***************************************************************************************************************/
    public int string2Int(String inString,int fun){
        int rInt=-1;
        int digi=16;
        boolean minus=false;
//Log.d("dbg","inString.i="+inString);
        if(!isValid(inString) && (fun&_E_NULL_RETURN_0_)>0){
            return 0;
        }

        if(this.isValid(inString)){

            if (inString.indexOf(0)=='-') {
                minus = true;
                inString=inString.substring(1,inString.length());
//Log.d("dbg","inString="+inString);
            }

            if((fun&_E_DECIMAL_)>0){
                digi=10;
            }
            if((fun&_E_HEXADECIMAL_)>0){
                digi=16;
            }


            if(inString.toLowerCase().startsWith("0x"))
            {
//dbgMsg.save("tmpS="+tmpS);
                inString=inString.substring(2,inString.length());
//dbgMsg.save("tmpS="+tmpS);
                digi=16;
            }
            else
            {
                for(int i=0;i<inString.length();i++){
                    if(  (inString.charAt(i)>=0x41 && inString.charAt(i)<=0x46)  //A~F
                            ||   (inString.charAt(i)>=0x61 && inString.charAt(i)<=0x66)  //a~f
                            ){
                        digi=16;
                        break;
                    }
                }
            }
/*
if(digi==16){
dbgMsg.save("hex");
}
else{
dbgMsg.save("dec");
}
*/
//dbgMsg.save("inString="+inString);
//            Log.d("dbg","rInt.1="+rInt);

//            Log.d("dbg","inString.2="+inString);
//            rInt=Integer.parseInt(inString,digi);
            try {
                rInt = (int) Long.parseLong(inString, digi);//for unsigned int
            }
            catch(NumberFormatException e)
            {

            }
//            Log.d("dbg","rInt.2="+rInt);
        }
//dbgMsg.save("rInt="+rInt);
        return rInt;
    }
    public String ints2String(int[] inIArr,int fun){
        String rS="";
        for(int i=0;i<inIArr.length;i++){
            rS+=int2String(inIArr[i], fun);
        }
        return rS;
    }
    /** ************************************************************************************************************
     * @brief    int to string,convert to byte formate,4->04,16->10
     *
     * param    [IN]    inI        (int):input int
     * param    [IN]    fun        (int):_E_DECIMAL_   =0x0001              <br>
     *                                    _E_REMOVE_0_  =0x0002              <br>
     *
     * @note     (0x457)="1111"
     ***************************************************************************************************************/
    public String int2String(int inI){
        return int2String(inI, _E_DECIMAL_);
    }
    /** ************************************************************************************************************
     * @brief    int to string,convert to byte formate,4->04,16->10
     *
//     * param    [IN]    inI        (int):input int
//     * param    [IN]    fun        (int):_E_DECIMAL_   =0x0001              <br>
     *                                               _E_REMOVE_0_  =0x0002              <br>
     *
     * @note     (0x457,0)="0457",(0x457,1)="1111",(0x457,2)="457",
     *                Integer.toHexString(kk)// kk=0xFFFFFFFF,rString="FFFFFFFF"
     ***************************************************************************************************************/
    public String int2String(int inI,int fun){
        int digi=16,digix2=256;
        long tmpI0=0;
        String rString="";
        boolean minus=false;
//dbgMsg.set_dbgEn(0x03);
//        if((fun & _E_NO_SIGN_)==0)
        {
            if (inI < 0) {
                minus = true;
                if((fun & _E_NO_SIGN_)>0) {
                    inI &=0x7FFFFFFF;
                }
                else{
                    inI = -inI;
                }
            }
        }
        if((fun&_E_DECIMAL_)>0){
            digi=10;
            digix2=100;
        }
//dbgMsg.save("pConvertType.int2String.inI="+inI);
//dbgMsg.save("pConvertType.int2String.fun="+fun);
//dbgMsg.save("pConvertType.int2String.digi="+digi);
        do{
            tmpI0=(inI%(digix2));
//dbgMsg.save("pConvertType.int2String.tmpI0.0="+tmpI0);
//Log.d("dbg","----");
//Log.d("dbg","inI="+inI);
//Log.d("dbg","tmpI0="+tmpI0);
//Log.d("dbg","rString.0="+rString);
            if(rString.length()==6 && (fun & _E_NO_SIGN_)>0)//MSB
            {
                rString = NibbleHex[(int) ((tmpI0 / digi)|0x8)] + NibbleHex[(int) (tmpI0 % digi)] + rString;
            }
            else {
                rString = NibbleHex[(int) (tmpI0 / digi)] + NibbleHex[(int) (tmpI0 % digi)] + rString;
            }
//Log.d("dbg","rString.1="+rString);
//dbgMsg.save("pConvertType.int2String.rString.0="+rString);
            inI/=(digix2);
//dbgMsg.save("pConvertType.int2String.inI.0="+inI);
//System.out.println("ins="+ins);
        }
        while(inI>0);


        if((fun&_E_ADD_0_FRONT_)>0){
            while((rString.length()%2)!=0){
                rString="0"+rString;
            }
        }


        if((fun&_E_REMOVE_0_KEEP_LAST_)>0){
            while(rString.indexOf("0")==0 && rString.length()>1){
                rString=rString.substring(1,rString.length());
            }
        }
        else if((fun&_E_REMOVE_0_)>0){
            while(rString.indexOf("0")==0){
                rString=rString.substring(1,rString.length());
            }
        }

//dbgMsg.save("pConvertType.int2String.rString.1="+rString);
        if(minus){
            if((fun & _E_NO_SIGN_)>0) {
                while(rString.length()<8){
                    if(rString.length()==7)//MSB
                    {
                        rString =NibbleHex[8] + rString;
                    }
                    else {
                        rString =NibbleHex[0] + rString;
                    }
                }
            }
            else {
                rString = "-" + rString;
            }
        }
        return rString;

    }

    /**
     * check String is binary format or not
     * <br>inString must be valid and each char must be 0x30 or 0x31
     *
     * @param    inString       (String)    :String for check
     * @return                  (boolen)    :true:binary   false:not binary
     */
    public boolean isBin(String inString){
        boolean rboolean=true;

        if(!isValid(inString))
            return false;

        for(int i=0;i<inString.length();i++){
            if(inString.charAt(i)>0x31 || inString.charAt(i)<0x30){
                rboolean=false;
            }
        }

        return rboolean;
    }


    /** ************************************************************************************************************
     * @brief    convert byte to hex,if inputByte not '0'~'9'/'a'~'f'/'A'~'F' return 0x00
     *
//     * @param    [IN]    inputbyte   (byte):input byte                          <br>
     *
     * @return                       (byte)
     *
     * @note     (0x41)->0x0A ,(0x31)=0x01,(0x99)=0x00
     ***************************************************************************************************************/
    public byte getHalfHex(byte inputbyte){
//System.out.println("inputbyte="+inputbyte);
        if(inputbyte>=0x30 && inputbyte<=0x39){//0~9
//System.out.println("0");
            return (byte)(inputbyte-0x30);
        }
        else if(inputbyte>=0x41 && inputbyte<=0x46){//A~F
//System.out.println("1");
            return (byte)bytearray[inputbyte-0x41+10];
        }
        else if(inputbyte>=0x61 && inputbyte<=0x66){//a~f
//System.out.println("2");
            return (byte)bytearray[inputbyte-0x61+10];
        }
        else{
//System.out.println("3");
//            System.out.println("pConvertType.getHalfHex_1.error=invalid input");
            if((dbgEn & _DBG_getHalfHex_)>0){
                dbgMsg.save("pConvertType.getHalfHex_1.error=invalid input");
            }
            return (byte)0x00;
        }
    }

    /** ************************************************************************************************************
     * @brief    convert byte to hex String,,if inputByte not '0'~'9'/'a'~'f'/'A'~'F' return ""
     *
//     * @param    [IN]    inputbyte   (byte):byte                       <br>
     *
     * @return                       (String)
     * @note     (30)="0"
     *           (31)="1"
     ***************************************************************************************************************/
    public String byte2HalfHexString(byte inputbyte){
//System.out.println("inputbyte="+inputbyte);
        if(inputbyte>=0x30 && inputbyte<=0x39){//0~9
//System.out.println("0");
//dbgMsg.save("byte2HalfHexString.0="+NibbleHex[inputbyte-0x30]);
            return NibbleHex[inputbyte-0x30];
        }
        else if(inputbyte>=0x41 && inputbyte<=0x46){//A~F
//System.out.println("1");
//dbgMsg.save("byte2HalfHexString.1="+NibbleHex[inputbyte-0x41+10]);
            return NibbleHex[inputbyte-0x41+10];
        }
        else if(inputbyte>=0x61 && inputbyte<=0x66){//a~f
//System.out.println("2");
//dbgMsg.save("byte2HalfHexString.2="+NibbleHex[inputbyte-0x61+10]);
            return NibbleHex[inputbyte-0x61+10];
        }
        else{
//System.out.println("3");
//            System.out.println("pConvertType.byte2HalfHexString.error=invalid input");
            if((dbgEn & _DBG_getHalfHex_)>0){
                dbgMsg.save("pConvertType.byte2HalfHexString.error=invalid input");
            }
            return "";
        }
    }

    /** ************************************************************************************************************
     * @brief    convert string to byte array  ("hex" format)
     *
//     * @param    [IN]    inString    (String):input String                          <br>
//     * @param    [IN]    fun         (int)   :funcion                               <br>
     *                                        <br>_C_LOW_BYTE_FIRST_    =0x0001
     *
     * @return                       (byte[]):
     *
     * @note     ("99aa",0)=99/AA,("99aa",1)=AA//99
     ***************************************************************************************************************/
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

    public int[] string2Ints(String inString){

        byte[] newbyte=string2Bytes(inString,0x00);
        int[] newInt=new int[newbyte.length];
        for(int i=0;i<newInt.length;i++){
            newInt[i]=newbyte[i]&0xFF;
        }
        return newInt;
    }

    public int[] string2Ints(String inString,int fun){

        byte[] newbyte=string2Bytes(inString,fun);
        int[] newInt=new int[newbyte.length];
        for(int i=0;i<newInt.length;i++){
            newInt[i]=newbyte[i]&0xFF;
        }
        return newInt;
    }

    /** ************************************************************************************************************
     * @brief    byte to int
     *
//     * @param    [IN]    inbyte      (byte):input byte                          <br>
     *
     * @return                       (int):
     *
     * @note     value range:0~255
     *           http://rritw.com/a/JAVAbiancheng/JAVAzonghe/20120828/213053.html
     *           (0x0A)=10
     ***************************************************************************************************************/
    public int byte2Int(byte inbyte){
        int rInt=(inbyte & 0xFF);
        return rInt;
    }

    /** ************************************************************************************************************
     * @brief    convert bytes to int
     *
//     * @param    [IN]    inbyte     (byte[])    :input byte[]                           <br>
     * param    [IN]    fun        (int)       :function                               <br>
     *                                           <br>_C_LOW_BYTE_FIRST_    =0x0001
     *
     * @return                      (int)       :int
     *
     * @note     http://rritw.com/a/JAVAbiancheng/JAVAzonghe/20120828/213053.html
     *           on byte to int can just use (int)byte or  Math.abs(byte)
     *           value range:0~255
     *           (010203,0)=66051,(010203,1)=197121
     ***************************************************************************************************************/
    public int bytes2Int(byte[] inbyte){

        return bytes2Int(inbyte,0);
    }
    public int bytes2Int(byte[] inbyte,int fun){
        int rInt=0;

        if(!isValid(inbyte) || inbyte.length>4){
            showWarning(TAG+".bytes2Int\r\n"+"1:input value out of range,input Len="+inbyte.length);
        }

        if((fun & _C_LOW_BYTE_FIRST_)==_C_LOW_BYTE_FIRST_){//030201=197121
            for(int i=(inbyte.length-1);i>0;i--){
                rInt+=(inbyte[i]&0xFF)*(0x100<<((i-1)*8));
//System.out.println("rInt_1="+rInt+"   i="+i);
            }
            rInt+=(inbyte[0]&0xFF);
//System.out.println("rInt_1_last="+rInt);
        }
        else{       //010203=66051
            rInt=(inbyte[inbyte.length-1]&0xFF);
//dbgMsg.save("rInt_0_first="+rInt);
            for(int i=1;i<(inbyte.length);i++){
                rInt+=(inbyte[inbyte.length-i-1]&0xFF)*(0x100<<((i-1)*8));
//dbgMsg.save("rInt_0="+rInt+"   i="+i);
            }

        }
//dbgMsg.save("rInt="+rInt);
        return rInt;
    }
    /** ************************************************************************************************************
     * @brief    byte to int
     *
//     * @param    [IN]    inbyte      (byte):input byte                          <br>
     *
     * @return                       (int):
     *
     * @note     value range:0~255
     *           http://rritw.com/a/JAVAbiancheng/JAVAzonghe/20120828/213053.html
     *           (0x0A)=10
     ***************************************************************************************************************/
    public int[] bytes2Ints(byte[] inbyte){
        int[] rInt=new int[0];
        if(inbyte==null){

        }
        else{
            rInt=new int[inbyte.length];
            for(int i=0;i<inbyte.length;i++){
                rInt[i]=(inbyte[i] & 0xFF);
            }
        }

        return rInt;
    }

    /** ************************************************************************************************************
     * @brief    int to byte,only return one byte
     *
//     * @param    [IN]    ins         (int ):input int                          <br>
     *
     * @return                       (byte):value range:0~255
     * @note      value range:0~255,if ins>255 will constrain to 255
     *            (10)=0A
     *            (255)=FF
     *            (256)=01
     ***************************************************************************************************************/
    public byte int2Byte(int ins){
        byte rbyte=0x00;

        if(ins>255)
            ins%=255;

        if((ins%16)>0)
            rbyte|=(byte)bytearray[ins%16];

        rbyte<<=4;

        if((ins/16)>0)
            rbyte|=(byte)bytearray[ins/16];

        return rbyte;
    }

    /** ************************************************************************************************************
     * @brief    int to byte[],return 4 bytes
     *
//     * @param    [IN]    ins      (int):input int                          <br>
     *
//     * @param    [IN]    fun      (int):function
     *                                  <br>_C_LOWBYTEFIRST_      =0x0001:low nibble first
     *
     * @return                    (byte[]):int to byte[] value,value range:0~4294967295
     *
     * @note     value range:0~4294967295(4 bytes)
     *           Integer.MAX_VALUE=2147483647=0x7FFFFFFF
     *           Integer.MIN_VALUE=-2147483648=0x80000000
     *           (255,1)=FF/00/00/00
     *           (256,0)=00/00/01/00
     ***************************************************************************************************************/
    public byte[] int2Bytes(int ins,int fun){
        byte tmpbyte[]=new byte[4];
        tmpbyte[0]=0;
        tmpbyte[1]=0;
        tmpbyte[2]=0;
        tmpbyte[3]=0;
        byte rbyte[]=new byte[4];
        rbyte[0]=0;
        rbyte[1]=0;
        rbyte[2]=0;
        rbyte[3]=0;
        int tmpins=0,index=0;


        do{
            tmpins=(ins%256);
//System.out.println("tmpins="+(tmpins));
            //----------------------------------
            if((tmpins/16)>0){
//System.out.println("tmpins/16="+(tmpins/16));
                tmpbyte[index]|=(byte)bytearray[tmpins/16];
            }

            tmpbyte[index]<<=4;

            if((tmpins%16)>0){
//System.out.println("tmpins%16="+(tmpins%16));
                tmpbyte[index]|=(byte)bytearray[tmpins%16];

            }
            //----------------------------------



            index++;
            ins/=256;
//System.out.println("ins="+ins);
        }
        while(ins>0);


        if((fun & _C_LOW_BYTE_FIRST_)==_C_LOW_BYTE_FIRST_){
            rbyte[0]=tmpbyte[0];
            rbyte[1]=tmpbyte[1];
            rbyte[2]=tmpbyte[2];
            rbyte[3]=tmpbyte[3];
        }
        else{
            rbyte[0]=tmpbyte[3];
            rbyte[1]=tmpbyte[2];
            rbyte[2]=tmpbyte[1];
            rbyte[3]=tmpbyte[0];
        }

        return rbyte;
    }
    public byte[] int2Bytes(int ins,int fun,int digi){
        if(digi==0){
//            dbgMsg.save("pConvertType.int2Bytes:digi format error");
            System.out.println("pConvertType.int2Bytes:digi format error");
        }

        byte tmpbyte[]=new byte[digi];
        for(int i=0;i<digi;i++){
            tmpbyte[i]=0;
        }
        byte rbyte[]=new byte[digi];
        for(int i=0;i<digi;i++){
            rbyte[i]=0;
        }

        int tmpins=0,index=0;


        do{
            tmpins=(ins%256);


            //----------------------------------
            if((tmpins/16)>0){
//System.out.println("tmpins/16="+(tmpins/16));
                tmpbyte[index]|=(byte)bytearray[tmpins/16];
            }

            tmpbyte[index]<<=4;

            if((tmpins%16)>0){
//System.out.println("tmpins%16="+(tmpins%16));
                tmpbyte[index]|=(byte)bytearray[tmpins%16];

            }
            //----------------------------------



            index++;
            ins/=256;
//System.out.println("ins="+ins);
        }
        while(ins>0);


        if((fun & _C_LOW_BYTE_FIRST_)==_C_LOW_BYTE_FIRST_){
            for(int i=0;i<digi;i++)
            {
                rbyte[i]=tmpbyte[i];
            }
        }
        else{
            for(int i=0;i<digi;i++)
            {
                rbyte[i]=tmpbyte[digi-i-1];
            }
        }

        return rbyte;

    }

    /** ************************************************************************************************************
     * @brief    convert bytes to string ("hex" format)
     *
//     * @param    [IN]    inputbyte    (byte[])    :input byte[]                     <br>
     *
     * @return                        (String)    :String
     *
     * @note     (0xAA)->"AA"
     ***************************************************************************************************************/

    public String bytes2String(byte[] inputbyte){

        String back="";
        if(inputbyte!=null) {
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

    /** ************************************************************************************************************
     * @brief    convert bytes to string ("hex" format)
     *
     * param    [IN]    inputbyte    (byte[])    :input byte[]                     <br>
     * param    [IN]    start           (int)          :start index                     <br>
     * param    [IN]    len             (int)          :length                     <br>
     *
     * @return                        (String)    :String
     *
     * @note     (AA/BB/CC,0,1)->"AA"
     *                  (AA/BB/CC,0,4)->"AABBCC"
     *                  (AA/BB/CC,2,1)->"CC"
     *                  (AA/BB/CC,4,1)->""
     ***************************************************************************************************************/
    public String bytes2String(byte[] inputbyte,int start,int len){
        String back="";

        if(inputbyte.length>0){
            if(start<inputbyte.length)
            {
                if((start+len)>inputbyte.length){
                    len=inputbyte.length-start;
                }

                final StringBuilder stringBuilder = new StringBuilder(len);
                for (int i=0;i<len;i++) {
                    stringBuilder.append(String.format("%02X", inputbyte[start+i]));
                }
                back = stringBuilder.toString();
            }
        }
        return back;
    }
    /** ************************************************************************************************************
     * @brief    convert bytes to string ("hex" format)
     *
     * param    [IN]    inputbyte    (byte[])    :input byte[]                     <br>
     * param    [IN]    fun        (int):_E_DECIMAL_   =0x0001              <br>
     *                                               _E_REMOVE_0_  =0x0002              <br>
     *
     * @return                        (String)    :String
     *
     * @note     (0xAA)->"AA"
     ***************************************************************************************************************/
    public String bytes2String(byte[] inputbyte,int fun){

        String rString="";
        convertStringBuilder0.setLength(0);

        for(int i=0;i<inputbyte.length;i++){
            convertStringBuilder0.append(this.int2String(inputbyte[i]&0xFF,fun));
        }
        return convertStringBuilder0.toString();
    }
    /** ************************************************************************************************************
     * @brief    byte[] to String and add String at the first
     *
     * param    [IN]    inputbyte    (byte[])    :input byte[]                     <br>
     * param    [IN]    s            (String)    :input String                     <br>
     *
     * @return                        (String)    :byte[] to String and add String at the first
     *
     * @note     (FF/AA/00,"ui")="uiFFuiAAui00"
     ***************************************************************************************************************/
    //byte+s+byte+s...
//  public String bytes2StringAdd(byte[] inputbyte,String s){
//      String back="";
//System.out.println("inputbyte[0]:"+inputbyte[0]);
//      if(inputbyte.length>0){
//          for(int i=0;i<inputbyte.length;i++){
//                back+=byte2StringAdd(inputbyte[i],s);
//          }
//      }
//      return back;
//  }
    //byte+s+byte+s...
    public String bytes2StringAdd(byte[] inputbyte,String s){
        String back="";
        if(inputbyte.length>0){
            final StringBuilder stringBuilder = new StringBuilder(inputbyte.length);
            for(byte byteChar : inputbyte) {
                stringBuilder.append(String.format("%02X", byteChar));
                stringBuilder.append(s);
            }
            back=stringBuilder.toString();
        }
        return back;
    }
    //------------------------------------------
    //s+byte+s+byte...
//    public String bytes2StringAdd(String s,byte[] inputbyte){
//        String back="";
//System.out.println("inputbyte[0]:"+inputbyte[0]);
//        if(inputbyte.length>0){
//            for(int i=0;i<inputbyte.length;i++){
//              if((i%16)==0){
//                  back+="\r\n";
//              }
//                back+=byte2StringAdd(s,inputbyte[i]);
//            }
//        }
//        return back;
//    }
    //------------------------------------------
    //s+byte+s+byte...
    public String bytes2StringAdd(String s,byte[] inputbyte){
        String back="";
        if(inputbyte.length>0){
            final StringBuilder stringBuilder = new StringBuilder(inputbyte.length);
            for(byte byteChar : inputbyte) {
                stringBuilder.append(s);
                stringBuilder.append(String.format("%02X", byteChar));
            }
            back=stringBuilder.toString();
        }
        return back;
    }
    /** ************************************************************************************************************
     * @brief    convert byte to String
     *
     * param    [IN]    inputbyte    (byte)      :input byte                       <br>
     *
     * @return                        (String)    :String
     *
     * @note     (FA)="FA"
     ***************************************************************************************************************/
    public String byte2String(byte inputbyte){
        return (NibbleHex[(inputbyte&0xF0)>>4]+NibbleHex[inputbyte&0x0F]);
    }
    /** ************************************************************************************************************
     * @brief    convert byte to String
     *
     * param    [IN]    inputbyte    (byte)      :input byte                       <br>
     *
     * @return                        (String)    :String
     *
     * @note     (FA)="FA"
     ***************************************************************************************************************/
    public String byte2String(byte inputbyte,int fun){
        String rString="";
        rString+=this.int2String(inputbyte&0xFF,fun);
        return rString;
    }
    /** ************************************************************************************************************
     * @brief    byte to String and add String at the first
     *
     * param    [IN]    inputbyte    (byte)      :input byte                     <br>
     * param    [IN]    s            (String)    :input String                     <br>
     *
     * @return                        (String)    :byte to String and add String at the first
     *
     * @note     (FF,"ui")="uiFF"
     ***************************************************************************************************************/
    public String byte2StringAdd(byte inputbyte,String s){
        return (byte2String(inputbyte)+s);
    }
    //------------------------------------------
    public String byte2StringAdd(String s,byte inputbyte){
        return (s+byte2String(inputbyte));
    }
    /** ************************************************************************************************************
     * @brief    convert string(bin) to String(hex)
     *
     * param    [IN]    binString      (String)    :input String                         <br>
     *
     * @return                          (String)
     *
     * @note     binString must be valid and isBin,if not multiple of 8 will padding 0
     *           ("1110000",0)="70"
     ***************************************************************************************************************/
    public String bin2Hex(String binString){
        return bin2Hex(binString,0);
    }

    int speedCheck=1;
    /** ************************************************************************************************************
     * @brief    convert string(bin) to String(hex)
     *
     * param    [IN]    binString      (String)    :input String                         <br>
     * param    [IN]    fun            (int)       :function                             <br>
     *                                               <br>_D_LOW_BIT_FIRST_      =0x0001
     *                                               <br>_D_NIBBLE_SWITCH_      =0x0002
     *                                               <br>_D_PADDING_FRONT_      =0x0004
     *
     * @return                          (String)
     *
     * @note     binString must be valid and isBin,if not multiple of 8 will padding 0
     *           ("1110000",0)="70"
     ***************************************************************************************************************/
    public String bin2Hex(String binString,int fun){

        if(!isValid(binString) || !isBin(binString)){
            showWarning(TAG+".bin2Hex\r\n"+"1:input value out of range,input="+binString);
            return "";
        }
        StringBuilder bin2HexSB_0=new StringBuilder(100);
        String rString="";

        int shortlen=(binString.length()%8);
        String tmpString="",tmpString1="";

        if(shortlen>0){
            shortlen=8-shortlen;
        }
//dbgMsg.save("shortlen="+shortlen);

        for(int i=0;i<shortlen;i++){
            if((fun&_D_PADDING_FRONT_)>0){
                binString="0"+binString;
            }
            else{
                binString+="0";
            }
        }
        bin2HexSB_0.setLength(0);

        int strat=0,end=0;
        for(int i=0;i<binString.length();i+=4){
            tmpString="";
            tmpString1="";
            if((fun & _D_NIBBLE_SWITCH_)==_D_NIBBLE_SWITCH_){
                end=binString.length()-i;
                strat=end-4;
            }
            else{
                strat=i;
                end=i+4;
            }

            tmpString=binString.substring(strat,end);

            if((fun & _D_LOW_BIT_FIRST_)==_D_LOW_BIT_FIRST_){

                for(int k=0;k<4;k++){
                    tmpString1+=tmpString.substring(4-k-1,4-k);
                }
                tmpString=tmpString1;
            }

            if(speedCheck>0)
                tmpString1="";
//dbgMsg.save("tmpString="+tmpString);

            for(int j=0;j<NibbleBin.length;j++){
                if(tmpString.equals(NibbleBin[j])){
                    if((fun & _D_LOW_BIT_FIRST_)==_D_LOW_BIT_FIRST_){
                    }
                    tmpString1=NibbleHex[j];
                    tmpString="";
                }
            }
            if(!tmpString.equals("")){
//dbgMsg.save("fail=");
                showWarning(TAG+".bin2Hex\r\n"+"2:input value out of range,input="+tmpString);
                tmpString1="0";
            }
//dbgMsg.save("tmpString1="+tmpString1);

            if((fun & _D_LOW_BIT_FIRST_)==_D_LOW_BIT_FIRST_){
                if(speedCheck==0)
                    rString=tmpString1+rString;
                else
                    bin2HexSB_0.insert(0,tmpString1);

            }
            else{
                if(speedCheck==0)
                    rString=rString+tmpString1;
                else
                    bin2HexSB_0.append(tmpString1);

            }

        }

//dbgMsg.save("rString="+rString);
        if(speedCheck==0)
            return rString;
        else
            return bin2HexSB_0.toString();
    }

    public String bin2Hex1(String binString,int fun){

        if(!isValid(binString) || !isBin(binString)){
            showWarning(TAG+".bin2Hex\r\n"+"1:input value out of range,input="+binString);
            return "";
        }

        String rString="";

        int shortlen=(binString.length()%8);
        String tmpString="",tmpString1="";

        if(shortlen>0){
            shortlen=8-shortlen;
        }
//dbgMsg.save("shortlen="+shortlen);

        for(int i=0;i<shortlen;i++){
            if((fun&_D_PADDING_FRONT_)>0){
                binString="0"+binString;
            }
            else{
                binString+="0";
            }
        }
        int strat=0,end=0;
        for(int i=0;i<binString.length();i+=4){
            tmpString="";
            tmpString1="";
            if((fun & _D_NIBBLE_SWITCH_)==_D_NIBBLE_SWITCH_){
                end=binString.length()-i;
                strat=end-4;
            }
            else{
                strat=i;
                end=i+4;
            }

            tmpString=binString.substring(strat,end);

            if((fun & _D_LOW_BIT_FIRST_)==_D_LOW_BIT_FIRST_){

                for(int k=0;k<4;k++){
                    tmpString1+=tmpString.substring(4-k-1,4-k);
                }
                tmpString=tmpString1;
            }

            tmpString1="";
//dbgMsg.save("tmpString="+tmpString);

            for(int j=0;j<NibbleBin.length;j++){
                if(tmpString.equals(NibbleBin[j])){
                    if((fun & _D_LOW_BIT_FIRST_)==_D_LOW_BIT_FIRST_){
                    }
                    tmpString1=NibbleHex[j];
                    tmpString="";
                }
            }
            if(!tmpString.equals("")){
//dbgMsg.save("fail=");
                showWarning(TAG+".bin2Hex\r\n"+"2:input value out of range,input="+tmpString);
                tmpString1="0";
            }
//dbgMsg.save("tmpString1="+tmpString1);

            if((fun & _D_LOW_BIT_FIRST_)==_D_LOW_BIT_FIRST_){
                rString=tmpString1+rString;
            }
            else{
                rString=rString+tmpString1;
            }

        }

//dbgMsg.save("rString="+rString);
        return rString;
    }
    /** ************************************************************************************************************
     * @brief    convert String to Long
     *
     * param    [IN]    inS            (String)    :input String                         <br>
     * param    [IN]    fun            (int)       :radix (2/8/10/16)                    <br>
     *
     * @return                          (String)
     *
     * @note     ("0", 10)=0L,("-FF", 16)=-255L,("1100110", 2)=102L
     ***************************************************************************************************************/
    public long string2Long(String inS,int fun){
        int digi=10;
//dbgMsg=new DBG(TAG+dbgExt,0x03);
//dbgMsg.save("inS="+inS);
//dbgMsg.save("fun="+int2String(fun));
        if((fun & _E_DECIMAL_)>0){

        }
        else if(inS.toLowerCase().startsWith("0x"))
        {
//dbgMsg.save("tmpS="+tmpS);
            inS=inS.substring(2,inS.length());
//dbgMsg.save("tmpS="+tmpS);
            digi=16;
        }
        else if((fun & _E_HEXADECIMAL_)>0){
            digi=16;
        }

//dbgMsg.save("inS="+inS);
//dbgMsg.save("digi="+int2String(digi));
        return (Long.parseLong(inS,digi));
    }

    public long string2Long(String inS){
        return string2Long(inS,10);
    }
    /** ************************************************************************************************************
     * @brief    convert Long to String
     *
     * param    [IN]    inL            (long)      :input Long                         <br>
     *
     * @return                          (String)
     *
     * @note     (100L)="100"
     ***************************************************************************************************************/
    public String long2String(long inL){
        return (Long.toString(inL));
    }
    /** ************************************************************************************************************
     * @brief    convert String to Long
     *
     * param    [IN]    inS            (String)    :input String                         <br>
     * param    [IN]    fun            (int)       :radix (2/8/10/16)                    <br>
     *
     * @return                          (String)
     *
     * @note     ("0", 10)=0L,("-FF", 16)=-255L,("1100110", 2)=102L
     ***************************************************************************************************************/
    public Double string2Double(String inS,int fun){
        return (Double.parseDouble(inS));
    }

    public Double string2Double(String inS){
        return (Double.parseDouble(inS));
    }
    /** ************************************************************************************************************
     * @brief    convert Long to String
     *
     * param    [IN]    inL            (long)      :input Long                         <br>
     *
     * @return                          (String)
     *
     * @note     (100L)="100"
     ***************************************************************************************************************/
    public String double2String(Double inD){
        return (Double.toString(inD));
    }

    /** ************************************************************************************************************
     * @brief    String to Unicode
     *
     * param    [IN]    inS         (String):input String                          <br>
     *
     * @return                       (String):Unicode
     *
     * @note     (\u0041)="A"
     * http://unicode-table.com/cn/#enclosed-alphanumerics
     ***************************************************************************************************************/
    public String string2Unicode(String inS)
    {
        StringBuilder rSB=new StringBuilder(100);
        int sIndex=0;
        String tmpS_00="",tmpS_01="";
//dbgMsg=new DBG(TAG+".dbg",0x03);
        try{
            while(inS.indexOf("\\u")!=-1){
                sIndex=inS.indexOf("\\u");
                tmpS_00=inS.substring(sIndex,sIndex+6);
                tmpS_01=new String(this.string2Bytes(tmpS_00.replace("\\u",""),0),"Unicode");
//dbgMsg.save(TAG + ".string2Unicode.inS.0=" +inS);
//dbgMsg.save(TAG + ".string2Unicode.tmpS_00.0=" +tmpS_00);
//dbgMsg.save(TAG + ".string2Unicode.tmpS_01.0=" +tmpS_01);
                inS=inS.replace(tmpS_00,tmpS_01);
//dbgMsg.save(TAG + ".string2Unicode.inS.1=" +inS);
            }
//        inS=inS.replace("\\u","");
//        inS=inS.replace("0x","");
//        try{
//            rSB.append(new String(this.string2Bytes(inS,0),"Unicode"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

//        return rSB.toString();
        return inS;
    }
    /** ************************************************************************************************************
     * @brief    String[] to Unicode
     *
     * param    [IN]    inS         (String[]):input String[]                          <br>
     *
     * @return                       (String)  :Unicode
     *
     * @note     (\u0041\u0042)="AB"
     ***************************************************************************************************************/
    public String string2Unicode(String inS[])
    {
        StringBuilder rSB=new StringBuilder(100);
        for(int i=0;i<inS.length;i++){

            rSB.append(string2Unicode(inS[i]));

        }
        return rSB.toString();
    }

    /**
     * show warrning
     *
     * @param    warnString     (String)    :warring message
     */
    private void showWarning(String warnString){

        dbgMsg.save("WARNING:");

        if(isValid(warnString)){
            dbgMsg.save(warnString);
        }
    }
    /** ************************************************************************************************************
     * @brief    convert char to String,can be hex or dec
     *
     * param    [IN]    inChar      (char):input char                          <br>
     *
     * @return                       (String)
     *
     * @note     (30)="30"
     ***************************************************************************************************************/
    public String char2String(char inChar){
        String rString="";
        rString+=this.int2String(inChar&0xFF,0);
        return rString;
    }
    /** ************************************************************************************************************
     * @brief    convert char to String,can be hex or dec
     *
     * param    [IN]    inChar      (char):input char                          <br>
     * param    [IN]    fun         (char):format,hex or dec
     *                                      <br>_E_DECIMAL_   =0x0001
     *
     * @return                       (String)
     *
     * @note     (30)="30"
     ***************************************************************************************************************/
    public String char2String(char inChar,int fun){
        String rString="";
        rString+=this.int2String(inChar&0xFF,fun);
        return rString;
    }
    /** ************************************************************************************************************
     * @brief    convert char array to String,default hex
     *
     * param    [IN]    inChar      (char[]):input char                          <br>
     *
     * @return                       (String)
     *
     * @note     (30/31/32)="303132"
     ***************************************************************************************************************/
    public String chars2String(char[] inChar){
        return chars2String(inChar,0);
    }
    /** ************************************************************************************************************
     * @brief    convert char array to String,can be hex or dec
     *
     * param    [IN]    inChar      (char[]):input char                          <br>
     * param    [IN]    fun         (char)  :format,hex(default) or dec
     *                                       <br>_E_DECIMAL_   =0x0001
     *
     * @return                       (String)
     *
     * @note     (30/31/32)="303132"
     ***************************************************************************************************************/
    public String chars2String(char[] inChar,int fun){
        String rString="";
        convertStringBuilder0.setLength(0);

        for(int i=0;i<inChar.length;i++){
            convertStringBuilder0.append(this.int2String(inChar[i]&0xFF,fun));
        }
        return convertStringBuilder0.toString();
    }

    /** ************************************************************************************************************
     * @brief    convert char array to String,
     *
     * param    [IN]    inChar      (char[]):input char                          <br>
     *
     * @return                       (String)
     *
     * @note     (30/31/32)="012"
     ***************************************************************************************************************/
    public String chars2Hex(char[] inChar){
        String rString="";

        for(int i=0;i<inChar.length;i++){
//System.out.println("getHalfHexString="+this.getHalfHexString((byte)inChar[i]));
            rString+=this.byte2HalfHexString((byte)inChar[i]);
        }
//System.out.println("rString="+rString);
        return rString;
    }
    /** ************************************************************************************************************
     * @brief    convert char array to String,
     *
     * param    [IN]    inChar      (char[]):input char                          <br>
     *
     * @return                       (String)
     *
     * @note     (30/31/32)="012"
     ***************************************************************************************************************/
    public String bytes2Hex(byte[] inByte){
        String rString="";
//dbgMsg.save("bytes2Hex.inByte="+this.bytes2String(inByte));
        for(int i=0;i<inByte.length;i++){
//System.out.println("getHalfHexString="+this.getHalfHexString((byte)inByte[i]));
            rString+=this.byte2HalfHexString(inByte[i]);
        }
//System.out.println("rString="+rString);
//dbgMsg.save("bytes2Hex.rString="+rString);
        return rString;
    }

    /** ************************************************************************************************************
     * @brief    convert boolean to String,"true"/"false"
     *
     * param    [IN]    b      (boolean):input boolean                          <br>
     *
     * @return                       (String)
     *
     * @note     b=true,return "true"
     ***************************************************************************************************************/
    public String boolean2String(boolean b){
        if(b){
            return "true";
        }
        else{
            return "false";
        }
    }
    /** ************************************************************************************************************
     * @brief    push data to list
     *
     * param    [IN]    inInt    (int)    :push data                <br>
     *
     * @return                    (int)    :push data index
     ***************************************************************************************************************/
    int pushI(List<Integer> listI,int inI){
        listI.add(inI);
        return (listI.size()-1);
    }
    int pushS(List<String> listS,String inS){
        listS.add(inS);
        return (listS.size()-1);
    }
    /** ************************************************************************************************************
     * @brief    pop data from list
     *
     * @return                    (int)    :pop data
     ***************************************************************************************************************/
    static final int _B_REMOVE_LIST_     =0x01;

    int popI(List<Integer> listI){
        return popI(listI,listI.size()-1,_B_REMOVE_LIST_);
    }
    int popIKeep(List<Integer> listI){
        return popI(listI,listI.size()-1,0);
    }
    //==============================================================================================
    String popS(List<String> listS){
        return popS(listS,listS.size()-1,_B_REMOVE_LIST_);
    }
    String popSKeep(List<String> listS){
        return popS(listS,listS.size()-1,0);
    }
    /** ************************************************************************************************************
     * @brief    pop data from list
     *
     * param    [IN]    listInd  (int)    :list index                <br>
     *
     * @return                    (int)    :pop data
     ***************************************************************************************************************/
    int popI(List<Integer> listI,int listInd,int fun){
        int tmplistInd=listI.size()-1;
        int rInt=-1;

        if(tmplistInd==-1){
            //dbgMsg.show(TAG + tagS + ".popInt.listI error:size()=0 !!!");
            while(true);
        }
        if(listInd>tmplistInd || listInd<0){
            //dbgMsg.show(TAG + tagS + ".popInt.listI error:index invalid "+listInd+"/"+tmplistInd+" !!!");
            while(true);
        }
        tmplistInd=listInd;

        rInt=listI.get(tmplistInd);

        if((fun & _B_REMOVE_LIST_)>0){
            listI.remove(tmplistInd);
        }

        return rInt;
    }
    //==============================================================================================
    String popS(List<String> listS,int listInd,int fun){
        int tmplistInd=listS.size()-1;
        String rS="";

        if(tmplistInd==-1){
            //dbgMsg.show(TAG + tagS + ".popInt.listS error:size()=0 !!!");
            while(true);
        }
        if(listInd>tmplistInd || listInd<0){
            //dbgMsg.show(TAG + tagS + ".popInt.listS error:index invalid "+listInd+"/"+tmplistInd+" !!!");
            while(true);
        }
        tmplistInd=listInd;

        rS=listS.get(tmplistInd);

        if((fun & _B_REMOVE_LIST_)>0){
            listS.remove(tmplistInd);
        }

        return rS;
    }

    /** ************************************************************************************************************
     * @brief    convert String to Map,
     *
     * @param    [IN]    inS      (String)  :input String                          <br>
     *
     * @return                       (Map)
     *
     * @note        inS={text.size=Blue,text.color=Green,text=Red,img.alpha=White}
     *                  return= map.put{text.size,Blue}
     *                              map.put{text.color,Green}
     *                              map.put{text,Red}
     *                              map.put{img.alpha,White}
     *
     *                 Map2String use Map.toString()
     ***************************************************************************************************************/
    //string2Map
    public static final int _F_KEY_LOWER_CASE_         =0x0001;//key force to lower case
    public static final int _F_KEY_UPPER_CASE_         =0x0002;//key force to upper case
    public static final int _F_ONE_MAP_                =0x0004;//parse only one map item


    public Map string2Map(String inS) {
        return string2Map(inS,0);
    }


    private List<String> listS0=new ArrayList<String>();//tmp use
    private List<String> listS1=new ArrayList<String>();
    private List<Integer> listInt1=new ArrayList<Integer>();
    private List<Integer> listInt2=new ArrayList<Integer>();

    private String[] parseStart={"{","["};
    private String[] parseEnd={"}","]"};

    public Map string2Map(String inS,int fun)
    {
//Log.d("dbg", "inS.0=" + inS);
        Map<String, String> map = new HashMap<String, String>();
        if(!isValid(inS)){
            return map;
        }
        String[] tmpS=new String[3];
        int[] tmpI=new int[4];
        int index=0,prevIndex=0;
        listS0.clear();
        listS1.clear();
        //1.remove map head and tail
        if(inS.startsWith("{")) {
            inS=inS.substring(1,inS.length());//use replaceFirst() cause crash(inS="{text=99, text.size={}")

            if(inS.endsWith("}")) {
                inS=inS.substring(0,inS.length()-1);
            }
        }
//Log.d("dbg", "inS.1=" + inS);

//Log.d("dbg", "inS.2=" + inS);
        //2.seperate map
//dbgMsg.save("string2Map1.inS.0=" + inS);
//dbgMsg.save("string2Map1.inS.length()=" + inS.length());
        do {
            index++;
//dbgMsg.save("----------");
//dbgMsg.save("string2Map1.index.0=" + int2String(index,0));
//dbgMsg.save("string2Map1.prevIndex.0=" + int2String(prevIndex,0));
            tmpS[0]=inS.substring(index,index+1);
            tmpS[1]=inS.substring(prevIndex,index+1);

//            dbgMsg.save("string2Map1.tmpS[0].0=" + tmpS[0]);
//            dbgMsg.save("string2Map1.tmpS[1].0=" + tmpS[1]);

            for(int i=0;i<parseStart.length;i++){
                if(tmpS[0].equals(parseStart[i]))
                {
                    pushS(listS0,parseStart[i]);
                }
                else if(tmpS[0].equals(parseEnd[i]))
                {
                    if(popSKeep(listS0).equals(parseStart[i]))
                    {
                        popS(listS0);
                    }
                }
            }


//            dbgMsg.save("string2Map1.listS0.size().1=" +int2String(listS0.size(),0));
//            dbgMsg.save("string2Map1.tmpS[0].1=" + tmpS[0]);
//            dbgMsg.save("string2Map1.tmpS[1].1=" + tmpS[1]);

            if(listS0.size()==0 && isValid(tmpS[1]))
            {
                tmpS[2]="";
//dbgMsg.save("string2Map1.tmpS[1].2=" + tmpS[1]);
//                dbgMsg.save("string2Map1.index.f=" + int2String(index,0));
                //if last item
                if((index+1)==inS.length())
                {
                    tmpS[2]=tmpS[1];
                }
                //if item
                if(isValid(tmpS[1].indexOf(", "))) {
                    if (tmpS[1].endsWith(", ")) {
                        tmpS[2] = tmpS[1].substring(0, tmpS[1].length() - 2);
                    }
                }

                if(isValid(tmpS[2])) {
                    //   dbgMsg.save("----------");
//dbgMsg.save("tmpS[2]=" + tmpS[2]);
                    listS1.add(tmpS[2]);
                    prevIndex=index+1;
                }
            }
//dbgMsg.save("string2Map1.index.f=" + int2String(index,0));
        }while((index+1)<inS.length());

//dbgMsg.save("string2Map1.f");

        //only one item
        if(listS1.size()==0){
            listS1.add(inS);
        }


//        //2.remove extra space from map
//        inS=inS.replace(", ", ",");
//
//        //3.remove last ","
//        if(inS.endsWith(",")){
//            inS=inS.substring(0,inS.length()-1);
//        }
////Log.d("dbg","inS.3="+inS);
        String[] inSA=new String[1];

        if((fun & _F_ONE_MAP_)>0){
            if(inS.endsWith(",")){
                inS=inS.substring(0,inS.length()-1);
            }
            inSA[0]=inS;
        }
        else{
//dbgMsg.save("string2Map1.listS1.size().1=" +int2String(listS1.size(),0));
            inSA = listS1.toArray(new String[listS1.size()]);
            //inSA=inS.split("[,]");

        }

        for(int i=0;i<inSA.length;i++) {
//Log.d("dbg", "inSA["+i+"]=" + inSA[i]);
//dbgMsg.save("----------");
//dbgMsg.save("inSA["+i+"]=" + inSA[i]);

            String[] inSA1=inSA[i].split("=",2);//execute (2-1) times

//            for(int j=0;j<inSA1.length;j++) {
//                dbgMsg.save("inSA1[" + j + "]=" + inSA1[j]);
//            }
            if((fun & _F_KEY_LOWER_CASE_)>0){
                inSA1[0]=inSA1[0].toLowerCase();
            }
            else if((fun & _F_KEY_UPPER_CASE_)>0){
                inSA1[0]=inSA1[0].toUpperCase();
            }
            map.put(inSA1[0], inSA1.length == 1 ? "" : inSA1[1]);
        }

//        Log.d("dbg","map.toString()="+map.toString());

        return map;
    }

    /** ************************************************************************************************************
     * @brief    convert String to String[],
     *
     * param    [IN]    inS      (String)  :input String                          <br>
     *
     * @return                       (String[])
     *
     * @note        inS=[1,2,3]
     *                  return= rs[0]=1,
     *                              rs[1]=2,
     *                              rs[2]=3
     *
     *                 String Arrty to String  use Arrays.toString()
     ***************************************************************************************************************/
    public String[] string2StringArray(String inS)
    {
        String[] rS=new String[0];
//Log.d("dbg", "inS.0=" + inS);
        if(!isValid(inS)){
            return rS;
        }

/*
        if(inS.startsWith("[")) {
            inS=inS.substring(1,inS.length());//use replaceFirst() cause crash(inS="{text=99, text.size={}")
        }
//Log.d("dbg", "inS.1=" + inS);
        if(inS.endsWith("]")) {
            inS=inS.substring(0,inS.length()-1);
        }
//Log.d("dbg", "inS.2=" + inS);
        inS=inS.replace(", ", ",");
*/
        inS=this.string2String(inS);
//Log.d("dbg","inS.3="+inS);
        rS=inS.split(",");

//        Log.d("dbg","map.toString()="+map.toString());
        return rS;
    }

    final int _SB_LENGTH_=100;

    //[a][b][c]->a,b,c
    public String sA2s(String[] inSArr,String speS)
    {
        String rS="";
        StringBuilder tmpSB=new StringBuilder(_SB_LENGTH_);
//Log.d("dbg", "inS.0=" + inS);
        if(!isValid(inSArr)){
            return rS;
        }
        for(int i=0;i<inSArr.length;i++){
            tmpSB.append(inSArr[i]);
            tmpSB.append(speS);
        }

        return tmpSB.toString();
    }
    //array string to string
    //[1, 2, ]->1,2
    public String aS2s(String inS)
    {
        String[] tmpS=new String[1];
//set_dbgEn(0x0A);
//dbgMsg.show("aS2s.inS.0="+inS);
        tmpS[0]="[";
        if(inS.indexOf(tmpS[0])==0){
            inS=inS.substring(1,inS.length());
        }
//dbgMsg.show("aS2s.inS.1="+inS);
        tmpS[0]="]";
        if((inS.lastIndexOf(tmpS[0])+tmpS[0].length())==inS.length()){
//dbgMsg.show("aS2s.i="+inS.indexOf(tmpS[0]));
//dbgMsg.show("aS2s.i="+inS.length());
            inS=inS.substring(0,inS.length()-tmpS[0].length());
        }
//dbgMsg.show("aS2s.inS.2="+inS);
        inS=inS.replace(", ",",");

        if(isValid(inS)){
            tmpS[0]=",";
//dbgMsg.show("aS2s.i="+inS.indexOf(tmpS[0]));
//dbgMsg.show("aS2s.i="+inS.length());
            if((inS.lastIndexOf(tmpS[0])+tmpS[0].length())==inS.length()){
                inS=inS.substring(0,inS.length()-tmpS[0].length());
            }
//dbgMsg.show("aS2s.inS.3="+inS);
        }
        return inS;
    }
    //remove extra ", " and "[]"
    //[a, b, c]->a,b,c
    public String string2String(String inS)
    {
        String rS="";
//Log.d("dbg", "inS.0=" + inS);
        if(!isValid(inS)){
            return rS;
        }

        if(inS.startsWith("[")) {
            inS=inS.substring(1,inS.length());//use replaceFirst() cause crash(inS="{text=99, text.size={}")
        }
//Log.d("dbg", "inS.1=" + inS);
        if(inS.endsWith("]")) {
            inS=inS.substring(0,inS.length()-1);
        }
//Log.d("dbg", "inS.2=" + inS);
        rS=inS.replace(", ", ",");

//Log.d("dbg","inS.3="+inS);

//        Log.d("dbg","map.toString()="+map.toString());
        return rS;
    }




    //str2HexStr
//"a"->"61"
    String str2HexStr(String inS){
        String rS="";
//dbgMsg.set_dbgEn(0x0B);
        inS="a";


        return rS;
    }
//str2DecStr
//"a"->"97"

//str2OctStr
//"a"->"141"

//str2BinStr
//"a"->"00001010"



    //str2Hex
//"61"->(61/)
    byte[] str2Hex(String inS){
        return this.str2Hex(inS,0);
    }
    //str2Hex
//"61"->(61/)
    byte[] str2Hex(String inS,int fun){

        if((inS.length()%2)>0){
            //default padding back
            if((fun & _C_ADD_0_BACK_)>0){
                inS+="0";
            }
            else{
                inS="0"+inS;

            }
        }

        byte[] inByte=new byte[inS.length()];

// dbgMsg.set_dbgEn(0x03);
// dbgMsg.save("inS="+inS);
        //"0123"->(00/01/02/03)
        for(int i=0;i<inByte.length;i++){
//dbgMsg.save("s="+inS.substring(i*2,i*2+2));
            try{
                inByte[i]=Byte.parseByte(inS.substring(i,i+1),16);
            }
            catch(Exception e){
                inByte[i]=0x00;
            }
        }
        //(00/01/02/03)->(01/23/)
        int k=0;
        byte[] rByte=new byte[inByte.length/2];
        for(int i=0;i<rByte.length;i++){
            if(( fun & _C_LOW_BYTE_FIRST_)>0){
                k=(rByte.length-1)-i;
            }
            else if(i>0){
                k++;
            }
//dbgMsg.save("s="+inS.substring(i*2,i*2+2));
            rByte[k]=inByte[i*2];
            rByte[k]<<=4;
            rByte[k]|=inByte[i*2+1];
        }
// dbgMsg.save("rByte="+hex2Str(rByte));

//dbgMsg.save("rChar="+this.int2String(rChar[0]&0xFF,0));
        return rByte;
    }
    //hex2Str
//(61/)->"61"
    String hex2Str(byte[] inByte){
        String rS="";
//dbgMsg.set_dbgEn(0x0B);

        if(inByte!=null) {
            if (inByte.length > 0) {
                final StringBuilder sB = new StringBuilder(inByte.length);
                for (byte byteChar : inByte) {
                    sB.append(String.format("%02X", byteChar));
                }
                rS = sB.toString();
            }
        }
//    rS=Byte.toString(inByte[0]);//(61)->"97"
//dbgMsg.save("rS="+rS);
        return rS;
    }
//str2Dec
//"a"->97/

//str2Oct
//"a"->141/

//str2Bin
//"a"->00/00/10/10




//hex2Dec
//61/->97/

//hex2Oct
//61/->141/

//hex2Bin
//61/->01/10/00/01




//dec2Oct
//10/->12/

//dec2Bin
//10/->00/01/00/00



//oct2Bin
//12/->00/01/00/00

}



