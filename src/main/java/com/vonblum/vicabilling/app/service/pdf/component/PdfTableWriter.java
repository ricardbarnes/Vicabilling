package com.vonblum.vicabilling.app.service.pdf.component;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import com.vonblum.vicabilling.app.data.model.Product;
import com.vonblum.vicabilling.app.service.StringTool;
import com.vonblum.vicabilling.app.service.pdf.PdfConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PdfTableWriter {

    private final PDDocument document;

    public PdfTableWriter(PDDocument document) {
        this.document = document;
    }

    public void writeTable(PDPage page, List<Product> products) throws IOException {
        String reference = StringTool.putSpaces(
                PdfTableHeaderConfig.REFERENCE_HEADER.getDisplayName(),
                PdfTableHeaderConfig.REFERENCE_HEADER.getMaxHeaderChars()
        );
        String description = StringTool.putSpaces(
                PdfTableHeaderConfig.DESCRIPTION_HEADER.getDisplayName(),
                PdfTableHeaderConfig.DESCRIPTION_HEADER.getMaxHeaderChars()
        );
        String quantity = StringTool.putSpaces(
                PdfTableHeaderConfig.QUANTITY_HEADER.getDisplayName(),
                PdfTableHeaderConfig.QUANTITY_HEADER.getMaxHeaderChars()
        );
        String unitPrice = StringTool.putSpaces(
                PdfTableHeaderConfig.U_PRICE_HEADER.getDisplayName(),
                PdfTableHeaderConfig.U_PRICE_HEADER.getMaxHeaderChars()
        );
        String unitSymbol = StringTool.putSpaces(
                PdfTableHeaderConfig.UNIT_SYMBOL_HEADER.getDisplayName(),
                PdfTableHeaderConfig.UNIT_SYMBOL_HEADER.getMaxHeaderChars()
        );
        String discount = StringTool.putSpaces(
                PdfTableHeaderConfig.DISCOUNT_HEADER.getDisplayName(),
                PdfTableHeaderConfig.DISCOUNT_HEADER.getMaxHeaderChars()
        );
        String price = StringTool.putSpaces(
                PdfTableHeaderConfig.PRICE_HEADER.getDisplayName(),
                PdfTableHeaderConfig.PRICE_HEADER.getMaxHeaderChars()
        );

        List<List<String>> data = new ArrayList<>();
        data.add(new ArrayList<>(
                Arrays.asList(
                        reference,
                        description,
                        quantity,
                        unitPrice,
                        unitSymbol,
                        discount,
                        price
                )
        ));

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("ca", "ES"));

        for (Product product : products) {
            data.add(new ArrayList<>(
                    Arrays.asList(
                            StringTool.cutStringToMaxWithEtc(product.getReference(), PdfTableHeaderConfig.REFERENCE_HEADER.getMaxHeaderChars() - 6),
                            StringTool.cutStringToMaxWithEtc(product.getDescription(), PdfTableHeaderConfig.DESCRIPTION_HEADER.getMaxHeaderChars() - 10),
                            StringTool.cutStringToMaxWithEtc(numberFormat.format(product.getQuantity()), PdfTableHeaderConfig.QUANTITY_HEADER.getMaxHeaderChars()),
                            StringTool.cutStringToMaxWithEtc(numberFormat.format(product.getUnitPrice()), PdfTableHeaderConfig.U_PRICE_HEADER.getMaxHeaderChars() + 2),
                            StringTool.cutStringToMaxWithEtc(product.getUnitSymbol(), PdfTableHeaderConfig.U_PRICE_HEADER.getMaxHeaderChars()),
                            StringTool.cutStringToMaxWithEtc(numberFormat.format(product.getDiscountPercentage()), PdfTableHeaderConfig.DISCOUNT_HEADER.getMaxHeaderChars()),
                            StringTool.cutStringToMaxWithEtc(numberFormat.format(product.getTotalPrice()), PdfTableHeaderConfig.PRICE_HEADER.getMaxHeaderChars())
                    )
            ));
        }

        PDFont boldFont = PDType1Font.HELVETICA_BOLD;
        PDFont normalFont = PDType1Font.HELVETICA;

        float fontSize = 9.5f;
        float x = 0f;
        float y = 490f;
        float width = 510f;
        float bottomExtraMargin = 60f;

        float marginY = PdfConfig.Y_MARGIN;
        float totalX = PdfConfig.X_MARGIN + x;
        float totalY = marginY + y;
        float totalYNewPage = marginY + y;
        float bottomMargin = PdfConfig.Y_MARGIN + bottomExtraMargin;

        BaseTable baseTable = new BaseTable(
                totalY,
                totalYNewPage,
                bottomMargin,
                width,
                totalX,
                document,
                page,
                true,
                true
        );

        DataTable table = new DataTable(baseTable, page);
        table.getTable().removeAllBorders(true);
        table.getHeaderCellTemplate().setFillColor(new Color(204, 224, 255));
        table.getHeaderCellTemplate().setFont(boldFont);
        table.getHeaderCellTemplate().setFontSize(fontSize);
        table.getDataCellTemplateEven().setFillColor(Color.WHITE);
        table.getDataCellTemplateEven().setFont(normalFont);
        table.getDataCellTemplateEven().setFontSize(fontSize);
        table.getDataCellTemplateOdd().setFillColor(new Color(230, 240, 255));
        table.getDataCellTemplateOdd().setFont(normalFont);
        table.getDataCellTemplateOdd().setFontSize(fontSize);
        table.addListToTable((List) data, DataTable.HASHEADER); // This API needs a generic list
        baseTable.draw();
    }

}
