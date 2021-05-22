package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //handler methods
    @GetMapping("/students")
    public String listStudents(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/student/new")
    public String addNewStudent(Model model){
        Student student = new Student();
        model.addAttribute("student",student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(
            @ModelAttribute("student") Student student
    ){
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String getStudentEditForm(
            @PathVariable Long id,
            Model model
    ){
        model.addAttribute("student",studentService.getStudentById(id));
        return "edit_form";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(
            @PathVariable Long id,
            @ModelAttribute("student") Student student,
            Model model
    ){
        Student cStudent = studentService.getStudentById(id);
        cStudent.setId(id);
        cStudent.setName(student.getName());
        cStudent.setEmail(student.getEmail());

        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(
            @PathVariable Long id
    ){
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}
