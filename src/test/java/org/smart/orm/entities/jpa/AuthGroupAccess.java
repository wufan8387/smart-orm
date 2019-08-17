package org.smart.orm.entities.jpa;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ocenter_auth_group_access")
public class AuthGroupAccess implements Serializable {
    
    @Id
    @Column(name = "uid")
    private int uid;
    
    @Id
    @Column(name = "group_id")
    private int groupId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private AuthGroup group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
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
    
    public AuthGroup getGroup() {
        return group;
    }
    
    public void setGroup(AuthGroup group) {
        this.group = group;
    }

    public UCenterMember getMember() {
        return member;
    }

    public void setMember(UCenterMember member) {
        this.member = member;
    }
}
