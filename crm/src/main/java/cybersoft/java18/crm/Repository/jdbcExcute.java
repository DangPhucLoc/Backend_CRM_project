package cybersoft.java18.crm.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface jdbcExcute <T>{
    T processQuery(Connection connection) throws SQLException;
}
