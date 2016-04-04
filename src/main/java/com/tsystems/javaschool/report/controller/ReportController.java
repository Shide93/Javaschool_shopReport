package com.tsystems.javaschool.report.controller;

import com.itextpdf.text.DocumentException;
import com.tsystems.javaschool.report.ejb.ReportService;
import com.tsystems.javaschool.report.ejb.dto.Report;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * The type Report controller.
 */
@ManagedBean
@SessionScoped
public class ReportController implements Serializable {
    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(ReportController.class);

    /**
     * The Report.
     */
    private Report report;

    /**
     * The Date from.
     */
    private Date dateFrom;
    /**
     * The Top users count.
     */
    private int topUsersCount;
    /**
     * The Top products count.
     */
    private int topProductsCount;
    /**
     * The Access token.
     */
    private String accessToken;
    /**
     * The Pie chart.
     */
    private PieChartModel pieChart;
    /**
     * The Fmt.
     */
    private SimpleDateFormat fmt
            = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * The Err msg.
     */
    private String errMsg;

    /**
     * The Report service.
     */
    @EJB
    private ReportService reportService;

    /**
     * Show report string.
     *
     * @return the string
     */
    public String showReport() {
        report = reportService.getShopReport(this.dateFrom, this.topUsersCount,
                this.topProductsCount, this.accessToken);
        if (report == null) {
            errMsg = "Wrong or expired token!";
            return "index";
        }
        return "report";

    }

    /**
     * Gets report.
     *
     * @return the report
     */
    public Report getReport() {
        return report;
    }

    /**
     * Sets report.
     *
     * @param report the report
     */
    public void setReport(final Report report) {
        this.report = report;
    }

    /**
     * Gets pdf.
     *
     * @return the pdf
     */
    public StreamedContent getPdf() {

        String path = "resources/report.pdf";
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        try {
            reportService.generatePDF(report, dateFrom, context.getRealPath("/" + path));
        } catch (DocumentException | FileNotFoundException e) {
            LOGGER.error("PDF generation exception", e);
        }

        InputStream stream = context.getResourceAsStream(path);

        return new DefaultStreamedContent(stream, "application/pdf",
                "report.pdf");
    }

    /**
     * Gets date from.
     *
     * @return the date from
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets date from.
     *
     * @param dateFrom the date from
     */
    public void setDateFrom(final Date dateFrom) {
            this.dateFrom = dateFrom;
    }

    /**
     * Gets top products count.
     *
     * @return the top products count
     */
    public int getTopProductsCount() {
        return topProductsCount;
    }

    /**
     * Sets top products count.
     *
     * @param topProductsCount the top products count
     */
    public void setTopProductsCount(final int topProductsCount) {
        this.topProductsCount = topProductsCount;
    }

    /**
     * Gets top users count.
     *
     * @return the top users count
     */
    public int getTopUsersCount() {
        return topUsersCount;
    }

    /**
     * Sets top users count.
     *
     * @param topUsersCount the top users count
     */
    public void setTopUsersCount(final int topUsersCount) {
        this.topUsersCount = topUsersCount;
    }

    /**
     * Gets formatted date.
     *
     * @return the formatted date
     */
    public String getFormattedDate() {
        if (dateFrom == null) {
            return null;
        }
        return fmt.format(dateFrom);
    }

    /**
     * Gets pie chart.
     *
     * @return the pie chart
     */
    public PieChartModel getPieChart() {
        pieChart = new PieChartModel();
        for (Map.Entry<String, Integer> status
                : report.getOrdersPerStatus().entrySet()) {
            pieChart.set(status.getKey(), status.getValue());
        }
        pieChart.setTitle("Orders per status");
        pieChart.setShowDataLabels(true);
        pieChart.setShowDatatip(true);
        return pieChart;
    }

    /**
     * Sets pie chart.
     *
     * @param pieChart the pie chart
     */
    public void setPieChart(final PieChartModel pieChart) {
        this.pieChart = pieChart;
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets err msg.
     *
     * @return the err msg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * Sets err msg.
     *
     * @param errMsg the err msg
     */
    public void setErrMsg(final String errMsg) {
        this.errMsg = errMsg;
    }
}
