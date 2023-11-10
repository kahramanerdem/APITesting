import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//Print No of courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
	
		//Print Purchase Amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of the first course
		
		String titleFirstCourse = js.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print All course titles and their respective Prices
		
		for(int i=0; i<count; i++) {
			String courseTitles = js.getString("courses["+i+"].title");
			int coursePrices = js.getInt("courses["+i+"].price");
			System.out.println(courseTitles + " -> " + coursePrices);
		}
		
		System.out.println("Print no of copies sold by RPA Course: ");//Print no of copies sold by RPA Course
		
		for(int i=0; i<count; i++) {
			String courseTitles = js.getString("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				int RPACopy = js.getInt("courses["+i+"].copies");
				System.out.println("RPA copies: "+RPACopy);
				break;
			}
		}
		
		
		
		
	}

}
