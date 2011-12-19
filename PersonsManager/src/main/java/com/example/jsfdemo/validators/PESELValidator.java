package com.example.jsfdemo.validators;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PESELValidator implements Validator {

	public PESELValidator() {
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		String strValue = (String) value;
		ResourceBundle	messages = ResourceBundle.getBundle("com.example.jsfdemo.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

		if (strValue.length() != 11) {
			throw new ValidatorException(new FacesMessage(messages.getString("peselErrLength")));
		}
		else {
			Integer peselSumToControl = (new Integer(Character.toString(strValue.charAt(0)))*1) + 
					(new Integer(Character.toString(strValue.charAt(1)))*3)+
						(new Integer(Character.toString(strValue.charAt(2)))*7)+
							(new Integer(Character.toString(strValue.charAt(3)))*9)+
					(new Integer(Character.toString(strValue.charAt(4)))*1) + 
					(new Integer(Character.toString(strValue.charAt(5)))*3)+
						(new Integer(Character.toString(strValue.charAt(6)))*7)+
							(new Integer(Character.toString(strValue.charAt(7)))*9)+
								(new Integer(Character.toString(strValue.charAt(8)))*1)+
						(new Integer(Character.toString(strValue.charAt(9)))*3)+
							(new Integer(Character.toString(strValue.charAt(10)))*1);
			
			if(peselSumToControl%10!=0){
				throw new ValidatorException(new FacesMessage(messages.getString("peselErrControlSum")+peselSumToControl));
			}
			
		}

	}
}
