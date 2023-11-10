import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;


public class MapsWithSpecBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String key = "qaclick123";
		RequestSpecification request = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", key)
				.addHeader("Content-Type", "application/json")
				.build();
		
		//Validate if Add place API is working as expected
		
		String response = given().log().all().spec(request)
			   .body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
							.body("scope", equalTo("APP"))
							.header("Server", "Apache/2.4.52 (Ubuntu)")
							.extract().response().asString();
		
		//Add place -> Update place with new address -> Get Place to validate is new address is present in response   
		System.out.println(response);
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		String newAddress = "70 Summer walk, USA";
		
		given().log().all().queryParam("key",key)
			   .header("Content-Type","application/json")
			   .body("{\n"
			   		+ "\"place_id\":\""+ placeId +"\",\n"
			   		+ "\"address\":\""+ newAddress +"\",\n"
			   		+ "\"key\":\""+key+"\"\n"
			   		+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all() .assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		String getPlaceResponse = given().log().all().queryParam("key", key)
						   .queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
			   .body("address", equalTo(newAddress))
			   .extract().response().asString();
	
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
	}

}
