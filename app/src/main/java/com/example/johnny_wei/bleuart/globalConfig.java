package com.example.johnny_wei.bleuart;

public class globalConfig {

    //drop down menu
    public static final int SPI_MODE = 0;
    public static final int UART_MODE = 1;
    static final int AIR_UART_MODE = 2;
    static final int AIR_HCI_MODE = 3;

    static final String SPI_XML_TAG = "UART";
    static final String UART_XML_TAG = "SPI";
    static final String AIR_UART_XML_TAG = "AIR_UART_CMD";
    static final String AIR_HCI_XML_TAG = "AIR_HCI_CMD";

    static String[] testModeArray = {"SPI mode","UART mode", "AIR UART CMD mode", "AIR HCI CMD mode"};

    //GATT action
    final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
    final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
    final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";

    //GATT error code
    static final int GATT_CONN_TERMINATE_LOCAL_HOST = 0x16;
    static final int GATT_CONN_TIMEOUT = 0x08;
    static final int GATT_INTERNAL_ERROR = 0x81;//129

    //broad cast
    static final String EXTRAS_ADDR = "EXTRAS_ADDR";
}
