package it.ifts.ifoa.teletubbies.entity;
import it.ifts.ifoa.teletubbies.exception.*;

import java.util.ArrayList;
import java.time.LocalDate;

public class User
{
    private String name,surname, gender,phoneNumber, fiscalCode,residencyCountry,residencyZipCode, residencyAddress, shipCountry, shipZipCode, shipAddress;
    private boolean privacy, rules;
    private LocalDate birthDate;

    public User(String name, String surname, String gender, String fiscalCode, String residencyCountry, String residencyZipCode, String residencyAddress, String shipCountry, String shipZipCode, String shipAddress, boolean privacy, boolean rules, LocalDate birthDate) throws InvalidNameException, InvalidSurnameException, InvalidGenderException, InvalidPhoneNumberException, InvalidFiscalCodeException, InvalidResidenceCountryException, InvalidZipCodeException, InvalidAddressExceprion, InvalidShipCountryException, InvalidPrivacyException, InvalidRulesException, InvalidBirthDateException {

        this.name = checkName(name);
        this.surname = checkSurname(surname);
        this.gender = checkGender(gender);
        this.fiscalCode = checkFiscalCode(fiscalCode);
        this.residencyCountry = checkResidencyCountry(residencyCountry);
        this.residencyZipCode = checkResidencyZipCode(residencyZipCode);
        this.residencyAddress = checkResidencyAddress(residencyAddress);
        this.shipCountry = checkShipCountry(shipCountry);
        this.shipZipCode = checkShipZipCode(shipZipCode);
        this.shipAddress = checkShipAddress(shipAddress);
        this.privacy = checkPrivacy(privacy);
        this.rules = checkRules(rules);
        this.birthDate = checkBirthDate(birthDate);
        this.phoneNumber = phoneNumber;
        if (phoneNumber != null) {
            this.phoneNumber = checkPhoneNumber(phoneNumber);
        }
    }

    private String checkName(String name) throws InvalidNameException{
        if (name == null || name.trim().isEmpty() || name.length() < 2){
            throw new InvalidNameException("0x01");
        }
        return name;
    }

    private String checkSurname(String surname) throws InvalidSurnameException{
        if (surname == null || surname.trim().isEmpty() || surname.length() < 2){
            throw new InvalidSurnameException("0x02");
        }
        return surname;
    }

    private String checkGender(String gender) throws InvalidGenderException {
        if(!gender.equals("Uomo") && !gender.equals("Donna") && !gender.equals("Altro")){
            throw new InvalidGenderException("0x03");
        }
        return gender;
    }

    private String checkPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }

        for(int i = 0; i < phoneNumber.length(); i++){
            char c = phoneNumber.charAt(i);
            if (c < '0' || c > '9') {
                throw new InvalidPhoneNumberException("0x04");
            }
        }
        return phoneNumber;
    }

    private String checkFiscalCode(String fiscalCode) throws InvalidFiscalCodeException {
        if (fiscalCode == null || fiscalCode.trim().isEmpty()) {
            throw new InvalidFiscalCodeException("0x11");
        }

        if (fiscalCode.length() != 16) {
            throw new InvalidFiscalCodeException("0x11");
        }

        for (int i = 0; i < fiscalCode.length(); i++) {
            char c = fiscalCode.charAt(i);

            if (i < 6) {
                if (c < 'A' || c > 'Z') {
                    throw new InvalidFiscalCodeException("0x05");
                }
            }
            else if (i >= 6 && i <= 7) {
                if (c < '0' || c > '9') {
                    throw new InvalidFiscalCodeException("0x06");
                }
            }
            else if (i == 8) {
                if (c < 'A' || c > 'Z') {
                    throw new InvalidFiscalCodeException("0x07");
                }
            }
            else if (i >= 9 && i <= 10) {
                if (c < '0' || c > '9') {
                    throw new InvalidFiscalCodeException("0x08");
                }
            }
            else if (i == 11) {
                if (c < 'A' || c > 'Z') {
                    throw new InvalidFiscalCodeException("0x09");
                }
            }
            else if (i >= 12 && i <= 14) {
                if (c < '0' || c > '9') {
                    throw new InvalidFiscalCodeException("0x10");
                }
            }
            else if (i == 15) {
                if (c < 'A' || c > 'Z') {
                    throw new InvalidFiscalCodeException("0x11");
                }
            }
        }
        return fiscalCode;
    }

    private String checkResidencyCountry(String residencyCountry) throws InvalidResidenceCountryException {
        if (residencyCountry == null || residencyCountry.trim().isEmpty() ||
                (!residencyCountry.equals("Italia") && !residencyCountry.equals("San Marino"))) {
            throw new InvalidResidenceCountryException("0x12");
        }
        return residencyCountry;
    }

    private String checkResidencyZipCode(String residencyZipCode) throws InvalidZipCodeException {
        if (residencyZipCode == null || residencyZipCode.trim().isEmpty()) {
            throw new InvalidZipCodeException("0x14");
        }
        if (residencyZipCode.length() != 5) {
            throw new InvalidZipCodeException("0x14");
        }
        for (int i = 0; i < residencyZipCode.length(); i++) {
            char c = residencyZipCode.charAt(i);
            if (c < '0' || c > '9') {
                throw new InvalidZipCodeException("0x13");
            }
        }
        return residencyZipCode;
    }

    private String checkResidencyAddress(String residencyAddress) throws InvalidAddressExceprion {
        if (residencyAddress == null || residencyAddress.trim().isEmpty()) {
            throw new InvalidAddressExceprion("0x18");
        }
        return residencyAddress;
    }

    private String checkShipCountry(String shipCountry) throws InvalidShipCountryException {
        if (shipCountry == null || shipCountry.trim().isEmpty() ||
                (!shipCountry.equals("Italia") && !shipCountry.equals("San Marino"))) {
            throw new InvalidShipCountryException("0x15");
        }
        return shipCountry;
    }

    private String checkShipZipCode(String shipZipCode) throws InvalidZipCodeException {
        if (shipZipCode == null || shipZipCode.trim().isEmpty()) {
            throw new InvalidZipCodeException("0x17");
        }

        if (shipZipCode.length() != 5) {
            throw new InvalidZipCodeException("0x17");
        }
        for (int i = 0; i < shipZipCode.length(); i++) {
            char c = shipZipCode.charAt(i);
            if (c < '0' || c > '9') {
                throw new InvalidZipCodeException("0x16");
            }
        }
        return shipZipCode;
    }

    private String checkShipAddress(String shipAddress) throws InvalidAddressExceprion {
        if (shipAddress == null || shipAddress.trim().isEmpty()) {
            throw new InvalidAddressExceprion("0x19");
        }
        return shipAddress;
    }

    private boolean checkPrivacy(boolean privacy) throws InvalidPrivacyException {
        if (!privacy) {
            throw new InvalidPrivacyException("0x20");
        }
        return privacy;
    }

    private boolean checkRules(boolean rules) throws InvalidRulesException {
        if (!rules) {
            throw new InvalidRulesException("0x21");
        }
        return rules;
    }

    private LocalDate checkBirthDate(LocalDate birthDate) throws InvalidBirthDateException {
        if (birthDate == null) {
            throw new InvalidBirthDateException("0x22");
        }

        LocalDate now = LocalDate.now();
        LocalDate minimumAge = now.minusYears(18);

        if (birthDate.isAfter(minimumAge)) {
            throw new InvalidBirthDateException("0x23");
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
    public boolean isPrivacy() {
        return privacy;
    }
    public boolean isRules() {
        return rules;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setName(String name) throws InvalidNameException {
        this.name = checkName(name);
    }
    public void setSurname(String surname) throws InvalidSurnameException {
        this.surname = checkSurname(surname);
    }
    public void setGender(String gender) throws InvalidGenderException {
        this.gender = checkGender(gender);
    }
    public void setPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        this.phoneNumber = checkPhoneNumber(phoneNumber);
    }
    public void setFiscalCode(String fiscalCode) throws InvalidFiscalCodeException {
        this.fiscalCode = checkFiscalCode(fiscalCode);
    }
    public void setResidencyCountry(String residencyCountry) throws InvalidResidenceCountryException {
        this.residencyCountry = checkResidencyCountry(residencyCountry);
    }
    public void setResidencyZipCode(String residencyZipCode) throws InvalidZipCodeException {
        this.residencyZipCode = checkResidencyZipCode(residencyZipCode);
    }
    public void setResidencyAddress(String residencyAddress) throws InvalidAddressExceprion {
        this.residencyAddress = checkResidencyAddress(residencyAddress);
    }
    public void setShipCountry(String shipCountry) throws InvalidShipCountryException {
        this.shipCountry = checkShipCountry(shipCountry);
    }
    public void setShipZipCode(String shipZipCode) throws InvalidZipCodeException {
        this.shipZipCode = checkShipZipCode(shipZipCode);
    }
    public void setShipAddress(String shipAddress) throws InvalidAddressExceprion {
        this.shipAddress = checkShipAddress(shipAddress);
    }
    public void setPrivacy(boolean privacy) throws InvalidPrivacyException {
        this.privacy = checkPrivacy(privacy);
    }
    public void setRules(boolean rules) throws InvalidRulesException {
        this.rules = checkRules(rules);
    }
    public void setBirthDate(LocalDate birthDate) throws InvalidBirthDateException {
        this.birthDate = checkBirthDate(birthDate);
    }

    public void validate() throws Exception {
        try {
            checkName(this.name);
            checkSurname(this.surname);
            checkGender(this.gender);
            if (this.phoneNumber != null) {
                checkPhoneNumber(this.phoneNumber);
            }
            checkFiscalCode(this.fiscalCode);
            checkResidencyCountry(this.residencyCountry);
            checkResidencyZipCode(this.residencyZipCode);
            checkResidencyAddress(this.residencyAddress);
            checkShipCountry(this.shipCountry);
            checkShipZipCode(this.shipZipCode);
            checkShipAddress(this.shipAddress);
            checkPrivacy(this.privacy);
            checkRules(this.rules);
            checkBirthDate(this.birthDate);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isValid() {
        try {
            validate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}