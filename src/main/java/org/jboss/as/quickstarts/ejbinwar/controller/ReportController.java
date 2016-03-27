package org.jboss.as.quickstarts.ejbinwar.controller;

import com.itextpdf.text.DocumentException;
import org.jboss.as.quickstarts.ejbinwar.ejb.ReportService;
import org.jboss.as.quickstarts.ejbinwar.ejb.dto.Report;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Shide on 26.03.2016.
 */
@ManagedBean
@SessionScoped
public class ReportController implements Serializable {

    private Report report;

    private Date dateFrom;
    private int topUsersCount;
    private int topProductsCount;
    private SimpleDateFormat fmt
            = new SimpleDateFormat("dd-MM-yyyy");

    @EJB
    private ReportService reportService;

    public String showReport() {
        report = reportService.getShopReport(this.dateFrom, this.topUsersCount,
                this.topProductsCount);
        return "report";
    }

    public Report getReport() {
        return report;
    }

    public void setReport(final Report report) {
        this.report = report;
    }

    public StreamedContent getPdf() {

        String path = "/resources/report.pdf";
        ServletContext context = ((ServletContext)FacesContext.getCurrentInstance()
                .getExternalContext().getContext());
        try {
            reportService.generatePDF(report, dateFrom, context.getRealPath(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStream stream = context.getResourceAsStream(path);

        StreamedContent file = new DefaultStreamedContent(stream, "application/pdf",
                "report.pdf");
        return file;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(final Date dateFrom) {
            this.dateFrom = dateFrom;
    }

    public int getTopProductsCount() {
        return topProductsCount;
    }

    public void setTopProductsCount(final int topProductsCount) {
        this.topProductsCount = topProductsCount;
    }

    public int getTopUsersCount() {
        return topUsersCount;
    }

    public void setTopUsersCount(final int topUsersCount) {
        this.topUsersCount = topUsersCount;
    }
    public String getFormattedDate() {
        if(dateFrom == null) {
            return null;
        }
        return fmt.format(dateFrom);
    }
}
