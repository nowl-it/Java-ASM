
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Information {
    private final Scanner scanner = new Scanner(System.in);

    private final TableTUI tableTUI = new TableTUI();

    private final Validate validate = new Validate();

    private final String[] options = {
            "1. View records",
            "2. Add record",
            "3. Update record",
            "4. Delete record",
            "5. Exit"
    };

    public Database db = new Database(
            "jdbc:mysql://localhost:3306/programming_asm",
            "root",
            "", true);

    public Information() {
        db.connect();
    }

    public void menu() {
        int choice;

        System.out.println("Welcome to the Employees Information System");

        do {
            System.out.println("Choose an option:");

            for (String option : options) {
                System.out.println(option);
            }

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> this.viewRecords();
                case 2 -> this.addRecord();
                case 3 -> this.updateRecord();
                case 4 -> this.deleteRecord();
                case 5 -> {
                    System.out.println("Exiting...");
                    this.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }

    private void viewRecords() {
        List<Map<String, String>> data = db.select("employees", List.of("*"), null);

        if (data.isEmpty()) {
            System.out.println("No records found");
        } else {
            tableTUI.register(data, 5);

            tableTUI.print();
        }
    }

    private void addRecord() {
        String[] ignores = { "ID" };

        String[] specialColumns = { "gender", "email", "birthDate", "status" };
        String[] specialValues = { "M (Male)/F (Female)", "example@gmail.com", "YYYY-MM-DD", "Working/Resigned" };

        String[] columns = db.getColumns("employees");
        String[] values;

        for (String ignore : ignores) {
            columns = List.of(columns).stream().filter(column -> !column.equals(ignore)).toArray(String[]::new);
        }

        values = new String[columns.length];

        scanner.nextLine();

        for (int i = 0; i < columns.length; i++) {
            while (true) {
                if (List.of(specialColumns).contains(columns[i])) {
                    int index = List.of(specialColumns).indexOf(columns[i]);
                    System.out.print("Enter " + columns[i] + " (" + specialValues[index] + "): ");
                } else {
                    System.out.print("Enter " + columns[i] + ": ");
                }
                values[i] = scanner.nextLine();
                if (!values[i].isEmpty()) {
                    if (!List.of(specialColumns).contains(columns[i]))
                        break;
                    if (validate.validate(values[i], columns[i]))
                        break;
                    System.out.println("Invalid " + columns[i] + ". Please enter a valid value.");
                } else {
                    System.out.println(columns[i] + " cannot be empty. Please enter a value.");
                }
            }
        }

        boolean done = db.insert("employees", List.of(columns), List.of(values));

        if (done) {
            System.out.println("Record added successfully");
        } else {
            System.out.println("Failed to add record");
        }
    }

    private void updateRecord() {
        String[] ignores = { "ID" };

        System.out.print("Enter ID of record to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Map<String, String> finded = db.findByID("employees", id);

        if (finded == null) {
            System.out.println("Record not found");
            return;
        }

        System.out.println("Record found (id: " + id + "):");

        tableTUI.register(
                List.of(finded),
                5);

        tableTUI.noMenu();

        tableTUI.print();

        String[] columns = db.getColumns("employees");
        String[] values;

        for (String ignore : ignores) {
            columns = List.of(columns).stream().filter(column -> !column.equals(ignore)).toArray(String[]::new);
        }

        values = new String[columns.length];

        for (int i = 0; i < columns.length; i++) {
            System.out.print("Enter " + columns[i] + ": ");
            values[i] = scanner.nextLine();
        }

        boolean done = db.update("employees", List.of(columns), List.of(values), "id = " + id);

        if (done) {
            System.out.println("Record updated successfully");
        } else {
            System.out.println("Failed to update record");
        }
    }

    private void deleteRecord() {
        System.out.print("Enter ID of record to delete: ");
        int id = scanner.nextInt();

        boolean done = db.delete("employees", "id = " + id);

        if (done) {
            System.out.println("Record deleted successfully");
        } else {
            System.out.println("Failed to delete record");
        }
    }

    private void close() {
        db.close();
        scanner.close();
    }
}
