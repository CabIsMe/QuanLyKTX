package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByIdRoom(Integer idRoom);
    Comment findByIdAndMssv(Integer id, String mssv);
    List<Comment> findAllByMssv(String mssv);
}
