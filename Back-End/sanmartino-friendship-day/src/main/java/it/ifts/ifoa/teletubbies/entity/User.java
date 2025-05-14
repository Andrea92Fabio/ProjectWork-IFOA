package it.ifts.ifoa.teletubbies.entity;
import it.ifts.ifoa.teletubbies.exception.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class User
{
    private String name,surname, gender,phoneNumber, email, fiscalCode,residencyCountry,residencyZipCode, residencyAddress, shipCountry, shipZipCode, shipAddress;
    private boolean privacy, rules;
    private LocalDate birthDate;

    public User(String name, String surname, String email, String gender, String fiscalCode, String residencyCountry, String residencyZipCode, String residencyAddress, String shipCountry, String shipZipCode, String shipAddress, boolean privacy, boolean rules, LocalDate birthDate) throws InvalidNameException, InvalidSurnameException, InvalidGenderException, InvalidPhoneNumberException, InvalidFiscalCodeException, InvalidResidenceCountryException, InvalidZipCodeException, InvalidAddressException, InvalidShipCountryException, InvalidPrivacyException, InvalidRulesException, InvalidBirthDateException {

        this.name = checkName(name);
        this.surname = checkSurname(surname);
        this.gender = checkGender(gender);
        this.email = checkEmail(email);
        this.residencyCountry = checkResidencyCountry(residencyCountry);
        this.fiscalCode = checkFiscalCode(fiscalCode);
        this.residencyZipCode = checkResidencyZipCode(residencyZipCode);
        this.residencyAddress = checkResidencyAddress(residencyAddress);
        this.shipCountry = checkShipCountry(shipCountry);
        this.shipZipCode = checkShipZipCode(shipZipCode);
        this.shipAddress = checkShipAddress(shipAddress);
        this.privacy = checkPrivacy(privacy);
        this.rules = checkRules(rules);
        this.birthDate = checkBirthDate(birthDate);
        this.phoneNumber = checkPhoneNumber(phoneNumber);
    }

    private String checkEmail(String email) throws  InvalidEmailException{
        String regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}&";
        if (!Pattern.matches(regex, email)){
            throw new InvalidEmailException("0x00");
        }
        return email;
    }

    private String checkName(String name) throws InvalidNameException{
        if (name == null || name.length() < 2){
            throw new InvalidNameException("0x01");
        }
        return name;
    }

    private String checkSurname(String surname) throws InvalidSurnameException{
        if (surname == null || surname.length() < 2){
            throw new InvalidSurnameException("0x02");
        }
        return surname;
    }

    private String checkGender(String gender) throws InvalidGenderException {
        if(!gender.equals("Uomo") && !gender.equals("Donna") && !gender.equals("ALtro")){
            throw new InvalidGenderException("0x03");
        }
        return gender;
    }

    private String checkPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("0x04");
        }
        String regex =  "^\\+[0-9]{6,15}$";
        if(!Pattern.matches(regex,phoneNumber)){
            throw new InvalidPhoneNumberException("0x05");
        }
        return phoneNumber;
    }

    private String checkFiscalCode(String fiscalCode) throws InvalidFiscalCodeException {
        if(this.residencyCountry.equals("San Marino")){
            return "";
        }
        String regex = "^[a-zA-Z]{6}[a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{3}[a-zA-Z]+$";
        if ( fiscalCode.length() != 17 && !Pattern.matches(regex,fiscalCode)) {
            throw new InvalidFiscalCodeException("0x06");
        }
        return fiscalCode;
    }

    private String checkResidencyCountry(String residencyCountry) throws InvalidResidenceCountryException {
        if (residencyCountry == null || (!residencyCountry.equals("Italia") && !residencyCountry.equals("San Marino"))) {
            throw new InvalidResidenceCountryException("0x07");
        }
        return residencyCountry;
    }

    private String checkResidencyZipCode(String residencyZipCode) throws InvalidZipCodeException {
        String regex = "^[0-9]{5}";
        if (!Pattern.matches(regex, residencyZipCode) ) {
            throw new InvalidZipCodeException("0x08");
        }

        return residencyZipCode;
    }

    private String checkResidencyAddress(String residencyAddress) throws InvalidAddressException
    {
        if (residencyAddress == null && residencyAddress.length()<=2) {
            throw new InvalidAddressException("0x09");
        }
        return residencyAddress;
    }


    private String checkShipCountry(String shipCountry) throws InvalidResidenceCountryException {
        if (shipCountry == null || (!shipCountry.equals("Italia") && !shipCountry.equals("San Marino"))) {
            throw new InvalidResidenceCountryException("0x10");
        }
        return shipCountry;
    }

    private String checkShipZipCode(String shipZipCode) throws InvalidZipCodeException {
        String regex = "^[0-9]{5}";
        if (!Pattern.matches(regex, shipZipCode) ) {
            throw new InvalidZipCodeException("0x11");
        }

        return shipZipCode;
    }

    private String checkShipAddress(String shipAddress) throws InvalidAddressException
    {
        if (shipAddress == null && shipAddress.length()<=2) {
            throw new InvalidAddressException("0x12");
        }
        return shipAddress;
    }

    private boolean checkPrivacy(boolean privacy) throws InvalidPrivacyException {
        if (!privacy) {
            throw new InvalidPrivacyException("0x13");
        }
        return privacy;
    }

    private boolean checkRules(boolean rules) throws InvalidRulesException {
        if (!rules) {
            throw new InvalidRulesException("0x14");
        }
        return rules;
    }

    private LocalDate checkBirthDate(LocalDate birthDate) throws InvalidBirthDateException {
        if (birthDate == null) {
            throw new InvalidBirthDateException("0x15");
        }

        LocalDate now = LocalDate.now();
        LocalDate minimumAge = now.minusYears(18);

        if (birthDate.isAfter(minimumAge)) {
            throw new InvalidBirthDateException("0x16");
        }
        return birthDate;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getGender() {
        return gender;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getFiscalCode() {
        return fiscalCode;
    }
    public String getResidencyCountry() {
        return residencyCountry;
    }
    public String getResidencyZipCode() {
        return residencyZipCode;
    }
    public String getResidencyAddress() {
        return residencyAddress;
    }
    public String getShipCountry() {
        return shipCountry;
    }
    public String getShipZipCode() {
        return shipZipCode;
    }
    public String getShipAddress() {
        return shipAddress;
    }
    public boolean getPrivacy() {
        return privacy;
    }
    public String getEmail() { return email; }
    public boolean getRules() {
        return rules;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
}