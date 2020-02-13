package com.unisound.iot.common.mongo;

import java.io.Serializable;
/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company 北京云知声技术有限公司
 */
//@Document(collection="users")
public class User implements Serializable {

//    @Id
    private String _id;
    private String id;
    private String userid;
    private String password;

    public User(){}

    public User(String id, String userid) {
        this.id = id;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
