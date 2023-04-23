package com.xodesito.kumareports.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xodesito.kumareports.KumaReports;
import lombok.Getter;
import org.bson.Document;


@Getter
public class MongoManager {

    private final KumaReports plugin;
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> coll;

    public MongoManager(KumaReports plugin) {
        this.plugin = plugin;
        String mongoUrl = plugin.getConfig().getString("database.mongodb.url");
        mongoClient = MongoClients.create(mongoUrl);
        database = mongoClient.getDatabase("kumareports");
        coll = database.getCollection("reports");
    }

    public void close() {
        mongoClient.close();
    }

    public void insertReport(Document document) {
        coll.insertOne(document);
    }

    public void deleteReport(Document document) {
        coll.deleteOne(document);
    }

    public void updateReport(Document document) {
        coll.updateOne(document, new Document("$set", document));
    }

    public Object getReport(Document document) {
        return coll.find(document).first();
    }


}
