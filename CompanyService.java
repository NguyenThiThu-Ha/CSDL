import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyService {

    public void listCompaniesWithTotalCost() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT c.name, (c.rent_area * 100) AS rent_cost, SUM(cs.service_cost) AS total_service_cost, " +
                       "(c.rent_area * 100 + SUM(cs.service_cost)) AS total_cost " +
                       "FROM Companies c " +
                       "JOIN Company_Services cs ON c.company_id = cs.company_id " +
                       "GROUP BY c.company_id ORDER BY total_cost DESC";

        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Company Name | Rent Cost | Service Cost | Total Cost");
        System.out.println("---------------------------------------------------");

        while (rs.next()) {
            String name = rs.getString("name");
            double rentCost = rs.getDouble("rent_cost");
            double serviceCost = rs.getDouble("total_service_cost");
            double totalCost = rs.getDouble("total_cost");

            System.out.printf("%-12s | %-10.2f | %-12.2f | %-10.2f%n", name, rentCost, serviceCost, totalCost);
        }
    }
}
