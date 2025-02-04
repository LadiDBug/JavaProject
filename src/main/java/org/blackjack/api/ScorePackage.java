package org.blackjack.api;

import org.blackjack.view.TypePlayer;

import java.util.List;

/**
 * ScorePackage class
 *
 * @param packageType
 * @param scores
 * @param player
 */
public record ScorePackage(PackageType packageType, List<Integer> scores, TypePlayer player) implements DataPackage {
}
