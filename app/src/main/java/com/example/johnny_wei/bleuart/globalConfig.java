package com.example.johnny_wei.bleuart;

public class globalConfig {


    public static final int SPI_MODE = 0;
    public static final int UART_MODE = 1;
    public static final int AIR_UART_MODE = 2;
    public static final int AIR_HCI_MODE = 3;

    public static final String SPI_XML_TAG = "UART";
    public static final String UART_XML_TAG = "SPI";
    public static final String AIR_UART_XML_TAG = "AIR_UART_CMD";
    public static final String AIR_HCI_XML_TAG = "AIR_HCI_CMD";

    public static String[] testModeArray = {"SPI mode","UART mode", "AIR UART CMD mode", "AIR HCI CMD mode"};
}
