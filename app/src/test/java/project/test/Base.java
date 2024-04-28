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

    @BeforeTest(description = "Setting up connection")
    final void setUpTest(){
        this.db = new DB();
        this.CONN = db.getConnection();
        this.STMT = db.getStatement();
    }

    @AfterTest(description = "Commiting the transactions and closing connection")
    void commitTransactionAndCloseConnection(){
        this.db.commitTransactions();
        this.db.close();
    }

}
