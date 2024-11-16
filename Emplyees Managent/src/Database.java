import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database {
    protected String host;
    protected String username;
    protected String password;

    protected Connection connection;

    public boolean debug = false;

    public Database(String host, String username, String password, Object... args) {
        this.host = host;
        this.username = username;
        this.password = password;

        if (args.length > 0) {
            this.debug = (boolean) args[0];
            System.out.println("[<DEBUG><Database>]: Debug mode is enabled.");
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected void connect() {
        try {
            connection = DriverManager.getConnection(host, username, password);
            if (this.debug) {
                System.out.println("[<DEBUG><Database>]: Connected to the database!");
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1045 ->
                    System.out.println("[<DEBUG><Database>]: Access denied for user '" + username
                            + "'@'localhost' (using password: YES)");
                case 1049 -> System.out.println("[<DEBUG><Database>]: Unknown database");
                default -> e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected boolean insert(
            String table,
            List<String> columns,
            List<String> values) {

        if (columns.size() != values.size()) {
            if (this.debug)
                System.out.println("[<DEBUG><Database>]: Number of columns and values do not match.");
            return false;
        }

        String query = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.size(); i++) {
            query += columns.get(i);
            if (i < columns.size() - 1) {
                query += ", ";
            }
        }
        query += ") VALUES (";
        for (int i = 0; i < values.size(); i++) {
            query += "?";
            if (i < values.size() - 1) {
                query += ", ";
            }
        }
        query += ")";

        try {
            var statement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                statement.setString(i + 1, values.get(i));
            }
            int status = statement.executeUpdate();

            if (status == 1) {
                if (this.debug) {
                    System.out.println("[<DEBUG><Database>]: Record inserted successfully.");
                    System.out.println("Query: " + query);
                }
                return true;
            } else {
                if (this.debug)
                    System.out.println("[<DEBUG><Database>]: Record insertion failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected List<Map<String, String>> select(
            String table,
            List<String> columns,
            String condition) {
        String query = "SELECT ";
        for (int i = 0; i < columns.size(); i++) {
            query += columns.get(i);
            if (i < columns.size() - 1) {
                query += ", ";
            }
        }

        query += " FROM " + table;
        if (condition != null) {
            query += " WHERE " + condition;
        }

        if (this.debug) {
            System.out.println("[<DEBUG><Database>]: Query: " + query);
        }

        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(query);

            List<Map<String, String>> records = new ArrayList<>();

            if (result.next()) {
                do {
                    Map<String, String> record = new LinkedHashMap<>();
                    if (columns.size() == 1 && columns.get(0).equals("*")) {
                        var metaData = result.getMetaData();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            record.put(metaData.getColumnName(i), result.getString(i));
                        }
                    } else {
                        for (String column : columns) {
                            record.put(column, result.getString(column));
                        }
                    }
                    records.add(record);
                } while (result.next());
            } else {
                System.out.println("[<DEBUG><Database>]: No records found.");
                return List.of();
            }

            return records;
        } catch (SQLException e) {
            if (this.debug) {
                System.out.println("[<DEBUG><Database>]: Query: " + query);
                e.printStackTrace();
            }
            return List.of();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected boolean update(
            String table,
            List<String> columns,
            List<String> values,
            String condition) {
        String query = "UPDATE " + table + " SET ";
        for (int i = 0; i < columns.size(); i++) {
            query += columns.get(i) + " = ?";
            if (i < columns.size() - 1) {
                query += ", ";
            }
        }
        query += " WHERE " + condition;

        try {
            var statement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                statement.setString(i + 1, values.get(i));
            }
            int status = statement.executeUpdate();

            if (status == 1) {
                if (this.debug) {
                    System.out.println("[<DEBUG><Database>]: Record updated successfully.");
                    System.out.println("[<DEBUG><Database>]: Query: " + query);
                }

                return true;
            } else {
                if (this.debug)
                    System.out.println("[<DEBUG><Database>]: Record update failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected boolean delete(
            String table,
            String condition) {
        String query = "DELETE FROM " + table + " WHERE " + condition;

        try {
            var statement = connection.createStatement();
            int status = statement.executeUpdate(query);

            if (status == 1) {
                if (this.debug) {
                    System.out.println("Record deleted successfully.");
                    System.out.println("Query: " + query);
                }
                return true;
            } else {
                if (this.debug)
                    System.out.println("[<DEBUG><Database>]: Record deletion failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected String[] getColumns(String table) {
        String query = "SHOW COLUMNS FROM " + table;

        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(query);
            List<String> columns = new ArrayList<>();
            while (result.next()) {
                columns.add(result.getString("Field"));
            }
            return columns.toArray(String[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Map<String, String> findByID(String table, int id) {
        List<Map<String, String>> records = this.select(table, List.of("*"), "id = " + id);
        if (records == null) {
            return null;
        }

        return records.get(0);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
