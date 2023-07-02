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
	private static final String PUBLIC_IMAGE_FOLDER = "D:/Desktop/project/ui/school-ui/public/images/";

	@Autowired
	private EventNewsRepository eventNewsRepository;

	@Override
	public EventNews createEventNews(EventNews eventNews,
			MultipartFile image) {
		String title = eventNews.getTitle();
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Tiêu đề là bắt buộc.");
		}

		if (eventNewsRepository.existsByTitle(title)) {
			throw new IllegalArgumentException("Tên tiêu đề đã tồn tại.");
		}

		if (image != null) {
			String imageName = image.getOriginalFilename();
			String imagePath = saveImage(image);

			eventNews.setImageName(imageName);
			eventNews.setImagePath(imagePath);
		}

		eventNews.setIsActive(true);
		eventNews.setCreatedAt(LocalDateTime.now());
		eventNews.setUpdatedAt(LocalDateTime.now());
		return eventNewsRepository.save(eventNews);
	}

	@Override
	public EventNews getEventNewsById(Long id) {
		return eventNewsRepository.findById(id)
				.orElseThrow(() -> new EventNewsNotFoundException("Không tìm thấy tin tức với id: " + id));
	}

	@Override
	public EventNews updateEventNews(Long id, EventNews eventNews, MultipartFile image) {
		if (!eventNewsRepository.existsById(id)) {
			throw new EventNewsNotFoundException("Không tìm thấy tin tức với id: " + id);
		}

		String title = eventNews.getTitle();
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Tiêu đề là bắt buộc.");
		}

		EventNews existingEventNews = eventNewsRepository.findById(id)
				.orElseThrow(() -> new EventNewsNotFoundException("Không tìm thấy tin tức với id: " + id));

		String newTitle = eventNews.getTitle();
		if (!existingEventNews.getTitle().equals(newTitle) && eventNewsRepository.existsByTitle(newTitle)) {
			throw new IllegalArgumentException("Tên tiêu đề đã tồn tại.");
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
			Path publicImagePath = Path.of(PUBLIC_IMAGE_FOLDER).toAbsolutePath().normalize();

			if (!Files.exists(publicImagePath)) {
				Files.createDirectories(publicImagePath);
			}

			String imageName = image.getOriginalFilename();
			String imageExtension = getFileExtension(imageName);
			String newImageName = generateUniqueFileName(imageExtension);
			String publicImagePathStr = PUBLIC_IMAGE_FOLDER + newImageName;

			Files.copy(image.getInputStream(), Path.of(publicImagePathStr), StandardCopyOption.REPLACE_EXISTING);

			return "images/" + newImageName;
		} catch (IOException e) {
			throw new RuntimeException("Lỗi.");
		}
	}

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1);
		}
		return "";
	}

	private String generateUniqueFileName(String fileExtension) {
		String fileName = LocalDateTime.now().toString().replace(":", "-");
		return fileName + "." + fileExtension;
	}

	public class EventNewsNotFoundException extends RuntimeException {
		public EventNewsNotFoundException(String message) {
			super(message);
		}
	}

	@Override
	public List<EventNews> searchNews(String title) {
		if (title == null || title.trim().isEmpty()) {
			return getAllEventNews();
		} else {
			return searchEventNewsByTitle(title);
		}
	}

	@Override
	public EventNews updateNewsStatus(Long id) {
		EventNews existingEventNews = eventNewsRepository.findById(id)
				.orElseThrow(() -> new EventNewsNotFoundException("Không tìm thấy tin tức với id: " + id));

		boolean isActive = existingEventNews.getIsActive();
		existingEventNews.setIsActive(!isActive);
		existingEventNews.setUpdatedAt(LocalDateTime.now());

		return eventNewsRepository.save(existingEventNews);
	}

}
