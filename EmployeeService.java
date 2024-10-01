import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeService {

    public void listEmployeeAccessRecords(String date) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT e.name, COUNT(ar.entry_time) AS access_count, ar.access_point " +
                       "FROM Employees_Company e " +
                       "JOIN Access_Records ar ON e.employee_id = ar.employee_id " +
                       "WHERE DATE(ar.entry_time) = ? " +
                       "GROUP BY e.employee_id, ar.access_point";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, date); 

        ResultSet rs = stmt.executeQuery();

        System.out.println("Employee Name | Access Count | Access Point");
        System.out.println("-------------------------------------------");

        while (rs.next()) {
            String name = rs.getString("name");
            int accessCount = rs.getInt("access_count");
            String accessPoint = rs.getString("access_point");

            System.out.printf("%-14s | %-12d | %-12s%n", name, accessCount, accessPoint);
        }
    }
}
