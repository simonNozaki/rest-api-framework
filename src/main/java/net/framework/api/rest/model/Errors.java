package net.framework.api.rest.model;

import java.util.List;

/**
 * 共通エラーオブジェクト。
 */
public class Errors {

    public Errors() {}

    /**
     * デフォルトコンストラクタ。
     * @param codes an error codes list
     */
    public Errors(List<String> codes) {
        this.codes = codes;
    }

	/**
	 * エラーコードリスト
	 */
	private List<String> codes;

	/**
	 * ID
	 */
	private String id;

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
