import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {
	
	
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().header("Content-type","application/json")
			   .body(payload.AddBook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);}
	
	@Test(dataProvider="BooksData")
	public void deleteBook(String isbn,String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String deletemessage = given().body(payload.deleteBook(isbn, aisle))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(deletemessage);
		JsonPath js = ReUsableMethods.rawToJson(deletemessage);
		String message = js.get("msg");
		System.out.println(message);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData () {
		
		return new Object [][] { {"brownAge","9345"},{"greenDay","4563"},{"blackDay","6574"} }; 
		
	}
}
