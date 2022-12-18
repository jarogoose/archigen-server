package org.jarogoose.archigen.web.auth;

import org.springframework.data.mongodb.core.mapping.Document;
import org.jarogoose.archigen.web.common.AuditMetadata;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Users")
class UserEntity extends AuditMetadata {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private String role;
    private String status;
}
