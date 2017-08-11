package com.betterzhang.common.http;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/03 下午 4:31
 * Desc   : Https处理工具类
 */
public class HttpsUtil {

    static Collection<? extends Certificate> dzInoutCert;

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            X509TrustManager trustManager;
            SSLSocketFactory sslSocketFactory;
            // Create a trust manager that does not validate certificate chains
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static X509TrustManager trustManagerForCertificates(InputStream in) throws GeneralSecurityException {
        CertificateFactory cerficateFy = CertificateFactory.getInstance("X.509");
        dzInoutCert = cerficateFy.generateCertificates(in);

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : dzInoutCert) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // By convention, 'null' creates an empty key store.
            InputStream in = null;
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


    static InputStream trustedCertificatesInputStream() {
        /**
         * PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
         * https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
         * sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
         * Typically developers will need to get a PEM file from their organization's TLS administrator.
         */
        final String DZ_INOUT_CERT = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDIjCCAgoCCQDLWBYrY5bbJDANBgkqhkiG9w0BAQsFADBTMQswCQYDVQQGEwJD\n" +
                "TjETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0\n" +
                "cyBQdHkgTHRkMQwwCgYDVQQDDANqbWUwHhcNMTQxMTI2MDc1MTUyWhcNMjQxMTIz\n" +
                "MDc1MTUyWjBTMQswCQYDVQQGEwJDTjETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8G\n" +
                "A1UECgwYSW50ZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMQwwCgYDVQQDDANqbWUwggEi\n" +
                "MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCYTPYFsxbKLcExuMEzuZhuvO90\n" +
                "MUUldmKPEmRA+AoeSEIcFDGB0ETMBEf/PmRRkP3ZE3kYD6i0xxeODOsdlddlnS1b\n" +
                "4PvIVFpGw46GaMUAZR9gP/BINaqt7cc1Kt0NfGDl6eqEiHNHYO8tk8tA0hhKV04x\n" +
                "+U60LdpLQNwEHl6mv4EotRL/C+k8y3zQSksfQMA3CJ+bdwQmkXd1d3TpYozS/o1X\n" +
                "wPxXB8nRa5InZbx9mbdPF2uQla52IvslQmll+o8gCGQhBZNpUxD32KVd1uwkTn2V\n" +
                "ieu/34hKj2ektQKMhG6UyjUvBDV/idVdF8qjUqo05FbWlHSTN8ayC35AN+EFAgMB\n" +
                "AAEwDQYJKoZIhvcNAQELBQADggEBABVGWDdDFTzhAvueQcwBJmJWpyuAp/78TMmB\n" +
                "KeXvTRFwxiI1d6t4RBCd1MxSyjmxanaDtB+1rQjDFpY3NvjIroqrGvSIowUS0Uxq\n" +
                "WASIf4k/lXqNrGe17wIIiEJ+Fu3rUx/LPhQ/0SWLlzy2Hyb4YO57uvvKsc1Bqc4v\n" +
                "SSFuGk24CaHK52FekfEB74vbcI8lM9l1Rix0izMtAG8GuIw9cfCxRaNzmv90uh/c\n" +
                "9VxUV6nlaIo/CsWphguBCq1PlXU4lF7Ts1xlYh8ti6mSJH6hdXcwAXZ3uWQa3Xn5\n" +
                "RPEzrH9+R+njIL0KJcsbKvAgELtkJRTCY5oVpzAr6dN0jNj+Kdk=\n" +
                "-----END CERTIFICATE-----\n";

        final String SPOT_REDWINE_CERT = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDFTCCAf0CCQCwPbMNwvQNETANBgkqhkiG9w0BAQsFADBUMQswCQYDVQQGEwJD\n" +
                "TjETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0\n" +
                "cyBQdHkgTHRkMQ0wCwYDVQQDDAREWVlUMB4XDTE0MTAwMjA3NTQyOFoXDTI0MDky\n" +
                "OTA3NTQyOFowRTELMAkGA1UEBhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAf\n" +
                "BgNVBAoMGEludGVybmV0IFdpZGdpdHMgUHR5IEx0ZDCCASIwDQYJKoZIhvcNAQEB\n" +
                "BQADggEPADCCAQoCggEBALLBIYa6YQrwSaK1UB9ifZ49sct1woj/5W9VEYDYqy/5\n" +
                "5brQNgEC3f21Zr+XuMl2Gks7rPqbvPFEGVi4t6IY0pHa4zVx4k3PtTxjopBQk4NL\n" +
                "iOIn9Yma2cI/hxIfYrchm7a4TKBQ9kYsioGiJY//E+TbfUob9xsYMQrMzknkqbio\n" +
                "C0qG75gxXVlVeN+1oAinun5UJqCh5w5aDANmB4NjaRgCLkW847TfFtyTBOTqV5By\n" +
                "2x1zHzbsyj/FAIeoMSVASyHZFqjDzd1pJaghrRSvLXHsTISGoU1Kk1DEBd3WAvcJ\n" +
                "W8rrmwaz8klSyzi5g78fjLskN4PX7RgizXSwej/vZHMCAwEAATANBgkqhkiG9w0B\n" +
                "AQsFAAOCAQEAR9Y784uBDKSuozVLqxSXtqp1xGqwgGHX6h1KowdH7mGAb+2rg5si\n" +
                "rExoJDEQUbTB2r2Lgy/gpUHQ0ZuLHMOEmXb6Mwh5rlwjwyDrGmUuRI1PyApSCjve\n" +
                "l6PuMdMvphrwEYPnMfe4q9G9rrc32bvMxZsoH5cKq/tvYGpjQNKw4Dyt707OVK7u\n" +
                "QEwUiOrl5+hCo72SilqTQ3VpLXTZABnnpoa2W4xNnqerzHNUy6TrMC17WYdY7CN4\n" +
                "z34HiJ7OTgzy7k+1r3CBQJYUhp123SmXVEbPiCm7e4ttcXgDi+S8bdl3FkAFBS9j\n" +
                "kK7xJF9F0GUKrdSTLsep2nMKB5fmn5rlqg==\n" +
                "-----END CERTIFICATE-----\n";

        final String PUER_WARNING_APP_LAN_CERT = "-----BEGIN CERTIFICATE-----\n" +
                "MIIEDTCCAvWgAwIBAgIJANplpchsTeIVMA0GCSqGSIb3DQEBBQUAMIGcMQswCQYD\n" +
                "VQQGEwJDTjEQMA4GA1UECAwHamlhbmdzdTEQMA4GA1UEBwwHbmFuamluZzEUMBIG\n" +
                "A1UECgwLZGF0YWkgZ3JvdXAxFDASBgNVBAsMC2RhdGFpIHRlY2guMRcwFQYDVQQD\n" +
                "DA4xOTIuMTY4LjE1MC42MzEkMCIGCSqGSIb3DQEJARYVbGl4aXVtaWFvQGpzZHR0\n" +
                "ZWMuY29tMB4XDTE2MDUwNDA4MzIxOFoXDTE5MDEyOTA4MzIxOFowgZwxCzAJBgNV\n" +
                "BAYTAkNOMRAwDgYDVQQIDAdqaWFuZ3N1MRAwDgYDVQQHDAduYW5qaW5nMRQwEgYD\n" +
                "VQQKDAtkYXRhaSBncm91cDEUMBIGA1UECwwLZGF0YWkgdGVjaC4xFzAVBgNVBAMM\n" +
                "DjE5Mi4xNjguMTUwLjYzMSQwIgYJKoZIhvcNAQkBFhVsaXhpdW1pYW9AanNkdHRl\n" +
                "Yy5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCpsJjY8zjXlgbC\n" +
                "+cx9nkJwNUwSe47TjI4ZEM0ulqBKVm5n7E2jXDQOFVRxlncEtcunjsEkV5cWSXEK\n" +
                "LUIwNUL2sSc3gaEhv/smOh1UQemEJu/y1hyr+cTAAMVRDC84RRRQbahjtB3b+lE8\n" +
                "21OgBPNGYQQjmRQ9ecitnD4TAByJwBM0xQw4Gxzn2EOS7hm3efW9MLvLfFupq9uR\n" +
                "H6o7lqDokO6/bqM6PtgZoOMZgI/hOqJeO3xkFajMRIrARkrfEzpgJaILIzcdxoGy\n" +
                "W5jZ1yFyufW9VS/n/QOdL6bIbNgpkF7aRwuGubdOcLIm6bKw6I7RycS2aUMIAE8x\n" +
                "67mnjE1hAgMBAAGjUDBOMB0GA1UdDgQWBBTGsEhQwEthELs00mih40tGxMmkszAf\n" +
                "BgNVHSMEGDAWgBTGsEhQwEthELs00mih40tGxMmkszAMBgNVHRMEBTADAQH/MA0G\n" +
                "CSqGSIb3DQEBBQUAA4IBAQB73d3pHPBozH0AyBswELf0+Y8aXjVZLw3IRjkALZEw\n" +
                "Jjce81Nh/mJfDuYEhQulH/Nt53CbRRml+naP2RmTO+wOohutG7Te3Vw8+naipZQj\n" +
                "bAtXUyMNJK2/1psoQRLe8ZC7rbnjNaeHeymceFQa+91NrvnaHVfDX3uD8va7Nov+\n" +
                "O1POQRYccpAqMlbdtOxWYWQqWiGbw5fTM5OlNLCgygyP4EmEXK5gAgCkb/fT8cMq\n" +
                "95NsRebLZnxhPNbSgcU1aJXJvE25CTyZ4MLyeiU1I82C6Z+ZcSJ6W6L8TpmEgdR7\n" +
                "up0I2KluQfpVJlpILGiWfCWapo9C3fkSr6Q7zTM8jX3F\n" +
                "-----END CERTIFICATE-----\n";

        final String PUER_WARNING_APP_WAN_PRODUCE_CERT = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFPjCCAyYCCQDeyNsJYQxUNTANBgkqhkiG9w0BAQsFADBhMQswCQYDVQQGEwJD\n" +
                "TjELMAkGA1UECAwCSlMxCzAJBgNVBAcMAk5KMQ0wCwYDVQQKDAREWVlUMQ0wCwYD\n" +
                "VQQLDAREWVlUMRowGAYDVQQDDBFhcHBjbGllbnQuamVtLmNvbTAeFw0xNjA2MDYw\n" +
                "NTQxMjFaFw0yNjA2MDQwNTQxMjFaMGExCzAJBgNVBAYTAkNOMQswCQYDVQQIDAJK\n" +
                "UzELMAkGA1UEBwwCTkoxDTALBgNVBAoMBERZWVQxDTALBgNVBAsMBERZWVQxGjAY\n" +
                "BgNVBAMMEWFwcGNsaWVudC5qZW0uY29tMIICIjANBgkqhkiG9w0BAQEFAAOCAg8A\n" +
                "MIICCgKCAgEAwotI4IXrUqQOCh3E2Ejx+ix9xLDNjCpJBJf4zzKB8+05iSjL94ZO\n" +
                "NaUhMPWw+8BzYpYCH9B6RsaOpP2NUT8EG72topINh/vWBy7R2IGPfPbpvMIBWn3K\n" +
                "TuJxQjDx/w/ns8QETEUQDAVkkWUv2qqUw6E2oPOWPY5dJvnWJ6Q0fbCs/r11cIKh\n" +
                "zLp/sMKfLq2MgZuoLGYSmFyHJRP0oattUbWEkadyFyXbn/3QTD0iU7jXhxkpVQlv\n" +
                "pbj3Zo/5+YnAWptXin9Sa5PEuqHTXZ6Y9BXrp+n6rlEo1Qi/15FZtIoMBnS2N3qX\n" +
                "uRX3RlehccSjOUgHbelyaj1Zw6wOS4b3zGYKqh+m2x+Kluwh45Z72xGz078OTjI0\n" +
                "hQXZPePj4lChRY3+Gpm3i3imHFuQwavnXs2a+yz8gyj0lSM9fmN5TSGNi1zgx9+N\n" +
                "X1W6kCm/KajlIp1yDMNxjI/pDdOcx69P3UqLp6hvdxfv7xjc03HRqUdvGzbi8ucI\n" +
                "dvPXknEuZQO7ofKeEMMvFv4sZcbciDPHMf0MNzRASarJleDcO0Xmf44VDn+nDxFq\n" +
                "XYhALECYO9Q+bX4pfzGqNRopf5XnRJBieFTLBX3oT6adNp4BsCxukTWRXOK3yQhV\n" +
                "zbR4icRziCh7yqmbf8gwAQvnvbX5UgGh5RaUtR3SOMgYfOA3PFIoG2kCAwEAATAN\n" +
                "BgkqhkiG9w0BAQsFAAOCAgEAeLshqhNM5f6q6aYwKNny1W2d+ROhxYaaGQ0vBwu+\n" +
                "lgCDnJ4WUlpXtyjUKXW4YU4Yjb1cuqwJp3GO8mSF/vI5DJ4/4SAWsaESIUAbdIDT\n" +
                "t24QWU1pJauNpzepnyf2RaX2PndiHahWp1xWLGLeED5PIfZxOfF+IE0yuPNtNLzy\n" +
                "5N/nMq8EMnloSZlTLdpbicSFvn/rPAcQ5yQQqICC8ZwvwDcBPTPyukbYaQDItHUT\n" +
                "b9AqNHN8OsOLDaB98YGKZfzttMWIXKLBSfjfIfieWYBZKgOXxjzhx2/Din+YiZMH\n" +
                "lXoUNmDbMponA2M7kHLxfLRzDeuPgEZUBkOVtS3Z2c0YlnIROYRVqVoHe96hk3AV\n" +
                "L1LznJrVO7nePjI6ZWVkP8Mj3/gFYa0gp/cYuYRG2kzgCiqMWrUn9k1LJ7+UK3f9\n" +
                "quvQQzqoXQGQPSWfeokZf+3k0DCRGAwkfXwz2DnGukN3WT6H7M2h6tXUPAvECNQp\n" +
                "dA/Es3mRWVPvBdUUMo/XULAf+dU/NTMJdm+C69x0SwPAeaQg25W/mS0IPenQU5Z9\n" +
                "vKLdwmrgHXL7r8KrZWhR+M3TH/Vxx9KR8XepV5qk559HLT0nlp18cIcTnbzGenq4\n" +
                "50bil2YZ9nSwYaMRYj1+e7VvhW5dfS4yCnyN2XTSKyMLaJhlynjXj0sgmpuFOSOf\n" +
                "4uY=\n" +
                "-----END CERTIFICATE-----\n";

        final String NJ_UAT_REDWINE_CERT = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDOzCCAiOgAwIBAgIJAJJnzD6/wH7MMA0GCSqGSIb3DQEBBQUAMDQxFTATBgNV\n" +
                "BAMMDHd3dy5kYXRlay5jbjEOMAwGA1UECgwFRGF0ZWsxCzAJBgNVBAYTAkNOMB4X\n" +
                "DTE2MTEyOTA2NTUzMFoXDTE2MTIyOTA2NTUzMFowNDEVMBMGA1UEAwwMd3d3LmRh\n" +
                "dGVrLmNuMQ4wDAYDVQQKDAVEYXRlazELMAkGA1UEBhMCQ04wggEiMA0GCSqGSIb3\n" +
                "DQEBAQUAA4IBDwAwggEKAoIBAQCp3XnVTnH+4oPWH7trFgZgI+xst2u2hvrkUMIZ\n" +
                "QLYhYG/eapF2Eqrr4TnuKR6+58UAtrcm0jwXsYeJ3TLPv54XO1kGQTtRtYQ3YXf4\n" +
                "Loyog0Xvy1fCZM57X3RlfpvyKzrh8NePBQcDzf7lRiF26lDxcEKcb2KQK228kAuL\n" +
                "QHhDmTt53dOK+o/tpfHA/gYd5ULfkdlWSm+Ai21lZi+1RdrT7sb2dCm31z35/l/y\n" +
                "+cobCWyC8KlHoajluwr4WMdUrdpog+mh8JO2nHr604luPEF+imBmhAlIhLqHP5Zy\n" +
                "ozZ2EJYJu+QU9jZYuWcNsg2WZ7zHzz5xREBWv1im8fAosN1xAgMBAAGjUDBOMB0G\n" +
                "A1UdDgQWBBSLETO+OgPYOBbV3+SFSAMoHtn0ezAfBgNVHSMEGDAWgBSLETO+OgPY\n" +
                "OBbV3+SFSAMoHtn0ezAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4IBAQAU\n" +
                "wfA/noOA0asQ2MOCX3MbAgsXT8eWQVl8YMZPMamwv3gvlY1nOpgDFPnkgvRCxHvt\n" +
                "h2QNkZrsebx3wb0Cn56dL806TYW7Bh/MCW3ebyEJGxv+LQjy4vFfMNkI4YcBOL7U\n" +
                "5lHWfamBmRPNvUmcm1cxcEwiJA1Iigz1pnAKgLKyb72hOgo8EwkpL8Z14sz8uIEf\n" +
                "sRBMUNJ+ZUTqRfqUyTpyXR4/lW1RMkY6PEwavCfNg39uLVTnOtfyY5ENgrw4XGuh\n" +
                "l3BM4IQ9HRzIDgotFkNr1YtAdt++EeX8teuB8FF2TV+XBfEpwzddZP3DoHOXefvd\n" +
                "GvyuPti6Wts+5BCM2oMK\n" +
                "-----END CERTIFICATE-----";

        return new Buffer()
                .writeUtf8(DZ_INOUT_CERT)
                .writeUtf8(SPOT_REDWINE_CERT)
                .writeUtf8(PUER_WARNING_APP_LAN_CERT)
                .writeUtf8(PUER_WARNING_APP_WAN_PRODUCE_CERT)
                .writeUtf8(NJ_UAT_REDWINE_CERT)
                .inputStream();
    }

}