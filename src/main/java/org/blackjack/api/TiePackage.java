package org.blackjack.api;

import org.blackjack.view.TypePlayer;

public record TiePackage(PackageType packageType, boolean tie, TypePlayer typePlayer) implements DataPackage {
}
