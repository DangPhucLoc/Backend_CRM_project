package cybersoft.java18.crm.Repository;

import cybersoft.java18.crm.jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRepository <T> {

    protected List<T> executeQuery(jdbcExcute<List<T>> process) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return process.processQuery(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected Integer executeSaveAndUpdate(jdbcExcute<Integer> process) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return process.processQuery(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
