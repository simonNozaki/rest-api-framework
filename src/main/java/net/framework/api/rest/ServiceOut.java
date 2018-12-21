package net.framework.api.rest;

/**
 * 共通Service処理結果オブジェクト。
 * @param <T> 型引数
 */
public class ServiceOut<T> {

	/**
	 * 正常結果のプロパティ
	 */
	private T value;

	/**
	 * エラー結果
	 */
	private Errors errors;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

}
