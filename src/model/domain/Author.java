package model.domain;

import java.io.Serializable;

final public class Author extends Person implements Serializable {

	private static final long serialVersionUID = 7508481940058530471L;
	private String biography;

	public Author(String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.biography = bio;
	}

	public String getBiography() {
		return biography;
	}

}
