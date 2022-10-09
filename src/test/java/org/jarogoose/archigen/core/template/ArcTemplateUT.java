package org.jarogoose.archigen.core.template;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.jarogoose.archigen.core.template.api.FacadeActionTemplate;
import org.jarogoose.archigen.core.template.api.FacadeSummaryTemplate;
import org.jarogoose.archigen.core.template.api.ServiceReadTemplate;
import org.jarogoose.archigen.core.template.api.ServiceWriteTemplate;
import org.jarogoose.archigen.core.template.control.ControllerActionTemplate;
import org.jarogoose.archigen.core.template.control.ControllerSummaryTemplate;
import org.jarogoose.archigen.core.template.domain.DtoMapperTemplate;
import org.jarogoose.archigen.core.template.domain.ExceptionTemplate;
import org.jarogoose.archigen.core.template.domain.DtoTemplate;
import org.jarogoose.archigen.core.template.domain.RequestTemplate;
import org.jarogoose.archigen.core.template.domain.ResponseTemplate;
import org.jarogoose.archigen.core.template.storage.EntityMapperTemplate;
import org.jarogoose.archigen.core.template.storage.EntityTemplate;
import org.jarogoose.archigen.core.template.storage.LoaderTemplate;
import org.jarogoose.archigen.core.template.storage.StorageTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Arc template")
public class ArcTemplateUT {

  @Nested
  @DisplayName("[FEATURE] action controller template")
  class ControllerActionTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new ControllerActionTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature =
        "public class FoodItemActionController";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[FEATURE] create file path")
    public void createFilePath() {
      final ArcTemplate template = new ControllerActionTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/control/FoodItemActionController.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] summary controller template")
  class ControllerSummaryTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new ControllerSummaryTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature =
        "public class FoodItemSummaryController";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[FEATURE] create file path")
    public void createFilePath() {
      final ArcTemplate template = new ControllerSummaryTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/control/FoodItemSummaryController.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] action facade template")
  class FacadeActionTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new FacadeActionTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public class FoodItemActionFacade";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[FEATURE] create file path")
    public void createFilePath() {
      final ArcTemplate template = new FacadeActionTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/api/FoodItemActionFacade.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] summary facade template")
  class FacadeSummaryTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new FacadeSummaryTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public class FoodItemSummaryFacade";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new FacadeSummaryTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/api/FoodItemSummaryFacade.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] read service template")
  class ServiceReadTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new ServiceReadTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "class FoodItemReadService";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new ServiceReadTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/api/FoodItemReadService.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] write service template")
  class ServiceWriteTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new ServiceWriteTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "class FoodItemWriteService";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new ServiceWriteTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/api/FoodItemWriteService.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] dto mapper template")
  class DtoMapperTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new DtoMapperTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public class FoodItemMapper";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new DtoMapperTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/domain/mapper/FoodItemMapper.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] exception template")
  class ExceptionTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new ExceptionTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public class FoodItemException";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new ExceptionTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/domain/exception/FoodItemException.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] dto template")
  class ModelTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new DtoTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public record FoodItem";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new DtoTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/domain/model/dto/FoodItem.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] request template")
  class RequestTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new RequestTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public record AddFoodItemRequest";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new RequestTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/domain/model/request/AddFoodItemRequest.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] response template")
  class ResponseTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new ResponseTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature =
        "public record ShowAllFoodItemResponse";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new ResponseTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/domain/model/response/ShowAllFoodItemResponse.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] entity mapper template")
  class EntityMapperTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new EntityMapperTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "class FoodItemEntityMapper";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new EntityMapperTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/storage/FoodItemEntityMapper.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] entity template")
  class EntityTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new EntityTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "class FoodItemEntity";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new EntityTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/storage/FoodItemEntity.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] loader template")
  class LoaderTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new LoaderTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature = "public class FoodItemLoader";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new LoaderTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/storage/FoodItemLoader.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }

  @Nested
  @DisplayName("[FEATURE] storage template")
  class StorageTemplateFeature {

    @Test
    @DisplayName("[MUTATION] generate content")
    public void generateContent() {
      final ArcTemplate template = new StorageTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualContent = template.content();

      final String expectClassSignature =
        "interface FoodItemStorage extends MongoRepository<FoodItemEntity, String>";
      assertAll(
        () -> assertThat(actualContent).isNotBlank(),
        () -> assertThat(actualContent).contains(expectClassSignature)
      );
    }

    @Test
    @DisplayName("[MUTATION] create file path")
    public void createFilePath() {
      final ArcTemplate template = new StorageTemplate(
        Given.CONFIG,
        Given.DOMAIN
      );

      final String actualPath = template.file().getPath();

      final String expectedPath =
        "/home/user/path/to/project/src/main/java/com/jarogoose/enenbi/feature/food/storage/FoodItemStorage.java";
      assertAll(
        () -> assertThat(actualPath).isNotNull(),
        () -> assertThat(actualPath).isEqualTo(expectedPath)
      );
    }
  }
}
