package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import project.Try;

import java.sql.SQLException;

import static project.Constants.*;
import static project.utils.CustomLogger.log;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Delete the tables")
public final class DropTables extends Base{

    @Test(description = "Dropping table Product")
    void dropProductTable(){
        log("Dropping table Product");
        log(DROP_TABLE_PRODUCT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> STMT.execute(DROP_TABLE_PRODUCT), SQLException.class);
        db.rollbackOnError(res, "Unable to drop table Product");
        log("Dropped Product Table Successfully");
    }

    @Test(description = "Dropping table Depot", dependsOnMethods = "dropProductTable")
    void dropDepotTable(){
        log("Dropping table Depot");
        log(DROP_TABLE_DEPOT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> STMT.execute(DROP_TABLE_DEPOT), SQLException.class);
        db.rollbackOnError(res, "Unable to drop table Depot");
        log("Dropped Depot Table Successfully");
    }

    @Test(description = "Dropping table Stock", dependsOnMethods = "dropDepotTable")
    void dropStockTable(){
        log("Dropping table Stock");
        log(DROP_TABLE_STOCK);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> STMT.execute(DROP_TABLE_STOCK), SQLException.class);
        db.rollbackOnError(res, "Unable to drop table Stock");
        log("Dropped Stock Table Successfully");
    }
}
