package models.entities;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.util.Date;

/**
 * Created by Dayel Ostraco
 * 10/28/15.
 */
@MappedSuperclass
public abstract class AuditableModel extends Model {

    @CreatedTimestamp
    @Column(name = "created")
    private Date created;

    @UpdatedTimestamp
    @Column(name = "modified")
    private Date modified;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
