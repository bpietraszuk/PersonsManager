package com.example.jsfdemo.domain;

import javax.validation.constraints.Min;


public class Person {

	private int id;
	private String name = "unknown";
	private int yob = 1900;
	private String peselNumber = "12345678911";
	private int luckyNumber = 1;
	private boolean editableOpt = true;



	public boolean getEditable() {
		return this.editableOpt;
	}

	public void setEditable(boolean editable) {
		this.editableOpt = editable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public int getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(int luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public String getPeselNumber() {
		return peselNumber;
	}

	public void setPeselNumber(String peselNumber) {
		this.peselNumber = peselNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Min(1900)
	public int getYob() {
		return yob;
	}

	public void setYob(int yob) {
		this.yob = yob;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o == null || !getClass().equals(o.getClass())) {
			return false;
		} else {
			Person m = (Person) o;
			return this.name.equals(m.name) && this.yob == (m.yob)
					&& this.peselNumber.equals(m.peselNumber)
					&& this.luckyNumber == (m.luckyNumber);
		}
	}

	@Override
	public String toString() {
		return name;
	}

}
