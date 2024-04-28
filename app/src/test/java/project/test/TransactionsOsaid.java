package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import project.Try;
import project.utils.DB;

import java.sql.SQLException;
import java.sql.Statement;

import static org.testng.Assert.assertNull;
import static project.utils.CustomLogger.*;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Transactions done by Osaid Khan")
public final class TransactionsOsaid {

    private DB db;
    private Statement STMT;

    private final String TRANSACTION_1 = "DELETE FROM Product WHERE prodid = 'p1';";
    private final String TRANSACTION_2 = "DELETE FROM Depot WHERE depid = 'd1';";

    @Test(description = "Transaction 1")
    void doTransaction1(){
        log("State of tables Product and Stock before transaction 1");
        logCurrentStateOfTableProduct(STMT);
        logCurrentStateOfTableStock(STMT);

        log(TRANSACTION_1);
        var res = Try.ThrowSupplier.apply(() -> STMT.execute(TRANSACTION_1), SQLException.class);
        assertNull(res.error());
        log("Deleted Successfully");

        this.db.commitTransactions();
        log("State of tables Product and Stock after transaction 1");
        logCurrentStateOfTableProduct(STMT);
        logCurrentStateOfTableStock(STMT);
    }

    @Test(description = "Transaction 2")
    void doTransaction2(){
        log("State of tables Depot and Stock before transaction 2");
        logCurrentStateOfTableDepot(STMT);
        logCurrentStateOfTableStock(STMT);

        log(TRANSACTION_2);
        var res = Try.ThrowSupplier.apply(() -> STMT.execute(TRANSACTION_2), SQLException.class);
        assertNull(res.error());
        log("Deleted Successfully");

        this.db.commitTransactions();
        log("State of tables Depot and Stock after transaction 2");
        logCurrentStateOfTableDepot(STMT);
        logCurrentStateOfTableStock(STMT);
    }

    @BeforeTest(description = "Feeding test instances reference to singleton connection")
    void setUpTestClass(){
        this.db = DB.INSTANCE;
        this.STMT = db.getStatement();
    }

    @BeforeSuite(description = "Setting up connection singleton")
    final void setUpConnectionSingleton(){
        var init =  DB.INSTANCE; // init variable required, since java doesn't allow _ = DB.INSTANCE
    }

    @AfterSuite(description = "closing connection singleton")
    void commitTransactionAndCloseConnection(){
        this.db.close();
    }

}
