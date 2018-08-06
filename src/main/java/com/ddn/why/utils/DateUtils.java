package com.ddn.why.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.junit.Test;

//获取当前时间
public class DateUtils {

	/**
	 * 获取当前时间的字符串 到秒
	 */
	public static String getDateYMDHMS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间的字符串 到天
	 * 
	 * @return
	 */
	public static String getDateYMD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
		return sdf.format(new Date());
	}

	/**
	 * 获取完整的今天的日期
	 * 
	 * @return
	 */
	public static String getDateYYMMDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取完整的今天的日期
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 获取明天的日期
	 */
	public static String getNextYMD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
		Calendar nextDate = Calendar.getInstance();
		nextDate.add(Calendar.DATE, 1);// 加一天
		return sdf.format(new Date());
	}

	/**
	 * 获取年份
	 */
	public static int getDateYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return Integer.parseInt(sdf.format(new Date()));
	}

	/**
	 * 获取月份
	 */
	public static int getDateMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return Integer.parseInt(sdf.format(new Date()));
	}

	/**
	 * 获取日期
	 */
	public static int getDateDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return Integer.parseInt(sdf.format(new Date()));
	}

	/**
	 * 获取秒
	 */
	public static String getDateSec() {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取小时
	 */
	public static int getDateHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		return Integer.parseInt(sdf.format(new Date()));
	}

	/**
	 * 获取到当前月份
	 */
	public static String getCurrentMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}

	/**
	 * 获取下个月的月份
	 */
	public static String getNextMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar nextDate = Calendar.getInstance();
		nextDate.add(Calendar.MONTH, 1);// 减一个月
		nextDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		return sdf.format(nextDate.getTime());
	}

	/**
	 * 输入的日期加1
	 */
	public static String getNextDay(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(datetime));
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	/**
	 * 毫秒数变成时间
	 * @param millsTime
	 * @return
	 */
	public static String getTimeByMillis(long millisTime) {
		try {
			Date date = new Date();
			date.setTime(millisTime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取24小时前的秒数
	 * @return
	 */
	public static Long getSecBetween24(){
		Long Sec = (System.currentTimeMillis()-24*60*60*1000)/1000;
		return Sec;
	}
	
	/**
	 * 获取N天前的秒数
	 * @return
	 */
	public static Long getSecBeforDay(int day){
		Long Sec = (System.currentTimeMillis()-day*60*60*1000)/1000;
		return Sec;
	}
	
	/**
	 * 获取当前的秒数
	 * @return
	 */
	public static Long getSecNow(){
		Long Sec = System.currentTimeMillis()/1000;
		return Sec;
	}
	
	/**
	 * 获取day天之前的时间
	 * @return
	 */
	public static String getDateBefore(int day){
		Long currentTIme = System.currentTimeMillis();
		Long dayBefore = currentTIme-day*24*60*60*1000;
		return getTimeByMillis(dayBefore);
	}
	
	/**
	 * 比较参数时间和现在时间的大小
	 * @param date
	 * @return -1：小于当前时间   1：大于当前时间 
	 */
	public static Integer dateComparator(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer result = 0;
		try {
			Date date1 = sdf.parse(date);
			Date nowDate = new Date();
			//System.out.println(date+"----"+nowDate);
			result = date1.compareTo(nowDate);
			System.out.println(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	/**
	 * 时间字符串转换成时间戳
	 * @param dateStr
	 * @return
	 */
	public static String dateStr2Millis(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Long millis = null;
		try {
			millis = sdf.parse(dateStr).getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return millis.toString();
	}
	
	/**
	 * 判断时间是否在当前时间的min前之内
	 * @param date
	 * @param min
	 * @return
	 */
	public static Boolean dateAmongMin(String date,Integer min){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Long dateMillisLong = sdf.parse(date).getTime();
			Long currentMillis = System.currentTimeMillis();
			Long milli = min*60L*1000;
			//System.out.println(currentMillis +"----"+dateMillisLong+"----*"+(currentMillis - dateMillisLong));
			if(currentMillis - dateMillisLong >milli){
				return false;
			}else {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断时间是否在当前时间的day天前之内
	 * @param date
	 * @param day
	 * @return
	 */
	public static Boolean dateAmongDay(String date,Integer day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Long dateMillisLong = sdf.parse(date).getTime();
			Long currentMillis = System.currentTimeMillis();
			Long milli = day*24L*60*60*1000;
			//System.out.println(currentMillis +"----"+dateMillisLong+"----*"+(currentMillis - dateMillisLong)+"---"+milli);
			if(currentMillis - dateMillisLong >milli){
				return false;
			}else {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 字符串转时间
	 * @param time
	 * @return
	 */
	
	public static Date stringToDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	
	/**
	 * 判断时间是否在某个时间点内
	 * @param date 时间
	 * @param strDateBegin 开始时间
	 * @param strDateEnd 结束时间
	 * @return
	 */
	public static boolean isInDate(Date date, String strDateBegin,
	        String strDateEnd) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate = sdf.format(date);
	    // 截取当前时间时分秒
	    int strDateH = Integer.parseInt(strDate.substring(11, 13));
	    int strDateM = Integer.parseInt(strDate.substring(14, 16));
	    int strDateS = Integer.parseInt(strDate.substring(17, 19));
	    // 截取开始时间时分秒
	    int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
	    int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
	    int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
	    // 截取结束时间时分秒
	    int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
	    int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
	    int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
	    if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
	        // 当前时间小时数在开始时间和结束时间小时数之间
	        if (strDateH > strDateBeginH && strDateH < strDateEndH) {
	            return true;
	            // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
	        } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
	                && strDateM <= strDateEndM) {
	            return true;
	            // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
	        } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
	                && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
	            return true;
	        }
	        // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
	        else if (strDateH >= strDateBeginH && strDateH == strDateEndH
	                && strDateM <= strDateEndM) {
	            return true;
	            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
	        } else if (strDateH >= strDateBeginH && strDateH == strDateEndH
	                && strDateM == strDateEndM && strDateS <= strDateEndS) {
	            return true;
	        } else {
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
	
	/**
	 * 判断日期是不是今天
	 * @param date
	 * @return
	 */
	public static boolean isToday(String date){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 try {
			Date sourceDate = sdf.parse(date);
			//创建日历
			Calendar calendar = Calendar.getInstance();
			//设置时间
			calendar.setTime(sourceDate);
			//获取日期
			int sourceDay =  calendar.get(Calendar.DAY_OF_MONTH);
			//System.out.println(sourceDay);
			//判断是否是今天
			if(sourceDay == getDateDay()){
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
//	@Test
	public void test() {
//		System.out.println(getSecBetween24());
//		
//		System.out.println(System.currentTimeMillis()-24*60*60*1000);
//		System.out.println((System.currentTimeMillis()-24*60*60*1000)/1000+"----------");
//		System.out.println(getTimeByMillis(1502261016000L));
//		System.out.println(getSecNow()+"------当前的秒数");
//		System.out.println("两天前的时间是："+getDateBefore(2));
//		dateComparator("2017-11-2 00:27:57");
//		System.out.println(getDateSec());
		//System.out.println(dateAmongDay("2018-01-04",2));
		//判断是否是今天
		System.out.println(isToday("2018-01-28 11:05:00"));
		
		
	}

}
