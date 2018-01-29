package com.lt.permission.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期时间工具类
 * 
 * @author 王昆
 * 
 */
public class DateUtil {

	private static final Log LOG = LogFactory.getLog(DateUtil.class);

	/**
	 * 获取某日期经过多少毫秒后的日期
	 * 
	 * @param date
	 *            日期
	 * @param dx
	 *            毫秒数
	 * @return
	 */
	public static Date nextDateAfterMs(Date date, int dx) {
		long time = date.getTime();
		Date newDate = new Date(time + dx);

		return newDate;
	}

	/**
	 * 获取某一日期后经过多少天后的新日期
	 * 
	 * @param date
	 *            日期
	 * @param dx
	 *            经过的时间数量
	 * @return
	 */
	public static java.sql.Date nextDateAfterDays(java.sql.Date startDate,
			int dx) {
		Calendar c = new GregorianCalendar();
		c.setTime(startDate);
		c.add(Calendar.DAY_OF_MONTH, dx);
		java.sql.Date nextDate = new java.sql.Date(c.getTimeInMillis());

		return nextDate;
	}

	/**
	 * 获取某一日期后经过多少月后的新日期（规则：比如起始日期为1-20，新日期为2-19；如起始日期为1-30，新日期为2-28或2-29，
	 * 为该月最后一天）
	 * 
	 * @param date
	 *            日期
	 * @param dx
	 *            经过的时间数量
	 * @return
	 */
	public static java.sql.Date nextDateAfterMonths(java.sql.Date startDate,
			int dx) {
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(startDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(startDate);
		endCalendar.add(Calendar.MONTH, dx);

		java.sql.Date nextDate = new java.sql.Date(
				endCalendar.getTimeInMillis());

		return nextDate;
	}

	/**
	 * 比较两个日期大小，date1较大时返回1，date2较大时返回-1，相等时返回0
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare(Date date1, Date date2) {
		long t1 = date1.getTime();
		long t2 = date2.getTime();
		if (t1 > t2) {
			return 1;
		} else {
			if (t1 < t2) {
				return -1;
			}
		}

		return 0;
	}

	/**
	 * 将日期转成日期yyyy-MM-dd 00:00:00
	 * 
	 * @param
	 * @return
	 */

	public static Date transform(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String d = df.format(date);
		Date datetime = null;
		Calendar c = new GregorianCalendar();
		String[] arr = d.split("\\s+");
		String[] ymdArr = arr[0].split("-");
		// String[] hmsArr = arr[1].split(":");
		c.set(Calendar.YEAR, Integer.parseInt(ymdArr[0]));
		c.set(Calendar.MONTH, Integer.parseInt(ymdArr[1]) - 1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ymdArr[2]));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		datetime = new Date(c.getTimeInMillis());

		return datetime;
	}

	public static Date transMax(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		String[] ymdArr = str.split("-");
		Date datetime = null;
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, Integer.parseInt(ymdArr[0]));
		c.set(Calendar.MONTH, Integer.parseInt(ymdArr[1]) - 1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ymdArr[2]));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		datetime = new Date(c.getTimeInMillis());

		return datetime;
	}

	/**
	 * 将字符串转成日期
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static java.sql.Date strToDate(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		String[] ymdArr = str.split("-");
		Calendar c = new GregorianCalendar(Integer.parseInt(ymdArr[0]),
				Integer.parseInt(ymdArr[1]) - 1, Integer.parseInt(ymdArr[2]));
		return new java.sql.Date(c.getTimeInMillis());
	}

	/**
	 * 将字符串转成日期时间
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Date strToDatetime(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		String[] arr = str.split("\\s+");
		String[] ymdArr = arr[0].split("-");
		String[] hmsArr = arr[1].split(":");
		Calendar c = new GregorianCalendar(Integer.parseInt(ymdArr[0]),
				Integer.parseInt(ymdArr[1]) - 1, Integer.parseInt(ymdArr[2]),
				Integer.parseInt(hmsArr[0]), Integer.parseInt(hmsArr[1]), 0);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 将一个日期转换为时间字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDate(Date d) {
		if (d == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String formatDates(Date d) {
		if (d == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 将一个日期转换为时间字符串
	 * 
	 * @param d
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatDate(Date d, String format) {
		if (d == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 两个日期相减间隔多少天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static int dateDiff(final Date startDate, final Date endDate)
			throws Exception {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(startDate);
		calendar2.setTime(endDate);

		// / 1000 * 60 * 60 * 24 = 86400000
		long increaseDate = (calendar2.getTimeInMillis() - calendar1
				.getTimeInMillis()) / 86400000;
		if (((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) % 86400000) > 0) {
			increaseDate = increaseDate + 1;
		}
		return (int) increaseDate;
	}

	/**
	 * 当前日期间隔多少天候的日期
	 * 
	 * @param interval
	 * @return
	 */
	public static Date getDateUseInterval(int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, interval);

		return calendar.getTime();
	}

	/**
	 * 某一个月间隔多少月后的日期
	 * 
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getDateIntervalMonth(Date date, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, interval);

		return calendar.getTime();
	}

	/**
	 * 某一日期间隔多少天后的日期
	 * 
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getDateUseIntervalBase(Date date, int interval) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, interval);

		return calendar.getTime();
	}

	/**
	 * 将字符串日期转换为Date
	 * 
	 * @param s
	 * @return
	 */
	public static Date convertToDate(String s) {
		DateFormat df;
		if (s == null) {
			return null;
		}

		if (s.contains(":")) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return df.parse(s);
			} catch (Exception e) {
				LOG.error(e);
			}
		} else {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(s);
			} catch (Exception e) {

			}
		}

		return null;
	}

	/**
	 * 日期加减月份
	 * 
	 * @param inputDate
	 * @param dx
	 * @return
	 */
	public static Date addMonth(Date inputDate, int dx) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		cal.add(Calendar.MONTH, dx);
		return cal.getTime();
	}

	public static void main(String[] args) {
		System.out.println("111");
		Date date1 = DateUtil.convertToDate("2014-08-04 0:00:00");
		Date date2 = DateUtil.convertToDate("2014-08-05 23:59:59");
		System.out.println("date1:" + date1.getTime() + ", date2:"
				+ date2.getTime());
		System.out.println(DateUtil.compare(date1, date2));
	}

}
