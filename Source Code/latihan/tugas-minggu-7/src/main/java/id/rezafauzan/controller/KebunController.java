package id.rezafauzan.controller;

import id.rezafauzan.model.Komoditas;
import id.rezafauzan.service.DocumentGenerator;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;

@Path("/kebun")
public class KebunController {
    @Inject
    ReactiveMailer mailer;
    @Inject
    DocumentGenerator dg;

    @GET
    public Collection<Komoditas> list() {
        return Komoditas.listAll();
    }

    @POST
    @Transactional
    public Collection<Komoditas> tambah(Komoditas kmdt) {
        kmdt.setId(null); //ignore id
        Komoditas.persist(kmdt);
        return Komoditas.listAll();
    }

    @Path("/report")
    @GET

//    public Uni<Void> kirimLaporan()
//    {
//        Mail email = Mail.withText("rezafauzan945@gmail.com","Test Quarkus Mailer","Pesan Ini dikirim menggunakan Quarkus Mailer");
//        return mailer.send(email);
//    }
    public Response generateDocument() throws IOException {
        dg.generateExcelFile(Komoditas.listAll());
        return Response.ok("REPORT GENERATED").build();
    }

}
