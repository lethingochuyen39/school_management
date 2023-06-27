package com.school.management.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.school.management.model.Document;
import com.school.management.service.DocumentServiceImpl;
import com.school.management.service.DocumentServiceImpl.DocumentNotFoundException;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	@Autowired
	private DocumentServiceImpl documentServiceImpl;

	@PostMapping()
	public ResponseEntity<?> createDocument(@ModelAttribute Document document, @RequestPart("file") MultipartFile file,
			@RequestParam("uploadedById") Long uploadedById) {
		try {
			Document addDocument = documentServiceImpl.createDocument(document, file, uploadedById);
			return ResponseEntity.ok(addDocument);
		} catch (DocumentNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDocumentById(@PathVariable("id") Long id) {
		try {
			Document document = documentServiceImpl.getDocumentById(id);
			return ResponseEntity.ok(document);
		} catch (DocumentNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateDocument(@PathVariable("id") Long id,
			@ModelAttribute Document document, @RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			Document updatedDocument;
			if (file != null) {
				updatedDocument = documentServiceImpl.updateDocument(id, document, file);
			} else {
				updatedDocument = documentServiceImpl.updateDocument(id, document, null); // Truyền giá trị null cho
																							// file
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedDocument);
		} catch (DocumentNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable("id") Long id) {
		boolean isDeleted = documentServiceImpl.deleteDocument(id);
		if (isDeleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<Document>> getAllDocuments(@RequestParam(required = false) String title) {
		List<Document> documents = documentServiceImpl.searchDocument(title);
		return ResponseEntity.ok(documents);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Document>> searchDocumentsByTitle(@RequestParam("title") String title) {
		List<Document> documents = documentServiceImpl.searchDocumentsByTitle(title);
		return ResponseEntity.ok(documents);
	}

	@GetMapping(path = "/{id}/download")
	public ResponseEntity<?> download(@RequestParam Long id, @RequestParam String fileName)
			throws MalformedURLException {

		try {
			Document document = documentServiceImpl.getDocumentById(id);
			// Tạo đường dẫn tới file
			String filePath = document.getFilePath();
			Path file = Paths.get(filePath);
			Resource resource = new UrlResource(file.toUri());

			// Kiểm tra xem file có tồn tại và có thể đọc được không
			if (resource.exists() && resource.isReadable()) {
				// Trả về dữ liệu file và header cho việc tải xuống
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + resource.getFilename() + "\"")
						.contentType(MediaType.APPLICATION_OCTET_STREAM)
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (DocumentNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
