package org.blackjack.api;

public record WinPackage(PackageType packageType, boolean win) implements DataPackage {
}
