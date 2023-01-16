package id.rezafauzan.service;

import id.rezafauzan.model.Komoditas;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@ApplicationScoped
public class DocumentGenerator {
    public void generateExcelFile(List<Komoditas> data) throws IOException {

        //Buka File Template
        FileInputStream templateFile = new FileInputStream(new File("assets/GeneratedDocument/Template/ReportTemplate.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(templateFile);

        // Pilih Sheet Page ke 1
        Sheet sheet = workbook.getSheetAt(0);

        //untuk Column Header
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("KOMODITAS");
        row.createCell(2).setCellValue("TOTAL");
        row.createCell(3).setCellValue("CREATED AT");
        row.createCell(4).setCellValue("UPDATED AT");

        //Isi Data Dari Database
        int rownumb =2;
        for (Komoditas kmdtData : data)
        {
            row = sheet.createRow(rownumb++);
            row.createCell(0).setCellValue(kmdtData.getId().toString());
            row.createCell(1).setCellValue(kmdtData.getKomoditas());
            row.createCell(2).setCellValue(kmdtData.getTotal());
            row.createCell(3).setCellValue(kmdtData.getCreatedAt().toString());
            row.createCell(4).setCellValue(kmdtData.getUpdatedAt().toString());
        }

        //Generate File Yang Sudah Diisi
        String namaFile;
        String filePath = "assets/GeneratedDocument/";
        namaFile = LocalDate.now().toString();
        FileOutputStream outputStream = new FileOutputStream(filePath + "Generated Production Komoditas Reports For Farm Pak Dengklek Created At-" + namaFile + ".xlsx");
        workbook.write(outputStream);

        //Closing
        outputStream.close();
        templateFile.close();

    }
}
