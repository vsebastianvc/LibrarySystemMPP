package model.dataaccess;

import java.util.HashMap;

import model.domain.Author;
import model.domain.Book;
import model.domain.LibraryMember;
import model.domain.User;

public interface DataAccess {
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String,Author> readAuthorMap();
	public void saveNewMember(LibraryMember member);
	public void saveAbook(Book bk);
}
