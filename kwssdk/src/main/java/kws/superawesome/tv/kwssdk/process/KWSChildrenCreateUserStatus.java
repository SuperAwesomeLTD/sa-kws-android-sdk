package kws.superawesome.tv.kwssdk.process;

public enum KWSChildrenCreateUserStatus {
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
