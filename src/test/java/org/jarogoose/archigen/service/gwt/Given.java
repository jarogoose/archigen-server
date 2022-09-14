package org.jarogoose.archigen.service.gwt;

import java.util.List;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.ReturnType;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Given {

  public static final String DAILY_METABOLIC_ACTIVITY_JSON = """
      {
        "feature": "dailyMetabolicActivity",
        "root": "metabolism",
        "data": [
          "id",
          "userId",
          "date",
          "fat",
          "carb",
          "protein",
          "fiber",
          "water",
          "sleep"
        ],
        "requests": [
          {
            "control": "addDailyMetabolicActivity",
            "execute": "add",
            "query": "save",
            "custom-query": false,
            "http-method": "POST",
            "return-type": "VOID",
            "data": [
              "userId",
              "date",
              "fat",
              "carb",
              "protein",
              "fiber",
              "water",
              "sleep"
            ]
          },
          {
            "control": "modifyDailyMetabolicActivity",
            "execute": "modify",
            "query": "update",
            "custom-query": false,
            "http-method": "PUT",
            "return-type": "VOID",
            "data": [
              "id",
              "date",
              "fat",
              "carb",
              "protein",
              "fiber",
              "water",
              "sleep"
            ]
          },
          {
            "control": "searchDailyMetabolicActivity",
            "execute": "search",
            "query": "findById",
            "custom-query": false,
            "http-method": "POST",
            "return-type": "OBJECT",
            "data": [
              "id"
            ]
          },
          {
            "control": "showAllDailyMetabolicActivities",
            "execute": "showAll",
            "query": "findAllByUserId",
            "custom-query": true,
            "http-method": "POST",
            "return-type": "COLLECTION",
            "data": [
              "userId"
            ]
          },
          {
            "control": "removeDailyMetabolicActivity",
            "execute": "remove",
            "query": "deleteById",
            "custom-query": false,
            "http-method": "DELETE",
            "return-type": "VOID",
            "data": [
              "id"
            ]
          }
        ]
      }
        """;;

  public static Domain dailyMetabolicActivityDomain() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(DAILY_METABOLIC_ACTIVITY_JSON, Domain.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Request addRequest() {
    String control = "addDailyMetabolicActivity";
    String execute = "add";
    String query = "save";
    boolean customQuery = false;
    List<String> data = List.of("userId", "date", "fat", "carb", "protein", "fiber", "water",
        "sleep");

    return new Request(
        control, execute, query, customQuery, HttpMethod.POST.name(), ReturnType.VOID.name(), data);
  }

  public static Request modifyRequest() {
    String control = "modifyDailyMetabolicActivity";
    String execute = "modify";
    String query = "update";
    boolean customQuery = false;
    List<String> data = List.of("id", "userId", "date", "fat", "carb", "protein", "fiber", "water",
        "sleep");

    return new Request(
        control, execute, query, customQuery, HttpMethod.PUT.name(), ReturnType.VOID.name(), data);
  }

  public static Request searchRequest() {
    String control = "searchDailyMetabolicActivity";
    String execute = "search";
    String query = "findById";
    boolean customQuery = true;
    List<String> data = List.of("id");

    return new Request(
        control, execute, query, customQuery, HttpMethod.POST.name(), ReturnType.OBJECT.name(), data);
  }

  public static Request showAllRequest() {
    String control = "showAllDailyMetabolicActivities";
    String execute = "showAll";
    String query = "findAllByUserId";
    boolean customQuery = true;
    List<String> data = List.of("userId");

    return new Request(
        control, execute, query, customQuery, HttpMethod.POST.name(), ReturnType.COLLECTION.name(),
        data);
  }

  public static Request removeRequest() {
    String control = "removeDailyMetabolicActivity";
    String execute = "remove";
    String query = "deleteById";
    boolean customQuery = true;
    List<String> data = List.of("id");

    return new Request(
        control, execute, query, customQuery, HttpMethod.DELETE.name(), ReturnType.VOID.name(), data);
  }
}
