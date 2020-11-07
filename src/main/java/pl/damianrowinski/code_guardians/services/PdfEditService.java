package pl.damianrowinski.code_guardians.services;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
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

    public File addDataToPdf(File fileSource, File fileDest, CertificateDTO dataToAdd) throws Exception {

        String fileSourceName = fileSource.getName();
        String changedTempFileName = fileSourceName.replace(".pdf", "temp.pdf");
        File file = new File(fileDest, changedTempFileName);

        PdfDocument pdfDoc = new PdfDocument(
                new PdfReader(fileSource),
                new PdfWriter(file));

        Document document = new Document(pdfDoc);

        int numberOfPages = pdfDoc.getNumberOfPages();
        PageSize ps = new PageSize(pdfDoc.getFirstPage().getPageSize());
        pdfDoc.addNewPage(numberOfPages + 1, ps);

        AreaBreak areaBreak = new AreaBreak();
        for (int i = 0; i < numberOfPages; i++) {
            document.add(areaBreak);
        }

        Paragraph paragraphToAdd = new Paragraph(dataToAdd.toString());
        document.add(paragraphToAdd);
        document.close();

        pdfDoc.close();
        log.info("Edited PDF, added data: " + dataToAdd);
        return file;
    }
}
