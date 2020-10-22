package com.vonblum.vicabilling.app.service.pdf.component;

import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.data.model.Company;
import com.vonblum.vicabilling.app.data.model.Customer;
import com.vonblum.vicabilling.app.data.model.Logo;
import com.vonblum.vicabilling.app.service.DateTool;
import com.vonblum.vicabilling.app.service.pdf.PdfCleanWriter;
import com.vonblum.vicabilling.app.service.pdf.PdfConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class PdfHeaderWriter {

    private final ResourceBundle bundle;
    private final Bill bill;
    private final PDDocument document;
    private final PdfCleanWriter writer;

    private PDPageContentStream stream;

    private void addBillData() throws IOException {
        Bill.Type billType = bill.getType();

        String type = bundle.getString(billType.name().toUpperCase());
        String number = String.valueOf(bill.getNumber());
        String date = DateTool.getDefaultLocaleDate(bill.getDate());

        String datePre = bundle.getString("Date");
        String numberPre = bundle.getString("Number");

        String text = datePre + ": " + date + "\n" +
                numberPre + ": " + number;

        PDFont typeFont = PDType1Font.HELVETICA_BOLD;
        PDFont customerFont = PDType1Font.HELVETICA;

        float typeFontSize = 12f;
        float fontSize = 10f;
        float leading = 14f;
        float x = 310f;
        float y = 700f;

        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = PdfConfig.Y_MARGIN + y;

        stream.setFont(typeFont, typeFontSize);
        stream.setLeading(leading);
        stream.beginText();
        stream.newLineAtOffset(totalX, totalY);
        writer.write(stream, type);
        stream.newLine();
        stream.endText();

        stream.setFont(customerFont, fontSize);
        stream.setLeading(leading);
        stream.beginText();
        stream.newLineAtOffset(totalX, totalY - leading * 2);
        writer.write(stream, text);
        stream.newLine();
        stream.endText();
    }

    private void addCompanyLogo() throws IOException {
        File logoFile = new File(Logo.getFilepath());

        if (logoFile.exists()) {
            PDImageXObject pdImage = PDImageXObject.createFromFile(Logo.getFilepath(), document);
            stream.drawImage(pdImage, PdfConfig.X_MARGIN, 630);
        }
    }

    private void addCompanyInfo() throws IOException {
        Company company = bill.getCompany();

        String name = company.getName();
        String phone = company.getPhone();
        String email = company.getEmail();
        String website = company.getWebsite();
        String address = company.getAddress();

        String text = name + "\n" +
                website + "\n" +
                email + " - " + phone + "\n" +
                address;

        PDFont font = PDType1Font.HELVETICA;

        float leading = 12f;
        float fontSize = 10f;
        float x = 0f;
        float y = 575f;

        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = PdfConfig.Y_MARGIN + y;

        stream.setFont(font, fontSize);
        stream.setLeading(leading);
        stream.beginText();
        stream.newLineAtOffset(totalX, totalY);
        writer.write(stream, text);
        stream.endText();
    }

    private void addCustomerInfo() throws IOException {
        Customer customer = bill.getCustomer();

        String name = customer.getName();
        String phone = customer.getPhone();
        String email = customer.getEmail();
        String address = customer.getAddress();
        String webpage = customer.getWebpage();

        String text = name + "\n" +
                address;

        PDFont font = PDType1Font.HELVETICA;

        float fontSize = 10.5f;
        float leading = 12f;
        float x = 310f;
        float y = 575f;

        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = PdfConfig.Y_MARGIN + y;

        stream.setFont(font, fontSize);
        stream.setLeading(leading);
        stream.beginText();
        stream.newLineAtOffset(totalX, totalY);
        writer.write(stream, text);
        stream.endText();
    }

    public PdfHeaderWriter(ResourceBundle bundle, Bill bill, PDDocument document) {
        this.bundle = bundle;
        this.bill = bill;
        this.document = document;
        this.writer = new PdfCleanWriter();
    }

    public void writeHeader(PDPage page) throws IOException {
        stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        addCompanyLogo();
        addCompanyInfo();
        addBillData();
        addCustomerInfo();
        stream.close();
    }

}
