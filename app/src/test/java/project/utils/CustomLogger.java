package project.utils;

import io.qameta.allure.Allure;
import org.testng.Reporter;
import project.Try;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;
import java.util.logging.*;

import static project.Constants.*;

public final class CustomLogger {

    private CustomLogger() {}

    private static final Logger log = Logger.getLogger("");
    private static final Consumer<String> TESTNG_INFO = msg -> Reporter.log("[INFO] " + msg, true);
    private static final Consumer<String> ALLURE_INFO = msg -> Allure.addAttachment("INFO", msg);
    private static final Consumer<String> LOG_INFO = log::info;

    static {
        for (Handler handler : log.getHandlers()) if (handler instanceof ConsoleHandler) log.removeHandler(handler);
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.OFF);
        log.addHandler(getFileHandler());
        log.addHandler(consoleHandler);
    }

    private static FileHandler getFileHandler() {
        if(Paths.get(System.getProperty("user.dir"),"reports", "java").toFile().mkdirs()) {
            final File file = Paths.get(System.getProperty("user.dir"),"reports", "java", "log.txt").toFile();
            final Try.Result<Boolean, IOException> res = Try.ThrowSupplier.apply(file::createNewFile, IOException.class);
            if (res.error() != null) System.out.println("Unable to create empty log file Error: " + res.error().getMessage());
        }
        final Try.Result<FileHandler, IOException> res =  Try.ThrowSupplier
                .apply(() -> new FileHandler(Paths.get(System.getProperty("user.dir"),"reports", "java", "log.txt").toString()), IOException.class);
        if(res.error() != null) System.out.println("Logger Error: " + res.error().getMessage());
        res.value().setFormatter( new SimpleFormatter());
        return res.value();
    }

    public static void log(final String msg) {TESTNG_INFO.andThen(ALLURE_INFO).andThen(LOG_INFO).accept(msg);}

    public static void logCurrentStateOfTableProduct(final Statement STMT){
        log(DB.getResultSetString(Try
                .ThrowSupplier.apply(() -> STMT.executeQuery(ALL_PRODUCTS), SQLException.class)
                .value()));
    }

    public static void logCurrentStateOfTableStock(final Statement STMT){
        log(DB.getResultSetString(Try
                .ThrowSupplier.apply(() -> STMT.executeQuery(ALL_DEPOTS), SQLException.class)
                .value()));
    }

    public static void logCurrentStateOfTableDepot(final Statement STMT){
        log(DB.getResultSetString(Try
                .ThrowSupplier.apply(() -> STMT.executeQuery(ALL_STOCK), SQLException.class)
                .value()));
    }

}
