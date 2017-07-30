package com.bilgeadam.hr.employees.cekirdekler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.Part;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.bilgeadam.hr.employees.model.Calisan;
import com.bilgeadam.hr.employees.utility.Util;

@ManagedBean(name = "employeeManagedBean") // web sayfasi, cekirdegi bu isim ile taniyacak
@RequestScoped // program calistirilip kapaninca, nesnenin ortadan kaldirilmasi icin;
			   // yani sadece o request(istek) byounca nesnenin omru
public class CalisanYoneticiCekirdek 
{
	
	private String infoMessage;
	
	@Resource
	private UserTransaction userTransaction;
	
	
	private static final String QUERY = "SELECT TC_KIMLIK, ISIM, SOYISIM, MAAS, EKLENME_TARIHI, resim, resim_turu"
										+ " FROM CALISAN order by eklenme_tarihi desc";


	private static final double ASGARI_UCRET = 1404;
	
	
	private String employeeID;
	private String employeeName;
	private String employeeSurname;
	private String employeeSalary;
	
	private Part employeeImage;
	
	@PersistenceUnit // Bu "annotation" sayesinde, EntityManagerFactory'nin nesnesini(entityManagerFactory) olusturduk.
	private EntityManagerFactory entityManagerFactory;
	
	
	/*
	 * Bu annotation ile disaradan veritabanina mudahale edecegimizi bildiriyoruz.
	 * Bunun sayesinde, entityManager bir nesne oluyor.
	 */
	@PersistenceContext
	private EntityManager entityManager;
	

	// BU getter metodu sayesinde, burada bir calisanListesi adinda degisken oldugu taniniyor.
	@SuppressWarnings("unchecked")
	public List<Calisan> getCalisanListesi() 
	{
		List<Calisan> calisanListesi;
		EntityManager entityManager = null;
		
		entityManager = entityManagerFactory.createEntityManager(); // JPA'yi getirdik.
		
		Query query = entityManager.createNativeQuery(QUERY, Calisan.class);
		calisanListesi = query.getResultList();
		// entityManager nesnesi uzerinden, createnativeQuery metodu ile query nesnesi olusturduk.
		
		return calisanListesi;
	}

	
	
	public void yeniCalisanEkle() throws NotSupportedException, SystemException
	{
		
		
		try
		{
			// transaction burada baslatiliyor 
			userTransaction.begin();
		
			Calisan calisan = new Calisan();
			
			
//			List<Calisan> denemeListesi = getCalisanListesi();
			
			
//			int ID, MAX = 0;
//			if(denemeListesi != null)
//			{
//				MAX = Integer.MIN_VALUE;
//				for (Calisan emp : denemeListesi) 
//				{
//					ID = emp.getId();
//					if(ID > MAX)
//					{
//						MAX = ID;
//					}
//				}
//			}
//			
//			MAX++;
			
			calisan.setTC_no(employeeID);
			calisan.setIsim(employeeName);
			calisan.setSoyisim(employeeSurname);
			calisan.setMaas(Double.parseDouble(employeeSalary));
			
			Date date = new Date();
			calisan.setEklenmeTarihi( new Timestamp(date.getTime()) );
			
			
			// Yukarisi ile ayni.
			// Calisan calisan = new Calisan(Integer.parseInt( employeeID ), employeeName, employeeSurname, Double.parseDouble(employeeSalary));

			calisan.setGorsel( Util.getInstance().resmiDonustur( employeeImage ) );
			calisan.setGorselTuru( employeeImage.getContentType() );
			
			entityManager.persist(calisan); // veritabanina burada ekleme yapiliyor.
			
			userTransaction.commit();
		}
		
		catch(Exception exception)
		{
			try
			{
				userTransaction.rollback();
				infoMessage = "Calisan eklenemedi. Hata mesaji: " + exception.getMessage();
			}
			catch(Exception e)
			{
				infoMessage = "Calisan eklenemedi. Rollback islemi yapilamadi.\nHata mesaji: " + e.getMessage();
			}
		}
	}
	
	
	public void pdfCikar()
	{
		Util.getInstance().pdfAl(getCalisanListesi());
		infoMessage = "PDF olusturuldu.";
	}
	
	public void calisanSil()
	{
		try 
		{
			userTransaction.begin();
			
			// Calisan calisan = entityManager.find(Calisan.class, Integer.parseInt(employeeID));
			
			Calisan calisan = entityManager.find(Calisan.class, employeeID);
			entityManager.remove(calisan);
			
			userTransaction.commit();
			
			infoMessage = employeeID + "ID'sine sahip olan calisan silindi.";
		} 
		
		catch (Exception exception) 
		{
			try
			{
				userTransaction.rollback();
				infoMessage = "Calisan silinemedi. Hata mesaji: " + exception.getMessage();
			}
			catch(Exception e)
			{
				infoMessage = "Calisan silinemedi. Rollback islemi yapilamadi.\nHata mesaji: " + e.getMessage();
			}
		}
		
	}
	
	public void calisanGuncelle()
	{
		try
		{
			userTransaction.begin();
			
			//Calisan employee = entityManager.find(Calisan.class, Integer.parseInt(employeeID));
			
			
			Calisan employee = entityManager.find(Calisan.class, employeeID);
			if(employeeName.trim().length() > 1)
			{
				employee.setIsim(employeeName);
			}
			
			if(employeeSurname.trim().length() > 1)
			{
				employee.setSoyisim(employeeSurname);
			}

			if(employeeSalary.trim().length() > 0)
			{
				
				
				double salary = Double.parseDouble(employeeSalary);
				if(salary > ASGARI_UCRET)
				{
					employee.setMaas(salary);
				}
				else
				{
					infoMessage = "Asgari ucretin altinda ucret girilemez.";
				}	
				
			}
			
			
			entityManager.merge(employee);
			
			userTransaction.commit();
			
			infoMessage = "Calisan guncellendi.";
		}
		
		catch(Exception exception)
		{
			try
			{
				userTransaction.rollback();
				infoMessage = "Calisan guncellenemedi. \nHata mesaji: " + exception.getMessage();
			}
			catch(Exception e)
			{
				infoMessage = "Calisan guncellenemedi. Rollback islemi yapilamadi.\nHata mesaji: " + e.getMessage();
			}
		}
	}
	
	
	public String getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getEmployeeSurname() {
		return employeeSurname;
	}


	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}


	public String getEmployeeSalary() {
		return employeeSalary;
	}


	public void setEmployeeSalary(String employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}



	public Part getEmployeeImage() {
		return employeeImage;
	}



	public void setEmployeeImage(Part employeeImage) {
		this.employeeImage = employeeImage;
	}
	
		
	
}
