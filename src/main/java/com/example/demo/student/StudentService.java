package com.example.demo.student;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
		if (studentByEmail.isPresent()) {
			throw new IllegalStateException("Email duplicado");
		}
		studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		if (!studentRepository.existsById(studentId)) {
			throw new IllegalStateException("El estudiante con id " + studentId + " no existe en la base de datos");
		}
		studentRepository.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId, String name, String email) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("El estudiante con id " + studentId + " no existe."));
		if(name != null && name.length()>0 && !student.getName().equals(name)){
			student.setName(name);
		}
		if(email != null && email.length()>0 && !student.getEmail().equals(email)){
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
			if(studentOptional.isPresent()){
				throw new IllegalStateException("Email existente");
			}
			student.setEmail(email);
		}
	}
}
