package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.service.StudentService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/search")
    public List<StudentDto> search(@RequestParam String keyWord){ return studentService.getListByTitle(keyWord);}

    @GetMapping("/add-students")
    public boolean addStudents() throws ExecutionException, InterruptedException{
        return studentService.addNewStudent();
    }
}
