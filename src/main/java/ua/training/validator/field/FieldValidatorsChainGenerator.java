package ua.training.validator.field;

public final class FieldValidatorsChainGenerator {

	private FieldValidatorsChainGenerator() {

	}

	public static FieldValidator getFieldValidatorsChain() {
		FieldValidator emailFieldValidator = EmailValidator.getInstance();
		FieldValidator passwordFieldValidator = PasswordValidator.getInstance();

		FieldValidator nameTextValidator = NameValidator.getInstance();
		FieldValidator surnameTextValidator = SurnameValidator.getInstance();
		FieldValidator patronymicTextValidator = PatronymicValidator.getInstance();
		FieldValidator countryTextValidator = CountryValidator.getInstance();
		FieldValidator publisherTextValidator = PublisherValidator.getInstance();
		FieldValidator phoneFieldValidator = PhoneValidator.getInstance();
		FieldValidator addressFieldValidator = AddressValidator.getInstance();
		FieldValidator readerCardNumberFieldValidator = ReaderCardNumberValidator.getInstance();
		FieldValidator iSBNValidator = ISBNValidator.getInstance();
		FieldValidator titleValidator = TitleValidator.getInstance();
		FieldValidator inventoryNumberValidator = InventoryNumberValidator.getInstance();

		emailFieldValidator.setNextFieldValidator(passwordFieldValidator);
		passwordFieldValidator.setNextFieldValidator(nameTextValidator);
		nameTextValidator.setNextFieldValidator(surnameTextValidator);
		surnameTextValidator.setNextFieldValidator(patronymicTextValidator);
		patronymicTextValidator.setNextFieldValidator(countryTextValidator);
		countryTextValidator.setNextFieldValidator(publisherTextValidator);
		publisherTextValidator.setNextFieldValidator(phoneFieldValidator);
		phoneFieldValidator.setNextFieldValidator(addressFieldValidator);
		addressFieldValidator.setNextFieldValidator(readerCardNumberFieldValidator);
		readerCardNumberFieldValidator.setNextFieldValidator(iSBNValidator);
		iSBNValidator.setNextFieldValidator(titleValidator);
		titleValidator.setNextFieldValidator(inventoryNumberValidator);

		return emailFieldValidator;
	}

}
