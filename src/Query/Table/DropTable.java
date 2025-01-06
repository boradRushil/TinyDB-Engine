package Query.Table;

import Utils.TableUtils;
import Log.EventLog;
import Log.QueryLog;
import Log.GeneralLog;

import java.io.File;

import static Query.Database.UseDatabase.getCurrentDatabase;
import static Query.Table.DatabaseTableValidator.validateDropTable;
import static Utils.ColorConstraint.*;
import static Utils.DatabaseUtils.getTotalRecords;

/**
 * Handles the dropping of database tables.
 */
public class DropTable {
    /**
     * Drops a table based on the provided query.
     *
     * @param query The SQL DROP TABLE query.
     */
    public static void drop(String query) {
        long startTime = System.currentTimeMillis(); // Log start time here

        if (!TableUtils.isDatabaseSelected()) {
            System.out.println(ANSI_RED + "No database selected." + ANSI_RESET);
            logQueryAndState(query, "No database selected", startTime);
            return;
        }

        String[] parts = query.split(" ", 3);
        if (validateDropTable(parts)) {
            String tableName = parts[2];
            File tableFile = new File("./databases/" + getCurrentDatabase() + "/" + tableName + ".txt");

            if (!tableFile.exists()) {
                System.out.println(ANSI_RED + "Table " + tableName + " does not exist." + ANSI_RESET);
                logQueryAndState(query, "Attempted to drop table " + tableName + ", but it does not exist.", startTime);
                return;
            }

            if (tableFile.delete()) {
                System.out.println(ANSI_GREEN + "Table " + tableName + " dropped successfully." + ANSI_RESET);
                logQueryAndState(query, "Table " + tableName + " dropped successfully.", startTime);
            } else {
                System.out.println(ANSI_RED + "Failed to drop table " + tableName + "." + ANSI_RESET);
                logQueryAndState(query, "Failed to drop table " + tableName + ".", startTime);
            }
        } else {
            System.out.println(ANSI_RED + "Invalid DROP TABLE query." + ANSI_RESET);
            logQueryAndState(query, "Invalid DROP TABLE query: " + query, startTime);
        }
    }

    /**
     * Logs the query and its state.
     *
     * @param query   The SQL query.
     * @param message The message to log.
     * @param startTime The start time of the operation.
     */
    private static void logQueryAndState(String query, String message, long startTime) {
        long endTime = System.currentTimeMillis();
        String databaseName = getCurrentDatabase();
        int numberOfTables = TableUtils.getNumberOfTables(databaseName);
        int totalRecords = getTotalRecords(new File("./databases/" + databaseName));

        GeneralLog.log(query, endTime - startTime, numberOfTables, totalRecords);
        QueryLog.logUserQuery(query, startTime);
        EventLog.logDatabaseChange(message);
    }
}
