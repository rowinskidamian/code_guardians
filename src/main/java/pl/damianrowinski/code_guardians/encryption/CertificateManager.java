package pl.damianrowinski.code_guardians.encryption;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;
import pl.damianrowinski.code_guardians.domain.model.dtos.CertificateDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Service
public class CertificateManager {

    public CertificateDTO getDataFromCert(File path) throws IOException, CertificateException {
        CertificateDTO certDTO = new CertificateDTO();

        try (FileInputStream is = new FileInputStream(path)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(is);

            //getting Common Name and Organization
            X500Name x500Name = new JcaX509CertificateHolder(cert).getSubject();
            RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
            RDN org = x500Name.getRDNs(BCStyle.O)[0];
            String commonName = IETFUtils.valueToString(cn.getFirst().getValue());
            String organization = IETFUtils.valueToString(org.getFirst().getValue());
            certDTO.setCommonName(commonName);
            certDTO.setOrganization(organization);

            return certDTO;
        }
    }
}
