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
@Feature("Populating the data base with default Assignment 2 data")
public final class PopulateData extends Base {

    @Test(description = "Populating table Product")
    void populateProductTable(){
        log("populating table Product");
        log(POPULATE_PRODUCT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(POPULATE_PRODUCT), SQLException.class);
        checkForErrors(res, "Unable to populate table Product");
        log("Populated Product Table Successfully");
    }

    @Test(description = "Populating table Depot", dependsOnMethods = "populateProductTable")
    void populateDepotTable(){
        log("populating table Depot");
        log(POPULATE_DEPOT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(POPULATE_DEPOT), SQLException.class);
        checkForErrors(res, "Unable to populate table Depot");
        log("Populated Depot Table Successfully");
    }

    @Test(description = "Populating table Stock", dependsOnMethods = "populateDepotTable")
    void populateStockTable(){
        log("populating table Stock");
        log(POPULATE_STOCK);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(POPULATE_STOCK), SQLException.class);
        checkForErrors(res, "Unable to populate table Stock");
        log("Populated Stock Table Successfully");
    }
}
