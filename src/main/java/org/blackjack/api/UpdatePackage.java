package org.blackjack.api;

public record UpdatePackage(PackageType packageType, int totalGames, int wonGames, int lostGames, String avatar,
                            String username, int totalFiches, int level) implements DataPackage {
}
