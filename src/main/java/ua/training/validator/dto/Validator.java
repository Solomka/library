package ua.training.validator.dto;

import java.util.List;

public interface Validator<T> {
	List<String> validate(T dto);
}
