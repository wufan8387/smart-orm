package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

@Table("ocenter_auth_group")
public class AuthGroup extends Model<AuthGroup> {
    
    @Column
    private String id;
    
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
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
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
    
    @Override
    public String toString() {
        return id + "-" + type + "-" + module + "-" + title + "-" + description + "-" + status + "-" + rules;
    }
}
