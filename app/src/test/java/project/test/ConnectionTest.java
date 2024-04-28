package project.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.*;
import project.Try;

import java.sql.SQLException;

import static org.testng.Assert.assertFalse;

@Epic("CS 623 Project")
@Owner("Team 1")
@Feature("Testing AWS DB connection is working properly")
public final class ConnectionTest extends Base {

    @Test(description = "Checking to see if connection is established")
    void testConnection(){
        assertFalse(Try.ThrowSupplier.apply(() -> CONN.isClosed(), SQLException.class).value());
    }

}
