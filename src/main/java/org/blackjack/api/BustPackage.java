package org.blackjack.api;

public record BustPackage(PackageType packageType, boolean isBust) implements DataPackage {
}
