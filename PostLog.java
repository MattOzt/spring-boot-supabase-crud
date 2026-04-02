package com.example.adp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PostLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long postId;
    private String processedByStudent = "4310998";
    private String processedByApplication = System.getProperty("user.name");
    private long timestamp = System.currentTimeMillis();
    public PostLog() {}
    public PostLog(long postId) { this.postId = postId; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getProcessedByStudent() {
        return processedByStudent;
    }

    public void setProcessedByStudent(String processedByStudent) {
        this.processedByStudent = processedByStudent;
    }

    public String getProcessedByApplication() {
        return processedByApplication;
    }

    public void setProcessedByApplication(String processedByApplication) {
        this.processedByApplication = processedByApplication;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}