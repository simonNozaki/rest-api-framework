package net.framework.api.rest.client

import com.fasterxml.jackson.databind.ObjectMapper
import net.framework.api.rest.consts.AppConst
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
    val uri: String = uri

    /**
     * パスパラメータ
     */
    val path: String = path

    /**
     * HTTPヘッダー情報map
     */
    val headerMap: Map<String, String> = headerMap

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
            }
        }
    }

    /**
     * GETリクエスト
     * @param targetClass バインド対象クラス
     * @return T 戻り値、引数のクラスにバインドして返却
     */
    fun get(targetClass: Class<T>): T {
        try {
            // ヘッダー指定なしならそのまま実行
            if(ObjectUtil.isNullOrEmpty(headers)){
                return webTarget.request(MediaType.APPLICATION_JSON)
                        .get(targetClass);
            }
            return webTarget.request()
                    .headers(headers)
                    .get(targetClass);
        }catch (e: BadRequestException){
            throw WebApiBadRequestException(AppConst.WEB_API_REQUEST_ERROR);
        }
    }

    /**
     * POSTリクエスト
     * @param targetClass バインド対象クラス
     * @return T 戻り値、引数のクラスにバインドして返却
     */
    fun <V> post(data: V, targetClass: Class<T>): T{
        try{
            // ヘッダー指定なし
            if(ObjectUtil.isNullOrEmpty(headers)){
                return webTarget.request()
                        .post(Entity.entity(data, MediaType.APPLICATION_JSON), targetClass);
            }
            return webTarget.request()
                    .headers(headers)
                    .post(Entity.entity(data, MediaType.APPLICATION_JSON), targetClass);
        }catch (e: BadRequestException){
            throw WebApiBadRequestException(AppConst.WEB_API_REQUEST_ERROR);
        }
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