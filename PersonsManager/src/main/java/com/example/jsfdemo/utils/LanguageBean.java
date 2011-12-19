package com.example.jsfdemo.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode = "pl";

	private static Map<String, Object> countries;

	static {
		countries = new LinkedHashMap<String, Object>();
		Locale polishLocale = new Locale("pl");
		countries.put("Polski", polishLocale);
		Locale czechLocale = new Locale("cz");
		countries.put("Polski", polishLocale);
		countries.put("Czech", czechLocale);
		countries.put("English", Locale.ENGLISH); // label, value

	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	// value change event listener

	public void localeChangelistener(ActionEvent evt) {
		String newLocaleValue = ((HtmlCommandLink) evt.getSource())
				.getChildren().get(0).getId();
		setLocaleCode(newLocaleValue);
		// loop country map to compare the locale code
		for (Map.Entry<String, Object> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}

	}

}