package com.ckview.utils.db;

public class Key extends Item {
	/**
	 * @return the identity
	 */
	public boolean isIdentity() {
		return identity;
	}

	public Key() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Key(String name, String preproty, boolean unique, boolean notNull,
			String defaultValue, int size, String type) {
		super(name, preproty, unique, notNull, defaultValue, size, type);
		// TODO Auto-generated constructor stub
	}
	
	public Key(boolean identity, String name, String preproty, boolean unique, boolean notNull,
			String defaultValue, int size, String type) {
		super(name, preproty, unique, notNull, defaultValue, size, type);
		// TODO Auto-generated constructor stub
		this.identity = identity;
	}

	public Key(boolean identity) {
		super();
		this.identity = identity;
	}

	/**
	 * @param identity the identity to set
	 */
	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	private boolean identity;
}
