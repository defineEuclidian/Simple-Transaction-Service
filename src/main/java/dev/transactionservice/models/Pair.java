package dev.transactionservice.models;

/**
 * Pair record that can store two generic objects at once, useful as a key for hash maps.
 * 
 * @param first generic type T1
 * @param second generic type T2
 */
public record Pair<T1, T2>(T1 first, T2 second) {}
