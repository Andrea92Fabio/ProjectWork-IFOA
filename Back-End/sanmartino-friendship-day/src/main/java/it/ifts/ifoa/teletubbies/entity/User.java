package it.ifts.ifoa.teletubbies.entity;
import it.ifts.ifoa.teletubbies.exception.*;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class User
{
    private String name,surname, gender,phoneNumber, fiscalCode,residencyCountry,residencyZipCode, residencyAddress, shipCountry, shipZipCode, shipAddress;
    private boolean privacy, rules;
    private LocalDate birthDate;

    public User(String name, String surname, String gender, String fiscalCode, String residencyCountry, String residencyZipCode, String residencyAddress, String shipCountry, String shipZipCode, String shipAddress, boolean privacy, boolean rules, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.fiscalCode = fiscalCode;
        this.residencyCountry = residencyCountry;
        this.residencyZipCode = residencyZipCode;
        this.residencyAddress = residencyAddress;
        this.shipCountry = shipCountry;
        this.shipZipCode = shipZipCode;
        this.shipAddress = shipAddress;
        this.privacy = privacy;
        this.rules = rules;
        this.birthDate = birthDate;
    }

    private String checkName(String name) throws InvalidNameException{
        if (name.length()<2){
            throw new InvalidNameException("0x01");
        }
        return name;
    }

    private String checkSurname(String surname) throws InvalidSurnameException{
        if (surname.length()<2){
            throw new InvalidSurnameException("0x02");
        }
        return surname;
    }

    private String checkGender(String gender) throws InvalidGenderException {
        if(gender.equals("Uomo") || gender.equals("Donna") || gender.equals("Altro")){
            throw new InvalidGenderException("0x03");
        }
        return gender;
    }

    private String checkPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        ArrayList<Integer> acceptedNumber = new ArrayList<Integer>();
        for(int i = 0; i<=9;i++){
            acceptedNumber.add(i);
        }
        for(int i=0;i<phoneNumber.length();i++){
            if(!acceptedNumber.contains(phoneNumber.charAt(i)) ){
                throw new InvalidPhoneNumberException("0x04");
            }
        }
        return phoneNumber;
    }

    private String  checkFiscalCode(String fiscalCode) throws InvalidFiscalCodeException {
        /*if(checkCountry.equals("San Marino"){
                    return "";
        }*/
        String arrayAcceptedChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        ArrayList<Integer> acceptedNumber = new ArrayList<Integer>();
        for(int i = 0; i<=9;i++){
            acceptedNumber.add(i);
        }
        ArrayList<Character> acceptedChar = new ArrayList<Character>();
        for(int i = 0; i< arrayAcceptedChar.length()  ;i++){
            acceptedChar.add(arrayAcceptedChar.charAt(i));
        }

        for (int i=0; i<fiscalCode.length(); i++) {
            if(i<6){
                if(!acceptedChar.contains(fiscalCode.charAt(i))){
                    throw new InvalidFiscalCodeException("0x05");
                }
            }
            if(i<8 && i>6){
                if(!acceptedNumber.contains(fiscalCode.charAt(i))){
                    throw new InvalidFiscalCodeException("0x06");
                }
            }
            if(i==8){
                if(!acceptedChar.contains(fiscalCode.charAt(i))){
                    throw new InvalidFiscalCodeException("0x07");
                }
            }
            if(i>9&&i<11){
                if(!acceptedNumber.contains(fiscalCode.charAt(i))){
                    throw new InvalidFiscalCodeException("0x08");
                }
            }
            if(i==11){
                if(!acceptedChar.contains(fiscalCode.charAt(i))){
                    throw new InvalidFiscalCodeException("0x09");
                }
            }
            if (i>12&& i<15){
                if(!acceptedNumber.contains(fiscalCode.charAt(i))){
                    throw new InvalidFiscalCodeException("0x10");
                }
            }
            if(i==15){
                /*Inserire controllo ultima lettere di chatGPT*/
            }
        }
        if (fiscalCode.length() != 16) {
            throw new InvalidFiscalCodeException("0x0");
        }
        return fiscalCode;
    }


}
