package com.vonblum.vicabilling.app.service.pdf.component;

import com.vonblum.vicabilling.app.service.pdf.PdfCleanWriter;
import com.vonblum.vicabilling.app.service.pdf.PdfConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfThreePointWriter {

    private final PDDocument document;
    private final PdfCleanWriter writer;

    public PdfThreePointWriter(PDDocument document) {
        this.document = document;
        this.writer = new PdfCleanWriter();
    }

    public void writePoints(PDPage page) throws IOException {
        PDPageContentStream stream =
                new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

        String text = "...";
        PDFont font = PDType1Font.HELVETICA;

        float fontSize = 12f;
        float leading = 14f;
        float x = 470f;
        float y = 50f;

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
