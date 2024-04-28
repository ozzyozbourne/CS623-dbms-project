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
import static project.utils.CustomLogger.logCurrentStateOfTableStock;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Transaction done by Rishabh Gada")
public final class TransactionsRishabh {

    private DB db;
    private Statement STMT;

    private final String TRANSACTION_1_1 = "INSERT INTO Product(prodid, pname, price) VALUES ('p100', 'cd', 5);";
    private final String TRANSACTION_1_2 = "INSERT INTO Stock(prodid, depid, quantity) VALUES ('p100', 'd2', 50);";


    private final String TRANSACTION_2_1 = "INSERT INTO Depot(depid, addr, volume) VALUES ('d100', 'Chicago', 100);";
    private final String TRANSACTION_2_2 = "INSERT INTO Stock(prodid, depid, quantity) VALUES ('p1', 'd100', 100);";


    @Test(description = "Transaction 1")
    void doTransaction1(){
        log("State of tables Product and Stock before transaction 1");
        logCurrentStateOfTableProduct(STMT);
        logCurrentStateOfTableStock(STMT);

        log(TRANSACTION_1_1);
        var res = Try.ThrowSupplier.apply(() -> STMT.execute(TRANSACTION_1_1), SQLException.class);
        assertNull(res.error());
        log("Updated Successfully");

        log(TRANSACTION_1_2);
        res = Try.ThrowSupplier.apply(() -> STMT.execute(TRANSACTION_1_2), SQLException.class);
        assertNull(res.error());
        log("Updated Successfully");

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

        log(TRANSACTION_2_1);
        var res = Try.ThrowSupplier.apply(() -> STMT.execute(TRANSACTION_2_1), SQLException.class);
        assertNull(res.error());
        log("Updated Successfully");

        log(TRANSACTION_2_2);
        res = Try.ThrowSupplier.apply(() -> STMT.execute(TRANSACTION_2_2), SQLException.class);
        assertNull(res.error());
        log("Updated Successfully");

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
    void setUpConnectionSingleton(){
        var init =  DB.INSTANCE; // init variable required, since java doesn't allow _ = DB.INSTANCE
    }

    @AfterSuite(description = "closing connection singleton")
    void commitTransactionAndCloseConnection(){
        this.db.close();
    }


}
