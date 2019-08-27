package com.example.a2019rednews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class bluetoothservice extends Service {
    boolean onoff;
    int num;
    int gas;
    final int handlerState = 0;
    Handler bluetoothIn; //핸들러 사용
    private BluetoothAdapter btAdapter = null;
    private ConnectingThread mConnectingThread;
    private ConnectedThread mConnectedThread;
    private boolean stopThread;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MAC_ADDRESS = "98:D3:91:FD:67:D9"; // 아두이노 맥 주소

    private StringBuilder recDataString = new StringBuilder();

    NotificationManager notificationManager;
    Notification Notify;

    //아두이노가 설치된 위치
    String location1 = "수정관 3층 340호";
    String location2 = "수정관 3층 323호";


    @Override
    public void onCreate() {
        super.onCreate();
        stopThread = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bluetoothIn.removeCallbacksAndMessages(null);
        stopThread = true;
        if (mConnectedThread != null) {
            mConnectedThread.closeStreams();
        }
        if (mConnectingThread != null) {
            mConnectingThread.closeSocket();
        }
    }

    IBinder mBinder = new bluetoothBinder();

    public class bluetoothBinder extends Binder {
        public bluetoothservice getService() {
            return bluetoothservice.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    recDataString.append(readMessage);
                    Log.d("RECORDED", recDataString.toString());
                    if (recDataString.toString().contains("x")) { // 가스1 > 가스2
                        gas = 1;
                    } else if (recDataString.toString().contains("y")) { // 가스1 < 가스2
                        gas = 2;
                    }
                    if (recDataString.toString().contains("N")) { // 불꽃1 on
                        onoff = true;
                        Alert(location1);
                    } else if (recDataString.toString().contains("F")) { // 불꽃1 off
                        onoff = false;
                    }
                    if (recDataString.toString().contains("M")) { //불꽃2 on
                        Alert(location2);
                    } else if (recDataString.toString().contains("G")) { // 불꽃2 off

                    }
                }
                recDataString.delete(0, recDataString.length());
            }
        };
        btAdapter = BluetoothAdapter.getDefaultAdapter(); //블루투스 연결
        checkBTState();
        return mBinder;
    }
    //불꽃이 감지되었는지 확인하는 함수
    public boolean isFireOn() {
        return onoff;
    }
    //가스 비교값을 리턴해주는 함수
    public int getGas() {
        return gas;
    }

    public int fireNum() {
        return num;
    }
    //푸쉬알림
    public void Alert(String location) {
        //푸시알림을 보내기 위해 시스템에 권한을 요청하여 생성
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //푸시알림 터치시 실행할 작업 설정
        Intent intent1 = new Intent(bluetoothservice.this.getApplicationContext(), MainActivity.class);
        //푸시알림을 터치하여 실행할 작업에 대한 플래그 설정 ( 현재 액티비티를 최상단으로 올린다 | 최상단 액티비티를 제외하고 모든 액티비티를 제거한다)
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //앞서 생성한 작업 내용을 Notification 객체에 담기 위한 PendingIntent 객체 생성
        //pendingintent는 Notification으로 작업을 수행할 때 인텐트가 실행되도록 합니다.

        // Notification은 안드로이드 시스템의 NotificationManager가 Intent를 실행합니다.
        // 즉 다른 프로세스에서 수행하기 때문에 Notification으로 Intent수행시 PendingIntent의 사용이 필수 입니다.

        PendingIntent pendingNotificationIntent =
                PendingIntent.getActivity(bluetoothservice.this, 0,
                        intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        //푸시 알림에 대한 각종 설정
        Notify = new Notification.Builder(getApplicationContext())  //notification 객체 생성
                .setSmallIcon(R.drawable.building)
                .setTicker("Red News")
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentTitle("화재가 감지되었습니다.")
                .setContentText(location + "에서 화재가 발생하였습니다. 대피로를 확인하여 신속하게 대피 바랍니다. ")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();
        //NotificationManager를 이용하여 푸시 알림 보내기
        notificationManager.notify(1, Notify);
    }

    private void checkBTState() {
        if (btAdapter == null) {
            stopSelf();
        } else {
            if (btAdapter.isEnabled()) {
                try {
                    BluetoothDevice device = btAdapter.getRemoteDevice(MAC_ADDRESS);
                    mConnectingThread = new ConnectingThread(device);
                    mConnectingThread.start();
                } catch (IllegalArgumentException e) {
                    stopSelf();
                }
            } else {
                stopSelf();
            }
        }
    }

    private class ConnectingThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectingThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket temp = null;
            try {
                temp = mmDevice.createRfcommSocketToServiceRecord(BTMODULEUUID);
            } catch (IOException e) {
                stopSelf();
            }
            mmSocket = temp;
        }

        @Override
        public void run() {
            super.run();
            btAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
            } catch (IOException e) {
                try {
                    mmSocket.close();
                    stopSelf();
                } catch (IOException e2) {
                    stopSelf();
                }
            } catch (IllegalStateException e) {
                stopSelf();
            }
        }

        public void closeSocket() {
            try {
                mmSocket.close();
            } catch (IOException e2) {
                stopSelf();
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //연결을 위해 I/O 스트림을 생성
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                stopSelf();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;
            //메세지를 받음
            while (true && !stopThread) {
                try {
                    bytes = mmInStream.read(buffer); //input 버퍼에서 부터 한 바이트씩 읽음
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    stopSelf();
                    break;
                }
            }
        }

        public void closeStreams() {
            try {
                mmInStream.close();
                mmOutStream.close();
            } catch (IOException e2) {
                stopSelf();
            }
        }
    }
}