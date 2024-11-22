package org.blackjack.api;

import org.blackjack.view.TypePlayer;

public record WinPackage(PackageType packageType, boolean win, TypePlayer typePlayer) implements DataPackage {
}
