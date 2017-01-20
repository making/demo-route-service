package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class TrustEverythingClientHttpRequestFactory
		extends SimpleClientHttpRequestFactory {

	@Override
	protected HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
		HttpURLConnection connection = super.openConnection(url, proxy);

		if (connection instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;

			httpsConnection.setSSLSocketFactory(
					getSslContext(new TrustEverythingTrustManager()).getSocketFactory());
			httpsConnection.setHostnameVerifier(new TrustEverythingHostNameVerifier());
		}

		return connection;
	}

	private static SSLContext getSslContext(TrustManager trustManager) {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			return sslContext;
		}
		catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);

		}

	}

	private static final class TrustEverythingHostNameVerifier
			implements HostnameVerifier {

		@Override
		public boolean verify(String s, SSLSession sslSession) {
			return true;
		}

	}

	private static final class TrustEverythingTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

	}
}
