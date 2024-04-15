package project;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public interface Constants {
    String URL = getProp("URL");
    String USER = getProp("USER");
    String PASSWORD = getProp("PASSWORD");

    static String getProp(final String key){
       final Try.Result<FileInputStream, IOException> res =  Try
                .ThrowSupplier
                .apply(() -> new FileInputStream(Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "connect.properties")
                        .toFile()), IOException.class);
       if(res.error() != null) throw new RuntimeException(res.error().getMessage());
       final Properties prop =  new Properties();
       final Try.ResultException<IOException> proRes = Try
               .ThrowConsumer
               .apply(() -> prop.load(res.value()), IOException.class);
       if(proRes.error() != null) throw new RuntimeException(proRes.error().getMessage());
       return prop.getProperty(key);
    }
}
