package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.management.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
	boolean existsByTitle(String title);

	List<Document> findByTitleContainingIgnoreCase(String title);
}