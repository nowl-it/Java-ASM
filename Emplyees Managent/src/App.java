import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        int pageSize = 5;
        if (args.length > 0) {
            pageSize = Integer.parseInt(args[0]);
        }

        List<Employee> employees = new ArrayList<>(
                List.of(
                        new Employee(1, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(2, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(3, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(4, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(5, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(6, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(7, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(8, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(9, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(10, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(11, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(12, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000),
                        new Employee(13, 1, "Nguyen", "Van A", Employee.EmployeeGender.MALE,
                                "123 Nguyen Luong Bang", "abc@gmail.com", "IT", "01/01/1990",
                                Employee.EmployeeStatus.WORKING,
                                2.0, 2000)));

        TableTUI dataTable = new TableTUI(employees, pageSize);
        dataTable.print();
    }
}
