package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public final class EmailValidator implements FieldValidator<String> {

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$";

	private EmailValidator(){
		
	}
	
	private static class Holder{
		static final EmailValidator INSTANCE = new EmailValidator();
	}
	
	public static EmailValidator getInstance(){
		return Holder.INSTANCE;
	}
	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(EMAIL_REGEX)) {
			errors.add(Message.WRONG_EMAIL);
		}

	}

}
