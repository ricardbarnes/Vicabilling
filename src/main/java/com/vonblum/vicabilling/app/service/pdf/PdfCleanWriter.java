package com.vonblum.vicabilling.app.service.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

import java.io.IOException;

public class PdfCleanWriter {

    public void write(PDPageContentStream stream, String string) throws IOException {
        for (int i = 0; i < string.length(); i++) {
            if (WinAnsiEncoding.INSTANCE.contains(string.charAt(i))) {
                stream.showText(String.valueOf(string.charAt(i)));
            } else if (string.charAt(i) == '\n' || string.charAt(i) == '\r') {
                stream.newLine();
            }
        }
    }

}
