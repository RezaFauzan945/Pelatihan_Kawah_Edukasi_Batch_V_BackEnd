package id.rezafauzan.controller;

import id.rezafauzan.model.Komoditas;
import id.rezafauzan.service.DocumentGenerator;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

@Path("/kebun")
public class KebunController {
    @Inject
    Mailer mailer;
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
    @Blocking
    public Response kirimLaporan() throws IOException {
          String tanggalFile = LocalDate.now().toString();
          dg.generateExcelFile(Komoditas.listAll());
          String fileName = "Generated Production Komoditas Reports For Farm Pak Dengklek Created At-"+ LocalDate.now()+ ".xlsx";
          String attachPath = "assets/GeneratedDocument/" + fileName;
          Mail email = Mail.withText("rezafauzan945@gmail.com","Laporan Produksi Pertanian Pak Dengklek","Pesan Ini dikirim menggunakan Quarkus Mailer! Lihat Lampiran Untuk Melihat Laporan Produksi Pak Dengklek").addAttachment(fileName, new File(attachPath), "text/plain");
          mailer.send(email);
          return Response.ok("REPORT GENERATED").build();
    }
}
