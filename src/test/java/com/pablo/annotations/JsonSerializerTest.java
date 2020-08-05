package com.pablo.annotations;

import com.pablo.annotations.data.Car;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;

public class JsonSerializerTest {
	
	@Test(expected = NullPointerException.class)
	public void serializeNullObjectEnsureNullPointerExceptionThrown() throws Exception {
		new JsonSerializer().serialize(null);
	}

	@Test
	public void serializeCarObjectEnsureCorrectOutputJson() throws Exception {
		JsonSerializer serializer = new JsonSerializer();
		Car testCar = new Car("Ford", "F150", "2018");
		String json = serializer.serialize(testCar);
		assertThat(json, isExpectedCarJson(testCar));
	}
	
	private static Matcher<String> isExpectedCarJson(Car car) {
		return isOneOf(
			"{\"manufacturer\":\"" + car.getMake() + "\",\"model\":\"" + car.getModel() + "\"}",
			"{\"model\":\"" + car.getModel() + "\",\"manufacturer\":\"" + car.getMake() + "\"}"
		);
	}
}
