package pl.damianrowinski.code_guardians.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

@Service
public class PdfEditService {

    public void addDataToPdf(String fileSource, String fileDest, String dataToAdd) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(
                new PdfReader(fileSource),
                new PdfWriter(fileDest));

        Document document = new Document(pdfDoc);

        Paragraph paragraphToAdd = new Paragraph(dataToAdd);
        AreaBreak ab = new AreaBreak();
        document.add(ab);
        document.add(paragraphToAdd);
        document.close();

        pdfDoc.close();
    }
}
