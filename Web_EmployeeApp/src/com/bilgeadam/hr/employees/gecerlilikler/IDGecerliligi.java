package com.bilgeadam.hr.employees.gecerlilikler;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class IDGecerliligi implements Validator 
{


	private static final int TC_LENGTH = 11;
	private static final FacesMessage HATA_TC_HANE = new FacesMessage("TC kimlik no 11 haneli olmalidir.");
	private static final FacesMessage HATA_TC_KARAKTER = new FacesMessage("TC kimlik no rakamlardan olusmalidir.");


	public IDGecerliligi() {}
	

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException 
	{
			// Gelen veri, metodun 3. parametresidir.
			String strValue = value.toString();
			int TC_length = strValue.length();
				
			if(TC_length != TC_LENGTH)
			{
				throw new ValidatorException(HATA_TC_HANE);
			}
			else
			{
				try
				{
					Long TC_kimlik = Long.parseLong(strValue);
					System.out.println("TC kimlik numarasi: " + TC_kimlik);
				}
				catch(Exception e)
				{
					throw new ValidatorException(HATA_TC_KARAKTER);
				}
			}	
	}

}
