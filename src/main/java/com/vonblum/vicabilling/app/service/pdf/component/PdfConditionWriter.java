package com.vonblum.vicabilling.app.service.pdf.component;

import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.service.pdf.PdfCleanWriter;
import com.vonblum.vicabilling.app.service.pdf.PdfConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ResourceBundle;

public class PdfConditionWriter {

    private final ResourceBundle bundle;
    private final Bill bill;
    private final PDDocument document;
    private final PdfCleanWriter writer;

    public PdfConditionWriter(ResourceBundle bundle, Bill bill, PDDocument document) {
        this.bundle = bundle;
        this.bill = bill;
        this.document = document;
        this.writer = new PdfCleanWriter();
    }

    public void writeCondition(PDPage page) throws IOException {
        PDPageContentStream stream =
                new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

        String text = bill.getConditions();
        PDFont font = PDType1Font.HELVETICA;

        float fontSize = 10f;
        float leading = 14f;
        float x = 0f;
        float y = 60f;

        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = PdfConfig.Y_MARGIN + y;

        stream.setFont(font, fontSize);
        stream.setLeading(leading);
        stream.beginText();
        stream.newLineAtOffset(totalX, totalY);
        writer.write(stream, text);
        stream.endText();
        stream.close();
    }

}
