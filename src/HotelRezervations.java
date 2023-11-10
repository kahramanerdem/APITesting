import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import POJOHotel.BookingDates;
import POJOHotel.CreateBooking;
import POJOHotel.CreateBookingResponse;
import POJOHotel.CreateToken;

public class HotelRezervations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="https://restful-booker.herokuapp.com";
		String username ="admin";
		String password = "password123";
		
		
		
		//Create Token
		CreateToken createToken = new CreateToken();
		createToken.setUsername(username);
		createToken.setPassword(password);
		String tokenJson = given().header("Content-Type","application/json").body(createToken)
			   .when().post("/auth")
			   .then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(tokenJson);
		String token = js.getString("token");
		System.out.println(token);
		
		//Get Booking Ids
		given().when().get("/booking").then().log().all().assertThat().statusCode(200);
		
		//Create Booking
		int totalPrice = (int) (Math.random()*1000);
		CreateBooking createBooking = new CreateBooking();
		createBooking.setFirstname("Erdem");
		createBooking.setLastname("Kahraman");
		createBooking.setTotalprice(totalPrice);
		createBooking.setDepositpaid(true);
		
		BookingDates bookingDates = new BookingDates();
		bookingDates.setCheckin("2023-01-01");
		bookingDates.setCheckout("2025-01-01");
		
		createBooking.setBookingdates(bookingDates);
		createBooking.setAdditionalneeds("Kahvaltı");
		System.out.println("data ->"+createBooking);
		
		CreateBookingResponse bookingIdJson2 = given().log().all().header("Content-Type","application/json").header("Accept","application/json")
				   .body(createBooking)
				   .when().post("/booking")
				   .then().log().all().assertThat().statusCode(200).extract().response().as(CreateBookingResponse.class);
		int bookingId =bookingIdJson2.getBookingid();
		System.out.println("----> "+bookingIdJson2);

		
		//Update Booking 
		given().pathParam("id", bookingId).header("Content-Type","application/json").header("Accept", "application/json")
			   .header("Cookie","token="+token+"").body("{\n"
			   		+ "    \"firstname\" : \"Faruk\",\n"
			   		+ "    \"lastname\" : \"Brown\",\n"
			   		+ "    \"totalprice\" : 111,\n"
			   		+ "    \"depositpaid\" : true,\n"
			   		+ "    \"bookingdates\" : {\n"
			   		+ "        \"checkin\" : \"2018-01-01\",\n"
			   		+ "        \"checkout\" : \"2019-01-01\"\n"
			   		+ "    },\n"
			   		+ "    \"additionalneeds\" : \"Breakfast\"\n"
			   		+ "}")
			   .when().put("/booking/{id}")
			   .then().log().all().assertThat().statusCode(200);
		
		//Get Booking Id
		getBooking(bookingId,200);
		
		//Partial Update Booking
		given().pathParam("bookingid", bookingId).header("Content-Type","application/json").header("Accept", "application/json")
		   	   .header("Cookie","token="+token+"").body("{\n"
		   	   		+ "    \"firstname\" : \"Faruk\",\n"
		   	   		+ "    \"lastname\" : \"Taşgöz\"\n"
		   	   		+ "}")
		   	   .when().patch("/booking/{bookingid}")
		   	   .then().log().all().assertThat().statusCode(200);
		
		//Delete Booking 
		given().pathParam("bookingid", bookingId).header("Content-Type","application/json").header("Cookie","token="+token+"")
			   .when().delete("/booking/{bookingid}")
			   .then().log().all().assertThat().statusCode(201);
		
		//Get Booking Id
		getBooking(bookingId,404);
	}
	
	public static void getBooking(int bookingId,Integer statusCode) {
		given().pathParam("bookingid", bookingId)
		   .when().get("/booking/{bookingid}")
		   .then().log().all().assertThat().statusCode(statusCode);
	}

}
