package com.unisound.iot.common.exception;

public class SerializationException extends BusinessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7052298729909014688L;

	/**
	 * Constructs a new <code>SerializationException</code> instance.
	 * 
	 * @param msg
	 * @param cause
	 */
	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructs a new <code>SerializationException</code> instance.
	 * 
	 * @param msg
	 */
	public SerializationException(String msg) {
		super(msg);
	}
}
