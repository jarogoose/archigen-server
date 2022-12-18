package org.jarogoose.archigen.web.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class AuditMetadata {
    @CreatedDate
    @Field(name = "create_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "update_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Field(name = "create_by")
    private String createdBy;

    @LastModifiedBy
    @Field(name = "update_by")
    private String updatedBy;
}
