package louw.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

// this custom formatter formats parts of a log record to a single line
class MyHtmlFormatter extends Formatter {
  // this method is called for every log records
  public String format(LogRecord rec) {
    StringBuffer buf = new StringBuffer(1000);
    buf.append("<tr>\r\n");

    // color any levels >= WARNING in red
    if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
      buf.append("\t<td style=\"color:red\">");
      buf.append("<b>");
      buf.append(rec.getLevel());
      buf.append("</b>");
    } else {
      buf.append("\t<td>");
      buf.append(rec.getLevel());
    }

    buf.append("</td>");
    buf.append("\t<td>");
    buf.append(calcDate(rec.getMillis()));
    buf.append("</td>");
    buf.append("\t<td>");
    buf.append(formatMessage(rec));
    buf.append("</td>");
    buf.append("</tr>\r\n");

    return buf.toString();
  }

  private String calcDate(long millisecs) {
    SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date resultdate = new Date(millisecs);
    return date_format.format(resultdate);
  }

  // this method is called just after the handler using this
  // formatter is created
  public String getHead(Handler h) {
      return "<!DOCTYPE html>\r\n<head>\n<style>\r\n"
          + "table { width: 100% }\r\n"
          + "th { font:bold 10pt Tahoma; }\r\n"
          + "td { font:normal 10pt Tahoma; }\r\n"
          + "h1 {font:normal 11pt Tahoma;}\r\n"
          + "</style>\r\n"
          + "</head>\r\n"
          + "<body>\r\n"
          + "<h1>" + h.toString() + " " + calcDate(System.currentTimeMillis()) + "</h1>\r\n" 
          + "<table border=\"0\" cellpadding=\"1\" cellspacing=\"1\">\r\n"
          + "<tr align=\"left\">\r\n"
          + "\t<th style=\"width:10%\">Loglevel</th>\r\n"
          + "\t<th style=\"width:15%\">Time</th>\r\n"
          + "\t<th style=\"width:75%\">Log Message</th>\r\n"
          + "</tr>\r\n";
    }

  // this method is called just after the handler using this
  // formatter is closed
  public String getTail(Handler h) {
    return "</table>\n</body>\n</html>";
  }
} 