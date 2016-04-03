package com.tsystems.javaschool.report.ejb;

import com.itextpdf.text.DocumentException;
import com.tsystems.javaschool.report.ejb.dto.Report;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Created by Shide on 23.03.2016.
 */
public interface ReportService {

    Report getShopReport(Date dateFrom,
                         Integer maxUsers,
                         Integer maxProducts,
                         String accessToken);

    void generatePDF(Report report, Date dateFrom, String path)
            throws DocumentException, FileNotFoundException;
}
