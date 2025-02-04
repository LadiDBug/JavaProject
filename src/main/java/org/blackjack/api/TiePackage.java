package org.blackjack.api;

import org.blackjack.view.TypePlayer;

/**
 * TiePackage class
 *
 * @param packageType
 * @param tie
 * @param typePlayer
 */
public record TiePackage(PackageType packageType, boolean tie, TypePlayer typePlayer) implements DataPackage {
}
