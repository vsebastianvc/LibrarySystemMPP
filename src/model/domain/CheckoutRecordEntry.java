package model.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckoutRecordEntry implements Serializable {
	private static final long serialVersionUID = 659783906422028230L;
	private BookCopy bookcopy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private LibraryMember member;

	CheckoutRecordEntry(BookCopy bookcopy, LocalDate checkoutDate, LocalDate dueDate, LibraryMember member) {
		super();
		this.bookcopy = bookcopy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.member = member;
	}

	public BookCopy getBookcopy() {
		return bookcopy;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public String fixSize(String string) {
		return null;
	}

	@Override
	public String toString() {

		String s1 = "                                                                       ";
		return String.format("|%s\t|%s\t\t\t|%s\t|%s\t|", bookcopy.getBook().getIsbn(),
				bookcopy.getBook().getTitle() + s1.substring(0, 30 - bookcopy.getBook().getTitle().length()),
				checkoutDate, dueDate);
	}

	public LibraryMember getMember() {
		// TODO Auto-generated method stub
		return member;
	}
	public String getDuedateString() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		return dueDate!=null?dueDate.format(pattern):"";
	}
	
	public String getCheckoutDateString() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		return checkoutDate!=null?checkoutDate.format(pattern):"";
	}

}