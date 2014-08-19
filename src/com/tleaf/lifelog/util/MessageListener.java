package com.tleaf.lifelog.util;

import android.os.Messenger;

public interface MessageListener {
	public void onMessage(Object data, Messenger messengerFrom);
}
