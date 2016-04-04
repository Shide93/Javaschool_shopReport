package com.tsystems.javaschool.report.ejb;

import com.itextpdf.text.DocumentException;
import com.tsystems.javaschool.report.ejb.dto.Report;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Created by Shide on 23.03.2016.
 */
public interface ReportService {

    /**
     * Gets shop report.
     *
     * @param dateFrom    the date from
     * @param maxUsers    the max users
     * @param maxProducts the max products
     * @param accessToken the access token
     * @return the shop report
     */
    Report getShopReport(Date dateFrom,
                         Integer maxUsers,
                         Integer maxProducts,
                         String accessToken);

    /**
     * Generate pdf.
     *
     * @param report   the report
     * @param dateFrom the date from
     * @param path     the path
     * @throws DocumentException     the document exception
     * @throws FileNotFoundException the file not found exception
     */
    void generatePDF(Report report, Date dateFrom, String path)
            throws DocumentException, FileNotFoundException;
}
