package models;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
public class BaseModel extends Model {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Calendar createdAt = Calendar.getInstance();


    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    public Calendar updatedAt = Calendar.getInstance();
}
