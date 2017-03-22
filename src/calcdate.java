package java1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;           // Debug
import java.util.Date;
import java.util.GregorianCalendar;  // Debug

public class calcdate {
	public static void main(String[] args) throws ParseException {
	Date rundate;
	DateFormat df = new SimpleDateFormat("yyyyMMdd");
	rundate = df.parse("20150114");
	Calendar cal = GregorianCalendar.getInstance();
	cal.setTime(rundate);
	cal.add(Calendar.DAY_OF_WEEK, -14);
	rundate = cal.getTime();
	
	System.out.println(df.format(rundate));
	}

}
