package project;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.testng.Assert.*;
import static project.Constants.*;

@Epic("CS 623 Project")
public class ConnectionTest {

    private final Try.Result<Connection, SQLException> result = Try.ThrowSupplier
            .apply(() -> DriverManager.getConnection(URL, USER, PASSWORD), SQLException.class);


    @Feature("Transaction 1 By Osaid Khan")
    @Test
    public void doTransaction1(){}

    @Feature("Transaction 2 By Osaid Khan")
    @Test
    public void doTransaction2(){}

    @Feature("Transaction 3 By Rishabh Gada")
    @Test
    public void doTransaction3(){}

    @Feature("Transaction 4 By Rishabh Gada")
    @Test
    public void doTransaction4(){}

    @Feature("Transaction 5 By Sharukh Saiyed")
    @Test
    public void doTransaction5(){}

    @Feature("Transaction 6 By Sharukh Saiyed")
    @Test
    public void doTransaction6(){}

    @Feature("Connecting to the database")
    @BeforeTest
    public void setUp() {assertNull(result.error());}

    @Feature("Cleaning up")
    @AfterTest
    public void tearDown() {assertNull(Try.ThrowConsumer.apply(result.value()::close, SQLException.class).error());}
}
