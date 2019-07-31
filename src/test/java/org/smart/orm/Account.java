package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

@Table("account")
public class Account extends Model<Account> {
    
    @Column
    private String id;
    
    @Column
    private  String name;
    
    @Column
    private String remark;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
