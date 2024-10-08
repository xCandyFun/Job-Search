package org.example.ctd;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.Window;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mysqlconnection {

    Dotenv dotenv = Dotenv.configure().load();

    String urlForDB = dotenv.get("DB_URL"),
            usernameForDB = dotenv.get("DB_USER"),
            passwordForDB = dotenv.get("DB_PASSWORD");

    private final String url = urlForDB,
            user = usernameForDB,
            password = passwordForDB;

    private final String tableName = "works";

    String createTableSQL = "CREATE TABLE IF NOT EXISTS works ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "Company VARCHAR(100) NOT NULL,"
            + "Topic VARCHAR(100) NOT NULL,"
            + "Area VARCHAR(100) NOT NULL,"
            + "Date VARCHAR(100) NOT NULL"
            + ")";


    public void connectTodatabase() {

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            System.out.println("Connected to the database!");
            if (!tableExists(connection, tableName)) {
                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully!");
            } else {
                System.out.println("Table already exists.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            System.exit(1);
        }
    }


    public void SaveVariablesToMySQL() {

        List<Object> workData = Window.GetWorkList();

        String company = (String) workData.get(0);
        String topic = (String) workData.get(1);
        String area = (String) workData.get(2);
        LocalDate today = (LocalDate) workData.get(3);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {

                String sql = "INSERT INTO works (Company, Topic, Area, Date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, company);
                    pstmt.setString(2, topic);
                    pstmt.setString(3, area);
                    pstmt.setDate(4, java.sql.Date.valueOf(today));
                    pstmt.executeUpdate();
                    System.out.println("Variables saved successfully!\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> GetDataFromDatabase() {
        List<String> data = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            if (conn != null) {

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, Company, Topic, Area, Date FROM works");

                while (rs.next()) {
                    String id = rs.getString("id");
                    String company = rs.getString("Company");
                    String topic = rs.getString("Topic");
                    String area = rs.getString("Area");
                    String date = rs.getString("Date");

                    data.add(id + " , " + company + " , " + topic + " , " + area + " , " + date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void exportDataToCsv(List<String> data, String filepath) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.append("ID,Company,Topic,Area,Date\n");
            for (String record : data) {
                //writer.append(record.replace("-", ",")).append("\n");
                writer.append(record).append("\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteDataFromId(Integer id) {

        int idToDelete = id;
        // SQL DELETE statement
        String sql = "DELETE FROM works WHERE id = ?"; // sql


        // Establishing connection and deleting the record
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the ID parameter in the SQL statement
            statement.setInt(1, idToDelete);

            // Execute the DELETE statement
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A record was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DropTable() {
        // SQL statement to drop a table
        String dropTableSQL = "DROP TABLE IF EXISTS works";

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Execute the SQL statement
            statement.executeUpdate(dropTableSQL);
            System.out.println("Table dropped successfully");

            if (!tableExists(connection, tableName)) {
                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully!");
            } else {
                System.out.println("Table already exists.");
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }

    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        var resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }
}
