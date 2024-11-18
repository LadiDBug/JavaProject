package org.blackjack.api;

import org.blackjack.view.TypePlayer;

import java.util.List;

public record ScorePackage(PackageType packageType, List<Integer> scores, TypePlayer player) implements DataPackage {
}
