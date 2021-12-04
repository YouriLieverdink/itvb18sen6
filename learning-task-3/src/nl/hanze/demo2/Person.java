package nl.hanze.demo2;

public class Person {
	private String name;
	private String code;
	private String emailAddress;
	private int age;

	public Person(String name, String code, String emailAddress, int age) {
		setName(name);
		setCode(code);
		setEmailAddress(emailAddress);
		setAge(age);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
