package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import project.Try;
import project.utils.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

import static project.utils.CustomLogger.log;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Assigned Transactions")
public final class Transactions extends Base {

    Try.ThrowSupplier<ResultSet> s1 =  () -> STMT.executeQuery("SELECT * FROM Depot");
    Try.ThrowSupplier<ResultSet> s2 =  () -> STMT.executeQuery("SELECT * FROM Product");
    Try.ThrowSupplier<ResultSet> s3 =  () -> STMT.executeQuery("SELECT * FROM Stock");

    @Test(description = "Transaction 1 By Osaid Khan")
    void doTransaction1(){
       log(DB.getResultSetString(Try.ThrowSupplier.apply(s1, SQLException.class).value()));
       log(DB.getResultSetString(Try.ThrowSupplier.apply(s2, SQLException.class).value()));
       log(DB.getResultSetString(Try.ThrowSupplier.apply(s3, SQLException.class).value()));
    }

    @Test(description = "Transaction 2 By Osaid Khan")
    void doTransaction2(){
        log("doTransaction2");

    }

    @Test(description = "Transaction 3 By Rishabh Gada")
    void doTransaction3(){
        log("doTransaction3");

    }

    @Test(description = "Transaction 4 By Rishabh Gada")
    void doTransaction4(){
        log("doTransaction4");

    }

    @Test(description = "Transaction 5 By Sharukh Saiyed")
    void doTransaction5(){
        log("doTransaction5");

    }

    @Test(description = "Transaction 6 By Sharukh Saiyed")
    void doTransaction6(){
        log("doTransaction6");

    }

}
