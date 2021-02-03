package com.example.RESTspring.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        boolean exists = studentRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Student with Id " + id + " does not exists.");
        }
        return studentRepository.findById(id).get();
    }

    public void addNewStudent(Student student) {
        if(student.getEmail()==null || student.getName()==null || student.getDob()==null){
            throw new IllegalStateException("Invalid arguments");
        }

        Optional<Student> studentByEmail =  studentRepository.
                findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("Email already registered");
        }
//        more validations can be done here.

        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists){
            throw new IllegalStateException("Student with ID " + id + " does not exists");
        }
        studentRepository.deleteById(id);
    }


//    transactional means it works on low level i.e no need to use jpa queries
    @Transactional
    public void updateStudent(Long id, String name, String email, Map<String,String> json) {
       if(name==null&&email==null && json!=null){
           name = json.get("name");
           email = json.get("email");
       }

        Student student = studentRepository.findById(id).
                orElseThrow(()-> new IllegalStateException("Student with ID " + id + " does not exists.")
                );
        if(name!=null && name.length()>0 && !name.equals(student.getName())){
            student.setName(name);
        }
        if(email!=null && email.length()>0 && !email.equals(student.getEmail())){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("Email Already taken");
            }
            student.setEmail(email);
        }
    }
}
