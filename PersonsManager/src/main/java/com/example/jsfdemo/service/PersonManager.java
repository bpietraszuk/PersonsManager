package com.example.jsfdemo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.example.jsfdemo.domain.Person;

@ApplicationScoped
public class PersonManager {
	private List<Person> db = new ArrayList<Person>();
	private int personID = 0;

	public List<Person> getDb() {
		return db;
	}

	public void setDb(List<Person> db) {
		this.db = db;
	}

	@Inject
	private ConnectionManager cm;

	public void updatePerson(Person person) {

		for (Person p : db) {
			if (person.getId() == p.getId()) {
				p.setName(person.getName());
				p.setPeselNumber(person.getPeselNumber());
				p.setYob(person.getYob());
				p.setLuckyNumber(person.getLuckyNumber());
				break;
			}
		}
	}

	public void addPerson(Person person) {
		Person newPerson = new Person();
		newPerson.setId(personID++);
		newPerson.setName(person.getName());
		newPerson.setYob(person.getYob());
		newPerson.setPeselNumber(person.getPeselNumber());
		newPerson.setLuckyNumber(person.getLuckyNumber());
		newPerson.setEditable(false);
		if (cm.getConnectionStatus().equals("Connected")) {

		} else {
			db.add(newPerson);
		}
	}

	public void deletePersonByName(Person person) {
		Person personToRemove = null;
		for (Person p : db) {
			if (person.getName().equals(p.getName())) {
				personToRemove = p;
				break;
			}
		}

		if (personToRemove != null)
			db.remove(personToRemove);
	}

	public void deletePerson(Person person) {

		Person personToRemove = null;

		for (Person p : db) {
			if (person.equals(p)) {
				personToRemove = p;
				break;
			}
		}

		if (personToRemove != null)
			db.remove(personToRemove);
	}

	public List<Person> findPerson(Person person) {
		List<Person> personToFind = new ArrayList<Person>();
		for (Person p : db) {
			if (p.getName().equals(person.getName())) {
				personToFind.add(p);
				break;
			}
		}
		return personToFind;
	}

	public List<Person> getAllPersons() {

		if (cm.getConnectionStatus().equals("Connected")) {
			try {
				cm.getConnection().setAutoCommit(false);
				Statement st = cm.getConnection().createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM users;");
				while (rs.next()) {
					Person person = new Person();
					person.setId(rs.getInt("usr_id"));
					person.setName(rs.getString("usr_name"));
					person.setYob(rs.getInt("usr_yob"));
					person.setPeselNumber(rs.getString("usr_pesel_num"));
					person.setLuckyNumber(rs.getInt("usr_lucky_num"));
					db.add(person);
				}
				for(Person p:db){
					System.out.println(p);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return db;

	}

	public List<String> getPersonsNames() {
		List<String> names = new ArrayList<String>();
		for (Person p : db) {
			names.add(p.getName());
		}
		return names;
	}
}
