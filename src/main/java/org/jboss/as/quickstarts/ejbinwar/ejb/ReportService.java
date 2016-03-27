package org.jboss.as.quickstarts.ejbinwar.ejb;

import com.itextpdf.text.DocumentException;
import org.jboss.as.quickstarts.ejbinwar.ejb.dto.Report;
import org.primefaces.model.StreamedContent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by Shide on 23.03.2016.
 */
public interface ReportService {

    Report getShopReport(Date dateFrom,
                     Integer maxUsers,
                     Integer maxProducts);

    void generatePDF(Report report, Date dateFrom, String path)
            throws DocumentException, FileNotFoundException;
}
