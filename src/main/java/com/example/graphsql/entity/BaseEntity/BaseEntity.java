package com.example.graphsql.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "Create_date")
    private Date CreateDate;

    @Column(name = "Create_By")
    private String CreateBy;

    @Column(name = "Modifier_date")
    private Date ModifierDate;

    @Column(name = "Modifier_by")
    private String ModifierBy;

    @PrePersist
    public void prePersist(){
        this.CreateBy ="CreateBy";
        this.CreateDate = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        this.ModifierBy ="Modifier";
        this.ModifierDate = new Date();
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public Date getModifierDate() {
        return ModifierDate;
    }

    public void setModifierDate(Date modifierDate) {
        ModifierDate = modifierDate;
    }

    public String getModifierBy() {
        return ModifierBy;
    }

    public void setModifierBy(String modifierBy) {
        ModifierBy = modifierBy;
    }
}
