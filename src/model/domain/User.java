package model.domain;

import java.io.Serializable;

import model.dataaccess.Auth;

final public class User implements Serializable {

	private static final long serialVersionUID = 5147265048973262104L;

	private String id;
	private String password;
	private Auth authorization;
	private Person person;

	public User(String id, String pass, Auth auth, Person person) {
		this.id = id;
		this.password = pass;
		this.authorization = auth;
		this.person = person;
	}

	public User(String id, String pass, Auth auth) {
		this.id = id;
		this.password = pass;
		this.authorization = auth;
	}

	public User() {

	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public Auth getAuthorization() {
		return authorization;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean authenticate(String id, String pass) {
		return this.id.equals(id) && this.password.equals(pass);

	}

	public boolean isSuperUser() {
		return this.authorization.equals(Auth.ADMIN) || this.authorization.equals(Auth.BOTH);

	}

	@Override
	public String toString() {
		return "[" + id + ":" + password + ", " + authorization.toString() + "]";
	}

}
