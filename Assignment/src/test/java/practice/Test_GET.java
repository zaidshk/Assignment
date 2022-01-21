package practice;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.List;
import static practice.Constant.*;

public class Test_GET extends BaseClass {

//Response HTTP Status code should be 200
	@Test
	void test_1_1() {
		given().get(URI).then().statusCode(200);

	}

	// Assert following json properties present in the response for each object
	@Test
	void test_1_2() {

		String res = given().when().get(URI).then().extract().response().asString();

		JsonPath js = new JsonPath(res);

		int Count = js.get("promotions.promotionId.size()");
		System.out.println(res);
		System.out.println("Count is    " + Count);

		// Assert following json properties present in the response for each object
		for (int i = 0; i < Count; i++) {
			// Pattern r = Pattern.compile("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$");
			given().get(URI).then().assertThat().body("promotions.promotionId[" + i + "]", not(hasValue(nullValue())))
					.assertThat().body("promotions.orderId[" + i + "]", not(hasValue(nullValue()))).assertThat()
					.body("promotions.promoArea[" + i + "]", not(hasValue(nullValue()))).assertThat()
					.body("promotions.promoType[" + i + "]", not(hasValue(nullValue()))).assertThat()
					.body("promotions.showPrice[" + i + "]", either(is(true)).or(is(false))).assertThat()
					.body("promotions.showText[" + i + "]", either(is(true)).or(is(false)));

			// Asserting localizedTexts
			System.out.println(js.get("promotions.localizedTexts[" + i + "]"));
			List<HashMap<String, List<String>>> ltList = js.get("promotions.localizedTexts");
			System.out.println(ltList);
			for (HashMap<String, List<String>> lt : ltList) {
				Assert.assertTrue(lt.containsKey("ar"));
				Assert.assertTrue(lt.containsKey("en"));

			}

		}
	}

	//Assert promotionId: any string value,
	@Test
	void test_2() {

		String res = given().when().get(URI).then().extract().response().asString();

		JsonPath js = new JsonPath(res);

		int Count = js.get("promotions.promotionId.size()");

		System.out.println(res);
		System.out.println("Count is    " + Count);

		for (int i = 0; i < Count; i++) {

			given().get(URI).then().assertThat().body("promotions.promotionId", not(hasValue(nullValue())));
		}

		// programType: EPISODE or MOVIE or SERIES or SEASON
		System.out.println("testing programType");

		List<List<HashMap<String, Object>>> propListofList = js.get("promotions.properties");
		for (List<HashMap<String, Object>> propList : propListofList) {
			for (HashMap<String, Object> prop : propList) {
				if (prop.containsKey("programType")) {
					String a = prop.get("programType").toString();
					System.out.println(prop.get("programType"));
					Assert.assertTrue(PROGRAMTYPEMASTERLIST.contains(prop.get("programType").toString().toUpperCase()));
				}
			}
		}

	}

	// Assert invalid api key response
	@Test
	void test_3() {

		given().when().get(INVALIDURI).then().statusCode(403).assertThat()
				.body("error.message", equalTo(INVALID_USER_MESSAGE)).assertThat()
				.body("error.code", equalTo(INVALID_USER_CODE)).assertThat()
				.body("error.requestId", not(hasValue(nullValue())));

	}

}
