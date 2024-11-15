import java.util.List;
import java.util.Scanner;

public class TableTUI {
    private Scanner scanner = new Scanner(System.in);

    private int menuSelect;
    private final List<Employee> employees;
    private List<Employee> currentEmployees;
    private List<String> columns = List.of("ID", "Employee ID", "First Name", "Last Name", "Gender", "Address", "Email",
            "Department", "Birth Date", "Status", "Salary Coefficient", "Salary");

    private int pageSize = 5;
    private int currentPage = 1;

    public TableTUI(List<Employee> employees, int pageSize) {
        this.pageSize = pageSize;
        this.employees = employees;

        if (employees.isEmpty()) {
            return;
        }

        this.currentEmployees = employees.subList(Math.min(pageSize * currentPage - pageSize, employees.size()),
                Math.min(pageSize * currentPage, employees.size()));
    }

    public void print() {
        do {
            printHeader();
            printRow(columns);
            printHeader();

            for (Employee employee : currentEmployees) {
                printRow(employee.getAllValues());
                printHeader();
            }

            printInfo();

            try {
                menu();
            } catch (Exception e) {
                System.out.println("Invalid input, auto exit");
            }
            switch (this.menuSelect) {
                case 1 -> firstPage();

                case 2 -> previousPage();

                case 3 -> nextPage();

                case 4 -> lastPage();

                default -> {
                }
            }
        } while (menuSelect != 0);
    }

    private String getLongestStringColumn(int i) {
        String longestStringColumn = columns.get(i);
        for (Employee employee : currentEmployees) {
            if (longestStringColumn.length() < employee.getValueFromKey(columns.get(i)).length()) {
                longestStringColumn = employee.getValueFromKey(columns.get(i));
            }
        }

        return longestStringColumn;
    }

    private void printHeader() {
        for (int i = 0; i < columns.size(); i++) {
            String longestStringColumn = getLongestStringColumn(i);
            System.out.print("+" + "-".repeat(longestStringColumn.length() + 2));
            if (i == columns.size() - 1) {
                System.out.println("+");
            }
        }
    }

    private void printRow(List<String> columns) {
        for (int i = 0; i < columns.size(); i++) {
            String longestStringColumn = getLongestStringColumn(i);
            int totalSpaces = longestStringColumn.length() - columns.get(i).length() + 2;
            System.out.print("|" + " ".repeat((int) (totalSpaces / 2)) + columns.get(i)
                    + " ".repeat(totalSpaces - (int) (totalSpaces / 2)));
            if (i == columns.size() - 1) {
                System.out.println("|");
            }
        }
    }

    private void printInfo() {
        System.out.println("Page " + currentPage + " / " + (int) Math.ceil((double) employees.size() / pageSize));
    }

    private void firstPage() {
        currentPage = 1;
        currentEmployees = employees.subList(Math.min(pageSize * currentPage - pageSize, employees.size()),
                Math.min(pageSize * currentPage, employees.size()));
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            currentEmployees = employees.subList(Math.min(pageSize * currentPage - pageSize, employees.size()),
                    Math.min(pageSize * currentPage, employees.size()));
        }
    }

    private void nextPage() {
        if (currentPage < (int) Math.ceil((double) employees.size() / pageSize)) {
            currentPage++;
            currentEmployees = employees.subList(Math.min(pageSize * currentPage - pageSize, employees.size()),
                    Math.min(pageSize * currentPage, employees.size()));
        }
    }

    private void lastPage() {
        currentPage = (int) Math.ceil((double) employees.size() / pageSize);
        currentEmployees = employees.subList(Math.min(pageSize * currentPage - pageSize, employees.size()),
                Math.min(pageSize * currentPage, employees.size()));
    }

    @SuppressWarnings("unused")
    private void addEmployee() {

    }

    private void menu() throws Exception {
        System.out.println("1. First page");
        System.out.println("2. Previous page");
        System.out.println("3. Next page");
        System.out.println("4. Last page");

        System.out.println("5. Add new employee");
        System.out.println("6. Edit employee");
        System.out.println("7. Delete employee");

        System.out.println("8. Search employee");

        System.out.println("9. Search by filter");

        System.out.println("0. Exit");

        System.out.print("Select: ");
        this.menuSelect = scanner.nextInt();
    }
}
