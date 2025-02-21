package model.entites;

public class People {
	private String name;
	private String gender;
	private Integer age;
	private String andress;
	private String bloodType;

	public People(String name, String gender, Integer age, String andress, String bloodType) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.andress = andress;
		this.bloodType = bloodType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAndress() {
		return andress;
	}

	public void setAndress(String andress) {
		this.andress = andress;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}


 
	
	
}
