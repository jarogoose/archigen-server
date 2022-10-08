package org.jarogoose.archigen.web.config.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
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

  @Field("project_name")
  @Indexed(unique = true)
  private String projectName;

  private String artefact;
  private String project;

  @Field("base_dir")
  private String baseDir;

  private String author;
}
