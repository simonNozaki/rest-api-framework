package net.framework.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.framework.api.rest.util.ObjectInspector;
import net.framework.api.rest.util.ObjectUtil;

/**
 * 利用サンプルクラス
 */
public class Main {

	/**
	 * サンプルデータクラス
	 */
	class Data{
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * サンプルメソッド。入力検査のイテレーション。
	 * @throws Exception
	 */
	public void iterate(List<Data> dataList) throws Exception{
		List<Errors> errors = new ArrayList<>();

		// ここから本題
		// ループのパターン
		for(Data subject : dataList){
			Errors error = ObjectInspector.of(subject)
					.isNull(subject.getKey(), "LOG002")
					.log("LOG001", "入力検査の実施")
					.build();
			Optional.ofNullable(error).ifPresent((Errors instance) -> {
				errors.add(instance);
			});
		}

		// ストリームでまるっと処理
		List<Errors> object = ObjectUtil.getStream(dataList)
		    .map((Data subject) -> {
				return ObjectInspector.of(subject)
						.isNull(subject.getKey(), "LOG002")
						.log("LOG001", "入力検査の実施")
						.build();
		    })
		    .collect(Collectors.toList());

		System.out.println(new ObjectMapper().writeValueAsString(object));
	}

}
