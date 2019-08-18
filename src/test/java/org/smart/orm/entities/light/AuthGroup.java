package org.smart.orm.entities.light;

import org.smart.orm.Model;
import org.smart.orm.annotations.Association;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Key;
import org.smart.orm.annotations.Table;
import org.smart.orm.data.AssociationType;

import java.util.List;

@Table("ocenter_auth_group")
public class AuthGroup extends Model<AuthGroup> {
    
    @Key
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
    
    @Association(type = AssociationType.HAS_MANY, thisKey = "id", otherKey = "uid", otherEntity = AuthGroupAccess.class)
    private List<AuthGroupAccess>  accessList;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        propertyChange("id", AuthGroup::getId);
        this.id = id;
    }
    
    public String getModule() {
        return module;
    }
    
    public void setModule(String module) {
        propertyChange("module", AuthGroup::getModule);
        this.module = module;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        propertyChange("type", AuthGroup::getType);
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        propertyChange("title", AuthGroup::getTitle);
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        propertyChange("description", AuthGroup::getDescription);
        this.description = description;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        propertyChange("status", AuthGroup::getStatus);
        this.status = status;
    }
    
    
    public String getRules() {
        return rules;
    }
    
    public void setRules(String rules) {
        propertyChange("rules", AuthGroup::getRules);
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
