import java.util.List;

public class Employee {
    public int ID;
    public int employeeID;
    public String firstName;
    public String lastName;
    public EmployeeGender gender;
    public String address;
    public String email;
    public String department;
    public String birthDate;
    public EmployeeStatus status;
    public double salaryCoefficient;
    public double salary;

    public enum EmployeeGender {
        MALE,
        FEMALE,
    };

    public enum EmployeeStatus {
        WORKING,
        RESIGNED,
    };

    public Employee(
            int ID,
            int employeeID,
            String firstName,
            String lastName,
            EmployeeGender gender,
            String address,
            String email,
            String department,
            String birthDate,
            EmployeeStatus status,
            double salaryCoefficient,
            double salary) {
        this.ID = ID;
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.department = department;
        this.birthDate = birthDate;
        this.status = status;
        this.salaryCoefficient = salaryCoefficient;
        this.salary = salary;
    }

    public String getValueFromKey(String key) {
        return switch (key) {
            case "ID" -> String.valueOf(ID);
            case "Employee ID" -> String.valueOf(employeeID);
            case "First Name" -> firstName;
            case "Last Name" -> lastName;
            case "Gender" -> gender.toString();
            case "Address" -> address;
            case "Email" -> email;
            case "Department" -> department;
            case "Birth Date" -> birthDate;
            case "Status" -> status.toString();
            case "Salary Coefficient" -> String.valueOf(salaryCoefficient);
            case "Salary" -> String.valueOf(salary);
            default -> "";
        };
    }

    public List<String> getAllValues() {
        return List.of(
                String.valueOf(ID),
                String.valueOf(employeeID),
                firstName,
                lastName,
                gender.toString(),
                address,
                email,
                department,
                birthDate,
                status.toString(),
                String.valueOf(salaryCoefficient),
                String.valueOf(salary));
    }
}
