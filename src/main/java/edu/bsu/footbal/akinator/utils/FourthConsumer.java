package edu.bsu.footbal.akinator.utils;

/**
 * created by @Kabaye
 * date 15.11.2020
 */
@FunctionalInterface
public interface FourthConsumer<F, S, T, V> {
    void accept(F first, S second, T third, V fourth);
}
