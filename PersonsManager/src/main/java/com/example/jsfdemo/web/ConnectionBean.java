package com.example.jsfdemo.web;



import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.jsfdemo.domain.Connection;
import com.example.jsfdemo.service.ConnectionManager;


@SessionScoped
@Named("connectionBean")
public class ConnectionBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConnectionManager cm;
	private Connection connection = new Connection();

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void createConnection(){
		cm.createConnection(connection);
	}
	
	public String getConnectionStatus(){
	return cm.getConnectionStatus();
}
	

}
