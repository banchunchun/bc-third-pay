package com.bc.third.pay.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils extends PropertyEditorSupport {

  public static final String yyyy_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  public static final String yyyy_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

  public static final String DATE_FORMAT = "yyyy-MM-dd";

  public static final String DATE_MONTH_FORMAT = "yyyy-MM";

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


  public static final String DATE_DAY_FORMAT = "yyyyMMdd";

  public static final String DATE_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String HOUR_MINUTE = "HH:mm";

  private String dateFormat = "yyyy-MM-dd";

  public static final String MM_DD = "MM月dd日";
    public static final String DATEyyyyPMMPdd = "yyyy.MM.dd";

  private static final String FORMAT_TIME = "HHmmssS";
  
  public static final String FORMAT_MM = "MM";

  public static final String MM_dd_HH_mm = "MM-dd HH:mm";

  /**
   * 一个日期早于一个日期区间
   */
  public static final int BEFORE_START_DATE = -2;

  /**
   * 一个日期等于一个日期区间的开始日期
   */
  public static final int EQUAL_START_DATE = -1;

  /**
   * 一个日期在一个日期区间之内
   */
  public static final int BETWEEN_TOW_DATE = 0;

  /**
   * 一个日期等于一个日期区间的结束日期
   */
  public static final int EQUAL_END_DATE = 1;

  /**
   * 一个日期晚于一个日期区间
   */
  public static final int AFTER_END_DATE = 2;

  /**
   * 三个日期相等
   */
  public static final int TREE_DATE_EQUAL = 3;


  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    System.out.println("set Text");
    SimpleDateFormat frm = new SimpleDateFormat(dateFormat);
    try {
      Date date = frm.parse(text);
      this.setValue(date);
    } catch (Exception exp) {
      exp.printStackTrace();
    }

  }

  /**
   * 如果解析错误则返回null
   *
   * @param text
   * @return
   */
  public static Date parse(String text) {
    if (StringUtils.isBlank(text)) {
      return null;
    }
    text = text.trim();
    SimpleDateFormat frm = new SimpleDateFormat(DATE_FORMAT);
    try {
      return frm.parse(text);
    } catch (ParseException e) {
      return null;
    }
  }

  public static Date parse(String text, String format) {
    if (StringUtils.isBlank(text)) {
      return null;
    }
    SimpleDateFormat frm = new SimpleDateFormat(format);
    try {
      return frm.parse(text);
    } catch (ParseException e) {
      return null;
    }
  }

  public static String parse(Date date) {
    if (date == null) {
      return "";
    }
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    return format.format(date);
  }

  public static String parse(Date date, String format) {
    if (date == null) {
      return "";
    }
    SimpleDateFormat fmat = new SimpleDateFormat(format);
    return fmat.format(date);
  }

  /**
   * 把时间的时分秒设置为 23:59:59 999
   *
   * @param date
   * @return
   */
  public static Date endTimeOfDay(Date date) {
    if (date == null) {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 999);
    return cal.getTime();
  }

  /**
   * 把时间的时分秒设置为 0:0:0 0
   *
   * @param date
   * @return
   */
  public static Date beginTimeOfDay(Date date) {
    if (date == null) {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * 在当前日期的基础上加减天数
   *
   * @param date
   * @param day
   * @return
   */
  public static Date addDay(Date date, int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + day);
    return cal.getTime();
  }

  /**
   * 在当前日期的基础上减去多少天
   *
   * @param date
   * @param day
   * @return
   */
  public static Date detractDay(Date date, int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - day);
    return cal.getTime();
  }
  
  /**
   * 在当前日期的基础上减去多少分钟
   * @param date
   * @return
   */
  public static Date detractMinute(Date date, int minute) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - minute);
    return cal.getTime();
  }


  /**
   * 是否只包含日期部分，不包含时分秒部分
   *
   * @param input
   * @return
   */
  public static boolean isDate(String input) {
    if (StringUtils.isBlank(input)) {
      return false;
    }
    Matcher matcher = Pattern.compile(
        "^(\\d{4})-(0\\d{1}|[1-9]|1[0-2])-([1-9]|0\\d{1}|[12]\\d{1}|3[01])$")
        .matcher(input);
    return matcher.matches();
  }

  public static boolean isDateTime(String input, String formt) {
    SimpleDateFormat df = new SimpleDateFormat(formt);
    try {
      df.parse(input);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static int getHour(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  public static long getHourOfDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  public static long parseTimeInMillis(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.getTimeInMillis();
  }

  public static Date getDate(String date) {
    return getDate(date, yyyy_MM_DD_HH_MM_SS);
  }

  public static Date getDate(String date, String format) {
    SimpleDateFormat df = new SimpleDateFormat(format);
    try {
      return df.parse(date);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

  public static Date getDate(String format, Date date) {
     return getDate(getDate(date, format), format);
  }
  public static int getDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  public static String getDate(Date date, String format) {
    SimpleDateFormat df = new SimpleDateFormat(format);
    return df.format(date);
  }


  /**
   * 指定时间的凌晨
   *
   * @param date
   * @return
   */
  public static Date getDateZero(Date date) {
    date = org.apache.commons.lang.time.DateUtils.setHours(date, 0);
    date = org.apache.commons.lang.time.DateUtils.setMinutes(date, 0);
    date = org.apache.commons.lang.time.DateUtils.setSeconds(date, 0);
    date = org.apache.commons.lang.time.DateUtils.setMilliseconds(date, 0);
    return date;
  }

  public static Calendar getDateZeroCalendar(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    return cal;
  }

  public static Date addMonth(Date date, int i) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + i);
    return cal.getTime();
  }

  /**
   * 每周从星期一开始、星期天结束
   *
   * @param date
   * @return
   */
  public static int getDayOfWeek(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int day = cal.get(Calendar.DAY_OF_WEEK);
    if (day == 1)
      day = 7;
    else
      day--;
    return day;
  }

  public static int getWeekOfYear(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.WEEK_OF_YEAR);
  }

  public static int getWeekOfYearBeginOfMonday(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
    int day = cal.get(Calendar.DAY_OF_WEEK);
    if (day == 1) { // 星期天
      weekOfYear--;
    }
    return weekOfYear;
  }

  public static int getYear(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.YEAR);
  }

  public static int getMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.MONTH);
  }

  public static int getMinute(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.MINUTE);
  }

  /**
   * 月初第一天
   *
   * @param date
   * @return
   */
  public static Date beginOfMonth(Date date) {
    if (date == null)
      return date;
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
   *
   * @param n
   * @param weekDay
   * @return
   */
  public static String getWeekDay(int n, int weekDay) {
    Calendar cal = Calendar.getInstance();

    cal.add(Calendar.DATE, n * 7);
    // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
    cal.set(Calendar.DAY_OF_WEEK, weekDay);
    return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
  }

  public static Date valueOf(long timestamp) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timestamp);
    return calendar.getTime();
  }

  public static void recursionDay(Date begin, Date end, List<String> allDays) {
    if (begin.after(end)) {
      return;
    }

    Date b = DateUtils.beginTimeOfDay(begin);
    Date e = DateUtils.endTimeOfDay(begin);
    if (e.after(end)) {
      e = end;
    }

    allDays.add(DateUtils.parse(b));

    recursionDay(DateUtils.beginTimeOfDay(DateUtils.addDay(begin, 1)), end, allDays);

  }

  public static void recursionMonth(Date begin, Date end) {
    if (begin.after(end)) {
      return;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(begin);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

    Date e = cal.getTime();
    e = DateUtils.endTimeOfDay(e);
    if (e.after(end)) {
      e = end;
    }

    recursionMonth(DateUtils.beginTimeOfDay(DateUtils.addDay(e, 1)), end);
  }

  public static void recursionWeek(Date begin, Date end, List<Date> lastDayOfWeek) {
    if (begin.after(end)) {
      return;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(begin);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    dayOfWeek = dayOfWeek == 1 ? 7 : dayOfWeek - 1;
    Date b = DateUtils.beginTimeOfDay(begin);

    Date e;
    if (dayOfWeek == 7) {
      e = DateUtils.endTimeOfDay(begin);
    } else {
      e = DateUtils.addDay(b, 7 - dayOfWeek);
      e = DateUtils.endTimeOfDay(e);
    }

    if (e.after(end)) {
      e = end;
    }

    lastDayOfWeek.add(e);

    recursionWeek(DateUtils.beginTimeOfDay(DateUtils.addDay(e, 1)), end, lastDayOfWeek);

  }

  public static void recursionWeek(Date begin, Date end, Map<Date, Date> weekDates) {
    if (begin.after(end)) {
      return;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(begin);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    dayOfWeek = dayOfWeek == 1 ? 7 : dayOfWeek - 1;
    Date b = DateUtils.beginTimeOfDay(begin);

    Date e;
    if (dayOfWeek == 7) {
      e = begin;
    } else {
      e = DateUtils.addDay(b, 7 - dayOfWeek);
    }

    if (e.equals(end) || e.after(end)) {
      e = end;
    } else {
      e = DateUtils.addDay(e, 1);
    }

    weekDates.put(b, e);

    if (!e.equals(end)) {
      recursionWeek(e, end, weekDates);
    }

  }

  /**
   * 判断日期是否昨天 或者前天.....
   *
   * @param paramDate 要判断的时间
   * @param day       昨天传1 前天传2以此类推
   * @return
   * @throws ParseException
   */
  public static boolean judgeDay(Date paramDate, Integer day) throws ParseException {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String todayStr = format.format(date);
    // 得到今天零时零分零秒这一时刻
    Date today = format.parse(todayStr);
    // 与今日零时零分零秒比较
    if ((today.getTime() - paramDate.getTime()) > (day - 1) * 86400000
        && (today.getTime() - paramDate.getTime()) < day * 86400000) {
      return true;
    }
    return false;
  }

  /**
   * 判断日期是否今天.....
   *
   * @param paramDate 要判断的时间
   * @return
   * @throws ParseException
   */
  public static boolean judgeToday(Date paramDate) throws ParseException {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String todayStr = format.format(date);
    // 得到今天零时零分零秒这一时刻
    Date today = format.parse(todayStr);
    // 与今日零时零分零秒比较
    if ((paramDate.getTime() - today.getTime()) >= 0
        && (paramDate.getTime() - today.getTime()) < 86400000) {
      return true;
    }
    return false;
  }

  public static Date addMinute(Date date, int minutes) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minutes);
    return cal.getTime();
  }

  public static Date addSecond(Date date, int seconds) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + seconds);
    return cal.getTime();
  }

  public static Date addHour(Date date, int hours) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hours);
    return cal.getTime();
  }

  /**
   * 开始时间和结束时间间的间隔.
   *
   * @param start 开始时间
   * @param end   结束时间
   * @return 时间间隔 毫秒粒度
   */
  public static long timeIntervalInDay(Date start, Date end) {
    Date now = beginTimeOfDay(end);
    return Math.abs(now.getTime() - start.getTime());
  }

  /**
   * 将日期时间转换成字符格式
   *
   * @param date
   * @param newformat
   * @return
   */
  public static String format(Date date, String newformat) {
    if (date == null) {
      return null;
    }
    if (StringUtils.isBlank(newformat)) {
      newformat = yyyy_MM_DD_HH_MM_SS;
    }
    SimpleDateFormat formatt = new SimpleDateFormat(newformat);
    return formatt.format(date);
  }

  /**
   * 得到某个时区的date对象
   *
   * @param dateTimeStr 时间字符串
   * @param patten      时间格式字符串
   * @param timeZoneStr 时区字符串
   * @return
   */
  public static Date dateTimeOfTomeZone(String dateTimeStr, String patten, String timeZoneStr) {
    SimpleDateFormat format = new SimpleDateFormat(patten);
    format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, timeZoneStr)));
    Date date = null;
    try {
      date = format.parse(dateTimeStr);
    } catch (ParseException e) {
    }
    return date;
  }

  /**
   * 获取当前时间距离今天最后一毫秒秒的差值
   *
   * @return
   */
  public static long getDvalueNowToEnd() {
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    cal.add(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date tomorrow = cal.getTime();
    return tomorrow.getTime() - today.getTime();
  }

  @SuppressWarnings("deprecation")
  public static Date getTodayWithYMD() {
    Date todayDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(todayDate.getYear() + 1900, todayDate.getMonth(), todayDate.getDate(), 0, 0, 0);
    todayDate = calendar.getTime();
    return todayDate;
  }

  /**
   * 比较两个时间点
   * 如果secondDate表示的时间等于此 firstDate 表示的时间，则返回 0 值；
   * 如果此 firstDate 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值；
   * 如果此 firstDate 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
   *
   * @param firstDate
   * @param secondDate
   * @return
   */
  public static int compare(Date firstDate, Date secondDate) {
    Calendar firstCalendar = null;
    /**使用给定的 Date 设置此 Calendar 的时间。**/
    if (firstDate != null) {
      firstCalendar = Calendar.getInstance();
      firstCalendar.setTime(firstDate);
    }

    Calendar secondCalendar = null;
    /**使用给定的 Date 设置此 Calendar 的时间。**/
    if (firstDate != null) {
      secondCalendar = Calendar.getInstance();
      secondCalendar.setTime(secondDate);
    }

    try {
      /**
       * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。
       * 如果参数表示的时间等于此 Calendar 表示的时间，则返回 0 值；
       * 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值；
       * 如果此 Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
       * **/
      return firstCalendar.compareTo(secondCalendar);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * 判断<firstDate>时间点是否在<secondDate>时间点之前
   *
   * @param firstDate
   * @param secondDate
   * @return
   */
  public static boolean isBefore(Date firstDate, Date secondDate) {
    return compare(firstDate, secondDate) < 0 ? true : false;
  }

  /**
   * 判断<firstDate>时间点是否在<secondDate>时间点之后
   * 如果此 firstDate 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
   *
   * @param firstDate
   * @param secondDate
   * @return
   */
  public static boolean isAfter(Date firstDate, Date secondDate) {
    return compare(firstDate, secondDate) > 0 ? true : false;
  }

  public static boolean betweenTwoDateWithYYYYMMDD(Date startDate, Date endDate, Date inDate) {
    startDate = org.apache.commons.lang3.time.DateUtils.truncate(startDate, Calendar.DAY_OF_MONTH);
    endDate = org.apache.commons.lang3.time.DateUtils.truncate(endDate, Calendar.DAY_OF_MONTH);
    inDate = org.apache.commons.lang3.time.DateUtils.truncate(inDate, Calendar.DAY_OF_MONTH);
    if (endDate.before(startDate)) {
      throw new IllegalArgumentException("endDate can not before startDate.");
    }
    if (!inDate.before(startDate) && !inDate.after(endDate)) {
      return true;
    }
    return false;
  }

  /**
   * 比较inDate是否在开始时间和结束时间取件内
   *
   * @param startDate
   * @param endDate
   * @param inDate
   * @return
   */
  public static int betweenTowDate(Date startDate, Date endDate, Date inDate) {
    /**
     * 判断<endDate>时间点是否在<startDate>时间点之前
     * 如果为真表示<endDate>时间点在<startDate>时间点之前则抛出异常
     * 即结束时间在开始时间之前是不正常的
     */
    if (isBefore(endDate, startDate)) {
      throw new IllegalArgumentException("endDate can not before startDate!");
    }

    /**
     * 比较两个时间点<inDate>和<startDate>
     * 如果inDate表示的时间等于此 startDate 表示的时间，则返回 0 值；
     * 如果此 inDate 的时间在参数<startDate>表示的时间之前，则返回小于 0 的值；
     * 如果此 inDate 的时间在参数<startDate>表示的时间之后，则返回大于 0 的值
     */
    int sflag = compare(inDate, startDate);

    /**
     * 比较两个时间点<inDate>和<endDate>
     * 如果inDate表示的时间等于此 endDate 表示的时间，则返回 0 值；
     * 如果此 inDate 的时间在参数<endDate>表示的时间之前，则返回小于 0 的值；
     * 如果此 inDate 的时间在参数<endDate>表示的时间之后，则返回大于 0 的值
     */
    int eflag = compare(inDate, endDate);

    int flag = 0;
    /**如果此 inDate 的时间在参数<startDate>表示的时间之前，则返回小于 0 的值**/
    if (sflag < 0) {
      /**一个日期早于一个日期区间**/
      flag = DateUtils.BEFORE_START_DATE;
    } else if (sflag == 0) {
      /**如果inDate表示的时间等于此 startDate 表示的时间，则返回 0 值；**/
      /**如果inDate表示的时间等于此 endDate 表示的时间，则返回 0 值；**/
      if (eflag == 0) {
        /**三个日期相等**/
        flag = DateUtils.TREE_DATE_EQUAL;
      } else {
        /**一个日期等于一个日期区间的开始日期**/
        flag = DateUtils.EQUAL_START_DATE;
      }
    } else if (sflag > 0 && eflag < 0) {
      /**满足-inDate 的时间在参数<startDate>表示的时间之后，和 inDate 的时间在参数<endDate>表示的时间之前**/
      /**一个日期在一个日期区间之内**/
      flag = DateUtils.BETWEEN_TOW_DATE;
    } else if (eflag == 0) {
      /**如果inDate表示的时间等于此 endDate 表示的时间**/
      /**一个日期等于一个日期区间的结束日期**/
      flag = DateUtils.EQUAL_END_DATE;
    } else if (eflag > 0) {
      /**满足inDate 的时间在参数<endDate>表示的时间之后**/
      /**一个日期晚于一个日期区间**/
      flag = DateUtils.AFTER_END_DATE;
    }
    return flag;
  }

  /**
   * 得到两个日期相差的天数 （这样不是很精确）
   */
  public static int getBetweenDay(Date date1, Date date2) {
//		Calendar d1 = Calendar.getInstance();
//		d1.setTime(date1);
//		Calendar d2 = Calendar.getInstance();
//		d2.setTime(date2);
//		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
//		// System.out.println("days="+days);
//		int y2 = d2.get(Calendar.YEAR);
//		if (d1.get(Calendar.YEAR) != y2) {
//			// d1 = (Calendar) d1.clone();
//			do {
//				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
//				d1.add(Calendar.YEAR, 1);
//			} while (d1.get(Calendar.YEAR) != y2);
//		}
//		return days;
    Long days = Long.valueOf(0L);
    if (date1.getTime() > date2.getTime())
      days = Long.valueOf(date1.getTime() - date2.getTime());
    else
      days = Long.valueOf(date2.getTime() - date1.getTime());
    return Integer.valueOf((int) (days.longValue() / 60L / 60L / 1000L / 24L));
  }


  /**
   * 判断日期格式合法 符合yyyy-MM-dd格式
   */
  public static boolean isValidDate(String date_str) {
    boolean convertSuccess = true;
    // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      // 设置lenient为false.
      // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
      format.setLenient(false);
      format.parse(date_str);
    } catch (ParseException e) {
      // e.printStackTrace();
      // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
      convertSuccess = false;
    }
    return convertSuccess;
  }

  /**
   * 判断时间是否属于当年
   *
   * @param date
   * @return
   */
  public static Boolean isNowYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int nowYear = calendar.get(Calendar.YEAR);
    calendar.setTime(date);
    int getYear = calendar.get(Calendar.YEAR);
    if ((nowYear - getYear) > 0) {
      return false;
    }
    return true;
  }

  /**
   * 根据开始时间和失效分钟查询剩余时间秒
   *
   * @param startTime
   * @param minute
   * @return
   */
  public static Long invalidMsecCalc(Date startTime, int minute) {
    long now = System.currentTimeMillis();
    long start = startTime.getTime();
    long invalid = minute * 60 * 1000; // 失效时间限制
    long invalidEndTime = (start + invalid); // 失效的最后毫秒
    long surplusInvalid = invalidEndTime - now; // 剩余失效时间

    // 判断还有多少毫秒失效，失效的最后时间减去当前时间
    if (surplusInvalid <= 0) {
      return 0l;
    }
    return surplusInvalid / 1000;
  }

  /**
   * 根据当前时间加或减小时获取时间
   * <pre>
   * 例如：当前的时间：11:03:10
   * +3：3个小时后的时间：14:03:10
   * -3：3个小时前的时间：08:03:10
   *
   * </pre>
   *
   * @param hours
   * @return
   */
  @SuppressWarnings("deprecation")
  public static String getTimeByHoursCalc(Integer hours) {
    /*Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) + hours);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		
		return  df.format(calendar.getTime());*/

    Date time = new Date();
    int newhours = 0;
    String newminutes = "00";
    int calcTime = time.getHours();

    if (time.getMinutes() <= 30) {
      newminutes = "30";
    } else {
      calcTime = calcTime + 1;
    }
    // 加上需要更新的值
    newhours = calcTime + hours;

    if ((newhours + "").length() == 1) {
      return "0" + newhours + ":" + newminutes;
    }
    return newhours + ":" + newminutes;
  }

  /**
   * 根据当前时间加或减小时获取时间
   * <pre>
   * 例如：3
   * 时间：03:00:00
   *
   * </pre>
   *
   * @param hours
   * @return
   */
  public static String getTimeByaHours(Integer hours) {
    if (hours == null) {
      return null;
    }
    if (hours.intValue() < 0 || hours.intValue() > 24) {
      return null;
    }
    if (hours.toString().length() == 1) {
      return "0" + hours + ":00";
    }
    return hours + ":00";
  }

  /**
   * 格式化天
   * <pre>
   * 1天内 今天
   * 2天内 昨天
   * 3天内 前天
   * 今年内 x月x日
   * 年外 x年x月x日
   * </pre>
   *
   * @param time
   * @return
   * @author chenguolin
   */
  public static String formatDayDiff(String time) {
    Date cTime = getDate(time, yyyy_MM_DD_HH_MM_SS);
    Calendar cal = Calendar.getInstance();
    cal.setTime(cTime);

    if (getDays(format(new Date(), yyyy_MM_DD_HH_MM_SS), time) == 0) {
      return "今天";
    } else if (getDays(format(new Date(), yyyy_MM_DD_HH_MM_SS), time) == 1) {
      return "昨天";
    } else if (getDays(format(new Date(), yyyy_MM_DD_HH_MM_SS), time) == 2) {
      return "前天";
    } else if (Calendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
      return (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DATE) + "日";
    } else {
      return cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DATE) + "日";
    }
  }

  
  /**
   * <pre>
   *     苹果版本 V4.0.0
   *     Android版本 V4.0.0
   *
   *      当天24小时制显示，如：13：05
   *      过了当天显示日期，如：3月29日，
   *      过了年显示年月日，如：2016年12月31日
   * </pre>
   * @param time 时间 2017-03-21 16:01
   * @author bowen
   * @return 
   * @since 2017/3/21 下午4:01
   */
  public static String formatTime(String time) {
    if(StringUtils.isEmpty(time)){
      return null;
    }
    Date cTime = getDate(time, yyyy_MM_DD_HH_MM_SS);
    Calendar cal = Calendar.getInstance();
    cal.setTime(cTime);
    if (getDays(format(new Date(), yyyy_MM_DD_HH_MM_SS), time) == 0) {
      //return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
      return format(cTime,HOUR_MINUTE);
    } else if (Calendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
      return (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DATE) + "日";
    } else {
      return cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DATE) + "日";
    }
  }

  /**
   * 两个时间之间的天数
   *
   * @param date1
   * @param date2
   * @return
   */
  public static long getDays(String date1, String date2) {
    if (date1 == null || date1.equals(""))
      return 0;
    if (date2 == null || date2.equals(""))
      return 0;
    // 转换为标准时间
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    Date mydate = null;
    try {
      date = myFormatter.parse(date1);
      mydate = myFormatter.parse(date2);
    } catch (Exception e) {
    }
    long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    return day;
  }

  /**
   * 获取几天前的日期
   *
   * @param days
   * @return
   * @author chenguolin
   */
  public static String getBeforeDateByDays(Integer days) {
    Calendar now = Calendar.getInstance();
    now.setTime(new Date());
    now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    return myFormatter.format(now.getTime());
  }

  /**
   * 获取今天还剩下多少秒
   *
   * @return
   * @author chenguolin
   */
  public static long getRemainSeconds() {
    Calendar curDate = Calendar.getInstance();
    Calendar tommorowDate = new GregorianCalendar(curDate
        .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
        .get(Calendar.DATE) + 1, 0, 0, 0);
    return (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000l;
  }

  /**
   * 将date日期增加days天，
   *
   * @param date 2015-12-08 16:53:50
   * @param days 2
   * @return 2015-12-09 23:59:59
   */
  public static Date getDeadline(Date date, int days) {
    long deadLine = addDay(parse(parse(date)), days).getTime() - 1000l; //date 日期增加 days 减1s
    return valueOf(deadLine);
  }

	/**
	 * 毫秒转时间
	 * @author chenguolin
	 * @param getTime
	 * @return
	 */
	public static String parseFormatDateTimeByGetItme(String getTime){

		Date time = new Date(Long.parseLong(getTime));
		return format(time, null);
	}

	public static String parseFormatDateTimeByGetItme(long getTime,String format){

		Date time = new Date(getTime);
		return format(time, format);
	}

  /**
   * 毫秒转时间
   *
   * @param getTime
   * @return
   * @author chenguolin
   */
//  public static String parseFormatDateTimeByGetItme(String getTime) {
//
//    Date time = new Date(Long.parseLong(getTime));
//    return format(time, null);
//  }

  /**
   * HHmmss
   *
   * @return
   * @author chenguolin
   */
  public static String formatHHmmss() {
    SimpleDateFormat df = new SimpleDateFormat(FORMAT_TIME);
    return df.format(new Date());
  }

  /**
   * 格式化日期
   *
   * @param date 日期对象
   * @return String 日期字符串
   */
  public static String formatDate(Date date) {
    SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
    String sDate = f.format(date);
    return sDate;
  }

  /**
   * 获取某年第一天日期
   *
   * @param year 年份
   * @return Date
   */
  public static Date getCurrYearFirst(int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, year);
    Date currYearFirst = calendar.getTime();
    return currYearFirst;
  }

  /**
   * 获取某年最后一天日期
   *
   * @param year 年份
   * @return Date
   */
  public static Date getCurrYearLast(int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, year);
    calendar.roll(Calendar.DAY_OF_YEAR, -1);
    Date currYearLast = calendar.getTime();

    return currYearLast;
  }

  /**
   * 将指定格式字符串转换为Calendar
   * <pre>
   * </pre>
   *
   * @param dateStr
   * @return
   * @author chenguolin
   * @createtime 2015年12月25日 下午2:38:23
   */
  public static Calendar parseCalendar(String dateStr) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = dateFormat.parse(dateStr);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar;
    } catch (Exception e) {
      e.printStackTrace();
      return Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    }
  }

  public static Calendar parseCalendar(Date date) {
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar;
    } catch (Exception e) {
      e.printStackTrace();
      return Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    }
  }

  /**
   * 计算相差天数 now>ca
   * <pre>
   * </pre>
   *
   * @param ca
   * @param now
   * @return
   * @author chenguolin
   * @createtime 2015年12月25日 下午2:55:40
   */
  public static int getBetweenDays(Calendar ca, Calendar now) {
    long time1 = ca.getTimeInMillis();
    long time2 = now.getTimeInMillis();
    long between_days = (time2 - time1) / (1000 * 3600 * 24);

    return Integer.parseInt(String.valueOf(between_days));
  }

  /**
   * 获取当前日期是星期几<br>
   * @return 当前日期是星期几
   */
  public static String getWeekOfDate() {
    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
    if (w < 0)
      w = 0;
    return weekDays[w];
  }

  /**
   * 根据号数获取剩余秒
   * <pre>
   * </pre>
   *
   * @param beginDays 开始号数
   * @param endDays   结束号数
   * @return
   * @author chenguolin
   * @createtime 2015年12月29日 下午4:18:28
   */
  public static long getSurplusSecondByMonthDays(int beginDays, int endDays) {

    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    int day = c.get(Calendar.DAY_OF_MONTH);

    if (day < beginDays || day > endDays) {
      return 0;
    }
    if (day == endDays) {
      return DateUtils.getRemainSeconds();
    }

    long surplusNow = getRemainSeconds();
    long oneDays = 60 * 60 * 24;

    return surplusNow + ((endDays - day) * oneDays);
  }

  /**
   * 根据星期获取剩余秒
   * <pre>
   * </pre>
   *
   * @param weeks 周几
   * @return
   * @author chenguolin
   * @createtime 2015年12月29日 下午4:55:25
   */
  public static long getSurplusSecondByWeek(int weeks) {

    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    int week = c.get(Calendar.DAY_OF_WEEK) - 1;

    if (week != weeks) {
      return 0;
    }

    return DateUtils.getRemainSeconds();
  }

  /**
   * 日期校验
   *
   * @param date
   * @return 1:大于当前时间  0：小于当前时间
   */
  public static int ckDate(String date) {
    Date aa = DateUtils.getDate(date, DateUtils.DATE_FORMAT);
    Date bb = DateUtils.getDate(DateUtils.getDate(new Date(), DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    if (aa.getTime() >= bb.getTime()) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 日期校验
   *
   * @param date
   * @return 1:大于当前时间  0：小于当前时间
   */
  public static int ckDate(Date date) {
    Date aa = DateUtils.getDate(DateUtils.getDate(date, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    Date bb = DateUtils.getDate(DateUtils.getDate(new Date(), DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    if (aa.getTime() >= bb.getTime()) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 日期相差天数
   *
   * @param early
   * @param late
   * @return
   */
  public static final int daysBetween(Date early, Date late) {
    Calendar calst = Calendar.getInstance();
    Calendar caled = Calendar.getInstance();
    calst.setTime(early);
    caled.setTime(late);
    //设置时间为0时
    calst.set(Calendar.HOUR_OF_DAY, 0);
    calst.set(Calendar.MINUTE, 0);
    calst.set(Calendar.SECOND, 0);
    caled.set(Calendar.HOUR_OF_DAY, 0);
    caled.set(Calendar.MINUTE, 0);
    caled.set(Calendar.SECOND, 0);
    //得到两个日期相差的天数
    int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
        .getTime().getTime() / 1000)) / 3600 / 24;
    return days;
  }

  /**
   * 日期相差天数
   * @return
   */
  public static final int daysBetween(String earlyYYYYMMDD, String lateYYYYMMDD) {
    Date aa = DateUtils.getDate(earlyYYYYMMDD, DateUtils.DATE_FORMAT);
    Date bb = DateUtils.getDate(lateYYYYMMDD, DateUtils.DATE_FORMAT);
    return daysBetween(aa, bb);
  }

  /**
   * 日期相差天数-到今天
   * @param early
   * @return
   */
  public static final int daysBetween(Date early) {
    Date bb = DateUtils.getDate(DateUtils.getDate(new Date(), DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    return daysBetween(early, bb);
  }

  /**
   * 日期相差天数-到今天
   * @return
   */
  public static final int daysBetween(String earlyYYYYMMDD) {
    Date aa = DateUtils.getDate(earlyYYYYMMDD, DateUtils.DATE_FORMAT);
    Date bb = DateUtils.getDate(DateUtils.getDate(new Date(), DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    return daysBetween(aa, bb);
  }
  
  /**
   * 日期相差月份-到今天
   * @return
   */
  public static final int monthsBetween(String earlyYYYYMMDD) {
    Date aa = DateUtils.getDate(earlyYYYYMMDD, DateUtils.DATE_FORMAT);
    Date bb = DateUtils.getDate(DateUtils.getDate(new Date(), DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    int day = daysBetween(aa, bb);
    return day/30;
  }
  
  public static final String getMonthDesByMonth(int month){
		String str = "";
		if(month < 0){
			str = "孕期必备";
		}
		else if(month == 0){
			str = "新生儿必备";
		}
		else if(month < 12 && month > 0){
			str = "宝宝"+month+"个月必备";
		}else{
			int year = month/12;
			int tmpMonth = month - (year*12);
			if(tmpMonth <= 0){
				str = "宝宝"+year+"岁必备";
			}else{
				str = "宝宝"+year+"岁"+tmpMonth+"个月必备";
			}
		}
		return str;
	}
  
  /**
   *  根据月获取天
   * @param days
   * @return
   */
	public static final int getMonthByDays(int days) {
		int month = 0;
		if (days < 365) {
			month = days/30;
		} else if (days >= 365) {
			int year = days / 365;
			int daytmp = days - (year * 365);
			month = (year * 12) + (daytmp / 30);
		}
		return month;
	}
	
	/**
	 * 返回格式 “5月6日 12”点
	 * @author chenguolin
	 * @date 2016年6月3日
	 * @param time
	 * @param hours
	 * @return
	 */
	public static final String getRiSenHours(String time,int hours){
		Date date = DateUtils.addHour(DateUtils.getDate(time), hours);
		StringBuffer sb = new StringBuffer();
		sb.append(date.getMonth()+1).append("月").append(date.getDate()).append("日").append(date.getHours()+1);
		return sb.toString();
	}
	public static final String getTodayTimes(int hours){
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(date.getMonth()+1).append("月").append(date.getDate()).append("日").append(hours);
		return sb.toString();
	}
	public static final String getTomorrowTimes(int hours){
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(date.getMonth()+1).append("月").append(date.getDate()+1).append("日").append(hours);
		return sb.toString();
	}
	
	public static final String getFormatArriveTime(String time){
		if(StringUtils.isEmpty(time)){
			return "";
		}
		Date date = DateUtils.getDate(time);
		StringBuffer sb = new StringBuffer();
		sb.append(date.getMonth()+1).append("月").append(date.getDate()).append("日").append(date.getHours()+1).append("点");
		return sb.toString();
	}
	
	public static final String getTimeDisparity(Date date) {
		int DisparityDays = DateUtils.getBetweenDay(date, new Date());
		if (DisparityDays < 1) {
			return "今天";
		}
		if (DisparityDays >= 1 && DisparityDays < 2) {
			return "昨天";
		}
		if (DisparityDays >= 2 && DisparityDays < 3) {
			return "前天";
		}
//		boolean isNowYear = DateUtils.isNowYear(date);
//		if (isNowYear) {
//			return DateUtils.getDate(date, "MM-dd");
//		} else {
//			return DateUtils.getDate(date, "yyyy-MM-dd");
//		}

        //modify by gxl
        return DateUtils.getDate(date, "yyyy-MM-dd");

	}

	/**
	 * 
	 * @author zhoushu
	 * @date 2016年8月16日 上午10:42:23
	 * @version V1.0
	 * @Description: 需要判断前后的详细结果
	 * 未开始 1 进行中 0  已结束 -1
	 */
	public static int getBetweenTwoDateScopeValue(Date startDate, Date endDate, Date inDate) {
		if(startDate == null || endDate == null){
			return -1;
		}
		if (endDate.before(startDate)) {
	    	return -1;
	    }
	    if (!inDate.before(startDate) && !inDate.after(endDate)) {
	        return 0;
	    }
	    if (inDate.before(startDate)){
	    	return 1;
	    }
	    return -1;
	}
	
	/**
	 * 获取剩余时间
	 * x天xx小时
	 * ((startTime + day) - nowTime) format
	 * @param startTime
	 * @param day
	 * @return
	 */
	public static String getSurplusTimeFormat(Date startTime,int day){
		
		// ((startTime + day) - nowTime) format
		Date lastDate = addDay(startTime, day);
		
		long time = lastDate.getTime() - new Date().getTime();
		
		long hours = time/1000/60/60;
		
		StringBuffer sb = new StringBuffer();
		// 小于一天
		if(hours/24 < 1){
			sb.append(hours).append("小时");
		}
		else{
			sb.append(hours/24).append("天");
			
			if(hours%24 > 0){
				sb.append(hours%24).append("小时");
			}
		}
		return sb.toString();
	}
	
	private static int getMonthDays(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);

		return cal.getActualMaximum(Calendar.DATE);
	}
	
	public static String getMonthDaysByFormatYYYYMM(String formatTime){
//		Date date = getDate(formatTime, "YYYY-MM");
//		int year = date.getYear();// 年份
//		int month = date.getMonth();// 月份
		int year = Integer.parseInt(formatTime.split("-")[0]);// 年份
		int month = Integer.parseInt(formatTime.split("-")[1]);// 月份
		int days = getMonthDays(year, month);
		if(days < 10){
			return "0" + days;
		}
		return days + "";
	}
	
 	/**
	 * 检测时间是否在指定天数内
	 * @author guolin
	 * @param date 比当前时间小的时间
	 * @param days 天数
	 * @return true-是，false-否
	 */
	public static boolean checkWithinFewDays(Date date,int days) {
		if (null == date) {
			return false;
		}
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
 
		Calendar paramCalendar = Calendar.getInstance();
		paramCalendar.setTime(date);
		paramCalendar.add(Calendar.DAY_OF_YEAR, days);
		if (now.before(paramCalendar)) {
			return true;
		}
		return false;
	}
  public static void main(String[] args) throws ParseException {

	//System.out.println(getMonthDaysByFormatYYYYMM("2017-02"));
    System.out.println("result::::="+formatTime("2017-05-09 14:10:00"));
  }

}
