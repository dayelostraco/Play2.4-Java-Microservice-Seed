package models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;

/**
 * Created by Dayel Ostraco
 * 10/28/15.
 */
@Entity
@Table(name = "SampleModel")
public class SampleModel extends AuditableModel {

    private static Finder<Long, SampleModel> find = new Finder<>(Long.class, SampleModel.class);

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<SampleModel> findAll() {
        return find.all();
    }

    public static SampleModel findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
}
