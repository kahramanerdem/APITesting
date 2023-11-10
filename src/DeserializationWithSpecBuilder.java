import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

import POJODeserialization.AddBook;
import POJODeserialization.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class DeserializationWithSpecBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification request = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.addHeader("Content-Type","application/json")
				.build();
		
		ResponseSpecification res = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		
		AddBook add = new AddBook();
		add.setAccuracy(50);
		add.setName("Frontline house");
		add.setPhone_number("(+91) 983 876 9899");
		add.setAddress("park");
		add.setLanguage("Turkish");
		add.setWebsite("www.google.com");
		
		List<String> a = new ArrayList<String>();
		a.add("shoe park");
		a.add("park");
		add.setTypes(a);
		
		Location l = new Location();
		l.setLat(-31.245);
		l.setLng(31.245);
		
		add.setLocation(l);
		
		System.out.println(add);
		RequestSpecification req = given().spec(request).body(add);
		Response response = req.when().post("maps/api/place/add/json")
				.then().spec(res).extract().response();
		
		int responseString = response.getStatusCode(); 
		System.out.println(responseString);
	}

}
