package net.framework.api.rest.client

import com.fasterxml.jackson.databind.ObjectMapper
import net.framework.api.rest.exception.WebApiBadRequestException
import net.framework.api.rest.util.ObjectUtil
import javax.ws.rs.BadRequestException
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedHashMap
import javax.ws.rs.core.MultivaluedMap

/**
 * Web APIクライアントクラス。
 */
class WebApiClient<T>(uri: String, path: String, headerMap: Map<String, String>) {

    /**
     * URI
     */
    val uri: String = uri;

    /**
     * パスパラメータ
     */
    val path: String = path;

    /**
     * HTTPヘッダー情報map
     */
    val headerMap: Map<String, String> = headerMap;

    /**
     * HTTPヘッダー
     */
    var headers: MultivaluedMap<String, Any> = MultivaluedHashMap();

    /**
     * APIクライアントインスタンス
     */
    var client: Client = ClientBuilder.newClient();

    /**
     * API接続先設定
     */
    var webTarget: WebTarget = client.target(uri).path(path);

    init {
        /**
         * ヘッダー指定
         */
        if(!ObjectUtil.isNullOrEmpty(headerMap)){
            headerMap.map { entry: Map.Entry<String, String> -> {
                    headers.putSingle(entry.key, entry.value as Any);
                }
            };
        }
    }

    /**
     * GETリクエスト
     * @param targetClass バインド対象クラス
     * @return T 戻り値、引数のクラスにバインドして返却
     */
    fun get(targetClass: Class<T>): T {
        try {
            var result: String = webTarget.request()
                    .headers(headers)
                    .get(String::class.java);
            return ObjectMapper().readValue(result, targetClass);
        }catch (e: BadRequestException){
            throw WebApiBadRequestException("resourcesへの通信時に例外が発生しました。");
        }
    }

    /**
     * POSTリクエスト
     * @param targetClass バインド対象クラス
     * @return T 戻り値、引数のクラスにバインドして返却
     */
    fun post(targetClass: Class<T>): T{
        var result: String = webTarget.request()
                .headers(headers)
                .post(Entity.entity(targetClass, MediaType.APPLICATION_JSON), String::class.java);
        return ObjectMapper().readValue(result, targetClass);
    }

    /**
     * PUTリクエスト
     * @param targetClass バインド対象クラス
     * @return T 戻り値、引数のクラスにバインドして返却
     */
    fun put(targetClass: Class<T>): T{
        var result: String = webTarget.request()
                .headers(headers)
                .put(Entity.entity(targetClass, MediaType.APPLICATION_JSON), String::class.java);
        return ObjectMapper().readValue(result, targetClass);
    }

    /**
     * DELETEリクエスト
     * @param targetClass バインド対象クラス
     * @return T 戻り値、引数のクラスにバインドして返却
     */
    fun delete(targetClass: Class<T>): T{
        var result: String = webTarget.request()
                .headers(headers)
                .delete(String::class.java);
        return ObjectMapper().readValue(result, targetClass);
    }

}