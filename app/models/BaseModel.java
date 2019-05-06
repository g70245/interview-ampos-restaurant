package models;

import io.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@MappedSuperclass
public class BaseModel extends Model {

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Calendar createdAt = Calendar.getInstance();


    @Temporal(TemporalType.TIMESTAMP)
    public Calendar updatedAt = Calendar.getInstance();

    @PrePersist
    @PreUpdate
    public void auditBeforePersist() {
        if (this.createdAt == null)
            this.createdAt = Calendar.getInstance();

        this.updatedAt = Calendar.getInstance();
    }
}
