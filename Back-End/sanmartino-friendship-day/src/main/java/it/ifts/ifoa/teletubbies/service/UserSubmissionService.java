package it.ifts.ifoa.teletubbies.service;

import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.repository.UserRepository;

public class UserSubmissionService
{
    private final UserRepository userRepository;

    public UserSubmissionService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public void saveUser(User user)
    {
        this.userRepository.saveUser(user);
    }

    public boolean isEmailAlreadyTaken(User user)
    {
        return this.userRepository.isEmailAlreadyTaken(user);
    }

    public boolean isFiscalCodeAlreadyTaken(User user)
    {
        return this.userRepository.isFiscalCodeAlreadyTaken(user);
    }
}
