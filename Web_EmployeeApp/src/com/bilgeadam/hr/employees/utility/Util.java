package com.bilgeadam.hr.employees.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;





import javax.servlet.http.Part;

import com.bilgeadam.hr.employees.model.Calisan;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public final class Util 
{
	private static Util INSTANCE;
	
	private Util() {
		// TODO Auto-generated constructor stub
	}
	
	public static Util getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Util();
		}
		
		return INSTANCE;
	}
	
	public void pdfAl(List<Calisan> calisanlar)
	{
		try
		{
			OutputStream file = new FileOutputStream(new File("example1.pdf"));
			
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			
			PdfPTable table1 = new PdfPTable(1);
			PdfPCell cell = new PdfPCell(new Paragraph("HR-CALISAN TABLOSU PDF"));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(cell);
			
			
			PdfPTable table2 = new PdfPTable(5);
			
			table2.addCell("TC: ");
			table2.addCell("AD: ");
			table2.addCell("SOYAD: ");
			table2.addCell("MAAS: ");
			table2.addCell("EKLENME: ");
			for (Calisan calisan : calisanlar) 
			{
				table2.addCell(calisan.getTC_no());
				table2.addCell(calisan.getIsim());
				table2.addCell(calisan.getSoyisim());
				table2.addCell(""+calisan.getMaas());
				table2.addCell(""+calisan.getEklenmeTarihi());
			}
			
			
			
			document.open();
			
			document.add(table1);
			document.add(table2);
			
			document.close();
			file.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception message: " + e.getMessage());
		}
	}

	public byte[] resmiDonustur(Part resim) throws IOException
	{
		byte[] yeniResim = new byte[(int)resim.getSize()];
		
		resim.getInputStream().read( yeniResim );
		
		return yeniResim;
	}
	
	
}
