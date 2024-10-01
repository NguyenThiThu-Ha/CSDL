import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingEmployeeService {

    public void listBuildingEmployeesWithSalary(String month) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT be.name, s.salary_amount " +
                       "FROM Building_Employees be " +
                       "JOIN Salary s ON be.employee_id = s.employee_id " +
                       "WHERE s.month = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, month);

        ResultSet rs = stmt.executeQuery();

        System.out.println("Employee Name | Salary");
        System.out.println("-----------------------");

        while (rs.next()) {
            String name = rs.getString("name");
            double salary = rs.getDouble("salary_amount");

            System.out.printf("%-14s | %-12.2f%n", name, salary);
        }
    }
}
