package com.school.management.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.management.model.Document;

public interface DocumentService {
	Document createDocument(Document document, MultipartFile file, Long uploadedById);

	Document getDocumentById(Long id);

	Document updateDocument(Long id, Document document, MultipartFile file);

	boolean deleteDocument(Long id);

	List<Document> getAllDocuments();

	List<Document> searchDocumentsByTitle(String title);

	List<Document> searchDocument(String title);
}
