package project.test;

import org.testng.annotations.*;
import project.utils.DB;

import java.sql.Connection;
import java.sql.Statement;


abstract sealed class Base permits ConnectionTest,
                                   DeleteData,
                                   PopulateData,
                                   Transactions,
                                   CreateTables,
                                   DropTables{
    DB db;
    Connection CONN;
    Statement STMT;

    @BeforeSuite(description = "Setting up connection singleton")
    final void setUpConnectionSingleton(){
        var init =  DB.INSTANCE; // init variable required to initialise singleton, since java has no _ = DB.INSTANCE option
    }

    @BeforeTest(description = "Feeding test instances reference to singleton connection")
    void setUpTestClass(){
        this.db = DB.INSTANCE;
        this.CONN = db.getConnection();
        this.STMT = db.getStatement();
    }

    @AfterTest(description = "Commiting the transactions after end of each test")
    void commitTransaction(){
        this.db.commitTransactions();
    }

    @AfterSuite(description = "closing connection singleton")
    void commitTransactionAndCloseConnection(){
        this.db.close();
    }

}
