package com.example.johnny_wei.bleuart;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Parserfuncs {
    public static List<BDTestitem> getTestFunctions(InputStream inStream) throws Throwable
    {
        List<BDTestitem> BDTestitemList = null;
        BDTestitem BDTestitem = null;


        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = pullFactory.newPullParser();

        parser.setInput(inStream, "UTF-8");
        //产生第一个事件
        int eventType = parser.getEventType();
        //只要不是文档结束事件，就一直循环
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                //触发开始文档事件
                case XmlPullParser.START_DOCUMENT:
                    BDTestitemList = new ArrayList<BDTestitem>();
                    break;
                //触发开始元素事件
                case XmlPullParser.START_TAG:
                    //获取解析器当前指向的元素的名称
                    String name = parser.getName();
                    if("BD_function".equals(name))
                    {
                        //通过解析器获取id的元素值，并设置student的id
                        BDTestitem = new BDTestitem();
                        BDTestitem.setName(parser.nextText());
                        BDTestitemList.add(BDTestitem);
                        BDTestitem = null;
                    }

                    break;
                //触发结束元素事件
                case XmlPullParser.END_TAG:
//                    Log.d("END_TAG getName() : ", parser.getName());
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return BDTestitemList;
    }
}
