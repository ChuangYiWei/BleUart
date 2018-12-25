package com.example.johnny_wei.bleuart.xmlpull;
import java.util.LinkedList;

public class BLE_testItem {
    private String testName;
    private String dataStr;

    private LinkedList<String> cmdList;

    BLE_testItem() {
        cmdList = new LinkedList<String>();
    }
    public String gettestName() {return testName;}
    public void settestName(String testName) {
        this.testName = testName;
    }

    public String getdataStr() {
        return dataStr;
    }
    public void setdataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public String getCmd() {
        if (cmdList.size() != 0) {
            return cmdList.getFirst();
        } else {
            return "cmd empty";
        }
    }

    public void addCmd(String cmd) {
        this.cmdList.add(cmd);
    }

    public String popCmd() {
        String cmd = cmdList.getFirst();
        cmdList.removeFirst();
        return cmd;
    }

    public int cmdSize() {
        return cmdList.size();
    }
}
