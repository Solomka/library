package ua.training.validator.field;

/**
 * Class that chains all the fields validators
 * 
 * @author Solomka
 *
 */
public final class FieldValidatorsChainGenerator {

	private FieldValidatorsChainGenerator() {
	}

	public static AbstractFieldValidatorHandler getFieldValidatorsChain() {
		AbstractFieldValidatorHandler emailFieldValidator = EmailValidator.getInstance();
		AbstractFieldValidatorHandler passwordFieldValidator = PasswordValidator.getInstance();

		AbstractFieldValidatorHandler nameTextValidator = NameValidator.getInstance();
		AbstractFieldValidatorHandler surnameTextValidator = SurnameValidator.getInstance();
		AbstractFieldValidatorHandler patronymicTextValidator = PatronymicValidator.getInstance();
		AbstractFieldValidatorHandler countryTextValidator = CountryValidator.getInstance();
		AbstractFieldValidatorHandler publisherTextValidator = PublisherValidator.getInstance();
		AbstractFieldValidatorHandler phoneFieldValidator = PhoneValidator.getInstance();
		AbstractFieldValidatorHandler addressFieldValidator = AddressValidator.getInstance();
		AbstractFieldValidatorHandler readerCardNumberFieldValidator = ReaderCardNumberValidator.getInstance();
		AbstractFieldValidatorHandler iSBNValidator = ISBNValidator.getInstance();
		AbstractFieldValidatorHandler titleValidator = TitleValidator.getInstance();
		AbstractFieldValidatorHandler inventoryNumberValidator = InventoryNumberValidator.getInstance();

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
