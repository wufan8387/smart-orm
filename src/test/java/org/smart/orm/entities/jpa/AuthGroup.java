package org.smart.orm.entities.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ocenter_auth_group")
public class AuthGroup implements Serializable {
    
    @Id
    @Column
    private int id;
    
    @Column
    private String module;
    
    @Column
    private int type;
    
    @Column
    private String title;
    
    @Column
    private String description;
    
    @Column
    private int status;
    
    @Column
    private String rules;
    
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<AuthGroupAccess>  accessList;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getModule() {
        return module;
    }
    
    public void setModule(String module) {
        this.module = module;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    
    public String getRules() {
        return rules;
    }
    
    public void setRules(String rules) {
        this.rules = rules;
    }
    
    public List<AuthGroupAccess> getAccessList() {
        return accessList;
    }
    
    public void setAccessList(List<AuthGroupAccess> accessList) {
        this.accessList = accessList;
    }
    
    @Override
    public String toString() {
        return id + "-" + type + "-" + module + "-" + title + "-" + description + "-" + status + "-" + rules;
    }
}
