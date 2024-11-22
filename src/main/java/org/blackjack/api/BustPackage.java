package org.blackjack.api;

import org.blackjack.view.TypePlayer;

public record BustPackage(PackageType packageType, boolean isBust, TypePlayer typePlayer) implements DataPackage {
}
