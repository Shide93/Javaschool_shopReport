package com.tsystems.javaschool.report.ejb;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsystems.javaschool.report.ejb.dto.Product;
import com.tsystems.javaschool.report.ejb.dto.Report;
import com.tsystems.javaschool.report.ejb.dto.User;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Shide on 26.03.2016.
 */
@Singleton
public class ReportServiceRestImpl implements ReportService {

    public static final String REST_SERVICE_ENTRY_POINT
            = "http://localhost:8080/api/";
    private SimpleDateFormat fmt
            = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Report getShopReport(final Date dateFrom,
                                final Integer topUsersCount,
                                final Integer topProductsCount,
                                final String accessToken) {

        Client client = ClientBuilder.newClient();
        WebTarget target =
                client.target(REST_SERVICE_ENTRY_POINT + "report/");
                if (dateFrom != null) {
                    target = target.queryParam("dateFrom",
                            fmt.format(dateFrom));
                }
                target = target.queryParam("topUsersCount", topUsersCount)
                .queryParam("topProductsCount", topProductsCount)
                .queryParam("accessToken", accessToken);
        Report report = target.request().get(Report.class);
        //TODO: handle webservice errors
        client.close();
        return report;
    }

    @Override
    public void generatePDF(final Report report, final Date dateFrom, final String path)
            throws DocumentException, FileNotFoundException {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(path));
        document.open();
        Font chapterFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,
                20, Font.BOLD);
        Font subHeaderFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,
                14, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,
                12, Font.NORMAL);
        Font cellHeaderFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,
                        12, Font.BOLD);
        float defaultSpacing = 20f;

        //Header
        Paragraph chapterParagraph = new Paragraph("Shop report", chapterFont);
        chapterParagraph.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter = new Chapter(chapterParagraph, 1);
        chapter.setNumberDepth(0);
        document.add(chapter);
        Paragraph periodParagraph;
        if (dateFrom != null) {
            periodParagraph = new Paragraph("Sales information for period from "
                    + fmt.format(dateFrom)
                    + " to "
                    + fmt.format(new Date(System.currentTimeMillis())),
                    subHeaderFont);
        } else {
            periodParagraph = new Paragraph("Sales information for full period", subHeaderFont);
        }
        periodParagraph.setAlignment(Element.ALIGN_CENTER);
        periodParagraph.setSpacingAfter(defaultSpacing * 2);
        document.add(periodParagraph);

        document.add(new Paragraph("Total orders: "
                + report.getPeriodOrders(), paragraphFont));
        document.add(new Paragraph("Total Sales: "
                + report.getPeriodSales(), paragraphFont));

        //Orders by statuses
        PdfPTable ordersPerStatus = new PdfPTable(2);
        ordersPerStatus.setHeaderRows(1);
        ordersPerStatus.setSpacingBefore(defaultSpacing);
        ordersPerStatus.addCell(new Phrase("Status", cellHeaderFont));
        ordersPerStatus.addCell(new Phrase("Orders", cellHeaderFont));
        for (Map.Entry<String, Integer> status
                : report.getOrdersPerStatus().entrySet()) {
            ordersPerStatus.addCell(status.getKey());
            ordersPerStatus.addCell(status.getValue().toString());
        }
        document.add(ordersPerStatus);
        //Top users table
        Paragraph usersParagraph = new Paragraph("Top users", subHeaderFont);
        usersParagraph.setAlignment(Element.ALIGN_CENTER);
        usersParagraph.setSpacingBefore(defaultSpacing);
        usersParagraph.setSpacingAfter(defaultSpacing);
        document.add(usersParagraph);
        PdfPTable topUsers = new PdfPTable(5);
        topUsers.addCell(new Phrase("Name", cellHeaderFont));
        topUsers.addCell(new Phrase("Email", cellHeaderFont));
        topUsers.addCell(new Phrase("Orders count", cellHeaderFont));
        topUsers.addCell(new Phrase("Total bought", cellHeaderFont));
        topUsers.addCell(new Phrase("% of total", cellHeaderFont));
        topUsers.setWidthPercentage(100);
        topUsers.setHeaderRows(1);
        for (User user : report.getTopUsers()) {
            topUsers.addCell(user.getName() + " "
                    + user.getLastName());
            topUsers.addCell(user.getEmail());
            topUsers.addCell("" + user.getOrdersCount());
            topUsers.addCell("" + user.getOrderTotal());
            topUsers.addCell("" + report.getSalesPercent(
                    (double) user.getOrderTotal()));

        }
        document.add(topUsers);

        //Top products table
        Paragraph productsParagraph = new Paragraph("Top products",
                subHeaderFont);
        productsParagraph.setAlignment(Element.ALIGN_CENTER);
        productsParagraph.setSpacingBefore(defaultSpacing);
        productsParagraph.setSpacingAfter(defaultSpacing);
        document.add(productsParagraph);
        PdfPTable topProducts = new PdfPTable(5);
        topProducts.addCell(new Phrase("Name", cellHeaderFont));
        topProducts.addCell(new Phrase("Price", cellHeaderFont));
        topProducts.addCell(new Phrase("Stocks count", cellHeaderFont));
        topProducts.addCell(new Phrase("Total sales", cellHeaderFont));
        topProducts.addCell(new Phrase("% of total", cellHeaderFont));
        topProducts.setWidthPercentage(100);
        topProducts.setHeaderRows(1);
        for (Product product : report.getTopProducts()) {
            topProducts.addCell(product.getName());
            topProducts.addCell(product.getPrice() + "");
            topProducts.addCell(product.getStock() + "");
            topProducts.addCell((product.getTotalSales()
                    * product.getPrice()) + "");
            topProducts.addCell(report.getSalesPercent(
                    (double) product.getTotalSales()
                            * product.getPrice()) + "");

        }
        document.add(topProducts);


        document.close();
    }
}
