package net.framework.api.rest.helper;

import net.framework.api.rest.model.Errors;
import net.framework.api.rest.model.ServiceOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The helper class for a service layer of DDD.
 * This class help programmer to handle service responses.
 * First of all, a target service class inherit this class, and call {@code doPipeServiceOut}.
 *
 *
 */
public class ServiceHelper {

	/**
	 * start a pipeline to build a output generator.
	 * @return the instance of ServiceOutBuilder, that has a generics of type T.
	 */
	protected static <T> ServiceOutBuilder<T> doPipeServiceOut() {
		return new ServiceOutBuilder();
	}

	/**
	 * Serviceクラスの、アウトプットを出力する操作を提供するクラスです。
	 */
	protected static class ServiceOutBuilder<T> {
		private final T value;

		private Errors errors;

		// デフォルトコンストラクタ禁止
		private ServiceOutBuilder() {
			this.value = null;
			this.errors = new Errors();
		}

		// デフォルトコンストラクタ。引数あり。
		private ServiceOutBuilder(T value, Errors errors) {
			this.value = Objects.requireNonNull(value);
            this.errors = errors;
		}

		/**
		 * Set and error.
		 * This is an intermediate operation.
		 * @param code error codes or messages
		 * @return ServiceOutBuilder
		 */
		public ServiceOutBuilder<T> setError(String code) {
		    // エラーが初期化されていない場合、コードのリストを初期化する
		    Errors newErr = Optional.ofNullable(this.errors).orElse(new Errors());
		    List<String> codes = new ArrayList();
			codes.add(code);
			newErr.setCodes(codes);
			return new ServiceOutBuilder<T>(this.value, newErr);
		}

		/**
		 * Set error codes.
		 * @param codes
		 * @return
		 */
		public ServiceOutBuilder<T> setErrors(List<String> codes) {
			// Initialize an error object.
			Errors newErr = Optional.ofNullable(this.errors).orElse(new Errors());
			List<String> errors = new ArrayList();
			errors.addAll(codes);
			newErr.setCodes(errors);
			return new ServiceOutBuilder<T>(this.value, newErr);
		}

		/**
		 * Set a result of down layers like repositories.
		 * This is an intermediate operation.
		 * @param input a result
		 * @return ServiceOutBuilder
		 */
		public <V> ServiceOutBuilder<V> setResult(V input) {
			return new ServiceOutBuilder<V>(input, null);
		}

		/**
		 * Build an output of a service class based on the value and the error.
		 * This is a terminal operation.
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
