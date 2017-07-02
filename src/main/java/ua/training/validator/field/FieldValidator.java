package ua.training.validator.field;

import java.util.List;

public interface FieldValidator<T> {	
	void validateField(T fieldValue, List<String> errors);
}
