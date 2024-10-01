import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CompanyService companyService = new CompanyService();
        EmployeeService employeeService = new EmployeeService();
        BuildingEmployeeService buildingEmployeeService = new BuildingEmployeeService();

        while (true) {
            System.out.println("===== OFFICE BUILDING MANAGEMENT =====");
            System.out.println("1. Xem thông tin các công ty và tổng chi phí");
            System.out.println("2. Xem thông tin nhân viên và số lần ra/vào tòa nhà");
            System.out.println("3. Xem thông tin nhân viên tòa nhà và lương tháng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    try {
                        companyService.listCompaniesWithTotalCost();
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Nhập ngày (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    try {
                        employeeService.listEmployeeAccessRecords(date);
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Nhập tháng (YYYY-MM): ");
                    String month = scanner.nextLine();
                    try {
                        buildingEmployeeService.listBuildingEmployeesWithSalary(month);
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }

            System.out.println();
        }
    }
}
