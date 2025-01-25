package org.blackjack.api;

import org.blackjack.view.TypePlayer;

public record ResetPackage(PackageType packageType, TypePlayer typePlayer) implements DataPackage {
}
