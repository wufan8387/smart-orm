package org.smart.orm.entities.jpa;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ocenter_ucenter_member")
public class UCenterMember implements Serializable {

    @Id
    @Column
    private int id;
    

    @Column(name = "username")
    private String userName;
    
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<AuthGroupAccess> accessList;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public List<AuthGroupAccess> getAccessList() {
        return accessList;
    }
    
    public void setAccessList(List<AuthGroupAccess> accessList) {
        this.accessList = accessList;
    }
}
