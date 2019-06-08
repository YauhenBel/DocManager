package ConnectionPooling;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static String address = "";
    private static Logger logger = LogManager.getLogger();

    static {
        logger.error("Настройка конфигурации сети");
        getAddress();

        config.setJdbcUrl(address + "?zeroDateTimeBehavior=convertToNull&" +
                "useUnicode=true&characterEncoding=utf-8" );
        config.setUsername( "root" );
        config.setPassword( "" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );

        try {
            ds = new HikariDataSource( config );
        }catch (Exception e){
            logger.error("Ошибка");
            ds = null;

        }

    }

    private static void getAddress(){
        logger.info("getAddress");
        try(FileReader reader = new FileReader("address.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

                System.out.print((char)c);
                address+=(char)c;


            }
        }
        catch(IOException ex){

            logger.error(ex.getMessage());

            System.out.println(ex.getMessage());
        }
        System.out.println("\ngetAddress = " + address);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        logger.info("getConnection");

        if (ds == null){
            logger.info("ds == null");
            return null;
        }
        return ds.getConnection();
    }

    public static void clearAddress(){
        //logger.info("getAdr");
        //config = new HikariConfig();
        address = "";
        getAddress();
        config.setJdbcUrl(address + "?zeroDateTimeBehavior=convertToNull&" +
                "useUnicode=true&characterEncoding=utf-8" );
        try {
            ds = new HikariDataSource( config );
        }catch (Exception e){
            logger.error("Ошибка");
            ds = null;

        }

    }


}

