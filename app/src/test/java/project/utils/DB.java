package project.utils;

import project.Try;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import static org.testng.Assert.assertNull;
import static project.Constants.*;
import static project.utils.CustomLogger.log;

public final class DB {

    private final Try.Result<Connection, SQLException> CONN;

    private final Try.Result<Statement, SQLException> stmt;

    public DB(){
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
}
