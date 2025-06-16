package it.ifts.ifoa.teletubbies.entity;
import it.ifts.ifoa.teletubbies.exception.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.Random;

public class User
{

    private String name,surname, gender,phoneNumber, tokenId, email, fiscalCode,residencyCountry,residencyZipCode, residencyAddress, shipCountry, shipZipCode, shipAddress, residencyProvincia, shipProvincia;
    private boolean privacy, rules;
    private LocalDate birthDate;


    public User() {
    }

    public void checkUser(){
        checkEmail(this.email);
        checkName(this.name);
        checkSurname(this.surname);
        checkGender(this.gender);
        checkPhoneNumber(this.phoneNumber);
        checkFiscalCode(this.fiscalCode);
        checkResidencyCountry(this.residencyCountry);
        checkResidencyAddress(this.residencyAddress);
        checkResidencyZipCode(this.residencyZipCode);
        checkShipCountry(this.shipCountry);
        checkShipAddress(this.shipAddress);
        checkShipZipCode(this.shipZipCode);
        checkPrivacy(this.privacy);
        checkRules(this.rules);
        checkBirthDate(this.birthDate);
        checkResidencyProvincia(this.residencyProvincia);
        checkShipProvincia(this.shipProvincia);
    }

    public void assignTokenId(){
        String safeCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
        StringBuilder retvalue = new StringBuilder();
        Random rand = new Random();
        int totalCharacter = rand.nextInt(10)+40;

        for (int i =0;i<totalCharacter;i++){
            int num = rand.nextInt(safeCharacters.length());
            String temvalue = String.valueOf(safeCharacters.charAt(num));
            retvalue.append(temvalue);
        }

        this.tokenId = retvalue.toString();
    }

    private void checkEmail(String email) throws  InvalidEmailException{
        String regex = "^[a-zA-Z0-9]{1,}[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(regex, email)){
            throw new InvalidEmailException("0x00");
        }
    }

    private void checkName(String name) throws InvalidNameException{
        if (name == null || name.length() < 2){
            throw new InvalidNameException("0x01");
        }
    }

    private void checkSurname(String surname) throws InvalidSurnameException{
        if (surname == null || surname.length() < 2){
            throw new InvalidSurnameException("0x02");
        }
    }

    private void checkGender(String gender) throws InvalidGenderException {
        if(!gender.equals("man") && !gender.equals("woman") && !gender.equals("other") && !gender.equals("not specified")){
            throw new InvalidGenderException("0x03");
        }
    }

    private void checkPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("0x04");
        }
        String regex =  "^\\+[0-9]{6,15}$";
        if(!Pattern.matches(regex,phoneNumber)){
            throw new InvalidPhoneNumberException("0x05");
        }
    }

    private void checkFiscalCode(String fiscalCode) throws InvalidFiscalCodeException {
        if(this.residencyCountry.equals("San Marino")){
            return;
        }
        String regex = "^[a-zA-Z]{6}[a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{3}[a-zA-Z]+$";
        if ( fiscalCode.length() != 17 && !Pattern.matches(regex,fiscalCode)) {
            throw new InvalidFiscalCodeException("0x06");
        }
    }

    private void checkResidencyCountry(String residencyCountry) throws InvalidResidenceCountryException {
        if (residencyCountry == null || (!residencyCountry.equals("Italia") && !residencyCountry.equals("San Marino"))) {
            throw new InvalidResidenceCountryException("0x07");
        }
    }

    private void checkResidencyZipCode(String residencyZipCode) throws InvalidZipCodeException {
        String regex = "^[0-9]{5}$";
        if (!Pattern.matches(regex, residencyZipCode) ) {
            throw new InvalidZipCodeException("0x08");
        }

    }

    private void checkResidencyAddress(String residencyAddress) throws InvalidAddressException {
        if (residencyAddress == null && residencyAddress.length()<=2) {
            throw new InvalidAddressException("0x09");
        }
    }

    private void checkResidencyProvincia(String residencyProvincia) throws InvalidResidencyProvinciaException{
        if(this.residencyCountry.equals("San Marino")){
            return;
        }
        if(residencyProvincia == null && residencyProvincia.length()!=2){
            throw new InvalidResidencyProvinciaException("0x17");
        }
    }


    private void checkShipCountry(String shipCountry) throws InvalidResidenceCountryException {
        if (shipCountry == null || (!shipCountry.equals("Italia") && !shipCountry.equals("San Marino"))) {
            throw new InvalidResidenceCountryException("0x10");
        }
    }

    private void checkShipZipCode(String shipZipCode) throws InvalidZipCodeException {
        String regex = "^[0-9]{5}$";
        if (!Pattern.matches(regex, shipZipCode) ) {
            throw new InvalidZipCodeException("0x11");
        }
    }

    private void checkShipAddress(String shipAddress) throws InvalidAddressException
    {
        if (shipAddress == null && shipAddress.length()<=2) {
            throw new InvalidAddressException("0x12");
        }
    }

    private void checkShipProvincia(String residencyProvincia) throws InvalidShipProvinciaException{
        if(this.residencyCountry.equals("San Marino")){
            return;
        }
        if(residencyProvincia == null && residencyProvincia.length()!=2){
            throw new InvalidShipProvinciaException("0x18");
        }
    }

    private void checkPrivacy(boolean privacy) throws InvalidPrivacyException {
        if (!privacy) {
            throw new InvalidPrivacyException("0x13");
        }
    }

    private void checkRules(boolean rules) throws InvalidRulesException {
        if (!rules) {
            throw new InvalidRulesException("0x14");
        }
    }

    private void checkBirthDate(LocalDate birthDate) throws InvalidBirthDateException {
        if (birthDate == null) {
            throw new InvalidBirthDateException("0x15");
        }
        LocalDate now = LocalDate.now();
        LocalDate minimumAge = now.minusYears(18);

        if (birthDate.isAfter(minimumAge)) {
            throw new InvalidBirthDateException("0x16");
        }
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

    public String getTokenId() {
        return tokenId;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public boolean isRules() {
        return rules;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", email='" + email + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                ", residencyCountry='" + residencyCountry + '\'' +
                ", residencyZipCode='" + residencyZipCode + '\'' +
                ", residencyAddress='" + residencyAddress + '\'' +
                ", shipCountry='" + shipCountry + '\'' +
                ", shipZipCode='" + shipZipCode + '\'' +
                ", shipAddress='" + shipAddress + '\'' +
                ", privacy=" + privacy +
                ", rules=" + rules +
                ", birthDate=" + birthDate +
                '}';
    }
}