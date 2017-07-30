  package com.bilgeadam.hr.employees.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "CALISAN")
public class Calisan 
{
	
	private static final String VARSAYILAN_STR = "bilinmiyor";

	private static final double VARSAYILAN_MAAS = 4000;

	private static final byte[] VARSAYILAN_GORSEL = {};

	@Id
	@Column(name = "TC_KIMLIK")
	private String TC_no;
	
	@Column(name="ISIM")
	private String isim;
	
	@Column(name="SOYISIM")
	private String soyisim;
	
	private double maas;

	@Column(name = "EKLENME_TARIHI")
	private Timestamp eklenmeTarihi;
	
	@Lob
	@Column(name = "RESIM")
	private byte[] gorsel;
	
	
	@Column(name = "RESIM_TURU")
	private String gorselTuru;
	
	public Calisan(String TC_no, String isim, String soyisim, double maas, byte gorsel[], String gorselTuru) 
	{
		this.TC_no = TC_no;
		this.isim = isim;
		this.soyisim = soyisim;
		this.maas = maas;
		
		Date date = new Date();
		eklenmeTarihi = new Timestamp( date.getTime() );
		
		this.gorsel = gorsel;
		this.gorselTuru = gorselTuru;
	}

	public Calisan()
	{
		this(VARSAYILAN_STR, VARSAYILAN_STR, VARSAYILAN_STR, VARSAYILAN_MAAS, VARSAYILAN_GORSEL, VARSAYILAN_STR);
	}
	
	

	public String getTC_no() {
		return TC_no;
	}

	public void setTC_no(String tC_no) {
		TC_no = tC_no;
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyisim() {
		return soyisim;
	}

	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
	}



	public double getMaas() {
		return maas;
	}

	public void setMaas(double maas) {
		this.maas = maas;
	}

	
	
	public Timestamp getEklenmeTarihi() {
		return eklenmeTarihi;
	}

	public void setEklenmeTarihi(Timestamp eklenmeTarihi) {
		this.eklenmeTarihi = eklenmeTarihi;
	}
	

	public byte[] getGorsel() {
		return gorsel;
	}

	public void setGorsel(byte[] gorsel) {
		this.gorsel = gorsel;
	}

	public String getGorselTuru() {
		return gorselTuru;
	}

	public void setGorselTuru(String gorselTuru) {
		this.gorselTuru = gorselTuru;
	}

	

	@Override
	public String toString() {
		return "Calisan [TC_no=" + TC_no + ", isim=" + isim + ", soyisim="
				+ soyisim + ", maas=" + maas + ", eklenmeTarihi="
				+ eklenmeTarihi + ", gorsel=" + Arrays.toString(gorsel)
				+ ", gorselTuru=" + gorselTuru + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TC_no == null) ? 0 : TC_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calisan other = (Calisan) obj;
		if (TC_no == null) {
			if (other.TC_no != null)
				return false;
		} else if (!TC_no.equals(other.TC_no))
			return false;
		return true;
	}

	
}
