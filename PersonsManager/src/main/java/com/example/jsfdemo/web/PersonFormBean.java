package com.example.jsfdemo.web;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.jsfdemo.domain.Person;
import com.example.jsfdemo.service.PersonManager;

@SessionScoped
@Named("personBean")
public class PersonFormBean implements Serializable {
	private String nameError;

	public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}

	private static final long serialVersionUID = 1L;

	private Person person = new Person();

	private ListDataModel<Person> persons = new ListDataModel<Person>();
	private ListDataModel<Person> foundPerson = new ListDataModel<Person>();

	@Inject
	private PersonManager pm;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<String> getPersonsNames() {
		return pm.getPersonsNames();

	}

	public ListDataModel<Person> getAllPersons() {
		persons.setWrappedData(pm.getAllPersons());
		return persons;
	}

	public String addPerson() {
		pm.addPerson(person);
		return "showPersons";
	}

	public String findPerson() {
		return "showPerson";
	}

	public String deletePerson() {
		Person personToDelete = persons.getRowData();
		pm.deletePerson(personToDelete);
		return null;
	}

	public String deletePersonByName() {
		pm.deletePersonByName(person);
		return "showPersons";
	}

	public ListDataModel<Person> getFoundPerson() {
		foundPerson.setWrappedData(pm.findPerson(person));
		return foundPerson;
	}

	public String saveAction(Person person) {
		pm.updatePerson(person);
		for (Person p : persons) {
			p.setEditable(false);
		}
		return null;
	}

	public String editPerson(Person person) {
		person.setEditable(true);
		return "showPersons";
	}

	public void validateNameForUpdate(FacesContext context,
			UIComponent validate, Object value) {
		String id = ((HtmlOutputText) context.getViewRoot().findComponent(
				"tableForm:table:id")).getValue().toString();
		String name = ((UIInput) context.getViewRoot().findComponent(
				"tableForm:table:name")).getSubmittedValue().toString();

		boolean nameExists = false;

		for (Person person : pm.getAllPersons()) {
			if (person.getName().equals(name)
					&& person.getId() != Integer.parseInt(id)) {
				nameExists = true;
				break;
			}
		}

		if (nameExists) {
			ResourceBundle messages = ResourceBundle.getBundle(
					"com.example.jsfdemo.messages", FacesContext
							.getCurrentInstance().getViewRoot().getLocale());
			throw new ValidatorException(new FacesMessage(
					messages.getString("personExistsError")));
		}
	}

	public void validatePESELForUpdate(FacesContext context,
			UIComponent validate, Object value) {

		String yob = ((UIInput) context.getViewRoot().findComponent(
				"tableForm:table:yob")).getSubmittedValue().toString()
				.substring(2, 4);

		String peselNumber = ((UIInput) context.getViewRoot().findComponent(
				"tableForm:table:peselNumber")).getSubmittedValue().toString()
				.substring(0, 2);

		if (!yob.equals(peselNumber)) {

			ResourceBundle messages = ResourceBundle.getBundle(
					"com.example.jsfdemo.messages", FacesContext
							.getCurrentInstance().getViewRoot().getLocale());
			throw new ValidatorException(new FacesMessage(
					messages.getString("peselAndYobErr")));
		}

	}

	public void validateName(ValueChangeEvent e) {
		ResourceBundle messages = ResourceBundle.getBundle(
				"com.example.jsfdemo.messages", FacesContext
						.getCurrentInstance().getViewRoot().getLocale());

		String name = e.getNewValue().toString();

		if (name.contains("_"))
			nameError = messages.getString("nameErrUnderscores");
		else if (name.equals(""))
			nameError = messages.getString("nameErrBlank");
		else if (name.contains(" "))
			nameError = messages.getString("nameErrSpaces");
		else
			nameError = "";
	}

	public void validateName(FacesContext context, UIComponent validate,
			Object value) {
		String name = (String) value;

		boolean nameExists = false;

		for (Person person : pm.getAllPersons()) {
			if (person.getName().equals(name)) {
				nameExists = true;
				break;
			}
		}
		if (nameExists) {
			ResourceBundle messages = ResourceBundle.getBundle(
					"com.example.jsfdemo.messages", FacesContext
							.getCurrentInstance().getViewRoot().getLocale());
			throw new ValidatorException(new FacesMessage(
					messages.getString("personExistsError")));
		}
	}

	public void validatePESEL(FacesContext context, UIComponent validate,
			Object value) {
		String yob = ((UIInput) context.getViewRoot().findComponent(
				"personform:yob")).getSubmittedValue().toString()
				.substring(2, 4);
		String peselNumber = ((UIInput) context.getViewRoot().findComponent(
				"personform:peselNumber")).getSubmittedValue().toString()
				.substring(0, 2);

		if (!yob.equals(peselNumber)) {
			ResourceBundle messages = ResourceBundle.getBundle(
					"com.example.jsfdemo.messages", FacesContext
							.getCurrentInstance().getViewRoot().getLocale());
			throw new ValidatorException(new FacesMessage(
					messages.getString("peselAndYobErr")));
		}

	}

}
