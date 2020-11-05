package pl.damianrowinski.code_guardians.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.code_guardians.domain.model.dtos.CertificateDTO;

import java.io.File;

@Service
@Slf4j
public class PdfEditService {

    public String addDataToPdf(String fileSource, String fileDest, CertificateDTO dataToAdd) throws Exception {

        String savedFilePath = fileDest;
        File file = new File(fileDest);
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(
                new PdfReader(fileSource),
                new PdfWriter(fileDest));

        Document document = new Document(pdfDoc);

        Paragraph paragraphToAdd = new Paragraph(dataToAdd.toString());
        AreaBreak ab = new AreaBreak();
        document.add(ab);
        document.add(paragraphToAdd);
        document.close();

        pdfDoc.close();
        log.info("Edited PDF, added data: " + dataToAdd);
        return savedFilePath;
    }
}
