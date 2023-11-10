import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

import POJODeserialization.AddBook;
import POJODeserialization.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Deserialization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String key = "qaclick123";
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
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given().log().all().queryParam("key", key)
			   .header("Content-Type","application/json")
			   .body(add)
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString(); 
		System.out.println(responseString);
	}

}
