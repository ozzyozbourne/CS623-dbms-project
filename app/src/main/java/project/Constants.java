package project;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public interface Constants {

    String CREATE_TABLE_PRODUCT = """
           CREATE TABLE Product(
            	prodid	CHAR(10),
            	pname	VARCHAR(30),
            	price	DECIMAL
            );
           """;
    String CREATE_CONSTRAINTS_PRODUCT_PRIMARY_KEY = "ALTER TABLE Product ADD CONSTRAINT pk_product PRIMARY KEY(prodid);";
    String CREATE_CONSTRAINTS_PRODUCT_PRODUCT_PRICE = "ALTER TABLE Product ADD CONSTRAINT ck_product_price CHECK(price > 0);";

    String CREATE_TABLE_DEPOT = """
           CREATE TABLE Depot(
            	depid	CHAR(10),
            	addr	VARCHAR(30),
            	volume	INTEGER
            );
           """;
    String CREATE_CONSTRAINTS_DEPOT_PRIMARY_KEY = "ALTER TABLE Depot ADD CONSTRAINT pk_depot PRIMARY KEY(depid);";
    String CREATE_CONSTRAINTS_DEPOT_VOLUME = "ALTER TABLE Depot ADD CONSTRAINT ck_depot_volume CHECK(volume > 0);";

    String CREATE_TABLE_STOCK = """
            CREATE TABLE Stock(
            	prodid    CHAR(10),
            	depid     CHAR(10),
            	quantity  INTEGER
            );
            """;
    String CREATE_CONSTRAINTS_STOCK_PRIMARY_KEY = "ALTER TABLE Stock ADD CONSTRAINT pk_stock PRIMARY KEY(prodid, depid);";
    String CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_PRODUCT =
            "ALTER TABLE Stock ADD CONSTRAINT fk_stock_product FOREIGN KEY(prodid) REFERENCES Product(prodid);";
    String CREATE_CONSTRAINTS_STOCK_FOREIGN_KEY_STOCK_DEPOT =
            "ALTER TABLE Stock ADD CONSTRAINT fk_stock_depot   FOREIGN KEY(depid)  REFERENCES Depot(depid)";

    String URL = getProp("URL");
    String USER = getProp("USER");
    String PASSWORD = getProp("PASSWORD");

    static String getProp(final String key){
        final Try.Result<FileInputStream, IOException> res =  Try.ThrowSupplier
                .apply(() -> new FileInputStream(Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "connect.properties")
                        .toFile()), IOException.class);
        if(res.error() != null) throw new RuntimeException(res.error().getMessage());
        final Properties prop = new Properties();
        final Try.ResultException<IOException> proRes = Try.ThrowConsumer
                .apply(() -> prop.load(res.value()), IOException.class);
        if(proRes.error() != null) throw new RuntimeException(proRes.error().getMessage());
        return prop.getProperty(key);
    }
}
