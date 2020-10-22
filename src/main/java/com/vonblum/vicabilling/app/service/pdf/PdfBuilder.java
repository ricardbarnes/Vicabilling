package com.vonblum.vicabilling.app.service.pdf;

import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.Bill;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class PdfBuilder {

    private final ResourceBundle bundle;
    private final File file;
    private final Bill bill;
    private final PdfCleanWriter writer;

    private PDDocument document;

    private void saveDocument() throws IOException, ProgramException {
        try {
            document.save(file);
        } catch (FileNotFoundException exception) {
            throw new ProgramException(exception.getMessage());
        }
    }

    private void closeDocument() throws IOException {
        document.close();
    }

    private void addPagesAndPageNumberToDocument() throws IOException {
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            addPageNumber(i);
        }
    }

    private void addPageNumber(int currentPageIdx) throws IOException {
        PDPageContentStream stream = new PDPageContentStream(
                document,
                document.getPage(currentPageIdx),
                PDPageContentStream.AppendMode.APPEND,
                true
        );
        int totalPages = document.getNumberOfPages();

        String pageStr = bundle.getString("Page");
        String ofStr = bundle.getString("of");

        String text = pageStr + " " +
                (currentPageIdx + 1) + " " +
                ofStr + " " +
                totalPages;

        PDFont font = PDType1Font.HELVETICA;

        float leading = 14f;
        float fontSize = 10f;
        float x = 460f;
        float y = 730f;

        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = PdfConfig.Y_MARGIN + y;

        stream.setFont(font, fontSize);
        stream.setLeading(leading);
        stream.setNonStrokingColor(Color.BLACK);
        stream.beginText();
        stream.newLineAtOffset(totalX, totalY);
        writer.write(stream, text);
        stream.endText();
        stream.close();
    }

    private void addMetadata() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bill.getDate());

        PDDocumentInformation info = document.getDocumentInformation();
        info.setTitle(PdfConfig.getPDFTitle(bill, bundle));
        info.setAuthor(bill.getCompany().getName());
        info.setProducer(bill.getCompany().getName());
        info.setCreator(bill.getCompany().getName());
        info.setKeywords(bill.getType().name().toLowerCase());
        info.setCreationDate(calendar);
        info.setModificationDate(calendar);
        info.setSubject(bill.getType().name().toUpperCase());
    }

    private void createBudget() throws IOException { // Both are the same right now. Only the type name is different.
        PdfPageInvoiceWriter writer = new PdfPageInvoiceWriter(bundle, bill);
        writer.createDocument();
        document = writer.getDocument();
    }

    private void createInvoice() throws IOException {
        PdfPageInvoiceWriter writer = new PdfPageInvoiceWriter(bundle, bill);
        writer.createDocument();
        document = writer.getDocument();
    }

    private void createBill() throws IOException {
        if (bill.getType() == Bill.Type.BUDGET) {
            createBudget();
        } else {
            createInvoice();
        }
    }

    public PdfBuilder(Bill bill, File file, ResourceBundle bundle) {
        this.file = file;
        this.bill = bill;
        this.bundle = bundle;
        this.document = new PDDocument();
        this.writer = new PdfCleanWriter();
    }

    public boolean buildBill() throws ProgramException {
        boolean isBillCreated = false;

        try {
            createBill();
            addPagesAndPageNumberToDocument();
            addMetadata();
            saveDocument();
            closeDocument();

            isBillCreated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isBillCreated;
    }

}
