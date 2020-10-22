package com.vonblum.vicabilling.app.controller.helper;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.config.AppConstant;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.service.pdf.PdfBuilder;
import com.vonblum.vicabilling.app.service.pdf.PdfConfig;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class PdfHandler {

    private static final String PDF_FILES_REPR = "*.pdf";

    private final ResourceBundle bundle;

    private FileChooser.ExtensionFilter[] getExtensionFilters() {
        String pdfDesc = bundle.getString("PDF_Files");

        return new FileChooser.ExtensionFilter[]{
                new FileChooser.ExtensionFilter(pdfDesc, PDF_FILES_REPR),
        };
    }

    public PdfHandler(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public boolean openSavingDialog(Bill bill) throws ProgramException {
        File iniFolder = new File(AppConstant.getPdfFolder());

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(getExtensionFilters());
        if (iniFolder.exists()) {
            chooser.setInitialDirectory(iniFolder);
        }
        chooser.setInitialFileName(PdfConfig.getSaveFilename(bundle, bill));

        File selectedFile = chooser.showSaveDialog(Context.getPrimaryStage());
        AtomicBoolean isBillCreated = new AtomicBoolean(false);

        if (selectedFile != null) {
            PdfBuilder builder = new PdfBuilder(bill, selectedFile, bundle);
            isBillCreated.set(builder.buildBill());
        }

        return isBillCreated.get();
    }

}
