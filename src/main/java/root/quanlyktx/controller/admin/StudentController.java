package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Student;
import root.quanlyktx.service.StudentService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/manage/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

//    @GetMapping("/search")
//    public List<StudentDto> search(@RequestParam String keyWord){ return studentService.getListByTitle(keyWord);}

    @GetMapping("/search")
    public List<StudentDto> getListResult(@RequestParam @Nullable String username,@RequestParam @Nullable String name){
        return studentService.getAllBySearchKey(username, name);
    }

    @GetMapping("/add-students")
    public boolean addStudents() throws ExecutionException, InterruptedException{
        return studentService.addNewStudent();
    }

//    @GetMapping("/")
//    public List<StudentDto> studentDtoList(@RequestParam("typeSort") boolean typeSort){
//        return studentService.getAllStudentInDorm(typeSort);
//    }
    @GetMapping("")
    public ResponseEntity<List<StudentDto>> getAllEmployees(
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "username") String sortBy,
            @RequestParam(defaultValue = "true") boolean typeSort,
            @RequestParam @Nullable Boolean gender)
    {

        List<StudentDto> list= studentService.getAllStudentEnabled(0, pageSize, sortBy,typeSort, gender);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

}
