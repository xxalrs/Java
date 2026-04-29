package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static Connection connection = null;
    private static String url;
    private static String user;
    private static String password;

    public static void init(String configPath) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
        }
        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        if (url == null || user == null || password == null) {
            throw new IOException("Не всі параметри підключення задані в файлі.");
        }
    }

    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Помилка закриття з'єднання: " + e.getMessage());
            }
        }
    }

    public static void saveOrUpdate(EmployeeEntry entry) {
        Employee emp = entry.getEmployee();
        int quantity = entry.getQuantity();
        String sql = "INSERT INTO employees (employee_id, type, name, salary, position, email, age, " +
                "contract_duration, bonus, university, internship_duration, remote_tools, hourly_rate, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (employee_id) DO UPDATE SET " +
                "quantity = EXCLUDED.quantity, " +
                "name = EXCLUDED.name, " +
                "salary = EXCLUDED.salary, " +
                "position = EXCLUDED.position, " +
                "email = EXCLUDED.email, " +
                "age = EXCLUDED.age, " +
                "contract_duration = EXCLUDED.contract_duration, " +
                "bonus = EXCLUDED.bonus, " +
                "university = EXCLUDED.university, " +
                "internship_duration = EXCLUDED.internship_duration, " +
                "remote_tools = EXCLUDED.remote_tools, " +
                "hourly_rate = EXCLUDED.hourly_rate";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, emp.getId());
            pstmt.setString(2, getType(emp));
            pstmt.setString(3, emp.getName());
            pstmt.setDouble(4, emp.getSalary());
            pstmt.setString(5, emp.getPosition().toString());
            pstmt.setString(6, emp.getEmail());
            pstmt.setInt(7, emp.getAge());

            if (emp instanceof ContractEmployee) {
                ContractEmployee c = (ContractEmployee) emp;
                pstmt.setInt(8, c.getContractDuration());
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.VARCHAR);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
                pstmt.setNull(13, Types.DOUBLE);
            } else if (emp instanceof FullTimeEmployee) {
                FullTimeEmployee f = (FullTimeEmployee) emp;
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setDouble(9, f.getBonus());
                pstmt.setNull(10, Types.VARCHAR);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
                pstmt.setNull(13, Types.DOUBLE);
            } else if (emp instanceof InternEmployee) {
                InternEmployee i = (InternEmployee) emp;
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setString(10, i.getUniversity());
                pstmt.setInt(11, i.getInternshipDuration());
                pstmt.setNull(12, Types.VARCHAR);
                pstmt.setNull(13, Types.DOUBLE);
            } else if (emp instanceof RemoteEmployee) {
                RemoteEmployee r = (RemoteEmployee) emp;
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.VARCHAR);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setString(12, r.getRemoteTools());
                pstmt.setDouble(13, r.getHourlyRate());
            } else {
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.VARCHAR);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
                pstmt.setNull(13, Types.DOUBLE);
            }
            pstmt.setInt(14, quantity);

            pstmt.executeUpdate();
            System.out.println("Запис збережено в БД: " + emp.getName() + " (id=" + emp.getId() + ")");
        } catch (SQLException e) {
            System.err.println("Помилка збереження в БД: " + e.getMessage());
        }
    }

    private static String getType(Employee emp) {
        if (emp instanceof ContractEmployee) return "Contract";
        if (emp instanceof FullTimeEmployee) return "FullTime";
        if (emp instanceof InternEmployee) return "Intern";
        if (emp instanceof RemoteEmployee) return "Remote";
        return "Employee";
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}