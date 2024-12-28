package org.blackjack.api;

public record BetPackage(PackageType packageType, int bet) implements DataPackage {
}
