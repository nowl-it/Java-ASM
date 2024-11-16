import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TableTUI {
    private final Scanner scanner = new Scanner(System.in);

    private int menuSelect;
    private boolean noMenuShow = false;

    private List<Map<String, String>> records;
    private List<Map<String, String>> currentRecords;
    private List<String> columns;

    private int pageSize = 5;
    private int currentPage = 1;

    public void register(List<Map<String, String>> records, int pageSize) {
        if (records.isEmpty()) {
            System.err.println("No records found");
            return;
        }

        this.pageSize = pageSize;
        this.records = records;

        this.currentRecords = records.subList(Math.min(pageSize * currentPage - pageSize, records.size()),
                Math.min(pageSize * currentPage, records.size()));

        this.columns = new ArrayList<>(records.get(0).keySet());
    }

    public void noMenu() {
        this.noMenuShow = true;
    }

    public void print() {
        do {
            printHeader();
            printRow(columns);
            printHeader();

            for (Map<String, String> record : currentRecords) {
                printRow(record);
                printHeader();
            }

            printInfo();

            if (!this.noMenuShow) {
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
            }
            this.noMenuShow = false;
        } while (this.menuSelect != 0);
    }

    private String getLongestStringColumn(int i) {
        String longestStringColumn = columns.get(i);
        for (Map<String, String> record : currentRecords) {
            if (longestStringColumn.length() < record.get(columns.get(i)).length()) {
                longestStringColumn = record.get(columns.get(i));
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

    private void printRow(Map<String, String> record) {
        for (int i = 0; i < columns.size(); i++) {
            String longestStringColumn = getLongestStringColumn(i);
            String value = record.get(columns.get(i));
            int totalSpaces = longestStringColumn.length() - value.length() + 2;
            System.out.print("|" + " ".repeat((int) (totalSpaces / 2)) + value
                    + " ".repeat(totalSpaces - (int) (totalSpaces / 2)));
            if (i == columns.size() - 1) {
                System.out.println("|");
            }
        }
    }

    private void printInfo() {
        System.out.println("Page " + currentPage + " / " + (int) Math.ceil((double) records.size() / pageSize));
    }

    private void firstPage() {
        currentPage = 1;
        currentRecords = records.subList(Math.min(pageSize * currentPage - pageSize, records.size()),
                Math.min(pageSize * currentPage, records.size()));
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            currentRecords = records.subList(Math.min(pageSize * currentPage - pageSize, records.size()),
                    Math.min(pageSize * currentPage, records.size()));
        }
    }

    private void nextPage() {
        if (currentPage < (int) Math.ceil((double) records.size() / pageSize)) {
            currentPage++;
            currentRecords = records.subList(Math.min(pageSize * currentPage - pageSize, records.size()),
                    Math.min(pageSize * currentPage, records.size()));
        }
    }

    private void lastPage() {
        currentPage = (int) Math.ceil((double) records.size() / pageSize);
        currentRecords = records.subList(Math.min(pageSize * currentPage - pageSize, records.size()),
                Math.min(pageSize * currentPage, records.size()));
    }

    private void menu() throws Exception {
        System.out.println("1. Previous page");
        System.out.println("2. Next page");
        System.out.println("3. First page");
        System.out.println("4. Last page");

        System.out.println("5. Search record");

        System.out.println("6. Search by filter");

        System.out.println("0. Exit");

        System.out.print("Select: ");
        this.menuSelect = scanner.nextInt();
    }
}
