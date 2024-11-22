package org.blackjack.api;

import org.blackjack.view.TypePlayer;

public record LosePackage(PackageType packageType, boolean lose, TypePlayer typePlayer) implements DataPackage {
}
