package org.blackjack.api;

public record LosePackage(PackageType packageType, boolean lose) implements DataPackage {
}
