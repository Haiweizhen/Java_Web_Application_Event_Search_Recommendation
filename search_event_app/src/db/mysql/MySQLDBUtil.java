package db.mysql;

public class MySQLDBUtil {
	  private static final String HOSTNAME = "localhost";
	  private static final String PORT_NUM = "3306"; //change it to your mysql port number8889
	  public static final String DB_NAME = "myproject";
	  private static final String USERNAME = "root";
	  private static final String PASSWORD = "root";
	  
	  
	  public static final String URL = "jdbc:mysql://"
	      + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
	      + "?user=" + USERNAME + "&password=" + PASSWORD
	      + "&autoReconnect=true&serverTimezone=UTC";
	  
	  //jdbc:mysql://localhost:8889/myproject?user=root&password=root&autoReconnect=true&serverTimezone=UTC
	}
