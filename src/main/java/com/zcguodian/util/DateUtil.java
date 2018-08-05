package com.zcguodian.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 一些用来处理时间的工具类
 */
public class DateUtil
{

    public static final SimpleDateFormat yyMMdd = new SimpleDateFormat("yyMMdd");
    
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public static final SimpleDateFormat yyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat yyMM = new SimpleDateFormat("yyyy-MM");

    public static final SimpleDateFormat MMddHHmm = new SimpleDateFormat("MM-dd HH:mm");
    
    public static final SimpleDateFormat yyMMddSeparator = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    

    public static String formatToyyMMdd(Date date)
    {
        return yyMMdd.format(date);
    }
    
    public static String formatToyyyyMMdd(Date date)
    {
        return yyyyMMdd.format(date);
    }

    public static String formatToyyMMddHHmm(Date date)
    {
        return yyMMddHHmm.format(date);
    }

    public static String formatToMMddHHmm(Date date)
    {
        return MMddHHmm.format(date);
    }
    
    public static String formatToYYYYMMDDMMHHSS(Date date)
    {
        return yyyyMMddHHmmss.format(date);
    }

    public static String formatToyyMM(Date date)
    {
        return yyMM.format(date);
    }

    public static Date parseStringFromyyMMdd(String yyMMddString) throws ParseException
    {
        return yyMMdd.parse(yyMMddString);
    }

    public static Date parseStringFromyyMM(String date) throws ParseException
    {
        return yyMM.parse(date);
    }
    
    public static Date parseStringFromyyMMddSeparator(String date) throws ParseException {
        return yyMMddSeparator.parse(date);
    }
    


    /**
     * 是否是同一天
     * @param newerDate
     * @param olderDate
     * @return
     */
    public static boolean isSameDay(Date newerDate, Date olderDate)
    {
    	if(daysBetween(newerDate, olderDate)==0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * 取两个Date之间的天数差<br>
     * <br>
     * 例：newerDate是6月3日，olderDate是5月31日，则应返回3 <br>
     * 一个更极端的列子：newerDate是6月3日 00:01，olderDate是6月2日 23:59，则应返回1，说明相差一天，即便实际上只差2分钟 <br>
     * 此代码来自网上 <br>
     * http://blog.csdn.net/rmartin/article/details/1452867
     * 
     * @param newerDate
     * @param olderDate
     * @return
     */
    public static int daysBetween(Date newerDate, Date olderDate)
    {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(newerDate);
        cReturnDate.setTime(olderDate);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
    }

    private static int millisecondsToDays(long intervalMs)
    {
        return (int) (intervalMs / (1000 * 86400));
    }

    private static void setTimeToMidnight(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public static Date dateByYears(String years) throws ParseException
    {
        String yearStr = years.split("-")[0].equals("") ? "0" : years.split("-")[0];
        if (yearStr.length() < 4)
        {
            int year = Integer.parseInt(yearStr);
            int month = 0;
            if (years.indexOf("-") != -1)
            {
                if (years.split("-").length > 1)
                    month = Integer.parseInt(years.split("-")[1]);
            }
            Calendar ca = Calendar.getInstance();
            int nowMonth = ca.get(Calendar.MONTH) + 1;
            int nowYear = ca.get(Calendar.YEAR);

            int oldYear = nowYear - year;
            int oldMonth = 0;
            int m = nowMonth - month;
            if (m > 0)
            {
                oldMonth = m;
            }
            else
            {
                oldMonth = 12 + m;
                oldYear = oldYear - 1;
            }
            return parseStringFromyyMM(oldYear + "-" + oldMonth);
        }
        else
        {
            if (years.split("-").length > 1)
                yearStr = yearStr + "-" + years.split("-")[1];
            else yearStr = yearStr + "-01";
            return parseStringFromyyMM(yearStr);
        }
    }

    public static String yearsByDate(Date date)
    {
        if (date != null)
        {
            String dateStr = formatToyyMM(date);
//            int oldYear = Integer.parseInt(dateStr.split("-")[0]);
//            int oldMonth = Integer.parseInt(dateStr.split("-")[1]);
//            Calendar ca = Calendar.getInstance();
//            int nowMonth = ca.get(Calendar.MONTH) + 1;
//            int nowYear = ca.get(Calendar.YEAR);
//            int m = nowMonth - oldMonth;
//            int year = nowYear - oldYear;
//            ;
//            int month = 0;
//            if (m >= 0)
//            {
//                month = m;
//            }
//            else
//            {
//                month = 12 + m;
//                year = year - 1;
//            }
//            return year + "-" + month;
            return dateStr;
        }
        else
        {
            return "";
        }
    }

    /**
     * 返回某个按月还款的Loan在第n期的还款时间。时间计算的起点是放标时间，即Loan.passTime <br>
     * <br>
     * 某标在6月3日投满，并经确认放款给借款者（都在6月3日
     * 23：59之前完成）。则此标第一个还款日应是7月2日。在7月2日晚23：59分或之前还款，都算是正常。（一个更典型的例子：6月1日放款的标，第一个还款日应是6月30日，而不是7月1日。） <br>
     * <br>
     * 如果在7月3日0：00之后还款，则视为逾期，如果还款人在7月4日凌晨1：00还款，逾期了25个小时，则视为逾期2天。
     * 
     * @param loan
     * @param phaseNumber
     *            期数，从1开始
     * @return 在第n期的还款时间
     */
    public static Date getMonthlyRepayDate(Date releaseTime, int phaseNumber)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(releaseTime);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        calendar.add(Calendar.MONTH, phaseNumber);
        return calendar.getTime();
    }
    
    

    /**
     * 返回一个二维数组，单位分别是月和日，代表两个Date之差。 <br>
     * 本方法忽略小时和分钟。 <br>
     * <br>
     * 例： <br>
     * 1，2012年6月1日到2012年6月3日，返回值是[0,2] （2天） <br>
     * 2，2012年6月15日到2012年8月4日，返回值是[1,20] （1个月零20天） <br>
     * 3，2011年11月3日到2013年1月14日，返回值是[14,11] （14个月零11天）
     * 
     * @param olderDate
     *            较早的日期
     * @param newerDate
     *            较晚的日期
     * @return
     * @throws IllegalPlatformAugumentException
     *             当olderDate晚于newerDate时
     */
    public static int[] getDateDifferenceInMonthAndDay(Date olderDate, Date newerDate)
    {
        int[] differenceInMonthAndDay = new int[2];
        int years = 0;
        int months = 0;
        int days = 0;

        Calendar older = Calendar.getInstance();
        Calendar newer = Calendar.getInstance();
        older.setTime(olderDate);
        newer.setTime(newerDate);
        
        if(olderDate.getTime()>newerDate.getTime()){
        }else{
            years = newer.get(Calendar.YEAR)-older.get(Calendar.YEAR);
            if(years>=0){
                
                months = newer.get(Calendar.MONTH)-older.get(Calendar.MONTH)+12*years;
                older.add(Calendar.MONTH,months);
                days = newer.get(Calendar.DATE)-older.get(Calendar.DATE);
                
                if(days<0){
                    months = months - 1;
                    older.add(Calendar.MONTH,-1);
                }
                
                days = daysBetween(newer.getTime(),older.getTime());
                differenceInMonthAndDay[0] = months;
                differenceInMonthAndDay[1] = days;
            }
            
        }  
        return differenceInMonthAndDay;
    }
    

	/**
	 * 获取指定日期上一天的日期
	 * @param baseDate
	 * @return
	 */
	public static Date getYesterdayDate(Date baseDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
    
}
