package net.framework.api.rest.helper;

import net.framework.api.rest.model.Errors;
import net.framework.api.rest.model.ServiceOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The helper class for a service layer of DDD.
 * This class help programmer to handle service responses.
 * First of all, a target service class inherit this class, and call {@code doPipeServiceOut}.
 */
public class ServiceHelper {

	/**
	 * start a pipeline to build a output generator.
	 * @param <T> generics of a value for an internal singleton class
	 * @return the instance of ServiceOutBuilder, that has a generics of type T.
	 */
	protected static <T> ServiceOutBuilder<T> doPipeServiceOut() {
		return new ServiceOutBuilder<>();
	}

	/**
	 * Serviceクラスの、アウトプットを出力する操作を提供するクラスです。
	 */
	protected static class ServiceOutBuilder<T> {
		private final T value;

		private final Errors errors;

		// デフォルトコンストラクタ禁止
		private ServiceOutBuilder() {
			this.value = null;
			this.errors = new Errors();
		}

		// デフォルトコンストラクタ。引数あり。
		private ServiceOutBuilder(T value, Errors errors) {
			this.value = value;
            this.errors = errors;
		}

		/**
		 * Set error.
		 * This is an intermediate operation.
		 * @param code error codes or messages
		 * @return ServiceOutBuilder
		 */
		public ServiceOutBuilder<T> setError(String code) {
		    // If an error is not initialized, initialize an error
		    Errors newErr = Optional.ofNullable(this.errors).orElse(new Errors());
			newErr.setCodes(new ArrayList<>(Arrays.asList(code)));
			return new ServiceOutBuilder<>(this.value, newErr);
		}

		/**
		 * Set error codes.
		 * @param codes an error code
		 * @return a ServiceOutBuilder instance
		 */
		public ServiceOutBuilder<T> setErrors(List<String> codes) {
			// Initialize an error object.
			Errors newErr = Optional.ofNullable(this.errors).orElse(new Errors());
			newErr.setCodes(new ArrayList<>(codes));
			return new ServiceOutBuilder<>(this.value, newErr);
		}

		/**
		 * Set a result of down layers like repositories.
		 * This is an intermediate operation.
		 * @param input a result
		 * @param <V> generics of an input
		 * @return ServiceOutBuilder
		 */
		public <V> ServiceOutBuilder<V> setResult(V input) {
			return new ServiceOutBuilder<>(input, null);
		}

		/**
		 * Build an output of a service class based on the value and the error.
		 * This is a terminal operation.
		 * @return a ServiceOut instance
		 */
		public ServiceOut<T> build() {
			ServiceOut<T> serviceOut = new ServiceOut<>();
			serviceOut.setValue(value);
			serviceOut.setErrors(errors);
			return serviceOut;
		}
	}

}
