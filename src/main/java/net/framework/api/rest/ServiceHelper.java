package net.framework.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Serviceのヘルパークラス。
 */
public class ServiceHelper {

	/**
	 * Serviceクラスの出力生成パイプラインを開始します。
	 * @param value
	 * @return
	 */
	protected static <T> ServiceOutBuilder<T> doPipeServiceOut() {
		return new ServiceOutBuilder();
	}

	/**
	 * Serviceクラスの、アウトプットを出力する操作を提供するクラスです。
	 * @param <T>
	 */
	protected static class ServiceOutBuilder<T> {
		T value;

		Errors errors;

		// デフォルトコンストラクタ禁止
		private ServiceOutBuilder() {
			this.errors = new Errors();
		}

		// デフォルトコンストラクタ。引数あり。
		private ServiceOutBuilder(T value, Errors errors) {
			this.value = Objects.requireNonNull(value);
            this.errors = errors;
		}

		/**
		 * エラーコードを設定します。これは中間操作です。
		 * @param String code
		 * @return ServiceOutBuilder<T>
		 */
		public ServiceOutBuilder<T> setError(String code) {
		    // エラーが初期化されていない場合、コードのリストを初期化する
		    Errors newErr = Optional.ofNullable(this.errors).orElse(new Errors());
		    List<String> codes = new ArrayList<>();
			codes.add(code);
			newErr.setCodes(codes);
			return new ServiceOutBuilder<T>(this.value, newErr);
		}

		/**
		 * 正常結果を設定します。
		 * @param input
		 * @return
		 */
		public <V> ServiceOutBuilder<V> setNormalResult(V input) {
			return new ServiceOutBuilder<V>(input, null);
		}

		/**
		 * 設定した値をもとにServiceクラスの出力を構築します。
		 * @return ServiceOut<T>
		 */
		public ServiceOut<T> build() {
			ServiceOut<T> serviceOut = new ServiceOut<T>();
			serviceOut.setValue(value);
			serviceOut.setErrors(errors);
			return serviceOut;
		}
	}

}
