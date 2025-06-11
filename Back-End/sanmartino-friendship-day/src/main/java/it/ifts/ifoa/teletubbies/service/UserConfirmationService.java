package it.ifts.ifoa.teletubbies.service;

import it.ifts.ifoa.teletubbies.repository.UserRepository;

public class UserConfirmationService
{
    private final UserRepository userRepository;

    public UserConfirmationService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    public boolean confirmTokenIdAndCheckWin(String tokenId)
    {
        this.userRepository.doubleOptIn(tokenId);
        return this.userRepository.isConfirmationTop499(tokenId);
    }
}
