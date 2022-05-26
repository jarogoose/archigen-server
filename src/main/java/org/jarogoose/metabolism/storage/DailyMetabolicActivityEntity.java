package org.jarogoose.metabolism.storage;

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
@Document(collection = "daily_metabolic_activity")
class DailyMetabolicActivityEntity {
  @MongoId(FieldType.OBJECT_ID)
  private String id;
  @Field("user_id")
  private String userId;
  private String date;
  private String fat;
  private String carb;
  private String protein;
  private String fiber;
  private String water;
  private String sleep;
}
