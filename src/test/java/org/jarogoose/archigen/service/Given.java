package org.jarogoose.archigen.service;

import java.util.List;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class Given {

  public static Domain dailyMetabolicActivityDomain() {
    String feature = "dailyMetabolicActivity";
    String root = "metabolism";
    List<String> data = List.of("id", "userId", "date", "fat", "carb", "protein", "fiber", "water",
        "sleep");
    List<Request> requests = List.of(
        addRequest(),
        modifyRequest(),
        searchRequest(),
        showAllRequest(),
        removeRequest());
    return new Domain(feature, root, data, requests);
  }

  public static Request addRequest() {
    String control = "addDailyMetabolicActivity";
    String execute = "add";
    String query = "save";
    boolean customQuery = false;
    String type = "POST";
    List<String> data = List.of("userId", "date", "fat", "carb", "protein", "fiber", "water",
        "sleep");

    return new Request(
        control, execute, query, customQuery, type, data
    );
  }

  public static Request modifyRequest() {
    String control = "modifyDailyMetabolicActivity";
    String execute = "modify";
    String query = "update";
    boolean customQuery = false;
    String type = "PUT";
    List<String> data = List.of("id", "userId", "date", "fat", "carb", "protein", "fiber", "water", "sleep");

    return new Request(
        control, execute, query, customQuery, type, data
    );
  }

  public static Request searchRequest() {
    String control = "searchDailyMetabolicActivity";
    String execute = "search";
    String query = "findById";
    boolean customQuery = false;
    String type = "GET";
    List<String> data = List.of("id");

    return new Request(
        control, execute, query, customQuery, type, data
    );
  }

  public static Request showAllRequest() {
    String control = "showAllDailyMetabolicActivities";
    String execute = "showAll";
    String query = "findAllByUserId";
    boolean customQuery = true;
    String type = "GET_ALL";
    List<String> data = List.of("userId");

    return new Request(
        control, execute, query, customQuery, type, data
    );
  }

  public static Request removeRequest() {
    String control = "removeDailyMetabolicActivity";
    String execute = "remove";
    String query = "deleteById";
    boolean customQuery = false;
    String type = "DELETE";
    List<String> data = List.of("id");

    return new Request(
        control, execute, query, customQuery, type, data
    );
  }
}
