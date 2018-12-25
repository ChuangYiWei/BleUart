package com.example.johnny_wei.bleuart.common;

import java.util.ArrayList;
import java.util.List;

public class pString{
/*********************************************************************************************************************//**
     * DBG
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
    //==================================================================
//    private final String versionx="2017/05/02 21:16:58";
    //DBG ==============================================================
    private final String TAG=getClass().getSimpleName();
    private final String dbgExt=".dbg";
    private DBG dbgMsg;
/*********************************************************************************************************************//**
     * boolean
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
     * byte
     *************************************************************************************************************************/
    //====================================================================================================
    // private final byte[] bytearray={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F};
    // private byte[] byteTxMin=new byte[0],byteTxMax=new byte[0],byteTxUnit=new byte[0],byteTx=new byte[0];
    // private byte ignorefirst=0;
/*********************************************************************************************************************//**
     * int
     *************************************************************************************************************************/
    //--------------------------------------------------------------------------------------------------------------------
//  private final int _ShowOnScreen_    =0x01;
//  private final int _SaveFile_        =0x02;
//  private final int _ShowDeltaTime_   =0x04;
//  private final int _KeepFile_        =0x08;

    private int dbgEn=0;
    private final int _A_BYTEMODIFYTX_  =0x0001;
    private final int _A_PARSESTRING_   =0x0002;
    private final int _DBG_replace_     =0x0004;
    private final int _DBG_splitString_0_      =0x0008;
    //-------------------------
    //get_version
    private final int _B_SHOW_VERSION_  =0x0001;

    private int newstringcount;
    // private int[] intTxIndex=new int[0],intTxFun=new int[0];
    //     private final int _FUN_RANDOM_      =0x0001;
    //     private final int _FUN_SERIES_      =0x0002;
    //--------------------------------
    // static private final int _TABLE_LINE_=3;//each line has 3 element
    // static private final int _TABLE_STRING_    =0x00;
    // static private final int _TABLE_TYPE_      =0x01;
    // static private final int _TABLE_VALUE_     =0x02;
    //-------------------------
    static final int _A_ACTIVITY_           =0x00;
    static final int _A_MENU_               =0x01;
    static final int _A_LIST_               =0x02;
    static final int _A_SETTING_            =0x03;
    //-------------------------
    static final int _B_ACTIVITY_EN_           =0x00;
    static final int _B_ACTIVITY_NAME_         =0x01;
    static final int _B_MENU_EN_               =0x02;
    static final int _B_MENU_SETTING_EN_       =0x03;
    static final int _B_LIST_EN_               =0x04;

    //_S_MENU_EN_--------------
    public static final int _b_MENU_EN_SETTING_           =0x00;
    public static final int _b_MENU_EN_BACK_          	  =0x01;
    public static final int _b_MENU_EN_SEARCH_            =0x02;
    //------------
    public static final int _B_MENU_EN_SETTING_           =(0x01<<_b_MENU_EN_SETTING_);
    public static final int _B_MENU_EN_BACK_             =(0x01<<_b_MENU_EN_BACK_);
    public static final int _B_MENU_EN_SEARCH_           =(0x01<<_b_MENU_EN_SEARCH_);

    //_S_MENU_SETTING_EN_------
    public static final int _b_SETTING_ITEM_00_          =0x00;
    public static final int _b_SETTING_ITEM_01_          =0x01;
    public static final int _b_SETTING_ITEM_02_          =0x02;
    public static final int _b_SETTING_ITEM_03_          =0x03;
    public static final int _b_SETTING_ITEM_04_          =0x04;
    public static final int _b_SETTING_ITEM_05_          =0x05;
    public static final int _b_SETTING_ITEM_06_          =0x06;
    public static final int _b_SETTING_ITEM_07_          =0x07;
    //------------
    public static final int _B_SETTING_ITEM_00_          =(0x01<<_b_SETTING_ITEM_00_);
    public static final int _B_SETTING_ITEM_01_          =(0x01<<_b_SETTING_ITEM_01_);
    public static final int _B_SETTING_ITEM_02_          =(0x01<<_b_SETTING_ITEM_02_);
    public static final int _B_SETTING_ITEM_03_          =(0x01<<_b_SETTING_ITEM_03_);
    public static final int _B_SETTING_ITEM_04_          =(0x01<<_b_SETTING_ITEM_04_);
    public static final int _B_SETTING_ITEM_05_          =(0x01<<_b_SETTING_ITEM_05_);
    public static final int _B_SETTING_ITEM_06_          =(0x01<<_b_SETTING_ITEM_06_);
    public static final int _B_SETTING_ITEM_07_          =(0x01<<_b_SETTING_ITEM_07_);


    //_S_LIST_EN_-------------
    public static final int _B_LIST_imageview_00_     =0x01;
    public static final int _B_LIST_textview_00_      =0x02;
    public static final int _B_LIST_textview_01_      =0x04;
    public static final int _B_LIST_checkbox_00_      =0x08;
    public static final int _B_LIST_view_00_          =0x10;

    public static final int _B_LIST_EN_imageview_00_     =0x01;
    public static final int _B_LIST_EN_textview_00_      =0x02;
    public static final int _B_LIST_EN_textview_01_      =0x04;
    public static final int _B_LIST_EN_checkbox_00_      =0x08;
    public static final int _B_LIST_EN_view_00_          =0x10;
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
//----------------------------------------android_start
    private final String PACKAGE=getClass().getName();
    private static final String pathSD=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String pathDBG="dbg";
    private final String dbgFolder=pathSD+"/"+pathDBG+"/"+PACKAGE;
//----------------------------------------android
//----------------------------------------java_start
//    private final String dbgFolder=".";
//----------------------------------------java

    private final String dbgPath=dbgFolder+"/"+TAG+dbgExt;
    static final String _ITEM_SEP_      =   ",",
            _VALUE_SEP_ =   "/",
            _EQUAL_     =   "=";
    //-------------------------
    private String tableString;
    //-------------------------
    public static final String _S_ACTIVITY_EN_					    ="activity.en";
    public static final String _S_ACTIVITY_NAME_			        ="activity.name";			//activity name,use to search DB

    public static final String _S_MENU_EN_						   ="menu.en";					//menu enable

    public static final String _S_MENU_SETTING_EN_                 ="menu.setting.en";			//menu->setting enable,each bit enable one item


    public static final String _S_MENU_SETTING_ITEM_EN_            ="menu.setting.item";		//menu->setting item enable
    //bit_0:item0 enable
    //bit_1:item1 enable

    public static final String _S_MENU_SETTING_ITEM_00_		="menu.setting.item.00";	//menu->setting item[0] value,for debug
    public static final String _S_MENU_SETTING_ITEM_01_		="menu.setting.item.01";	//menu->setting item[1] value
    public static final String _S_MENU_SETTING_ITEM_02_		="menu.setting.item.02";	//menu->setting item[2] value
    public static final String _S_MENU_SETTING_ITEM_03_		="menu.setting.item.03";	//menu->setting item[3] value

    public static final String _S_ITEM_00_						="s.item.00";				//item[0]
    public static final String _S_ITEM_01_						="s.item.01";				//item[1]
    public static final String _S_ITEM_02_						="s.item.02";				//item[2]
    public static final String _S_ITEM_03_						="s.item.03";				//item[3]


    public static final String _S_LIST_EN_							="list.en";					//list enable,use in activity_main.java

    public static final String _S_EXPIRE_DATE_					="expire.date";				//after this date means expired
    public static final String _S_EXPIRE_VERSION_					="expire.version";			//class version,use for compare with _S_EXPIRE_DATE_

    //========================================common
    public static final String _S_0_                       ="0";                               //
    public static final String _S_1_                       ="1";                               //
    public static final String _S_2_                       ="2";                               //
    public static final String _S_3_                       ="3";                               //
    public static final String _S_4_                       ="4";                               //
    public static final String _S_5_                       ="5";                               //
    public static final String _S_6_                       ="6";                               //
    public static final String _S_7_                       ="7";                               //
    public static final String _S_8_                       ="8";                               //
    public static final String _S_9_                       ="9";                               //
    public static final String _S_10_                      ="10";                              //
    //-------------------------
    public static final String _S_1st_                     ="1st";                             //
    public static final String _S_2nd_                     ="2nd";                             //
    public static final String _S_3rd_                     ="3rd";                             //
    public static final String _S_4th_                     ="4th";                             //
    public static final String _S_5th_                     ="5th";                             //
    public static final String _S_6th_                     ="6th";                             //
    public static final String _S_7th_                     ="7th";                             //
    public static final String _S_8th_                     ="8th";                             //
    public static final String _S_9th_                     ="9th";                             //
    public static final String _S_10th_                    ="10th";                             //
    //-------------------------a
    public static final String _S_Action_                  ="action";
    public static final String _S_Activity_                ="activity";
    public static final String _S_Address_                 ="address";
    public static final String _S_Array_                   ="array";                           //
    public static final String _S_Arr_                     ="Arr";                             //
    public static final String _S_Ascii_                   ="ascii";                           //
    //-------------------------b
    public static final String _S_Bin_                     ="bin";                             //
    public static final String _S_BitSize_                 ="bitSize";                         //bitSize,padding to this size
    public static final String _S_BitSizeMin_              ="bitSizeMin";                      //bitSizeMin
    public static final String _S_BitSizeMax_              ="bitSizeMax";                      //bitSizeMax
    public static final String _S_BitSizeMask_             ="bitSizeMask";                     //
    public static final String _S_Baudrate_                ="baudrate";                        //COM
    public static final String _S_Button_                  ="button";                          //
    public static final String _S_Buffer_                  ="buffer";                          //COM
    public static final String _S_Block_                   ="block";                           //
    public static final String _S_Boolean_                 ="boolean";                         //
    public static final String _S_Byte_                    ="byte";                            //
    //-------------------------c
    public static final String _S_CheckBox_                ="checkBox";                        //
    public static final String _S_Choice_                  ="choice";                          //choice

    public static final String _S_Class_                   ="class";                           //
    public static final String _S_Clear_                   ="clear";                           //
    public static final String _S_Com_                     ="com";                             //
    public static final String _S_Console_                 ="console";                         //
    public static final String _S_Check_                   ="check";                           //
    public static final String _S_Count_                   ="count";                           //
    public static final String _S_Char_                    ="char";                            //
    //-------------------------d
    public static final String _S_DataBit_                 ="dataBit";                         //COM
    public static final String _S_Dbg_                     ="dbg";                             //
    public static final String _S_Dec_                     ="dec";                             //
    public static final String _S_Default_                 ="default";                         //
    public static final String _S_Delay_                   ="delay";                           //
    public static final String _S_Dot_                     =".";                               //
    public static final String _S_Device_                  ="device";                          //
    //-------------------------e
    public static final String _S_Enable_                  ="enable";                      //
    public static final String _S_Exe_                     ="exe";                      //
    //-------------------------f
    public static final String _S_Fail_                    ="fail";                            //
    public static final String _S_File_                    ="file";                            //file,only file name,ex:a.txt
    public static final String _S_FileHead_                ="fileHead";                        //fileHead
    public static final String _S_FileTail_                ="fileTail";                        //fileTail
    public static final String _S_FileExt_                 ="fileExt";                         //fileExt
    public static final String _S_FileAppend_              ="fileAppend";                      //fileAppend
    public static final String _S_FilePath_                ="filePath";                        //_S_Path_+_S_File_,ex:./a.txt
    public static final String _S_FileList_                ="fileList";                        //

    public static final String _S_Filter_                  ="filter";                          //

    public static final String _S_Fix_                     ="fix";                             //

    public static final String _S_Frame_                   ="frame";                           //
    public static final String _S_Function_                ="function";                        //function

    public static final String _S_FlowCtrl_                ="flowCtrl";                        //COM
    //-------------------------g
    public static final String _S_Group_                   ="group";                           //group
    //-------------------------h
    public static final String _S_Hex_                     ="hex";                             //
    public static final String _S_Hint_                    ="hint";                            //hint
    public static final String _S_HintHead_                ="hintHead";                        //hintHead
    public static final String _S_HintTail_                ="hintTail";                        //hintTail
    //-------------------------i
    public static final String _S_In_                      ="in";                              //
    public static final String _S_Index_                   ="index";                           //index

    public static final String _S_Input_                   ="input";                           //input
    public static final String _S_Include_                 ="include";                         //
    public static final String _S_Integer_                 ="integer";                         //
    public static final String _S_Int_                      ="int";                             //
    public static final String _S_Item_                     ="item";                            //
    //-------------------------j
    public static final String _S_Jmp_                     ="jmp";                            //
    //-------------------------k
    public static final String _S_Key_                     ="key";                             //
    public static final String _S_KeyArr_                  ="keyArr";                          //
    public static final String _S_KeyHead_                 ="keyHead";                         //
    public static final String _S_KeyTail_                 ="keyTail";                         //
    public static final String _S_KeyMin_                  ="keyMin";                          //
    public static final String _S_KeyMax_                  ="keyMax";                          //
    public static final String _S_KeyMask_                 ="keyMask";                         //
    //-------------------------l
    public static final String _S_Last_                    ="last";                            //
    public static final String _S_List_                    ="list";                            //
    public static final String _S_Lsb_                     ="lsb";                             //
    public static final String _S_Log_                     ="log";                             //
    public static final String _S_LoopBack_                ="loopBack";                        //

    public static final String _S_Label_                   ="label";                           //label
    public static final String _S_Layout_                  ="layout";                          //layout
    public static final String _S_Line_                    ="line";                            //
    public static final String _S_Long_                    ="long";                            //
    //-------------------------m
    public static final String _S_Map_                     ="map";
    public static final String _S_Mask_                    ="mask";                            //
    public static final String _S_MaxLen_                  ="maxLen";                          //

    public static final String _S_Method_                  ="method";                          //
    public static final String _S_Message_                 ="message";                         //message
    public static final String _S_MessageHead_             ="messageHead";                     //messageHead
    public static final String _S_MessageTail_             ="messageTail";                     //messageTail
    public static final String _S_Menu_                    ="menu";                            //Menu
    public static final String _S_Msb_                     ="msb";                             //

    //-------------------------n

    public static final String _S_Name_                    ="name";                            //
    public static final String _S_NameHead_                ="nameHead";                        //
    public static final String _S_NameTail_                ="nameTail";                        //
    public static final String _S_Next_                    ="next";                            //
    public static final String _S_Number_                  ="number";                          //

    public static final String _S_Nibble_                  ="nibble";                          //
    public static final String _S_NibbleSize_              ="nibbleSize";                      //nibbleSize,padding to this size
    public static final String _S_NibbleSizeMin_           ="nibbleSizeMin";                   //nibbleSizeMin
    public static final String _S_NibbleSizeMax_           ="nibbleSizeMax";                   //nibbleSizeMax
    public static final String _S_None_                    ="none";                            //
    //-------------------------o
    public static final String _S_Ok_                      ="ok";                              //
    public static final String _S_Off_                     ="off";                              //
    public static final String _S_Out_                     ="out";                             //
    public static final String _S_Output_                  ="output";                          //output
    public static final String _S_Other_                   ="other";                           //
    public static final String _S_Open_                    ="open";                            //
    //-------------------------p
    public static final String _S_Padding_                 ="padding";                         //
    public static final String _S_Path_                    ="path";                            //path,only file path,ex:./
    public static final String _S_Pause_                   ="pause";                         //
    public static final String _S_PathHead_                ="pathHead";                        //pathHead
    public static final String _S_PathTail_                ="pathTail";                        //pathTail

    public static final String _S_Part_                    ="part";                            //part

    public static final String _S_Parameter_               ="parameter";                      //

    public static final String _S_Parity_                  ="parity";                          //COM

    public static final String _S_Port_                    ="port";                            //COM

    public static final String _S_PValue_                  ="pValue";                          //

    public static final String _S_Panel_                   ="panel";                           //panel
    public static final String _S_Pane_                    ="pane";                            //scrollPane
    public static final String _S_Period_                  ="period";                          //
    public static final String _S_Password_                ="password";                        //
    //-------------------------q
    //-------------------------r
    public static final String _S_Result_                  ="result";                          //
    public static final String _S_Record_                  ="record";                             //
    public static final String _S_Rx_                       ="rx";                              //
    public static final String _S_RunTime_                 ="runTime";                         //

    public static final String _S_RadioButton_             ="radioButton";                     //radioButton
    //-------------------------s
    public static final String _S_Save_                    ="save";                            //save
    public static final String _S_Screen_                  ="screen";                          //
    public static final String _S_SizeMin_                 ="sizeMin";                         //sizeMin
    public static final String _S_SizeMax_                 ="sizeMax";                         //sizeMax
    public static final String _S_Sign_                    ="sign";                            //sign,significant

    public static final String _S_ShortCut_                ="shortCut";                        //shortcut
    public static final String _S_Star_                    ="star";                            //
    public static final String _S_StopBit_                 ="stopBit";                         //COM

    public static final String _S_StaticInclude_           ="staticInclude";                   //
    public static final String _S_String_                  ="string";                          //

    public static final String _S_ScrollPane_              ="scrollPane";                      //scrollPane
    public static final String _S_Sleep_                   ="sleep";                           //thread
    public static final String _S_Show_                    ="show";                            //
    public static final String _S_Size_                    ="size";                            //
    public static final String _S_Spinner_                 ="Spinner";                         //
    //-------------------------t
    public static final String _S_Timeout_                 ="timeout";                         //
    public static final String _S_Thread_                  ="thread";                          //
    public static final String _S_Type_                    ="type";                            //desc  :type of action
    //type  :string
    //value :none/hex(lsb)/dec(lsb)/bin/fix/choice(msb)/string(msb)

    public static final String _S_Title_                   ="title";                           //title
    public static final String _S_TitleHead_               ="titleHead";                       //titleHead
    public static final String _S_TitleTail_               ="titleTail";                       //titleTail
    public static final String _S_Title_Before_            ="titleBefore";                      //
    public static final String _S_Title_After_             ="titleAfter";                      //
    public static final String _S_Trigger_                 ="trigger";                         //
    public static final String _S_Tx_                      ="tx";                              //
    public static final String _S_Txt_                     ="txt";                             //

    public static final String _S_Tag_                     ="tag";                             //

    public static final String _S_Text_                    ="text";                            //text
    public static final String _S_True_                    ="true";                             //
    //-------------------------u
    public static final String _S_Ui_                      ="ui";                              //
    public static final String _S_Update_                  ="update";                          //
    public static final String _S_Unknow_                  ="unknow";                          //
    public static final String _S_Uuid_                    ="uuid";                          //

    //-------------------------v
    public static final String _S_Var_                     ="var";                             //
    public static final String _S_Value_                   ="value";                           //value
    public static final String _S_ValueMin_                ="valueMin";                        //valueMin
    public static final String _S_ValueMax_                ="valueMax";                        //valueMax
    public static final String _S_ValueMask_               ="valueMask";                       //
    public static final String _S_Variable_                ="variable";                        //
    public static final String _S_ValueArr_                ="valueArr";                        //
    //-------------------------w
    public static final String _S_WaitBefore_              ="waitBefore";                      //
    public static final String _S_WaitAfter_               ="waitAfter";                      //
    //-------------------------x

    //-------------------------y

    //-------------------------z
    //string[] =========================================================
    private String[] newstring;

    // private final String[] NibbleHex={  "0","1","2","3","4","5","6","7",
    //                                     "8","9","A","B","C","D","E","F"};

    // private final String[] NibbleBin={ "0000","0001","0010","0011",
    //                                    "0100","0101","0110","0111",
    //                                    "1000","1001","1010","1011",
    //                                    "1100","1101","1110","1111",};

/*********************************************************************************************************************//**
     * others
     *************************************************************************************************************************/
    //====================================================================================================
    private pConvertType ct=new pConvertType();
    private List<String> stringList=new ArrayList<String>();//tmp use
    private List<Integer> intList=new ArrayList<Integer>();//tmp use



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
//     public String get_version(int fun){
//         String vString=versionx+"\r\n";

//         if((fun & _B_SHOW_VERSION_)==_B_SHOW_VERSION_){
//             vString+=getClass().getName()+".java\r\n";
//             vString+="v1.6 2016/03/17\r\n";
//             vString+="     1.enhance splitString for multi seperate\r\n";
//             vString+="v1.5 2015/07/21\r\n";
//             vString+="     1.add escape()/removeComment()\r\n";
//             vString+="v1.5 2015/05/18\r\n";
//             vString+="     1.use StringBuilder for speedup\r\n";
// //            vString+="v1.4 2015/03/11\r\n";
// //          vString+="     1.modify replace for speed\r\n";
// //            vString+="v1.3 2014/09/28\r\n";
// //          vString+="     1.fix checkString count should --\r\n";
// //            vString+="1.2 2014/08/25\r\n";
// //            vString+="     1.modify comment\r\n";
// //            vString+="1.1 2014/08/19\r\n";
// //            vString+="     1.sync\r\n";
// //            vString+="1.0 2013/12/01\r\n";
// //            vString+="     1.modify for format\r\n";
// //            vString+="     2.set_dbgEn    ByteModifyTx    (0x01):byteModifyTx() dbg\r\n";
// //            vString+="                    ParseString     (0x02):parseString() dbg\r\n";
//         }

//         return vString;
//     }

    /**
     * get class debug setting
     *
     * @return          (int)   :class debug setting
     *                           <br>_A_BYTEMODIFYTX_    =0x0001
     *                           <br>_A_PARSESTRING_     =0x0002
     */
    public int get_dbgEn(){
        return dbgEn;
    }
    //================================================================================
    //set dbgEn
    //================================================================================
    //by int------------------------------------------------------------
    /**
     * set class debug setting by int
     *
     * @param    dbg    (int)   :class debug setting
     *                           <br>_A_BYTEMODIFYTX_    =0x0001
     *                           <br>_A_PARSESTRING_     =0x0002
     */
    public void set_dbgEn(int dbg){
        dbgEn=dbg;

        if(dbgEn==0){
            dbgMsg=null;
        }
        else{
            dbgMsg=new DBG(dbgPath,0x01);
        }
    }
    /**
     * set class debug setting by int,set DBG setting by int
     *
     * @param    dbg            (int)   :class debug setting
     *                                   <br>_A_BYTEMODIFYTX_    =0x0001
     *                                   <br>_A_PARSESTRING_     =0x0002
     * @param    dbgMsgEn       (int)   :DBG setting
     *                                   <br>_ShowOnScreen_      =0x0001
     *                                   <br>_SaveFile_          =0x0002
     *                                   <br>_ShowDeltaTime_     =0x0004
     *                                   <br>_KeepFile_          =0x0008
     */
    public void set_dbgEn(int dbg,int dbgMsgEn){
        set_dbgEn(dbg);

        if(dbgEn==0){
            dbgMsg=null;
        }
        else{
            dbgMsg=new DBG(dbgPath,dbgMsgEn);
        }
    }
    //by string---------------------------------------------------------
    /**
     * set class debug setting by string
     *
     * @param    fString    (String)   :class debug setting
     *                               <br>ByteModifyTx      =0x0001
     *                               <br>ParseString       =0x0002
     */
    // public void set_dbgEn(String fString){

    //     fString=fString.toLowerCase();

    //     dbgEn=0;//set 0 at first
    //     //dbgEn
    //     if(ct.isHex(fString)){
    //         dbgEn=Integer.parseInt(fString,16);
    //     }
    //     else{
    //         dbgEn=checkdbgEnString(fString);
    //     }
    //     //dbgMsgEn
    //     if(dbgEn>0){
    //         dbgMsg=new DBG(dbgPath,0x01);
    //     }
    // }
    /**
     * set class debug setting by string,DBG setting by String
     *
     * @param    fString        (String)   :class debug setting
     *                                       <br>ByteModifyTx      =0x0001
     *                                       <br>ParseString       =0x0002
     * @param    dbgMsgEn       (int)   :DBG setting
     */
    //  public void set_dbgEn(String fString,int dbgMsgEn){
    //      fString=fString.toLowerCase();

    //      dbgEn=0;//set 0 at first
    //      if(ct.isHex(fString)){
    //          dbgEn=Integer.parseInt(fString,16);
    //      }
    //      else{
    //          dbgEn=checkdbgEnString(fString);
    //      }
    //      //dbgMsgEn
    //      if(dbgEn>0){
    // dbgMsg=new DBG(dbgPath,dbgMsgEn);
    //      }
    //  }
    /**
     * parase class debug setting by string
     *
     * @param    fString        (String)   :class debug setting in string
     *                                       <br>ByteModifyTx      =0x0001
     *                                       <br>ParseString       =0x0002
     * @return                  (int)   :class debug setting in int
     */
    // private int checkdbgEnString(String fString){
    //     int tmpdbgEn=0;
    //     String checkString="";
    //     if(ct.isValid(fString)){
    //         for(int i=0;i<fString.length();i++){
    //             //----------------------------------
    //             checkString="ByteModifyTx";
    //                 if(fString.startsWith(checkString.toLowerCase(),i)){
    //                     tmpdbgEn|=_A_BYTEMODIFYTX_;
    //                     i+=checkString.length()-1;
    //                 }
    //             //----------------------------------
    //             checkString="ParseString";
    //                 if(fString.startsWith(checkString.toLowerCase(),i)){
    //                     tmpdbgEn|=_A_PARSESTRING_;
    //                     i+=checkString.length()-1;
    //                 }
    //         }
    //     }
    //     return tmpdbgEn;
    // }
    /**
     * get class help
     *
     * @return          (String)    :help message
     */
    // public String getHelp(){
    //     String hString=versionx+"\r\n";
    //     hString+=getClass().getName()+".java\r\n";

    //     hString+="     1.add setFun,getFun for function set,get\r\n";
    //     hString+="           set_dbgEn(0x0001/ByteModifyTx):_A_BYTEMODIFYTX_\r\n";
    //     hString+="           set_dbgEn(0x0002/ParseString):_A_PARSESTRING_\r\n";
    //     return hString;
    // }
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
//        int tmpfunen=0;
//        String checkString="";
//        if(ct.isValid(fString)){
//            for(int i=0;i<fString.length();i++){
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunen|=;
////                        i+=checkString.length()-1;
////                    }
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunen|=;
////                        i+=checkString.length()-1;
////                    }
    //----------------------------------
////                checkString="";
////                    if(fString.startsWith(checkString.toLowerCase(),i)){
////                        tmpfunen|=;
////                        i+=checkString.length()-1;
////                    }
//            }
//        }
//        return tmpfunen;
//    }

    //================================================================================
    //initial
    //================================================================================
    /**
     * class initial
     */
    private void initial(){
        newstring=new String[1];
        newstringcount=0;
    }
    /**
     * constructor
     */
    public pString(){
        initial();
        set_dbgEn(0);
    }
    //------------------------------------------------------------------
    public pString(String t){
        initial();
        set_dbgEn(0);
        tableString=t;
    }
    //------------------------------------------------------------------
    public pString(String[] t){
        initial();
        set_dbgEn(0);
//        table=t;
    }
    //------------------------------------------------------------------
//  public pString(int fun){
//        initial();
//        set_dbgEn(fun);
//  }
    //------------------------------------------------------------------
//  public pString(int fun,int funMsg){
//        initial();
//        set_dbgEn(fun,funMsg);
//  }
    //------------------------------------------------------------------
//  public pString(String fString){
//        initial();
//        set_dbgEn(fString);
//  }
    //------------------------------------------------------------------
//  public pString(String fString,int funMsg){
//        initial();
//        set_dbgEn(fString,funMsg);
//  }
    public boolean isValid(String inString){

        if(inString==null || inString=="")
            return false;
        else if(inString.length()==0)
            return false;
        else
            return true;
    }
    public boolean isValid(int inInt){
        return (inInt>=0) ? true:false;
    }
    /**
     * seperate orgString by seperateString and remove removeString
     * <br>dbgEn=_A_PARSESTRING_ =0x0002,show debug message
     *
     * @param    orgString          (String)    :orginal string
     * @param    seperateString     (String)    :seperate string
     * @param    removeString       (String[])  :remove string
     *
     * @return                      (String[])  :String array after seperate
     */
    public String[] parseString(String orgString,String seperateString,String[] removeString){


        newstringcount=0;

        String[] tmpString=orgString.split(seperateString);
        String[] tmpString_1=new String[tmpString.length];

        if(tmpString.length>0){

// if((dbgEn & _A_PARSESTRING_)==_A_PARSESTRING_){
// dbgMsg.save("-- dbgEn parseString ----------------------");
// }
            for(int i=0;i<tmpString.length;i++){
                //remove mark part
                while(tmpString[i].indexOf("//")!=-1){

                    String tuse="";

                    //in the last(with \r\n)--------------------------------------
                    if(tmpString[i].indexOf("\r\n") == tmpString[i].indexOf("//")+"//".length()){
//dbgMsg.save("check_1");

                        tuse="//\r\n";
                    }
                    //not in the last----------------------------------
                    else if(tmpString[i].indexOf("\r\n",tmpString[i].indexOf("//")+1) > tmpString[i].indexOf("//")){
//dbgMsg.save("check_2");
//dbgMsg.save("1:"+tmpString[i].indexOf("\r\n"));
//dbgMsg.save("2:"+tmpString[i].indexOf("//")+"//".length());
                        tuse=tmpString[i].substring(tmpString[i].indexOf("//"),tmpString[i].indexOf("\r\n",tmpString[i].indexOf("//")+1)+"\r\n".length());
                    }
                    //in the last(no \r\n)--------------------------------------
                    else{
                        tuse=tmpString[i].substring(tmpString[i].indexOf("//"),tmpString[i].length());
                    }


                    tmpString[i]=tmpString[i].replace(tuse,"");
                }
                //remove mark part


                for(int j=0;j<removeString.length;j++){
//dbgMsg.save("check_3 removeString["+j+"]="+removeString[j]);
                    while(tmpString[i].indexOf(removeString[j])!=-1){

                        tmpString[i]=tmpString[i].replaceFirst(removeString[j],"");
                    }
                }

                if(!tmpString[i].equals("")){//if not empty string save tmp save
                    tmpString_1[newstringcount]=tmpString[i];
                    newstringcount++;
                }

            }

            newstring=new String[newstringcount];


            for(int i=0;i<newstringcount;i++){
                newstring[i]=tmpString_1[i];



// if((dbgEn & _A_PARSESTRING_)==_A_PARSESTRING_){
// dbgMsg.save("newstring["+i+"]="+newstring[i]);
// }
            }
// if((dbgEn & _A_PARSESTRING_)==_A_PARSESTRING_){
// dbgMsg.save("newstring.length="+newstring.length);
// }
        }

        return newstring;
    }

    /**
     * seperate inputString by seperateString and get string which start with TargetString
     *
     * //@param    inputString        (String)        :orginal string
     * @param    TargetString       (String)        :traget string
     * @param    seperateString     (String)      :seperate string
     *
     * @return                      (String)        :part of String
     */

    public String getPartOfString(String inString,String TargetString,String seperateString){
        String rString=null;
        String[] tString;
        int tmpIndex=-1;

//dbgMsg.set_dbgEn(0x03);

//dbgMsg.save("pstring.getpartofstring1.inString="+inString);
//dbgMsg.save("pstring.getPartOfString1.TargetString="+TargetString);
//dbgMsg.save("pstring.getPartOfString1.seperateString="+seperateString);
        if(inString.equals(null) || TargetString.equals(null) || seperateString.equals(null)){
            return null;
        }

//        tString=inString.split("["+seperateString+"]");//regular-expression
        tString=this.splitString(inString,seperateString);

//dbgMsg.save("getPartOfString.tString.length="+tString.length);
        for(int i=0;i<tString.length;i++){
//dbgMsg.save("pstring.getPartOfString1.tString["+i+"]="+tString[i]);
            tmpIndex=tString[i].indexOf(TargetString);
            if(tmpIndex==0){
                rString=tString[i].substring(TargetString.length(),tString[i].length());
                break;
            }
        }
//dbgMsg.save("pString.getPartOfString1.rString="+rString);
        return rString;
    }

    public String getPartOfString1(String inString,String TargetString,String seperateString){
        String rString=null;
        String[] tString;
        int tmpIndex=-1;
//dbgMsg.set_dbgEn(0x03);

//dbgMsg.save("pstring.getPartOfString.inString="+inString);
//dbgMsg.save("pstring.getPartOfString.TargetString="+TargetString);
//dbgMsg.save("pstring.getPartOfString.seperateString="+seperateString);
        if(inString.equals(null) || TargetString.equals(null) || seperateString.equals(null)){
            return null;
        }

        tString=inString.split("["+seperateString+"]");//regular-expression

//dbgMsg.save("getPartOfString.tString.length="+tString.length);
        for(int i=0;i<tString.length;i++){
//dbgMsg.save("pstring.getPartOfString.tString["+i+"]="+tString[i]);
            tmpIndex=tString[i].indexOf(TargetString);
            if(tmpIndex==0){
                rString=tString[i].substring(TargetString.length(),tString[i].length());
                break;
            }
        }
//dbgMsg.save("pstring.getPartOfString.rString="+rString);
        return rString;
    }

    /** ************************************************************************************************************
     * @brief    update part of String
     *
     * @param    [IN]    inString        (String)    :input string                               <br>
     * @param    [IN]    TargetString    (String)    :target sring                                <br>
     * @param    [IN]    seperateString  (String)    :seperate string            <br>
     * @param    [IN]    updateString    (String)    update string            <br>
     *
     * @return                           (String)    :input string after modify<br>
     *
     * @note     ex: inString        =index=0000,range=0000,
     *               TargetString    =range=
     *               seperateString  =,
     *               updateString    =0000[7:0]/
     *               rString         =index=0000,range=0000[7:0]/,
     ***************************************************************************************************************/
    int speedCheck=1;
    public String setPartOfString(String inString,String TargetString,String seperateString,String updateString){
        String rString=null;
        String[] tString;
        int tmpIndex=-1;
        StringBuilder setPartOfStringSB_0=new StringBuilder(100);
//dbgMsg.set_dbgEn(0x03);
//dbgMsg.showDeltaTime(0);
//dbgMsg.save("pstring.getPartOfString1.inString="+inString);
//dbgMsg.save("pstring.getPartOfString1.TargetString="+TargetString);
//dbgMsg.save("pstring.getPartOfString1.seperateString="+seperateString);
//dbgMsg.save("pstring.getPartOfString1.updateString="+updateString);
        if(inString.equals(null) || TargetString.equals(null) || seperateString.equals(null)){
            return null;
        }

//dbgMsg.showDeltaTime(1);
//        tString=inString.split("["+seperateString+"]");//regular-expression
        tString=this.splitString(inString,seperateString);
//dbgMsg.showDeltaTime(2);
//dbgMsg.save("getPartOfString.tString.length="+tString.length);
        for(int i=0;i<tString.length;i++){
//dbgMsg.save("pstring.getPartOfString1.tString["+i+"]="+tString[i]);
            tmpIndex=tString[i].indexOf(TargetString);
            if(tmpIndex==0){
//                rString=tString[i].substring(TargetString.length(),tString[i].length());
                tString[i]=tString[i].substring(0,TargetString.length())+updateString;
                break;
            }
        }
//dbgMsg.showDeltaTime(3);
        if(speedCheck==0)
            rString="";

        setPartOfStringSB_0.setLength(0);

        for(int i=0;i<tString.length;i++){
            if(!tString[i].equals("")){
                if(speedCheck>0){
                    setPartOfStringSB_0.append(tString[i]+seperateString);
                }
                else{
                    rString+=tString[i]+seperateString;
                }
            }
        }
//dbgMsg.showDeltaTime(4);
//dbgMsg.save("pstring.getPartOfString1.rString="+rString);
        if(speedCheck>0){
//dbgMsg.save("pstring.getPartOfString1.rString="+setPartOfStringSB_0.toString());
            return setPartOfStringSB_0.toString();
        }
        else{
            return rString;
        }
    }




    /** ************************************************************************************************************
     * @brief    replace String
     *
     * param    [IN]    str         (String):org String                          <br>
     * param    [IN]    patten      (String):String to be replace                <br>
     * param    [IN]    replacement (String):replace String                      <br>
     * param    [IN]    pos         (int)   :String index(replacement)           <br>
     *
     * @return   (String)
     ***************************************************************************************************************/
//http://www.javacodegeeks.com/2010/09/string-performance-exact-string.html
    public String _replace(String str,String patten,String replacement,int pos)
    {
        int len=str.length();
        int plen=patten.length();
        StringBuilder newContent=new StringBuilder(len);

        int lastPos=0;

        do{
            newContent.append(str,lastPos,pos);
            newContent.append(replacement);
            lastPos=pos+plen;
            pos=str.indexOf(patten,lastPos);
        }
        while(pos>0);

        newContent.append(str,lastPos,len);

        return newContent.toString();
    }


    public String replace(String str,String patten,String replacement)
    {
        int pos=str.indexOf(patten);
        return pos<0 ? str : _replace(str,patten,replacement,pos);
    }
    /** ************************************************************************************************************
     * @brief    replace String
     *
     * @param    [IN]    str         (String):org String                          <br>
     * @param    [IN]    patten      (String):String to be replace                <br>
     * @param    [IN]    replacement (String):replace String                      <br>
     * @param    [IN]    pos         (int)   :String index(replacement)           <br>
     * @param    [IN]    fun         (int)   :function                             <br>
     *                                       :_FUN_EVEN_=0x0001:only replace even char<br>
     *
     * @return   (String)
     ***************************************************************************************************************/

    int _FUN_EVEN_ =0x0001;//only replace even char
    public String _replace(String str,String patten,String replacement,int pos,int fun)
    {
        int len=str.length();
        int plen=patten.length();
        StringBuilder newContent=new StringBuilder(len);

        int lastPos=0;
// if((dbgEn&_DBG_replace_)>0){
// dbgMsg.save("_replace.str="+str);
// dbgMsg.save("_replace.newContent="+newContent.toString());
// dbgMsg.save("_replace.len="+len);
// dbgMsg.save("_replace.lastPos.00="+lastPos);
// dbgMsg.save("_replace.pos.00="+pos);
// dbgMsg.save("---------------------");
// }
        do{
            newContent.append(str,lastPos,pos);
            newContent.append(replacement);
            lastPos=pos+plen;

            pos=str.indexOf(patten,lastPos);
            while(pos%2>0 && (fun&_FUN_EVEN_)>0){
// if((dbgEn&_DBG_replace_)>0){
// dbgMsg.save("_replace.pos.01="+pos);
// dbgMsg.save("_replace.lastPos.01="+lastPos);
// }
                pos++;
                pos=str.indexOf(patten,pos);
            }

// if((dbgEn&_DBG_replace_)>0){
// dbgMsg.save("_replace.pos.02="+pos);
// dbgMsg.save("_replace.lastPos.02="+lastPos);
// }
        }
        while(pos>0);

        newContent.append(str,lastPos,len);
// if((dbgEn&_DBG_replace_)>0){
// dbgMsg.save("---------------------");
// dbgMsg.save("_replace.str.02="+newContent.toString());
// }
        return newContent.toString();
    }


    /** ************************************************************************************************************
     * @brief    replace String
     *
     * param    [IN]    str         (String):String before replace                         <br>
     * param    [IN]    patten      (String):String to be replace                <br>
     * param    [IN]    replacement (String):replace String                      <br>
     * param    [IN]    fun         (int)   :function                             <br>
     *                                       :_FUN_EVEN_=0x0001:only replace even char<br>
     *
     * @return                       (String):String after replace
     ***************************************************************************************************************/
    public String replace(String str,String patten,String replacement,int fun)
    {
        int pos=str.indexOf(patten);

        while(pos%2>0 && (fun&_FUN_EVEN_)>0){
            pos++;
            pos=str.indexOf(patten,pos);
// if((dbgEn&_DBG_replace_)>0){
// dbgMsg.save("replace.pos="+pos);
// }
        }
// if((dbgEn&_DBG_replace_)>0){
// if(pos<0){
// dbgMsg.save("replace=replace not exist");
// }
// }
        return pos<0 ? str : _replace(str,patten,replacement,pos,fun);
    }
/*
public String replace(String text, String searchString, String replacement) {
    return _replace(text, searchString, replacement, -1);
}

public String _replace(String text, String searchString, String replacement, int max) {
    if (ct.isValid(text) || ct.isValid(searchString) || replacement == null || max == 0) {
        return text;
    }
    int start = 0;
    int end = text.indexOf(searchString, start);
    if (end == -1) {
        return text;
    }
    int replLength = searchString.length();
    int increase = replacement.length() - replLength;
    increase = (increase < 0 ? 0 : increase);
    increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
    StringBuffer buf = new StringBuffer(text.length() + increase);
    while (end != -1) {
        buf.append(text.substring(start, end)).append(replacement);
        start = end + replLength;
        if (--max == 0) {
            break;
        }
        end = text.indexOf(searchString, start);
    }
    buf.append(text.substring(start));
    return buf.toString();
}
*/
    /** ************************************************************************************************************
     * @brief    split String
     *
     * @param    [IN]    inString       (String):input string                         <br>
     * @param    [IN]    sepString      (String):seperate string                <br>
     *
     * @return                          (String[]):String array
     ***************************************************************************************************************/
    public static final int _IGNORE_CASE_        =0x0001;
    public static final int _KEEP_SEPSTRING_     =0x0002;
    public static final int _IGNORE_NOT_MATCH_   =0x0004;
    public static final int _FIRST_CAN_BE_NULL_  =0x0008;

    public String[] splitString(String inString,String sepString)
    {
        return splitString(inString,sepString,"",0);
    }

    private List<String> list_split1=new ArrayList<String>();//tmp use
    private List<String> list_split2=new ArrayList<String>();//tmp use
    /** ************************************************************************************************************
     * @brief    split String
     *
     * param    [IN]    inString       (String):input string                         <br>
     * param    [IN]    sepString      (String[]):seperate string array                <br>
     *
     * @return                          (String[]):String array
     ***************************************************************************************************************/
    public String[] splitString(String inString,String[] sepString)
    {
        return splitString(inString,sepString,0);
    }

    public String[] splitString(String inString,String[] sepString,int fun)
    {

        String[] kk={""};

        list_split1.clear();
        list_split2.clear();

        list_split1.add(inString);


        for(int i=0;i<sepString.length;i++){

// if((dbgEn & _DBG_splitString_0_)>0){
//     dbgMsg.save("-------------------");
//     dbgMsg.save("splitString."+ct.int2String(i)+"="+sepString[i]);
// }

            for(int j=0;j<list_split1.size();j++){

// if((dbgEn & _DBG_splitString_0_)>0){
//     dbgMsg.save("list_split1."+ct.int2String(j)+".0="+list_split1.get(j));
// }
                kk=splitString(list_split1.get(j),sepString[i],"",fun);

                for(int k=0;k<kk.length;k++){
                    list_split2.add(kk[k]);
// if((dbgEn & _DBG_splitString_0_)>0){
//     dbgMsg.save("\tlist_split2."+ct.int2String(k)+".0="+kk[k]);
// }
                }
            }

            list_split1=new ArrayList<String>(list_split2);
            list_split2.clear();
        }

// if((dbgEn & _DBG_splitString_0_)>0){
//         for(int i=0;i<list_split1.size();i++){
// dbgMsg.save("\t-------------------");
// dbgMsg.save("\tlist_split1."+ct.int2String(i)+".f="+list_split1.get(i));
//         }
// }

        return list_split1.toArray(new String[list_split1.size()]);
    }
    /** ************************************************************************************************************
     * @brief    split String
     *
     * param    [IN]    inString       (String) :input string                           <br>
     * param    [IN]    sepString      (String) :seperate string                        <br>
     * param    [IN]    fun            (int)    :function                               <br>
     *                                           _IGNORE_CASE_     =0x0001;              <br>
     *                                           _KEEP_SEPSTRING_  =0x0002;              <br>
     *
     * @return                          (String[]):String array
     ***************************************************************************************************************/
    public String[] splitString(String inString,String sepString,int fun)
    {
        return splitString(inString,sepString,"",fun);

    }
    /** ************************************************************************************************************
     * @brief    split String
     *
     * param    [IN]    inString       (String) :input string                           <br>
     * param    [IN]    sepString      (String) :seperate string                        <br>
     * param    [IN]    fun            (int)    :function                               <br>
     *                                           _IGNORE_CASE_     =0x0001;              <br>
     *                                           _KEEP_SEPSTRING_  =0x0002;              <br>
     *                                           _IGNORE_NOT_MATCH_=0x0004;              <br>
     *
     * @return                          (String[]):String array
     ***************************************************************************************************************/
//ex:   inString    ="index=99,sub.index=0,sub.message=k0,sub.index=1,sub.message=k1,sub.index=2,sub.message=k2,sub.sub.index=3,sub.sub.message=k3"
//      sepString   ="sub.index="
//      sepString1  =","
//      fun         =0x02


    public String[] splitString(String inString,String sepString,String sepString1,int fun)
    {
//dbgMsg.set_dbgEn(0x03);
//dbgMsg.save("---------");
//dbgMsg.save("pstring.splitstring.inString="+inString);
//dbgMsg.save("pstring.splitstring.sepString="+sepString);
//dbgMsg.save("pstring.splitstring.sepString1="+sepString1);
//dbgMsg.save("pstring.splitstring.fun="+fun);
        String[] rStringArray={new String(inString)};
        String[] rStringArrayLow={new String(inString)};

        String in;
        String sep,sep1;
        String tmpS0="";
        int count=0,tmpInd;
        int[] index;
        boolean trig=false;

        if((fun&_IGNORE_CASE_)>0){
            in=new String(inString.toLowerCase());
            sep=new String(sepString.toLowerCase());
        }
        else{
            in=new String(inString);
            sep=new String(sepString);

        }

        sep1=new String(sepString1);
        //count
        intList.clear();
        tmpInd=in.indexOf(sep1+sep);

        while(tmpInd>=0){

            if(tmpInd>0 && intList.size()==0){
                intList.add(0);//add first,start from 0
            }
            intList.add(tmpInd);
//dbgMsg.save("pstring.splitstring.tmpInd.0="+tmpInd);
            tmpInd=in.indexOf(sep1+sep,tmpInd+sep.length()+sep1.length());
//dbgMsg.save("pstring.splitstring.tmpInd.1="+tmpInd);
        }
        //count
//dbgMsg.save("pstring.splitstring.count.0="+count);

        if(intList.size()==0){
            return rStringArray;
        }

        if(intList.get(intList.size()-1)<(inString.length())){
            intList.add(inString.length());//add last
        }


        rStringArray=new String[intList.size()-1];
        rStringArrayLow=new String[intList.size()-1];

        for(int i=0;i<intList.size()-1;i++){
            tmpS0=inString.substring(intList.get(i),intList.get(i+1));

            //move sep1 to the last of string
//            if(ct.isValid(sep1))
            if(isValid(sep1))
            {
                //remove first
                while(tmpS0.indexOf(sep1)==0){
                    tmpS0=this.replaceFirst(tmpS0,sep1,"");
                }

                //remove last
                while((tmpS0.lastIndexOf(sep1)+sep1.length())==tmpS0.length()){
//dbgMsg.save("pstring.splitstring.tmpS0.1="+tmpS0);
                    tmpS0=this.replaceLast(tmpS0,sep1,"");
//dbgMsg.save("pstring.splitstring.tmpS0.2="+tmpS0);
                }
                tmpS0=tmpS0+sep1;

            }
            //move sep1 to the last of string


            rStringArray[i]     =tmpS0;
            rStringArrayLow[i]  =tmpS0.toLowerCase();
//dbgMsg.save("pstring.splitstring.rStringArray   ["+i+"].0="+rStringArray[i]);
//dbgMsg.save("pstring.splitstring.rStringArrayLow["+i+"].0="+rStringArrayLow[i]);
        }

        tmpInd=sep.length()+sep1.length();//length use
        stringList.clear();


        for(int i=0;i<intList.size()-1;i++){
            trig=false;
//dbgMsg.save("pstring.splitstring.rStringArray    ["+i+"].1="+rStringArray[i]);
            if((fun&_IGNORE_CASE_)>0)//use rStringArrayLow
            {
                if((fun&_IGNORE_NOT_MATCH_)>0){
                    if(rStringArrayLow[i].indexOf(sep)!=0)
                    {
                        rStringArrayLow[i]="";
                        rStringArray[i]="";
                    }
                }

                if((fun&_KEEP_SEPSTRING_)==0){
                    if(rStringArrayLow[i].indexOf(sep)==0)
                    {
                        trig=true;
                        rStringArrayLow[i]=rStringArrayLow[i].substring(sep.length(),rStringArrayLow[i].length());
                        rStringArray[i]=rStringArray[i].substring(sep.length(),rStringArray[i].length());
                    }
                }

            }
            else//use rStringArray
            {
                if((fun&_IGNORE_NOT_MATCH_)>0){
                    if(rStringArray[i].indexOf(sep)!=0)
                    {
                        rStringArrayLow[i]="";
                        rStringArray[i]="";
                    }
                }

                if((fun&_KEEP_SEPSTRING_)==0){
                    if(rStringArray[i].indexOf(sep)==0)
                    {
                        trig=true;
                        rStringArrayLow[i]=rStringArrayLow[i].substring(sep.length(),rStringArrayLow[i].length());
                        rStringArray[i]=rStringArray[i].substring(sep.length(),rStringArray[i].length());
                    }
                }
            }
//dbgMsg.save("pstring.splitstring.rStringArray    ["+i+"].2="+rStringArray[i]);

//            if(ct.isValid(rStringArray[i]))
            if(isValid(rStringArray[i]))
            {
                if(i==0)
                {
                    if(     ( fun & _FIRST_CAN_BE_NULL_)>0
                            &&  trig )
                    {
                        stringList.add("");
                    }
                }
                stringList.add(rStringArray[i]);
            }
        }

        rStringArray=new String[stringList.size()];

        for(int i=0;i<rStringArray.length;i++){
            rStringArray[i]=stringList.get(i);
//dbgMsg.save("pstring.splitString1.rStringArray["+i+"].0="+rStringArray[i]);
        }

        return rStringArray;
    }
    /** ************************************************************************************************************
     * @brief    replace last string
     *
     * param    [IN]   inString     (String)   :input string                            <br>
     * param    [IN]   targeString  (String)   :target string                            <br>
     * param    [IN]   replaceString(String)   :replace string                            <br>
     *
     * @return                    (String)   :String after process
     *
     * @note:    inString        =123456123456
     *           targeString     =23
     *           replaceString   =00
     *           rString         =123456100456
     ***************************************************************************************************************/
    public String replaceLast(String inString,String targeString,String replaceString){
        int index=inString.lastIndexOf(targeString);
        String rString;

//        if(ct.isValid(index))
        if(isValid(index))
        {
            rString=inString.substring(0,index)+replaceString+inString.substring(index+targeString.length(),inString.length());
        }
        else{
            rString=inString;
        }

        return rString;
    }
    /** ************************************************************************************************************
     * @brief    replace first string
     *
     * param    [IN]   inString     (String)   :input string                            <br>
     * param    [IN]   targeString  (String)   :target string                            <br>
     * param    [IN]   replaceString(String)   :replace string                            <br>
     *
     * @return                    (String)   :String after process
     *
     * @note:    inString        =123456123456
     *           targeString     =23
     *           replaceString   =00
     *           rString         =100456123456
     ***************************************************************************************************************/
    public String replaceFirst(String inString,String targeString,String replaceString){
        int index=inString.indexOf(targeString);
        String rString;

//        if(ct.isValid(index))
        if(isValid(index))
        {
            rString=inString.substring(0,index)+replaceString+inString.substring(index+targeString.length(),inString.length());
        }
        else{
            rString=inString;
        }

        return rString;
    }
/*
    //use new ,cost more extra 30~50% time
    String[] splitString(String inString,String sepString,int fun)
    {
//dbgMsg.set_dbgEn(0x03);
//dbgMsg.save("-------------------");
//dbgMsg.save("pstring.splitstring.inString="+inString);
//dbgMsg.save("pstring.splitstring.sepString="+sepString);
        String[] rString={inString};
        String in=new String(inString.toLowerCase());
        String sep=new String(sepString.toLowerCase());
        int count=0,tmpInd;
        int[] index;

        if((fun&_IGNORE_CASE_)>0){
            in=new String(inString.toLowerCase());
            sep=new String(sepString.toLowerCase());
        }
        else{
            in=new String(inString);
            sep=new String(sepString);
        }
        //count
        tmpInd=in.indexOf(sep);
        while(tmpInd>=0){
            count++;
//dbgMsg.save("pstring.splitstring.tmpInd.0="+tmpInd);
            tmpInd=in.indexOf(sep,tmpInd+sep.length());
//dbgMsg.save("pstring.splitstring.tmpInd.1="+tmpInd);
        }
        //count
//dbgMsg.save("pstring.splitstring.count.0="+count);

        if(count==0){
            return rString;
        }

        if(in.indexOf(sep)>0){
            count++;
        }
//dbgMsg.save("pstring.splitstring.count.1="+count);
        rString=new String[count];
        index=new int[count];

        count=0;
        if(in.indexOf(sep)>0){
            index[count]=0;
            count++;
        }

        //save index
        tmpInd=in.indexOf(sep);
        while(tmpInd>=0){
            index[count]=tmpInd;
//dbgMsg.save("pstring.splitstring.index["+count+"]="+index[count]);
            count++;

            tmpInd=in.indexOf(sep,tmpInd+sep.length());
//dbgMsg.save("pstring.splitstring.tmpInd="+tmpInd);
        }
        //save index


        for(int i=0;i<count;i++){
            if(i<(count-1)){
                rString[i]=inString.substring(index[i],index[i+1]);
            }
            else{
                rString[i]=inString.substring(index[i],inString.length());
            }
//dbgMsg.save("pstring.splitstring.rString["+i+"].0="+rString[i]);
        }
//dbgMsg.save("count="+count);
        tmpInd=sep.length();
        int tmpcount=count;
        if((fun&_KEEP_SEPSTRING_)==0){
tmpcount=0;
            for(int i=0;i<count;i++){
                if(rString[i].indexOf(sep)==0){
                    rString[i]=rString[i].substring(tmpInd,rString[i].length());
                }
                if((fun&_IGNORE_CASE_)>0){
                    if(rString[i].toLowerCase().indexOf(sep)==0){
                        rString[i]=rString[i].substring(tmpInd,rString[i].length());
                    }
                }
                if(ct.isValid(rString[i])){
                    tmpcount++;
                }
            }
        }
//dbgMsg.save("tmpcount="+tmpcount);
        String[] rString1=new String[tmpcount];

        count=0;
        for(int i=0;i<rString.length;i++){
            if(ct.isValid(rString[i])){
//dbgMsg.save("i="+i);
                rString1[count]=rString[i];

//dbgMsg.save("pstring.splitstring.rString1["+count+"].1="+rString1[count]);
                count++;

            }
        }

        return rString1;
    }
*/


    /** ************************************************************************************************************
     * @brief    modify escape from char to escape,ex from "\\r\\n" to "\r\n",can use unicode (\\uXXXX)
     *
     * param    [IN]   inString  (String)   :input string for modify                            <br>
     *
     * @return                    (String)   :String after process
     *
     * @note:    unicode fix 4 char,ex:\\uXXXX
     ***************************************************************************************************************/
    public String escape(String inputS){

        String rString=new String(inputS);
//        if(!ct.isValid(rString))
        if(!isValid(rString))
        {
            rString="";
        }

        int tmpInd=0;
        String tmpS0="";
        //escape-------------------------------------------------------
//dbgMsg.save("pString.escape.inputS.i="+rString);
        rString=this.replace(rString,"\\r\\n","\r\n");

        rString=this.replace(rString,"\\n","\n");

        rString=this.replace(rString,"\\r","\r");

        rString=this.replace(rString,"\\t","\t");

        rString=this.replace(rString,"\\b","\b");

        rString=this.replace(rString,"\\f","\f");

        rString=this.replace(rString,"\\s"," ");

        rString=this.replace(rString,"\\0","\0");        //null
//dbgMsg.save("pString.escape.inputS.0="+rString);
        //Unicode-------------------------------------------------------
        tmpInd=rString.indexOf("\\u");
//dbgMsg.save("modifyEscape.tmpInd="+tmpInd);
//dbgMsg.save("modifyEscape.tmpS0.0="+tmpS0);
//dbgMsg.save("modifyEscape.tmpS0.1="+ct.string2Unicode("0020"));
//dbgMsg.save("modifyEscape.tmpS0.1="+ct.string2Unicode(sA));

        while(tmpInd!=-1){
            tmpS0=rString.substring(tmpInd,tmpInd+6);
            rString=this.replace(rString,tmpS0,ct.string2Unicode(tmpS0));
            tmpInd=rString.indexOf("\\u");
//dbgMsg.save("modifyEscape.tmpInd="+tmpInd);
//dbgMsg.save("modifyEscape.tmpS0.0="+tmpS0);
//dbgMsg.save("modifyEscape.rString.1="+rString);

        }
        //Unicode-------------------------------------------------------

        return rString;
    }


}


//----------------------------------------a
//----------------------------------------b
//----------------------------------------c
//----------------------------------------d
//----------------------------------------e
//----------------------------------------f
//----------------------------------------g
//----------------------------------------h
//----------------------------------------i
//----------------------------------------j
//----------------------------------------k
//----------------------------------------l
//----------------------------------------m
//----------------------------------------n
//----------------------------------------o
//----------------------------------------p
//----------------------------------------q
//----------------------------------------r
//----------------------------------------s
//----------------------------------------t
//----------------------------------------u
//----------------------------------------v
//----------------------------------------w
//----------------------------------------x
//----------------------------------------y
//----------------------------------------z
//----------------------------------------others
























//--------------------
//----------------------------------------
//------------------------------------------------------------
//--------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------


//====================
//========================================
//============================================================
//================================================================================
//====================================================================================================
//========================================================================================================================


/*********************************************************************************************************************//**
 * DBG
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
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
/*********************************************************************************************************************//**
 * others
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * FUNCTION
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------



/*********************************************************************************************************************//**
 * A
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * B
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * C
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * D
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * E
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * F
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * G
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * H
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * I
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * J
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * K
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * L
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * M
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * N
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * O
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * P
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * Q
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * R
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * S
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * T
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * U
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * V
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * W
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * X
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * Y
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * Z
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------
/*********************************************************************************************************************//**
 * OTHERS
 *************************************************************************************************************************/
//--------------------------------------------------------------------------------------------------------------------



