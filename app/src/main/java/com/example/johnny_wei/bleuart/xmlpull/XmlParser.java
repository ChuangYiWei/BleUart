package com.example.johnny_wei.bleuart.xmlpull;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    //tag name
    private final static String ITEM = "item";
    private final static String FUNC_NAME = "func_name";
    private static String DATA = "data";
    private final static String CMD = "cmd";

    public static List<BLE_testItem> getTestItems(InputStream inStream) throws Throwable
    {
        List<BLE_testItem> BLE_testItemList = null;
        BLE_testItem obj_ble_testitem = null;
        boolean item_enable  = false;
        boolean select_mode  = false;
        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = pullFactory.newPullParser();

        parser.setInput(inStream, "UTF-8");
        //产生第一个事件
        int eventType = parser.getEventType();
        //只要不是文档结束事件，就一直循环
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("START_DOCUMENT","START_DOCUMENT");
                        break;
                    case XmlPullParser.START_TAG:
                        Log.d("START_TAG", parser.getName());
                        if (parser.getName().equals("UART")) {
                            Log.d("uart","new ArrayList<BLE_testItem>");
                            BLE_testItemList = new ArrayList<BLE_testItem>();
                            select_mode = true;
                        }
                        if (select_mode) {
                            obj_ble_testitem = addItem(obj_ble_testitem, parser);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("END_TAG", parser.getName());
                        if (parser.getName().equals("UART")) {
                            Log.d("/UART", "select_mode false");
                            select_mode = false;
                        }
                        if (select_mode) {
                            if (parser.getName().equals("item")) {
                                Log.d("/item", "/item");
                                BLE_testItemList.add(obj_ble_testitem);
                                obj_ble_testitem = null;
                            }
                        }

                        break;
                    default:
                        break;
                }

                eventType = parser.next();
            }
            eventType = parser.next();
        }
        return BLE_testItemList;
    }

    public static List<BLE_testItem> getTestItems(InputStream inStream, final String mode_str) throws Throwable
    {
        List<BLE_testItem> BLE_testItemList = null;
        BLE_testItem obj_ble_testitem = null;
        boolean item_enable  = false;
        boolean select_mode  = false;
        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = pullFactory.newPullParser();

        parser.setInput(inStream, "UTF-8");
        //产生第一个事件
        int eventType = parser.getEventType();
        //只要不是文档结束事件，就一直循环
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("START_DOCUMENT","START_DOCUMENT");
                        break;
                    case XmlPullParser.START_TAG:
                        Log.d("START_TAG", parser.getName());
                        if (parser.getName().equals(mode_str)) {
                            Log.d(mode_str,"new ArrayList<BLE_testItem>");
                            BLE_testItemList = new ArrayList<>();
                            select_mode = true;
                        }
                        if (select_mode) {
                            obj_ble_testitem = addItem(obj_ble_testitem, parser);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("END_TAG", parser.getName());
                        if (parser.getName().equals(mode_str)) {
                            Log.d(mode_str, "select_mode false");
                            select_mode = false;
                        }
                        if (select_mode) {
                            if (parser.getName().equals("item")) {
                                Log.d("/item", "/item");
                                BLE_testItemList.add(obj_ble_testitem);
                                obj_ble_testitem = null;
                            }
                        }

                        break;
                    default:
                        break;
                }

                eventType = parser.next();
            }
            eventType = parser.next();
        }
        return BLE_testItemList;
    }

    private static BLE_testItem addItem(BLE_testItem obj_ble_testitem, XmlPullParser parser) throws IOException, XmlPullParserException {
        if (parser.getName().equals(ITEM)) {
            Log.d("item", "meet item new obj");
            Log.d("item enable", parser.getAttributeValue(null, "enable"));
            obj_ble_testitem = new BLE_testItem();
        } else if (parser.getName().equals(FUNC_NAME)) {
            obj_ble_testitem.settestName(parser.nextText());
        } else if (parser.getName().equals(DATA)) {
            Log.d("data", "data");
            obj_ble_testitem.setdataStr(parser.nextText());
        } else if (parser.getName().equals(CMD)) {
            Log.d("cmd", "cmd");
            obj_ble_testitem.addCmd(parser.nextText());
        }
        return obj_ble_testitem;
    }
}
