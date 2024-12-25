package com.ebook.domain;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class AbstractClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @PrePersist
    public void initializeCreatedAt(){

        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void initializeModifiedAt(){
        this.modifiedAt= LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractClass that = (AbstractClass) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
