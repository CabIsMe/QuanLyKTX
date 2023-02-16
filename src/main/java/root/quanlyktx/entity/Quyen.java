package root.quanlyktx.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quyen")
public class Quyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quyen")
    private String quyen;

    @OneToMany(mappedBy = "phanQuyen")
    private List<Users> usersList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Quyen() {
    }
}
