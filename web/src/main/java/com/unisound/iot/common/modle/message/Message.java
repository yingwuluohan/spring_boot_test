package com.unisound.iot.common.modle.message;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Message<T> implements Serializable {

    private Long id;

    private int type;

    private String content;

    private volatile List< T > list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message<?> message = (Message<?>) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


}
