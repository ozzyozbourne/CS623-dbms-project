package project.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import project.Try;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.testng.Assert.assertNull;
import static project.Constants.*;

abstract sealed class Base permits ConnectionTest,
                                   DeleteData,
                                   PopulateData,
                                   Transactions {

    final Try.Result<Connection, SQLException> CONN = Try.ThrowSupplier
            .apply(() -> DriverManager.getConnection(URL, USER, PASSWORD), SQLException.class);


    @BeforeTest(description ="Checking connection" )
    final void setUp() {
        assertNull(CONN.error());
    }

    @AfterTest(description = "Closing connection")
    final void tearDown() {
        assertNull(Try.ThrowConsumer.apply(CONN.value()::close, SQLException.class).error());
    }
}
