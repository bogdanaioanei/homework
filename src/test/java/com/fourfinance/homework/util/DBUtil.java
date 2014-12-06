package com.fourfinance.homework.util;

import static org.dbunit.operation.DatabaseOperation.CLOSE_CONNECTION;
import static org.dbunit.operation.DatabaseOperation.DELETE_ALL;
import static org.dbunit.operation.DatabaseOperation.INSERT;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {

    private static final Logger log = LoggerFactory.getLogger(DBUtil.class);

    private DatabaseConnection iDatabaseConnection;

    private IDataSet dataSet;

    public DBUtil(DataSource dataSource, String xmlFilePath) {

        createDBConnection(dataSource);

        loadDataSet(xmlFilePath);

        cleanInsert();
    }

    private void createDBConnection(DataSource dataSource) {
        try {
            iDatabaseConnection = new DatabaseConnection(dataSource.getConnection());
        } catch (Exception e) {
            log.error("DBUtil get connection exception: ", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        iDatabaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);
    }

    private void loadDataSet(String xmlFilePath) {
        try {
            dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).setCaseSensitiveTableNames(false)
                    .build(ClassLoader.getSystemResourceAsStream(xmlFilePath));
        } catch (DataSetException e) {
            log.error("DBUtil data set loading exception: ", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void cleanInsert() {
        try {
            DELETE_ALL.execute(iDatabaseConnection, dataSet);
            INSERT.execute(iDatabaseConnection, dataSet);
        } catch (Exception e) {
            log.error("DBUtil clean insert exception: ", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            CLOSE_CONNECTION(DELETE_ALL).execute(iDatabaseConnection, dataSet);
        } catch (Exception e) {
            log.error("DBUtil close connection exception: ", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
