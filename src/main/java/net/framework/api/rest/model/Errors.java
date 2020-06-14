package net.framework.api.rest.model;

import java.util.List;

/**
 * Common error object
 */
public class Errors extends AbstractErrors {

	/**
	 * Default constructor
	 */
    public Errors() {}

    /**
     * Default constructor
     * @param codes an error codes list
     */
    public Errors(List<String> codes) {
        this.codes = codes;
    }

	/**
	 * Error list
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

	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}


}
