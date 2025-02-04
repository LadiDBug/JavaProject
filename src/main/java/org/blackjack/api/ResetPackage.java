package org.blackjack.api;

import org.blackjack.view.TypePlayer;

/**
 * ResetPackage class
 *
 * @param packageType
 * @param typePlayer
 */
public record ResetPackage(PackageType packageType, TypePlayer typePlayer) implements DataPackage {
}
