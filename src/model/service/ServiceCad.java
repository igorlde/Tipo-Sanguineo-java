package model.service;

import java.util.Map;

public interface ServiceCad {
	public void registerPeople(String name, String gender, Integer age, String andress, String bloodType, String phoneNumber);

	public Map<String, Integer> countBloodType();

	public String bloodTypeInLack();

	public void listbloodTypeAvailable();
	
	public void listDonors();
}
