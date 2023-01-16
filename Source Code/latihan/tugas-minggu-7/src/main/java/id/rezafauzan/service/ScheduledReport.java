package id.rezafauzan.service;

import id.rezafauzan.model.Komoditas;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.scheduler.Scheduled;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ScheduledReport {
    @Inject
    Mailer mailer;
    @Inject
    DocumentGenerator dg;

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
