package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import project.Try;

import java.sql.SQLException;

import static project.Constants.*;
import static project.Constants.DROP_TABLE_STOCK;
import static project.utils.CustomLogger.log;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Deleting all the data present in all the tables")
public final class DeleteData extends Base {

    @Test(description = "Deleting all data from table Product")
    void deleteAllDataFromProductTable(){
        log("Deleting all data from table Product");
        log(DELETE_TABLE_PRODUCT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(DELETE_TABLE_PRODUCT), SQLException.class);
        checkForErrors(res, "Unable to Delete data from table Product");
        log("Deleted all data from Product Table Successfully");
    }

    @Test(description = "Deleting all data from table Depot", dependsOnMethods = "deleteAllDataFromProductTable")
    void deleteAllDataFromDepotTable(){
        log("Deleting all data from table Depot");
        log(DELETE_TABLE_DEPOT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(DELETE_TABLE_DEPOT), SQLException.class);
        checkForErrors(res, "Unable to Delete data from table Depot");
        log("Deleted all data from Table Depot Successfully");
    }

    @Test(description = "Deleting all data from table Stock", dependsOnMethods = "deleteAllDataFromDepotTable")
    void deleteAllDataFromStockTable(){
        log("Deleting all data from table Stock");
        log(DELETE_TABLE_STOCK);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(DELETE_TABLE_STOCK), SQLException.class);
        checkForErrors(res, "Unable to Delete data from table Stock");
        log("Deleted all data from Table Stock Successfully");
    }
}
