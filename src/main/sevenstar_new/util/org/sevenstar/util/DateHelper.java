package org.sevenstar.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author rtm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class DateHelper {
	private static String year;

	private static String month;

	private static String day;

	private static String hour;

	private static String minute;

	private static String second;

	private static String millisecond;
	
	private static String weekday;



	/**
	 * 把日期转化成指定的日期格式 返回String
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null)
			return " ";
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 把日期转化成指定的日期格式 返回String
	 *
	 * @param date
	 * @return
	 * @throws
	 */
	public static Date stringToDate(String date,String format)  {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date stringToDateCommon(String date)  {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


	public static String dateToStringCommon(Date date) {
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	private static String[] weekday_gb = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

	private final static void setCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		year = String.valueOf(cal.get(Calendar.YEAR));
		month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) {
			day = "0" + day;
		}
		hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		minute = String.valueOf(cal.get(Calendar.MINUTE));
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		second = String.valueOf(cal.get(Calendar.SECOND));
		if (second.length() == 1) {
			second = "0" + second;
		}
		weekday =  weekday_gb[cal.get(Calendar.DAY_OF_WEEK) -1];
		millisecond = String.valueOf(cal.get(Calendar.MILLISECOND));
	}
	
	public final  static String getCurrentWeekDay(){
		setCurrentDate();
		return weekday;
	}

	public final  static String getCurrentYear() {
		setCurrentDate();
		return year;
	}

	public final  static String getCurrentMonDay(){
		setCurrentDate();
		return month+day;
	}
	public final  static String getCurrentYearMonDay(){
		setCurrentDate();
		return year.substring(2)+month+day;
	}
	
	public final  static String getCurrentTime(){
		setCurrentDate();
		return year + "-" + month + "-" + day + " "+hour+":"+minute+":"+second;
	}

	public  final  static String getLastYearMon(){
		setCurrentDate();
		if(Integer.parseInt(month) == 1){
			return getLastYear()+"12";
		}else{
			String lastMonth = String.valueOf(Integer.parseInt(month)-1);
			if(lastMonth.length() == 1){
				lastMonth = "0"+lastMonth;
			}
			return year+lastMonth;
		}
	}

	public final  static String getLastYear() {
		setCurrentDate();
		int lastYear = Integer.parseInt(year) - 1;
		return String.valueOf(lastYear);
	}


	public final  static String getCurrentMonth() {
		setCurrentDate();
		return month;
	}

	public final  static String getCurrentYearMon() {
		setCurrentDate();
		return year + month;
	}



	public final  static String getCurrentDate() {
		setCurrentDate();
		return year + "." + month + "." + day;
	}
	
	public final  static String getCurrentDateGBK() {
		setCurrentDate();
		return year + "年" + month + "月" + day + "日";
	}

	public static void main(String[] args) {
		//System.out.println(getCurrentYearMonDay());
		String str = "2007-06-16T11:03:09.000+08:00";
		 Date date = parseDate(str);
		 System.out.println(date);
	}

	public static Date parseDate(String datestr){
		if(datestr != null && datestr.length() >=19){
		//	datestr = datestr.substring(0,19);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.Sz");
			try {
				return df.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		throw new RuntimeException("日期格式不规范，必须为类似[2007-06-16T11:03:09.000+08:00]的格式");
	}
}
