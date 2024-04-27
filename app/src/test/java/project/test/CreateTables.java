package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.*;
import project.Try;

import java.sql.SQLException;

import static project.Constants.*;
import static project.utils.CustomLogger.log;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Create Tables")
public final class CreateTables extends Base{

    @Test(description = "Creating table Product")
    void createProductTable(){
        log("Creating table Product");
        log(CREATE_TABLE_PRODUCT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_TABLE_PRODUCT), SQLException.class);
        checkForErrors(res, "Unable to create table Product");
        log("Created Product Table Successfully");
    }

    @Test(description = "Creating table Product Constraints", dependsOnMethods = "createProductTable")
    void createProductTableConstraints(){
        log("Creating table Product Constraints");
        log(CREATE_CONSTRAINTS_PRODUCT_PRIMARY_KEY);
        var res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_PRODUCT_PRIMARY_KEY), SQLException.class);
        checkForErrors(res, "Unable to create table Primary key Product Constraint");
        log(CREATE_CONSTRAINTS_PRODUCT_PRODUCT_PRICE);
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_PRODUCT_PRODUCT_PRICE), SQLException.class);
       checkForErrors(res, "Unable to create table Product Product Price Constraint");
        log("Created table Product Constraints Successfully");
    }

    @Test(description = "Creating table Depot", dependsOnMethods = "createProductTableConstraints")
    void createDepot(){
        log("Creating table Depot");
        log(CREATE_TABLE_DEPOT);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_TABLE_DEPOT), SQLException.class);
        checkForErrors(res, "Unable to create table Depot");
        log("Created Depot Table Successfully");
    }

    @Test(description = "Creating table Depot Constraints", dependsOnMethods = "createDepot")
    void createDepotConstraints(){
        log("Creating table Depot Constraints");
        log(CREATE_CONSTRAINTS_DEPOT_PRIMARY_KEY);
        var res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_DEPOT_PRIMARY_KEY), SQLException.class);
        checkForErrors(res, "Unable to create table Depot Primary key Constraint");
        log(CREATE_CONSTRAINTS_DEPOT_VOLUME);
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_DEPOT_VOLUME), SQLException.class);
        checkForErrors(res, "Unable to create table Depot Volume Constraint");
        log("Created table Depot Constraints Successfully");
    }

    @Test(description = "Creating table Stock", dependsOnMethods = "createDepotConstraints")
    void createStockTable(){
        log("Creating table Stock");
        log(CREATE_TABLE_STOCK);
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_TABLE_STOCK), SQLException.class);
        checkForErrors(res, "Unable to create table Stock");
        log("Created Stock Table Successfully");
    }

    @Test(description = "Creating table Stock Constraints", dependsOnMethods = "createStockTable")
    void createStockTableConstraints(){
        log("Creating table Stock Constraints");
        log(CREATE_CONSTRAINTS_STOCK_PRIMARY_KEY);
        var res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_STOCK_PRIMARY_KEY), SQLException.class);
        checkForErrors(res, "Unable to create table Stock Primary key Product Constraint");
        log(CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_PRODUCT);
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_PRODUCT), SQLException.class);
        checkForErrors(res, "Unable to create table Stock ForeignKey Stock to Product Constraint");
        log(CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_DEPOT);
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_DEPOT), SQLException.class);
        checkForErrors(res, "Unable to create table Stock ForeignKey Stock to Depot Constraint");
        log("Created table Stock Constraints Successfully");
    }

}


