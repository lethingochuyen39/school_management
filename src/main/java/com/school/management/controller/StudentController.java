package com.school.management.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.school.management.dto.StudentDTO;
import com.school.management.model.Student;
import com.school.management.service.StudentService;
import com.school.management.service.StudentServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
  // huyen
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    
    @GetMapping("/allStudent")
    public ResponseEntity<List<StudentDTO>> getAllStudent() {
        return ResponseEntity.ok(studentService.GetAllStudent());
    }

    @GetMapping("/")
    public ResponseEntity<?> getStudent(@RequestBody String email) {
        try {
            return ResponseEntity.ok(studentService.GetStudent(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createStudent")
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO entity) {
        try {
            return ResponseEntity.ok(studentService.AddStudent(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity<?> delete(@RequestBody String email) {
        try {
            return ResponseEntity.ok(studentService.DeleteStudent(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody StudentDTO entity) {
        try {
            return ResponseEntity.ok(studentService.UpdateProfile(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/giveAccessAccount")
    public ResponseEntity<?> giveAccessAccount() {
        try {
            return ResponseEntity.ok(studentService.generateAccount());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/import")
    public ResponseEntity<?> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
    
        try (// List<StudentDTO> tempStudentList = new ArrayList<StudentDTO>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream())) {
            XSSFSheet worksheet = workbook.getSheetAt(0);
            
            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                XSSFRow row = worksheet.getRow(i);
                StudentDTO tempStudent = new StudentDTO();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                String date = row.getCell(2).toString();
                LocalDate localDate = LocalDate.parse(date, formatter);
                tempStudent.setAddress(row.getCell(3).toString()).setClassName(row.getCell(7).toString()).setDob(localDate).setEmail(row.getCell(2).toString()).setGender(row.getCell(1).toString()).setImage(null).setName(row.getCell(0).toString()).setPhone(row.getCell(5).toString()).setStatus("pending");
                // tempStudent.setId((int) row.getCell(0).getNumericCellValue());
                // tempStudent.setContent(row.getCell(1).getStringCellValue());
                // tempStudentList.add(tempStudent);   
                studentService.AddStudent(tempStudent);
            }
            return ResponseEntity.ok("Student list added successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Student list added failed, "+e.getMessage());
        }
    }
    

    // huyen
    @GetMapping("/api/classes/{classId}/students")
    public ResponseEntity<List<?>> getAllStudentClass(@PathVariable Long classId) {
        List<Student> studentClass = studentServiceImpl.findByClassId(classId);
        return ResponseEntity.ok(studentClass);
    }


}
