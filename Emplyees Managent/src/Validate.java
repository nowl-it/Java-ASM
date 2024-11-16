public class Validate {
    public boolean validate(String value, String column) {
        switch (column) {
            case "gender":
                return value.equals("M") || value.equals("F");
            case "email":
                return value.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
            case "birthDate":
                return value.matches("^\\d{4}-\\d{2}-\\d{2}$");
            case "status":
                return value.equals("Working") || value.equals("Resigned");
            default:
                return false;
        }
    }
}
