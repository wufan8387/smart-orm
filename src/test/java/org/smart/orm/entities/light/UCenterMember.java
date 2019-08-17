package org.smart.orm.entities.light;

import org.smart.orm.Model;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Key;
import org.smart.orm.annotations.Table;


@Table("ocenter_ucenter_member")
public class UCenterMember extends Model<UCenterMember> {

    @Key
    @Column
    private int id;
    

    @Column("username")
    private String userName;
    
    
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
}
