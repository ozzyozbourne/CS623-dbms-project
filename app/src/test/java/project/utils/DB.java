package project.utils;

import project.Try;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import static org.testng.Assert.assertNull;
import static project.Constants.*;
import static project.utils.CustomLogger.log;

public enum DB {
    INSTANCE;
    private final Try.Result<Connection, SQLException> CONN;

    private final Try.Result<Statement, SQLException> stmt;

    DB(){
        this.CONN = Try.ThrowSupplier.apply(() -> DriverManager.getConnection(URL, USER, PASSWORD), SQLException.class);
        checkConnection();
        setAutocommitOff();
        setIsolationToSerializable();
        this.stmt =  createStatement();
    }

    private void checkConnection() {
        log("Checking connection");
        assertNull(CONN.error());
        log("Connection established Successfully");
    }

    private void setAutocommitOff(){
        log("Setting Autocommit off for atomicity");
        assertNull(Try.ThrowConsumer.apply (() -> CONN.value().setAutoCommit(false), SQLException.class).error());
        log("Autocommit turned off successfully");
    }

    private void setIsolationToSerializable(){
        log("Setting isolation level to serializable");
        assertNull(Try.ThrowConsumer.apply (() -> CONN.value().setTransactionIsolation(TRANSACTION_SERIALIZABLE), SQLException.class).error());
        log("Isolation set to serializable successfully");
    }

    private Try.Result<Statement, SQLException> createStatement(){
        log("Setting result-set type");
        final Try.Result<Statement, SQLException> stmt = Try.ThrowSupplier
                .apply(() -> CONN.value().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY), SQLException.class);
        assertNull(stmt.error());
        log("result-set is successfully initialized");
        return stmt;
    }

    public Connection getConnection(){
        return CONN.value();
    }

    public Statement getStatement(){
        return stmt.value();
    }

    public void commitTransactions(){
        log("Commiting transactions");
        assertNull(Try.ThrowConsumer.apply(() -> CONN.value().commit(), SQLException.class).error());
        log("Committed transactions Successfully");
    }

    public void rollbackOnError(final Try.Result<Boolean, SQLException> res, final String runtimeExceptionMessage){
        if(res.error() != null) {
            log("Error: " + res.error());
            log("Rolling back transaction");
            assertNull(Try.ThrowConsumer.apply(() -> CONN.value().rollback(), SQLException.class).error());
            log("Rolled back transaction Successfully");
            throw new RuntimeException(runtimeExceptionMessage);
        }
    }

    public void close(){
        log("Closing connection");
        assertNull(Try.ThrowConsumer.apply(stmt.value()::close, SQLException.class).error());
        assertNull(Try.ThrowConsumer.apply(CONN.value()::close, SQLException.class).error());
        log("Connection Closed Successfully");
    }

    public static String getResultSetString(final ResultSet resultSet) {
        final StringBuilder builder = new StringBuilder();
        try {
            final ResultSetMetaData resultMetaData = resultSet.getMetaData();
            final int columnCount = resultMetaData.getColumnCount();
            final List<Integer> columnSize = new ArrayList<>(columnCount);
            List<String> valuesRow = new ArrayList<>();
            final List<List<String>> valuesCol = new ArrayList<>();

            for(int i = 0; i < columnCount; i++) {
                final String resColName = resultMetaData.getColumnName(i+1).trim();
                columnSize.add(resColName.length());
                valuesRow.add(resColName);
            }

            valuesCol.add(valuesRow);
            int rowCount = 1; //the zeroth row has the tile hence row values will start from 1
            resultSet.beforeFirst();

            while (resultSet.next()) {
                rowCount += 1;
                valuesRow = new ArrayList<>();

                for(int i = 0; i < columnCount; i++) {
                    final String value = resultSet.getString(i + 1).trim();
                    columnSize.set(i,Math.max(value.length(), columnSize.get(i)));
                    valuesRow.add(value);
                }
                valuesCol.add(valuesRow);
            }

            builder.append("\n");
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < columnCount; j++){
                    if(j == columnCount -1 )
                        builder.append(String.format("| %-"+columnSize.get(j)+"s |", valuesCol.get(i).get(j)));
                    else
                        builder.append(String.format("| %-"+columnSize.get(j)+"s ", valuesCol.get(i).get(j)));
                }
                if(i != rowCount -1)builder.append("\n");
            }
        }
        catch (SQLException e) {return "Error:- " + e.getMessage();}
        return builder.toString();
    }
}
