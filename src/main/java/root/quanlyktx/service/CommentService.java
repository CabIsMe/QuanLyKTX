package root.quanlyktx.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.Comment;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.repository.CommentRepository;
import root.quanlyktx.repository.HopDongKTXRepository;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final long timePeriodCommented= 86400000L * 10;

    public static String getUsernameFromSecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            logger.error("Is not authenticated");
            return null;
        }
        return authentication.getName();
    }

    public ResponseEntity<?> addComment(String content){
        String mssv= getUsernameFromSecurityContextHolder();
        HopDongKTX hopDongKTX= hopDongKTXRepository.findFirstByMSSVAndTrangThaiTrueOrderByNgayLamDonDesc(mssv);
        if(mssv==null || hopDongKTX==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MSSV not found!");
        long endTerm=hopDongKTX.getTerm().getNgayKetThuc().getTime();
        long currentTime= new Date().getTime();
        // Nếu trước thời gian kết thúc hđ và sau thời gian kết thúc hđ(>3 ngày) không được cmt
        if(currentTime - endTerm < 0 || currentTime - endTerm > timePeriodCommented){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't comment at the moment!");
        }
        try{
            Comment comment= new Comment(mssv, hopDongKTX.getIdPhongKTX(), content, new Date());
            commentRepository.save(comment);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        List<Comment> comments= commentRepository.findAllByMssv(mssv);
        return ResponseEntity.ok(comments);
    }

    public List<Comment> readComments(Integer idRoom){
        return commentRepository.findAllByIdRoom(idRoom);
    }

    public boolean deleteComment(Integer id){
        String mssv= getUsernameFromSecurityContextHolder();
        if(mssv== null)
            return false;

        Comment comment= commentRepository.findByIdAndMssv(id, mssv);
        if(comment==null)
            return false;
        try{
            commentRepository.delete(comment);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
