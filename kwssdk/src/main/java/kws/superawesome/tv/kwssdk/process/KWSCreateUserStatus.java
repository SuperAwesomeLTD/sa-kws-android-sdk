package kws.superawesome.tv.kwssdk.process;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public enum KWSCreateUserStatus {
    Success,
    InvalidUsername,
    InvalidPassword,
    InvalidDateOfBirth,
    InvalidCountry,
    InvalidParentEmail,
    DuplicateUsername,
    NetworkError,
    InvalidOperation
}
