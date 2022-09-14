package org.jarogoose.archigen.web.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "configs")
public class ConfigEntity {

  @MongoId(FieldType.OBJECT_ID)
  private String id;
  private String artefact;
  private String project;
  @Field("base_dir")
  private String baseDir;
}