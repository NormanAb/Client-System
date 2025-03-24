package com.benedict.minibank.Utilities;

import com.benedict.minibank.Models.Report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class PDFGenerator {

    private static final Logger logger = Logger.getLogger(PDFGenerator.class.getName());

    /**
     * Generates a PDF for the given Report and returns its bytes.
     */
    public static byte[] generateReportPDF(Report report) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Report Title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Report: " + report.getName());
                contentStream.endText();

                // Report Date
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Date: " + report.getDate().toString());
                contentStream.endText();

                // Additional Content – placeholder for statistics/graphs
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 680);
                contentStream.showText("This report contains client statistics and graphs.");
                contentStream.endText();
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            logger.info("PDF generated successfully for report id: " + report.getId());
            return out.toByteArray();
        } catch (IOException e) {
            logger.severe("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a PDF for the given Report and saves it to the specified file path.
     * Returns the absolute file path if successful.
     */
    public static String saveReportPDFToFile(Report report, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Report Title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Report: " + report.getName());
                contentStream.endText();

                // Report Date
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Date: " + report.getDate().toString());
                contentStream.endText();

                // Additional Content – placeholder for statistics/graphs
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 680);
                contentStream.showText("This report contains client statistics and graphs.");
                contentStream.endText();
            }
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            document.save(file);
            logger.info("PDF saved to file: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (IOException e) {
            logger.severe("Error saving PDF to file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
