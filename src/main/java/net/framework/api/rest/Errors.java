package net.framework.api.rest;

import java.util.List;

/**
 * 共通エラーオブジェクト。
 */
public class Errors {

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
