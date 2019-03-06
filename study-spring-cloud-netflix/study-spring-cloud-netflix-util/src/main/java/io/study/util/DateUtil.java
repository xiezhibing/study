package io.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * TODO 日期工具类
 *
 * @author XieZhibing
 * @version <b>1.0.0</b>
 * @date 2017年3月8日 下午5:00:59
 */
public class DateUtil implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
    /***/
    private static final long serialVersionUID = 1L;

    /**
     * 默认短日期格式
     * yyyy-MM-dd
     */
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
    
    /** 
     * 订单号前缀 yyyyMMddHHmmss
     */
    public static final String DATETIME_ORDER_FORMAT = "yyyyMMddHHmmss";

    /**
     * 订单号前缀 yyyyMMddHHmmssSSS
     */
    public static final String DATETIME_WITH_MILLI_ORDER_FORMAT = "yyyyMMddHHmmssSSS";


    /**
     * 默认日期时间格式
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认时间格式
     * HH:mm:ss
     */
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
    /**
     * 默认日期短格式
     * yyyyMMdd
     */
    public static final String DATE_DEFAULT_SHORT_FORMAT = "yyyyMMdd";

    /**
     * 默认短日期格格式化
     */
    private static DateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
    /**
     * 默认日期时间格式化
     */
    private static DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
    /**
     * 默认时间格式化
     */
    private static DateFormat timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);

    /**
     * 日期正则格式
     */
    private static Map<String, String> dateRegFormat = new HashMap<String, String>();

    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
    
    static {
        //2017年3月12日 12时05分34秒，2017-03-12 12:05:34，2017/3/12 12:5:34
        dateRegFormat.put("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$", "yyyy-MM-dd-HH-mm-ss");
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd-HH-mm");//2017-03-12 12:05
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd-HH");//2017-03-12 12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");//2017-03-12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");//2017-03
        dateRegFormat.put("^\\d{4}$", "yyyy");//2017
        dateRegFormat.put("^\\d{14}$", "yyyyMMddHHmmss");//20170312120534
        dateRegFormat.put("^\\d{12}$", "yyyyMMddHHmm");//201703121205
        dateRegFormat.put("^\\d{10}$", "yyyyMMddHH");//2017031212
        dateRegFormat.put("^\\d{8}$", "yyyyMMdd");//20170312
        dateRegFormat.put("^\\d{6}$", "yyyyMM");//201703
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm-ss");//13:05:34 拼接当前日期
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm");//13:05 拼接当前日期
        dateRegFormat.put("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd");//16.10.18(年.月.日)
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM");//30.12(日.月) 拼接当前年份
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy");//12.21.2016(日.月.年)
    }
    
    /**
	 * ron
     * 解决SimpleDateFormat静态化的坑
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 双检锁是防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                   
                    // 使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {

                        @Override
                        protected SimpleDateFormat initialValue() {
                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }
    

    /**
     * TODO 将各种日期格式转为  yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     * @author XieZhibing
     * @date 2017年3月13日 下午6:13:45
     */
    public static String formatDate(String dateStr) {
        String curDate = new SimpleDateFormat(DATE_DEFAULT_FORMAT).format(new Date());
        DateFormat formatter1 = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        DateFormat formatter2;
        String result = "";
        try {
            for (String key : dateRegFormat.keySet()) {
                if (Pattern.compile(key).matcher(dateStr).matches()) {
                    formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
                    if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
                            || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//13:05:34 或 13:05 拼接当前日期
                        dateStr = curDate + "-" + dateStr;
                    } else if (key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//21.1 (日.月) 拼接当前年份
                        dateStr = curDate.substring(0, 4) + "-" + dateStr;
                    }

                    result = formatter1.format(formatter2.parse(dateStr.replaceAll("\\D+", "-")));
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("-----------------日期格式无效:" + dateStr);
        }
        return result;
    }

    /**
     * 日期格式化
     * @param date	日期格式字符串
     * @param format 日期格式
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 日期格式化
     * @author XieZhibing
     * @date 2017年5月10日 下午2:46:44
     * @param date 日期
     * @param format 日期格式
     * @return
     */
    public static Date formatDate(Date date, String format) {
    	String sdate = getDateFormat(date, format);
    	return formatDate(sdate, format);
    }
    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
    	if(date == null) {
    		return null;
    	}
        return dateFormat.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
    	if(date == null) {
    		return null;
    	}
        return dateTimeFormat.format(date);
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
    	if(date == null) {
    		return null;
    	}
        return timeFormat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format 格式类型
     * @return
     */
    public static String getDateFormat(Date date, String format) {
        if (StringUtil.isNotEmpty(format)) {
            return new SimpleDateFormat(format).format(date);
        }
        return null;
    }

    /**
     * 日期格式化 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @return
     */
    public static Date getNowDate() {
        return DateUtil.getDateFormat(dateFormat.format(new Date()));
    }

    /**
     * 日期时间毫秒级格式化
     *
     * @param time 日期 yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static String getDateTimeMillisFormat(long time) {
    	return getDateTimeMillisFormat(new Date(time)) ;
    }

    /**
     * 日期时间毫秒级格式化
     *
     * @param date 日期 格式类型 yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static String getDateTimeMillisFormat(Date date) {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
    }

    /**
     * 获取本周第一天
     *
     * @return 周日
     */
    public static Date getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // SUNDAY
        return calendar.getTime();
    }

    /**
     * 获取本周最后一天
     *
     * @return  周六
     */
    public static Date getLastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY + 6); // SATURDAY
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在周的第一天
     *
     * @param date 指定日期
     * @return 周日
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // SUNDAY
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在周的最后一天
     *
     * @param date 指定日期
     * @return 周六
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY + 6); // SATURDAY
        return calendar.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取当前年份的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前年份的最后一天
     *
     * @return 今天的1月1日
     */
    public static Date getLastDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在年份的第一天
     *
     * @return 某年1月1日
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在年份的最后一天
     *
     * @return 某年1月1日
     */
    public static Date getLastDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampStr 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String timeStamp2Date(String timestampStr, String formats) {
        if (StringUtil.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampStr) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    public static Date getDayBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);
        return calendar.getTime();
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    public static Date getDayAfter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);
        return calendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getNowMonthDays() {
        Calendar d = Calendar.getInstance();
        return d.getActualMaximum(Calendar.DATE);
    }

    /**
     * 现在是本周中第几天
     * @return
     */
	public static int getNowDayOfWeek() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.DAY_OF_WEEK);
	}
	
    /**
     * 现在是本月中第几天
     * @return
     */
	public static int getNowDayOfMonth() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.DAY_OF_MONTH);
	}
	
    /**
     * 现在是一年中第几天
     * @return
     */
	public static int getNowDayOfYear() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 现在是一天中第几小时
	 * @return
	 */
	public static int getNowHourOfDay() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 现在是一年中第几小时
	 * @return
	 */
	public static int getNowHourOfYear() {
		int days = getNowDayOfYear();
		int hours = getNowHourOfDay();
		
		return 24 * days + hours;
	}
    

	/**
	 * 一年中第几小时
	 * 
	 * @return
	 */
	public static String formatHourOfYear() {
		Calendar d = Calendar.getInstance();
		int day = d.get(Calendar.DAY_OF_YEAR);
		int hour = d.get(Calendar.HOUR_OF_DAY);
		hour = 24 * day + hour;
		return String.format("%04d", hour);
	}

	/**
	 * 一年中的第几分钟
	 * 
	 * @return
	 */
	public static String formatMinuteOfYear() {
		Calendar d = Calendar.getInstance();
		int day = d.get(Calendar.DAY_OF_YEAR);
		int hour = d.get(Calendar.HOUR_OF_DAY);
		int minute = d.get(Calendar.MINUTE);
		minute = 60 * 24 * day + 60 * (hour - 1) + minute;
		return String.format("%06d", minute);
	}

	/**
	 * 一年中第几秒
	 * 
	 * @return
	 */
	public static String formatSecondOfYear() {
		Calendar d = Calendar.getInstance();
		int day = d.get(Calendar.DAY_OF_YEAR);
		int hour = d.get(Calendar.HOUR_OF_DAY);
		int minute = d.get(Calendar.MINUTE);
		int second = d.get(Calendar.SECOND);
		second = 60 * (60 * 24 * day + 60 * (hour - 1) + 60 * (minute - 1)) + second;
		return String.format("%08d", second);
	}

    /**
     * 获取时间段的每一周
     *
     * @param startDate
     * @param endDate
     * @return 周列表:[0, 1, 2,....53]
     */
    public static List<Integer> getEveryWeek(Date startDate, Date endDate) {
        List<Integer> weeks = new ArrayList<Integer>();
        if (startDate == null || endDate == null) {
            return weeks;
        }

        // 格式化日期(yy-MM-dd)
        startDate = DateUtil.getDateFormat(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.getDateFormat(DateUtil.getDateFormat(endDate));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.setTime(startDate);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int days = DateUtil.getDaysBetween(startDate, endDate) + 1;
        int n = days/7;
        if((days%7) > 0){
            n = n + 1;
        }
        for(int i = 0; i < n; i++){
            int e = week + i;
            weeks.add(e);
        }
        return weeks;
    }

    /**
     * 获取时间段的每一天
     *
     * @param startDate
     * @param endDate
     * @return 日期列表
     */
    public static List<Date> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        // 格式化日期(yy-MM-dd)
        startDate = DateUtil.getDateFormat(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.getDateFormat(DateUtil.getDateFormat(endDate));
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        dates.add(calendar.getTime());
        while (calendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(calendar.getTime());
        }
        return dates;
    }

    /**
     * 获取提前多少个月
     *
     * @param monty
     * @return
     */
    public static Date getFirstMonth(int monty) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -monty);
        return c.getTime();
    }
    
    /**
     * 
     * TODO 比较日期大小
     * @author XieZhibing
     * @date 2017年3月17日 下午5:19:02
     * @param sdate1
     * @param sdate2
     * @return sdate1大于sdate2, 返回 1; </br>
     * 		sdate1等于sdate2, 返回 0; </br>
     * 		sdate1小于sdate2, 返回 -1; </br>
     */
    public static int compareDate(String sdate1, String sdate2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = df.parse(sdate1);
            Date date2 = df.parse(sdate2);
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     * 
     * TODO 比较日期大小
     * @author XieZhibing
     * @date 2017年3月17日 下午5:25:30
     * @param date1
     * @param sdate
     * @return date1大于sdate, 返回 1; </br>
     * 		date1等于sdate, 返回 0; </br>
     * 		date1小于sdate, 返回 -1; </br>
     */
    public static int compareDate(Date date1, String sdate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date2 = df.parse(sdate);
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     * 
     * TODO 比较日期大小
     * @author XieZhibing
     * @date 2017年3月17日 下午5:27:17
     * @param date1
     * @param date2
     * @return date1大于date2, 返回 1; </br>
     * 		date1等于date2, 返回 0; </br>
     * 		date1小于date2, 返回 -1; </br>
     */
    public static int compareDate(Date date1, Date date2) {
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     * TODO 返回日期之间相差的天数
     * @author XieZhibing
     * @date 2017年3月17日 下午5:29:49
     * @param smdate
     * @param bdate
     * @return
     */
    public static int getDaysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Calendar getTime() {
        Calendar time=Calendar.getInstance();
        return time;
    }

    public static Calendar getTime(long timeMillis) {
        Calendar time=getTime();
        time.setTimeInMillis(timeMillis);
        return time;
    }

    public static Date getDate() {
        return getTime().getTime();
    }

    public static Date getDate(long timeMillis) {
        return getTime(timeMillis).getTime();
    }

    /**
     * 转换日期
     * @param date
     * @return
     */
	public static Calendar getCalendar(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	/**
	 * 
	 * TODO 计算昨天或上周五15点
	 * @author xiezb
	 * @date 2016年9月22日 上午10:04:48
	 * @param date
	 * @return 计算昨天或上周五15点
	 */
	public static Date calcYesterdayOrLastFriday15OClock(Date date) {
		//某一天
		Date oneDate = date;
		//今天周几
		int weekDay = getDayOfWeek(date);
		//date是周一
		if(weekDay == 1){
			//某一天
			oneDate = addDays(oneDate, -3);
		} else {
			//某一天
			oneDate = addDays(date, -1);
		}
		
		//某一天
		String sOneDay = getDateFormat(oneDate, DATE_DEFAULT_FORMAT);		
		//某一天15点
		String sOneDay15 = sOneDay+" 15:00:00";
		
		return formatDate(sOneDay15, DATETIME_DEFAULT_FORMAT);
	}
	/**
	 * 
	 * TODO 计算某一天15点
	 * @author xiezb
	 * @date 2016年9月22日 上午11:58:37
	 * @param date 日期
	 * @param days 天数
	 * @return
	 */
	public static Date calcOneDay15OClock(Date date, int days) {
		//某一天
		Date oneDate = addDays(date, days);
		//某一天
		String sOneDay = getDateFormat(oneDate, DATE_DEFAULT_FORMAT);		
		//某一天15点
		String sOneDay15 = sOneDay+" 15:00:00";
		
		return formatDate(sOneDay15, DATETIME_DEFAULT_FORMAT);
	}
	
	/**
	 * 年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		if (date == null) {
			return -1;
		}
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		if (date == null) {
			return -1;
		}
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 本年的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		if (date == null) {
			return -1;
		}
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 本月的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		if (date == null) {
			return -1;
		}
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

    /**
     * 本年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        if (date == null) {
            return -1;
        }
        Locale.setDefault(Locale.US);
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

	/**
	 * 本周第几天（周几）
	 * 
	 * @param date
	 * @return
	 */
    public static int getDayOfWeek(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

	/**
	 * 返回小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 返回两个日期之间的相隔毫秒数
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getMillisBetween(Date d1, Date d2) {
		long s = getCalendar(d1).getTimeInMillis();
		long e = getCalendar(d2).getTimeInMillis();
		return Math.abs(e - s);
	}

	/**
	 * 返回两个日期之间的相隔分钟
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getMinutesBetween(Date d1, Date d2) {
		long s = getCalendar(d1).getTimeInMillis();
		long e = getCalendar(d2).getTimeInMillis();
		return Math.abs(e - s) / (1000 * 60);
	}

	/**
	 * 获取两个日期之间的天数：cal2 - cal1
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static int getDaysBetween(Calendar cal1, Calendar cal2) {
		boolean flag = true;
		int days = cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		int year1 = cal1.get(Calendar.YEAR);

		if (year2 < year1) {
			flag = false;
		}
		if (year1 != year2) {
			do {
				days += cal1.getActualMaximum(Calendar.DAY_OF_YEAR);
				if (flag) {
					cal1.add(Calendar.YEAR, 1);
				} else {
					cal1.add(Calendar.YEAR, -1);
				}
			} while (cal1.get(Calendar.YEAR) != year2);
		}
		if (flag) {
			return days;
		}
		return -days;
	}

	/**
	 * 获取两个日期之间的天数：date2 - date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDaysBetween(Date date1, Date date2) {
		return getDaysBetween(getCalendar(date1), getCalendar(date2));
	}

	/**
	 * 获取两个日期之间相隔月份 cal2 - cal1
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static int getMonthsBetween(Calendar cal1, Calendar cal2) {
		int months = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		boolean flag = true;

		int year2 = cal2.get(Calendar.YEAR);
		int year1 = cal1.get(Calendar.YEAR);

		if (year2 < year1) {
            flag = false;
        }

		if (year1 != year2) {
			do {
				months += cal1.getActualMaximum(Calendar.MONTH) + 1;
				if (flag) {
                    cal1.add(Calendar.YEAR, 1);
                }
				else {
                    cal1.add(Calendar.YEAR, -1);
                }
			} while (cal1.get(Calendar.YEAR) != year2);
		}
		if (flag) {
			return months;
		}
		return -months;
	}

	/**
	 * 获取两个日期相隔的月份 date2 - date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMonthsBetween(Date date1, Date date2) {
		return getMonthsBetween(getCalendar(date1), getCalendar(date2));
	}

	/**
	 * 获取两个日期相隔年份 cal2 - cal1
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static int getYearsBetween(Calendar cal1, Calendar cal2) {
		int year2 = cal2.get(1);
		int year1 = cal1.get(1);
		return year2 - year1;
	}

	/**
	 * 获取两个日期相隔年份 date2 - date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getYearsBetween(Date date1, Date date2) {
		return getYearsBetween(getCalendar(date1), getCalendar(date2));
	}

	/**
	 * 获取给定日期n天后（前）的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDays(Date date, int n) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return calendar.getTime();
	}

	/**
	 * 获取给定日期n个月后（前）的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMonths(Date date, int n) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);
		return calendar.getTime();
	}

	/**
	 * 获取给定日期n年后（前）的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addYears(Date date, int n) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, n);
		return calendar.getTime();
	}

	/**
	 * 获取给定日期n小时后（前）的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addHours(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, n);
		return calendar.getTime();
	}

	/**
	 * 获取给定日期n分钟后（前）的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMinutes(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, n);
		return calendar.getTime();
	}

	/**
	 * 获取给定日期n秒后（前）的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addSeconds(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, n);
		return calendar.getTime();
	}

	/**
	 * 获取上个月第一天
	 * 
	 * @return
	 */
	public static Date getLastMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取上个月最后一天
	 * 
	 * @return
	 */
	public static Date getLastMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前月第一天
	 * 
	 * @return
	 */
	public static Date getCurMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * / 获取前月的最后一天
	 * 
	 * @return
	 */
	public static Date getCurMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.getTime();
	}

	/**
	 * 获取给定时间所在月份的第一天
	 * @param date
	 * @return
	 */
	public static Date getSpecifyDateMonthFirstDay(Date date){
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	public static Date getSpecifyDateMonthLastDay(Date date){
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.getTime();
	}
	

	/**
	 * 获取当前年份给定月份的天数
	 * 
	 * @return
	 */
	public static int dayLenOfMonth(int m) {
		if (m == 2) {
			int year = getYear(new Date());
			if (((year % 100 == 0) && (year % 400 == 0)) || ((year % 100 != 0) && (year % 4 == 0))) {
				return 29;
			}
			return 28;
		} else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
			return 31;
		} else {
			return 30;
		}
	}

	/**
	 * 是否使用平今仓
	 * @param exchangeNo	交易所编码
	 * @param sysBuyDealTime	系统买入成交时间
	 * @return
	 */
	public static boolean isCloseToday(String exchangeNo, Date sysBuyDealTime){
		if(!"SHFE".equalsIgnoreCase(exchangeNo)){
			return false;
		}
		
		if(sysBuyDealTime == null) {
			return false;
		}
		
		//系统买入时间
		long buyTime = sysBuyDealTime.getTime();
		//当前时间
		Date now = new Date();
		//系统平仓时间
		long sysSaleTime = now.getTime();		
		//今天15点		
		Date thisDate15 = DateUtil.calcOneDay15OClock(now, 0);
		//昨天或上周五15点
		Date lastDate15 = DateUtil.calcYesterdayOrLastFriday15OClock(now);
		//明天15点
		Date nextDate15 = DateUtil.calcOneDay15OClock(now, 1);
		
		//上期所闭市时间： [01:00, 02:30, 15:00]
		if(sysSaleTime > lastDate15.getTime() && sysSaleTime <= thisDate15.getTime()) {				
			if(buyTime > lastDate15.getTime() && buyTime < thisDate15.getTime()) {
				//平今: 计算系统平仓参数
				return true;
			} else if(buyTime < lastDate15.getTime()) {
				//平仓: 计算系统平仓参数
				return false;
			}
		} 
		//上期所闭市时间： [23:00]
		else if(sysSaleTime > thisDate15.getTime() && sysSaleTime < nextDate15.getTime()) {
			//平仓
            //平今: 计算系统平仓参数
            if (buyTime < thisDate15.getTime()) {
				//平仓: 计算系统平仓参数
				return false;
            } else return buyTime > thisDate15.getTime() && buyTime < nextDate15.getTime();
		}
		return false;
	}

    /**
     * 获取日期的开始时间
     * @param date 被转换的日期, 如: 2020-05-01 13:40:50
     * @return 2020-05-01 00:00:00
     */
	public static Date formatDateStartTime(Date date){
        String sdate = getDateFormat(date, DATE_DEFAULT_FORMAT);
        return formatDate(sdate + " 00:00:00", DATETIME_DEFAULT_FORMAT);
    }

    /**
     * 获取日期的结束时间
     * @param date 被转换的日期, 如: 2020-05-01 13:40:50
     * @return 2020-05-01 23:59:59
     */
    public static Date formatDateEndTime(Date date){
        String sdate = getDateFormat(date, DATE_DEFAULT_FORMAT);
        return formatDate(sdate + " 23:59:59", DATETIME_DEFAULT_FORMAT);
    }

    /**
     * 获取日期的开始时间
     * @param date 被转换的日期, 如: 2020-05-01 13:40:50
     * @return 2020-05-01 00:00:00
     */
    public static Date formatDateStartTime(Date date, TimeZone timeZone){
        String sdate = getDateFormat(date, DATE_DEFAULT_FORMAT);
        Date date1 = formatDate(sdate + " 00:00:00", DATETIME_DEFAULT_FORMAT);
        int n = -diffHour(timeZone);
        return DateUtil.addHours(date1, n);
    }

    /**
     * 获取日期的结束时间
     * @param date 被转换的日期, 如: 2020-05-01 13:40:50
     * @return 2020-05-01 23:59:59
     */
    public static Date formatDateEndTime(Date date, TimeZone timeZone){
        String sdate = getDateFormat(date, DATE_DEFAULT_FORMAT);
        Date date1 = formatDate(sdate + " 23:59:59", DATETIME_DEFAULT_FORMAT);
        int n = -diffHour(timeZone);
        return DateUtil.addHours(date1, n);
    }
    /**
     * 根据时区转换时间
     * @param utcTime
     * @param targetFormat
     * @param targetTimeZone
     * @return
     */
    public static String timeTransfer(Long utcTime, String targetFormat, TimeZone targetTimeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(targetFormat);
        simpleDateFormat.setTimeZone(targetTimeZone);
        return simpleDateFormat.format(utcTime);
    }

    /**
     * 根据时区转换时间
     * @param utcTime
     * @param targetFormat
     * @param targetTimeZone
     * @return
     */
    public static String timeTransfer(Date utcTime, String targetFormat, TimeZone targetTimeZone) {
        if(utcTime == null){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(targetFormat);
        simpleDateFormat.setTimeZone(targetTimeZone);
        return simpleDateFormat.format(utcTime);
    }

    /**
     * 根据时区转换时间
     * @param time
     * @param srcTimeZone
     * @param targetTimeZone
     * @return
     */
    public static String timeTransfer(String time, String targetFormat, TimeZone srcTimeZone, TimeZone targetTimeZone) {
        if(StringUtil.isEmpty(time)){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(srcTimeZone);
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            logger.info("info", e);
            return "0";
        }
        simpleDateFormat.setTimeZone(targetTimeZone);
        return simpleDateFormat.format(date);
    }


    /**
     * 计算时区与标准时区的时间差
     * @param timeZone
     * @return 小时数
     */
    public static int diffHour(TimeZone timeZone){
        int diff1 = timeZone.getRawOffset() / (1000 * 60 * 60);
        int diff2 = timeZone.getDSTSavings() / (1000 * 60 * 60);
        int diff = diff1 + diff2;
        return diff;
    }

    /**
     * 计算两个时区的时间差
     * @param timeZone1
     * @param timeZone2
     * @return 小时数
     */
    public static int diffHour(TimeZone timeZone1, TimeZone timeZone2){
        int diff1 = diffHour(timeZone1);
        int diff2 = diffHour(timeZone2);
        int diff = diff2 - diff1;
        return diff;
    }

    public static void main(String[] args) {
    	
//    	int day = getNowDayOfYear();
//    	int dhour = getNowHourOfDay();
//    	int yhour = getNowHourOfYear();
//    	int wday = getNowDayOfWeek();
//    	logger.info("day:{}, wday:{}, dhour:{}, yhour:{}", day, wday, dhour, yhour);
    	
    	Date startTime = DateUtil.formatDate("2018-01-01 00:00:00", DATETIME_DEFAULT_FORMAT);
        Date endTime = DateUtil.formatDate("2018-05-01 00:00:00", DATETIME_DEFAULT_FORMAT);

        List<Integer> everyWeek = getEveryWeek(startTime, endTime);
        for (Integer week:everyWeek) {
            logger.info("week:{}", week);
        }


        System.out.println("startTime: " + startTime.getTime());
        System.out.println("endTime: "+endTime.getTime());
    }

}
