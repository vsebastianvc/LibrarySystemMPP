package model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import model.dataaccess.DataAccessFacade;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable{

	private static final long serialVersionUID = -63976228084869815L;
	private Book book;
	private int copyNum;

	private boolean isAvailable;

	BookCopy(Book book, int copyNum) {
		this.book = book;
		this.copyNum = copyNum;
	}

	BookCopy(Book book, int copyNum, boolean isAvailable) {
		this.book = book;
		this.copyNum = copyNum;
		this.isAvailable = isAvailable;
	}

	public String Available() {
		return isAvailable ? "Yes" : "No";
	}

	public void changeAvailability() {
		isAvailable = !isAvailable;
	}

	@Override
	public boolean equals(Object ob) {
		if (ob == null)
			return false;
		if (!(ob instanceof BookCopy))
			return false;
		BookCopy copy = (BookCopy) ob;
		return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
	}

	public Book getBook() {
		return book;
	}

	public int getCopyNum() {
		return copyNum;
	}

	public String getExists() {
		return this.Available();
	}

	public boolean getisAvailable() {
		return isAvailable;
	}

	public CheckoutRecordEntry getCopyCheckoutRecordEntry() {
		DataAccessFacade daf = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = daf.readMemberMap();
		Set<Entry<String, LibraryMember>> memberSet = mems.entrySet();
		for (Entry<String, LibraryMember> lm : memberSet) {
			LibraryMember mem = lm.getValue();
			for (CheckoutRecordEntry cre : mem.getCheckoutRecordEntries()) {
				if (cre.getBookcopy().equals(this)) {
					return cre;
				}
			}
		}
		return null;
	}

	public String getOverdue() {
		CheckoutRecordEntry result = getCopyCheckoutRecordEntry();
		if (result == null)
			return "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
		String date = result.getDueDate().format(formatter);
		return result.getDueDate().compareTo(LocalDate.now()) < 0 ? "YES: " : "On " + date;
	}

	public String getPossesion() {
		CheckoutRecordEntry result = getCopyCheckoutRecordEntry();
		if (result == null)
			return "";
		return result.getMember().getMemberId() + " - " + result.getMember().getFullName();
	}
	public String getIsbn() {
		return book.getIsbn();
	}
	public String getBookName() {
		return book.getTitle();
	}
	
	@Override
	public String toString() {
		return book!=null?this.getCopyNum()+ " | " + book.getIsbn() +" | "+ book.getTitle():"There's no book associated to this copy!";
	}

}