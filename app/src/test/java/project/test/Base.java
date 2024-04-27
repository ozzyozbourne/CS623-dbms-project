package project.test;

import org.testng.annotations.*;
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

abstract sealed class Base permits ConnectionTest,
                                   DeleteData,
                                   PopulateData,
                                   Transactions,
                                   CreateTables,
                                   DropTables{

    final Try.Result<Connection, SQLException> CONN = Try.ThrowSupplier
            .apply(() -> DriverManager.getConnection(URL, USER, PASSWORD), SQLException.class);

    Try.Result<Statement, SQLException> stmt;

    @BeforeSuite(description ="Checking connection and statement" )
    final void checkConnection() {
        log("Checking connection");
        assertNull(CONN.error());
        log("Connection established Successfully");
    }

    @BeforeTest(description = "Setting up connection")
    final void setUpTest(){
        log("Setting Autocommit off for atomicity");
        var res = Try
                .ThrowConsumer
                .apply (() -> CONN.value().setAutoCommit(false), SQLException.class);
        assertNull(res.error());
        log("Autocommit turned off successfully");
        log("Setting isolation level to serializable");
        res = Try
                .ThrowConsumer
                .apply (() -> CONN.value().setTransactionIsolation(TRANSACTION_SERIALIZABLE), SQLException.class);
        assertNull(res.error());
        log("Isolation set to serializable successfully");
        log("Setting result-set type");
        stmt = Try
                .ThrowSupplier
                .apply(() -> CONN.value().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY), SQLException.class);
        assertNull(stmt.error());
        log("result-set is successfully initialized");

    }

    @AfterTest(description = "Commiting the transactions")
    void commitTransactions(){
        log("Commiting transactions");
        assertNull(Try.ThrowConsumer.apply(() -> CONN.value().commit(), SQLException.class).error());
        log("Committed transactions Successfully");
    }

    @AfterSuite(description = "Closing connection")
    final void tearDown() {
        log("Closing connection");
        assertNull(Try.ThrowConsumer.apply(CONN.value()::close, SQLException.class).error());
        log("Connection Closed Successfully");
    }

    void checkForErrors(final Try.Result<Boolean, SQLException> res, final String runtimeExceptionMessage){
        if(res.error() != null || res.value()){
            if(res.error() != null) log("Error: " + res.error());
            else log("statement returned true indicating a result-set for create type query");
            log("Rolling back transaction");
            assertNull(Try.ThrowConsumer.apply(() -> CONN.value().rollback(), SQLException.class).error());
            log("Rolled back transaction Successfully");
            throw new RuntimeException(runtimeExceptionMessage);
        }
    }
}
