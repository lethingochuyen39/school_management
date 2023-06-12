package com.school.management.service;

import com.school.management.model.EventNews;
import com.school.management.repository.EventNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventNewsServiceImpl implements EventNewsService {
	private static final String UPLOAD_FOLDER = "uploads/news/";

	@Autowired
	private EventNewsRepository eventNewsRepository;

	@Override
	public EventNews createEventNews(EventNews eventNews, MultipartFile image) {
		validateEventNewsFields(eventNews);

		String title = eventNews.getTitle();
		if (eventNewsRepository.existsByTitle(title)) {
			throw new IllegalArgumentException("EventNews with the same title already exists.");
		}

		String imageName = image.getOriginalFilename();
		String imagePath = saveImage(image);

		eventNews.setImageName(imageName);
		eventNews.setImagePath(imagePath);
		eventNews.setIsActive(true);
		eventNews.setCreatedAt(LocalDateTime.now());
		eventNews.setUpdatedAt(LocalDateTime.now());

		return eventNewsRepository.save(eventNews);
	}

	@Override
	public EventNews getEventNewsById(Long id) {
		return eventNewsRepository.findById(id)
				.orElseThrow(() -> new EventNewsNotFoundException("EventNews not found with id: " + id));
	}

	@Override
	public EventNews updateEventNews(Long id, EventNews eventNews, MultipartFile image) {
		if (!eventNewsRepository.existsById(id)) {
			throw new EventNewsNotFoundException("EventNews not found with id: " + id);
		}

		validateEventNewsFields(eventNews);

		EventNews existingEventNews = eventNewsRepository.findById(id)
				.orElseThrow(() -> new EventNewsNotFoundException("EventNews not found with id: " + id));

		String newTitle = eventNews.getTitle();
		if (!existingEventNews.getTitle().equals(newTitle) && eventNewsRepository.existsByTitle(newTitle)) {
			throw new IllegalArgumentException("EventNews with the same title already exists.");
		}

		existingEventNews.setTitle(newTitle);
		existingEventNews.setContent(eventNews.getContent());
		existingEventNews.setIsActive(eventNews.getIsActive());
		existingEventNews.setUpdatedAt(LocalDateTime.now());

		if (image != null && !image.isEmpty()) {
			String imagePath = saveImage(image);
			existingEventNews.setImagePath(imagePath);
			existingEventNews.setImageName(image.getOriginalFilename());
		}

		return eventNewsRepository.save(existingEventNews);
	}

	@Override
	public boolean deleteEventNews(Long id) {
		if (!eventNewsRepository.existsById(id)) {
			return false;
		}
		eventNewsRepository.deleteById(id);
		return true;
	}

	@Override
	public List<EventNews> getAllEventNews() {
		return eventNewsRepository.findAll();
	}

	@Override
	public List<EventNews> searchEventNewsByTitle(String title) {
		return eventNewsRepository.findByTitleContainingIgnoreCase(title);
	}

	private String saveImage(MultipartFile image) {
		try {
			Path uploadPath = Path.of(UPLOAD_FOLDER).toAbsolutePath().normalize();

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String imageName = image.getOriginalFilename();
			String imagePath = uploadPath + "/" + imageName;

			Files.copy(image.getInputStream(), Path.of(imagePath), StandardCopyOption.REPLACE_EXISTING);

			return imagePath;
		} catch (IOException e) {
			throw new RuntimeException("Failed to save image.");
		}
	}

	private void validateEventNewsFields(EventNews eventNews) {
		String title = eventNews.getTitle();
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Title is required.");
		}
	}

	public class EventNewsNotFoundException extends RuntimeException {
		public EventNewsNotFoundException(String message) {
			super(message);
		}
	}
}
