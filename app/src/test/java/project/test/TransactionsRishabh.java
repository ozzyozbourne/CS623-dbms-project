package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import project.utils.DB;

import java.sql.Statement;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Transaction done by Rishabh Gada")
public final class TransactionsRishabh {

    private DB db;
    private Statement STMT;

    private final String TRANSACTION_1 = "DELETE FROM Depot WHERE depid = 'd1';";
    private final String TRANSACTION_2 = "DELETE FROM Depot WHERE depid = 'd1';";


    @Test(description = "Transaction 1")
    void doTransaction1(){


    }

    @Test(description = "Transaction 2")
    void doTransaction2(){


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
