package com.xodesito.kumareports.report;

import com.xodesito.kumareports.KumaReports;
import com.xodesito.kumareports.mongodb.MongoManager;

import java.util.UUID;

public class ReportManager {

    private final KumaReports plugin;
    private final MongoManager mongoManager;

    public ReportManager(KumaReports plugin) {
        this.plugin = plugin;
        this.mongoManager = plugin.getMongoManager();
    }

    public void createReport(Report report) {
        mongoManager.insertReport(report.toDocument());
    }

    public void deleteReport(Report report) {
        mongoManager.deleteReport(report.toDocument());
    }

    public void updateReport(Report report) {
        mongoManager.updateReport(report.toDocument());
    }

    public Report getReport(Report report) {
        return (Report) mongoManager.getReport(report.toDocument());
    }

    public Report getReportByReportedUuid(String reportedUuid) {
        return (Report) mongoManager.getReport(new Report(UUID.fromString(reportedUuid), null, null).toDocument());
    }

    public Report getReportByReporterUuid(String reporterUuid) {
        return (Report) mongoManager.getReport(new Report(null, null, reporterUuid).toDocument());
    }
}
