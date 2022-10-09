package org.jarogoose.archigen.web.generate.control;

import lombok.experimental.UtilityClass;
import org.jarogoose.archigen.web.config.storage.ConfigEntity;

@UtilityClass
public class Given {

  String PREVIEW_ALL_URL =
    "http://localhost:%s/archigen-ui/generate-api/preview-all";

  ConfigEntity SAVE_CONFIG_ENTITY = ConfigEntity
    .builder()
    .projectName("NNB")
    .artefact("com.jarogoose")
    .project("enenbi")
    .baseDir("home/user/pass/to/project")
    .author("Goose Duck")
    .build();

  String GENERATE_REQUEST = """
    {
        "projectName": "NNB",
        "feature": "foodItem",
        "package": "food",
        "restApi": "user-ui",
        "readWrite": "RW",
        "data": [
          "name",
          "category",
          "quantity"
        ]
    }
    """;
}
