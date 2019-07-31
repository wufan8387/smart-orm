package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

@Table("order")
public class Order extends Model<Order> {
    
    @Column
    private int id;
    
    @Column
    private  int uid;
    
    @Column
    private String no;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUid() {
        return uid;
    }
    
    public void setUid(int uid) {
        this.uid = uid;
    }
    
    public String getNo() {
        return no;
    }
    
    public void setNo(String no) {
        this.no = no;
    }
}
