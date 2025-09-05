package lab6.common.validators;

public interface Validator<T> {
  boolean validate(T value);
}
