package id.rezafauzan.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Table( name = "penduduk" , schema = "ke_schema")
public class Penduduk extends PanacheEntity {

    public Long id;

    public String nama;


//    setter & getter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}
