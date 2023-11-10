import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class graphQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String characterId = "1950";
		String episodeId="1797";
		String response = given().log().all().contentType(ContentType.JSON).body("{\"query\":\"query(\\n  $characterId:Int!\\n  $episodeId:Int!\\n){\\n  character(characterId:$characterId){\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId:2402){\\n    name\\n    type\\n    dimension\\n  }\\n\\n  \\n  episode(episodeId:$episodeId){\\n    name\\n    air_date\\n    episode\\n  }\\n  \\n  episodes(filters:{episode:\\\"hulu\\\"}){\\n    result{\\n      id\\n      name\\n      air_date\\n      episode   \\n    }\\n  }\\n  \\n  characters(filters:{name:\\\"Rahul\\\"}){\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      type\\n      status\\n    }\\n    \\n  }\\n  \\n}\\n\\n\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":"+episodeId+"}}")
		.when().post("https://www.rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String cn = js.getString("data.character.name");
		System.out.println(cn);
	}

}
