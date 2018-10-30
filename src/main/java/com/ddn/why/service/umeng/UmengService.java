package com.ddn.why.service.umeng;
import java.util.*;
import com.ddn.why.utils.umeng.AndroidBroadcast;
import com.ddn.why.utils.umeng.AndroidUnicast;

public interface UmengService {

	/**
	 * 友盟广播--向安装该App的所有设备发送消息。
	 * @return
	 */
	boolean uMengBroadcastPush(AndroidBroadcast broadcast , String content , String uuid);

	boolean alertuMengListcastPush(String content, String cityId, int minute);

	boolean saveCurrentToken( String currentToken);


	/**
	 * 分页推送当前设备打开应用minute分钟前到现在这段时间内，除当前设备外的其他设备
	 * @param unicast 列播
	 * @param content 弹幕内容
	 * @param uuid 弹幕id
	 * @param currentToken 当前设备token
	 * @param minute 分钟
	 * @return
	 */
	boolean controluMengListcastPush(AndroidUnicast unicast,String content,String uuid,String currentToken,int minute);
	
}
