package com.tleaf.lifelog.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.tleaf.lifelog.model.Document;
import com.tleaf.lifelog.util.MessageHandler;
import com.tleaf.lifelog.util.MessageListener;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.database.CouchDBLiteTask;

public class DataAccessService extends Service implements MessageListener{
	public static final String INTENT_ACTION = "com.tleaf.service.uploader";
	private static final String TAG = "데이터접근서비스";
	private CouchDBLiteTask connector;
	
	private Messenger serviceMessenger;
	private MessageHandler mHandler;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "데이터베이스에 접근하는 서비스 컴포넌트가 실행되었습니다.");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//받은 메시지를 처리할 핸들러
		mHandler = new MessageHandler("service", this);
		serviceMessenger = new Messenger(mHandler);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Mylog.i(TAG, "데이터베이스에 접근하는 서비스 컴포넌트가 죽었습니다.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return serviceMessenger.getBinder(); //이부분은 추가했는데 이유를 모름...
	}

	/* 메시지가 들어왔을때 불리는 콜백 함수입니다. */
	@Override
	public void onMessage(Object data, Messenger messengerFrom) {
		// TODO Auto-generated method stub
		Document document = (Document) data;
		Mylog.i(TAG, "도큐먼트 : " + document.getType());
		
		//데이터 저장이 완료되고 이 메시지를 보낸곳으로 메시지를 보낸다.
		sendObjMessageToCaller(MessageHandler.MSG_SEND_SUCCESS, "true", messengerFrom);
	}
	
    /* 오브젝트 메시지를 액티비티에 보낸다. */
    private void sendObjMessageToCaller(int msgType, Object obj, Messenger messengerFrom) {
        try {
            Mylog.i(TAG, "");
            messengerFrom.send(Message.obtain(null, msgType, obj));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
	
}
