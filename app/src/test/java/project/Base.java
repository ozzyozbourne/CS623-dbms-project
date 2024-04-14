package project;


import io.qameta.allure.Description;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.testng.Assert.assertNull;
import static project.Constants.*;

public abstract class Base {

    protected final Try.Result<Connection, SQLException> result = Try.ThrowSupplier
            .apply(() -> DriverManager.getConnection(URL, USER, PASSWORD), SQLException.class);

    @Description("Checking connection")
    @BeforeTest
    public void setUp() {
        //assertNull(result.error());
    }

    @Description("Closing connection")
    @AfterTest
    public void tearDown() {
        //assertNull(Try.ThrowConsumer.apply(result.value()::close, SQLException.class).error());
    }
}
