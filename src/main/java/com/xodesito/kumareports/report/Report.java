package com.xodesito.kumareports.report;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Report {

    private String id;
    private UUID reportedUuid;
    private String reporterName;
    private String reason;
    private Date date;
    private ReportStatus status;

    public Report(UUID reportedUuid, String reporterName, String reason) {
        this.id = UUID.randomUUID().toString();
        this.reportedUuid = reportedUuid;
        this.reporterName = reporterName;
        this.reason = reason;
        this.date = new Date();
        this.status = ReportStatus.OPEN;
    }


    public Report() {
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("reportedUuid", reportedUuid.toString())
                .append("reporterName", reporterName)
                .append("reason", reason)
                .append("date", date);
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }


}
