package com.bilgeadam.hr.employees.servletler;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bilgeadam.hr.employees.model.Calisan;

/**
 * Servlet implementation class ResimWebHizmeti
 */
@WebServlet("/ResimWebHizmeti")
public class ResimWebHizmeti extends HttpServlet 
{
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResimWebHizmeti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String calisanTC = request.getParameter("id");
		
		Calisan calisan = entityManager.find(Calisan.class, calisanTC);
		
		if(calisan != null)
		{
			response.setContentLength( calisan.getGorsel().length );
			//response.setHeader("Content-type" , calisan.getGorselTuru());
			response.setContentType( calisan.getGorselTuru() );
		
			response.getOutputStream().write( calisan.getGorsel() );
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
