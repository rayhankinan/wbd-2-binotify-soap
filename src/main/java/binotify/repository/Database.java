package binotify.repository;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Database {
  private Connection connection;

  private static String DB_HOST = Dotenv.load().get("MYSQL_HOST", "localhost");
  private static String DB_PORT = Dotenv.load().get("MYSQL_PORT", "3306");
  private static String DB_NAME = Dotenv.load().get("MYSQL_DATABASE", "binotify_soap");
  private static String DB_USER = Dotenv.load().get("MYSQL_USER", "root");
  private static String DB_PASS = Dotenv.load().get("MYSQL_PASSWORD", "root");

  private static String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
  
  public Database() {
    try {
      this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return this.connection;
  }
}

