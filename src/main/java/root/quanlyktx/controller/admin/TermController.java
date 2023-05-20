package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.TermDTO;
import root.quanlyktx.service.TermService;

import java.util.List;

@RestController
@RequestMapping("/api/manage/term")
public class TermController {
    @Autowired
    private TermService termService;
    @GetMapping("/")
    public List<TermDTO> getAll(){
        return termService.getAllTerm();
    }

    @GetMapping("/combobox")
    public List<TermDTO> comboboxTermHaveContract(@RequestParam(name = "status") boolean status){
        return termService.getAllTermAccordingToStatusContract(status);
    }

    @GetMapping("/search/")
    public TermDTO getSingle(@RequestParam(required = false) Integer id){
        return termService.getSingleTerm(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTerm(@RequestBody TermDTO termDTO){
        return termService.createTerm(termDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update_term(@PathVariable("id") Integer id, @RequestBody TermDTO termDTO){
        return termService.updateTerm(id,termDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeTerm(@PathVariable("id") Integer id){
        return termService.deleteTerm(id);
    }

}
