package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.Comment;
import root.quanlyktx.service.CommentService;

import java.util.List;

@RequestMapping("api/student/comment/")
@RestController
@PreAuthorize("hasAuthority('student')")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("create")
    ResponseEntity <?> createComment(@RequestBody Comment comment){
        return commentService.addComment(comment.getContent());
    }

    @GetMapping("room/{idRoom}")
    List<Comment> comments(@PathVariable Integer idRoom){
        return commentService.readComments(idRoom);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity <?> removeComment(@PathVariable Integer id){
        return commentService.deleteComment(id) ?
             ResponseEntity.noContent().build() :  ResponseEntity.internalServerError().body("Delete comment failed");
    }
}
