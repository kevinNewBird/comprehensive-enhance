package com.base.vavr;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

/**
 * @Description:
 * @Author:zhao.song
 * @Date:2019/12/14 16:22
 * @Version:1.0
 */
public class PersonValidator {

    String NAME_ERR = "Invalid characters in name:";
    String AGE_ERR = "Age must be at least 0";

    public Validation<Seq<String>,Person> validatePerson(String name, Integer age){
        return Validation.combine(validateName(name),validateAge(age)).ap(Person::new);
    }

    private Validation<String, String> validateName(String name) {
        String invalidChars = name.replaceAll("[a-zA-Z ]", "");
        return invalidChars.isEmpty() ?
                Validation.valid(name)
                : Validation.invalid(NAME_ERR + invalidChars);
    }

    private Validation<String, Integer> validateAge(Integer age) {
        return age < 0 ? Validation.invalid(AGE_ERR)
                : Validation.valid(age);
    }
}
