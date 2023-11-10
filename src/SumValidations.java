import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidations {

	@Test
	public void sumOfCourses() {
		
		int sum = 0; 
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		
			for(int i=0; i<count;i++) {
				int copies = js.getInt("courses["+i+"].copies");
				int prices = js.getInt("courses["+i+"].price");
				int amount = copies * prices;
				System.out.println(js.getString("courses["+i+"].title")+" amount "+" -> "+amount);
				sum+=amount;
				}
		Assert.assertEquals(sum, js.getInt("dashboard.purchaseAmount"));
		System.out.println("purchase amount: "+sum);

	}
}
 