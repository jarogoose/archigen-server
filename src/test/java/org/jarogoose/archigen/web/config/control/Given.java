package org.jarogoose.archigen.web.config.control;

public interface Given {

  String SAVE_CONFIG_URL = "http://localhost:%s/archigen-ui/configs-api/save-configs";

  String GET_ALL_CONFIG_URL = "http://localhost:%s/archigen-ui/configs-api/load-all-configs";

  String SAVE_CONFIG_JSON = """
    {
        "projectName": "NNB",
        "artefact": "com.jarogoose",
        "project": "enenbi",
        "baseDir": "Dev/jarogoose/enenbi",
        "author": "Yaroslav Kynyk"
    }  
    """;
}
