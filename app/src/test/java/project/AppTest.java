package project;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class AppTest {
    @Feature("Feature1")
    @Story("Story1")
    @Description("This is a test description")
    @Test public void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
