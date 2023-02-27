package utilities;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

public class DataHelper {
	private Faker dataFaker = new Faker();
	
	public DataHelper getDataHelper() {
		return new DataHelper();
	}
	
	public String getNameWithMiddle() {
		return dataFaker.name().nameWithMiddle();
	}
	
	public String getStreetAddress() {
		return dataFaker.address().streetAddress();
	}
	
	public String getCityAddress() {
		return dataFaker.address().cityName();
	}
	
	public String getStateAddress() {
		return dataFaker.address().state();
	}
	
	public String getZipCodeAddress() {
		return dataFaker.address().zipCode();
	}
	
	public String country() {
		return dataFaker.address().country();
	}
	
	public String gethomePhone() {
		return dataFaker.phoneNumber().phoneNumber();
	}
	
	public String getmobilePhone() {
		return dataFaker.phoneNumber().cellPhone().replace(".", "-");
	}
	
	public String getLastName() {
		return dataFaker.name().lastName();
	}
	
	public String getPassword() {
		return dataFaker.internet().password(8, 12, true, true);
	}
}
