package com.example.jsfdemo.service;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.example.jsfdemo.domain.Connection;

@ApplicationScoped
public class ConnectionManager implements Serializable{
	@Inject
	private PersonManager pm;
	
	private static final long serialVersionUID = 1L;
	private String connectionStatus="Not connected";
	private java.sql.Connection connection;
	
	public java.sql.Connection getConnection() {
		return connection;
	}

	public void setConnection(java.sql.Connection connection) {
		this.connection = connection;
	}

	public void createConnection(Connection connection) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.connection= DriverManager.getConnection("jdbc:postgresql://"+ connection.getHost() + ":" + connection.getPort() + "/" + connection.getDb(), connection.getLogin(), connection.getPassword());
			setConnectionStatus("Connected");
			pm.getDb().removeAll(pm.getDb());
		} catch (Exception e) {
			setConnectionStatus("Not connected");
		}
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
