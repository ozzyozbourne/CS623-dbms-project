package project.test;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import project.Try;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;
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


    @BeforeSuite(description ="Checking connection" )
    final void setUp() {
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

    }

    @AfterSuite(description = "Closing connection")
    final void tearDown() {
        log("Closing connection");
        assertNull(Try.ThrowConsumer.apply(CONN.value()::close, SQLException.class).error());
        log("Connection Closed Successfully");
    }
}
