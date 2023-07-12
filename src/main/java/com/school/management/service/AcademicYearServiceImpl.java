package com.school.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.AcademicYear;
import com.school.management.repository.AcademicYearRespository;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

	@Autowired
	private AcademicYearRespository academicYearRepository;

	@Override
	public AcademicYear createAcademicYear(AcademicYear academicYear) {

		if (academicYear.getName() == null || academicYear.getStartDate() == null
				|| academicYear.getEndDate() == null) {
			throw new IllegalArgumentException("Tên, ngày bắt đầu và ngày kết thúc là bắt buộc.");
		}

		if (academicYearRepository.existsByName(academicYear.getName())) {
			throw new IllegalArgumentException("Năm học với tên đã tồn tại.");
		}
		// Kiểm tra xem ngày bắt đầu có nhỏ hơn ngày kết thúc không
		if (academicYear.getStartDate().isAfter(academicYear.getEndDate())) {
			throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
		}

		// Kiểm tra trùng lặp với các năm học hiện có
		List<AcademicYear> existingYears = academicYearRepository.findAll();
		for (AcademicYear existingYear : existingYears) {
			LocalDate existingStartDate = existingYear.getStartDate();
			LocalDate existingEndDate = existingYear.getEndDate();

			if ((academicYear.getStartDate().isAfter(existingStartDate)
					&& academicYear.getStartDate().isBefore(existingEndDate))
					|| (academicYear.getEndDate().isAfter(existingStartDate)
							&& academicYear.getEndDate().isBefore(existingEndDate))
					|| (academicYear.getStartDate().isEqual(existingStartDate)
							&& academicYear.getEndDate().isEqual(existingEndDate))) {
				throw new IllegalArgumentException("Thòi gian năm học trùng lặp với năm học đã tồn tại.");
			}
		}
		return academicYearRepository.save(academicYear);
	}

	@Override
	public AcademicYear getAcademicYearById(Long id) {
		return academicYearRepository.findById(id)
				.orElseThrow(() -> new AcademicYearNotFoundException("Không tìm thấy năm học với id: " + id));

	}

	@Override
	public AcademicYear updateAcademicYear(Long id, AcademicYear academicYear) {
		if (academicYear.getName() == null || academicYear.getStartDate() == null
				|| academicYear.getEndDate() == null || academicYear.getName().isEmpty()) {
			throw new IllegalArgumentException("Tên, ngày bắt đầu và ngày kết thúc là bắt buộc.");
		}
		if (!academicYearRepository.existsById(id)) {
			throw new AcademicYearNotFoundException("Không tìm thấy năm học với id: " + id);
		}

		if (academicYear.getName() == null || academicYear.getStartDate() == null
				|| academicYear.getEndDate() == null) {
			throw new IllegalArgumentException("Tên , ngày bắt đầu và ngày kết thúc là bắt buộc.");
		}
		// Kiểm tra xem ngày bắt đầu có nhỏ hơn ngày kết thúc không
		if (academicYear.getStartDate().isAfter(academicYear.getEndDate())) {
			throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
		}

		AcademicYear existingAcademicYear = academicYearRepository.findById(id)
				.orElseThrow(() -> new AcademicYearNotFoundException("Không tìm thấy năm học với id: " + id));

		String newName = academicYear.getName();
		if (!existingAcademicYear.getName().equals(newName)) {
			List<AcademicYear> academicYearsWithSameName = academicYearRepository.findByName(newName);
			if (!academicYearsWithSameName.isEmpty()) {
				throw new IllegalArgumentException("Năm học với tên đã tồn tại.");
			}
		}
		// Kiểm tra trùng lặp với các năm học hiện có, trừ năm học đang được cập nhật
		List<AcademicYear> existingYears = academicYearRepository.findAll();
		for (AcademicYear existingYear : existingYears) {
			if (!existingYear.getId().equals(id)) {
				LocalDate existingStartDate = existingYear.getStartDate();
				LocalDate existingEndDate = existingYear.getEndDate();

				if ((academicYear.getStartDate().isAfter(existingStartDate)
						&& academicYear.getStartDate().isBefore(existingEndDate))
						|| (academicYear.getEndDate().isAfter(existingStartDate)
								&& academicYear.getEndDate().isBefore(existingEndDate))
						|| (academicYear.getStartDate().isEqual(existingStartDate)
								&& academicYear.getEndDate().isEqual(existingEndDate))
						|| (academicYear.getStartDate().isBefore(existingStartDate)
								&& academicYear.getEndDate().isAfter(existingEndDate))) {
					throw new IllegalArgumentException("Thời gian năm học trùng lặp với năm học đã tồn tại.");
				}
			}
		}

		existingAcademicYear.setName(academicYear.getName())
				.setStartDate(academicYear.getStartDate())
				.setEndDate(academicYear.getEndDate());

		return academicYearRepository.save(existingAcademicYear);
	}

	@Override
	public boolean deleteAcademicYear(Long id) {
		if (!academicYearRepository.existsById(id)) {
			throw new AcademicYearNotFoundException("Không tìm thấy năm học nào với id: " + id);
		}
		academicYearRepository.deleteById(id);
		return true;
	}

	@Override
	public List<AcademicYear> getAllAcademicYears() {
		return academicYearRepository.findAll();
	}

	@Override
	public List<AcademicYear> getAcademicYearsByName(String name) {
		return academicYearRepository.findByNameContainingIgnoreCase(name);
	}

	public class AcademicYearNotFoundException extends RuntimeException {
		public AcademicYearNotFoundException(String message) {
			super(message);
		}
	}

	@Override
	public List<AcademicYear> searchAcademicYears(String name) {
		if (name == null || name.trim().isEmpty()) {
			return getAllAcademicYears(); // Trả về toàn bộ danh sách nếu name là null, rỗng hoặc không có giá trị
		} else {
			return getAcademicYearsByName(name); // Trả về danh sách các AcademicYear theo name nếu có giá trị
		}
	}

}
