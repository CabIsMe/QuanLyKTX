package root.quanlyktx.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String mssv;

    @Column(name = "id_room", nullable = false)
    private Integer idRoom;

    @Column(nullable = false)
    private String content;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_post", nullable = false)
    private Date timePost;

    public Comment() {
    }

    public Comment(String mssv, Integer idRoom, String content, Date timePost) {
        this.mssv = mssv;
        this.idRoom = idRoom;
        this.content = content;
        this.timePost = timePost;
    }

    public Comment(String content) {
        this.content = content;
    }
}
