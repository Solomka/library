package ua.training.validator.field;

public final class FieldValidatorsChainGenerator {

	private FieldValidatorsChainGenerator() {

	}

	public static FieldValidator getFieldValidatorsChain() {
		FieldValidator emailFieldValidator = EmailValidator.getInstance();
		FieldValidator passwordFieldValidator = PasswordValidator.getInstance();
		
		FieldValidator plainTextValidator = PlainTextValidator.getInstance();
		FieldValidator phoneFieldValidator = PhoneValidator.getInstance();
		FieldValidator addressFieldValidator = AddressValidator.getInstance();		
		FieldValidator readerCardNumberFieldValidator = ReaderCardNumberValidator.getInstance();
		FieldValidator iSBNValidator = ISBNValidator.getInstance();
		FieldValidator titleValidator = TitleValidator.getInstance();		
		FieldValidator inventoryNumberValidator = InventoryNumberValidator.getInstance();
		
		emailFieldValidator.setNextFieldValidator(passwordFieldValidator);
		passwordFieldValidator.setNextFieldValidator(plainTextValidator);
		plainTextValidator.setNextFieldValidator(phoneFieldValidator);
		phoneFieldValidator.setNextFieldValidator(addressFieldValidator);
		addressFieldValidator.setNextFieldValidator(readerCardNumberFieldValidator);
		readerCardNumberFieldValidator.setNextFieldValidator(iSBNValidator);
		iSBNValidator.setNextFieldValidator(titleValidator);
		titleValidator.setNextFieldValidator(inventoryNumberValidator);

		return emailFieldValidator;
	}

}
