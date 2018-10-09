package model.domain;

import java.time.LocalDate;
import java.util.*;

import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;

public class Main {

	public static void main(String[] args) {
		System.out.println(allWhoseZipContains3());
		System.out.println(allHavingOverdueBook());
		System.out.println(allHavingMultipleAuthors());

	}
	//Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		List<String> result = new ArrayList<>();
		for (LibraryMember lm : mems) {
			if (lm.getAddress().getZip().contains("3"))
				result.add(lm.getMemberId());
		}
		return result;
		
	}
	//Returns a list of all ids of  LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		List<String> result = new ArrayList<>();
		for (LibraryMember lm : mems) {
			for (CheckoutRecordEntry cre : lm.getCheckoutRecordEntries()) {
				if (cre.getDueDate().compareTo(LocalDate.now()) < 0) {
					result.add(lm.getMemberId());
					break;
				}
			}
		}
		return result;
		
	}
	
	//Returns a list of all isbns of  Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();
		List<Book> bs = new ArrayList<>();
		bs.addAll(books);
		//implement
		List<String> result = new ArrayList<>();
		for (Book b : books) {
			if (b.getAuthors().size() > 1)
				result.add(b.getIsbn());
		}
		return result;
		
		}

}
