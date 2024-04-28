package project.test;

import org.testng.annotations.*;
import project.Try;
import project.utils.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static project.utils.CustomLogger.log;


abstract sealed class Base permits ConnectionTest,
                                   DeleteData,
                                   PopulateData,
                                   CreateTables,
                                   DropTables{
    DB db;
    Connection CONN;
    Statement STMT;

    @BeforeSuite(description = "Setting up connection singleton")
    final void setUpConnectionSingleton(){
        var init =  DB.INSTANCE; // init variable required, since java doesn't allow _ = DB.INSTANCE
    }

    @BeforeTest(description = "Feeding test instances reference to singleton connection")
    final void setUpTestClass(){
        this.db = DB.INSTANCE;
        this.CONN = db.getConnection();
        this.STMT = db.getStatement();
    }

    @AfterTest(description = "Commiting the transactions after end of each test method")
    final void commitTransaction(){
        this.db.commitTransactions();
    }

    @AfterSuite(description = "closing connection singleton")
    final void commitTransactionAndCloseConnection(){
        this.db.close();
    }



}
