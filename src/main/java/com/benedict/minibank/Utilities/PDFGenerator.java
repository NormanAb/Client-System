package com.benedict.minibank.Utilities;

import com.benedict.minibank.Models.Client;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Models.Report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PDFGenerator {

    private static final Logger logger = Logger.getLogger(PDFGenerator.class.getName());

    /**
     * Generates a PDF for the given Report using the provided graph snapshots and returns its bytes.
     */
    public static byte[] generateReportPDF(Report report, BufferedImage barChartImage, BufferedImage pieChartImage) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // ========== HEADER SECTION ==========
                // Title (bold, larger font)
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Report: " + report.getName());
                contentStream.endText();

                // Sub-title (date)
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Date: " + report.getDate());
                contentStream.endText();

                // Draw a thin divider line under the header
                drawLine(contentStream, 50, 715, 550, 715);

                // ========== STATISTICS SECTION ==========
                float currentY = 690;
                currentY = addTextStatistics(contentStream, currentY);

                // ========== IMAGES SECTION ==========
                // Add bar chart image if available
                if (barChartImage != null) {
                    PDImageXObject barChartXImage = LosslessFactory.createFromImage(document, barChartImage);
                    float imageWidth = 300;
                    float imageHeight = 200;

                    // Center it (for an 8.5" wide page ~ 612px wide minus left margin)
                    float xPosition = 50 + (500 - imageWidth) / 2; // adjust to taste
                    contentStream.drawImage(barChartXImage, xPosition, currentY - imageHeight, imageWidth, imageHeight);
                    currentY -= (imageHeight + 40); // Extra spacing after image
                }

                // Add pie chart image if available
                if (pieChartImage != null) {
                    PDImageXObject pieChartXImage = LosslessFactory.createFromImage(document, pieChartImage);
                    float imageWidth = 300;
                    float imageHeight = 200;

                    float xPosition = 50 + (500 - imageWidth) / 2;
                    contentStream.drawImage(pieChartXImage, xPosition, currentY - imageHeight, imageWidth, imageHeight);
                    currentY -= (imageHeight + 20);
                }
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
     * Generates a PDF for the given Report using the provided graph snapshots and saves it to a file.
     * Returns the absolute file path if successful.
     */
    public static String saveReportPDFToFile(Report report, BufferedImage barChartImage, BufferedImage pieChartImage, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // ========== HEADER SECTION ==========
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Report: " + report.getName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Date: " + report.getDate());
                contentStream.endText();

                drawLine(contentStream, 50, 715, 550, 715);

                float currentY = 690;
                currentY = addTextStatistics(contentStream, currentY);

                if (barChartImage != null) {
                    PDImageXObject barChartXImage = LosslessFactory.createFromImage(document, barChartImage);
                    float imageWidth = 300;
                    float imageHeight = 200;

                    float xPosition = 50 + (500 - imageWidth) / 2;
                    contentStream.drawImage(barChartXImage, xPosition, currentY - imageHeight, imageWidth, imageHeight);
                    currentY -= (imageHeight + 40);
                }

                if (pieChartImage != null) {
                    PDImageXObject pieChartXImage = LosslessFactory.createFromImage(document, pieChartImage);
                    float imageWidth = 300;
                    float imageHeight = 200;

                    float xPosition = 50 + (500 - imageWidth) / 2;
                    contentStream.drawImage(pieChartXImage, xPosition, currentY - imageHeight, imageWidth, imageHeight);
                    currentY -= (imageHeight + 20);
                }
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

    /**
     * Writes the text-based statistics into the PDF and returns the updated Y coordinate.
     * (We have REMOVED the "New Clients in Last 7 Days" section here.)
     */
    private static float addTextStatistics(PDPageContentStream contentStream, float currentY) throws IOException {
        int totalClients = Model.getInstance().getClients().size();
        int activeClients = (int) Model.getInstance().getClients().stream()
                .filter(client -> "ACTIVE".equalsIgnoreCase(client.getStatus()))
                .count();

        // Title "Statistics" in bold
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, currentY);
        contentStream.showText("Statistics:");
        contentStream.endText();
        currentY -= 25;

        // Total Clients
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(60, currentY);
        contentStream.showText("Total Clients: " + totalClients);
        contentStream.endText();
        currentY -= 18;

        // Active Clients
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(60, currentY);
        contentStream.showText("Active Clients: " + activeClients);
        contentStream.endText();
        currentY -= 18;

        // Title for breakdown
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(60, currentY);
        contentStream.showText("Client Breakdown by Status:");
        contentStream.endText();
        currentY -= 18;

        // Print the status breakdown in bullet-like lines
        Map<String, Long> statusCounts = Model.getInstance().getClients().stream()
                .collect(Collectors.groupingBy(Client::getStatus, Collectors.counting()));
        for (Map.Entry<String, Long> entry : statusCounts.entrySet()) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(80, currentY);
            contentStream.showText("• " + entry.getKey() + ": " + entry.getValue());
            contentStream.endText();
            currentY -= 16;
        }

        return currentY - 10;
    }

    /**
     * Helper method to draw a line between two points.
     */
    private static void drawLine(PDPageContentStream contentStream, float startX, float startY, float endX, float endY) throws IOException {
        contentStream.setStrokingColor(Color.GRAY); // a subtle gray line
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(endX, endY);
        contentStream.stroke();
    }
}
