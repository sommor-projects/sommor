package com.sommor.bundles.outline.client;

import com.alibaba.fastjson.JSONObject;
import com.sommor.bundles.outline.client.request.ServerRenameParam;
import com.sommor.bundles.outline.client.response.AccessKey;
import com.sommor.bundles.outline.client.response.BytesTransferredResponse;
import com.sommor.bundles.outline.client.response.ListAccessKeysResponse;
import com.sommor.bundles.outline.client.response.ServerInfo;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
public class OutlineServerClient {

    private String apiUrl;

    private OkHttpClient client = getUnsafeOkHttpClient();

    private static final String METHOD_GET = "GET";

    private static final String METHOD_POST = "POST";

    private static final String METHOD_PUT = "PUT";

    private static final String METHOD_DELETE = "DELETE";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public OutlineServerClient(String apiUrl) {
        if (! apiUrl.endsWith("/")) {
            apiUrl += "/";
        }

        this.apiUrl = apiUrl;
    }

    private <T> T execute(String method, String api, Object params, Class<T> responseClass) {
        String response = execute(method, api, params);
        return com.alibaba.fastjson.JSON.parseObject(response, responseClass);
    }

    private Request buildRequest(String method, String api, Object params) {
        Request.Builder builder = new Request.Builder();

        String url = this.apiUrl + api;

        if (METHOD_GET.equals(method)) {
            if (null != params) {
                Map<String, Object> map = (JSONObject) JSONObject.toJSON(params);
                String queryString = map.entrySet().stream().map(p->p.getKey() + "=" + p.getValue())
                        .collect(Collectors.joining("&"));
                url += "?" + queryString;
            }
            builder.get();
        } else {
            RequestBody requestBody = RequestBody.create(JSON, null == params ? "{}" : JSONObject.toJSONString(params));
            builder.method(method, requestBody);
        }

        return builder
                .url(url)
                .build();
    }

    private void call(String method, String api, Object params) {
        try {
            Response response = client.newCall(buildRequest(method, api, params)).execute();
            processHttpStatusCode(method, response);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private String execute(String method, String api, Object params) {
        try {
            Response response = client.newCall(buildRequest(method, api, params)).execute();
            processHttpStatusCode(method, response);

            if (null != response.body()) {
                return response.body().string();
            }
            return null;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void processHttpStatusCode(String method, Response response) {
        boolean success = false;

        int code = response.code();
        if (METHOD_GET.equals(method)) {
            success = 200 == code;
        } else if (METHOD_POST.equals(method)) {
            success = 201 == code;
        } else if (METHOD_PUT.equals(method) || METHOD_DELETE.equals(method)) {
            success = 204 == code;
        }

        if (! success) {
            throw new ErrorCodeException(ErrorCode.of("outline.server.http.error", code));
        }
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(getSSLContext().getSocketFactory(), new AllTrustManager())
                .hostnameVerifier(DO_NOT_VERIFY)
                .build();
    }

    private static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static SSLContext getSSLContext(){
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new AllTrustManager()}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private static class AllTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public List<AccessKey> listAccessKeys() {
        ListAccessKeysResponse response = execute(METHOD_GET, "access-keys", null, ListAccessKeysResponse.class);
        return response.getAccessKeys();
    }

    public AccessKey createAccessKey() {
        return execute(METHOD_POST, "access-keys", null, AccessKey.class);
    }

    public void deleteAccessKey(String id) {
        call(METHOD_DELETE, "access-keys/" + id, null);
    }

    public void rename(ServerRenameParam param) {
        call(METHOD_PUT, "name", param);
    }

    public Map<String, Long> getBytesTransferred() {
        BytesTransferredResponse response = execute(METHOD_GET, "metrics/transfer", null, BytesTransferredResponse.class);
        return response.getBytesTransferredByUserId();
    }

    public ServerInfo info() {
        return execute(METHOD_GET, "server", null, ServerInfo.class);
    }
}
