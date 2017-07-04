package ua.training.validator.entity;

import java.util.List;

public interface Validator<T> {
	List<String> validate(T dto);
}
