package id.rezafauzan.controller;

import id.rezafauzan.model.Penduduk;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/penduduk")
public class PendudukResource {

    @GET
    public Collection<Penduduk> list() {
        return Penduduk.listAll();
    }

    @GET
    @Path("/{id}")
    public Penduduk get(@PathParam("id") Integer id) {
        Penduduk pndk  = Penduduk.findById(id);
        if(pndk!=null){
            return pndk;
        }else{
            throw new NotFoundException("ID Penduduk tidak diketahui : " + id);
        }
    }

    @POST
    @Transactional
    public Collection<Penduduk> tambah(Penduduk pndk) {
        pndk.setId(null); //ignore id
        Penduduk.persist(pndk);
        return Penduduk.listAll();
    }

    @PUT
    @Transactional
    public Penduduk update(Penduduk pndk) {

        Penduduk pendudukUpdate = Penduduk.findById(pndk.id);
        if(pendudukUpdate!=null){
            pendudukUpdate.setNama(pndk.nama);
            Penduduk.persist(pendudukUpdate);
            return pendudukUpdate;
        }else{
            throw new NotFoundException("ID Penduduk Tidak Diketahui : " + pndk.id);
        }

    }

    @DELETE
    @Transactional
    public void delete(Penduduk pndk) {
        Penduduk pendudukDelete  = Penduduk.findById(pndk.id);
        if(pendudukDelete!=null){
            Penduduk.deleteById(pndk.id);
        }else{
            throw new NotFoundException("ID Penduduk Tidak Diketahui: " + pndk.id);
        }
    }

}