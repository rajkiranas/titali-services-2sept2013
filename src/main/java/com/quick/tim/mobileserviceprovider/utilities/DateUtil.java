/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.utilities;

import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sonalis
 */
public class DateUtil {
    
    private static Date defaultExpiryDate;
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateUtil.class);  


    static {
        initializeDefaultExpiryDate();
    }

    /**
     *  This method is used to convert string to date
     * @param strDate
     * @return date in Date format
     * @throws ParseException
     */
    public static Date stringToDate(String strDate) throws ParseException  {
       DateFormat sdf = null;
       Date date = null;     
       sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
       date = sdf.parse(strDate);
       return date;
    }

    /**
     * compares source date
     * @param sourceDate 
     * @author suyogn
     * @return boolean ComapreDatemethod compares inputed date with current date
     */
    public static boolean ComapreDate(Date sourceDate) {
        boolean flag = true;
        if (sourceDate != null) {
            if (sourceDate.compareTo(new Date()) >= 0) {
                flag = true;
            } else if (sourceDate.compareTo(new Date()) <= 0) {
                flag = false;
            }

        }


        return flag;
    }

    /**
     * getter for defaultExpiryDate
     *
     * @return defaultExpiryDate
     */
    public static Date getDefaultExpiryDate() {
        return defaultExpiryDate;
    }

    /**
     * this method is used to initialize the default expiry date which is
     * further accessed by the application by it gettter method *
     */
    private static void initializeDefaultExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(9999, 11, 31);
        defaultExpiryDate = cal.getTime();
    }

    /**
     * This method converts a string to  date format
     * @param l_strDate
     * @return date in Date format
     */
    public static Date StringToDate(String l_strDate) {

        Date date = null;
        
            try {
            l_strDate = l_strDate.replaceAll(GlobalConstants.HYPHEN, GlobalConstants.forwardSlash);
            date = (Date) formatter.parse(l_strDate);
        } catch (ParseException e) {
            e.printStackTrace();
              logger.debug("Exception occured in StringToDate(String l_strDate) method, input parameter l_strDate=",l_strDate+" ParseException=", e);
        }
        return date;
    }
    public static Date removeTimeStamp(Date date) throws ParseException {

        // Get Calendar object set to the date and time of the given Date object  
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Set time fields to zero  
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Put it back in the Date object  
        date = cal.getTime();
        return date;
    }
     
    public static Date StringToDateForDefaultDate(String l_strDate) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DATEFORMAT);
            date = (Date) formatter.parse(l_strDate);
        } catch (ParseException e) {
            e.printStackTrace();
             logger.debug("Exception occured in  StringToDateForDefaultDate(String l_strDate) method, input parameter l_strDate=",l_strDate+" ParseException=", e);
        }
        return date;
    }
    
    public static Date changeDateFormatToAmerican(Date inputDate) {
        
        Date date = null;
        
            try {
            DateFormat formatter;

            formatter = new SimpleDateFormat(GlobalConstants.DATEFORMAT);
            String strDate = formatter.format(date);
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
             logger.debug("Exception occured in  changeDateFormatToAmerican(Date inputDate) method, input parameter inputDate=",inputDate+" Exception=", e);
        }
            
        
        
        return date;
    }

    /**
     * This method converts date to string format
     * @param date
     * @return strDate
     */
    public static String dateToString(Date date) {
        String strDate;

        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        strDate = formatter.format(date);

        return strDate;
    }
    
    /**
     * This method is used to get the difference of months between two dates.
     * used while generating employee terminated report.
     * @param terminationDate
     * @param hireDate
     * @return 
     */
    
    public static float getMonthsCount(Date terminationDate, Date hireDate )
    {
        long diff = terminationDate.getTime() - hireDate.getTime();

        float duration=(diff / (1000 * 60 * 60 * 24));
        
        //converting to months
        duration=duration/30;
                
        return duration;
    }
    
     /**
     * This method gives no. of days count from current date
     *
     * @param date
     * @return strDate
     */
    public static int getDateDifferenceWithCurrentDate(Date date) {

        return (int) ((new Date().getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
    }
    
    
    /**
     * This method gives Dates between two dates
     *
     * @param startDate and endDate
     * @return List
     */
    
    public static List<Date> getDatesBetween(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date resultado = calendar.getTime();
            dates.add(resultado);
            calendar.add(Calendar.DATE, 1);
        }
        calendar.setTime(endDate);
        dates.add(calendar.getTime());
        return dates;
    }
     public static void getDatesBetween(Date startDate, Date endDate, List dates) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date resultado = calendar.getTime();
            dates.add(resultado);
            calendar.add(Calendar.DATE, 1);
        }
        calendar.setTime(endDate);
        dates.add(calendar.getTime());
    }
    
    public static int getDateDifference(Date startDate, Date endDate ) {

        return (int) ((startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    
     public static int getWeeklyOff(Date startDate, Date endDate) {
        SimpleDateFormat f;
        String day;
         int count=0;
        List<Date> dates = new ArrayList<Date>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startDate);
        f = new SimpleDateFormat("EEEE");
        while (calendar.getTime().before(endDate)) {
            Date resultado = calendar.getTime();
            dates.add(resultado);
            calendar.add(java.util.Calendar.DATE, 1);
            
             day=f.format(resultado);
             if(day.equalsIgnoreCase("Sunday")) {
                count++;
            }

        }
        calendar.setTime(endDate);
        Date date = calendar.getTime();
        day = f.format(date);
        if (day.equalsIgnoreCase("Sunday")) {
            count++;
        }
        return count;
    }

     
        
     public static Calendar getCalenderInstance(){
         Calendar c= Calendar.getInstance();
         return c;
     }    
     
     public static int getYear(Calendar c){
         return c.get(Calendar.YEAR);
     }
     
     public static int getMonth(Calendar c){
         return c.get(Calendar.MONTH);
     }
        
     public static List getSundayList(Date startDate, Date endDate) {

        SimpleDateFormat f;
        String day;
        List<Date> sundayList = new ArrayList<Date>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startDate);
        
        f = new SimpleDateFormat("EEEE");
        
        while (calendar.getTime().before(endDate)) {
            Date resultado = calendar.getTime();
            day = f.format(resultado);
            calendar.add(java.util.Calendar.DATE, 1);
            if (day.equalsIgnoreCase("Sunday")) {
                sundayList.add(resultado);
            }
        }
        calendar.setTime(endDate);
        Date date = calendar.getTime();
        day = f.format(date);
        if (day.equalsIgnoreCase("Sunday")) {
            sundayList.add(date);
        }
        
        return sundayList;
    }
       public static String dateToStringforLOA(Date date) {
        String strDate;

        DateFormat formatter;
        formatter = new SimpleDateFormat(GlobalConstants.DATEFORMAT);
        strDate = formatter.format(date);

        return strDate;
    }
    public static Date increamentDate(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        date = calendar.getTime();

        return date;
    }

    public static int getDateDifference(Short value, Date date) {
        
         java.util.Calendar calendar = java.util.Calendar.getInstance();
         calendar.setTime(date);
         int dob=calendar.get(Calendar.YEAR);         
         return dob-value;        
    }
    
    
  
            
}
