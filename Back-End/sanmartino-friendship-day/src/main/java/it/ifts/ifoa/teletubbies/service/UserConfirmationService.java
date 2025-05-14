package it.ifts.ifoa.teletubbies.service;

import it.ifts.ifoa.teletubbies.repository.UserRepository;

public class UserConfirmationService
{
    private final UserRepository userRepository;

    public UserConfirmationService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    public boolean confirmKeyAndCheckWin(String key)
    {
        this.userRepository.doubleOptIn(key);
        return this.userRepository.isConfirmationTop499(key);
    }
}
