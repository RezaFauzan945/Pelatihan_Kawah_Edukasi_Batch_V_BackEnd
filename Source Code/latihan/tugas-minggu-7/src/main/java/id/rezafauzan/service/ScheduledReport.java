package id.rezafauzan.service;

import id.rezafauzan.model.Komoditas;
import id.rezafauzan.model.KomoditasRepository;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.scheduler.Scheduled;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ScheduledReport {
    @Inject
    Mailer mailer;
    @Inject
    DocumentGenerator dg;
    @Inject
    KomoditasRepository kmdtr;
    @Transactional
    @Scheduled(cron = "0 0 0 ? * MON")
    public void panenKomoditas()
    {
        Komoditas kmdt = new Komoditas();
        kmdt.setKomoditas("Tomat");
        kmdt.setTotal(500);
        kmdtr.persist(kmdt);
    }

    @Scheduled(cron = "0 0 23 L * ?")
    public void kirimLaporan() throws IOException {
        String tanggalFile = LocalDate.now().toString();
        dg.generateExcelFile(Komoditas.listAll());
        Response.ok("REPORT GENERATED").build();
        String fileName = "Generated Production Komoditas Reports For Farm Pak Dengklek Created At-"+ LocalDate.now()+ ".xlsx";
        String attachPath = "assets/GeneratedDocument/" + fileName;
        Mail email = Mail.withHtml("rezafauzan945@gmail.com","Laporan Bulanan Produksi Pertanian Pak Dengklek","Pesan Ini dikirim Secara Otomatis Tiap Bulan menggunakan Quarkus Mailer Silahkan Lihat Lampiran Untuk Melihat Laporan Bulanan").addAttachment(fileName, new File(attachPath), "text/plain");
        mailer.send(email);
    }
}
