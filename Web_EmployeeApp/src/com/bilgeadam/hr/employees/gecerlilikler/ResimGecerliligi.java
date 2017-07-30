package com.bilgeadam.hr.employees.gecerlilikler;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import com.bilgeadam.hr.employees.model.Calisan;

public class ResimGecerliligi implements Validator {

	private static final FacesMessage HATA_BOYUT = new FacesMessage("DOSYA BOYUTU HADDÝNDEN FAZLA BÜYÜK");
	private static final FacesMessage HATA_UZANTI = new FacesMessage("DOSYA UZANTISI JPEG VEYA PNG OLMALIDIR");

	public ResimGecerliligi() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object deger)
			throws ValidatorException {
					
			Part dosya=(Part) deger;
			
			if(dosya.getSize()>900*1024)
				throw new ValidatorException(HATA_BOYUT);
			
			if( !( dosya.getContentType().equals("image/png") || dosya.getContentType().equals("image/jpeg") ) )
				throw new ValidatorException(HATA_UZANTI);
	}

}
