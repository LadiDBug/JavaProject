package org.blackjack.api;

public record TiePackage(PackageType packageType, boolean tie) implements DataPackage {
}
