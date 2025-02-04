package org.blackjack.api;

/**
 * UpdatePackage class
 *
 * @param packageType
 * @param totalGames
 * @param wonGames
 * @param lostGames
 * @param avatar
 * @param username
 * @param totalFiches
 * @param level
 */
public record UpdatePackage(PackageType packageType, int totalGames, int wonGames, int lostGames, String avatar,
                            String username, int totalFiches, int level) implements DataPackage {
}
