package EcommersE2E;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import POJOEcommers.LoginRequest;
import POJOEcommers.LoginResponse;
import POJOEcommers.OrderDetail;
import POJOEcommers.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class EcommersApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RequestSpecification loginReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
	
		LoginRequest logReq = new LoginRequest();
		logReq.setUserEmail("apilearning@gmail.com");
		logReq.setUserPassword("Api:1234");
		
		RequestSpecification login = given().spec(loginReq).body(logReq);
		LoginResponse logRes = login.when().post("/api/ecom/auth/login").then().extract().as(LoginResponse.class);
		
		String token =logRes.getToken();
		String userId = logRes.getUserId();
		System.out.println(logRes.getMessage());
		
		//AddProduct
		RequestSpecification addProductBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification addProduct = given().spec(addProductBaseReq)
			.param("productName", "HM Coat")
			.param("productAddedBy", userId)
			.param("productCategory", "fashion")
			.param("productSubCategory", "shirts")
			.param("productPrice", "11500")
			.param("productDescription", "Addias Originals")
			.param("productFor", "women")
			.multiPart("productImage",new File("//Users//erdemkahraman//Desktop/imageP.jpeg"));
				
		 String addProductResponse = addProduct.when().post("/api/ecom/product/add-product").then().extract().asString();
		 JsonPath js = new JsonPath(addProductResponse);
		 String productId = js.get("productId");
		 System.out.println("Product id => " + productId.toString());
		 System.out.println("message: "+js.get("message").toString());
		
		
		//CreateOrder
		RequestSpecification createOrderBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq = given().spec(createOrderBaseReq).body(orders);
		String createOrderRes = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
		productId =orderDetail.getProductOrderedId();
		JsonPath js3= new JsonPath(createOrderRes);
		String orderId = js3.get("orders").toString();
		System.out.println(orderId);
	
		
		//View Order
				RequestSpecification getOrderBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
						.addHeader("Authorization", token).addQueryParam("id",productId).build();
				
				given().spec(getOrderBaseReq).when().get("/api/ecom/order/get-orders-details").then().log().all().extract().response();
		
		//Delete Order 
		RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		RequestSpecification deleteOrderRequest = given().spec(deleteOrderBaseReq).pathParam("orderId", orderId);
		String deleteOrderMessage = deleteOrderRequest.when().delete("/api/ecom/order/delete-order/{orderId}").then().extract().response().asString();
		System.out.println("Delete order message: " +deleteOrderMessage);
		
		
		//Delete Product
		RequestSpecification deleteProductBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProductRequest = given().spec(deleteProductBaseReq).pathParam("productId", productId);
		String deleteProductResponse = deleteProductRequest.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().extract().response().asString();
		JsonPath js1 = new JsonPath(deleteProductResponse);
		String deleteMessage = js1.get("message");
		 
		System.out.println("Delete Message: "+deleteMessage);
		
	}
	
}
