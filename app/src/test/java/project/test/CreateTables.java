package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import project.Try;

import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import static org.testng.Assert.*;
import static project.Constants.*;
import static project.utils.CustomLogger.log;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Create Tables")
public final class CreateTables extends Base{

    private final Try.Result<Statement, SQLException> stmt;

    public CreateTables() {
        log("Setting result-set type");
        stmt = Try
                .ThrowSupplier
                .apply(() -> CONN.value().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY), SQLException.class);
        assertNull(stmt.error());
        log("result-set set to scroll insensitive and readonly successfully");
    }

    @Test(description = "Creating table Product")
    void createProductTable(){
        log("Creating table Product");
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_TABLE_PRODUCT), SQLException.class);
        checkForErrors(res, "Unable to create table Product");
        log("Created Product Table Successfully");
    }

    @Test(description = "Creating table Product Constraints", dependsOnMethods = "createProductTable")
    void createProductTableConstraints(){
        log("Creating table Product Constraints");
        log("Setting primary key");
        var res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_PRODUCT_PRIMARY_KEY), SQLException.class);
        checkForErrors(res, "Unable to create table Primary key Product Constraint");
        log("setting price constraint");
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_PRODUCT_PRODUCT_PRICE), SQLException.class);
        checkForErrors(res, "Unable to create table Product Product Price Constraint");
        log("Created table Product Constraints Successfully");
    }

    @Test(description = "Creating table Depot", dependsOnMethods = "createProductTableConstraints")
    void createDepot(){
        log("Creating table Depot");
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_TABLE_DEPOT), SQLException.class);
        checkForErrors(res, "Unable to create table Depot");
        log("Created Depot Table Successfully");
    }

    @Test(description = "Creating table Depot Constraints", dependsOnMethods = "createDepot")
    void createDepotConstraints(){
        log("Creating table Depot Constraints");
        log("Setting primary key");
        var res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_DEPOT_PRIMARY_KEY), SQLException.class);
        checkForErrors(res, "Unable to create table Depot Primary key Constraint");
        log("setting volume constraint");
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_DEPOT_VOLUME), SQLException.class);
        checkForErrors(res, "Unable to create table Depot Volume Constraint");
        log("Created table Depot Constraints Successfully");
    }

    @Test(description = "Creating table Stock", dependsOnMethods = "createDepotConstraints")
    void createStockTable(){
        log("Creating table Stock");
        final Try.Result<Boolean, SQLException> res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_TABLE_STOCK), SQLException.class);
        checkForErrors(res, "Unable to create table Stock");
        log("Created Stock Table Successfully");
    }

    @Test(description = "Creating table Stock Constraints", dependsOnMethods = "createStockTable")
    void createStockTableConstraints(){
        log("Creating table Stock Constraints");
        log("Setting primary key");
        var res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_STOCK_PRIMARY_KEY), SQLException.class);
        checkForErrors(res, "Unable to create table Stock Primary key Product Constraint");
        log("Setting foreign key constraint from stock to product");
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_PRODUCT), SQLException.class);
        checkForErrors(res, "Unable to create table Stock ForeignKey Stock to Product Constraint");
        log("Setting foreign key constraint from stock to depot");
        res = Try
                .ThrowSupplier
                .apply(() -> stmt.value().execute(CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_DEPOT), SQLException.class);
        checkForErrors(res, "Unable to create table Stock ForeignKey Stock to Depot Constraint");
        log("Created table Stock Constraints Successfully");
    }

    @AfterTest(description = "Commiting the transactions")
    void commitTransactions(){
        log("Commiting transactions");
        assertNull(Try.ThrowConsumer.apply(() -> CONN.value().commit(), SQLException.class).error());
        log("Committed transactions Successfully");
    }

    private void checkForErrors(final Try.Result<Boolean, SQLException> res, final String runtimeExceptionMessage){
        if(res.error() != null || !res.value()){
            if(res.error() != null)log("Error: " + res.error());
            else log("statement returned false");
            log("Rolling back transaction");
            assertNull(Try.ThrowConsumer.apply(() -> CONN.value().rollback(), SQLException.class).error());
            log("Rolled back transaction Successfully");
            throw new RuntimeException(runtimeExceptionMessage);
        }
    }

}


