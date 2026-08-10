package com.example.lambda_upload_document_service.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.lambda_upload_document_service.model.UploadEvent;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DocumentUploadRepository {

    private final HikariDataSource dataSource;

    public DocumentUploadRepository() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));       // e.g. jdbc:mysql://<rds-endpoint>:3306/doctor_service_db
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASSWORD"));
        config.setMaximumPoolSize(2);       // keep small — each Lambda container gets its own pool
        config.setMinimumIdle(0);
        config.setConnectionTimeout(5000);
        config.setIdleTimeout(10000);
        this.dataSource = new HikariDataSource(config);
    }

    public void saveOrUpdateUpload(UploadEvent event) throws SQLException {
        String sql = """
            INSERT INTO doctor_documents
                (document_key, doctor_id, bucket, size_bytes, status, uploaded_at)
            VALUES (?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                status = VALUES(status),
                size_bytes = VALUES(size_bytes),
                uploaded_at = VALUES(uploaded_at)
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getKey());
            ps.setObject(2, event.getUser_id());   // nullable if key parsing fails
            ps.setString(3, event.getBucket());
            ps.setLong(4, event.getFileSize());
            ps.setString(5, "UPLOADED");
            ps.setString(6, event.getUploadTime().toString());
            ps.executeUpdate();
        }
    }
}


