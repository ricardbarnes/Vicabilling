package com.vonblum.vicabilling.app.service.pdf.component;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.datatable.DataTable;
import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.service.pdf.PdfConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class PdfTotalWriter {

    private final ResourceBundle bundle;
    private final Bill bill;
    private final PDDocument document;
    private final PDPage page;

    public PdfTotalWriter(ResourceBundle bundle, Bill bill, PDDocument document, PDPage totalPage) {
        this.bundle = bundle;
        this.bill = bill;
        this.document = document;
        this.page = totalPage;
    }

    public void writeTotal(float y) throws IOException {
        String taxName = bundle.getString("Total") + " " + bill.getTax().getSymbol();
        double taxPercent = bill.getTax().getCoefficient() * 100;
        double untaxedTotal = bill.getUntaxedTotal();
        double taxedTotal = bill.getTaxedTotal();
        double taxAmount = bill.getTaxAmount();

        PDFont normalFont = PDType1Font.HELVETICA;
        PDFont boldFont = PDType1Font.HELVETICA_BOLD;

        float x = 310f;
        float yNewPage = 90f;
        float width = 100f;
        float rowHeight = 15f;
        float cellWidth = 100f;
        float fontSize = 9.5f;

        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = PdfConfig.Y_MARGIN + y;
        float totalYNewPage = PdfConfig.Y_MARGIN + yNewPage;
        float bottomMargin = PdfConfig.Y_MARGIN + 40f;

        BaseTable table = new BaseTable(
                totalY,
                totalYNewPage,
                bottomMargin,
                width,
                totalX,
                document,
                page,
                true,
                true);

        Row<PDPage> subtotalRow = table.createRow(rowHeight);
        Row<PDPage> taxRow = table.createRow(rowHeight);
        Row<PDPage> totalRow = table.createRow(rowHeight);

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("ca", "ES"));

        // Subtotal row
        Cell<PDPage> subtotalTitleCell = subtotalRow.createCell(cellWidth, bundle.getString("Subtotal"));
        subtotalTitleCell.setFont(normalFont);
        subtotalTitleCell.setFontSize(fontSize);

        Cell<PDPage> subtotalCell = subtotalRow.createCell(cellWidth, numberFormat.format(untaxedTotal));
        subtotalCell.setFont(normalFont);
        subtotalCell.setFontSize(fontSize);

        // Tax row
        Cell<PDPage> taxTitleField = taxRow.createCell(cellWidth, taxName);
        taxTitleField.setFont(normalFont);
        taxTitleField.setFontSize(fontSize);

        Cell<PDPage> taxField = taxRow.createCell(cellWidth, numberFormat.format(taxAmount));
        taxField.setFont(normalFont);
        taxField.setFontSize(fontSize);

        // Total row
        Cell<PDPage> totalTitleCell = totalRow.createCell(cellWidth, bundle.getString("Total"));
        totalTitleCell.setFont(boldFont);
        totalTitleCell.setFontSize(fontSize);

        Cell<PDPage> totalCell = totalRow.createCell(cellWidth, numberFormat.format(taxedTotal));
        totalCell.setFont(boldFont);
        totalCell.setFontSize(fontSize);

        DataTable dataTable = new DataTable(table, page);
        dataTable.getTable().removeAllBorders(true);

        table.draw();
    }

}
