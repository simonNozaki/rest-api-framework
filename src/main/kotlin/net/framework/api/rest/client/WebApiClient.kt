package net.framework.api.rest.client

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MultivaluedMap

/**
 * Web APIクライアントクラス。
 */
class WebApiClient(uri: String, path: String, headerMap: HashMap<String, String>) {

    /**
     * クラスの初期化
     */
    init {
        /**
         * APIクライアントインスタンス
         */
        val client: Client = ClientBuilder.newClient();

        /**
         * API接続先設定
         */
        var webTarget: WebTarget = client.target(uri).path(path);

        /**
         * ヘッダー指定
         */
        var headers: MultivaluedMap<String, Object>;
        headerMap.map { entry -> {
//            headers.putSingle(entry.key, (entry.value);
        } }
    }


}