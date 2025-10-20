package org.filewriter.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FileInfo {
    private UUID id;
    private String originalName;
    private String storedName;
    private long size;
    private String contentType;
    private LocalDateTime uploadDate;
    private LocalDateTime lastDownload;
    private int downloadCount;
    private String uploaderId;

    public FileInfo(UUID id, String originalName, String storedName, long size, String contentType, LocalDateTime uploadDate, LocalDateTime lastDownload, int downloadCount, String uploaderId) {
        this.id = id;
        this.originalName = originalName;
        this.storedName = storedName;
        this.size = size;
        this.contentType = contentType;
        this.uploadDate = uploadDate;
        this.lastDownload = lastDownload;
        this.downloadCount = downloadCount;
        this.uploaderId = uploaderId;
    }

    public FileInfo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LocalDateTime getLastDownload() {
        return lastDownload;
    }

    public void setLastDownload(LocalDateTime lastDownload) {
        this.lastDownload = lastDownload;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfo fileInfo = (FileInfo) o;
        return Objects.equals(id, fileInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", originalName='" + originalName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", uploadDate=" + uploadDate +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
