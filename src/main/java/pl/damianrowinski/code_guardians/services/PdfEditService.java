package pl.damianrowinski.code_guardians.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import pl.damianrowinski.code_guardians.model.CertDTO;

import java.io.File;

@Service
public class PdfEditService {

    public void addDataToPdf(String fileSource, String fileDest, CertDTO dataToAdd) throws Exception {
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
    }
}
