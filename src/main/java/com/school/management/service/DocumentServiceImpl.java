package com.school.management.service;

import com.school.management.model.Document;
import com.school.management.model.User;
import com.school.management.repository.DocumentRepository;
import com.school.management.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

	private static final String UPLOAD_FOLDER = "uploads/documents/";

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Document createDocument(Document document, MultipartFile file, Long uploadedById) {
		validateDocumentFields(document);

		String title = document.getTitle();
		if (documentRepository.existsByTitle(title)) {
			throw new IllegalArgumentException("Document with the same title already exists.");
		}

		String fileName = file.getOriginalFilename();
		String filePath = saveFile(file);

		document.setFilePath(filePath);
		document.setFileName(fileName);
		document.setUploadedAt(LocalDateTime.now());
		document.setUpdatedAt(LocalDateTime.now());

		User uploadedBy = userRepository.findById(uploadedById)
				.orElseThrow(() -> new DocumentNotFoundException("User not found with id: " + uploadedById));

		document.setUploadedBy(uploadedBy);

		return documentRepository.save(document);
	}

	private String saveFile(MultipartFile file) {
		try {
			Path uploadPath = Path.of(UPLOAD_FOLDER).toAbsolutePath().normalize();

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String fileName = file.getOriginalFilename();
			String filePath = uploadPath + "/" + fileName;

			Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

			return filePath;
		} catch (IOException e) {
			throw new RuntimeException("Failed to save file.");
		}
	}

	@Override
	public Document getDocumentById(Long id) {
		return documentRepository.findById(id)
				.orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));
	}

	@Override
	public Document updateDocument(Long id, Document document, MultipartFile file) {
		if (!documentRepository.existsById(id)) {
			throw new DocumentNotFoundException("Document not found with id: " + id);
		}

		validateDocumentFields(document);

		Document existingDocument = documentRepository.findById(id)
				.orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));

		String newTitle = document.getTitle();
		if (!existingDocument.getTitle().equals(newTitle) && documentRepository.existsByTitle(newTitle)) {
			throw new IllegalArgumentException("Document with the same title already exists.");
		}

		existingDocument.setTitle(newTitle);
		existingDocument.setDescription(document.getDescription());
		existingDocument.setUpdatedAt(LocalDateTime.now());
		if (file != null && !file.isEmpty()) {
			String filePath = saveFile(file);
			existingDocument.setFilePath(filePath);
			existingDocument.setFileName(file.getOriginalFilename());
		}
		return documentRepository.save(existingDocument);
	}

	@Override
	public boolean deleteDocument(Long id) {
		if (!documentRepository.existsById(id)) {
			return false;
		}
		documentRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Document> getAllDocuments() {
		return documentRepository.findAll();
	}

	@Override
	public List<Document> searchDocumentsByTitle(String title) {
		return documentRepository.findByTitleContainingIgnoreCase(title);
	}

	private void validateDocumentFields(Document document) {
		String title = document.getTitle();
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Title is required.");
		}
	}

	public class DocumentNotFoundException extends RuntimeException {
		public DocumentNotFoundException(String message) {
			super(message);
		}
	}

}
