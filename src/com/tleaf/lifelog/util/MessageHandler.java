package com.tleaf.lifelog.util;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;

/**
 * 2014.08.19
 * @author jangyoungjin
 * 서비스컴포넌트와 통신을 하게해주는 핸들러입니다.
 */
public class MessageHandler extends Handler {
	private static final String TAG = "컴포넌트간메시지핸들러";
	/* 서비스컴포넌트 & 컴포넌트 메시지 통신 규약 */
	public static final int MSG_SAVE_DOCUMENT= 001;
	public static final int MSG_GET_DOCUMENT = 002;
	public static final int MSG_SEND_SUCCESS = 201;
	
	private MessageListener messageListener;

	private String reciper; //메시지를 받는곳 
	private Messenger messengerFrom; //메시지를 보낸곳

	public MessageHandler(String reciper, MessageListener messageListener) {
		this.reciper = reciper;
		this.messageListener = messageListener;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Messenger messenger = msg.replyTo;
		
		if (reciper.equals("service")) {
			Mylog.i(TAG, "컴포넌트에서 서비스로 보낸 메시지 처리 ");		
			fragmentToService(msg, messenger);
		} else if (reciper.equals("componet")) {
			Mylog.i(TAG, "서비스에서 컴포넌트로 보낸 메시지 처리");		
			serviceToFragment(msg, messenger);
		}else{
			super.handleMessage(msg);
		}
	}

	/* 컴포넌트에서 서비스로 보낸 메시지를 처리합니다.  */
	private void fragmentToService(Message message, Messenger messengerFrom) {
		switch (message.what) {
        case MSG_SAVE_DOCUMENT:
			Mylog.i(TAG, "도큐먼트를 저장합니다.");
			messageListener.onMessage(message.obj, messengerFrom);
			break;
        case MSG_GET_DOCUMENT:
			Mylog.i(TAG, "도큐먼트를 저장합니다.");
			messageListener.onMessage(message.obj, messengerFrom);
				
            break;
		}
	}
	
	/* 서비스에서 컴포넌트로 보낸 메시지를 처리합니다.  */
	private void serviceToFragment(Message message, Messenger messengerFrom) {
		switch (message.what) {
        case MSG_SEND_SUCCESS:
			Mylog.i(TAG, "도큐먼트 저장 성공을 알립니다. ");
			messageListener.onMessage(message.obj, messengerFrom);
            break;
		}
	
	}
}
