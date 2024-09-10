package org.example.library;

public record Book(
		String title, String author,
		String isbn, String genre,
		String coverPage
) {
}
