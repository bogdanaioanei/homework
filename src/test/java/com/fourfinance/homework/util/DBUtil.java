package com.fourfinance.homework.util;

import static org.dbunit.operation.DatabaseOperation.CLOSE_CONNECTION;
import static org.dbunit.operation.DatabaseOperation.DELETE_ALL;
import static org.dbunit.operation.DatabaseOperation.INSERT;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class DBUtil {

	private DatabaseConnection iDatabaseConnection;

	private IDataSet dataSet;

	public DBUtil(DataSource dataSource, String xmlFilePath) throws Exception {
		iDatabaseConnection = new DatabaseConnection(dataSource.getConnection());
		iDatabaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);

		dataSet = getDataSet(xmlFilePath);

		cleanInsert();
	}

	protected IDataSet getDataSet(String xmlFilePath) throws Exception {
		return new FlatXmlDataSetBuilder().setColumnSensing(true).setCaseSensitiveTableNames(false)
				.build(ClassLoader.getSystemResourceAsStream(xmlFilePath));
	}

	public void cleanInsert() throws Exception {
		DELETE_ALL.execute(iDatabaseConnection, dataSet);
		INSERT.execute(iDatabaseConnection, dataSet);
	}

	public void close() throws Exception {
		CLOSE_CONNECTION(DELETE_ALL).execute(iDatabaseConnection, dataSet);
	}

}
