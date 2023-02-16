package root.quanlyktx.entity;

import javax.persistence.*;

import java.util.List;


@Entity
@Table(name = "quyen")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "role_name")
    private String roleName;
//    @Column(name = "role_desc")
//    private String roleDesc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role_id")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }


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

//    public String getRoleDesc() {
//        return roleDesc;
//    }
//
//    public void setRoleDesc(String roleDesc) {
//        this.roleDesc = roleDesc;
//    }
}
