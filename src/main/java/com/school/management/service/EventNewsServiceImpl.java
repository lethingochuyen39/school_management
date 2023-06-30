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
			Path uploadPath = Path.of(UPLOAD_FOLDER).toAbsolutePath().normalize();

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String imageName = image.getOriginalFilename();
			String imagePath = uploadPath + "/" + imageName;

			Files.copy(image.getInputStream(), Path.of(imagePath), StandardCopyOption.REPLACE_EXISTING);

			return imagePath;
		} catch (IOException e) {
			throw new RuntimeException("Lỗi.");
		}
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
	public void updateNewsStatus(EventNews news, Integer isActive) {
		if (isActive.equals(0)) { // Sử dụng equals() để so sánh giá trị Integer
			news.setIsActive(false);
		} else {
			news.setIsActive(true);
		}
		eventNewsRepository.save(news);
	}

	public void updateNewsStatus(Long id, boolean isActive) {
		// Lấy mục tin tức từ cơ sở dữ liệu dựa trên id
		EventNews news = eventNewsRepository.findById(id)
				.orElseThrow(() -> new EventNewsNotFoundException("Không tìm thấy tin tức với id: " + id));

		// Cập nhật trạng thái của mục tin tức
		news.setIsActive(isActive);

		// Lưu mục tin tức đã được cập nhật vào cơ sở dữ liệu
		eventNewsRepository.save(news);
	}
}
