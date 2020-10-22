package com.vonblum.vicabilling.app.service.pdf;

import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.service.pdf.component.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;
import java.util.ResourceBundle;

public class PdfPageInvoiceWriter {

    private final ResourceBundle bundle;
    private final PDDocument document;
    private final Bill bill;

    private void addHeader(PDPage page) throws IOException {
        PdfHeaderWriter headerWriter = new PdfHeaderWriter(bundle, bill, document);
        headerWriter.writeHeader(page);
    }

    private void addFooter(PDPage page) throws IOException {
        PdfFooterWriter footerWriter = new PdfFooterWriter(bundle, bill, document);
        footerWriter.writeFooter(page);
    }

    private void addConditions(PDPage page) throws IOException {
        PdfConditionWriter footerWriter = new PdfConditionWriter(bundle, bill, document);
        footerWriter.writeCondition(page);
    }

    private void addTotal(PDPage totalPage, float y) throws IOException {
        PdfTotalWriter totalWriter = new PdfTotalWriter(bundle, bill, document, totalPage);
        totalWriter.writeTotal(y);
    }

    private void buildTable() throws IOException {
        int nProducts = bill.getProducts().size();
        int maxRowsWithTotal = 15;
        int maxRowsNoTotal = 20;

        int unfittedRows = nProducts % maxRowsNoTotal;
        boolean hasExtraPage = unfittedRows > maxRowsWithTotal;
        boolean hasFitRows = nProducts > 0 && nProducts % maxRowsNoTotal == 0;
        int nFinalRows = getNFinalRows(unfittedRows, maxRowsWithTotal);

        PDPage page = new PDPage();
        document.addPage(page);

        PdfTableWriter tableWriter = new PdfTableWriter(document);
        tableWriter.writeTable(page, bill.getProducts());

        float rowHeight = 19f; // Just... guessed
        float maxTotalY = 450f;
        float totalY = maxTotalY - nFinalRows * rowHeight - rowHeight; // 1 row height as spacer

        if (hasExtraPage || hasFitRows) {
            PDPage extraPage = new PDPage();
            document.addPage(extraPage);
            totalY = maxTotalY;
        }

        int docPages = document.getNumberOfPages();
        if (docPages > 1) {
            int contSignPages = docPages - 1;

            if (hasExtraPage) {
                contSignPages -= 1;
            }

            PdfThreePointWriter writer = new PdfThreePointWriter(document);
            for (int i = 0; i < contSignPages; i++) {
                writer.writePoints(document.getPage(i));
            }
        }

        int lastPageIdx = document.getNumberOfPages() - 1;
        addTotal(document.getPage(lastPageIdx), totalY);
        addConditions(document.getPage(lastPageIdx));
    }

    private int getNFinalRows(int unfittedRows, int maxRowsWithTotal) {
        if (unfittedRows <= maxRowsWithTotal) {
            return unfittedRows;
        } else {
            return unfittedRows % maxRowsWithTotal;
        }
    }

    public PdfPageInvoiceWriter(ResourceBundle bundle, Bill bill) {
        this.bundle = bundle;
        this.bill = bill;
        this.document = new PDDocument();
    }

    public void createDocument() throws IOException {
        buildTable();

        for (PDPage page : document.getPages()) {
            addHeader(page);
            addFooter(page);
        }

    }

    public PDDocument getDocument() {
        return document;
    }
}
