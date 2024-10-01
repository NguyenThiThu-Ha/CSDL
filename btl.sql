CREATE DATABASE office_building;
USE office_building;

-- Bảng Companies
CREATE TABLE Companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    tax_code VARCHAR(20) NOT NULL,
    capital DECIMAL(18, 2),
    industry VARCHAR(255),
    employee_count INT,
    address VARCHAR(255),
    phone VARCHAR(50),
    office_space DECIMAL(10, 2)
);

-- Bảng Employees_Company
CREATE TABLE Employees_Company (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT,
    id_card VARCHAR(20),
    name VARCHAR(255),
    birth_date DATE,
    phone VARCHAR(50),
    FOREIGN KEY (company_id) REFERENCES Companies(company_id)
);

-- Bảng Services
CREATE TABLE Services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255),
    service_type VARCHAR(50),
    base_price DECIMAL(18, 2)
);

-- Bảng Building_Employees
CREATE TABLE Building_Employees (
    building_employee_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    birth_date DATE,
    address VARCHAR(255),
    phone VARCHAR(50),
    grade VARCHAR(50),
    position VARCHAR(255)
);

-- Bảng Company_Services
CREATE TABLE Company_Services (
    service_usage_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT,
    service_id INT,
    register_date DATE,
    end_date DATE,
    FOREIGN KEY (company_id) REFERENCES Companies(company_id),
    FOREIGN KEY (service_id) REFERENCES Services(service_id)
);

-- Bảng Access_Records
CREATE TABLE Access_Records (
    access_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    access_time TIMESTAMP,
    access_point VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES Employees_Company(employee_id)
);

-- Bảng Salary
CREATE TABLE Salary (
    salary_id INT AUTO_INCREMENT PRIMARY KEY,
    building_employee_id INT,
    service_id INT,
    month INT,
    salary_amount DECIMAL(18, 2),
    FOREIGN KEY (building_employee_id) REFERENCES Building_Employees(building_employee_id),
    FOREIGN KEY (service_id) REFERENCES Services(service_id)
);

-- Insert dữ liệu
INSERT INTO Companies (company_name, tax_code, capital, industry, employee_count, address, phone, office_space)
VALUES ('Company A', '123456789', 10000000, 'IT', 50, 'Floor 2, Room 204', '0123456789', 150);

INSERT INTO Services (service_name, service_type, base_price)
VALUES ('Cleaning', 'Mandatory', 500), ('Security', 'Mandatory', 800), ('Parking', 'Optional', 200);

INSERT INTO Building_Employees (name, birth_date, address, phone, grade, position)
VALUES ('Nguyen Van B', '1990-01-01', '123 Main St', '0987654321', 'Level 1', 'Security Manager');

INSERT INTO Employees_Company (company_id, id_card, name, birth_date, phone)
VALUES (1, '123456789', 'Tran Van C', '1985-05-05', '0912345678');

-----
SELECT c.company_name, 
       (c.office_space * 1000) AS office_rent, 
       SUM(cs.service_usage_id * s.base_price) AS total_service_cost
FROM Companies c
LEFT JOIN Company_Services cs ON c.company_id = cs.company_id
LEFT JOIN Services s ON cs.service_id = s.service_id
GROUP BY c.company_id
ORDER BY (c.office_space * 1000 + SUM(cs.service_usage_id * s.base_price)) DESC;
-----
SELECT e.name, COUNT(a.access_id) AS access_count, a.access_point
FROM Employees_Company e
LEFT JOIN Access_Records a ON e.employee_id = a.employee_id
WHERE a.access_time >= CURDATE()
GROUP BY e.employee_id, a.access_point;
-----
SELECT be.name, s.salary_amount
FROM Building_Employees be
LEFT JOIN Salary s ON be.building_employee_id = s.building_employee_id
WHERE s.month = MONTH(CURDATE());