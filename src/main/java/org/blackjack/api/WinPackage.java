package org.blackjack.api;

import org.blackjack.view.TypePlayer;

/**
 * WinPackage class
 *
 * @param packageType
 * @param win
 * @param typePlayer
 */
public record WinPackage(PackageType packageType, boolean win, TypePlayer typePlayer) implements DataPackage {
}
