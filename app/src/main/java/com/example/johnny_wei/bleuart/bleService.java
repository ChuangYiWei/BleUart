package com.example.johnny_wei.bleuart;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.johnny_wei.bleuart.util.commonutil;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class bleService extends Service {
    //ble
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private String mBluetoothDeviceAddress;

    private int mConnectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";

    public final static String ACTION_GATT_CHARA_DATA_READ = "ACTION_GATT_CHARA_DATA_READ";
    public final static String ACTION_GATT_CHARA_DATA_WRITE = "ACTION_GATT_CHARA_DATA_WRITE";
    public final static String ACTION_GATT_CHARA_DATA_CHANGE = "ACTION_GATT_CHARA_DATA_CHANGE";

    public final static String ACTION_GATT_DESC_DATA_READ = "ACTION_GATT_DESC_DATA_READ";
    public final static String ACTION_GATT_DESC_DATA_WRITE = "ACTION_GATT_DESC_DATA_WRITE";

    //UUID
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    //service usage
    private final IBinder mBinder = new MyBinder();
    String TAG = this.getClass().getSimpleName();


    String g_charChagedstr;
    String g_charReadstr;

    //save time for comparing
    long changeCbUnixTime;

    public bleService() {
    }

    public class MyBinder extends Binder {
        public bleService getService() {
            return bleService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean initialize() {
        Log.d(TAG, "initialize BluetoothManager.");
        // For API level 18 and above, get a reference to BluetoothAdapter through
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "destroy service.");
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }

        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        super.onDestroy();
    }

    public boolean connect(final String address) {
        commonutil.wdbgLogcat(TAG,0,"=>connect to " + address);

        if (mBluetoothAdapter == null || address == null) {
            commonutil.wdbgLogcat(TAG,2,"BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        // Previously connected device. Try to reconnect.
        if (mBluetoothDeviceAddress != null
                && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            commonutil.wdbgLogcat(TAG,0,"Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                commonutil.wdbgLogcat(TAG,2,"mBluetoothGatt.connect() fail");
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        if (device == null) {
            commonutil.wdbgLogcat(TAG,2,"there is no device");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);

        //can't fix gatt error
        //mBluetoothGatt = (new BleConnectionCompat(this)).connectGatt(device, false, mGattCallback);

        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;

        commonutil.wdbgLogcat(TAG,0,"Trying to create a new connection.");
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The
     * disconnection result is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        commonutil.wdbgLogcat(TAG,0,"=>disconnect");
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            commonutil.wdbgLogcat(TAG,1,"BluetoothAdapter not initialized");
            return;
        }
        resetConnectState();
    }

    public boolean refreshGatt()
    {
        try {
            Method localMethod = mBluetoothGatt.getClass().getMethod("refresh");
            if (localMethod != null) {
                commonutil.wdbgLogcat(TAG,1, "refresh gatt");
                return (Boolean) localMethod.invoke(mBluetoothGatt);
            }
        } catch (Exception localException) {
            commonutil.wdbgLogcat(TAG,2, "An exception occured while refreshing device");
        }
        return false;
    }

    public boolean isConnected() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            commonutil.wdbgLogcat(TAG,2, "BluetoothAdapter not initialized");
            return false;
        }
        if(mConnectionState != STATE_CONNECTED) {
            commonutil.wdbgLogcat(TAG,1, "ble not connected state :" + mConnectionState);
            return false;
        }
        return true;
    }

    public boolean BT_Enable() {
        if (mBluetoothAdapter == null) {
            commonutil.wdbgLogcat(TAG,2, "BluetoothAdapter not initialized");
            return false;
        }
        return true;
    }

    public void resetConnectState() {
        commonutil.wdbgLogcat(TAG, 0, "[Enter] resetConnectState");
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            commonutil.wdbgLogcat(TAG, 2, "BluetoothAdapter not initialized");
            return;
        }
        if(!refreshGatt()) {
            commonutil.wdbgLogcat(TAG, 2, "gatt refresh fail");
        }

        mBluetoothGatt.disconnect();
        mConnectionState = STATE_DISCONNECTED;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public boolean rebootBluetooth() {
        disconnect();
        if(!mBluetoothAdapter.disable()) {
            commonutil.wdbgLogcat(TAG, 2, "disable bluetooth fail");
        }
        SystemClock.sleep(2000);
        if(!mBluetoothAdapter.enable()) {
            commonutil.wdbgLogcat(TAG, 2, "enable bluetooth fail");
        }
        SystemClock.sleep(2000);
        return true;
    }

    static final int GATT_CONN_TERMINATE_LOCAL_HOST = 0x16;
    static final int GATT_CONN_TIMEOUT = 0x08;
    /*gatt callback*/
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    commonutil.wdbgLogcat(TAG, 1, "Connected to GATT server. Attempting to start service discovery");
                    mBluetoothGatt.discoverServices();
                    mConnectionState = STATE_CONNECTED;
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    mConnectionState = STATE_DISCONNECTED;
                    commonutil.wdbgLogcat(TAG, 1, "Disconnected from GATT server.");
                }
            } else if (status == GATT_CONN_TERMINATE_LOCAL_HOST) {
                //android may reconnect if BT is already disconnect
                commonutil.wdbgLogcat(TAG, 1, "GATT_CONN_TERMINATE_LOCAL_HOST, resetConnectState !");
                resetConnectState();
            } else if (status == GATT_CONN_TIMEOUT) {
                //android may reconnect if BT is already disconnect
                commonutil.wdbgLogcat(TAG, 1, "GATT_CONN_TIMEOUT!, resetConnectState !");
                resetConnectState();
            } else {
                commonutil.wdbgLogcat(TAG, 2, "onConnectionStateChange received: " + status);
                commonutil.wdbgLogcat(TAG, 1, "resetConnectState !");
                resetConnectState();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            commonutil.wdbgLogcat(TAG, 1, "onServicesDiscovered.");
            if (BluetoothGatt.GATT_SUCCESS == status) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                commonutil.wdbgLogcat(TAG, 2, "onServicesDiscovered error status : " + status);
            }
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                String dataStr = "";
                byte[] allData = characteristic.getValue();
                for (byte data : allData) {
                    dataStr = dataStr + String.format("%02x", data);
                }
                setCharReadstr(dataStr);
                commonutil.wdbgLogcat(TAG, 1, "onCharacteristicRead uuid:" + characteristic.getUuid().toString() + ",data:" + dataStr);
                broadcastUpdate(ACTION_GATT_CHARA_DATA_READ, characteristic);
            } else {
                Log.e(TAG, "onCharacteristicRead fail");
                commonutil.wdbgLogcat(TAG, 2, "onCharacteristicRead fail!");
            }

            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                broadcastUpdate(ACTION_GATT_CHARA_DATA_WRITE);
            } else {
                Log.e(TAG, "onCharacteristicWrite fail");
            }
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            changeCbUnixTime = System.currentTimeMillis();
            Log.d(TAG, "onCharacteristicChanged uuid:" + characteristic.getUuid().toString() + "unix time:" + changeCbUnixTime);

            byte[] bytes = characteristic.getValue();
            if (bytes.length == 0) {
                Log.w(TAG, "byte len is 0");
                commonutil.wdbgLogcat(TAG, 1, "onCharacteristicChanged byte len is 0");
                return;
            }
            String dataStr = commonutil.bytes2String(bytes);
            //for uart test, concatenate received data, because the max data is 20 byte
            g_charChagedstr = g_charChagedstr + dataStr;

            commonutil.wdbgLogcat(TAG, 1, "Changed byte len:" + bytes.length + ",data: " + dataStr);

            broadcastUpdate(ACTION_GATT_CHARA_DATA_CHANGE, characteristic);
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                Log.w(TAG, "onDescriptorRead uuid." + descriptor.getUuid().toString());
                byte[] allData = descriptor.getValue();
                for (byte data : allData) {
                    Log.d(TAG, "data = " + String.format("0x%02x", data));
                }
                broadcastUpdate(ACTION_GATT_DESC_DATA_READ, descriptor);
            } else {
                Log.e(TAG, "onCharacteristicRead fail");
            }
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                broadcastUpdate(ACTION_GATT_DESC_DATA_WRITE);
            } else {
                Log.e(TAG, "onDescriptorWrite fail");
            }
            super.onDescriptorWrite(gatt, descriptor, status);
        }

    };

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic
     *            Characteristic to act on.
     * @param enabled
     *            If true, enable notification. False otherwise.
     */
    public void setCharacteristicNotification(
            BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter null");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID
                .fromString(CLIENT_CHARACTERISTIC_CONFIG));
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    public void setCharacteristicNotify(final String serviceUUID,final String characteristicUUID, final String descriptorUUID) {
        Log.d(TAG,"[Enter] setCharacteristicNotify");
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            commonutil.wdbgLogcat(TAG,1,"BluetoothAdapter null");
            return;
        }
        UUID service_uuid = UUID.fromString(serviceUUID);
        UUID chara_uuid = UUID.fromString(characteristicUUID);
        UUID desc_uuid = UUID.fromString(descriptorUUID);

        int retrymax = 3;
        int cnt = 0;
        BluetoothGattService service;
        do {
            service = mBluetoothGatt.getService(service_uuid);
            if(service == null) {
                commonutil.wdbgLogcat(TAG, 1, "service retry ");
            }
            SystemClock.sleep(500);
            cnt++;
        }
        while((service == null) && (cnt < retrymax) );

        if (service == null) {
            commonutil.wdbgLogcat(TAG, 2, "service is null");
            return;
        }

        BluetoothGattCharacteristic chara = service.getCharacteristic(chara_uuid);
        if (chara == null) {
            commonutil.wdbgLogcat(TAG, 2, "BluetoothGattCharacteristic is null");
            return;
        }

        if (!mBluetoothGatt.setCharacteristicNotification(chara, true)) {
            commonutil.wdbgLogcat(TAG, 2, "setCharacteristicNotification fail");
            return;
        }
        BluetoothGattDescriptor descriptor = chara.getDescriptor(desc_uuid);

        if (null != descriptor) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            if (!mBluetoothGatt.writeDescriptor(descriptor)) {
                commonutil.wdbgLogcat(TAG, 2, "writeDescriptor fail");
            }
        } else {
            commonutil.wdbgLogcat(TAG, 2, "null descriptor");
        }
    }


    /**
     * Retrieves a list of supported GATT services on the connected device. This
     * should be invoked only after {@code BluetoothGatt#discoverServices()}
     * completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;

        return mBluetoothGatt.getServices();
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read
     * result is reported asynchronously through the
     * {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic
     *            The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        Log.d(TAG, "readCharacteristic: " + characteristic.getProperties());
        commonutil.wdbgLogcat(TAG, 0, "readCharacteristic uuid:" + characteristic.getUuid().toString());
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.d(TAG, "BluetoothAdapter null");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }


    public void readCharacteristic2(final String serviceUUID, final String characteristicUUID) {
        commonutil.wdbgLogcat(TAG, 0, "readCharacteristic2 uuid:" + characteristicUUID);
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.d(TAG, "BluetoothAdapter null");
            return;
        }
        UUID service_uuid = UUID.fromString(serviceUUID);
        UUID chara_uuid = UUID.fromString(characteristicUUID);
        BluetoothGattService service = mBluetoothGatt.getService(service_uuid);

        if (service != null) {
            BluetoothGattCharacteristic chara = service.getCharacteristic(chara_uuid);
            if (chara != null) {
                mBluetoothGatt.readCharacteristic(chara);
            } else {
                commonutil.wdbgLogcat(TAG, 2, "chara is null");
            }
        } else {
            commonutil.wdbgLogcat(TAG, 2, "service is null");
        }
    }

    public void writeCharaval(final String serviceUUID, final String characteristicUUID, byte[] bytes) {
        if (mBluetoothGatt == null) return;
        UUID service_uuid = UUID.fromString(serviceUUID);
        UUID chara_uuid = UUID.fromString(characteristicUUID);
        BluetoothGattService service = mBluetoothGatt.getService(service_uuid);
        if (service == null) { Log.e(TAG, "service null"); return;}

        BluetoothGattCharacteristic chara = service.getCharacteristic(chara_uuid);
        if (chara == null) { commonutil.wdbgLogcat(TAG, 2, "BluetoothGattCharacteristic is null"); return; }

        chara.setValue(bytes);

        int retrymax = 3;
        int cnt = 0;
        boolean writeflag;
        do {
            writeflag = mBluetoothGatt.writeCharacteristic(chara);
            if (!writeflag) {
                commonutil.wdbgLogcat(TAG, 1, "write chara not success, retry");
            } else {
                break;
            }
            SystemClock.sleep(500);
            cnt++;
        }
        while ((writeflag == false) && (cnt < retrymax));

        if (!writeflag) {
            commonutil.wdbgLogcat(TAG, 2, "write characteristic fail");
        }

    }

    public void wirteCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);

    }


    //uart test
    public String getCharChagedstr() {
        return g_charChagedstr;
    }

    //uart test
    public void clearCharChagedstr() {
        g_charChagedstr = "";
    }

    public String getCharReadstr() {
        return g_charReadstr;
    }

    public void clearCharReadstr() {
        g_charReadstr = "";
    }

    public void setCharReadstr(String charStr) {
        g_charReadstr = charStr;
    }

    /**
     * Read the RSSI for a connected remote device.
     * */
    public boolean getRssiVal() {
        if (mBluetoothGatt == null) {
            return false;
        }
        return mBluetoothGatt.readRemoteRssi();
    }

    public Long GetchangeCbUnixTime() {
        if (mBluetoothGatt == null) {
            Long tmp = 0L;
            return tmp;
        }
        return changeCbUnixTime;
    }

    public void clearCbUnixTime() {
         changeCbUnixTime = 0;
    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {

    }
    private void broadcastUpdate(final String action,
                                 final BluetoothGattDescriptor descriptor) {

    }

}
