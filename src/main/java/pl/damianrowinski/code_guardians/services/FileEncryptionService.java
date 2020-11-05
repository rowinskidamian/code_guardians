package pl.damianrowinski.code_guardians.services;

import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileEncryptionService {

    public String encryptFile(String fileSource, String fileDestination, String certificateSrc) {
        String savedFilePath = fileDestination;
        File file = new File(fileDestination);
        file.getParentFile().mkdirs();

        try {
            Security.addProvider(new BouncyCastleProvider());

            Certificate cert = getPublicCertificate(certificateSrc);

            PdfDocument pdfDoc = new PdfDocument(
                    new PdfReader(fileSource),
                    new PdfWriter(fileDestination, new WriterProperties().setPublicKeyEncryption(
                            new Certificate[]{cert},
                            new int[]{EncryptionConstants.ALLOW_PRINTING},
                            EncryptionConstants.ENCRYPTION_AES_256)));

            pdfDoc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedFilePath;
    }

    private Certificate getPublicCertificate(String path) throws IOException, CertificateException {
        try (FileInputStream is = new FileInputStream(path)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(is);
        }
    }

}
