import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session = new SessionFilter();
		String expectedMessage = "Hi how are you?";
		
		//Authentication
		String jSessionIdJson = given().header("Content-Type","application/json")
			   .body("{ \"username\": \"...\", \"password\": \"...\" }").log().all()
			   .filter(session)
			   .when().post("/rest/auth/1/session")
			   .then().log().all().extract().response().asString();
		JsonPath js3 = new JsonPath(jSessionIdJson);
		String jSessionId = js3.getString("session.value");	
		System.out.println(jSessionId);
		
		//Create Issue
		String issueIdJson = given().header("Content-Type","application/json")
				.body("{\n"
						+ "    \"fields\": {\n"
						+ "       \"project\":\n"
						+ "       {\n"
						+ "          \"key\": \"REST\"\n"
						+ "       },\n"
						+ "       \"summary\": \"REST ye merry gentlemen.\",\n"
						+ "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n"
						+ "       \"issuetype\": {\n"
						+ "          \"name\": \"Bug\"\n"
						+ "       }\n"
						+ "   }\n"
						+ "}")
			   .when().post("/rest/api/2/issue")
			   .then().log().all().assertThat().statusCode(400).extract().response().asString();
		JsonPath js2 = new JsonPath(issueIdJson);
		String issueId = js2.get("id").toString();
		System.out.println(issueId);
		
		//Add comment
		String addCommentResponse = given().pathParam("id", issueId).header("Content-Type","application/json")
			   .body("{\n"
						+ "    \"body\": \""+expectedMessage+"\",\n"
						+ "    \"visibility\": {\n"
						+ "        \"type\": \"role\",\n"
						+ "        \"value\": \"Administrators\"\n"
						+ "    }\n"
						+ "}")
			   .filter(session)
			   .when().post("/rest/api/2/issue/{id}/comment")
			   .then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");
		System.out.println(commentId);
		
		//Add attachment 
		given().pathParam("id", issueId).header("X-Atlassian-Token","no-check").filter(session)
			   .header("Content-Type","multipart/form-data")
			   .multiPart("file",new File("jira.txt"))
			   .when().post("/rest/api/2/issue/{id}/attachments")
			   .then().log().all().assertThat().statusCode(200);
		
		//Get issue 
		String issueDetails = given().filter(session).pathParam("id", issueId)
			   .queryParam("fields", "comment").log().all()
			   .when().get("/rest/api/2/issue/{id}")
			   .then().assertThat().statusCode(200).log().all().extract().response().asString();
		System.out.println(issueDetails);
	
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount = js1.getInt("fields.comment.comments.size()");
		for (int i=0; i<commentsCount; i++) {
			String commentIds =js1.get("fields.comment.comments["+i+"].id").toString();
			System.out.println(commentIds);
			if(commentIds.equalsIgnoreCase(commentId)) {
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}	
	}

}
