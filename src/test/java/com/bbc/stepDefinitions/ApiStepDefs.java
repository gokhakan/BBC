package com.bbc.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class ApiStepDefs {
    String url = "https://testapi.io/api/ottplatform/";
    Response response;
    JSONObject obj;
    JSONArray arr;


    @Given("user sends a GET request to {string} endpoint")
    public void user_sends_a_GET_request_to_endpoint(String endpoint) {
        response = given().accept(ContentType.JSON)
                .when()
                .get(url + endpoint);

    }

    @Then("response status code is {int}")
    public void response_status_code_is(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("response time is less than {int} milliseconds")
    public void response_time_is_less_than_milliseconds(int expectedResponseTime) {
        System.out.println("expectedResponseTime = " + expectedResponseTime);
        System.out.println("response.getTime() = " + response.getTime());
        boolean responseTime = response.getTime() < expectedResponseTime;
        System.out.println("responseTime = " + responseTime);

        Assert.assertTrue(responseTime);
    }

    @Then("id field is populated")
    public void id_field_is_populated() throws JSONException {
        String jsonstring = response.asString();
        obj = new JSONObject(jsonstring);
        arr = obj.getJSONArray("data");
        System.out.println("arr.length() = " + arr.length());

        for (int i = 0; i < arr.length(); i++) {
            System.out.println("arr.getJSONObject(" + i + ").getString(\"id\") = " + arr.getJSONObject(i).getString("id"));
            Assert.assertFalse(arr.getJSONObject(i).getString("id").isEmpty());
        }
    }
    @Then("{string} is always {string}")
    public void contains(String field, String word) throws JSONException {
        for (int i = 0; i <arr.length() ; i++) {
            System.out.println("arr.getJSONObject(i).getString(\"segment_type\") = " + arr.getJSONObject(i).getString(field));
            Assert.assertTrue(arr.getJSONObject(i).getString("segment_type").equalsIgnoreCase(word));
        }
    }

    @Then("{string} field is not empty")
    public void field_is_not_empty(String field) throws JSONException {
        System.out.println("field = " + field);
        System.out.println(arr.getJSONObject(0).getString(field));
    }



    }