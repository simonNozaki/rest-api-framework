package net.framework.api.rest;

import java.util.List;

/**
 * エラーの抽象オブジェクト
 */
public abstract class AbstractErrors {

    /**
     * エラーコードリスト
     */
    private List<String> codes;

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
