package com.csvreader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateReport {

	public static void generateReport(Map<String, String> errorMap) throws FileNotFoundException, DocumentException {

		Document document = new Document();
		PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("Transaction reference");
		table.addCell("Failed Reason");
		table.addCell("Date/Time");

		table.setHeaderRows(1);
		PdfPCell[] cells = table.getRow(0).getCells();
		for (int j = 0; j < cells.length; j++) {
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}
		
		errorMap.forEach((k, v) -> {
			table.addCell(k);
			table.addCell(v);
			table.addCell(new PdfDate().toString());

		});

		PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
		document.open();
		document.add(table);
		document.close();
		System.out.println("Done");
	}

	
}
