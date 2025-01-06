    package Query.Table;

    import Utils.RegexPatterns;
    import Utils.TableUtils;
    import Log.EventLog;
    import Log.QueryLog;
    import Log.GeneralLog;

    import java.io.File;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.regex.Matcher;

    import static Query.Database.UseDatabase.getCurrentDatabase;
    import static Query.Table.DatabaseTableValidator.isValidCreateTableQuery;
    import static Utils.ColorConstraint.*;
    import static Utils.DatabaseUtils.getTotalRecords;

    /**
     * Handles the creation of database tables.
     */
    public class CreateTable {
        /**
         * Creates a table based on the provided query.
         *
         * @param query The SQL CREATE TABLE query.
         */
        public static void create(String query) {
            long startTime = System.currentTimeMillis();
            if (!TableUtils.isDatabaseSelected()) {
                System.out.println(ANSI_RED + "No database selected." + ANSI_RESET);
                logQueryAndState(query, "No database selected", startTime);
                return;
            }

            if (isValidCreateTableQuery(query)) {
                Matcher matcher = RegexPatterns.CREATE_TABLE_PATTERN.matcher(query);

                if (matcher.matches()) {
                    String tableName = matcher.group(1);
                    String columnsDefinition = matcher.group(2).replaceAll("\n", "").replaceAll("\r", "").trim();

                    File tableFile = new File("./databases/" + getCurrentDatabase() + "/" + tableName + ".txt");
                    if (!tableFile.exists()) {
                        // Handle foreign key constraint setup
                        ForeignKeyHandler.handleForeignKey(tableName, columnsDefinition);
                    } else {
                        System.out.println(ANSI_RED + "Table " + tableName + " already exists." + ANSI_RESET);
                        logQueryAndState(query, "Attempted to create table " + tableName + ", but it already exists.", startTime);
                    }
                } else {
                    System.out.println(ANSI_RED + "Invalid CREATE TABLE query format." + ANSI_RESET);
                    logQueryAndState(query, "Invalid CREATE TABLE query format: " + query, startTime);
                }
            } else {
                System.out.println(ANSI_RED + "Invalid query. Primary key not specified in the CREATE TABLE statement." + ANSI_RESET);
                logQueryAndState(query, "Invalid CREATE TABLE query. Primary key not specified: " + query, startTime);
            }
        }

        /**
         * Creates a table with foreign key constraints.
         *
         * @param tableName        The name of the table to create.
         * @param columnsDefinition The columns definition string.
         */
        public static void createTableWithForeignKey(String tableName, String columnsDefinition) {
            long startTime = System.currentTimeMillis();
            File tableFile = new File("./databases/" + getCurrentDatabase() + "/" + tableName + ".txt");

            try (FileWriter writer = new FileWriter(tableFile)) {
                writer.write(columnsDefinition);
                System.out.println(ANSI_GREEN + "Table " + tableName + " created successfully with columns: " + columnsDefinition + ANSI_RESET);
                logQueryAndState("CREATE TABLE " + tableName + " (" + columnsDefinition + ")", "Table " + tableName + " created successfully with columns: " + columnsDefinition, startTime);
            } catch (IOException e) {
                System.out.println(ANSI_RED + "An error occurred while creating the table." + ANSI_RESET);
                e.printStackTrace();
                logQueryAndState("CREATE TABLE " + tableName + " (" + columnsDefinition + ")", "Error occurred while creating table " + tableName + ": " + e.getMessage(), startTime);
            }
        }

        /**
         * Logs the query and its state.
         *
         * @param query   The SQL query.
         * @param message The message to log.
         */
        private static void logQueryAndState(String query, String message,long startTime) {
            long endTime = System.currentTimeMillis();
            String databaseName = getCurrentDatabase();
            if (databaseName == null) {
                return;
            }
            int numberOfTables = TableUtils.getNumberOfTables(databaseName);
            System.out.println(databaseName);
            int totalRecords = getTotalRecords(new File("./databases/" + databaseName));

            GeneralLog.log(query,endTime - startTime, numberOfTables, totalRecords);
            QueryLog.logUserQuery(query, startTime);
            EventLog.logDatabaseChange(message);
        }
    }