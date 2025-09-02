package lab5.collectionManagers.validators;

public interface Validator<T> {
  boolean validate(T value);
}
