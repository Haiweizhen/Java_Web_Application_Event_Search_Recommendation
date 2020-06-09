package db;

import db.mysql.MySQLConnection;

public class DBConnectionFactory {
	  // This should change based on the pipeline.
	  private static final String DEFAULT_DB = "mysql";
	  
	  
	  //one data parameter
	  public static DBConnection getConnection(String db) {
	    switch (db) {
	    case "mysql":
	      return new MySQLConnection();
	     // return null;
	    case "mongodb":
	      // return new MongoDBConnection();
	      return null;
	    default:
	      throw new IllegalArgumentException("Invalid db: " + db);
	    }
	  }
	  
	  //no parameter
	  public static DBConnection getConnection() {
	    return getConnection(DEFAULT_DB);
	  }
}
