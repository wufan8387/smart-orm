package org.smart.orm.entities.light;

import org.smart.orm.Model;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

@javax.persistence.Table(name = "ocenter_auth_group_access")
@Table("ocenter_auth_group_access")
public class AuthGroupAccess  extends Model<AuthGroupAccess> {
    
    @Column("uid")
    private int uid;
    
    @Column("group_id")
    private int groupId;
    
    private AuthGroup authGroup;
    
    private UCenterMember member;
    
    public int getUid() {
        return uid;
    }
    
    public void setUid(int uid) {
        this.uid = uid;
    }
    
    public int getGroupId() {
        return groupId;
    }
    
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    
    public AuthGroup getAuthGroup() {
        return authGroup;
    }
    
    public void setAuthGroup(AuthGroup authGroup) {
        this.authGroup = authGroup;
    }
    
    public UCenterMember getMember() {
        return member;
    }
    
    public void setMember(UCenterMember member) {
        this.member = member;
    }
}
