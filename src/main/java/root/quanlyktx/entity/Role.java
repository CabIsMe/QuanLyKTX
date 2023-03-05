package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.List;


@Entity
@Table(name = "quyen")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quyen")
    private String roleName;


//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role_id")
//    private List<Student> users;
//
//
//    public List<Student> getUsers() {
//        return users;
//    }


    public Role(){}
    public Role(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
//        this.roleDesc = roleDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
