package org.blackjack.api;

public record ScorePackage(PackageType packageType, int score) implements DataPackage {
}
