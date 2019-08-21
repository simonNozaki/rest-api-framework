package net.framework.api.rest;

import java.util.List;

/**
 * 共通エラーオブジェクト。
 */
public class Errors extends AbstractErrors {

    public Errors() {}

    /**
     * デフォルトコンストラクタ。
     * @param codes
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

	@Override
	public List<String> getCodes() {
		return codes;
	}

	@Override
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
