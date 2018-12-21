package net.framework.api.rest.util;

import java.util.ArrayList;
import java.util.List;

/**
 * オブジェクト操作のユーティリティクラス。
 */
public class ObjectUtil {

	/**
	 * 与えられた引数からリストを作成します。
	 * @param ...t 一つ以上のオブジェクト、全て同じ型を指定する
	 * @return List<T> 引数で与えられたオブジェクトを結合したリスト
	 */
	public <T> List<T> convertToList(T...t) {
		List<T> result = new ArrayList<>();
		for (T instance : t) {
			result.add(instance);
		}
		return result;
	}
}
