package Assessment2;

public class CredentialUpdater {
    public static void main(String[] args) {
        // This is a simple utility class to help with credential management

        javax.swing.JOptionPane.showMessageDialog(null,
                "MySQL Credential Checker\n\n" +
                        "Use this information to update your database connection:\n\n" +
                        "1. In DatabaseManager.java, update these lines:\n" +
                        "   private static final String DB_USER = \"root\";  // Your MySQL username\n" +
                        "   private static final String DB_PASS = \"\";     // Your MySQL password (empty string if none)\n\n" +
                        "2. Also change the connection URL to add allowPublicKeyRetrieval=true:\n" +
                        "   connection = DriverManager.getConnection(\n" +
                        "       DB_URL + \"?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true\",\n" +
                        "       DB_USER,\n" +
                        "       DB_PASS\n" +
                        "   );\n\n" +
                        "After making these changes, rebuild and run HangmanGame.java again.",
                "Credentials Update Guide",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}