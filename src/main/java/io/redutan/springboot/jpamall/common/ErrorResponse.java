package io.redutan.springboot.jpamall.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author redutan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private String message;
	private String code;
	// private List<FieldError> errors;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FieldError {
		private String field;
		private String value;
		private String reason;
	}
}
